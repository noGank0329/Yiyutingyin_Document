<template>
    <div>
        <a-card>
            <h2 class="blog-title" style="color: #fff; background: #00aca1; padding: 10px;">博客详情</h2>


            <a-card style="background: #f0f0f0; height: 50px;">
                <p class="blog-detail-title" style="line-height: 5px;">标题: {{ detail_blog_title }}</p>
            </a-card>

            <a-card style="background: #ffffff;">
                <p class="blog-detail-meta">发帖人：{{ detail_blog_usernickname }}</p>
                <p class="blog-detail-meta">内容：{{ detail_blog_content }}</p>
                <p class="blog-detail-meta">发帖时间: {{ detail_blog_time }}</p>
            </a-card>
        </a-card>

        <a-card>
            <h2 class="blog-comment" style="color: #fff; background: #00aca1; padding: 10px;">评论</h2>

            <!-- 循环渲染评论组件 -->
            <a-comment v-for="comment in blog_comments" :key="comment.id" style="background-color: #e6fffb;height: 70%;">
                <template #author><a>{{ comment.user_nickname }}</a></template>
                <template #avatar>
                    <a-avatar :src="require('@/assets/img/avatar.png')" :alt="comment.author" />
                </template>
                <template #content>
                    <p>{{ comment.comment_content }}</p>
                </template>
                <template #datetime>
                    <a-tooltip :title="comment.comment_time">
                        <span>{{ comment.comment_time }}</span>
                    </a-tooltip>
                </template>
                <a-divider />
            </a-comment>


        </a-card>
        <div class="fixed-comment-box">
            <div class="comment-input-container">
                <a-input v-model="blog_input_comment" placeholder="输入评论..." />
                <div class="button-container">
                    <a-button type="primary" @click="submitComment">发表评论</a-button>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.blog-title {
    font-size: 24px;
    color: #fff;
    background: #333;
    padding: 10px;
    margin-bottom: 16px;
}

.blog-detail-title {
    font-size: 24px;
    font-weight: bold;
    /* 设置字体加粗 */
    color: #040f0e;
    /* 调整标题颜色 */
}

.blog-detail-meta {
    font-weight: bold;
    /* 设置字体加粗 */
    color: #888;
    /* 调整颜色 */
}

.blog-comment {
    font-size: 24px;
}

.fixed-comment-box {
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    background: #fff;
    padding: 10px;
    box-shadow: 0px -2px 6px rgba(0, 0, 0, 0.1);
    /* 添加阴影效果 */
}

.comment-input-container {
    max-width: 60%;
    /* 占据屏幕三分之二的宽度 */
    margin: 0 auto;
    /* 水平居中 */
    height: 80px;
    /* 设置高度，根据需要调整 */
}

.button-container {
    margin-left: 92%;
    /* 将按钮推到最右侧 */
}
</style>

  
<script>
import axios from 'axios'
import { ADDRESS } from '@/services/api'


export default {
    data() {
        return {
            blog: [],

            user_id: '',
            detail_blog_id: '',
            detail_blog_title: '',
            detail_blog_content: '',
            detail_blog_time: '',
            detail_blog_usernickname: '',

            blog_comments: [],

            blog_input_comment: '',
            blog_input_time: '',

        };
    },
    created() {
        this.fetchBlogId();

        this.fetchBlogComment();
    },
    methods: {
        fetchBlogId() {
            this.detail_blog_id = this.$route.params.blog_id || '';
            this.detail_blog_title = this.$route.params.blog_title || '';
            this.detail_blog_content = this.$route.params.blog_content || '';
            this.detail_blog_time = this.$route.params.blog_time || '';
            this.detail_blog_usernickname = this.$route.params.blog_usernickname || '';

            this.user_id = localStorage.getItem('user_id');
            console.log(this.user_id);
            console.log(this.detail_blog_id);
            // this.fetchPresentBlog();

            console.log("博客id:", this.detail_blog_id)
            console.log("博客标题:", this.detail_blog_title)
            console.log("博客内容:", this.detail_blog_content)
            console.log("博客发布者昵称:", this.detail_blog_usernickname)
            console.log("博客时间:", this.detail_blog_time)
        },

        fetchPresentBlog() {
            axios
                .get(ADDRESS + `/user_posts/get_post_detail`, {}, {
                    params: {
                        blog_id: this.detail_blog_id,
                    }
                })
                .then((response) => {
                    if (response.data.code === 0) {
                        console.log(response.data.data);
                        this.blog = response.data.data.detail;
                        console.log("获取帖子成功！")
                    }
                    else {
                        console.log("获取帖子失败！")
                    }
                })
                .catch((error) => {
                    console.error("获取帖子出错！", error);
                });
        },

        fetchBlogComment() {
            axios
                .get(ADDRESS + `/user_posts/get_post_comment`, {
                    params: {
                        blog_id: this.detail_blog_id,
                    }
                })
                .then((response) => {
                    if (response.data.code == 0) {
                        console.log(response.data.data);
                        this.blog_comments = response.data.data.all_comment;
                        console.log("帖子评论：", this.blog_comments)
                        console.log("获取帖子评论成功！")
                    }
                    else {
                        console.log(response.data.data);
                        console.log(response.data.code)
                        console.log("获取帖子评论失败！")
                    }
                })
                .catch((error) => {
                    console.error("获取帖子评论出错！", error);
                });

        },

        submitComment() {
            // 在这里执行点击按钮后的逻辑
            console.log('按钮被点击了！');
            console.log(this.blog_input_comment)
            this.blog_input_time = this.getCurrentTime()
            console.log(this.blog_input_time)
            if (this.blog_input_comment == "") {
                this.$message.info("评论不能为空");
            }
            else {
                this.sendBlogComment();
            }


        },
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
        sendBlogComment() {
            axios
                .post(ADDRESS + '/user_posts/comment', {}, {
                    params: {
                        blog_id: this.detail_blog_id,
                        user_id: this.user_id,
                        comment_content: this.blog_input_comment,
                        comment_time: this.blog_input_time,
                    }
                })
                .then((response) => {
                    console.log(response.data.code)
                    if (response.data.code === 0) {
                        console.log(response)
                        this.$message.success("评论成功！");
                        this.blog_input_comment = "";
                        this.blog_input_time = "";
                        this.fetchBlogComment();
                    }
                    else {
                        this.$message.info("评论失败");
                    }
                })
        }


    }
};
</script>
  
<style scoped></style>
  