<template>
</template>

<script setup>
import { onMounted } from 'vue'

// 暂时只能此方式局部引入uni-ui
import uniCard from '@dcloudio/uni-ui/lib/uni-card/uni-card.vue'
import uniSection from '@dcloudio/uni-ui/lib/uni-section/uni-section.vue'
import uniList from '@dcloudio/uni-ui/lib/uni-list/uni-list.vue'
import uniListItem from '@dcloudio/uni-ui/lib/uni-list-item/uni-list-item.vue'

onMounted(() => {
  //在起始页面跳转到test.vue页面并传递参数
  // uni.navigateTo({
  //   url: '/pages/test/index?id=1&name=uniapp'
  // })
})
</script>

<style lang="scss" scoped>
.content-box {
  margin-top: 20px;
  padding: 0 40rpx;
}
.content-nav {
  margin: 40rpx 0;

  display: flex;
  justify-content: space-around;
  .content-item {
    text-align: center;
  }
  .img {
    width: 80rpx;
    height: 80rpx;
  }
  .text {
  }
}
.explanation-info {
  padding: 10rpx 40rpx;
  height: 80vh;
  overflow: auto;
}
.explanation-title {
  font-weight: bold;
  margin-top: 40rpx;
}
.content-btn {
  display: flex;
  align-items: center;
  justify-content: space-between;

  .img {
    width: 310rpx;
    height: 160rpx;
  }
}
.swiper-box {
  position: relative;
  margin-bottom: 60rpx;
  .swiper {
    height: 360rpx;
  }
  .swiper-item {
    width: 100%;
  }
  .message-box {
    margin: 0 40rpx;
    padding: 0 20rpx;
    width: calc(100% - 80rpx);
    box-sizing: border-box;
    height: 80rpx;
    line-height: 80rpx;
    font-size: 26rpx;
    font-weight: bold;

    position: absolute;
    bottom: -40rpx;

    background-color: #fff;
    box-shadow: 0px 0px 60rpx 12rpx rgba(0, 0, 0, 0.1);
    border-radius: 10rpx;
  }
}
.article-box {
  font-size: 30rpx;
  border-top: 20rpx solid #f1f1f1;
  .img-box {
    margin: 20rpx 0;
    width: 100%;
    display: flex;
    flex-wrap: wrap;
  }
  .article-img {
    width: 260rpx;
    height: 160rpx;
    border-radius: 20rpx;
  }
}
.article-title {
  font-weight: bold;
}
</style>
