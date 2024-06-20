<template>
    <section class='m-calendar' :style="dateStyle">
        <!-- 切换月份 -->
        <header class='changeMonth'>
            <span class='prepMonth' @click="changeMonth(-1)"></span>
            <h1>{{ year }}年{{ month }}月</h1>
            <span class='nextMonth' @click="changeMonth(1)"></span>
        </header>
        <ul class='dates'>
            <!-- 星期 -->
            <li class='weeks' v-for="item in weeks" :key="item">{{ item }}</li>
            <!-- 日期 -->
            <li class='day' v-for="(item, i) in dates" :key="i" :class="{
                isPrep: item.isPrep,
                isNext: item.isNext,
                hidden: (item.isNext || item.isPrep) && !showPrepNext,
                isToday: item.date == today,
                isSelected: item.date == selectedDate,
                isMarked: markDates.includes(item.date)
            }" @click="clickDate(item)">{{ item.date == today ? '今' : item.day }}</li>
        </ul>
    </section>
</template>
  
<script>
const D = new Date()
const ThisYear = D.getFullYear()
const ThisMonth = D.getMonth() + 1
const today = new Date().toLocaleDateString()
export default {
    // props都为非必传
    props: {
        // 初始年月
        startYearMonth: {
            type: String,
            default() {
                return `${ThisYear}/${ThisMonth}`
                // 格式：2021-1或2020-01或者2020/1或者2020/01
            }
        },
        // 需要标记的日期数组
        markDate: {
            type: Array,
            default() {
                return []
                // 格式：['2020-01-01', '2020-02-12']
            }
        },
        // 选中的日期
        checkedDate: {
            type: String,
            default() {
                return ''
                // 格式：'2020-01-01'
            }
        },
        // 是否星期一开始，默认星期日开始
        mondayStart: {
            type: Boolean,
            default() {
                return false
            }
        },
        // 是否显示上个月和下个月日期
        showPrepNext: {
            type: Boolean,
            default() {
                return true
            }
        },
        // 日期字体颜色
        fontColor: {
            type: String,
            default() {
                return '#000'
            }
        },
        // 标记点颜色
        markColor: {
            type: String,
            default() {
                return '#ff6633'
            }
        },
        // 选中的日期字体颜色
        activeColor: {
            type: String,
            default() {
                return '#fff'
            }
        },
        // 选中的日期背景颜色
        activeBgColor: {
            type: String,
            default() {
                return '#ff6633'
            }
        }
    },
    data() {
        return {
            // 当前年
            year: ThisYear,
            // 当前月
            month: ThisMonth,
            // 今天
            today,
            // 日期数组
            dates: [],
            // 选中的日期
            selectedDate: ''
        }
    },
    computed: {
        // 标记的日期
        markDates() {
            // 获得不带0的日期，如：2021/1/1  =>  2021/1/1
            return this.markDate.map(item => this.formatDate(item))
        },
        // 星期
        weeks() {
            if (this.mondayStart) {
                return ['一', '二', '三', '四', '五', '六', '日']
            } else {
                return ['日', '一', '二', '三', '四', '五', '六']
            }
        },
        dateStyle() {
            return {
                '--font-color': this.fontColor,
                '--mark-color': this.markColor,
                '--active-color': this.activeColor,
                '--active-bg-color': this.activeBgColor
            }
        }
    },
    created() {
        this.year = new Date(this.startYearMonth).getFullYear()
        this.month = new Date(this.startYearMonth).getMonth() + 1
        // 选中的日期
        if (this.checkedDate) {
            // 获得不带0的日期，如：2021-1-1  =>  2021/1/1
            this.selectedDate = this.formatDate(this.checkedDate)
        }
        // 初始化日历
        this.initCalendar()
    },
    methods: {
        // 初始化日历
        initCalendar() {
            // ⚠️注意：new Date中的month要减1才是真正的本月月数，即本月：this.month - 1，下个月：this.month
            // ⚠️注意：下个月的第0天即为本月的最后一天
            // 上个月总天数（本月第0天日期）
            const prepMonthDays = new Date(this.year, this.month - 1, 0).getDate()
            // 上个月最后一天星期几（本月第0天星期数）
            const prepMonthEndDayWeek = new Date(this.year, this.month - 1, 0).getDay()
            // 当前月总天数（下个月第0天日期）
            const thisMonthDays = new Date(this.year, this.month, 0).getDate()
            // 当前月第一天是星期几
            const firstDayWeek = new Date(this.year, this.month - 1, 1).getDay()
            // 当前月最后一天是星期几（下个月第0天星期数）
            const thisEndDayWeek = new Date(this.year, this.month, 0).getDay()
            var dates = []
            // 需要计算的总天数
            var totalDays = firstDayWeek + thisMonthDays
            // 从星期一开始index为1，从星期天开始index为0
            var index = this.mondayStart ? 1 : 0
            // 星期一开始且本月最后一天是星期天之后，需补满最后一行
            if (this.mondayStart && thisEndDayWeek > 0) {
                totalDays += 7 - thisEndDayWeek
            } else if (!this.mondayStart && thisEndDayWeek < 6) {
                // 星期天开始且本月最后一天是星期天之前，需补满最后一行
                totalDays += 6 - thisEndDayWeek
            }
            for (index; index < totalDays; index++) {
                // 上个月月底
                if (index < firstDayWeek) {
                    // 上个月天数 - 上个月最后一天星期数 + 下标 (如：31 - 5 + 0)
                    const day = prepMonthDays - prepMonthEndDayWeek + index
                    const date = new Date(this.year, this.month - 2, day).toLocaleDateString()
                    dates.push({ isPrep: true, day, date })
                } else if (index >= firstDayWeek + thisMonthDays) {
                    // 下个月月初
                    // 下标 - 当前月总天数 - 当前月第一天星期数 + 1 (如：30 - 31 + 1 + 1)
                    const day = index - thisMonthDays - firstDayWeek + 1
                    const date = new Date(this.year, this.month, day).toLocaleDateString()
                    dates.push({ isNext: true, day, date })
                } else {
                    // 本月
                    // 下标 - 当前月第一天星期数 + 1 (如：5 - 5 + 1)
                    const day = index - firstDayWeek + 1
                    const date = new Date(this.year, this.month - 1, day).toLocaleDateString()
                    dates.push({ day, date })
                }
            }
            this.dates = [...dates]
        },
        // 点击日期
        clickDate({ date, isPrep, isNext }) {
            if (isPrep || isNext) return
            this.selectedDate = date
            this.$emit('clickDate', date.replace(/\//g, '-'))
        },
        // 切换月份
        changeMonth(month) {
            this.month += month
            if (this.month === 0) {
                this.month = 12
                this.year--
            } else if (this.month === 13) {
                this.month = 1
                this.year++
            }
            this.initCalendar()
            this.$emit('changeMonth', `${this.year}-${this.month}`)
        },
        // 格式化日期
        formatDate(date) {
            // 获得不带0，且分隔符为/的日期，如：2020-01-01  =>  2021/1/1
            return new Date(date).toLocaleDateString()
        }
    }
}
</script>
  
<style lang='less' scoped>
.m-calendar {
    max-width: 400px;
    max-height: 450px;
    border: 1px solid #054C96;
    border-radius: 8px 8px 0 0;
    padding-bottom: 20px;

    header {
        display: flex;
        align-items: center;
        justify-content: center;
        border-bottom: 1px solid #054C96;
        padding: 20px 0;
        margin-bottom: 5px;

        h1 {
            margin: 0 20px;
            color: #444;
            font-size: 20px;
            font-weight: bold;
            width: 120px;
            text-align: center;
        }

        span {
            cursor: pointer;
            padding: 4px 10px;

            &::after {
                display: inline-block;
                content: '';
                width: 10px;
                height: 10px;
                border-top: 2px solid var(--font-color);
            }

            &.prepMonth::after {
                border-left: 2px solid var(--font-color);
                transform: rotate(-45deg);
            }

            &.nextMonth::after {
                border-right: 2px solid var(--font-color);
                transform: rotate(45deg);
            }

            &:hover::after {
                border-color: var(--mark-color);
            }
        }
    }

    ul {
        display: flex;
        flex-wrap: wrap;
        margin: 0 auto;
        padding: 0 12px;

        li {
            width: 42px;
            height: 42px;
            margin: 4px calc((100% / 7 - 42px) / 2);
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            font-weight: bold;
            position: relative;
            transition: all ease .25s;
            border-radius: 6px;

            // 标记
            &::after {
                bottom: 0;
                left: 50%;
                transform: translateX(-50%);
                position: absolute;
                display: inline-block;
                content: '';
                width: 5px;
                height: 5px;
                border-radius: 50%;
            }

            // 星期
            &.weeks {
                font-size: 18px;
                color: #444;
                margin-bottom: 12px;
            }

            &.day {
                cursor: pointer;
                font-size: 20px;
                color: var(--font-color);

                // 今天
                &.isToday {
                    color: var(--mark-color);
                }

                // 标记
                &.isMarked::after {
                    transition: all ease .25s;
                    background: var(--mark-color);
                }

                // 选中、hover
                &:hover,
                &.isSelected {
                    background: var(--active-bg-color);
                    color: var(--active-color);

                    &:after {
                        display: none;
                    }
                }

                // 上个月、下个月
                &.isNext,
                &.isPrep {
                    cursor: default;
                    opacity: .3;

                    &:hover {
                        color: var(--font-color);
                        opacity: .3;
                        background: transparent;
                    }
                }

                // hidden
                &.hidden {
                    opacity: 0;

                    &:hover {
                        opacity: 0;
                    }
                }
            }
        }
    }
}
</style>
  