<template>
  <hnCalendar v-model="data.dateArr" />
  {{ data.date }}
</template>

<script setup name="HnCalendar">
import { reactive, ref } from 'vue'
import { onLoad } from '@dcloudio/uni-app'
import hnCalendar from '@/components/hn-calendar'

const data = reactive({
  dateArr: [],
  date: '2020-01-01'
})
onLoad(val => {
  console.log(val)
})
</script>

<style scoped lang="scss"></style>
