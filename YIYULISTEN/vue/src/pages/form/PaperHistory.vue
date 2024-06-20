<template>
  <page-layout>
    <div>
      <img class="background-paphis" src=https://gw.alipayobjects.com/zos/rmsportal/TVYTbAXWheQpRcWDaDMu.svg>
    </div>
    <a-card :bordered="false"
      style="width: 90%; margin:0 auto; margin-bottom: 20px; box-shadow: 4px 8px 16px rgba(0, 0, 0, 0.2); border: 1px solid #d9d9d9;border-radius: 10px;">


      <!-- <a-divider style="margin-bottom: 32px"/> -->

      <img src="@/assets/img/paperHistory.png" alt="问卷图片" style="width: 300px; border-radius: 10px; margin-left: 2%;">
      <a-table style="margin-bottom: 24px" :columns="historyColumns" :dataSource="historyData" :pagination="false">
      </a-table>
    </a-card>
  </page-layout>
</template>

<script>
// import DetailList from '../../components/tool/DetailList'
// import PageLayout from '../../layouts/PageLayout'
import axios from 'axios';
import { ADDRESS } from '@/services/api'


const historyColumns = [
  {
    title: '时间',
    dataIndex: 'date',
    key: 'date',
    align: 'center'
  },
  {
    title: '部位',
    dataIndex: 'part',
    key: 'part',
    align: 'center'

  },
  {
    title: '是否有明显烦躁情绪',
    dataIndex: 'mood',
    key: 'mood',
    align: 'center'
  },
  {
    title: '是否有明显的听力困难',
    dataIndex: 'listen',
    key: 'listen',
    align: 'center'
  },
  {
    title: '是否有明显的语言表达困难？',
    dataIndex: 'speak',
    key: 'speak',
    align: 'center'
  },
  {
    title: '听力障碍程度主观打分',
    dataIndex: 'score',
    key: 'score',
    align: 'center'
  }
]



export default {
  name: 'BasicDetail',
  // components: {PageLayout},
  data() {
    return {
      historyColumns,

      historyData: [
        {
          date: '',
          part: '',
          mood: '',
          listen: '',
          speak: '',
          score: '',
          question: '',
        },
        // 添加更多历史记录项...
      ],


      score: null,
      date: null,
      question: "",

      user_id: null

    }
  },

  mounted() {
    // 在组件挂载后异步获取数据
    this.user_id = localStorage.getItem('user_id');
    console.log("用户查看历史记录的身份" + this.user_id)
    axios.get(ADDRESS + '/UserHistory/QuestionnaireHistory', {
      params: {
        user_id: this.user_id,
      }
    })
      .then(response => {

        if (response.data.code === 0) {
          this.historyData = response.data.data.questionnaire_history.map(item => {
            // 拆分question字符串
            const questionArr = item.question.split('');

            const partMap = { 1: '左耳存在听力障碍', 2: '右耳存在听力障碍', 3: '双耳都存在听力障碍' };
            const moodListenSpeakMap = { 1: '是', 2: '否' };

            return {
              date: item.date,
              part: partMap[questionArr[0]] || '',
              mood: moodListenSpeakMap[questionArr[1]] || '',
              listen: moodListenSpeakMap[questionArr[2]] || '',
              speak: moodListenSpeakMap[questionArr[3]] || '',
              score: item.score,
            };
          });
        } else {
          console.error("获取问卷记录失败！");
        }
      })
      .catch(error => {
        console.error('获取问卷记录错误', error);
      });
  },
}
</script>

<style lang="less" scoped>
.title {
  color: @title-color;
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 16px;
}

.background-paphis {
  position: absolute;
  width: 100%;
  height: 60vh;
  z-index: 0;
}
</style>
