<template>
  <div class="container">
    <!-- Existing components -->
    <div class="record-container">
      <el-image class="slow-rotate" style="width: 200px; height: 200px;" :src="require('../assets/img/music.svg')"
        fit="cover"></el-image>
      <aplayer :audio="audio" class="player-container" ref="aplayer" />
    </div>
  </div>
</template>

<script>
import Vue from 'vue';
import APlayer from '@moefe/vue-aplayer';
import axios from 'axios';
import { ADDRESS } from '@/services/api';

Vue.use(APlayer);

export default {
  data() {
    return {
      music_type: null,
      music_name: null,
      dataSource: [],
      audio: [],
      currentLyrics: null,
    };
  },

  created() {
    this.loadData();
  },

  methods: {
    loadData() {
      this.music_type = this.$route.params.music_type || null;
      this.music_name = this.$route.params.music_name || null;

      axios
        .get(ADDRESS + `/UserHistory/AllMusic`)
        .then((response) => {
          if (response.data.code === 0) {
            this.dataSource = response.data.data.music_list;

            const typeMap = { 0: '轻度', 1: '中度', 2: '重度' };

            const musicList = this.dataSource.map(music => {
              return {
                ...music,
                music_type: typeMap[music.music_type]
              };
            });

            this.dataSource = musicList;

            this.audio = this.filterAndProcessMusic(this.dataSource);
          }
        })
        .catch((error) => {
          console.error("Error fetching music data!", error);
        });
    },

    filterAndProcessMusic(musicList) {
      const filteredMusic = musicList.filter((music) => {
        return !this.music_type || music.music_type === this.music_type;
      });

      const processedMusic = filteredMusic.map((music) => {
        return {
          name: music.music_name,
          artist: '益语听音',
          url: music.music_link,
          cover: 'https://p1.music.126.net/5zs7IvmLv7KahY3BFzUmrg==/109951163635241613.jpg?param=300y300',
        };
      });

      const matchingName = processedMusic.filter((music) => {
        return music.name === this.music_name;
      });

      return [...matchingName, ...processedMusic.filter((music) => music.name !== this.music_name)];
    },
  }
};
</script>

<style scoped>
.container {
  display: flex;
  justify-content: space-between;
}

.record-container {
  width: 100%;
  position: relative;
}

.slow-rotate {
  position: absolute;
  top: -50px;
  left: 550px;
  border-radius: 50%;
  transform-origin: center;
  animation: rotate 9s linear infinite;
}

.slow-rotate:hover {
  animation-play-state: paused;
}

.player-container {
  margin-top: 250px;
  margin-right: 50px;
  width: 100%;
}

@keyframes rotate {
  from {
    transform: rotate(0deg);
  }

  to {
    transform: rotate(360deg);
  }
}
</style>
