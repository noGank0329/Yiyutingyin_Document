<template>
  <div>

    <div>
      <el-dialog :visible.sync="showModal" title="提示信息" width="30%" @close="closeModal">

        <p style="text-align: center;">
          <br>
          接下来将进行听力测试<br><br>
          请将您的媒体音量调节至50%，并佩戴好耳机<br><br>
          测试顺序为：先右耳，后左耳
        </p>


        <span slot="footer" class="dialog-footer">
          <el-button @click="closeModal" class="button">确认</el-button>
        </span>
      </el-dialog>
    </div>


    <div>
      <img src="@/assets/img/listentest.png" style="width:40%">
    </div>

    <div class="audio-info">
      <p style="text-align: center; font-size: 18px; font-weight: bold;">
        当前播放音频文件：{{ audioList[currentAudioIndex].name }}
      </p>
      <p style="text-align: center; font-size: 18px; font-weight: bold;">
        当前分贝值: {{ currentDecibels + 40 }} dB
      </p>
    </div>

    <div>
      <div id="main" style="width: 100%;height: 520px;background:#fff"></div>
    </div>


    <audio ref="audioPlayer" style="display: none;" loop>
      <source :src=audioList[currentAudioIndex].src>
    </audio>

    <div class="button-container">
      <button @click="playAudio" :disabled="isPlaying" class="button">播放音频</button>
      <button @click="heardIt" class="button">声音太大</button>
      <button @click="didNotHearIt" class="button">声音太小</button>
      <button @click="justHearIt" class="button">刚好听到</button>
    </div>


    <div>
      <el-dialog :visible.sync="secondModal" title="测试结果" width="30%" @close="updatePage">

        <p style="text-align: center;">
          <br>
          右耳测试分数：{{ this.listenRightScore }}<br><br>
          左耳测试分数：{{ this.listenLeftScore }}<br><br>
          右耳听力损失程度：{{ this.listenRightResult }}<br><br>
          左耳听力损失程度：{{ this.listenLeftResult }}<br><br>
          建议音乐曲库：{{ this.result }}<br><br>
        </p>

        <span slot="footer" class="dialog-footer">
          <el-button @click="updatePage" class="button">确认</el-button>
        </span>
      </el-dialog>
    </div>




  </div>
</template>

<script>

import audioFile1 from '@/assets/music/100Hz.wav';
import audioFile2 from '@/assets/music/200Hz.wav';
import audioFile3 from '@/assets/music/500Hz.wav';
import audioFile4 from '@/assets/music/700Hz.mp3';
import audioFile5 from '@/assets/music/1000Hz.mp3';

import * as echarts from 'echarts'
import axios from 'axios';
import { ADDRESS } from '@/services/api'

export default {
  data() {
    return {
      audioContext: null,
      gainNode: null,
      decibelLevels: [-40, -30, -20, -10, 0, 10, 20],
      currentDecibels: 0, // 初始分贝值为 10dB

      lastHeardDecibels: null, // 最近一次用户听到的分贝值
      isPlaying: false, // 是否正在播放音频
      isFirstPlay: true, // 是否是首次播放
      currentAudioIndex: 0, // 当前播放音频的索引

      charts: '',

      user_id: null,
      date: null,
      all_score: null,


      listenLeftData: [],//数据
      listenLeftDataIndex: 0,
      listenLeftScore: 0,//听力分数
      listenLeftResult: '',//测试结果

      listenRightData: [],//数据
      listenRightDataIndex: 0,
      listenRightScore: 0,//听力分数
      listenRightResult: '',//测试结果

      result: '',//综合结果

      panner: null,//用于切换声道

      audioList: [      //音频列表
        { name: '100Hz', src: audioFile1, frequency: 100 },
        { name: '200Hz', src: audioFile2, frequency: 200 },
        { name: '500Hz', src: audioFile3, frequency: 500 },
        { name: '700Hz', src: audioFile4, frequency: 700 },
        { name: '1000Hz', src: audioFile5, frequency: 1000 },

      ],


      showModal: true,//第一个弹窗
      modalMessage: '接下来将进行听力测试，测试顺序为：右耳，左耳，请将您的媒体音量调节至50%，并佩戴好耳机。',

      secondModal: false
    };
  },

  created() {
    console.log("传递成功了吗" + this.$store.state.account.user_id)

  },
  mounted() {
    this.$nextTick(function () {
      this.drawLine('main')
    })
  },
  methods: {

    //传递数据
    getCurrentTime() {
      // ... (您的 getCurrentTime 函数的实现)

      const utcTime = new Date();
      const year = utcTime.getFullYear();
      const month = utcTime.getMonth() + 1;
      const day = utcTime.getDate();
      const hours = utcTime.getHours();
      const minutes = utcTime.getMinutes();
      const seconds = utcTime.getSeconds();
      // const milliseconds = utcTime.getMilliseconds();

      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    },

    updateTime() {
      this.date = this.getCurrentTime()
    },

    postData() {

      this.updateTime()
      console.log("听力测试获取时间" + this.date)

      this.user_id = localStorage.getItem('user_id');
      console.log("听力测试传输数据获取id" + this.user_id)

      this.left_score = this.listenLeftData.join(',')
      this.right_score = this.listenRightData.join(',')
      console.log("听力测试传输数据左耳数组" + this.left_score)
      console.log("听力测试传输数据右耳数组" + this.right_score)


      axios.post(ADDRESS + '/MusicTestAndCure/Test', {}, {
        params: {
          user_id: this.user_id,
          right_score: this.right_score,
          left_score: this.left_score,
          date: this.date,
          all_score: this.all_score,

        }
      })
        .then(response => {
          console.log(response)

          if (response.data.code === 0) {
            console.log("听力测试成功")
          } else {
            console.error("传送听力测试数据出错")
          }
        })
        .catch(error => {
          console.error('测试数据提交失败', error);
        });
    },

    //关闭第一个弹窗
    closeModal() {
      this.showModal = false;
    },

    updatePage() {
      this.secondModal = false;
      this.$router.go(0);
    },

    //计算听力分数
    caculateScore(arr) {
      if (arr.length === 0) {
        console.warn('数组为空，无法计算平均值。');
        return null;
      }

      // 使用 reduce 方法计算总和
      const sum = arr.reduce((acc, value) => acc + value, 0);

      const average = Math.round(100 - sum / arr.length);

      return average;
    },

    listenResult(score) {
      var result = '';
      if (score <= 70 && score > 60) {
        result = '轻度损失'
      } else if (score <= 60 && score > 50) {
        result = '中度损失'
      } else if (score <= 50) {
        result = '重度损失'
      } else if (score > 70) {
        result = '正常'
      }

      return result;
    },

    //音频初始化
    initializeAudio() {


      this.audioContext = new (window.AudioContext || window.webkitAudioContext)();

      // 创建 GainNode
      this.gainNode = this.audioContext.createGain();

      // 创建 StereoPannerNode
      this.panner = this.audioContext.createStereoPanner();



      this.panner.pan.value = 1; // 左声道


      // 连接音频元素和 GainNode
      const audioElement = this.$refs.audioPlayer;
      const source = this.audioContext.createMediaElementSource(audioElement);
      source.connect(this.gainNode);

      // 连接 GainNode 和 StereoPannerNode
      this.gainNode.connect(this.panner);

      // 连接 StereoPannerNode 和 audioContext.destination
      this.panner.connect(this.audioContext.destination);

    },

    //播放音频
    playAudio() {
      // 如果音频正在播放且不是首次播放，不执行初始化和播放操作
      if (this.isPlaying && !this.isFirstPlay) {
        return;
      }
      console.log("初始化")

      // 初始化音频
      if (this.isFirstPlay) {
        this.initializeAudio();
        this.isFirstPlay = false;
      }

      // 设置 GainNode 的增益
      const gain = Math.pow(10, this.currentDecibels / 20);
      this.gainNode.gain.value = gain;

      // 播放音频
      this.$refs.audioPlayer.play();
      this.isPlaying = true;

      //进行图线绘制
      if (this.panner.pan.value == -1) {
        this.listenLeftData.push(this.currentDecibels + 40);
      } else if (this.panner.pan.value == 1) {
        this.listenRightData.push(this.currentDecibels + 40);

      }

      // this.listenLeftData.push(this.currentDecibels+40);
      this.drawLine('main')


    },

    //停止播放音频
    stopAudio() {
      // 停止音频播放
      this.$refs.audioPlayer.pause();
      this.$refs.audioPlayer.currentTime = 0;
      this.isPlaying = false;
    },

    //声音太大，降低音量
    heardIt() {
      // 用户听到了，减小分贝值，并记录最近一次用户听到的分贝值
      const index = this.decibelLevels.indexOf(this.currentDecibels);
      if (index > 0) {
        this.lastHeardDecibels = this.currentDecibels;
        this.currentDecibels = this.decibelLevels[index - 1];

        // 通过改变 GainNode 的增益来模拟降低分贝
        this.gainNode.gain.value = Math.pow(10, this.currentDecibels / 20);

        if (this.panner.pan.value == -1) {
          this.listenLeftData[this.listenLeftDataIndex] = this.currentDecibels + 40;
        } else if (this.panner.pan.value == 1) {
          this.listenRightData[this.listenRightDataIndex] = this.currentDecibels + 40;
        }

        console.log(this.currentDecibels + 'Db')
        console.log(this.listenRightData)


        // 重新渲染折线图
        this.drawLine('main');
      }

    },

    //声音太小，增加音量
    didNotHearIt() {
      // 用户没听到，增加分贝值，并记录最近一次用户听到的分贝值
      const index = this.decibelLevels.indexOf(this.currentDecibels);
      if (index >= 0 && index <= 5) {
        this.lastHeardDecibels = this.currentDecibels;
        this.currentDecibels = this.decibelLevels[index + 1];

        // 通过改变 GainNode 的增益来模拟降低分贝
        this.gainNode.gain.value = Math.pow(10, this.currentDecibels / 20);

        // 在第一个点的位置确定之前，直接赋值而不是 push
        // this.listenLeftData[this.listenLeftDataIndex] = this.currentDecibels+40;

        if (this.panner.pan.value == -1) {
          this.listenLeftData[this.listenLeftDataIndex] = this.currentDecibels + 40;
        } else if (this.panner.pan.value == 1) {
          this.listenRightData[this.listenRightDataIndex] = this.currentDecibels + 40;
        }

        // 重新渲染折线图
        console.log(this.currentDecibels + 'Db')
        console.log(this.listenRightData)


        this.drawLine('main');
      }
    },

    //刚好听到
    justHearIt() {
      if (!this.isFirstPlay && this.isPlaying) {
        this.$message.info('本次能听到的声音分贝为：' + [this.currentDecibels + 40] + 'dB')

        //左声道
        if (this.panner.pan.value == -1) {
          console.log(this.secondModal)
          console.log(this.listenLeftDataIndex)
          if (this.listenLeftDataIndex > 3) {
            //将要结束
            this.listenLeftScore = this.caculateScore(this.listenLeftData)
            this.listenRightScore = this.caculateScore(this.listenRightData)

            this.listenLeftResult = this.listenResult(this.listenLeftScore)
            this.listenRightResult = this.listenResult(this.listenRightScore)

            this.all_score = Math.round((this.listenLeftScore + this.listenRightScore) / 2);

            if (this.all_score <= 70 && this.all_score > 60) {
              this.result = '轻度曲库'
            } else if (this.all_score <= 60 && this.all_score > 50) {
              this.result = '中度曲库'
            } else if (this.all_score <= 50) {
              this.result = '重度曲库'
            }

            //结束
            this.postData()
            this.secondModal = true;
          }
          else {
            this.listenLeftDataIndex = this.listenLeftDataIndex + 1;
          }
        } else if (this.panner.pan.value == 1)//右声道
        {
          this.listenRightDataIndex = this.listenRightDataIndex + 1;
        }

        this.currentDecibels = 0;

        this.stopAudio()


        console.log(this.listenRightDataIndex)

        if (this.listenRightDataIndex >= 5) {
          // 切换到左声道
          this.panner.pan.value = -1;
        }


        this.currentAudioIndex = (this.currentAudioIndex + 1) % this.audioList.length;
        this.$refs.audioPlayer.src = this.audioList[this.currentAudioIndex].src;

        // Reload the audio element
        this.$refs.audioPlayer.load();


        // 断开之前的音频上下文和节点
      } else {
        this.$message.error("您还没有听到音频，无法提供分贝值。");
      }
    },

    //折线图绘制
    drawLine(id) {
      this.charts = echarts.init(document.getElementById(id))
      this.charts.setOption({
        title: {
          left: '3%',
          top: '5%',
          text: "听力测试折线图",//标题文本，支持使用 \n 换行。
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

  },
}
</script>


<style>
.button-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.button {
  background-color: rgb(32, 229, 212);
  color: white;
  width: 80px;
  height: 40px;
  border: 0;
  font-size: 14px;
  border-radius: 30px;
  transition: background-color 0.3s ease;
  /* 添加过渡效果 */
  cursor: pointer;
  /* 将鼠标光标设为手型 */
  margin: 0 20px;
  /* 调整按钮之间的水平间隔 */
}

.button:active {
  background-color: #08ab9e;
  /* 按下时的背景色 */
}


* {
  margin: 0;
  padding: 0;
  list-style: none;
}


.el-dialog {
  background-color: #f0f0f0;
  color: #ee2424;
  border: 2px solid #ccc;
  border-radius: 50px;
}

.audio-info-container {
  display: flex;
  align-items: center;
  justify-content: center;
}

.audio-info {
  flex-grow: 1;
}
</style>