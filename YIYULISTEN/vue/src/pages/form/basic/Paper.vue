<template>
  <page-layout>
    <div>
      <img class="background-particles" src=https://gw.alipayobjects.com/zos/rmsportal/TVYTbAXWheQpRcWDaDMu.svg>
    </div>
    <a-card :body-style="{ padding: '24px 32px' }" :bordered="false"
      style="width: 70%; margin:0 auto; margin-bottom: 20px; box-shadow: 4px 8px 16px rgba(0, 0, 0, 0.2); border: 1px solid #d9d9d9;border-radius: 10px;">
      <img src="@/assets/img/paper.png" alt="问卷图片" style="width: 300px; border-radius: 10px; margin-left: 20%;">

      <a-form :form="form" style="width: 100%; margin: 0 auto;">
        <a-form-item :label="$t('question_one')" :labelCol="{ span: 7 }" :wrapperCol="{ span: 10 }"
          style="width: 100%; margin: 0 auto;">
          <div style="margin-bottom: 8px;font-size: 16px;">请问孩子哪边的耳朵存在听力障碍？</div>
          <a-select v-model="question_one" placeholder="请选择" style="width: 300px;">
            <a-select-option value="1">左耳存在听力障碍</a-select-option>
            <a-select-option value="2">右耳存在听力障碍</a-select-option>
            <a-select-option value="3">双耳都存在听力障碍</a-select-option>
          </a-select>

        </a-form-item>


        <a-form-item :label="$t('question_two')" :labelCol="{ span: 7 }" :wrapperCol="{ span: 10 }"
          style="width: 100%; margin: 0 auto;">
          <div style="margin-bottom: 8px;font-size: 16px;">请问孩子是否因为听力障碍有明显的烦躁情绪？</div>
          <a-select v-model="question_two" placeholder="请选择" style="width: 300px;">
            <a-select-option value="1">是</a-select-option>
            <a-select-option value="2">否</a-select-option>
          </a-select>

        </a-form-item>

        <a-form-item :label="$t('question_three')" :labelCol="{ span: 7 }" :wrapperCol="{ span: 10 }"
          style="width: 100%; margin: 0 auto;">
          <div style="margin-bottom: 8px;font-size: 16px;">请问孩子在日常沟通中是否有明显的听力困难？</div>
          <a-select v-model="question_three" placeholder="请选择" style="width: 300px;">
            <a-select-option value="1">是</a-select-option>
            <a-select-option value="2">否</a-select-option>
          </a-select>

        </a-form-item>

        <a-form-item :label="$t('question_four')" :labelCol="{ span: 7 }" :wrapperCol="{ span: 10 }"
          style="width: 100%; margin: 0 auto;">
          <div style="margin-bottom: 8px;font-size: 16px;">请问孩子在日常沟通中是否有明显的语言表达困难？</div>
          <a-select v-model="question_four" placeholder="请选择" style="width: 300px;">
            <a-select-option value="1">是</a-select-option>
            <a-select-option value="2">否</a-select-option>
          </a-select>

        </a-form-item>

        <a-form-item :label="$t('question_five')" :labelCol="{ span: 7 }" :wrapperCol="{ span: 10 }" :required="false">
          <div style="margin-bottom: 8px;font-size: 16px;">请让孩子为自己听力丧失程度进行打分？(分值越高代表越严重)</div>
          <a-input-number v-model="score" :min="0" :max="10" />
          <span>分</span>
        </a-form-item>


        <a-form-item style="margin-top: 24px" :wrapperCol="{ span: 10, offset: 7 }">
          <a-button type="primary" @click="paperSubmit">提交</a-button>
          <!-- <a-button style="margin-left: 8px">{{$t('save')}}</a-button> -->
        </a-form-item>

      </a-form>
    </a-card>
  </page-layout>
</template>

<script>

import axios from 'axios';
import { ADDRESS } from '@/services/api'

export default {
  name: 'Paper',
  i18n: require('./i18n'),
  data() {
    return {
      question_one: "",
      question_two: "",
      question_three: "",
      question_four: "",
      question: "",

      score: 0,

      user_id: null,

      form: this.$form.createForm(this),
      date: null//年月日时分秒
    }
  },

  computed: {
    desc() {
      return this.$t('pageDesc')
    }
  },
  methods: {
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
    paperSubmit() {
      // this.user_id=this.$store.state.account.user_id
      this.user_id = localStorage.getItem('user_id');
      this.question = this.question_one + this.question_two + this.question_three + this.question_four;
      this.updateTime()


      console.log("问卷提交用户id" + this.user_id)
      console.log("问卷提交时间" + this.date)
      console.log("问卷提交问题" + this.question)
      console.log("问卷提交分数" + this.score)

      axios
        .post(ADDRESS + '/MusicTestAndCure/Questionnaire', {}, {
          params: {
            user_id: this.user_id,
            date: this.date,
            question: this.question,
            score: this.score
          }
        })
        .then((response) => {
          if (response.data.code === 0) {
            console.log(response)
            this.$message.success("问卷提交成功！");

            this.question_one = "",
              this.question_two = "",
              this.question_three = "",
              this.question_four = "",
              this.question = "",
              this.score = 0

          }
          else {
            this.$message.info("问卷提交失败");
          }
        })
    }
  }
}
</script>

<style scoped>
.background-particles {
  position: absolute;
  width: 100%;
  height: 80vh;
  z-index: 0;
}
</style>
