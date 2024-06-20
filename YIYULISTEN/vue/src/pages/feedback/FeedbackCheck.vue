<template>
    <div>
        <StandardTable :columns="columns" :dataSource="feedbackList" rowKey="formData.feedback_id">
            <div slot="status" slot-scope="{record}">
                <div class="progress-container">
                    <div v-if="record.result === null" class="progress-bar yellow">
                        <span class="status-text">处理中</span>
                    </div>
                    <div v-else-if="record.result != null" class="progress-bar green">
                        <span class="status-text">已回复</span>
                    </div>
                </div>
            </div>
            <div slot="action" slot-scope="{record}">
                <a-button v-if="record.result === null" @click="handleEdit(record)">回复</a-button>
                <a-button v-if="record.result != null" @click="handleEdit(record)">修改</a-button>
            </div>
        </StandardTable>
        <a-modal v-model="checkmodal" title="反馈回复" @ok="handleSave" @cancel="handleCancel">
            <a-form :form="formData">
                <a-form-item label="反馈内容（只读）">
                    <a-input v-model="formData.feedback" :read-only="true" />
                </a-form-item>

                <a-form-item label="反馈回复" :rules="[{ required: true, message: '请输入反馈回复' }]">
                    <a-textarea v-model="formData.result" :autosize="{ minRows: 4, maxRows: 8 }" />
                </a-form-item>
            </a-form>
        </a-modal>
    </div>
</template>
  
<script>
import StandardTable from '@/components/table/StandardTable'
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
    },
    data() {
        return {
            feedbackList: [],
            selectedStatus: 'all',
            date: null,
            columns: [
                {
                    title: '反馈内容',
                    dataIndex: 'feedback',
                    key: 'feedback',
                },
                {
                    title: '反馈时间',
                    dataIndex: 'time',
                    key: 'time',
                    scopedSlots: { customRender: 'time' },
                },
                {
                    title: '回复结果',
                    dataIndex: 'result',
                    key: 'result',
                    scopedSlots: { customRender: 'result' },
                },
                {
                    title: '回复时间',
                    dataIndex: 'result_time',
                    key: 'result_time',
                    scopedSlots: { customRender: 'result_time' },
                },
                {
                    title: '处理状态',
                    scopedSlots: { customRender: 'status' }
                },
                {
                    title: '操作',
                    scopedSlots: { customRender: 'action' }
                }
            ],
            checkmodal: false,
            form: this.$form.createForm(this),
            formData: {
                user_id: '',
                feedback_id: '',
                feedback: '',
                result: '',
                time: '',
            },
        };
    },
    created() {
        this.GetFeedbackList();
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
        GetFeedbackList() {
            axios
                .get(ADDRESS + `/UserHistory/FeedbackReplyList`, {
                    params: {

                    },
                })
                .then((response) => {
                    if (response.data.code === 0) {
                        console.log("获取列表信息成功！")
                        this.feedbackList = response.data.data.feedback_list;
                    } else {
                        console.log("获取列表信息失败！")
                    }
                })
                .catch((error) => {
                    console.error("获取列表信息出错！", error);
                });
        },
        handleEdit(record) {
            console.log(record);
            this.formData.user_id = record.user_id;
            this.formData.feedback_id = record.feedback_id;
            console.log("123123",record)
            console.log(record.feedback_id)
            console.log(this.formData.feedback_id)
            this.formData.feedback = record.feedback;
            this.formData.result = record.result;
            this.checkmodal = true;
        },
        handleSave() {
            this.updateTime();
            this.form.validateFields((err) => {
                console.log(this.formData.feedback_id)
                if (!err) {
                    axios
                        .post(ADDRESS + `/AdministratorAboutMusic/HandleFeedback`, {},{
                            params: {
                                user_id: this.formData.user_id,
                                feedback_id: this.formData.feedback_id,
                                content: this.formData.result,
                                time: this.date,
                            },
                        })
                        .then(response => {
                            if (response.data.code === 0) {
                                console.log('保存成功！');
                                this.$message.success("回复成功！");
                                this.GetFeedbackList();
                                this.form.resetFields();
                            } else {
                                console.log('保存失败！');
                                this.$message.fail("回复失败！");
                            }
                        })
                        .catch(error => {
                            console.log(this.formData.user_id)
                            console.log("leimu",this.formData.feedback_id)
                            console.error('保存出错！', error);

                        });
                    this.checkmodal = false;
                }
            });
        },
        handleCancel() {
            this.checkmodal = false;
            this.form.resetFields();
        },
    },
};
</script>
  
<style scoped>
.progress-container {
    display: flex;
    align-items: center;
}

.progress-bar {
    height: 20px;
    border-radius: 10px;
    display: flex;
    align-items: center;
    width: 150px;
}

.green {
    background-color: green;
    color: white;
}

.yellow {
    background-color: yellow;
    color: black;
}

.status-text {
    padding: 0 8px;
    font-size: 12px;
}
</style>
  