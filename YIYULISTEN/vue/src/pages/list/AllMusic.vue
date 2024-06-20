<template>
  <div>
    <div>
      <img class="background-pic" src="@/assets/img/ocean.jpg">
    </div>
    <div class="playlist-cards">
      <a-card hoverable @click="filterByType(0)" class="playlist-card">
        <img alt="轻度歌单" src="@/assets/img/less.png" />
        <a-card-meta style="margin-top:20px;font-size: large;" title="微风轻扬" description="轻度类型的歌单" />
      </a-card>
      <a-card hoverable @click="filterByType(1)" class="playlist-card">
        <img alt="中度歌单" src="@/assets/img/middle.png" />
        <a-card-meta style="margin-top:20px;font-size: large;" title="音浪起伏" description="中度类型的歌单" />
      </a-card>
      <a-card hoverable @click="filterByType(2)" class="playlist-card">
        <img alt="重度歌单" src="@/assets/img/more.png" />
        <a-card-meta style="margin-top:20px;font-size: large;" title="震撼狂潮" description="重度类型的歌单" />
      </a-card>
    </div>
    <a-card style="  box-shadow: 4px 8px 16px rgba(0, 0, 0, 0.4);border: 1px solid #d9d9d9;">
      <div :class="advanced ? 'search' : null">
        <a-form layout="horizontal">
          <div :class="advanced ? null : 'fold'">
            <a-row>
              <a-col :md="8" :sm="24">
                <!-- 省份下拉框 -->
                <a-form-item label="类别" :labelCol="{ span: 2 }" :wrapperCol="{ span: 18, offset: 1 }">
                  <a-select v-model="selectedMusicType" @change="handleTypeChange" placeholder="请选择">
                    <a-select-option v-for="Type in music_types" :key="Type" :value="Type">
                      {{ mapMusicType(Type) }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>

              <a-col :md="8" :sm="24">
                <!-- 城市下拉框 -->
                <a-form-item label="风格" :labelCol="{ span: 2 }" :wrapperCol="{ span: 18, offset: 1 }">
                  <a-select v-model="selectedMusicStyle" @change="handleStyleChange" placeholder="请选择">
                    <a-select-option v-for="Style in music_styles" :key="Style" :value="Style">
                      {{ Style }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>

              <a-col :md="8" :sm="24">
                <!-- 区下拉框 -->
                <a-form-item label="名称" :labelCol="{ span: 2 }" :wrapperCol="{ span: 18, offset: 1 }">
                  <a-select v-model="selectedMusicName" placeholder="请选择">
                    <a-select-option v-for="Name in music_names" :key="Name" :value="Name">
                      {{ Name }}
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
            <a-button style="margin-left: 10px;" @click="handleReset">重置</a-button>
            <a-button v-if="user_type === '2'" style="margin-left: 20px;margin-right: 50px;"
              @click="addMusic">新增</a-button>
          </span>
        </a-form>

      </div>
      <div>


        <standard-table id="music-list-container" :columns="columns" :dataSource="dataSource" @change="onChange"
          :pagination="{ ...pagination, onChange: onPageChange }">
          <div slot="description" slot-scope="{text}">
            {{ text }}
          </div>

          <div slot="action" slot-scope="{text, record}">
            <!-- 详情跳转的地方 -->
            <router-link
              :to="{ name: '音乐详情', params: { music_type: record.music_type, music_name: record.music_name } }">详情</router-link>
            <span style="margin-right: 20px;"></span>
            <a-button v-if="user_type === '2'" @click="handleEdit(record)">修改</a-button>
            <span style="margin-right: 20px;"></span>
            <a-button v-if="user_type === '2'" @click="handleDelete(record)">删除</a-button>
          </div>


        </standard-table>
      </div>

      <div>
        <el-dialog :visible.sync="AddModal" title="音乐信息" width="30%" @close="closeModal">

          <el-form ref="musicForm" label-width="80px" :model="formData" :rules="rules">
            <el-form-item label="程度类别" prop="type">
              <el-select v-model="formData.type" placeholder="请选择">
                <el-option label="轻度" value="0"></el-option>
                <el-option label="中度" value="1"></el-option>
                <el-option label="重度" value="2"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="音乐风格" prop="style">
              <el-input v-model="formData.style"></el-input>
            </el-form-item>

            <el-form-item label="音乐名称" prop="name">
              <el-input v-model="formData.name"></el-input>
            </el-form-item>

            <el-form-item label="音乐链接" prop="link">
              <el-input v-model="formData.link"></el-input>
            </el-form-item>
          </el-form>



          <span slot="footer" class="dialog-footer">
            <el-button @click="closeAddModal" class="button">确认</el-button>
          </span>
        </el-dialog>
      </div>


      <div>
        <!-- 新的弹窗用于修改音乐信息 -->
        <el-dialog :visible.sync="EditModal" title="修改音乐信息" width="30%" @close="closeEditModal">
          <el-form ref="editForm" :model="editFormData" :rules="editRules" label-width="80px">
            <el-form-item label="程度类别" prop="type">
              <el-select v-model="editFormData.type" placeholder="请选择">
                <!-- <el-option label="轻度" value="0"></el-option>
                <el-option label="中度" value="1"></el-option>
                <el-option label="重度" value="2"></el-option> -->
                <el-option v-for="value in [0, 1, 2]" :key="value" :label="mapMusicType(value)"
                  :value="value"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="音乐风格" prop="style">
              <el-input v-model="editFormData.style"></el-input>
            </el-form-item>
            <el-form-item label="音乐名称" prop="name">
              <el-input v-model="editFormData.name"></el-input>
            </el-form-item>
            <el-form-item label="音乐链接" prop="link">
              <el-input v-model="editFormData.link"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="saveEdit" class="button">确认</el-button>
          </span>
        </el-dialog>
      </div>

      <div>
        <el-dialog :visible.sync="deleteConfirmationModal" title="删除确认" width="30%"
          @close="closeDeleteConfirmationModal">
          <p>您确定要删除这首歌曲吗？</p>
          <span slot="footer" class="dialog-footer">
            <el-button @click="confirmDelete">确定</el-button>
            <el-button @click="cancelDelete">取消</el-button>
          </span>
        </el-dialog>
      </div>

    </a-card>

  </div>
</template>

<script>
import StandardTable from '@/components/table/StandardTable'
// let echarts = require('echarts')  //导入echart
import axios from 'axios';
import { ADDRESS } from '@/services/api'


const columns = [
  {
    title: '曲库类别',
    dataIndex: 'music_type',
    customRender: (text) => {
      // Assuming 0 represents "轻度", 1 represents "中度", and 2 represents "重度"
      const mapping = {
        0: '轻度',
        1: '中度',
        2: '重度'
      };

      // Display the corresponding value based on the mapping
      return mapping[text];
    }
  },
  {
    title: '音乐风格',
    dataIndex: 'music_style',
    scopedSlots: { customRender: 'music_style' }
  },
  {
    title: '音乐名称',
    dataIndex: 'music_name',
    // sorter: true,
    needTotal: true,
    customRender: (text) => text // 直接显示区的文本
  },


  {
    title: '操作',
    scopedSlots: { customRender: 'action' }
  }
]

export default {
  name: 'QueryList',
  components: { StandardTable },
  data() {
    return {
      showParticles: true,
      selectedMusicType: null,
      selectedMusicStyle: null,
      selectedMusicName: null,

      loading: true,


      music_types: [],
      music_styles: [],
      music_names: [],

      music_type: '',

      user_type: '',


      formData: {
        type: '',
        style: '',
        name: '',
        link: '',
      },

      rules: {
        type: [{ required: true, message: '请选择程度类别', trigger: 'blur' }],
        style: [{ required: true, message: '请填写音乐风格', trigger: 'blur' }],
        name: [{ required: true, message: '请填写音乐名称', trigger: 'blur' }],
        link: [{ required: true, message: '请填写音乐链接', trigger: 'blur' }],
      },

      AddModal: false,


      //修改音乐的弹窗
      EditModal: false,
      editFormData: {
        type: '',
        style: '',
        name: '',
        link: '',
        music_id: ''
      },
      editRules: {
        type: [{ required: true, message: '请选择程度类别', trigger: 'blur' }],
        style: [{ required: true, message: '请填写音乐风格', trigger: 'blur' }],
        name: [{ required: true, message: '请填写音乐名称', trigger: 'blur' }],
        link: [{ required: true, message: '请填写音乐链接', trigger: 'blur' }],
      },


      deleteConfirmationModal: false,
      deleteRecord: null,



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

      Authorization: null
    };
  },

  created() {
    this.showParticles = true;
    setTimeout(() => {
      this.loading = false;
      this.GetAllMusicData()
      this.typeCheck()

      this.dataSource = this.originalDataSource

      // this.initLineChart(); // 初始化线状图
    }, 100);

  },

  methods: {
    filterByType(type) {
      this.selectedMusicType = type;
      this.handleQuery();
      const musicListContainer = document.getElementById('music-list-container');
      if (musicListContainer) {
        // 滚动到歌单列表处
        musicListContainer.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }
    },
    mapMusicType(type) {
      const typeMap = {
        0: '轻度',
        1: '中度',
        2: '重度'
      };
      return typeMap[type];
    },

    //身份验证
    typeCheck() {
      this.user_type = localStorage.getItem('user_type');
      console.log("音乐菜单界面的身份" + this.user_type)

    },

    GetAllMusicData() {


      axios
        .get(ADDRESS + `/UserHistory/AllMusic`)
        .then((response) => {
          console.log('test', response)

          console.log("这里来获取所有的音乐信息")
          console.log(response.data.message)
          console.log(response.data.code)
          console.log(response.data.data.music_list)

          if (response.data.code === 0) {
            console.log("这里来获取所有的音乐信息成功！")
            this.dataSource = response.data.data.music_list
            this.originalDataSource = response.data.data.music_list


            // //将数字转换成文字类型
            // const typeMap = { 0: '轻度', 1: '中度', 2: '重度' };

            // const musicList = this.originalDataSource.map(music => {
            //   return {
            //     ...music,
            //     music_type: typeMap[music.music_type]
            //   };
            // });

            // this.originalDataSource = musicList
            // this.dataSource = musicList

            //提取音乐程度类型
            this.ExtractType();

            console.log(this.dataSource)

          }
          else {
            console.log("这里来获取所有的音乐信息失败！")
          }


        })
        .catch((error) => {
          console.error("这里来获取所有的音乐信息出错！", error);
        });
    },

    //提取所有的程度类型
    ExtractType() {
      const uniqueType = [...new Set(this.originalDataSource.map(item => item.music_type))];

      this.music_types = uniqueType;
      console.log("我提取的程度类型")
      console.log(this.music_types)
    },

    handleQuery() {
      this.dataSource = this.originalDataSource;
      // 根据选择的程度、类别和名字进行筛选
      const filteredData = this.dataSource.filter(item => {
        const matchesType = this.selectedMusicType === null || item.music_type === this.selectedMusicType;
        const matchesStyle = !this.selectedMusicStyle || item.music_style === this.selectedMusicStyle;
        const matchesName = !this.selectedMusicName || item.music_name === this.selectedMusicName;
        return matchesType && matchesStyle && matchesName;
      });
      // 更新表格数据
      console.log("过滤后")
      console.log(filteredData)
      this.dataSource = filteredData;
    },

    handleReset() {
      // 清空选择，恢复原数据
      this.selectedMusicType = null;
      this.selectedMusicStyle = null;
      this.selectedMusicName = null;
      this.dataSource = this.originalDataSource; // 假设有一个 originalDataSource 用于存储原始数据

      // 重置下拉框的选择状态
      this.music_styles = [];
      this.music_names = [];
    },

    handleTypeChange() {
      this.selectedMusicStyle = null;
      this.selectedMusicName = null;

      const uniqueStyle = [...new Set(this.originalDataSource
        .filter(item => item.music_type === this.selectedMusicType)
        .map(item => item.music_style))];

      this.music_styles = uniqueStyle;
      console.log("音乐风格下拉框");
      console.log(this.music_styles);
    },

    handleStyleChange() {
      this.selectedMusicName = null;
      this.music_names = this.originalDataSource
        .filter(item => item.music_type === this.selectedMusicType && item.music_style === this.selectedMusicStyle)
        .map(item => item.music_name);

    },


    //改变表格中的内容
    onPageChange(page, pageSize) {
      this.pagination.current = page
      this.pagination.pageSize = pageSize

    },

    toggleAdvanced() {
      this.advanced = !this.advanced
    },

    addMusic() {
      this.AddModal = true
    },

    closeAddModal() {

      this.$refs.musicForm.validate((valid) => {
        if (valid) {
          axios
            .post(ADDRESS + '/AdministratorAboutMusic/AddMusic', {}, {
              params: {
                music_name: this.formData.name,
                music_type: this.formData.type,
                music_style: this.formData.style,
                music_link: this.formData.link,

              }
            })
            .then((response) => {
              console.log('test', response)

              console.log("这里来检测新增音乐是否成功")

              if (response.data.code === 0) {
                console.log("新增成功！")

                this.$message.success("添加成功！")

              }
              else {
                console.log("新增音乐失败！")
                this.$message.error("添加失败！")
              }


            })
            .catch((error) => {
              console.error("新增音乐出错！", error);
              this.$message.error("添加出错！")
            });
          this.AddModal = false;
        } else {
          // 表单验证失败，不执行添加音乐逻辑
        }
      })
    },

    handleEdit(record) {
      console.log(record)
      // 将当前行的音乐信息填充到编辑表单中
      this.editFormData.music_id = record.music_id
      console.log("音乐id" + record.music_id)
      this.editFormData.type = record.music_type;
      this.editFormData.style = record.music_style;
      this.editFormData.name = record.music_name;
      this.editFormData.link = record.music_link;

      // this.music_type=parseInt(this.editFormData.type)

      // 打开编辑弹窗
      this.EditModal = true;
    },
    saveEdit() {
      // 保存编辑后的数据
      this.$refs.editForm.validate((valid) => {
        if (valid) {
          // 提交编辑后的数据到后端
          axios
            .post(ADDRESS + '/AdministratorAboutMusic/ModifyMusic', {}, {

              params: {
                music_name: this.editFormData.name,
                music_type: this.editFormData.type,
                music_style: this.editFormData.style,
                music_link: this.editFormData.link,
                music_id: this.editFormData.music_id
              }


            })
            .then((response) => {
              console.log("音乐链接")
              console.log(this.editFormData.name)
              console.log(this.editFormData.type)
              console.log(this.editFormData.style)
              console.log(this.editFormData.link)
              console.log(this.editFormData.music_id)


              if (response.data.code === 0) {
                this.$message.success('修改成功！');
                // 刷新表格数据
                this.GetAllMusicData();
                // 关闭编辑弹窗
                this.EditModal = false;
              } else {
                this.$message.error('修改失败！');
              }
            })
            .catch((error) => {
              console.error('修改音乐出错！', error);
              this.$message.error('修改出错！');
            });
        } else {
          // 表单验证失败，不执行保存逻辑
        }
      });
    },
    closeEditModal() {
      // 关闭编辑弹窗时清空编辑表单数据
      this.$refs.editForm.resetFields();
      // 关闭编辑弹窗
      this.EditModal = false;
    },

    handleDelete(record) {
      this.deleteRecord = record;
      console.log("删除的歌曲" + this.deleteRecord.music_id)
      this.deleteConfirmationModal = true;
    },

    confirmDelete() {
      // 在这里实现您的删除逻辑，使用 this.deleteRecord
      // 一旦删除成功，关闭确认对话框
      axios
        .post(ADDRESS + '/AdministratorAboutMusic/DeleteMusic', {}, {
          params: {
            music_id: this.deleteRecord.music_id
          }
        })
        .then((response) => {
          if (response.data.code === 0) {
            this.$message.success('删除成功！');
            // 刷新表格数据
            this.GetAllMusicData();
            // 关闭删除弹窗
            this.deleteConfirmationModal = false;
          } else {
            this.$message.error('删除失败！');
          }
        })
        .catch((error) => {
          console.error('删除出错！', error);
          this.$message.error('删除出错！');
        });

    },

    cancelDelete() {
      // 取消删除操作，关闭确认对话框
      this.deleteConfirmationModal = false;
    },

  },
  mounted() {
    this.showParticles = true;
  },
}
</script>

<style lang="less" scoped>
.playlist-cards {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.playlist-card {
  width: 31%;
  cursor: pointer;
  box-shadow: 4px 8px 16px rgba(0, 0, 0, 0.3);
  border: 1px solid #d9d9d9;
  border-radius: 10%;
}

.playlist-card img {
  width: 100%;
  height: 350px;
  object-fit: cover;
  border-radius: 10%;
}

.background-pic {
  position: absolute;
  width: 100%;
  height: 60vh;
  z-index: 0;
  opacity: 0.4;
  border-radius: 10%;
}
</style>