<template>
    <div style="padding: 10px; margin-bottom: 50px;">
        <div style="display: flex; align-items: center; padding-bottom: 10px; border-bottom: 1px solid #ccc;">
            <img src="@/assets/img/Symbol.png" alt="Logo" style="height: 50px; margin-right: 10px;">
            <span style="font-size: 20px; font-family: 'Microsoft YaHei'; font-weight: bold;">
                益语听音医患交流平台（Beta）
            </span>
        </div>
        <el-drawer title="在线用户" :visible.sync="drawerVisible" direction="ltr" :with-header="false" :size="'30%'"
            custom-class="full-height-drawer">
            <el-card style="width: 100%; min-height: 100vh; color: #333; background-color:white;">
                <div style="display: flex; align-items: center; padding-bottom: 10px; border-bottom: 1px solid #ccc;">
                    <img src="@/assets/img/Symbol.png" alt="Logo" style="height: 50px; margin-right: 10px;">
                    <span style="font-size: 20px; font-family: 'Microsoft YaHei'; font-weight: bold;">
                        益语听音医患交流平台（Beta）
                    </span>
                </div>
                <br />
                <div style="padding: 10px 0; font-size: 18px; font-weight: bold;">
                    在线空闲用户
                    <span style="font-size: 12px;">（点击聊天气泡开始聊天）</span>
                </div>
                <div style="padding: 10px 0" v-for="user in users" :key="user.user_id">
                    <span>{{ user.username }}</span>
                    <el-button v-if="Number(user.usertype) !== 0" class="el-icon-chat-dot-round"
                        style="margin-left: 10px; font-size: 16px; cursor: pointer"
                        @click="requestConnection(user.username), chatUser = user.username"></el-button>

                    <span style="font-size: 12px; color: limegreen; margin-left: 5px"
                        v-if="user.username === chatUser">chatting...</span>
                </div>
            </el-card>
        </el-drawer>

        <el-container style="background-color:#e6ffe6;">
            <el-tooltip v-if="!tooltipDisabled" content="点击展开在线可连接列表" placement="right" effect="light">
                <el-button icon="el-icon-menu" @click="handleClick"
                    style="position: fixed; top: 100px; left: 10px; z-index: 2000; margin-right: 10px;">
                </el-button>
            </el-tooltip>
            <br />
            <el-tooltip v-if="!tooltipDisabled" content="点击回到主页" placement="right" effect="light">
                <el-button icon="el-icon-s-home" @click="navigateToPersonal"
                    style="position: fixed; margin-top: 80px; left: 10px; z-index: 2000; margin-right: 10px;">
                </el-button>
            </el-tooltip>
            <br />
            <el-tooltip v-if="!tooltipDisabled" content="为交流平台提些建议" placement="right" effect="light">
                <el-button icon="el-icon-s-order" @click="navigateToFeedback"
                    style="position: fixed; margin-top: 130px; left: 10px; z-index: 2000; margin-right: 10px;">
                </el-button>
            </el-tooltip>
        </el-container>

        <el-col class="main-content">
            <div class="container">
                <div style="text-align: center; line-height: 50px;">
                    医患交流系统（{{ chatUser }}）
                </div>
                <div class="disconnect-button">
                    <el-button v-if="chatUser" type="danger" size="mini" @click="disconnect">断开连接</el-button>
                    <el-button v-else type="info" size="mini" disabled>没有连接</el-button>
                </div>
                <div style="height: 60%; overflow:auto; border-top: 1px solid #ccc" v-html="content"></div>
                <div style="height: 25%">
                    <textarea v-model="text" style="height: 100%; width: 100%; padding: 20px; border: none;border-radius: 10px; border-top: 1px solid #ccc;
          border-bottom: 1px solid #ccc; outline: none; resize: none;"></textarea>
                    <div style="text-align: right; padding-right: 10px">
                        <el-button type="primary" size="mini" @click="send">发送</el-button>
                    </div>
                </div>
            </div>
        </el-col>
    </div>
</template>

<script>
let socket;
export default {
    name: "Im",
    data() {
        return {
            circleUrl: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
            user: {
                user_id: '',
                user_type: 0,
            },
            drawerVisible: false,
            tooltipDisabled: false,
            isCollapse: false,
            users: [],
            chatUser: '',
            text: "",
            messages: [],
            content: ''
        }
    },
    created() {
        this.init()
    },
    mounted() {
        this.startWebSocket();
    },
    beforeDestroy() {
        this.stopWebSocket();
    },
    methods: {
        requestConnection(targetUsername) {
            if (typeof (WebSocket) == "undefined") {
                console.log("您的浏览器不支持WebSocket");
            } else {
                console.log("您的浏览器支持WebSocket");
                let message = { type: 'request', from: this.user.user_id, to: targetUsername };
                socket.send(JSON.stringify(message));
            }
        },
        disconnect() {
            let message = { type: 'disconnect', from: this.user.user_id, to: this.chatUser };
            socket.send(JSON.stringify(message));
            //this.messages = '';
            this.content = '';
            this.chatUser = '';
        },
        navigateToPersonal() {
            this.$router.push({ path: '/personal/personal' });

        },
        navigateToFeedback() {
            this.$router.push({ path: '/feedback/feedbackform' });
        },
        handleClick() {
            this.drawerVisible = !this.drawerVisible;
            this.tooltipDisabled = true;
            setTimeout(() => {
                this.tooltipDisabled = false;
            }, 0);
        },
        send() {
            if (!this.chatUser && this.user.user_type === 0) {
                this.$message.warn("请选择聊天对象");
                return;
            }
            if (!this.chatUser && this.user.user_type !== 0) {
                this.$message.warn("目前没有聊天");
                console.log(this.chatUser)
                console.log(this.user.user_type)
                return;
            }
            if (!this.text) {
                this.$message.warn("请输入内容");
            } else {
                if (typeof (WebSocket) == "undefined") {
                    console.log("您的浏览器不支持WebSocket");
                    //} else if (this.user.userstate !== 1) {
                    //this.$message.warn("连接尚未建立，请等待确认");
                } else {
                    console.log("您的浏览器支持WebSocket");
                    let message = { from: this.user.user_id, to: this.chatUser, text: this.text };
                    socket.send(JSON.stringify(message));
                    this.messages.push({ user: this.user.user_id, text: this.text });
                    this.createContent(null, this.user.user_id, this.text);
                    this.text = '';
                }
            }
        },
        createContent(remoteUser, nowUser, text) {  // 这个方法是用来将 json的聊天消息数据转换成 html的。
            let html
            // 当前用户消息
            if (nowUser) { // nowUser 表示是否显示当前用户发送的聊天消息，绿色气泡
                html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
                    "  <div class=\"el-col el-col-22\" style=\"text-align: right; padding-right: 10px\">\n" +
                    "    <div class=\"tip left\">" + text + "</div>\n" +
                    "  </div>\n" +
                    "  <div class=\"el-col el-col-2\">\n" +
                    "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
                    "    <img src=\"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png\" style=\"object-fit: cover;\">\n" +
                    "  </span>\n" +
                    "  </div>\n" +
                    "</div>";
            } else if (remoteUser) {   // remoteUser表示远程用户聊天消息，蓝色的气泡
                html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
                    "  <div class=\"el-col el-col-2\" style=\"text-align: right\">\n" +
                    "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
                    "    <img src=\"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png\" style=\"object-fit: cover;\">\n" +
                    "  </span>\n" +
                    "  </div>\n" +
                    "  <div class=\"el-col el-col-22\" style=\"text-align: left; padding-left: 10px\">\n" +
                    "    <div class=\"tip right\">" + text + "</div>\n" +
                    "  </div>\n" +
                    "</div>";
            }
            console.log(html)
            this.content += html;
        },
        toggleDrawer() {
            this.drawerVisible = !this.drawerVisible;
        },
        init() {
            this.user.user_type = localStorage.getItem('user_type');
            console.log("该用户身份为" + this.user.user_type);
            this.user.user_id = localStorage.getItem("user_id");
            let user_id = this.user.user_id;
            let user_type = this.user.user_type;
            console.log(user_id);
            console.log(user_type);
        },
        startWebSocket() {
            let _this = this;
            let user_id = this.user.user_id;
            let user_type = this.user.user_type;
            if (typeof (WebSocket) == "undefined") {
                console.log("您的浏览器不支持WebSocket");
            } else {
                console.log("您的浏览器支持WebSocket");
                let socketUrl = "ws://124.223.83.106:1127/imserver/" + user_id + "/" + user_type;
                if (socket != null) {
                    socket.close();
                    socket = null;
                }
                // 开启一个websocket服务
                socket = new WebSocket(socketUrl);
                //打开事件
                socket.onopen = function () {
                    console.log("websocket已打开");
                };
                //  浏览器端收消息，获得从服务端发送过来的文本消息
                socket.onmessage = function (msg) {
                    console.log(msg.data);
                    let data = JSON.parse(msg.data);
                    if (data.type === 'request') {
                        if (data.to === _this.user.user_id) {
                            _this.$message.success(`${data.from} 正在与您连接`)
                            _this.chatUser = data.from;
                            console.log(_this.chatUser)
                            let message = { type: 'confirm', from: _this.user.user_id, to: data.from };
                            socket.send(JSON.stringify(message));
                        }
                    } else if (data.type === 'confirm') {
                        if (data.to === _this.user.user_id) {
                            _this.$message.success("连接已经建立");
                        }
                    } else if (data.type === 'disconnect') {//被取消用户
                        if (data.to === _this.user.user_id) {
                            //_this.messages = '';
                            _this.content = '';
                            _this.chatUser = '';
                            //_this.init();
                            _this.$message.error("连接已经断开");
                        }
                    } else if (data.users) {
                        console.log(data.users)
                        _this.users = data.users.filter(user =>
                            (user.username !== user_id && user.usertype !== Number(user_type) && user.userstate !== Number(1))
                        );
                    } else {
                        // 如果服务器端发送过来的json数据 不包含 users 这个key，那么发送过来的就是聊天文本json数据
                        //  // {"from": "zhang", "text": "hello"}
                        if (data.from === _this.chatUser) {
                            console.log('asdffsaf');
                            _this.messages.push(data);
                            // 构建消息内容
                            _this.createContent(data.from, null, data.text);
                        }
                    }
                };
                //关闭事件
                socket.onclose = function (event) {
                    console.log('WebSocket closed');
                    if (!event.wasClean) {
                        console.error('WebSocket connection closed with error');
                        console.error('Code:', event.code);
                        console.error('Reason:', event.reason);
                    }
                };

                //发生了错误事件
                socket.onerror = function () {
                    console.log("websocket发生了错误");
                }
            }
        },
        stopWebSocket() {
            if (socket) {
                let message = { type: 'close' };
                socket.send(JSON.stringify(message));
                socket.close();
                socket = null;
                console.log("websocket已关闭");
            }
        }
    }
}
</script>

<style>
.tip {
    color: white;
    text-align: center;
    border-radius: 10px;
    font-family: sans-serif;
    padding: 10px;
    width: auto;
    display: inline-block !important;
    display: inline;
}

.right {
    background-color: deepskyblue;
}

.left {
    background-color: forestgreen;
}

.el-tooltip__popper[x-placement^='right'] {
    background-color: white;
    color: black;
    border: 1px solid #dcdfe6;
    padding: 10px;
}

.container {
    width: 70%;
    height: 85vh;
    margin: 0 auto;
    background-color: white;
    border-radius: 10px;
    box-shadow: 0 0 10px #ccc;
    /* Add padding to create space for the background color */
    margin-bottom: 45px;
}

.main-content {
    background-color: #e6ffe6;
    ;
    /* Set the background color for the sides */
}
</style>