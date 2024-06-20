<template>
    <div>
        <div>
            <img class="background-paphis" src=https://gw.alipayobjects.com/zos/rmsportal/TVYTbAXWheQpRcWDaDMu.svg>
        </div>
        <a-card style=" box-shadow: 4px 8px 16px rgba(0, 0, 0, 0.2); border: 1px solid #d9d9d9;border-radius: 10px;">
            <h2>
                <img src="@/assets/img/mainblog.png" alt="博客主页"
                    style="width: 300px; border-radius: 10px; margin-left: 2%;">
            </h2><br />
            <a-row gutter="16">

                <a-col :span="12" v-for="blog in paginatedBlogs" :key="blog.blog_id">
                    <a-card class="myblogcard" @mouseover="handleMouseOver" @mouseleave="handleMouseLeave"
                        :style="{ backgroundImage: `url(${getRandomBackgroundImage()})`, backgroundSize: 'cover', backgroundPosition: 'center' }">
                        <div
                            style="margin-bottom: 16px; display: flex; flex-direction: column; justify-content: space-between; height: 100%;">
                            <div>
                                <p style="font-size: 20px;"><strong>{{ blog.blog_title }}</strong> <router-link
                                        style="font-size:15px"
                                        :to="{ name: '博客详情', params: { blog_id: blog.blog_id, blog_title: blog.blog_title, blog_time: blog.blog_time, blog_usernickname: blog.user_nickname, blog_comment_num: blog.blog_comment_num, blog_collect_num: blog.blog_collect_num, blog_content: blog.blog_content } }">查看详情</router-link>
                                </p>
                                <p style="font-size: 15px;"><strong>{{ blog.user_nickname }}</strong></p>
                                <p>{{ blog.blog_time }}</p>
                                <p>{{ blog.blog_content }}</p>
                            </div>
                            <div style="margin-top: auto; display: flex; align-items: center;">
                                <div style="margin-right: 10px;">
                                    <a-icon type="message" style="margin-right: 8px;"></a-icon>
                                    <span>{{ blog.blog_comment_num }}</span>
                                </div>
                                <div>
                                    <a-icon type="star" style="margin-right: 8px;"
                                        @click="handleStarClick(blog.blog_id)"></a-icon>
                                    <span>{{ blog.blog_collect_num }}</span>
                                </div>
                            </div>
                        </div>
                    </a-card>
                </a-col>
            </a-row>

            <a-pagination :current="currentPage" :total="blogs.length" :pageSize="pageSize" @change="handlePageChange"
                style="margin-top: 16px; text-align: left" />
            <div style="text-align: center; ">
                共 {{ blogs.length }} 条记录
            </div>
        </a-card>
    </div>
</template>

<script>
import axios from 'axios'
import { ADDRESS } from '@/services/api'

export default {
    data() {
        return {
            blogs: [],
            user_id: '',
            currentPage: 1,
            pageSize: 6,
            clickBlogId: '',
            backgroundImages: [
                require('@/assets/img/blogbk1.png'),
                require('@/assets/img/blogbk2.png'),
                require('@/assets/img/blogbk3.png'),
                require('@/assets/img/blogbk4.png'),
                require('@/assets/img/blogbk5.png'),
                require('@/assets/img/blogbk6.png'),
                require('@/assets/img/blogbk7.png'),
            ]
        };
    },

    created() {
        this.fetchUserBlogs();
    },
    computed: {
        paginatedBlogs() {
            const startIndex = (this.currentPage - 1) * this.pageSize;
            const endIndex = startIndex + this.pageSize;
            return this.blogs.slice(startIndex, endIndex);
        },
    },
    methods: {
        handleStarClick(blogId) {
            this.clickBlogId = blogId;
            console.log(this.clickBlogId);
            console.log('Star clicked! Call your collect function here.');
            this.user_id = localStorage.getItem('user_id');
            console.log(this.user_id);
            axios
                .post(ADDRESS + '/user_posts/collection', {}, {
                    params: {
                        user_id: this.user_id,
                        blog_id: this.clickBlogId,
                    }
                })
                .then((response) => {
                    if (response.data.code === 0) {
                        console.log(response)
                        this.$message.success("收藏成功！");
                        this.updateBlogCollectNum(blogId);
                    }
                    else {
                        this.$message.info("收藏失败");
                    }
                })
        },
        updateBlogCollectNum(blogId) {
            const blogToUpdate = this.blogs.find(blog => blog.blog_id === blogId);
            if (blogToUpdate) {
                blogToUpdate.blog_collect_num += 1;
            }
        },
        handlePageChange(page) {
            this.currentPage = page;
        },
        fetchUserBlogs() {
            this.user_id = localStorage.getItem('user_id');
            console.log(this.user_id)

            axios
                .get(ADDRESS + `/user_posts/get_all_post`, {
                })
                .then((response) => {
                    if (response.data.code === 0) {
                        console.log(response.data.data);
                        this.blogs = response.data.data.all_post;
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
        handleMouseOver(e) {
            e.currentTarget.style.transform = 'scale(1.05)';
            e.currentTarget.style.borderWidth = '2px'; // 设置边框宽度变粗
        },
        handleMouseLeave(e) {
            e.currentTarget.style.transform = 'scale(1)';
            e.currentTarget.style.borderWidth = '1px'; // 恢复边框宽度
        },
        getRandomBackgroundImage() {
            // 随机选择一张背景图片
            const randomIndex = Math.floor(Math.random() * this.backgroundImages.length);
            return this.backgroundImages[randomIndex];
        }
    }
};
</script>

<style scoped>
.myblogcard {
    margin-bottom: 40px;
    border-radius: 8px;
    transition: transform 0.3s, border-width 0.3s;
    width: 100%;
    height: 300px;
    border: 1px solid #e8e8e8;
}

.myblogcard:hover {
    border-width: 2px;
}

.background-paphis {
    position: absolute;
    width: 100%;
    height: 60vh;
    z-index: 0;
}
</style>