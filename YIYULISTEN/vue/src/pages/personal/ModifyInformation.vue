<template>
    <page-layout>
        <div>
            <img class="background-particles" src=https://gw.alipayobjects.com/zos/rmsportal/TVYTbAXWheQpRcWDaDMu.svg>
        </div>
        <a-card
            style="margin-bottom: 20px; box-shadow: 4px 8px 16px rgba(0, 0, 0, 0.2); border: 1px solid #d9d9d9; border-radius: 10px; width:80%; margin: 0 auto;">
            <div slot="title" class="custom-card">个人信息修改</div>
            <a-form :form="form" @submit="handleSubmit" :layout="formLayout">
                <a-form-item label="用户ID">
                    <a-input v-model="userInfo.user_id" disabled />
                </a-form-item>
                <a-form-item label="用户姓名">
                    <a-input v-model="userInfo.user_name" disabled />
                </a-form-item>
                <a-form-item label="用户昵称" :rules="[{ required: true, message: '请输入用户昵称' }]">
                    <a-input v-model="userInfo.user_nickname" />
                </a-form-item>
                <a-form-item label="用户性别" :rules="[{ required: true, message: '请选择用户性别' }]">
                    <a-radio-group v-model="userInfo.user_gender">
                        <a-radio value="男">男</a-radio>
                        <a-radio value="女">女</a-radio>
                    </a-radio-group>
                </a-form-item>

                <a-button style="margin-bottom:20px" @click="showChangePasswordModal">修改密码</a-button>
                <a-modal v-model="changePasswordModalVisible" title="修改密码" @ok="handleChangePassword"
                    @cancel="hideChangePasswordModal">
                    <a-form :form="passwordForm" :layout="formLayout">
                        <a-form-item label="旧密码" :rules="[{ required: true, message: '请输入旧密码' }]">
                            <a-input-password v-model="oldPassword" visibility-toggle />
                        </a-form-item>
                        <a-form-item label="新密码" :rules="[{ required: true, message: '请输入新密码' }]">
                            <a-input-password v-model="newPassword" visibility-toggle />
                        </a-form-item>
                        <a-form-item label="确认新密码" :rules="[{ required: true, message: '请确认新密码' }]">
                            <a-input-password v-model="confirmPassword" visibility-toggle />
                        </a-form-item>
                    </a-form>
                </a-modal>

                <a-form-item>
                    <a-button type="primary" html-type="submit">保存</a-button>
                </a-form-item>
            </a-form>
        </a-card>
    </page-layout>
</template>

<script>
import PageLayout from '@/layouts/PageLayout'
import axios from 'axios'
import { ADDRESS } from '@/services/api'

export default {
    components: { PageLayout },
    data() {
        return {
            form: this.$form.createForm(this),
            formLayout: 'vertical',
            userInfo: {
                user_id: '',
                user_name: '',
                user_nickname: '',
                user_gender: '',
            },
            changePasswordModalVisible: false,
            passwordForm: this.$form.createForm(this),
            oldPassword: '',
            newPassword: '',
            confirmPassword: '',
        };
    },
    created() {
        this.GetOldUserInfo();
    },
    mounted() {
    },
    methods: {
        GetOldUserInfo() {
            this.userInfo.user_id = localStorage.getItem('user_id');
            console.log(this.userInfo.user_id)

            axios
                .get(ADDRESS + `/user/get_personal_information`, {
                    params: {
                        user_id: this.userInfo.user_id,
                    }
                })
                .then((response) => {
                    if (response.data.code === 0) {
                        console.log("获取个人信息成功！")
                        this.userInfo.user_nickname = response.data.data.user_nickname;
                        this.userInfo.user_name = response.data.data.user_name;
                        this.userInfo.user_gender = response.data.data.user_gender;
                    }
                    else {
                        console.log("获取个人信息失败！")
                    }
                })
                .catch((error) => {
                    console.error("获取个人信息出错！", error);
                });
        },
        handleSubmit() {

            axios
                .post(ADDRESS + '/user/modify_personal_information', {}, {
                    params: {
                        user_id: this.userInfo.user_id,
                        user_nickname: this.userInfo.user_nickname,
                        user_gender: this.userInfo.user_gender,
                    }
                })
                .then((response) => {
                    if (response.data.code === 0) {
                        console.log(this.userInfo.user_id);
                        console.log(this.userInfo.user_nickname);
                        console.log(this.userInfo.user_gender);
                        console.log(this.userInfo.user_password);
                        console.log(response)
                        this.$message.success("个人资料修改成功！");
                    }
                    else {
                        this.$message.info("个人资料修改失败");
                    }
                })
        },
        showChangePasswordModal() {
            this.changePasswordModalVisible = true;
        },

        hideChangePasswordModal() {
            this.changePasswordModalVisible = false;
            this.passwordForm.resetFields();
        },

        /*handleChangePassword() {
            axios
                .get(ADDRESS + `/user/login`, {
                    params: {
                        user_id: this.userInfo.user_id,
                        user_password: this.oldPassword,
                    }
                })
                .then((response) => {
                    if (response.data.code === 0) {
                        console.log("验证旧密码成功！")
                        this.handleNewPassword();
                    }
                    else {
                        console.log("验证旧密码失败！")
                        this.$message.error('旧密码输入错误！')
                        return;
                    }
                })
                .catch((error) => {
                    console.error("验证旧密码出错！", error);
                });

            if (this.newPassword !== this.confirmPassword) {
                this.$message.error('新密码和确认密码不一致！');
                return;
            }
        },
        handleNewPassword(){
            axios
                .post(ADDRESS + `/user/modify_password`, {}, {
                    params: {
                        user_id: this.userInfo.user_id,
                        user_new_password: this.newPassword,
                    }
                })
                .then((response) => {
                    if (response.data.code === 0) {
                        console.log("修改新密码成功！")
                        this.$message.success('修改新密码成功！')
                    }
                    else {
                        console.log("修改新密码失败！")
                        this.$message.error('修改新密码失败！')
                        return;
                    }
                }),
                this.oldPassword = '',
                this.newPassword = '',
                this.confirmPassword = '',
                this.hideChangePasswordModal();
        },*/

        handleChangePassword() {
            if (this.newPassword !== this.confirmPassword) {
                this.$message.error('新密码和确认密码不一致！');
                return;
            }

            axios
                .post(ADDRESS + `/user/modify_password`, {}, {
                    params: {
                        user_id: this.userInfo.user_id,
                        user_password: this.oldPassword,
                        user_new_password: this.newPassword,
                    }
                })
                .then((response) => {
                    if (response.data.code === 0) {
                        console.log("修改新密码成功！")
                        this.$message.success('修改新密码成功！')
                        this.oldPassword = '',
                            this.newPassword = '',
                            this.confirmPassword = '',
                            this.hideChangePasswordModal();
                    }
                    else {
                        if (response.data.message === "旧密码验证错误") {
                            console.log("旧密码验证错误")
                            this.$message.error('旧密码输入错误！')
                        }
                        else {
                            console.log("修改新密码失败！")
                            this.$message.error('修改新密码失败！')
                        }
                        return;
                    }
                });




        },
    }

};
</script>

<style scoped>
.background-particles {
    position: absolute;
    width: 100%;
    height: 70vh;
    z-index: 0;
}

.custom-card {
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
</style>