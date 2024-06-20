<template>
    <div class="piano-keyboard">

        <div v-for="noteObject in notes" :key="noteObject.note" class="white-note"
            :class="[noteObject.pressed ? 'white-note-pressed' : '']"
            :style="{ width: whiteNoteWidthSize + '%', background: whiteNoteBackground(noteObject.pressed) }"
            @mousedown="playNoteMouse(noteObject)" @mouseup="removePressedKeyMouse(noteObject)"
            @mouseover="playNoteHover(noteObject)" @mouseleave="removePressedKey(noteObject)">

            <div v-if="noteObject.blackNote" class="black-note"
                :class="[noteObject.blackNote.pressed ? 'black-note-pressed' : '']"
                :style="{ background: blackNoteBackground(noteObject.blackNote.pressed) }"
                @mousedown.stop="playNoteMouse(noteObject.blackNote)"
                @mouseup.stop="removePressedKeyMouse(noteObject.blackNote)"
                @mouseover.stop="playNoteHover(noteObject.blackNote)"
                @mouseleave.stop="removePressedKey(noteObject.blackNote)">

                <div class="key-group unselectable">
                    <div v-if="showKeys" class="key-input">
                        {{ noteObject.blackNote.key }}
                    </div>

                    <div v-if="showNotes"
                        :class="['key-text', 'key-text-on-black-note', indianNotes ? '' : 'key-text-vertical']">
                        {{ noteObject.blackNote.label }}
                    </div>
                </div>
            </div>

            <div class="key-group unselectable">
                <div v-if="showKeys" class="key-input">
                    {{ noteObject.key }}
                </div>

                <div v-if="showNotes" class="key-text">
                    {{ noteObject.label }}
                </div>
            </div>

        </div>
    </div>
</template>

<script>
import * as Tone from "tone";
import SwaralipiCore from "swaralipi-core"

/* Tone.js Synth instance which help us to play musical notes */
let synth = null;

export default {
    data: function () {
        return {
            notesIndexesByKey: {},
            notes: [],
            allNotes: ['C', 'D', 'E', 'F', 'G', 'A', 'B'],
            whiteNoteWidthSize: 0,
            isMousePressed: false,
            swaralipi: null
        };
    },

    props: {
        allKeys: {
            type: Array,
            default: () => [
                '`', `1`, '2', '3', '4', '5', '6', '7', '8', '9', '0', '-', '=',
                'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '[', ']', '\\',
                'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', ';',
                'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.'
            ]
        },
        whiteNoteColor: {
            type: String,
            default: "#1eb7eb"
        },
        blackNoteColor: {
            type: String,
            default: "#f9bb2d"
        },
        showKeys: {
            type: Boolean,
            default: false,
        },
        showNotes: {
            type: Boolean,
            default: false
        },
        startOctave: {
            type: Number,
            default: 3
        },
        endOctave: {
            type: Number,
            default: 5
        },
        sustain: {
            type: Boolean,
            default: false
        },
        indianNotes: {
            type: Boolean,
            default: false
        },
        noteConfig: {
            type: Object,
            default: function () {
                return {
                    scale: "C",
                    middleOctave: 4,
                    lang: "hi"
                }
            },
        },
        tone: {
            type: Boolean,
            default: true
        }
    },

    watch: {
        '$route'(to, from) {
            console.log('Route changed from', from.path, 'to', to.path);
            if (to.path === '/music/Piano') {
                console.log('Route111111111111 changed from', from.path, 'to', to.path);
                this.setupEventListeners();
            } else {
                console.log('Route22222222222 changed from', from.path, 'to', to.path);
                this.closepiano();
            }
        },
        startOctave(val) {
            this.startOctave = val;
            this.regenerate();
        },
        endOctave(val) {
            this.endOctave = val;
            this.regenerate();
        },
        allKeys(val) {
            this.allKeys = val;
            this.regenerate();
        },
        indianNotes(val) {
            this.indianNotes = val;
            this.regenerate();
        },
        scale(val) {
            this.noteConfig.scale = val;
            this.regenerate();
        },
        middleOctave(val) {
            this.noteConfig.middleOctave = val;
            this.regenerate();
        },
        lang(val) {
            this.noteConfig.lang = val;
            this.regenerate();
        }
    },

    methods: {
        setupEventListeners() {
            this.keydownHandler = this.keydownHandler || (e => this.handleKeyDown(e));
            this.keyupHandler = this.keyupHandler || (e => this.handleKeyUp(e));
            this.mouseupHandler = this.mouseupHandler || (e => this.handleMouseUp(e));

            window.addEventListener('keydown', this.keydownHandler);
            window.addEventListener('keyup', this.keyupHandler);
            window.addEventListener('mouseup', this.mouseupHandler);
        },

        closepiano() {
            window.removeEventListener('keydown', this.keydownHandler);
            window.removeEventListener('keyup', this.keyupHandler);
            window.removeEventListener('mouseup', this.mouseupHandler);
        },

        handleKeyDown(e) {
            const key = e.key;
            const index = this.notesIndexesByKey[key];

            if (index !== undefined) {
                const noteObject = this.notes[index].key === key ? this.notes[index] : this.notes[index].blackNote;
                this.playNote(noteObject);
            }
        },

        handleKeyUp(e) {
            const key = e.key;
            const index = this.notesIndexesByKey[key];

            if (index !== undefined) {
                const noteObject = this.notes[index].key === key ? this.notes[index] : this.notes[index].blackNote;
                this.removePressedKey(noteObject);
            }
        },

        handleMouseUp() {
            this.isMousePressed = false;
        },

        playNote(noteObject) {
            if (!noteObject.pressed) {
                if (this.tone) {
                    synth.triggerAttackRelease(noteObject.note, this.sustain ? "2n" : "8n");
                }
                noteObject.pressed = true;
                this.$emit("noteClick", noteObject);
            }
        },

        playNoteMouse(noteObject) {
            this.isMousePressed = true;
            this.playNote(noteObject);
        },

        playNoteHover(noteObject) {
            if (this.isMousePressed)
                this.playNote(noteObject);
        },

        removePressedKey(noteObject) {
            noteObject.pressed = false;
        },

        removePressedKeyMouse(noteObject) {
            this.isMousePressed = false;
            this.removePressedKey(noteObject);
        },

        whiteNoteBackground(pressed) {
            return pressed ? this.whiteNoteColor : 'linear-gradient(to bottom, #eee 0%, #fff 100%)';
        },

        blackNoteBackground(pressed) {
            return pressed ? this.blackNoteColor : 'linear-gradient(45deg, #555, #222)';
        },

        generateNotes() {
            let keyIndex = 0;
            let noteIndex = 0;
            this.notes.length = 0;
            for (let octave = this.startOctave; octave <= this.endOctave; octave++) {
                while (noteIndex < this.allNotes.length) {
                    const currentNote = this.allNotes[noteIndex];
                    let newNote = {
                        note: currentNote + octave,
                        key: this.allKeys[keyIndex++],
                        pressed: false,
                        label: this.getLabel(currentNote, octave)
                    }
                    if (currentNote !== "B" && currentNote !== "E") {
                        let blackNote = {
                            note: currentNote + '#' + octave,
                            key: this.allKeys[keyIndex++],
                            pressed: false,
                            label: this.getLabel(currentNote + '#', octave)
                        }
                        newNote["blackNote"] = blackNote;
                    }
                    this.notes.push(newNote);
                    if (octave === this.endOctave && currentNote === "B") break;
                    noteIndex++;
                }
                noteIndex = 0;
            }
            this.whiteNoteWidthSize = 100 / this.notes.length;
        },

        generateNotesIndexesByKey() {
            this.notesIndexesByKey = {}
            for (let index = 0; index < this.notes.length; index++) {
                this.notesIndexesByKey[this.notes[index].key] = index;
                if (this.notes[index].blackNote !== undefined)
                    this.notesIndexesByKey[this.notes[index].blackNote.key] = index;
            }
        },

        regenerate() {
            this.swaralipi = new SwaralipiCore(this.scale, this.middleOctave, this.lang);
            this.generateNotes();
            this.generateNotesIndexesByKey();
        },

        getLabel(note, octave) {
            return this.indianNotes ? this.swaralipi.convertWestern(note, octave) : note;
        }
    },

    mounted() {
        if (this.$route.path === '/music/Piano') {
            this.setupEventListeners();
        }
        if (this.tone) {
            synth = new Tone.Synth().toDestination();
        }

        this.generateNotes();
        this.generateNotesIndexesByKey();
        this.swaralipi = new SwaralipiCore(this.scale, this.middleOctave, this.lang);
    },

    beforeDestroy() {
        this.closepiano();
    }
}
</script>

<style scoped>
.piano-keyboard {
    position: relative;
    margin-top: 50px;
    height: 20vh;
    width: 100%;
}

.white-note {
    display: flex;
    justify-content: center;
    float: left;
    position: relative;
    cursor: pointer;
    color: black;
    height: 98%;
    border-radius: 0 0 5px 5px;
    box-shadow:
        0px 0px 0px rgba(255, 255, 255, 0.8) inset,
        -2px -5px 3px #ccc inset,
        0 0 3px rgba(0, 0, 0, 0.5);
}

.white-note-pressed {
    box-shadow:
        2px 0 3px rgba(0, 0, 0, 0.2) inset,
        -5px -1px 20px rgba(0, 0, 0, 0.2) inset,
        0 0 3px rgba(0, 0, 0, 0.5);
}

.black-note {
    display: flex;
    align-items: flex-end;
    justify-content: center;
    position: absolute;
    cursor: pointer;
    height: 65%;
    width: 65%;
    left: 68%;
    z-index: 1;
    color: white;
    border-radius: 0 0 3px 3px;
    box-shadow:
        -1px -1px 2px rgba(255, 255, 255, 0.2) inset,
        0 -5px 2px rgba(0, 0, 0, 0.5) inset,
        0 2px 4px rgba(0, 0, 0, 0.5);
}

.black-note-pressed {
    box-shadow:
        -1px -1px 2px rgba(255, 255, 255, 0.2) inset,
        0 -1px 2px rgba(0, 0, 0, 0.2) inset,
        0 1px 2px rgba(0, 0, 0, 0.2);
}

.key-group {
    align-self: flex-end;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: 0.8vw;
    font-size: 1.2vw;
}

.key-text {
    margin-top: 0.8vw;
}

.key-text-on-black-note {
    margin: 0.8vw 0;
    margin-top: 1vw;
}

.key-text-vertical {
    transform: rotate(-90deg);
}

.key-input {
    text-align: center;
    width: 2em;
    color: inherit;
    font-size: 1vw;
}

.unselectable {
    -webkit-touch-callout: none;
    -webkit-user-select: none;
    -khtml-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
}
</style>
