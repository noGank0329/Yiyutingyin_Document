<template>
    <div class="play-audio">
        <h2 style="text-align: center;">播放页面</h2>
        <div class="main-box">
            <video ref="localVideo" class="video" autoplay="autoplay"></video>
            <video ref="remoteVideo" class="video" height="500px" autoplay="autoplay"></video>
        </div>
        <div style="text-align: center;">
            <el-button @click="requestConnect()" ref="callBtn">开始对讲</el-button>
            <el-button @click="hangupHandle()" ref="hangupBtn">结束对讲</el-button>
        </div>
        <div style="text-align: center;">
            <label for="name">发送人：</label>
            <input type="text" id="name" readonly v-model="userId" class="form-control" />
        </div>
        <div style="text-align: center;">
            <label for="name">接收人：</label>
            <input type="text" id="name" v-model="toUserId" class="form-control" />
        </div>

    </div>

</template>

<el-dialog :title="'提示'" :visible.sync="dialogVisible" width="30%">
  <span>{{ toUserId + '请求连接!' }}</span>
  <span slot="footer" class="dialog-footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="dialogVisibleYes">确 定</el-button>
    </span>
  </el-dialog>

<script>
import request from '@/utils/reeques'
import Websocket from '@/utils/websocket'
import Stomp from "stompjs";
import SockJS from "sockjs-client";
import adapter from "webrtc-adapter";
import axios from 'axios'

export default {
    data() {
        return {
            stompClient: null,
            userId: '001',
            socket: null,
            toUserId: '',
            localStream: null,
            remoteStream: null,
            localVideo: null,
            remoteVideo: null,
            callBtn: null,
            hangupBtn: null,
            peerConnection: null,
            dialogVisible: false,
            msg: '',
            config: {
                iceServers: [
                    { urls: 'stun:global.stun.twilio.com:3478?transport=udp' }
                ],
            }

        };
    },
    computed: {},
    methods: {
        handleClose() {
            this.dialogVisible = false
        },
        dialogVisibleYes() {
            var _self = this;
            this.dialogVisible = false
            _self.startHandle().then(() => {
                _self.stompClient.send("/api/chat", _self.toUserId, { 'type': 'start' })
            })
        },
        requestConnect() {
            let that = this;

            if (!that.toUserId) {
                alert('请输入对方id')
                return false
            } else if (!that.stompClient) {
                alert('请先打开websocket')
                return false
            } else if (that.toUserId == that.userId) {
                alert('自己不能和自己连接')
                return false
            }
            //准备连接
            that.startHandle().then(() => {
                that.stompClient.send("/api/chat", that.toUserId, { 'type': 'connect' })
            })
        },

        startWebsocket(user) {
            let that = this;
            that.stompClient = new Websocket(user);
            that.stompClient.connect(() => {
                that.stompClient.subscribe("/user/" + that.userId + "/queue/notifications", function (result) {
                    that.onmessage(result)
                })
            })
        }
        ,
        gotLocalMediaStream(mediaStream) {
            var _self = this;
            _self.localVideo.srcObject = mediaStream;
            _self.localStream = mediaStream;
            // _self.callBtn.disabled = false;
        }
        ,
        createConnection() {
            var _self = this;
            _self.peerConnection = new RTCPeerConnection()

            if (_self.localStream) {
                // 视频轨道
                const videoTracks = _self.localStream.getVideoTracks();
                // 音频轨道
                const audioTracks = _self.localStream.getAudioTracks();
                // 判断视频轨道是否有值
                if (videoTracks.length > 0) {
                    console.log(`使用的设备为: ${videoTracks[0].label}.`);
                }
                // 判断音频轨道是否有值
                if (audioTracks.length > 0) {
                    console.log(`使用的设备为: ${audioTracks[0].label}.`);
                }

                _self.localStream.getTracks().forEach((track) => {
                    _self.peerConnection.addTrack(track, _self.localStream)
                })
            }

            // 监听返回的 Candidate
            _self.peerConnection.addEventListener('icecandidate', _self.handleConnection);
            // 监听 ICE 状态变化
            _self.peerConnection.addEventListener('iceconnectionstatechange', _self.handleConnectionChange)
            //拿到流的时候调用
            _self.peerConnection.addEventListener('track', _self.gotRemoteMediaStream);
        }
        ,
        startConnection() {
            var _self = this;
            // _self.callBtn.disabled  = true;
            // _self.hangupBtn.disabled = false;
            // 发送offer
            _self.peerConnection.createOffer().then(description => {
                console.log(`本地创建offer返回的sdp:\n${description.sdp}`)

                // 将 offer 保存到本地
                _self.peerConnection.setLocalDescription(description).then(() => {
                    console.log('local 设置本地描述信息成功');
                    // 本地设置描述并将它发送给远端
                    // _self.socket.send(JSON.stringify({
                    //   'userId': _self.userId,
                    //   'toUserId': _self.toUserId,
                    //   'message': description
                    // }));
                    _self.stompClient.send("/api/chat", _self.toUserId, description)

                }).catch((err) => {
                    console.log('local 设置本地描述信息错误', err)
                });
            })
                .catch((err) => {
                    console.log('createdOffer 错误', err);
                });
        }
        ,
        async startHandle() {
            this.callBtn = this.$refs.callBtn
            this.hangupBtn = this.$refs.hangupBtn
            this.remoteVideo = this.$refs.remoteVideo
            this.localVideo = this.$refs.localVideo
            var _self = this;
            // 1.获取本地音视频流
            // 调用 getUserMedia API 获取音视频流
            let constraints = {
                video: true,
                audio: {
                    // 设置回音消除
                    noiseSuppression: true,
                    // 设置降噪
                    echoCancellation: true,
                }
            }
            navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia
            await navigator.mediaDevices.getUserMedia(constraints)
                .then(_self.gotLocalMediaStream)
                .catch((err) => {
                    console.log('getUserMedia 错误', err);
                    //创建点对点连接对象
                });

            _self.createConnection();
        },
        onmessage(e) {
            var _self = this;
            const description = e.message
            _self.toUserId = e.from
            switch (description.type) {
                case 'connect':
                    _self.dialogVisible = true
                    this.$confirm(_self.toUserId + '请求连接!', '提示', {}).then(() => {
                        _self.startHandle().then(() => {
                            _self.stompClient.send("/api/chat", _self.toUserId, { 'type': 'start' })
                        })
                    }).catch(() => {
                    });
                    break;
                case 'start':
                    //同意连接之后开始连接
                    _self.startConnection()
                    break;
                case 'offer':
                    _self.peerConnection.setRemoteDescription(new RTCSessionDescription(description)).then(() => {

                    }).catch((err) => {
                        console.log('local 设置远端描述信息错误', err);
                    });

                    _self.peerConnection.createAnswer().then(function (answer) {

                        _self.peerConnection.setLocalDescription(answer).then(() => {
                            console.log('设置本地answer成功!');
                        }).catch((err) => {
                            console.error('设置本地answer失败', err);
                        });
                        _self.stompClient.send("/api/chat", _self.toUserId, answer)
                    }).catch(e => {
                        console.error(e)
                    });
                    break;
                case 'icecandidate':
                    // 创建 RTCIceCandidate 对象
                    let newIceCandidate = new RTCIceCandidate(description.icecandidate);
                    // 将本地获得的 Candidate 添加到远端的 RTCPeerConnection 对象中
                    _self.peerConnection.addIceCandidate(newIceCandidate).then(() => {
                        console.log(`addIceCandidate 成功`);
                    }).catch((error) => {
                        console.log(`addIceCandidate 错误:\n` + `${error.toString()}.`);
                    });
                    break;
                case 'answer':
                    _self.peerConnection.setRemoteDescription(new RTCSessionDescription(description)).then(() => {
                        console.log('设置remote answer成功!');
                    }).catch((err) => {
                        console.log('设置remote answer错误', err);
                    });
                    break;
                default:
                    break;
            }
        },
        hangupHandle() {
            var _self = this;
            // 关闭连接并设置为空
            _self.peerConnection.close();
            _self.peerConnection = null;

            // _self.hangupBtn.disabled = true;
            // _self.callBtn.disabled = false;

            _self.localStream.getTracks().forEach((track) => {
                track.stop()
            })
        },
        handleConnection(event) {
            var _self = this;
            // 获取到触发 icecandidate 事件的 RTCPeerConnection 对象
            // 获取到具体的Candidate
            console.log("handleConnection")
            const peerConnection = event.target;
            const icecandidate = event.candidate;

            if (icecandidate) {
                _self.stompClient.send("/api/chat", _self.toUserId, {
                    type: 'icecandidate',
                    icecandidate: icecandidate
                })
            }
        },
        gotRemoteMediaStream(event) {
            var _self = this;
            console.log('remote 开始接受远端流')

            if (event.streams[0]) {
                console.log(' remoteVideo')
                _self.remoteVideo.srcObject = event.streams[0];
                _self.remoteStream = event.streams[0];
            }
        },
        handleConnectionChange(event) {
            const peerConnection = event.target;
            console.log('ICE state change event: ', event);
            console.log(`ICE state: ` + `${peerConnection.iceConnectionState}.`);
        },
        log(v) {
            console.log(v)
        },
    },
    created() {
        let that = this;
        request({
            url: '/user',
            method: 'get',
            params: {}
        }).then(response => {
            console.log(response.data)
            that.userId = response.data;
            this.startWebsocket(response.data)
            debugger
        })
        debugger

    }
}

</script>
<style lang="scss">
.spreadsheet {
    padding: 0 10px;
    margin: 20px 0;
}

.main-box {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
}
</style>