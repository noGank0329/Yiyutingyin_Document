<template>
  <!-- 添加用户头像和下拉菜单 -->
  <a-dropdown class="user-avatar-dropdown" slot="extra" placement="bottomRight">
    <a class="ant-dropdown-link" @click="handleMenuClick">
      <a-avatar icon="user" />
    </a>
    <a-menu slot="overlay">
      <a-menu-item key="logout" @click="logout">
        <a-icon type="logout" /> 退出登录
      </a-menu-item>
      <a-menu-item @click="info">
        <a-icon type="user" /> 个人资料
      </a-menu-item>
    </a-menu>
  </a-dropdown>
</template>

<script>
import { mapGetters } from 'vuex'
import { logout } from '@/services/user'

export default {
  name: 'HeaderAvatar',
  computed: {
    ...mapGetters('account', ['user']),
  },
  methods: {
    logout() {
      logout()
      this.$router.push('/login')
    },
    info() {
      this.$router.push('/personal/modifyinformation')
    }
  }
}
</script>

<style lang="less">
.header-avatar {
  display: inline-flex;

  .avatar,
  .name {
    align-self: center;
  }

  .avatar {
    margin-right: 8px;
  }

  .name {
    font-weight: 500;
  }
}

.avatar-menu {
  width: 150px;
}
</style>
