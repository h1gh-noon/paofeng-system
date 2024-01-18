<template>
  <div class="hn-calendar">
    <div class="hn-calendar__header__date">
      <img class="hn-calendar__header__btn-item" src="./assets/page_first.png" @click="addYear()" />
      <img class="hn-calendar__header__btn-item" src="./assets/arrow_left.png" @click="addMonth()" />
      <div class="hn-calendar__header__text">
        {{ `${dateYear}年${dateMonth}月` }}{{ dateDay ? dateDay + '日' : '' }}
      </div>
      <img class="hn-calendar__header__btn-item" src="./assets/arrow_right.png" @click="addMonth(1)" />
      <img class="hn-calendar__header__btn-item" src="./assets/page_last.png" @click="addYear(1)" />
    </div>
    <div class="hn-calendar__container">
      <div class="hn-calendar__container-week">
        <div class="hn-calendar__container-week-item" v-for="week in weekText" :key="week">
          {{ week }}
        </div>
      </div>
      <div class="hn-calendar__container-main">
        <div class="hn-calendar__container-main-item" v-for="day in dateList" :key="day.value">
          <div v-if="day.value" class="day-item" :class="{
            'day-item-actived': valueComputed.includes(day.value),
            'day-item-disabled': disableValidate(day)
          }" @click="selectHandler(day)">
            {{ day.day }}
          </div>
          <div v-else></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { momentUtil } from '@/utils'
import { computed, reactive, toRefs, watch } from 'vue'

const props = defineProps({
  modelValue: {
    type: [String, Number, Array],
    default: new Date()
  },
  valueFormat: {
    type: String,
    default: 'YYYY/MM/DD'
  },
  disableValidate: {
    type: Function,
    default: () => false // 不禁用
  }
})

const emit = defineEmits(['update:modelValue'])

const weekText = ['周日', '周一', '周二', '周三', '周四', '周五', '周六']

const data = reactive({
  dateList: [],
  dateTime: new Date(),
  dateYear: null,
  dateMonth: null,
  dateDay: null
})
const { dateList, dateYear, dateMonth, dateDay } = toRefs(data)

const valueComputed = computed(() => parseValueTime(props.modelValue))

// 解析时间
function parseValueTime(value) {
  if (!value) {
    return []
  }
  if (Array.isArray(value)) {
    // 多选展示清空
    data.dateDay = null
    // 多选
    return value.map(e => momentUtil(e).format(props.valueFormat))
  } else {
    data.dateDay = momentUtil(value).date()
    // 时间戳
    return momentUtil(value).format(props.valueFormat)
  }
}

const selectHandler = val => {
  if (props.disableValidate(val)) {
    // 自定义禁用日期
    return
  }

  if (Array.isArray(props.modelValue)) {
    // 多选
    if (valueComputed.value.includes(val.value)) {
      emit(
        'update:modelValue',
        props.modelValue.filter(e => e !== val.value)
      )
    } else {
      emit(
        'update:modelValue',
        props.modelValue.concat(val.value)
      )
    }
  } else {
    // 单选
    emit('update:modelValue', val.value)
  }

}

const getMonthDayHandler = () => {
  const dateMoment = momentUtil(data.dateTime)

  data.dateYear = dateMoment.year()
  data.dateMonth = dateMoment.month() + 1
  data.dateDay = dateMoment.date()

  const tempMoment = momentUtil(`${data.dateYear}/${data.dateMonth}/1`)
  const startDate = tempMoment.format(props.valueFormat)
  const startWeekDay = tempMoment.day() // 开始的星期 0周日

  const endDate = tempMoment
    .add(1, 'M')
    .subtract(1, 'day')
    .format(props.valueFormat)
  const daysNum = endDate.split('/')[2] // 本月天数

  console.log(startDate, endDate, `周${startWeekDay}`, `本月${daysNum}天`)

  const dayArr = []

  if (startWeekDay !== 0) {
    // 预置空的天数
    for (let index = 0; index < startWeekDay; index++) {
      dayArr.push({
        value: '',
        dateTime: '',
        weekDay: '',
        day: ''
      })
    }
  }
  const indexMoment = momentUtil(startDate)
  dayArr.push({
    value: indexMoment.format(props.valueFormat), // 日期
    dateTime: indexMoment.unix(), // unix时间戳
    weekDay: indexMoment.day(), // 周几
    day: 1 // 日
  })
  for (let index = 1; index < daysNum; index++) {
    dayArr.push({
      value: indexMoment.add(1, 'd').format(props.valueFormat),
      dateTime: indexMoment.unix(),
      weekDay: indexMoment.day(),
      day: index + 1
    })
  }
  data.dateList = dayArr
}

const addMonth = val => {
  const tMoment = momentUtil(data.dateTime)
  if (val) {
    data.dateTime = tMoment.add(1, 'M')
  } else {
    data.dateTime = tMoment.subtract(1, 'month')
  }
  getMonthDayHandler()
}

const addYear = val => {
  const tMoment = momentUtil(data.dateTime)
  if (val) {
    data.dateTime = tMoment.add(1, 'Y')
  } else {
    data.dateTime = tMoment.subtract(1, 'year')
  }
  getMonthDayHandler()
}

// 初始化
if (props.modelValue) {
  if (Array.isArray(props.modelValue)) {
    emit('update:modelValue', props.modelValue.map(e => parseValueTime(props.modelValue)))
    // 多选
    if (props.modelValue.length) {
      data.dataTime = props.modelValue[0]
    }
  } else {
    emit('update:modelValue', parseValueTime(props.modelValue))
    data.dateTime = props.modelValue
  }
}
getMonthDayHandler()
</script>

<style lang="scss" scoped>
@import './calendar';

.hn-calendar {
  width: 100%;

  &__header__date {
    display: flex;
    justify-content: center;
    align-items: center;
  }

  &__header__btn-item {
    height: vw(30);
    line-height: vw(30);
    width: vw(30);
    text-align: center;
    color: #333;

    margin: 0 vw(10);
  }

  &__header__text {
    flex: 1;
    text-align: center;
    font-weight: bold;
    // color: $uni-color-primary;
    color: #333;
  }

  .hn-calendar__container-week,
  .hn-calendar__container-main {
    // display: flex;
    display: grid;
    grid-template-columns: repeat(7, 1fr);

    text-align: center;

    &-item {
      // flex: 1;
      height: 80rpx;
      line-height: 80rpx;
      text-align: center;
    }

    .day-item {
      display: inline-block;
      width: 80rpx;
    }

    .day-item-actived {
      background: $uni-color-primary;
      border-radius: 50%;

      color: $uni-text-color-inverse;
    }

    .day-item-disabled {
      color: $uni-text-color-disable;
    }
  }
}
</style>
