<template>
  <div class="login-container"
       ref="login">
    <div class="login-weaper animated bounceInDown">
      <div class="login-left">
        <div class="login-time">
             {{time}}
        </div>
        <img class="img" :src="logo" alt="QSHOP-BBC多商户管理系统">
        <p class="title">{{title}}</p>
      </div>
      <div class="login-border">
        <div class="login-main">
          <h4 class="login-title"> 登陆商家后台 </h4>

    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" label-position="left" label-width="0px" class="login-form">
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="账号">
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="loginForm.password" type="password" auto-complete="off" placeholder="密码" @keyup.enter.native="handleLogin">
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-input v-model="loginForm.code" auto-complete="off" placeholder="验证码" style="width: 63%" @keyup.enter.native="handleLogin">
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
        </el-input>
        <div class="login-code">
          <img :src="codeUrl" @click="getCode">
        </div>
      </el-form-item>
      <el-checkbox v-model="loginForm.rememberMe" style="margin:0 0 25px 0;">
        记住我
      </el-checkbox>
      <el-form-item style="width:100%;">
        <el-button :loading="loading" size="medium" class="login-submit" type="primary" style="width:100%;" @click.native.prevent="handleLogin">
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
      </el-form-item>
    </el-form>
       </div>
      </div>
    </div>

    <!--  底部  -->
    <div v-if="$store.state.settings.showFooter" id="el-login-footer">
      <span v-html="$store.state.settings.footerTxt" />
      <span> ⋅ </span>
      <a href="http://www.beian.miit.gov.cn" target="_blank">{{ $store.state.settings.caseNumber }}</a>
    </div>
  </div>
</template>

<script>
import { encrypt } from '@/utils/rsaEncrypt'
import Logo from '@/assets/images/logo.png'
import LogoTaowo from '@/assets/images/logo-taowo.png'
import Config from '@/settings'
import { getCodeImg } from '@/api/login'
import {dateFormat} from "@/utils";
import Cookies from 'js-cookie'
export default {
  name: 'Login',
  data() {
    return {
      codeUrl: '',
      cookiePass: '',
      logo: Logo,
      title: 'QSHOP-BBC多商户管理系统',
      time: "",
      loginForm: {
        username: 'admin',
        password: '123456',
        rememberMe: false,
        code: '',
        uuid: ''
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', message: '用户名不能为空' }],
        password: [{ required: true, trigger: 'blur', message: '密码不能为空' }],
        code: [{ required: true, trigger: 'change', message: '验证码不能为空' }]
      },
      loading: false,
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {
    if (window.location.href.indexOf('taowowang.cn') > -1){
      this.logo = LogoTaowo;
      this.title = 'BBC多商户管理系统';
    }
    this.getCode()
    this.getCookie()
    this.getTime();
    setInterval(() => {
      this.getTime();
    }, 1000);
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.codeUrl = res.img
        this.loginForm.uuid = res.uuid
      })
    },
    getCookie() {
      const username = Cookies.get('username')
      let password = Cookies.get('password')
      const rememberMe = Cookies.get('rememberMe')
      // 保存cookie里面的加密后的密码
      this.cookiePass = password === undefined ? '' : password
      password = password === undefined ? this.loginForm.password : password
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password,
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe),
        code: ''
      }
    },
    getTime() {
      this.time = dateFormat(new Date());
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        const user = {
          username: this.loginForm.username,
          password: this.loginForm.password,
          rememberMe: this.loginForm.rememberMe,
          code: this.loginForm.code,
          uuid: this.loginForm.uuid
        }
        if (user.password !== this.cookiePass) {
          user.password = encrypt(user.password)
        }
        if (valid) {
          this.loading = true
          if (user.rememberMe) {
            Cookies.set('username', user.username, { expires: Config.passCookieExpires })
            Cookies.set('password', user.password, { expires: Config.passCookieExpires })
            Cookies.set('rememberMe', user.rememberMe, { expires: Config.passCookieExpires })
          } else {
            Cookies.remove('username')
            Cookies.remove('password')
            Cookies.remove('rememberMe')
          }
          this.$store.dispatch('Login', user).then(() => {
            this.loading = false
            this.$router.push({ path: this.redirect || '/' })
          }).catch(() => {
            this.loading = false
            this.getCode()
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
.login-container {
  display: flex;
  align-items: center;
  position: relative;
  width: 100%;
  height: 100%;
  margin: 0 auto;
  background: #059FC4;
}

.login-weaper {
  margin: 0 auto;
  width: 1000px;
  box-shadow: -4px 5px 10px rgba(0, 0, 0, 0.4);
  .el-input-group__append {
    border: none;
  }
}

.login-left,
.login-border {
  position: relative;
  min-height: 500px;
  align-items: center;
  display: flex;
}

.login-left {
  border-top-left-radius: 5px;
  border-bottom-left-radius: 5px;
  justify-content: center;
  flex-direction: column;
  background-color: #409EFF;
  color: #fff;
  float: left;
  width: 50%;
  position: relative;
}

.login-left .img {
  width: 200px;
}

.login-time {
  position: absolute;
  left: 25px;
  top: 25px;
  width: 100%;
  color: #fff;
  font-weight: 200;
  opacity: 0.9;
  font-size: 18px;
  overflow: hidden;
}

.login-left .title {
  margin-top: 60px;
  text-align: center;
  color: #fff;
  font-weight: 400;
  letter-spacing: 2px;
  font-size: 25px;
}

.login-border {
  border-left: none;
  border-top-right-radius: 5px;
  border-bottom-right-radius: 5px;
  color: #fff;
  background-color: #fff;
  width: 50%;
  float: left;
  box-sizing: border-box;
}

.login-main {
  margin: 0 auto;
  box-sizing: border-box;
}

.login-main > h3 {
  margin-bottom: 20px;
}

.login-main > p {
  color: #76838f;
}

.login-title {
  color: #333;
  margin-bottom: 40px;
  font-weight: 500;
  font-size: 22px;
  text-align: center;
  letter-spacing: 4px;
}

.login-submit {
  width: 100%;
  height: 45px;
  font-size: 16px;
  letter-spacing: 2px;
  font-weight: bold;
  color: #ffffff;
  cursor: pointer;
  margin-top: 30px;
  transition: 0.25s;
}

.login-form {
  margin: 10px 0;
border-radius: 6px;
    background: #ffffff;
    width: 385px;
    padding: 25px 25px 5px 25px;
  .el-form-item__content {
    width: 100%;
  }

  .el-form-item {
    margin-bottom: 12px;
  }

  .el-input {
    height: 38px;
    input {
      text-indent: 5px;
      height: 38px;
      background: transparent;
      border: none;
      border-radius: 0;
      color: #333;
      border-bottom: 1px solid rgb(235, 237, 242);
    }
  }
  .input-icon{
    height: 39px;width: 14px;margin-left: 2px;
  }

  .login-code {
    width: 33%;
    display: inline-block;
    height: 38px;
    float: right;
    img{
      cursor: pointer;
      vertical-align:middle
    }
  }
}
</style>
