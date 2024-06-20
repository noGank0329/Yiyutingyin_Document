<template>
    <page-layout>
        <div>
            <a-button style="margin-top:10px;margin-bottom:20px" type="primary" @click="showaddModal">新增</a-button>
            <standard-table :columns="columns" :dataSource="newsList">

                <div slot="action" slot-scope="{record}">
                    <a-button @click="handleEdit(record)">修改</a-button>
                    <span style="margin-right: 20px;"></span>
                    <a-button @click="handleDelete(record)">删除</a-button>
                </div>

            </standard-table>
            <a-modal v-model="addmodal" title="新增资讯" @ok="handleaddSave" @cancel="handleaddCancel">
                <a-form :form="form">
                    <a-form-item label="资讯标题" :rules="[{ required: true, message: '请输入资讯标题' }]">
                        <a-input v-model="formData.message_title" />
                    </a-form-item>
                    <a-form-item label="资讯类型" :rules="[{ required: true, message: '请选择资讯类型' }]">
                        <a-select v-model="formData.message_type">
                            <a-select-option value="0">耳聋科普</a-select-option>
                            <a-select-option value="1">康复教程</a-select-option>
                            <a-select-option value="2">专家建议</a-select-option>
                        </a-select>
                    </a-form-item>
                    <a-form-item label="资讯内容" :rules="[{ required: true, message: '请输入资讯内容' }]">
                        <a-input v-model="formData.message_content" />
                    </a-form-item>
                </a-form>
            </a-modal>

            <a-modal v-model="editmodal" title="修改资讯" @ok="handleeditSave" @cancel="handleeditCancel">
                <a-form :form="form">
                    <a-form-item label="资讯标题" :rules="[{ required: true, message: '请输入资讯标题' }]">
                        <a-input v-model="editformData.message_title" />
                    </a-form-item>
                    <a-form-item label="资讯类型" :rules="[{ required: true, message: '请选择资讯类型' }]">
                        <a-select v-model="editformData.message_type">
                            <a-select-option value="0">耳聋科普</a-select-option>
                            <a-select-option value="1">康复教程</a-select-option>
                            <a-select-option value="2">专家建议</a-select-option>
                        </a-select>
                    </a-form-item>
                    <a-form-item label="资讯内容" :rules="[{ required: true, message: '请输入资讯内容' }]">
                        <a-input v-model="editformData.message_content" />
                    </a-form-item>
                </a-form>
            </a-modal>

            <div>
                <el-dialog :visible.sync="deleteConfirmationModal" title="删除确认" width="30%"
                    @close="closeDeleteConfirmationModal">
                    <p>您确定要删除这条资讯吗？</p>
                    <span slot="footer" class="dialog-footer">
                        <el-button @click="confirmDelete">确定</el-button>
                        <el-button @click="cancelDelete">取消</el-button>
                    </span>
                </el-dialog>
            </div>
        </div>
    </page-layout>
</template>
  
<script>
import StandardTable from '@/components/table/StandardTable'
import PageLayout from '@/layouts/PageLayout'
import { Modal, Form, Input } from 'ant-design-vue';
import axios from 'axios';
import { ADDRESS } from '@/services/api'

export default {
    components: {
        'a-modal': Modal,
        'a-form': Form,
        'a-input': Input,
        'a-form-item': Form.Item,
        StandardTable,
        PageLayout
    },
    data() {
        return {
            newsList: [],
            columns: [
                {
                    title: '资讯标题',
                    dataIndex: 'message_title',
                    key: 'message_title',
                },
                {
                    title: '资讯内容',
                    dataIndex: 'message_content',
                    key: 'message_content',
                },
                {
                    title: '资讯类型',
                    dataIndex: 'message_type',
                    key: 'message_type',
                },
                {
                    title: '资讯发布时间',
                    dataIndex: 'message_time',
                    key: 'message_time',
                },
                {
                    title: '操作',
                    scopedSlots: { customRender: 'action' }
                }
            ],
            addmodal: false,
            editmodal: false,
            deleteConfirmationModal: false,
            deleteRecord: null,
            form: this.$form.createForm(this),
            formData: {
                message_title: '',
                message_type: '',
                message_content: '',
            },
            editformData: {
                message_id: '',
                message_title: '',
                message_type: '',
                message_content: '',
            },
            date: null,
        };
    },
    created() {
        this.GetNewsList();
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
        GetNewsList() {
            axios
                .get(ADDRESS + `/admin_user_news/get_all_news`, {
                    params: {
                    },
                })
                .then((response) => {
                    if (response.data.code === 0) {
                        console.log("获取资讯信息成功！")
                        this.newsList = response.data.data.message_list;
                        console.log(response.data.data.message_list)
                    } else {
                        console.log("获取资讯信息失败！")
                    }
                })
                .catch((error) => {
                    console.error("获取资讯信息出错！", error);
                });
        },
        showaddModal() {
            this.addmodal = true;
        },
        handleaddSave() {
            this.updateTime();
            this.form.validateFields((err) => {
                if (!err) {
                    axios
                        .post(ADDRESS + `/admin_news/add_news`, {},{
                            params: {
                                message_title: this.formData.message_title,
                                message_content: this.formData.message_content,
                                message_type: this.formData.message_type,
                                message_time: this.date,
                            },
                        })
                        .then(response => {
                            if (response.data.code === 0) {
                                console.log('保存成功！');
                                this.$message.success("新增成功！");
                                this.GetNewsList();
                                this.form.resetFields();
                                this.formData.message_title='';
                                this.formData.message_content='';
                                this.formData.message_type='';
                                this.date='';

                            } else {
                                console.log('保存失败！');
                                this.$message.fail("新增失败！");
                            }
                        })
                        .catch(error => {
                            console.error('保存出错！', error);
                        });
                    this.addmodal = false;
                }
            });
        },
        handleaddCancel() {
            this.addmodal = false;
            this.form.resetFields();
        },
        handleEdit(record) {
            console.log(record);
            this.editformData.message_id = record.message_id;
            this.editformData.message_type = record.message_type;
            this.editformData.message_content = record.message_content;
            this.editformData.message_title = record.message_title;
            // 打开编辑弹窗
            this.editmodal = true;
        },
        handleeditSave() {
            this.form.validateFields((err) => {
                if (!err) {
                    axios
                        .post(ADDRESS + '/admin_news/modify_news', {}, {
                            params: {
                                message_id: this.editformData.message_id,
                                message_type: this.editformData.message_type,
                                message_content: this.editformData.message_content,
                                message_title: this.editformData.message_title,
                            }
                        })
                        .then((response) => {
                            if (response.data.code === 0) {
                                this.$message.success('修改成功！');
                                console.log("修改id：",this.editformData.message_id)
                                this.GetNewsList();
                                this.editmodal = false;
                            } else {
                                this.$message.error('修改失败！');
                            }
                        })
                        .catch((error) => {
                            console.error('修改资讯出错！', error);
                            this.$message.error('修改出错！');
                        });
                } else {
                    // 表单验证失败，不执行保存逻辑
                }
            });
        },
        handleeditCancel() {
            this.editmodal = false;
            this.form.resetFields();
        },
        handleDelete(record) {
            this.deleteRecord = record;
            this.deleteConfirmationModal = true;
        },
        confirmDelete() {
            axios
                .post(ADDRESS + '/admin_news/delete_news', {}, {
                    params: {
                        message_id: this.deleteRecord.message_id
                    }
                })
                .then((response) => {
                    if (response.data.code === 0) {
                        this.$message.success('删除成功！');
                        this.GetNewsList();
                        this.deleteConfirmationModal = false;
                    } else {
                        this.$message.error('删除失败！');
                    }
                })
                .catch((error) => {
                    console.error('删除出错！', error);
                    this.$message.error('删除出错！');
                });

        },

        cancelDelete() {
            this.deleteConfirmationModal = false;
        },
    },
};
</script>
  
<style scoped></style>
  