<template>
  <div>
    <div id="main" style="width: 100%;height: 520px;background:#fff"></div>
  </div>
</template>

<script>

import * as echarts from 'echarts'


export default {
  name: 'history_line',
  data() {
    return {
      right_score: "",
      left_score: "",

      listenRightData: [],
      listenLeftData: [],

      charts: '',

    }
  },

  mounted() {
    this.$nextTick(function () {
      this.drawLine('main')
    })
  },

  created() {
    this.LoadRouteParams()
  },

  methods: {

    LoadRouteParams() {
      // 从路由参数中获取并存储
      this.right_score = this.$route.params.rightScore || '';
      this.left_score = this.$route.params.leftScore || '';

      this.listenRightData = this.right_score.split(',').map(Number);
      this.listenLeftData = this.left_score.split(',').map(Number);

      this.listenRightData = this.listenRightData.map(item => item);
      this.listenLeftData = this.listenLeftData.map(item => item);

      console.log("转换成功")
      console.log(this.listenRightData)
      console.log(this.listenLeftData)

      this.drawLine('main')
    },
    drawLine(id) {
      this.charts = echarts.init(document.getElementById(id))
      this.charts.setOption({
        title: {
          left: '3%',
          top: '5%',
          text: "听力测试图",//标题文本，支持使用 \n 换行。
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          align: 'right',//文字在前图标在后
          left: '3%',
          top: '15%',
          // data: ['近一周']
        },
        grid: {
          top: '30%',
          left: '5%',
          right: '5%',
          bottom: '5%',
          containLabel: true
        },

        toolbox: {
          feature: {
            saveAsImage: {} //保存为图片
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: true,
          axisTick: {
            alignWithLabel: true //保证刻度线和标签对齐
          },
          data: ["100Hz", "200Hz", "500Hz", "700Hz", "1000Hz"] //x坐标的名称

        },
        yAxis: {
          type: 'value',

          min: 0,  // 设置 y 轴的最小值
          max: 60,   // 设置 y 轴的最大值
          interval: 10,  // 设置刻度之间的间隔

          axisLabel: {
            formatter: '{value} dB'
          }
        },

        series: [

          {
            name: '右耳',
            type: 'line',
            stack: '数值',
            itemStyle: {
              color: 'rgb(32,229,212)',
              lineStyle: {
                color: 'rgb(64, 158, 255)'
              },
              emphasis: {  // 高亮时的样式（例如鼠标悬停）
                color: 'rgb(32,229,212)',  // 修改高亮时的点颜色
                borderColor: 'rgb(32,229,212)',  // 修改高亮时的点边框颜色
                borderWidth: 8  // 修改高亮时的点边框宽度
              }
            },
            symbol: 'triangle', // 设置点的形状，可以是 'circle', 'rect', 'roundRect', 'triangle', 'diamond', 'pin', 'arrow', 'none'
            symbolSize: 10,  // 设置点的大小
            // 另一条折线的数据，可以根据需要设置
            data: this.listenRightData
          },
          {
            name: '左耳',
            type: 'line', //折线图line;柱形图bar;饼图pie
            stack: '总量',
            itemStyle: {
              color: 'rgb(255,96,64)', //改变折线点的颜色
              lineStyle: {
                color: 'rgb(255,96,64)' //改变折线颜色
              },
              emphasis: {  // 高亮时的样式（例如鼠标悬停）
                color: 'rgb(255,0,0)',  // 修改高亮时的点颜色
                borderColor: 'rgb(255,0,0)',  // 修改高亮时的点边框颜色
                borderWidth: 8  // 修改高亮时的点边框宽度
              }

            },
            symbol: 'diamond', // 设置点的形状，可以是 'circle', 'rect', 'roundRect', 'triangle', 'diamond', 'pin', 'arrow', 'none'
            symbolSize: 10,  // 设置点的大小
            data: this.listenLeftData
          },

        ]
      })
    },
  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  list-style: none;
}
</style>