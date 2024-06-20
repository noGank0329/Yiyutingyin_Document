<template>
    <div class="progress-container">
        <!-- 根据状态显示不同颜色的进度条 -->
        <div v-if="status === 1" class="progress-bar green">
            <span class="status-text">已回复</span>
        </div>
        <div v-else-if="status === 0" class="progress-bar yellow">
            <span class="status-text">处理中</span>
        </div>
        <!-- 如果需要处理其他状态，可以继续添加 v-else-if 分支 -->
    </div>
</template>
  
<script>
export default {
    props: {
        status: {
            type: Number,
            required: true
        }
    }
};
</script>
  
<style>
/* 样式可以根据需要进行调整 */
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
  