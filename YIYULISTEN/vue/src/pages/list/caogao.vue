<template>

  <div>
    <div>
      <div class="record-container">
        <el-image class="slow-rotate" style="width: 330px; height: 330px;" :src="require('../../assets/img/music.jpg')"
          fit="cover"></el-image>

        <!-- <iframe frameborder="no" border="0" marginwidth="0" marginheight="0" width=330 height=86 src="//music.163.com/outchain/player?type=2&id=1855755735&auto=1&height=66"></iframe> -->

      </div>


      <div id="app">

        <div class="player-container">
          <meting-js ref="meting" :key="currentSong" :server="server" :type="type" :id="currentSong" fixed="false"
            autoplay="false" loop="all" order="list" preload="auto" list-folded="false" list-max-height="500px"
            lrc-type="0" @play="startRotate" @pause="stopRotate">
          </meting-js>
        </div>

        <div class="player-container">
          <meting-js ref="meting" :key="currentSong" :server="server" :type="type" :id="currentSong" fixed="false"
            autoplay="false" loop="all" order="list" preload="auto" list-folded="false" list-max-height="500px"
            lrc-type="0" @play="startRotate" @pause="stopRotate">
          </meting-js>
        </div>

        <div class="player-container">
          <meting-js ref="meting" :key="currentSong" :server="server" :type="type" :id="currentSong" fixed="false"
            autoplay="false" loop="all" order="list" preload="auto" list-folded="false" list-max-height="500px"
            lrc-type="0" @play="startRotate" @pause="stopRotate">
          </meting-js>
        </div>

        <div class="player-container">
          <meting-js ref="meting" :key="currentSong" :server="server" :type="type" :id="currentSong" fixed="false"
            autoplay="false" loop="all" order="list" preload="auto" list-folded="false" list-max-height="500px"
            lrc-type="0" @play="startRotate" @pause="stopRotate">
          </meting-js>
        </div>

        <div class="player-container">
          <meting-js ref="meting" :key="currentSong" :server="server" :type="type" :id="currentSong" fixed="false"
            autoplay="false" loop="all" order="list" preload="auto" list-folded="false" list-max-height="500px"
            lrc-type="0" @play="startRotate" @pause="stopRotate">
          </meting-js>
        </div>

        <div class="player-container">
          <meting-js ref="meting" :key="currentSong" :server="server" :type="type" :id="currentSong" fixed="false"
            autoplay="false" loop="all" order="list" preload="auto" list-folded="false" list-max-height="500px"
            lrc-type="0" @play="startRotate" @pause="stopRotate">
          </meting-js>
        </div>

        <div class="player-container">
          <meting-js ref="meting" :key="currentSong" :server="server" :type="type" :id="currentSong" fixed="false"
            autoplay="false" loop="all" order="list" preload="auto" list-folded="false" list-max-height="500px"
            lrc-type="0" @play="startRotate" @pause="stopRotate">
          </meting-js>
        </div>

        <div class="player-container">
          <meting-js ref="meting" :key="currentSong" :server="server" :type="type" :id="currentSong" fixed="false"
            autoplay="false" loop="all" order="list" preload="auto" list-folded="false" list-max-height="500px"
            lrc-type="0" @play="startRotate" @pause="stopRotate">
          </meting-js>
        </div>

        <div class="player-container">
          <meting-js ref="meting" :key="currentSong" :server="server" :type="type" :id="currentSong" fixed="false"
            autoplay="false" loop="all" order="list" preload="auto" list-folded="false" list-max-height="500px"
            lrc-type="0" @play="startRotate" @pause="stopRotate">
          </meting-js>
        </div>

        <div class="player-container">
          <meting-js ref="meting" :key="currentSong" :server="server" :type="type" :id="currentSong" fixed="false"
            autoplay="false" loop="all" order="list" preload="auto" list-folded="false" list-max-height="500px"
            lrc-type="0" @play="startRotate" @pause="stopRotate">
          </meting-js>
        </div>

        <div class="player-container">
          <meting-js ref="meting" :key="currentSong" :server="server" :type="type" :id="currentSong" fixed="false"
            autoplay="false" loop="all" order="list" preload="auto" list-folded="false" list-max-height="500px"
            lrc-type="0" @play="startRotate" @pause="stopRotate">
          </meting-js>
        </div>

      </div>
    </div>

    <div class="playlist-container">
      <div :id="scrollId" style="border:1px solid #08ab9e;
    height: 100px;
    width: 200px;
    overflow-y: scroll;
    position: absolute;
    text-align: center;
    top: 10%;
    left: 40%;">
        <!-- 使用播放列表数据替代静态内容 -->
        <div v-for="(song, index) in playlist" :key="index" :class="{ 'bold-text': currentSongIndex === index }">
          {{ song.name }}<br>
        </div>



      </div>

      <button @click="playPrevious">上一首</button>
      <button @click="playNext">下一首</button>
    </div>


  </div>

</template>

<script>

export default {
  data() {
    return {


      scrollTimer: null, // 滚动定时器
      pauseTimer: null, // 暂停定时器
      scrollId: 'scrollId', // 滚动容器id
      scrollDirection: 'down', // 滚动方向 up向上 down向下


      server: 'netease',
      type: 'song',

      songList: [],
      currentSong: '',
      currentSongIndex: 0,
      playlist: [],

      visiblePlaylistIndex: 0, // 用于跟踪可见播放列表的起始位置的索引
      visiblePlaylistSize: 5, // 播放列表中可见歌曲的数量

      styles: {
        animationPlayState: 'paused'
      }
    }
  },
  destroyed() {
    // 清理定时器
    clearTimeout(this.pauseTimer)
    this.pauseTimer = null
    clearInterval(this.scrollTimer)
    this.scrollTimer = null
    // 清理点击监听
    window.document.removeEventListener('click', this.pauseScroll)
  },

  created() {
    const songIds = ['36270426', '515269424', '1959324681', '1874302915', '2100630469', '1519343', '1386002493'];
    // 请求歌曲信息，这里简化为直接在组件中定义歌曲名字和ID
    this.playlist = songIds.map(id => ({ id, name: `歌曲 ${id}` }));
    this.currentSong = this.playlist[0].id; // 默认播放第一首歌曲
    console.log(this.playlist)

  },
  mounted() {
    this.dataCompleteFun()
  },

  computed: {
    visiblePlaylist() {
      // 返回播放列表的可见部分
      return this.playlist.slice(this.visiblePlaylistIndex, this.visiblePlaylistIndex + this.visiblePlaylistSize);
    },
  },
  methods: {
    // 数据加载完成方法
    dataCompleteFun() {
      // 开启滚动
      this.autoScroll()
    },
    autoScroll() {
      const scrollHeight = document.getElementById(this.scrollId).scrollHeight
      const clientHeight = document.getElementById(this.scrollId).clientHeight
      const scroll = scrollHeight - clientHeight
      // 滚动长度为0
      if (scroll === 0) {
        return
      }
      // 触发滚动方法
      this.scrollFun()
      // 去除点击监听
      window.document.removeEventListener('click', this.pauseScroll)
      // 重设点击监听
      window.document.addEventListener('click', this.pauseScroll, false)
    },
    pauseScroll() {
      // 定时器不为空
      if (this.scrollTimer) {
        // 清除定时器
        clearInterval(this.scrollTimer)
        this.scrollTimer = null
        // 一秒钟后重新开始定时器
        this.pauseTimer = setTimeout(() => {
          this.scrollFun()
        }, 2000)
      }
    },
    scrollFun() {
      // 如果定时器存在
      if (this.scrollTimer) {
        // 则先清除
        clearInterval(this.scrollTimer)
        this.scrollTimer = null
      }
      this.scrollTimer = setInterval(() => {
        const scrollHeight = document.getElementById(this.scrollId).scrollHeight
        const clientHeight = document.getElementById(this.scrollId).clientHeight
        const scroll = scrollHeight - clientHeight
        // 获取当前滚动条距离顶部高度
        const scrollTop = document.getElementById(this.scrollId).scrollTop
        // 向下滚动
        if (this.scrollDirection === 'down') {
          const temp = scrollTop + 1
          document.getElementById(this.scrollId).scrollTop = temp // 滚动
          // 距离顶部高度  大于等于 滚动长度
          if (scroll <= temp) {
            // 滚动到底部 停止定时器
            clearInterval(this.scrollTimer)
            this.scrollTimer = null
            // 改变方向
            this.scrollDirection = 'up'
            // 一秒后重开定时器
            setTimeout(() => {
              this.scrollFun()
            }, 1000)
          }
          // 向上滚动
        } else if (this.scrollDirection === 'up') {
          const temp = scrollTop - 0.5
          document.getElementById(this.scrollId).scrollTop = temp // 滚动
          // 距离顶部高度  小于等于 0
          if (temp <= 0) {
            // 滚动到底部 停止定时器
            clearInterval(this.scrollTimer)
            this.scrollTimer = null
            // 改变方向
            this.scrollDirection = 'down'
            // 一秒后重开定时器
            setTimeout(() => {
              this.scrollFun()
            }, 1000)
          }
        }
      }, 150)
    },

    playSong(song) {
      this.currentSong = song.id; // 点击播放列表中的歌曲时，更新当前播放歌曲
      this.currentSongIndex = this.playlist.findIndex(item => item.id === song.id);
      this.updateVisiblePlaylist();

    },
    playPrevious() {
      this.currentSongIndex = (this.currentSongIndex - 1 + this.playlist.length) % this.playlist.length;
      this.currentSong = this.playlist[this.currentSongIndex].id;
      this.updateVisiblePlaylist();
    },
    playNext() {
      this.currentSongIndex = (this.currentSongIndex + 1) % this.playlist.length;
      this.currentSong = this.playlist[this.currentSongIndex].id;
      console.log(this.currentSongIndex)
      console.log(this.currentSong)
      this.updateVisiblePlaylist();

    },

    playSong(song) {
      this.currentSong = song.id; // 点击播放列表中的歌曲时，更新当前播放歌曲
      this.currentSongIndex = this.playlist.findIndex(item => item.id === song.id);
      this.updateVisiblePlaylist();

    },
    playPrevious() {
      this.currentSongIndex = (this.currentSongIndex - 1 + this.playlist.length) % this.playlist.length;
      this.currentSong = this.playlist[this.currentSongIndex].id;
      this.updateVisiblePlaylist();
    },
    playNext() {
      this.currentSongIndex = (this.currentSongIndex + 1) % this.playlist.length;
      this.currentSong = this.playlist[this.currentSongIndex].id;
      console.log(this.currentSongIndex)
      console.log(this.currentSong)
      this.updateVisiblePlaylist();

    },

    playSong(song) {
      this.currentSong = song.id; // 点击播放列表中的歌曲时，更新当前播放歌曲
      this.currentSongIndex = this.playlist.findIndex(item => item.id === song.id);
      this.updateVisiblePlaylist();

    },
    playPrevious() {
      this.currentSongIndex = (this.currentSongIndex - 1 + this.playlist.length) % this.playlist.length;
      this.currentSong = this.playlist[this.currentSongIndex].id;
      this.updateVisiblePlaylist();
    },
    playNext() {
      this.currentSongIndex = (this.currentSongIndex + 1) % this.playlist.length;
      this.currentSong = this.playlist[this.currentSongIndex].id;
      console.log(this.currentSongIndex)
      console.log(this.currentSong)
      this.updateVisiblePlaylist();

    },

    playSong(song) {
      this.currentSong = song.id; // 点击播放列表中的歌曲时，更新当前播放歌曲
      this.currentSongIndex = this.playlist.findIndex(item => item.id === song.id);
      this.updateVisiblePlaylist();

    },
    playPrevious() {
      this.currentSongIndex = (this.currentSongIndex - 1 + this.playlist.length) % this.playlist.length;
      this.currentSong = this.playlist[this.currentSongIndex].id;
      this.updateVisiblePlaylist();
    },
    playNext() {
      this.currentSongIndex = (this.currentSongIndex + 1) % this.playlist.length;
      this.currentSong = this.playlist[this.currentSongIndex].id;
      console.log(this.currentSongIndex)
      console.log(this.currentSong)
      this.updateVisiblePlaylist();

    },

    playSong(song) {
      this.currentSong = song.id; // 点击播放列表中的歌曲时，更新当前播放歌曲
      this.currentSongIndex = this.playlist.findIndex(item => item.id === song.id);
      this.updateVisiblePlaylist();

    },
    playPrevious() {
      this.currentSongIndex = (this.currentSongIndex - 1 + this.playlist.length) % this.playlist.length;
      this.currentSong = this.playlist[this.currentSongIndex].id;
      this.updateVisiblePlaylist();
    },
    playNext() {
      this.currentSongIndex = (this.currentSongIndex + 1) % this.playlist.length;
      this.currentSong = this.playlist[this.currentSongIndex].id;
      console.log(this.currentSongIndex)
      console.log(this.currentSong)
      this.updateVisiblePlaylist();

    },
    updateVisiblePlaylist() {
      // 调整可见播放列表的索引，确保当前歌曲在可见范围内
      const halfVisible = Math.floor(this.visiblePlaylistSize / 2);

      if (this.currentSongIndex < this.visiblePlaylistIndex + halfVisible) {
        // 当前歌曲在可见范围的上半部分，向前调整可见播放列表的索引
        this.visiblePlaylistIndex = Math.max(0, this.currentSongIndex - halfVisible);
      } else if (this.currentSongIndex >= this.visiblePlaylistIndex + this.visiblePlaylistSize - halfVisible) {
        // 当前歌曲在可见范围的下半部分，向后调整可见播放列表的索引
        this.visiblePlaylistIndex = Math.min(this.playlist.length - this.visiblePlaylistSize, this.currentSongIndex - halfVisible + 1);
      }
    },


  }
}
</script>

<style scoped>
.record-container {
  position: relative;
}

.slow-rotate {
  position: absolute;
  top: -100px;
  left: 60px;
  border-radius: 50%;
  transform-origin: center;
  animation: rotate 9s linear infinite;
}

.slow-rotate:hover {
  animation-play-state: paused;
}



@keyframes rotate {
  from {
    transform: rotate(0deg);
  }

  to {
    transform: rotate(360deg);
  }
}

.player-container {
  margin-top: 250px;
  margin-left: 100px;
  /* 设置右边距，调整与右侧元素的距离 */
  float: left;
  /* 左浮动 */
  width: 70%;
}

.playlist {
  overflow-y: auto;
  /* 启用纵向滚动 */
  max-height: 150px;
  /* 设置可见区域的最大高度，根据需要调整 */
}

.playlist-container {
  /* float: left; */
  width: 30%;
  margin-top: 80px;
  margin-left: 200px;
  /* 设置右边距，调整与右侧元素的距离 */
  /* padding: 20px; */
}

.playlist-container h2 {
  text-align: center;
}

.bold-text {
  font-weight: bold;
}

.player-container {
  margin-top: 250px;
  margin-left: 100px;
  /* 设置右边距，调整与右侧元素的距离 */
  float: left;
  /* 左浮动 */
  width: 70%;
}

.playlist {
  overflow-y: auto;
  /* 启用纵向滚动 */
  max-height: 150px;
  /* 设置可见区域的最大高度，根据需要调整 */
}

.playlist-container {
  /* float: left; */
  width: 30%;
  margin-top: 80px;
  margin-left: 200px;
  /* 设置右边距，调整与右侧元素的距离 */
  /* padding: 20px; */
}

.playlist-container h2 {
  text-align: center;
}

.bold-text {
  font-weight: bold;
}

.player-container {
  margin-top: 250px;
  margin-left: 100px;
  /* 设置右边距，调整与右侧元素的距离 */
  float: left;
  /* 左浮动 */
  width: 70%;
}

.playlist {
  overflow-y: auto;
  /* 启用纵向滚动 */
  max-height: 150px;
  /* 设置可见区域的最大高度，根据需要调整 */
}

.playlist-container {
  /* float: left; */
  width: 30%;
  margin-top: 80px;
  margin-left: 200px;
  /* 设置右边距，调整与右侧元素的距离 */
  /* padding: 20px; */
}

.playlist-container h2 {
  text-align: center;
}

.bold-text {
  font-weight: bold;
}

.player-container {
  margin-top: 250px;
  margin-left: 100px;
  /* 设置右边距，调整与右侧元素的距离 */
  float: left;
  /* 左浮动 */
  width: 70%;
}

.playlist {
  overflow-y: auto;
  /* 启用纵向滚动 */
  max-height: 150px;
  /* 设置可见区域的最大高度，根据需要调整 */
}

.playlist-container {
  /* float: left; */
  width: 30%;
  margin-top: 80px;
  margin-left: 200px;
  /* 设置右边距，调整与右侧元素的距离 */
  /* padding: 20px; */
}

.playlist-container h2 {
  text-align: center;
}

.bold-text {
  font-weight: bold;
}

.player-container {
  margin-top: 250px;
  margin-left: 100px;
  /* 设置右边距，调整与右侧元素的距离 */
  float: left;
  /* 左浮动 */
  width: 70%;
}

.playlist {
  overflow-y: auto;
  /* 启用纵向滚动 */
  max-height: 150px;
  /* 设置可见区域的最大高度，根据需要调整 */
}

.playlist-container {
  /* float: left; */
  width: 30%;
  margin-top: 80px;
  margin-left: 200px;
  /* 设置右边距，调整与右侧元素的距离 */
  /* padding: 20px; */
}

.playlist-container h2 {
  text-align: center;
}

.bold-text {
  font-weight: bold;
}
</style>