<template>
    <a-card title="发帖提示"
        style="margin: 20px auto;box-shadow: 4px 8px 16px rgba(0, 0, 0, 0.2); border: 1px solid #d9d9d9;border-radius: 10px;">
        <p>
            <strong style="font-size: 16px;">欢迎您在社区模块发表博客！请注意以下事项：</strong>
        </p>
        <ul>
            <li><strong>请勿发布违规、违法的信息</strong></li><br />
            <li><strong>提交的博客将在24小时内由管理员审核</strong></li><br />
            <li><strong>审核通过后，您可以在“我的博客”页面查看具体的博客内容</strong></li><br />
            <li><strong>审核不通过时，您将收到具体的信息说明</strong></li><br />
        </ul>
        <a-form :form="form" @submit="handleSubmit">
            <a-form-item label="博客标题" :rules="[{ required: true, message: '请输入标题' }]">
                <a-input v-model="title" style="font-size: 16px;" />
            </a-form-item>

            <a-form-item label="博客内容" :rules="[{ required: true, message: '请输入内容' }]">
                <a-textarea v-model="content" :autosize="{ minRows: 10, maxRows: 10 }"
                    style="font-size: 16px; resize: none;" />
            </a-form-item>

            <a-form-item>
                <a-button @click="submit">发布</a-button>
            </a-form-item>
        </a-form>
    </a-card>
</template>

<script>
import axios from 'axios';
import { ADDRESS } from '@/services/api'

export default {
    data() {
        return {
            form: this.$form.createForm(this),
            title: '',
            content: '',
            user_id: null,
            date: null,
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
        submit() {
            this.user_id = localStorage.getItem('user_id');
            this.updateTime();
            console.log("发帖时间:", this.date)
            console.log('帖子标题:', this.title);
            console.log('帖子内容:', this.content);

            axios
                .post(ADDRESS + '/user_posts/post_blog', {}, {
                    params: {
                        user_id: this.user_id,
                        blog_title: this.title,
                        blog_content: this.content,
                        blog_time: this.date,
                    }
                })
                .then((response) => {
                    if (response.data.code === 0) {
                        console.log(response)
                        this.$message.success("发帖成功！");
                        this.title = "";
                        this.content = "";
                    }
                    else {
                        this.$message.info("发帖失败");
                    }
                })
        }
    }
};
</script>

<style scoped></style>