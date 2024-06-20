<template>
    <div>
        <a-card style=" box-shadow: 4px 8px 16px rgba(0, 0, 0, 0.2); border: 1px solid #d9d9d9;border-radius: 10px;">
            <h2>
                <img src="@/assets/img/myblog.png" alt="我的博客"
                    style="width: 300px; border-radius: 10px; margin-left: 2%;">
            </h2><br />
            <a-row gutter="16">
                <a-col :span="12" v-for="blog in paginatedBlogs" :key="blog.blog_id">
                    <a-card class="myblogcard" @mouseover="handleMouseOver" @mouseleave="handleMouseLeave"
                        :style="{ backgroundImage: `url(${getRandomBackgroundImage()})`, backgroundSize: 'cover', backgroundPosition: 'center' }">
                        <div style="position: absolute; top: 10px; right: 10px;">
                            <a-button type="primary" @click="handleDelete(blog.blog_id)">删除帖子</a-button>
                        </div>
                        <div style="margin-bottom: 16px;">
                            <p style="font-size: 20px;"><strong>{{ blog.blog_title }}</strong> <router-link
                                    style="font-size:15px"
                                    :to="{ name: '博客详情', params: { blog_id: blog.blog_id, blog_title: blog.blog_title, blog_time: blog.blog_time, blog_usernickname: blog.user_nickname, blog_comment_num: blog.blog_comment_num, blog_collect_num: blog.blog_collect_num, blog_content: blog.blog_content } }">查看详情</router-link>
                            </p>
                            <p>{{ blog.blog_time }}</p>
                            <p>{{ blog.blog_content }}</p>
                            <div style="margin-top: auto; display: flex; align-items: center;">
                                <div style="margin-right: 10px;">
                                    <a-icon type="message" style="margin-right: 8px;"></a-icon>
                                    <span>{{ blog.blog_comment_num }}</span>
                                </div>
                                <div>
                                    <a-icon type="star" style="margin-right: 8px;"></a-icon>
                                    <span>{{ blog.blog_collect_num }}</span>
                                </div>
                            </div>
                        </div>
                    </a-card>
                </a-col>
            </a-row>
            <div>
                <el-dialog :visible.sync="deleteConfirmationModal" title="删除确认" width="30%"
                    @close="closeDeleteConfirmationModal">
                    <p>您确定要删除该帖子吗？</p>
                    <span slot="footer" class="dialog-footer">
                        <el-button @click="handleDeleteBlog">确定</el-button>
                        <el-button @click="cancelDelete">取消</el-button>
                    </span>
                </el-dialog>
            </div>
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
            click_blog_id: '',
            currentPage: 1,
            pageSize: 6,
            deleteConfirmationModal: false,
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
        handleDelete(blogId) {
            this.click_blog_id = blogId;
            this.deleteConfirmationModal = true;
        },
        cancelDelete() {
            this.deleteConfirmationModal = false;
        },
        handlePageChange(page) {
            this.currentPage = page;
        },
        handleDeleteBlog() {
            console.log("Delete button clicked!");
            this.user_id = localStorage.getItem('user_id');
            axios
                .post(ADDRESS + '/user_posts/delete', {}, {
                    params: {
                        user_id: this.user_id,
                        blog_id: this.click_blog_id,
                    }
                })
                .then((response) => {
                    if (response.data.code === 0) {
                        console.log(response)
                        this.$message.success("删除帖子成功！");
                        this.fetchUserBlogs();
                    }
                    else {
                        this.$message.info("删除帖子失败");
                    }
                })
            this.deleteConfirmationModal = false;
        },
        fetchUserBlogs() {
            this.user_id = localStorage.getItem('user_id');
            console.log(this.user_id)

            axios
                .get(ADDRESS + `/user_posts/get_personal_post`, {
                    params: {
                        user_id: this.user_id,
                    }
                })
                .then((response) => {
                    if (response.data.code === 0) {
                        console.log(response.data.data);
                        this.blogs = response.data.data.personal_post;
                        console.log("获取个人帖子成功！")
                    }
                    else {
                        console.log("获取个人帖子失败！")
                    }
                })
                .catch((error) => {
                    console.error("获取个人帖子出错！", error);
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
</style>