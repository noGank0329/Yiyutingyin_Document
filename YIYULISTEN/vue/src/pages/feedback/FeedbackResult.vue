<template>
    <div
        style="margin: 20px auto;box-shadow: 4px 8px 16px rgba(0, 0, 0, 0.2); border: 1px solid #d9d9d9;border-radius: 10px;">
        <a-table :columns="columns" :dataSource="feedbackList" rowKey="feedback_id">
            <template v-slot:processing_status="text">
                <div class="progress-container">
                    <div v-if="text === null" class="progress-bar yellow">
                        <span class="status-text">处理中</span>
                    </div>
                    <div v-else-if="text != null" class="progress-bar green">
                        <span class="status-text">已回复</span>
                    </div>
                </div>
            </template>
        </a-table>
    </div>
</template>

<script>
import { Table } from 'ant-design-vue';
import axios from 'axios';
import { ADDRESS } from '@/services/api'

export default {
    components: {
        'a-table': Table,
    },
    data() {
        return {
            feedbackList: [],
            selectedStatus: 'all',
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
                    dataIndex: 'result',
                    key: 'result',
                    scopedSlots: { customRender: 'processing_status' },
                },
            ],
        };
    },
    created() {
        this.GetFeedbackList();
    },
    methods: {
        GetFeedbackList() {
            axios
                .get(ADDRESS + `/UserHistory/FeedbackList`, {
                    params: {
                        user_id: localStorage.getItem('user_id'),
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
        }
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