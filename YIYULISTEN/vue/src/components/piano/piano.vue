<template>
    <el-card class="piano-card">
        <div id="Pianoapp">

            <section class="config-container">
                <div class="container">
                    <div class="text-container">
                        <img src="@/assets/img/piano.png">
                        <h2>按键说明</h2>
                    </div>
                    <div class="image-container">
                        <img src="@/assets/img/note.png" style="width:200px;">
                    </div>
                </div>
                <!-- <div style="width: 50%"> -->
                <div class="config-elem">
                    <div class="config-elem-cell">
                        <div> 所有按键（对应键盘上的字符）:
                        </div>

                        <textarea :value="allKeys" @change="handleAllKeysChanges" rows="2" cols="60"
                            style="margin-top: 1.5%;resize:none;"
                            placeholder="add keys followed by semicolumns (e.g: a,s,d...)" readonly>
            </textarea>

                    </div>
                </div>

                <div class="config-elem">
                    <div class="config-elem-cell">
                        <label> 显示按键: </label> <input type="checkbox" v-model="showKeys" />
                    </div>
                    <div class="config-elem-cell">
                        <label> 显示音符: </label> <input type="checkbox" v-model="showNotes" />
                    </div>
                    <div v-if="showNotes" class="config-elem-cell">
                        <label> Indian Notes: </label> <input type="checkbox" v-model="indianNotes" />
                    </div>
                    <div class="config-elem-cell">
                        <label> 延长音: </label> <input type="checkbox" v-model="sustain" />
                    </div>
                </div>

                <div v-if="indianNotes" class="config-elem">
                    <div class="config-elem-cell">
                        <label> Notation Scale </label>
                        <select v-model="noteConfig.scale">
                            <option disabled value="">Please select scale</option>
                            <option selected>C</option>
                            <option>C#</option>
                            <option>D</option>
                            <option>D#</option>
                            <option>E</option>
                            <option>F</option>
                            <option>F#</option>
                            <option>G</option>
                            <option>G#</option>
                            <option>A</option>
                            <option>A#</option>
                            <option>B</option>
                        </select>
                    </div>

                    <div class="config-elem-cell">
                        <label> Notation Language </label>
                        <select v-model="noteConfig.lang">
                            <option disabled value="">Please select language</option>
                            <option selected value="bn">Bengali</option>
                            <option value="hi">Hindi</option>
                        </select>
                    </div>

                    <div class="config-elem-cell">
                        <label> Middle Octave </label> <input v-model="noteConfig.middleOctave" min="3" max="5"
                            type="number">
                    </div>

                </div>

                <div class="config-elem">
                    <div class="config-elem-cell">
                        <label> 白键按下后颜色: </label> <input v-model="whiteNoteColor" type="color">
                    </div>
                    <div class="config-elem-cell">
                        <label> 黑键按下后颜色: </label> <input v-model="blackNoteColor" type="color">
                    </div>
                </div>

                <div class="config-elem" style="display:none">
                    <div class="config-elem-cell">
                        <label> Start Octave </label> <input v-model="startOctave" min="1" max="8" type="number"
                            readonly="readonly">
                    </div>
                    <div class="config-elem-cell">
                        <label> End Octave: </label> <input v-model="endOctave" min="1" max="8" type="number"
                            readonly="readonly">
                    </div>
                </div>
                <!-- </div> -->

                <!-- <div> <hr> </div> -->
            </section>

            <section class="piano-container">
                <PianoKeyboard :showKeys="showKeys" :showNotes="showNotes" :sustain="sustain"
                    :whiteNoteColor="whiteNoteColor" :blackNoteColor="blackNoteColor" :startOctave="startOctave"
                    :endOctave="endOctave" :allKeys="allKeys" :indianNotes="indianNotes" :noteConfig="noteConfig"
                    @noteClick="noteClick" />
            </section>
        </div>
    </el-card>
</template>

<script>
import PianoKeyboard from "./keyboard"

export default {
    name: 'App',
    data: function () {
        return {
            whiteNoteColor: "#1eb7eb",
            blackNoteColor: "#f9bb2d",
            showKeys: false,
            showNotes: false,
            sustain: false,
            startOctave: 3,
            endOctave: 5,
            allKeys: ['`', `1`, '2', '3', '4', '5', '6', '7', '8', '9', '0', '-', '=',
                'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '[', ']', '\\',
                'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', ';',
                'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.'],
            indianNotes: false,
            noteConfig: {
                scale: "C",
                middleOctave: 4,
                lang: "bn"
            }
        }
    },
    components: {
        PianoKeyboard
    },
    methods: {
        handleAllKeysChanges: function (e) {
            this.allKeys = e.srcElement.value.split(',').map(elem => elem.trim())
        },
        noteClick: function (n) {
            console.log(n)
        }
    },
}
</script>

<style>
* {
    -webkit-user-select: none;
    /* Safari */
    -ms-user-select: none;
    /* IE 10+ and Edge */
    user-select: none;
    /* Standard syntax */
    box-sizing: border-box;
}

#Pianoapp {
    font-family: Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    display: flex;
    padding: 5%;
    justify-content: space-between;
    flex-direction: column;
    height: inherit;
}

body,
html {
    width: 100%;
    height: 100%;
    margin: 0;
    padding: 0;
}

.piano-container {
    margin-left: 8%;
    margin-right: 8%;
    height: 30%;
}

.config-container {
    text-align: left;
    display: flex;
    justify-content: space-between;
    flex-direction: column;
    height: 40%;
    width: 90%;
    padding-left: 6%;
}

.config-elem {
    display: flex;
    justify-content: flex-start;
}

.config-elem-cell {
    margin-right: 5%;
}

.container {
    display: flex;
    align-items: center;
    /* 垂直居中对齐 */
}

.text-container {
    flex: 1;
    /* 占据剩余的空间 */
}

.image-container {
    margin-left: 20px;
    /* 图片与文字之间的间距 */
}

.piano-card {
    border-radius: 10%;
    background-color: rgb(0, 0, 0);
    /* 设置卡片的背景色为白色 */
    padding: 90px 90px 0px 90px;
    /* 设置卡片的内边距 */
}

.piano-card .el-card__body {
    background-color: rgb(255, 255, 255);
    /* 设置卡片内部主体部分的背景色为黑色 */
    color: white;
    /* 设置卡片内部主体部分的文字颜色为白色 */
    padding-top: 10px;
    /* 设置卡片内部主体部分顶部内边距 */
    padding-left: 20px;
    /* 设置卡片内部主体部分左侧内边距 */
    padding-right: 20px;
    /* 设置卡片内部主体部分右侧内边距 */
    padding-bottom: 20px;
    /* 设置卡片内部主体部分底部内边距 */

    /* 继承父元素的圆角设置 */
}
</style>
