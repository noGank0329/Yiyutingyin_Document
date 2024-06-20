<template>
    <div>
        <a-card>
            <h2>博客审核</h2><br />
            <a-row gutter="16">
                <a-col :span="12" v-for="blog in paginatedBlogs" :key="blog.blog_id">
                    <a-card class="myblogcard" @mouseover="handleMouseOver" @mouseleave="handleMouseLeave">
                        <div
                            style="margin-bottom: 16px; display: flex; flex-direction: column; justify-content: space-between; height: 100%;">
                            <div style="position: absolute; top: 10px; right: 10px;">
                                <a-button type="primary" @click="handleCheck(blog.blog_id)">审核博客</a-button>
                            </div>
                            <div>
                                <p style="font-size: 20px;"><strong>{{ blog.blog_title }}</strong></p>
                                <p style="font-size: 15px;"><strong>{{ blog.user_nickname }}</strong></p>
                                <p>{{ blog.blog_time }}</p>
                                <p>{{ blog.blog_content }}</p>
                            </div>
                        </div>
                    </a-card>
                </a-col>
            </a-row>
            <div>
                <el-dialog :visible.sync="checkConfirmationModal" title="删除确认" width="30%"
                    @close="closeDeleteConfirmationModal">
                    <p>该帖子是否通过审核</p>
                    <span slot="footer" class="dialog-footer">
                        <el-button @click="CheckPass">通过</el-button>
                        <el-button @click="CheckUnpass">不通过</el-button>
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
            currentPage: 1,
            pageSize: 6,
            clickBlogId: '',
            check_id: '',
            check_status: '',
            checkConfirmationModal: false,
        };
    },
    created() {
        this.fetchCheckBlogs();
    },
    computed: {
        paginatedBlogs() {
            const startIndex = (this.currentPage - 1) * this.pageSize;
            const endIndex = startIndex + this.pageSize;
            return this.blogs.slice(startIndex, endIndex);
        },
    },
    methods: {
        CheckPass() {
            this.check_status = 1;
            this.Check();
        },
        CheckUnpass() {
            this.check_status = 2;
            this.Check();
        },
        handleCheck(blogId) {
            this.check_id = blogId;
            this.checkConfirmationModal = true;
        },
        cancelCheck() {
            this.checkConfirmationModal = false;
        },
        Check() {
            axios
                .post(ADDRESS + `/admin_posts/check_posts`,{}, {
                    params: {
                        blog_id: this.check_id,
                        blog_status: this.check_status,
                    }
                })
                .then((response) => {
                    if (response.data.code === 0) {
                        // console.log("cnm",this.check_id);
                        // console.log(this.check_status);
                        this.$message.success("审核操作成功！");
                        this.fetchCheckBlogs();
                    }
                    else {
                        this.$message.info("审核操作失败");
                    }
                    this.checkConfirmationModal = false;
                })

        },
        handlePageChange(page) {
            this.currentPage = page;
        },
        fetchCheckBlogs() {
            this.user_id = localStorage.getItem('user_id');
            console.log(this.user_id);

            axios
                .get(ADDRESS + `/admin_posts/get_unchecked_posts`, {
                })
                .then((response) => {
                    if (response.data.code === 0) {
                        console.log(response.data.data);
                        this.blogs = response.data.data.unchecked_posts;
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
        updateBlogCollectNum(blogId) {
            const blogToUpdate = this.blogs.find(blog => blog.blog_id === blogId);
            if (blogToUpdate) {
                blogToUpdate.blog_collect_num += 1;
            }
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
  