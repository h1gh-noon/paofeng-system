import mui from '@/static/mui/js/mui.min.js'

export function initHandler() {
  mui.init()

  ///拖动悬浮按钮
  var oDiv = document.getElementById('xuanfu')
  var tX = 0
  var tY = 0
  oDiv.addEventListener(
    'touchstart',
    function (ev) {
      var disX = ev.targetTouches[0].pageX - tX
      var disY = ev.targetTouches[0].pageY - tY

      oDiv.addEventListener('touchmove', function (ev) {
        tX = ev.targetTouches[0].pageX - disX
        tY = ev.targetTouches[0].pageY - disY
        if (tX < 0) {
          //限制左边，不让它超出左边框
          tX = 0
        } else if (
          tX >
          document.documentElement.clientWidth - oDiv.offsetWidth
        ) {
          //限制右边，不让它超出右边框。计算方式:可视区的宽度-div距离左边的距离
          tX = document.documentElement.clientWidth - oDiv.offsetWidth // div现所在位置=可视区的宽度-div距离左边的距离
        }

        if (tY > 0) {
          tY = 0
        } else if (
          tY <
          2 * oDiv.offsetHeight - document.documentElement.clientHeight
        ) {
          tY = 2 * oDiv.offsetHeight - document.documentElement.clientHeight
        }

        oDiv.style.WebkitTransform = 'translate(' + tX + 'px,' + tY + 'px)'
      })

      oDiv.addEventListener('touchend', function (ev) {
        oDiv.ontouchmove = null
        oDiv.ontouchend = null
      })
      ev.preventDefault()
      // ev.stopPropagation()
    },
    false
  )
}
