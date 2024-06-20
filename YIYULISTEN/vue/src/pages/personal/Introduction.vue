<template>
    <div class="team-introduction">
        <el-row class="medical-teams">
            <el-card class="card-container">
                <div class="card-header1"></div>
                <div class="title-container">
                    <span class="title">专业医疗团队</span>
                </div>
                <div class="card-footer1"></div>
            </el-card>
            <el-col :span="24" v-for="(team, index) in teams" :key="index">
                <el-card class="team-card" :class="{ 'right-avatar': index === 1 }">
                    <div slot="header" style="    font-family: 'Microsoft YaHei', sans-serif;
    font-weight: bold;">
                        <span>{{ team.name }}</span>
                    </div>
                    <div class="team-info">
                        <img :src="team.avatar" class="team-avatar">
                    </div>
                </el-card>
            </el-col>
        </el-row>
        <br /> <br /> <br /> <br />
        <el-row>
            <el-col :span="24">
                <el-card class="card-container">
                    <div class="card-header2"></div>
                    <div class="title-container">
                        <span class="title">资深指导老师</span>
                    </div>
                    <div class="card-footer2"></div>
                </el-card>
                <div style="display: flex;justify-content: center;align-items: center;">
                    <el-carousel :interval="5000" arrow="always" class="teacher-carousel">
                        <el-carousel-item v-for="(teacher, index) in teachers" :key="index">
                            <el-card class="teacher-card">
                                <div style="text-align: center;">
                                    <div slot="header" class="clearfix">
                                        <span>{{ teacher.name }}</span>
                                    </div>
                                    <div class="teacher-info">
                                        <img :src="teacher.avatar" class="teacher-avatar">
                                    </div>
                                </div>
                            </el-card>
                        </el-carousel-item>
                    </el-carousel>
                </div>
            </el-col>
        </el-row>
        <br /> <br /> <br /> <br />
        <el-row>
            <el-col :span="24">
                <el-card class="card-container">
                    <div class="card-header3"></div>
                    <div class="title-container">
                        <span class="title">软件开发人员</span>
                    </div>
                    <div class="card-footer3"></div>
                </el-card>
                <div style="  display: flex;justify-content: center;align-items: center;">
                    <div class="circle-container">
                        <div class="quadrant" v-for="(person, index) in people" :key="index"
                            :class="'quadrant' + (index + 1)">
                            <div :class="'avatar' + (index + 1)">
                                <img :src="person.avatar" class="img">
                                <div class="info">
                                    <p>{{ person.name }}</p>
                                    <p>{{ person.title }}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
export default {
    data() {
        return {
            people: [
                { avatar: require("@/assets/img/kun1.png"), name: '开发人员1', title: '负责前端开发' },
                { avatar: require("@/assets/img/kun2.png"), name: '开发人员2', title: '负责后端开发' },
                { avatar: require("@/assets/img/kun3.png"), name: '开发人员3', title: '全栈开发工程师' },
                { avatar: require("@/assets/img/kun4.png"), name: '开发人员4', title: '测试工程师' },
            ],
            teams: [
                {
                    name: '同济大学校医院',
                    avatar: require("@/assets/img/hos1.jpg"),
                },
                {
                    name: '专业医疗人员团队',
                    avatar: require("@/assets/img/hos2.jpg"),
                },
                {
                    name: '上海市某三甲医院',
                    avatar: require("@/assets/img/hos3.jpg"),
                }
            ],
            teachers: [
                {
                    name: '指导老师1',
                    avatar: require("@/assets/img/tea1.png"),
                },
                {
                    name: '指导老师2',
                    avatar: require("@/assets/img/tea2.png"),
                }
            ]
        };
    },
};
</script>

<style scoped>
.team-introduction {
    padding: 20px;
}

.section-title {
    font-family: 'Microsoft YaHei', sans-serif;
    font-weight: bold;
    margin-bottom: 20px;
    text-align: center;
}

.developers,
.medical-teams {
    display: flex;
    justify-content: center;
}

.photo-placeholder {
    width: 100%;
    height: 150px;
    background-color: #f2f2f2;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 15px;
    font-size: 16px;
    color: #999;
}

.circle-container {
    align-items: center;
    position: relative;
    width: 80vh;
    height: 80vh;
    border-radius: 50%;
    overflow: hidden;
    display: flex;
    flex-wrap: wrap;
}

.quadrant {
    position: absolute;
    width: 50%;
    height: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    box-sizing: border-box;
}

.img {
    border-radius: 50%;
    width: 80%;
    height: 80%;
}

.quadrant1 {
    top: 0;
    left: 0;
    background-color: #f9f9f9;
}

.avatar1 {
    margin-top: 20px;
    margin-left: 80px;
}

.quadrant2 {
    top: 0;
    left: 50%;
    background-color: #f0f0f0;
}

.avatar2 {
    margin-top: 20px;
    margin-right: 80px;
}

.quadrant3 {
    top: 50%;
    left: 0;
    background-color: #e9e9e9;
}

.avatar3 {
    margin-bottom: 20px;
    margin-left: 80px;
}

.quadrant4 {
    top: 50%;
    left: 50%;
    background-color: #e0e0e0;
}

.avatar4 {
    margin-bottom: 20px;
    margin-right: 80px;
}


.info {
    text-align: center;
    margin-top: 5px;
    font-family: 'Microsoft YaHei', sans-serif;
    font-weight: bold;
}

.medical-teams {
    margin-top: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.team-card {
    margin-bottom: 20px;
    text-align: center;
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: center;
}

.team-card.right-avatar {
    flex-direction: row-reverse;
    text-align: right;
}

.team-avatar {
    width: 90%;
    margin: 0 20px;
}


.teacher-carousel {
    width: 80%;
}

.teacher-card {
    display: flex;
    justify-content: center;
    align-items: center;
    height: auto;
}

.teacher-info {
    text-align: center;
}

.teacher-avatar {
    max-width: 20%;
    height: auto;
}

.card-container {
    width: 300px;
    margin: 20px auto;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    border-radius: 30px;
    overflow: hidden;
    position: relative;
}

.card-header1,
.card-footer1 {
    height: 10px;
    background-color: #d3f9d8;
}

.card-header2,
.card-footer2 {
    height: 10px;
    background-color: #51dae7;
}

.card-header3,
.card-footer3 {
    height: 10px;
    background-color: #a5b650;
}

.title-container {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 20px 0;
}

.title {
    font-size: 24px;
    font-weight: bold;
    color: #333;
}
</style>