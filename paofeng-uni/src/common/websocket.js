

function checkPermission(per) {
  const permissions = $store.getters.getPermissions
  const perArr = per.split(':')
  return permissions.some(item => {
    const itemArr = item.split(':')
    return perArr.every(
      (p, index) => itemArr[index] === '*' || p === itemArr[index]
    )
  })
}

export {  }
