<template>
  <common-layout>
    <div>
      <vue-particles color="#409EFF" :particleOpacity="0.7" :particlesNumber="150" shapeType="circle" :particleSize="4"
        linesColor="#409EFF" :linesWidth="1" :lineLinked="true" :lineOpacity="0.4" :linesDistance="150" :moveSpeed="3"
        :hoverEffect="true" hoverMode="grab" :clickEffect="true" clickMode="push">
      </vue-particles>
    </div>
    <div class="top">
      <div class="header">
        <img alt="logo" class="logo" src="@/assets/img/Symbol.png" />
        <span class="title">{{ systemName }}</span>
      </div>
    </div>

    <div class="login">
      <a-form>
        <a-tabs size="large" :tabBarStyle="{ textAlign: 'center' }" style="padding: 0 2px;">

          <a-tab-pane tab="账户密码登录" key="1">
            <a-alert type="error" :closable="true" v-show="error" :message="error" showIcon
              style="margin-bottom: 24px;" />

            <a-form :form="form">
              <a-form-item>
                <a-input autocomplete="autocomplete" size="large" placeholder="身份证号码"
                  v-decorator="['loginID', { rules: [{ required: true, message: '请输入身份证号', whitespace: true }] }]">
                  <a-icon slot="prefix" type="user" />
                </a-input>
              </a-form-item>

              <a-form-item>
                <a-input size="large" placeholder="密码" autocomplete="autocomplete" type="password"
                  v-decorator="['loginPassword', { rules: [{ required: true, message: '请输入密码', whitespace: true }] }]">
                  <a-icon slot="prefix" type="lock" />
                </a-input>
              </a-form-item>
            </a-form>


            <a-button @click="onSubmit" :loading="logging" style="width: 100%;margin-top: 24px;" size="large"
              htmlType="submit" type="primary">登录</a-button>
          </a-tab-pane>

          <a-tab-pane tab="注册" key="2">

            <a-form :form="registerForm">
              <a-form-item>
                <a-input size="large" placeholder="身份证号"
                  v-decorator="['register_user_id', { rules: [{ required: true, message: '请输入身份证号', whitespace: true }] }]">
                  <a-icon slot="prefix" type="idcard" />
                </a-input>

              </a-form-item>

              <a-form-item>
                <a-input size="large" placeholder="姓名"
                  v-decorator="['user_name', { rules: [{ required: true, message: '请输入昵称', whitespace: true }] }]">
                  >
                  <a-icon slot="prefix" type="user" />
                </a-input>
              </a-form-item>

              <a-form-item>
                <a-select size="large" placeholder="选择性别"
                  v-decorator="['user_gender', { rules: [{ required: true, message: '请选择性别' }] }]"
                  v-model="user_gender">
                  <a-select-option value="男">
                    <a-icon type="man" />
                    男
                  </a-select-option>

                  <a-select-option value="女">
                    <a-icon type="woman" />
                    女
                  </a-select-option>
                </a-select>
              </a-form-item>


              <a-form-item>
                <a-input size="large" placeholder="昵称"
                  v-decorator="['user_nickname', { rules: [{ required: true, message: '请选择性别' }] }]">
                  >
                  <a-icon slot="prefix" type="smile" />
                </a-input>
              </a-form-item>

              <a-form-item>
                <a-input size="large" placeholder="设置密码" type="password" v-decorator="['register_user_password', {
                  rules: [{ required: true, message: '请设置密码' }, { required: true, message: '请设置密码' },
                  { min: 6, message: '密码至少为6个字符' },
                  { max: 20, message: '密码最多为20个字符' }]
                }]">
                  >
                  <a-icon slot="prefix" type="lock" />
                </a-input>
              </a-form-item>

              <a-form-item>
                <a-input size="large" placeholder="请再次输入密码" type="password"
                  v-decorator="['passwordForSure', { rules: [{ required: false, message: '请再次输入密码', whitespace: true }] }]">
                  <a-icon slot="prefix" type="lock" />
                </a-input>
              </a-form-item>
            </a-form>
            <a-form-item>
              <a-button @click="RegisterSubmit" style="width: 100%;margin-top: 24px;" size="large" htmlType="submit"
                type="primary">注册</a-button>
            </a-form-item>
          </a-tab-pane>

        </a-tabs>
      </a-form>

    </div>

  </common-layout>
</template>

<script>
import CommonLayout from '@/layouts/CommonLayout'
// import {getRoutesConfig} from '@/services/user'
// import {setAuthorization} from '@/utils/request'
// import {loadRoutes} from '@/utils/routerUtil'
import { mapMutations } from 'vuex'
// import {useRoute} from 'vue-router'
import axios from 'axios';
import { ADDRESS } from '@/services/api'

export default {
  name: 'Login',
  components: { CommonLayout },
  data() {
    return {
      logging: false,//表示登录状态
      error: '',
      user_gender: '',
      form: this.$form.createForm(this),
      registerForm: this.$form.createForm(this),
      showForm: true,

      user_id: null,
      user_type: null

    }
  },
  computed: {
    systemName() {
      return this.$store.state.setting.systemName
    }
  },
  methods: {
    ...mapMutations('account', ['setUserId', 'setUserType', 'setRoles']),



    //注册函数
    RegisterSubmit(event) {
      console.log("这是注册的event参数", event)
      event.preventDefault()
      this.registerForm.validateFields((err) => {
        console.log(this.error)
        console.log('这是form', this.registerForm)
        if (!err) {
          const user_name = this.registerForm.getFieldValue('user_name')
          const register_user_id = this.registerForm.getFieldValue('register_user_id')
          // const user_gender = this.form.getFieldValue('user_gender')
          const user_nickname = this.registerForm.getFieldValue('user_nickname')
          const register_user_password = this.registerForm.getFieldValue('register_user_password')
          const passwordForSure = this.registerForm.getFieldValue('passwordForSure')
          const regexUser = /^.{18}$/
          console.log('这是名称', user_name)
          console.log('这是ID', register_user_id)
          console.log('这是性别', this.user_gender)
          console.log('这是昵称', user_nickname)
          console.log('这是密码', register_user_password)
          console.log('这是二次密码', passwordForSure)


          if (register_user_password !== passwordForSure) {
            this.$message.error("请确认两次密码输入一致")
            return
          } else
            if (!regexUser.test(register_user_id)) {
              this.$message.error('请确认身份证ID格式是否正确')
              return
            } else
              if (
                user_name == undefined ||
                register_user_id == undefined ||
                this.user_gender == undefined ||
                register_user_password == undefined ||
                passwordForSure == undefined) {
                this.error = '请完整的输入注册信息'
                console.log('3')
                return
              }


          axios
            .post(ADDRESS + '/user/register', {}, {
              params: {
                user_id: register_user_id,
                user_name: user_name,
                user_gender: this.user_gender,
                user_nickname: user_nickname,
                user_password: register_user_password,
              }
            })
            .then((response) => {
              if (response.data.code === 0) {
                console.log(response)
                this.$message.success("注册成功！");
                this.registerForm.resetFields();
                // 跳转到登录页面

                //  this.afterLogin(response)

              }
              else {
                this.$message.info("注册失败");
              }
            })


        }

      })
    },

    //登录函数
    onSubmit(e) {
      console.log("这是登录函数的e参数", e)
      e.preventDefault()//防止默认提交

      this.form.validateFields((err) => {
        console.log(this.error)
        console.log('这是form', this.form)//填写的表单内容
        if (!err) {
          // this.logging = true
          const ID = this.form.getFieldValue('loginID')
          const password = this.form.getFieldValue('loginPassword')


          //规定id格式
          const regexCustomer = /^.{18}$/
          const regexDoctor = /^.{10}$/
          const regexManager = /^.{5}$/

          if (regexCustomer.test(ID)) {
            this.user_type = 0
          } else if (regexDoctor.test(ID)) {
            this.user_type = 1
          } else if (regexManager.test(ID)) {
            this.user_type = 2
          } else {
            this.$message.error('id格式错误')
            return
          }

          console.log(ID)
          console.log(password)


          axios
            .get(ADDRESS + '/user/login', {
              params: {
                user_id: ID,
                user_password: password,
              }
            })
            .then((response) => {
              if (response.data.code === 0) {
                console.log(response)
                //  this.user_type=response.data.data.user_type
                this.user_id = ID
                this.afterLogin(response)
              }
              else {
                console.log(response)
                this.$message.info("登陆失败");
              }
            })

        }
      })
    },


    //   console.log('afterLogin',res)
    //   // var result = res.data.resultDto
    //   var roles = [
    //     {
    //       id: null,
    //       operation: ["add","edit","delete"]
    //     },
    //   ];
    //   if(this.userType == 2)
    //     roles[0].id = "manager"
    //   else if(this.userType == 1)
    //     roles[0].id = 'staff'
    //   else if(this.userType == 0)
    //     roles[0].id = 'user'
    //   this.setRoles(roles)
    //   var user
    //   if(this.userType == 1){
    //     user = {

    //       id: this.id
    //     }      
    //   }
    //   else if(this.userType == 0){
    //     user = {
    //       id: this.id,

    //     }
    //   }
    //   else if(this.userType == 2){
    //     user = {
    //       id: this.id,

    //     }
    //   }
    //   this.setUser(user)
    //   // 获取路由配置
    //   getRoutesConfig(roles).then(result => {
    //     const routesConfig = result.data.data
    //     loadRoutes(routesConfig)
    //     this.$router.push('/Music/AllMusic')
    //     this.$message.success('欢迎回来', 3)
    //   })
    // },
    afterLogin(res) {

      //设置id可以全局取
      // this.setUserId(this.user_id)
      // this.setUserType(this.user_type)

      localStorage.setItem('user_id', this.user_id)
      localStorage.setItem('user_type', this.user_type)

      var roles = [
        {
          id: null,
          operation: ["add", "edit", "delete"]
        },
      ];

      console.log("用户的类型二次确认" + this.user_type)
      if (this.user_type == 2) {
        roles[0].id = "manager"
      }
      else if (this.user_type == 1) {
        roles[0].id = 'doctor'
      }
      else if (this.user_type == 0) {
        roles[0].id = 'customer'
      }



      this.setRoles(roles)
      console.log("roles")
      console.log(roles)


      const loginRes = res.data
      console.log(loginRes)

      // setAuthorization({token: loginRes.data.token, expireAt: new Date(loginRes.data.expireAt)})

      // getRoutesConfig().then(result => {
      //   const routesConfig = result.data.data
      //   loadRoutes(routesConfig)
      this.$router.push('/personal/personal')
      this.$message.success('欢迎回来', 3)
      // })

      // this.$router.push({path:'/Dashboard'})
      // this.$message.success('欢迎回来', 3)

      // })


    },




  }
}
</script>

<style lang="less" scoped>
.common-layout {
  .top {
    text-align: center;

    .header {
      height: 30px;
      line-height: 4px;

      a {
        text-decoration: none;
      }

      .logo {
        height: 44px;
        vertical-align: top;
        margin-right: 16px;
        margin-top: -20px;
      }

      .title {
        font-size: 33px;
        color: @title-color;
        font-family: 'Myriad Pro', 'Helvetica Neue', Arial, Helvetica, sans-serif;
        font-weight: 600;
        position: relative;
        top: 2px;
      }
    }

    .desc {
      font-size: 14px;
      // color: @text-color-second;
      margin-top: 12px;
      margin-bottom: 40px;
    }
  }

  .login {
    width: 368px;
    margin: 0 auto;

    @media screen and (max-width: 576px) {
      width: 95%;
    }

    @media screen and (max-width: 320px) {
      .captcha-button {
        font-size: 14px;
      }
    }

    .icon {
      font-size: 24px;
      color: @text-color-second;
      margin-left: 16px;
      vertical-align: middle;
      cursor: pointer;
      transition: color 0.3s;

      // &:hover {
      //   color: @primary-color;
      // }
    }
  }

  #particles-js {
    width: 100%;
    height: 100%;
    position: fixed;
    top: 10px;
  }

}
</style>
