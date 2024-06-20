<template>
    <page-layout>
        <vue-particles color="#409EFF" :particleOpacity="0.7" :particlesNumber="150" shapeType="circle"
            :particleSize="4" linesColor="#409EFF" :linesWidth="1" :lineLinked="true" :lineOpacity="0.4"
            :linesDistance="200" :moveSpeed="3" :hoverEffect="true" hoverMode="grab" :clickEffect="true"
            clickMode="push" class="background-particles">
        </vue-particles>
        <a-card class="tips"
            style="box-shadow: 4px 8px 16px rgba(0, 0, 0, 0.2); border: 1px solid #d9d9d9; border-radius: 10px; width:70%; margin: 20px auto;">
            <div>
                <a-card style="font-weight: bold;">
                    <p>欢迎您为益语听音测试治疗平台做出反馈或是提出宝贵的意见或建议！</p>
                    <p>若您在这里提交的反馈没有及时得到回复，请联系平台工作人员联系方式</p>
                    <p>联系方式：2151136@tongji.edu.cn</p>
                </a-card>
            </div>
        </a-card>
        <a-card class="feedback-form"
            style=" box-shadow: 4px 8px 16px rgba(0, 0, 0, 0.2); border: 1px solid #d9d9d9; border-radius: 10px; width:70%; margin: 20px auto;">
            <img src="@/assets/img/FeedbackForm.png" style="width:400px">
            <div>

                <br />
                <div class="form-group">
                    <label for="feedbackType">反馈类型:</label>
                    <select v-model="feedbackType" id="feedbackType">
                        <option style="font-weight: bold;" value="0">功能建议</option>
                        <option style="font-weight: bold;" value="1">投诉意见</option>
                        <option style="font-weight: bold;" value="2">其他反馈</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="feedbackContent">反馈内容:</label>
                    <textarea v-model="feedbackContent" id="feedbackContent" rows="10" maxlength="150"
                        placeholder="在这里输入您的反馈..."></textarea>
                </div>
                <div class="form-group">
                    <label for="contact">联系方式:</label>
                    <input v-model="contact" id="contact" type="text" placeholder="电子邮件、手机号等">
                </div>
                <button @click="submitFeedback">提交反馈</button>
            </div>
        </a-card>
    </page-layout>
</template>

<script>
import axios from 'axios'
import { ADDRESS } from '@/services/api'

export default {
    name: 'Feedback',
    data() {
        return {
            feedbackType: '0',
            feedbackContent: "",
            contact: "",
            user_id: null,
            date: null,
            form: this.$form.createForm(this),
        };
    },
    methods: {
        getCurrentTime() {
            const utcTime = new Date();
            const year = utcTime.getFullYear();
            const month = utcTime.getMonth() + 1;
            const day = utcTime.getDate();
            const hours = utcTime.getHours();
            const minutes = utcTime.getMinutes();
            const seconds = utcTime.getSeconds();
            return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
        },
        updateTime() {
            this.date = this.getCurrentTime()
        },
        submitFeedback() {
            this.user_id = localStorage.getItem('user_id');
            this.updateTime();
            console.log("反馈时间", this.date)
            console.log('反馈类型:', this.feedbackType);
            console.log('反馈内容:', this.feedbackContent);
            console.log('联系方式:', this.contact);

            axios
                .post(ADDRESS + '/MusicTestAndCure/Feedback', {}, {
                    params: {
                        user_id: this.user_id,
                        time: this.date,
                        feedback: this.feedbackContent,
                        feedback_type: this.feedbackType,
                    }
                })
                .then((response) => {
                    if (response.data.code === 0) {
                        console.log(response)
                        this.$message.success("反馈提交成功！");

                        this.feedbackType = '0';
                        this.feedbackContent = '';

                    }
                    else {
                        this.$message.info("反馈提交失败");
                    }
                })
        }
    }
};
</script>

<style scoped>
.background-particles {
    position: absolute;
    width: 100%;
    height: 115vh;
    z-index: 0;
}

.tips {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 8px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
}

.feedback-form {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 8px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.form-group {
    margin-bottom: 20px;
}

label {
    font-weight: bold;
    display: block;
    margin-bottom: 8px;
}

select,
textarea {
    width: 100%;
    margin-bottom: 10px;
    resize: none;
    border-radius: 8px;
    border: 1px solid #ccc;
    padding: 8px;
}

input {
    width: 100%;
    padding: 8px;
    margin-top: 4px;
    margin-bottom: 12px;
    border: 1px solid #ccc;
    border-radius: 8px;
    box-sizing: border-box;
}

.other-feedback-input {
    margin-top: 10px;
}

button {
    background-color: #1890ff;
    color: #fff;
    border: none;
    padding: 10px 15px;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
}
</style>