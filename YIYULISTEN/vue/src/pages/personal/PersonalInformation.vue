<template>
    <page-layout>
        <vue-particles color="#409EFF" :particleOpacity="0.7" :particlesNumber="150" shapeType="circle"
            :particleSize="4" linesColor="#409EFF" :linesWidth="1" :lineLinked="true" :lineOpacity="0.4"
            :linesDistance="200" :moveSpeed="3" :hoverEffect="true" hoverMode="grab" :clickEffect="true"
            clickMode="push" class="background-particles">
        </vue-particles>
        <div slot="headerContent" class="header-content"
            style="display: flex; align-items: center; justify-content: space-between;">
            <div class="title" style="width: 800px;">
                <img src="@/assets/img/welcome.png" style="width:600px">
                <img src="@/assets/img/note.png" style="width:100px">
            </div>
            <div class="cards" style="display: flex; ">
                <el-card class="card" shadow="hover"
                    style="width: 150px; text-align: center; color: black; margin-right: 30px; ">
                    <i class="el-icon-medal-1" style="font-size: 18px; color: orange;"></i>
                    <div>中文平台</div>
                </el-card>
                <el-card class="card" shadow="hover"
                    style="width: 150px; text-align: center; color: black; margin-right: 30px; ">
                    <i class="el-icon-money" style="font-size: 18px; color: greenyellow;"></i>
                    <div>公益性质</div>
                </el-card>
                <el-card class="card" shadow="hover"
                    style="width: 150px; text-align: center; color: black; margin-right: 30px; ">
                    <i class="el-icon-chat-dot-square" style="font-size: 18px; color: burlywood;"></i>
                    <div>24h客服在线</div>
                </el-card>
            </div>
        </div>
        <template>
            <a-row style="margin: 0 -12px">
                <a-col style="padding: 0 12px" :xl="16" :lg="24" :md="24" :sm="24" :xs="24">
                    <a-carousel autoplay
                        style="margin-bottom: 20px ; box-shadow: 4px 8px 16px rgba(0, 0, 0, 0.2); border: 1px solid #d9d9d9;">
                        <a-card style="text-align: center; background-color: #ffffff;">
                            <img alt="example" src="@/assets/img/mainhome.png"
                                style="max-width: 100%; max-height: 300px; margin: auto; display: block;">
                        </a-card>
                    </a-carousel>
                    <a-card class="custom-card"
                        style="margin-bottom: 20px; box-shadow: 4px 8px 16px rgba(0, 0, 0, 0.2); border: 1px solid #d9d9d9;">
                        <div slot="title" class="custom-card-title">简化导航</div>
                        <a href="#/list/test">
                            <a-card-grid
                                style="width: 31%; text-align: center; color: black; margin-right: 10px; font-weight: bold;">
                                <i class="el-icon-message-solid" style="font-size: 18px;"></i>
                                听力测试
                            </a-card-grid>
                        </a>
                        <a href="#/music/AllMusic">
                            <a-card-grid
                                style="width: 31%; text-align: center; color: black; margin-right: 10px; font-weight: bold;">
                                <i class="el-icon-first-aid-kit" style="font-size: 18px;"></i>
                                音乐治疗
                            </a-card-grid>
                        </a>
                        <a href="#/community/mainblog">
                            <a-card-grid
                                style="width: 31%; text-align: center; color: black; margin-right: 10px; font-weight: bold;">
                                <i class="el-icon-office-building" style="font-size: 18px;"></i>
                                社区论坛
                            </a-card-grid>
                        </a>
                        <a href="#/consult">
                            <a-card-grid
                                style="width: 31%; text-align: center; color: black; margin-right: 10px; font-weight: bold;">
                                <i class="el-icon-phone" style="font-size: 18px;"></i>
                                专业咨询
                            </a-card-grid>
                        </a>
                        <a href="#/feedback/feedbackform">
                            <a-card-grid
                                style="width: 31%; text-align: center; color: black; margin-right: 10px; font-weight: bold;">
                                <i class="el-icon-chat-line-square" style="font-size: 18px;"></i>
                                意见反馈
                            </a-card-grid>
                        </a>
                        <a href="#/personal/Introduction">
                            <a-card-grid
                                style="width: 31%; text-align: center; color: black; margin-right: 10px; font-weight: bold;">
                                <i class="el-icon-warning" style="font-size: 18px;"></i>
                                关于我们
                            </a-card-grid>
                        </a>
                    </a-card>
                    <a-card
                        style="height:525px; box-shadow: 4px 8px 16px rgba(0, 0, 0, 0.2); border: 1px solid #d9d9d9;">
                        <div slot="title" class="custom-card-title">系统资讯</div>
                        <el-select v-model="selectedType" placeholder="选择资讯类型" @change="filterMessages">
                            <el-option label="全部资讯" value="all" />
                            <el-option label="耳聋科普" value="0" />
                            <el-option label="康复教程" value="1" />
                            <el-option label="专家建议" value="2" />
                        </el-select>
                        <div style="height: 350px; overflow-y: auto;">
                            <el-card style="margin-top:50px;margin-bottom:5px" v-for="message in filteredMessages"
                                :key="message.id">
                                <div slot="header" class="news-header">{{ messageTypeMap[message.message_type] }}</div>
                                <p>标题: {{ message.message_title }}</p>
                                <p>内容: {{ message.message_content }}</p>
                                <p>发布时间: {{ message.message_time }}</p>
                            </el-card>
                        </div>
                    </a-card>
                </a-col>
                <a-col style="padding: 0 12px" :xl="8" :lg="24" :md="24" :sm="24" :xs="24">

                    <div>
                        <a-card :bordered="true"
                            style="width: 400px; margin-bottom: 20px; box-shadow: 4px 8px 16px rgba(0, 0, 0, 0.3); border: 1px solid #d9d9d9;">
                            <div slot="title" class="right-title">个人寄语
                                <i class="el-icon-edit" style="font-size: 18px; color: black;"></i>
                            </div>
                            <textarea v-model="announcement" :readonly="!isEditing" rows="5"
                                style="width: 100%;"></textarea>
                            <button @click="toggleEditMode">{{ isEditing ? '保存' : '修改' }}</button>
                        </a-card>
                    </div>


                    <a-card :bordered="false"
                        style="width: 400px;height:550px;margin-bottom:20px;box-shadow: 4px 8px 16px rgba(0, 0, 0, 0.3); border: 1px solid #d9d9d9;">
                        <div slot="title" class="right-title">日历
                            <i class="el-icon-date" style="font-size: 18px; color: black;"></i>
                        </div>
                        <calendar showPrepNext startYearMonth='2024-6' :markDate="markDate" :checkedDate='checkedDate'
                            @clickDate="clickDate" @changeMonth="changeMonth" />
                    </a-card>

                    <a-card
                        style="width: 400px; box-shadow: 4px 8px 16px rgba(0, 0, 0, 0.4); border: 1px solid #d9d9d9;">
                        <div slot="title" class="right-title">用户资料
                            <i class="el-icon-user-solid" style="font-size: 18px; color: black;"></i>
                        </div>
                        <template #extra><a href="#/personal/modifyinformation">修改资料</a></template>
                        <p>UID：{{ user_id }}</p>
                        <p>昵称：{{ user_nickname }}</p>
                        <p>姓名：{{ user_name }}</p>
                        <p>性别：{{ user_gender }}</p>
                    </a-card>

                </a-col>
            </a-row>
        </template>
    </page-layout>
</template>

<script>
import PageLayout from '@/layouts/PageLayout'
import { mapState } from 'vuex'
import { request, METHOD } from '@/utils/request'
import calendar from '@/components/calendar/calendar'
import axios from 'axios'
import { ADDRESS } from '@/services/api'

export default {
    // name: 'PersonalInformation',
    components: { PageLayout, calendar },
    i18n: require('./i18n'),
    data() {
        return {
            loading: true,
            welcome: {
                timeFix: '',
                message: ''
            },
            announcement: this.retrieveData() || '记录每一天！',
            originalAnnouncement: '',
            isEditing: false,
            user_id: '',
            user_gender: '',
            user_nickname: '',
            user_name: '',
            selectedType: 'all',
            messages: [],
            messageTypeMap: {
                0: '耳聋科普',
                1: '康复教程',
                2: '专家建议',
            },
        }
    },
    computed: {
        ...mapState('account', { currUser: 'user' }),
        ...mapState('setting', ['lang']),
        filteredMessages() {
            if (this.selectedType === 'all') {
                return this.messages;
            } else {
                return this.messages.filter(message => message.message_type.toString() === this.selectedType);
            }
        },
    },
    created() {
        this.showParticles = true;
        request('/user/welcome', METHOD.GET).then(res => this.welcome = res.data)
        request('/work/activity', METHOD.GET).then(res => this.activities = res.data)
        request('/work/team', METHOD.GET).then(res => this.teams = res.data)
        request('/project', METHOD.GET).then(res => {
            this.projects = res.data
            this.loading = false
        })
        this.GetOldUserInfo()
        this.GetAllNews()
        console.log("泪目")
    },
    methods: {
        toggleEditMode() {
            if (this.isEditing) {
                this.originalAnnouncement = this.announcement;
                this.isEditing = false;
            } else {
                this.isEditing = true;
            }
        },
        saveData() {
            localStorage.setItem('userAnnouncement', JSON.stringify(this.announcement));
        },
        retrieveData() {
            const savedData = localStorage.getItem('userAnnouncement');
            return savedData ? JSON.parse(savedData) : null;
        },
        GetOldUserInfo() {
            this.user_id = localStorage.getItem('user_id');
            console.log(this.user_id)

            axios
                .get(ADDRESS + `/user/get_personal_information`, {
                    params: {
                        user_id: this.user_id,
                    }
                })
                .then((response) => {
                    if (response.data.code === 0) {
                        console.log("获取个人信息成功！")
                        this.user_nickname = response.data.data.user_nickname;
                        this.user_name = response.data.data.user_name;
                        this.user_gender = response.data.data.user_gender;
                    }
                    else {
                        console.log("获取个人信息失败！")
                    }
                })
                .catch((error) => {
                    console.error("获取个人信息出错！", error);
                });
        },
        GetAllNews() {
            axios
                .get(ADDRESS + `/admin_user_news/get_all_news`, {}, {
                })
                .then((response) => {
                    if (response.data.code === 0) {
                        console.log("获取资讯信息成功！")
                        this.messages = response.data.data.message_list;
                    }
                    else {
                        console.log("获取资讯信息失败！")
                    }
                })
                .catch((error) => {
                    console.error("获取资讯信息出错！", error);
                });
        }
    },
    mounted() {
        window.addEventListener('beforeunload', this.saveData);
        this.showParticles = true;
    },
    beforeDestroy() {
        window.removeEventListener('beforeunload', this.saveData);
    },
    clickDate(date) {
        console.log(date)
    },
    changeMonth(date) {
        console.log(date)
    }
}
</script>

<style lang="less" scoped>
@import "index";

.progressa {
    padding: 5px 0;
    position: relative;
    width: 100%;

    .wrap {
        background-color: @layout-bg-color;
        position: relative;
    }

    .progress {
        transition: all 0.4s cubic-bezier(0.08, 0.82, 0.17, 1) 0s;
        border-radius: 1px 0 0 1px;
        background-color: #13C2C2;
        width: 0;
        height: 100%;
    }

    .target {
        position: absolute;
        top: 0;
        bottom: 0;

        span {
            border-radius: 100px;
            position: absolute;
            top: 0;
            left: 0;
            height: 4px;
            width: 2px;
        }

        span:last-child {
            top: auto;
            bottom: 0;
        }
    }
}

.personal-header {
    position: relative;
    overflow: hidden;
    width: 100%;
}

.personal-header .meta {
    position: relative;
    overflow: hidden;
    width: 100%;
    color: @text-color-second;
    font-size: 14px;
    line-height: 22px;
}

.personal-action {
    cursor: pointer;
    position: absolute;
    top: 0;
    right: 0;
}

.total {
    overflow: hidden;
    text-overflow: ellipsis;
    word-break: break-all;
    white-space: nowrap;
    margin-top: 4px;
    margin-bottom: 0;
    font-size: 30px;
    line-height: 38px;
    height: 38px;
}

.personal-footer {
    border-top: 1px solid @border-color-base;
    padding-top: 9px;
    margin-top: 8px;
}

.personal-content {
    margin-bottom: 12px;
    position: relative;
    height: 46px;
    width: 100%;
}

.icon {
    font-size: 72px;
    line-height: 72px;
    margin-bottom: 24px;
}

.success {
    color: @success-color;
}

.error {
    color: @error-color;
}

.title {
    font-size: 24px;
    color: @title-color;
    font-weight: 500;
    line-height: 32px;
    margin-bottom: 16px;
}

.desc {
    font-size: 14px;
    line-height: 22px;
    color: @text-color-second;
    margin-bottom: 24px;
}

.content {
    background-color: @background-color-light;
    padding: 24px 40px;
    border-radius: 2px;
    text-align: left;
}

.action {
    margin-top: 32px;
}

.personal-header1 {
    position: relative;
    overflow: hidden;
    width: 100%;
}

.personal-header1 .meta {
    position: relative;
    overflow: hidden;
    width: 100%;
    color: @text-color-second;
    font-size: 14px;
    line-height: 22px;
}

.personal-header2 {
    position: relative;
    overflow: hidden;
    width: 100%;
}

.personal-header2 .meta {
    position: relative;
    overflow: hidden;
    width: 100%;
    color: @text-color-second;
    font-size: 14px;
    line-height: 22px;
}

.personal-header3 {
    position: relative;
    overflow: hidden;
    width: 100%;
}

.personal-header3 .meta {
    position: relative;
    overflow: hidden;
    width: 100%;
    color: @text-color-second;
    font-size: 14px;
    line-height: 22px;
}

.personal-header4 {
    position: relative;
    overflow: hidden;
    width: 100%;
}

.personal-header4 .meta {
    position: relative;
    overflow: hidden;
    width: 100%;
    color: @text-color-second;
    font-size: 14px;
    line-height: 22px;
}

.personal-header5 {
    position: relative;
    overflow: hidden;
    width: 100%;
}

.personal-header5 .meta {
    position: relative;
    overflow: hidden;
    width: 100%;
    color: @text-color-second;
    font-size: 14px;
    line-height: 22px;
}

.background-particles {
    position: absolute;
    width: 100%;
    height: 134vh;
    z-index: 0;
}

.custom-card-title {
    background-color: rgb(8, 67, 67);
    /* 深颜色背景 */
    color: #fff;
    /* 白色文字 */
    font-family: "黑体", Arial, sans-serif;
    /* 黑体字体 */
    font-weight: bold;
    /* 加粗 */
    font-size: 20px;
    font-style: italic;
    /* 倾斜 */
    padding: 18px 30px;
    /* 调整标题内边距 */
    border-radius: 4px 4px 0 0;
    /* 可选：如果需要圆角效果 */
}

.title img {
    -webkit-user-select: none;
    /* 禁用文本选择 */
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    pointer-events: none;
    /* 禁用鼠标事件 */
}

.news-header {
    font-weight: bold;
    /* 加粗 */
    font-family: "微软雅黑", sans-serif;
    /* 微软雅黑字体 */
    background-color: #add8e6;
    /* 浅蓝色背景 */
    padding: 20px;
    /* 添加内边距以确保背景色覆盖内边距 */
    width: 100%;
    /* 确保背景色覆盖整个宽度 */
    box-sizing: border-box;
    /* 包含内边距和边框在元素的总宽度和高度中 */
}

.right-title {
    font-weight: bold;
    /* 加粗 */
    font-family: "黑体", sans-serif;
}
</style>
