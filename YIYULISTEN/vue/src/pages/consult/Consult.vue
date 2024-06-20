<template>
    <a-card>
        <div class="box">
            <div class="title">
                <span class="title-hn" style="font-weight:bold">平台机器人</span>

                <el-button class="redirect-button" @click="redirectToPage">跳转</el-button><span
                    class="additional-text">到医生咨询平台</span>
                <br /><br />
            </div>
            <div id="content" class="content">
                <div v-for="(item, index) in info" :key="index">
                    <div class="info_r info_default" v-if="item.type == 'leftinfo'">
                        <div class="con_l con_text">
                            <span class="circle circle_r">
                                <img src="@/assets/img/robot.png" style="height:100%">
                            </span>
                        </div>
                        <div class="con_r con_text">
                            <div>{{ item.content }}</div>
                            <div v-for="(item2, index) in item.question" :key="index">
                                <div class="con_que" @click="clickRobot(item2.content, item2.id)">
                                    <div class="czkj-question-msg">
                                        {{ item2.index }}
                                        {{ item2.content }}
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="time_r">{{ item.time }}</div>
                    </div>

                    <div class="info_l" v-if="item.type == 'rightinfo'">
                        <div class="con_r con_text">
                            <span class="con_l">{{ item.content }}</span>
                            <span class="circle circle_l">
                                <!-- <a-avatar :src="user.avatar" /> -->
                            </span>
                        </div>
                        <div class="time_l">{{ item.time }}</div>
                    </div>
                </div>
            </div>

            <div class="setproblem">
                <textarea placeholder="请输入您的问题..."
                    style="height: 68px;width: 100%;resize:none;padding-right:80px;outline: none;border-color:#ccc;border-radius:5px;"
                    id="text" v-model="customerText" @keyup.enter="sentMsg()"></textarea>
                <button @click="sentMsg()" class="setproblems">
                    <span style="vertical-align: 4px;">发 送</span>
                </button>
            </div>
        </div>
    </a-card>
</template>
<script>
import { mapGetters } from 'vuex'
export default {
    name: "onlineCustomer",
    components: {},
    computed: {
        ...mapGetters('account', ['user']),
    },
    data() {
        return {
            customerText: "",
            info: [
                {
                    type: "leftinfo",
                    time: this.getTodayTime(),
                    name: "robot",
                    content:
                        "您好，欢迎使用机器人资讯功能，为保证账户安全，在会话过程中请勿透露您的账号，我能为您解答耳健康的相关问题^_^，请输入任何字符以获取问题列表",
                    question: [],
                },
            ],
            timer: null,
            robotQuestion: [
                { id: 1, content: "长时间使用耳机是否对耳朵有害？", index: 1 },
                { id: 2, content: "怎样清洁耳朵？", index: 2 },
                { id: 3, content: "耳塞是否安全？", index: 3 },
                {
                    id: 4,
                    content: "什么是耳嗓子炎？",
                    index: 4,
                },
                { id: 5, content: "如何预防耳聋？", index: 5 },
                { id: 6, content: "音乐是否对治疗耳聋有益？", index: 6 },
                { id: 7, content: "哪种类型的音乐对耳聋患者有益？", index: 7 },
                { id: 8, content: "音乐听力训练是否对提高听力有帮助？", index: 8 },
                { id: 9, content: "音乐对心理健康的影响是否有助于耳聋患者？", index: 9 },
                { id: 10, content: "音乐疗法是否适用于所有类型的听力损伤？", index: 10 },

            ],
            robotAnswer: [
                {
                    id: 1,
                    content:
                        "长时间使用耳机可能导致耳朵疲劳和听力损伤。建议定期休息，并避免将音量调得过高。",
                    index: 1,
                },
                { id: 2, content: "耳朵自身有清理机制，通常不需要用棉签深入耳道。外部耳道可以用湿纸巾擦拭，内部耳朵应避免使用尖锐物品。", index: 2 },
                { id: 3, content: "正确使用耳塞可以帮助减少噪音对听力的影响，但过度使用可能导致耳朵湿疹或感染。建议根据需要使用，并确保清洁。", index: 3 },
                {
                    id: 4,
                    content: "耳嗓子炎是指咽喉部感染扩散至耳朵引起的炎症。保持口腔卫生、预防感冒可以降低发生耳嗓子炎的风险。",
                    index: 4,
                },
                { id: 5, content: "避免长时间暴露在高音量环境中，佩戴洁净耳塞保护耳朵，保持良好的生活习惯，戒除不良习惯，如长期吸烟等，有助于预防耳聋。", index: 5 },
                { id: 6, content: "没有特定类型的音乐适用于所有耳聋患者，因为每个人的喜好和反应不同。然而，某些音乐可能有助于提高情绪、减轻焦虑，从而改善生活质量。", index: 6 },
                { id: 7, content: "音乐疗法可以激发神经系统，帮助重建和加强听觉通路。通过提供声音刺激，音乐有助于激发听觉系统的功能。", index: 7 },
                { id: 8, content: "音乐听力训练可以帮助提高听觉感知能力，加强对声音的分辨和理解。这对耳聋患者的听觉康复可能有正面影响。", index: 8 },
                { id: 9, content: "音乐被认为对心理健康有积极作用，包括减轻焦虑、缓解抑郁，这些因素对耳聋患者的整体康复都可能有帮助。", index: 9 },
                { id: 10, content: "音乐疗法可以在多种听力损伤情况下使用，但最好在专业医生或听觉专家的指导下进行，以确保个性化的治疗计划。", index: 10 },
            ],
        };
    },
    created() {
        this.showTimer();
    },
    watch: {},
    methods: {
        // 用户发送消息
        redirectToPage() {
            this.$router.push('/chat')
        },
        sentMsg() {
            clearTimeout(this.timer);
            this.showTimer();
            let text = this.customerText.trim();
            if (text != "") {
                var obj = {
                    type: "rightinfo",
                    time: this.getTodayTime(),
                    content: text,
                };
                this.info.push(obj);
                this.appendRobotMsg(this.customerText);
                this.customerText = "";
                this.$nextTick(() => {
                    var contentHeight = document.getElementById("content");
                    contentHeight.scrollTop = contentHeight.scrollHeight;
                });
            }
        },
        // 机器人回答消息
        appendRobotMsg(text) {
            clearTimeout(this.timer);
            this.showTimer();
            text = text.trim();
            let answerText = "";
            let flag;
            for (let i = 0; i < this.robotAnswer.length; i++) {
                if (this.robotAnswer[i].content.indexOf(text) != -1) {
                    flag = true;
                    answerText = this.robotAnswer[i].content;
                    break;
                }
            }
            if (flag) {
                let obj = {
                    type: "leftinfo",
                    time: this.getTodayTime(),
                    name: "robot",
                    content: answerText,
                    question: [],
                };
                this.info.push(obj);
            } else {
                answerText = "您可能想问：";
                let obj = {
                    type: "leftinfo",
                    time: this.getTodayTime(),
                    name: "robot",
                    content: answerText,
                    question: this.robotQuestion,
                };
                this.info.push(obj);
            }
            this.$nextTick(() => {
                var contentHeight = document.getElementById("content");
                contentHeight.scrollTop = contentHeight.scrollHeight;
            });
        },
        sentMsgById(val, id) {
            clearTimeout(this.timer);
            this.showTimer();

            let robotById = this.robotAnswer.filter((item) => {
                return item.id == id;
            });
            let obj_l = {
                type: "leftinfo",
                time: this.getTodayTime(),
                name: "robot",
                content: robotById[0].content,
                question: [],
            };
            let obj_r = {
                type: "rightinfo",
                time: this.getTodayTime(),
                name: "robot",
                content: val,
                question: [],
            };
            this.info.push(obj_r);
            this.info.push(obj_l);
            this.$nextTick(() => {
                var contentHeight = document.getElementById("content");
                contentHeight.scrollTop = contentHeight.scrollHeight;
            });
        },
        // 点击机器人的单个问题
        clickRobot(val, id) {
            this.sentMsgById(val, id);
        },
        // 结束语
        endMsg() {
            let happyEnding = {
                type: "leftinfo",
                time: this.getTodayTime(),
                content: "您可以回到其他页面",
                question: [],
            };
            this.info.push(happyEnding);
            this.$nextTick(() => {
                var contentHeight = document.getElementById("content");
                contentHeight.scrollTop = contentHeight.scrollHeight;
            });

        },
        showTimer() {
            this.timer = setTimeout(this.endMsg, 1000 * 60);
        },
        getTodayTime() {
            // 获取当前时间
            var day = new Date();
            let seconds = day.getSeconds();
            if (seconds < 10) {
                seconds = "0" + seconds;
            } else {
                seconds = "" + seconds;
            }
            let minutes = day.getMinutes();
            if (minutes < 10) {
                minutes = "0" + minutes;
            } else {
                minutes = "" + minutes;
            }
            let time =
                day.getFullYear() +
                "-" +
                (day.getMonth() + 1) +
                "-" +
                day.getDate() +
                " " +
                day.getHours() +
                ":" +
                minutes +
                ":" +
                seconds;
            return time;
        },
    },
    mounted() { },
    props: {},
    destroyed() { },
};
</script>
<style lang="less">
.box {
    width: 100%;
    /* width: 680px; */
    height: 700px;
    background-color: #fafafa;
    position: relative;
    padding: 1.25rem;

    #content {
        height: 480px;
        overflow-y: scroll;
        font-size: 14px;
        width: 100%;

        .circle {
            display: inline-block;
            width: 34px;
            height: 34px;
            border-radius: 50%;
            background-color: #eff1f3;
        }

        .con_text {
            color: #333;
            margin-bottom: 5px;
        }

        .con_que {
            color: #1c88ff;
            height: 30px;
            line-height: 30px;
            cursor: pointer;
        }

        .info_r {
            position: relative;

            .circle_r {
                position: absolute;
                left: 0%;
            }

            .pic_r {
                width: 17px;
                height: 17px;
                margin: 8px;
            }

            .con_r {
                display: inline-block;
                /* max-width: 253px; */
                width: 55%;
                min-height: 55px;
                /* min-height: 20px; */
                background-color: #e2e2e2;
                border-radius: 6px;
                padding: 10px;
                margin-left: 40px;
            }

            .time_r {
                margin-left: 45px;
                color: #999999;
                font-size: 12px;
            }
        }

        .info_l {
            text-align: right;
            // margin-right: 20px;
            color: #ffffff;
            color: #3163C5;
            margin-top: 10px;

            // .circle_l {
            //   // vertical-align: -10px;
            // }
            .pic_l {
                width: 13px;
                height: 17px;
                margin: 8px 10px;
            }

            .time_l {
                margin-right: 45px;
                color: #999999;
                font-size: 12px;
                margin-top: 5px;
            }

            .con_l {
                display: inline-block;
                width: 220px;
                min-height: 51px;
                background-color: #3163C5;
                border-radius: 6px;
                padding: 10px;
                text-align: left;
                color: #fff;
                margin-right: 5px;
            }
        }

        #question {
            cursor: pointer;
        }
    }
}

.redirect-button {
    margin-left: 50px;
    padding: 5px 10px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.redirect-button:hover {
    background-color: #0056b3;
    /* 按钮悬停时背景色 */
}

.setproblem {
    width: 100%;
    height: 68px;
    background-color: #ffffff;
    position: relative;
    margin-top: 3.75rem;

}

.setproblem textarea {
    color: #999999;
    padding: 10px;
    box-sizing: border-box;
}

.setproblem button {
    width: 5.875rem;
    height: 2.5rem;
    line-height: 2.5rem;
    background: #3163C5;
    opacity: 1;
    border-radius: 4px;
    font-size: 10px;
    color: #ffffff;
    position: absolute;
    right: 5%;
    top: 30%;
    cursor: pointer;
    border: none;
}

.czkj-item-title {
    line-height: 25px;
    border-bottom: 1px solid #ccc;
    padding-bottom: 5px;
    margin-bottom: 5px;
}

.czkj-item-question {
    cursor: pointer;
    display: block;
    padding: 8px;
    position: relative;
    border-bottom: 1px dashed #ccc;
    line-height: 20px;
    min-height: 20px;
    overflow: hidden;
}

.czkj-question-msg {
    float: left;
    font-size: 14px;
    color: #3163C5;
}
</style>