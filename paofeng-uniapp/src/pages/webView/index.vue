<template>
  <web-view :src="src"></web-view>
</template>

<script setup name="web-view">
import { onLoad } from '@dcloudio/uni-app'
import { ref } from 'vue'

const src = ref('')

onLoad(val => {
  src.value = val.src
})
</script>

<style scoped></style>
