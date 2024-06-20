<template>
  <div>
    <div>
      <img class="background-paphis" src=https://gw.alipayobjects.com/zos/rmsportal/TVYTbAXWheQpRcWDaDMu.svg>
    </div>
    <a-card style="box-shadow: 4px 8px 16px rgba(0, 0, 0, 0.2); border: 1px solid #d9d9d9;border-radius: 10px;">
      <div :class="advanced ? 'search' : null">
        <a-form layout="horizontal">
          <div :class="advanced ? null : 'fold'">
            <a-row>
              <a-col :md="8" :sm="24">
                <!-- 时间下拉框 -->
                <a-form-item label="时间" :labelCol="{ span: 2 }" :wrapperCol="{ span: 18, offset: 1 }">
                  <a-select v-model="selectedDate" @change="handleDateChange" placeholder="请选择">
                    <a-select-option v-for="date in dates" :key="date" :value="date">
                      {{ date }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>

              <a-col :md="8" :sm="24">
                <!-- 听力总分下拉框 -->
                <a-form-item label="总分" :labelCol="{ span: 2 }" :wrapperCol="{ span: 18, offset: 1 }">
                  <a-select v-model="selectedAllScore" placeholder="请选择">
                    <a-select-option v-for="score in allScores" :key="score" :value="score">
                      {{ score }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>


            </a-row>
          </div>
          <!-- 其他表单控件和按钮 -->
        </a-form>
      </div>
      <div>
        <!-- <div :loading="loading" id="lineChart" class="chart-container"></div> -->
      </div>

      <div :class="advanced ? 'search' : null">

        <a-form layout="horizontal">
          <span style="float: right; margin-top: -20px; position: left:-1000px;">
            <a-button type="primary" @click="handleQuery">查询</a-button>
            <a-button style="margin-left: 10px;margin-right: 50px;" @click="handleReset">重置</a-button>
          </span>
        </a-form>

      </div>
      <div>


        <standard-table :columns="columns" :dataSource="dataSource" @change="onChange"
          :pagination="{ ...pagination, onChange: onPageChange }">
          <div slot="description" slot-scope="{text}">
            {{ text }}
          </div>

          <div slot="action" slot-scope="{text, record}">
            <!-- 详情跳转的地方 -->
            <router-link
              :to="{ name: '测试曲线图', params: { leftScore: record.left_score, rightScore: record.right_score } }">详情</router-link>
          </div>



        </standard-table>
      </div>
    </a-card>

  </div>
</template>

<script>
import StandardTable from '@/components/table/StandardTable'
let echarts = require('echarts')  //导入echart
import axios from 'axios';
import { ADDRESS } from '@/services/api'


const columns = [
  {
    title: '时间',
    dataIndex: 'date'
  },
  {
    title: '右耳五次听力分数',
    dataIndex: 'right_score',
    scopedSlots: { customRender: 'right_score' }
  },
  {
    title: '左耳五次听力分数',
    dataIndex: 'left_score',
    scopedSlots: { customRender: 'left_score' }
  },
  {
    title: '总分',
    dataIndex: 'all_score',
  },


  {
    // title: '操作',
    scopedSlots: { customRender: 'action' }
  }
]

export default {
  name: 'QueryList',
  components: { StandardTable },
  data() {
    return {
      selectedDate: null,
      selectedAllScore: null,


      user_id: null,
      loading: true,

      Date: new Date().toISOString().split('T')[0],//yyyy-mm-dd

      dates: [],
      allScores: [],

      //求平均分的
      leftScore: 0,
      rightScore: 0,


      advanced: true,
      columns: columns,
      dataSource: [],

      //用来重置后显示数据
      originalDataSource: [],
      selectedRows: [],

      pagination:
      {
        current: 1,
        pageSize: 10,
        total: 0
      },

    };
  },

  created() {
    setTimeout(() => {
      this.loading = false;
      this.GetAllTestData()

      this.initLineChart(); // 初始化线状图
    }, 100);

  },

  mounted() {
    // this.drawLine();
    // this.drawLineChart(); // 绘制线状图

    const leftScore = this.$route.params.leftScore;
    const rightScore = this.$route.params.rightScore;

    // 在这里处理左右耳听力分数，可以传递给你需要的方法或进行其他操作
    console.log('左耳听力分数:', leftScore);
    console.log('右耳听力分数:', rightScore);
  },
  methods: {
    //   calculateAverageScore(scores) {
    //   const scoreArray = scores.split(',').map(Number);
    //   const averageScore = scoreArray.reduce((sum, score) => sum + score, 0) / scoreArray.length;
    //   return isNaN(averageScore) ? '0' : averageScore.toFixed(2);
    // },

    // calculateAndSetAverageScores() {
    //   this.dataSource.forEach(item => {
    //     // 计算并设置左耳平均分
    //     item.left_score = this.calculateAverageScore(item.left_score);

    //     // 计算并设置右耳平均分
    //     item.right_score = this.calculateAverageScore(item.right_score);
    //   });
    // },

    GetAllTestData() {
      this.user_id = localStorage.getItem('user_id');
      console.log("听力测试记录" + this.user_id)

      axios
        .get(ADDRESS + `/UserHistory/TestHistory`, {
          params: {
            user_id: this.user_id,
          }
        })
        .then((response) => {
          console.log('test', response)

          console.log("这里来获取所有的听力测试记录信息")
          console.log(response.data.message)
          console.log(response.data.code)
          console.log(response.data.data.test_history)

          if (response.data.code === 0) {
            console.log("这里来获取所有的听力测试记录信息成功！")
            this.dataSource = response.data.data.test_history
            this.originalDataSource = response.data.data.test_history

            console.log("左耳数组数据")
            console.log(this.left_score)
            console.log("右耳数组数据")
            console.log(this.right_score)

            //提取时间
            this.Extractdates();
            // 计算并设置左右耳平均分
            console.log("平均之前")
            console.log(this.dataSource)


            // this.calculateAndSetAverageScores();

            console.log("平均之后")
            console.log(this.dataSource)

          }
          else {
            console.log("这里来获取所有的风险信息失败！")
          }
        })
        .catch((error) => {
          console.error("这里来获取所有的听力测试记录出错！", error);
        });
    },

    extractDate(fullTimeString) {
      return fullTimeString.substring(0, 10); // 或者 return fullTimeString.slice(0, 10);
    },

    //提取所有的时间
    Extractdates() {
      const uniquedates = [...new Set(this.originalDataSource.map(item => this.extractDate(item.date)))];
      this.dates = uniquedates;
      console.log("我提取的时间")
      console.log(this.dates)
    },

    handleQuery() {
      this.dataSource = this.originalDataSource;
      // 根据选择的时间、总分进行筛选
      const filteredData = this.dataSource.filter(item => {
        const matchesDate = !this.selectedDate || this.extractDate(item.date) === this.selectedDate;
        const matchesScore = !this.selectedAllScore || item.all_score === this.selectedAllScore;
        // const matchesDistrict = !this.selectedDistrict || item.area === this.selectedDistrict;
        return matchesDate && matchesScore;
      });
      // 更新表格数据
      console.log("过滤后")
      console.log(filteredData)
      this.dataSource = filteredData;
    },

    handleReset() {
      // 清空选择，恢复原数据
      this.selectedDate = null;
      this.selectedAllScore = null;
      // this.selectedDistrict = null;
      this.dataSource = this.originalDataSource; // 假设有一个 originalDataSource 用于存储原始数据

      // 重置下拉框的选择状态
      this.allScores = [];
    },

    handleDateChange() {
      this.selectedAllScore = null;

      const uniqueallScores = [...new Set(this.originalDataSource
        .filter(item => this.extractDate(item.date) === this.selectedDate)
        .map(item => item.all_score))];

      this.allScores = uniqueallScores;
      console.log("总分下拉框");
      console.log(this.allScores);
    },


    //     updateLineChartTitle(riskLevel) {
    // //   if (this.lineChart) {
    // //     this.lineChart.setOption({
    // //       title: { text: `风险等级 - ${riskLevel}` },
    // //     });
    // //   }
    // // },

    //改变表格中的内容
    onPageChange(page, pageSize) {
      this.pagination.current = page
      this.pagination.pageSize = pageSize

    },


    toggleAdvanced() {
      this.advanced = !this.advanced
    },


    initLineChart() {
      this.lineChart = echarts.init(document.getElementById('lineChart'));
      this.drawLineChart();
    },


  }
}
</script>

<style>
.background-paphis {
  position: absolute;
  width: 100%;
  height: 60vh;
  z-index: 0;
}
</style>