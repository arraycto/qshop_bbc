const goodsUtilsFun = {
  // 格式化时间
  timeFun(data) {
    if (!isNaN(data)) {
      var time = new Date(parseInt(data))
      var y = time.getFullYear()
      var m = time.getMonth() + 1
      var d = time.getDate()
      var h = time.getHours() < 10 ? '0' + time.getHours() : time.getHours()
      var mm = time.getMinutes() < 10 ? '0' + time.getMinutes() : time.getMinutes()
      var s = time.getSeconds() < 10 ? '0' + time.getSeconds() : time.getSeconds()
      return y + '-' + m + '-' + d + ' ' + h + ':' + mm + ':' + s
    }
    return ''
  },
  timeTwoFun(data) {
    if (!isNaN(data)) {
      var time = new Date(parseInt(data))
      var y = time.getFullYear()
      var m = time.getMonth() + 1 < 10 ? `0${time.getMonth() + 1}` : time.getMonth() + 1
      var d = time.getDate() < 10 ? '0' + time.getDate() : time.getDate()
      var h = time.getHours() < 10 ? '0' + time.getHours() : time.getHours()
      var mm = time.getMinutes() < 10 ? '0' + time.getMinutes() : time.getMinutes()
      var s = time.getSeconds() < 10 ? '0' + time.getSeconds() : time.getSeconds()
      return y + '-' + m + '-' + d + ' ' + h + ':' + mm + ':' + s
    }
    return ''
  },
  timeDayFun(data) {
    if (!isNaN(data)) {
      var time = new Date(parseInt(data))
      var y = time.getFullYear()
      var m = time.getMonth() + 1
      var d = time.getDate()
      return y + '-' + m + '-' + d
    }
    return ''
  },
  timeStamp(index) {
    if (index !== '') {
      const time = new Date(index).getTime()
      return time
    }
    return ''
  },
  // 到秒
  timeStampFun() {
    const time = Date.parse(new Date())
    return time
  },
  callweekDay(data) {
    const currentTime = this.timeStamp(data)
    let mondayData = ''
    let sundayData = ''
    if (data) {
      let timeNum = new Date(data).getDay()
      console.log('我是周几', timeNum)
      if (timeNum === 0) {
        timeNum = 7
      }
      console.log('我是周几2', timeNum)
      mondayData = this.timeDayFun(currentTime - 1000 * 60 * 60 * 24 * (timeNum - 1))
      sundayData = this.timeDayFun(currentTime + 1000 * 60 * 60 * 24 * (7 - timeNum))
    }
    console.log('我是周几3', mondayData, sundayData)
    return {
      mondayData,
      sundayData
    }
  },
  // 选择日期的格式
  selectData(data) {
    let startTime = ''
    let endTime = ''
    endTime = this.timeDayFun(this.timeStampFun() - 1000 * 60 * 60 * 24 * 1)
    if (data === 1) {
      startTime = this.timeDayFun(this.timeStampFun() - 1000 * 60 * 60 * 24 * 1)
    } else if (data === 7) {
      startTime = this.timeDayFun(this.timeStampFun() - 1000 * 60 * 60 * 24 * 7)
    } else if (data === 15) {
      startTime = this.timeDayFun(this.timeStampFun() - 1000 * 60 * 60 * 24 * 15)
    } else if (data === 30) {
      startTime = this.timeDayFun(this.timeStampFun() - 1000 * 60 * 60 * 24 * 30)
    }

    return {
      startTime,
      endTime
    }
  },
  // 分类数据组合
  classFun(data, res) {
    if (!res) {
      const arr = []
      data.forEach(element => {
        if (element.id !== '1') {
          const objOne = {}
          objOne.id = element.id
          objOne.label = element.name
          objOne.level = element.level
          if (element.hasOwnProperty('listTwo')) {
            if (element.listTwo.length !== 0) {
              objOne.children = []
              element.listTwo.forEach((elementTwo, i) => {
                const objTwo = {}
                objTwo.id = elementTwo.id
                objTwo.label = elementTwo.name
                objTwo.level = elementTwo.level
                objOne.children.push(objTwo)
                if (elementTwo.hasOwnProperty('listThree')) {
                  if (elementTwo.listThree.length !== 0) {
                    objOne.children[i].children = []
                    elementTwo.listThree.forEach((elementThree, index) => {
                      const objThree = {}
                      objThree.id = elementThree.id
                      objThree.label = elementThree.name
                      objThree.level = elementThree.level
                      objOne.children[i].children.push(objThree)
                    })
                  }
                }
              })
            }
          }
          arr.push(objOne)
        }
      })
      return arr
    } else {
      const arr = []
      arr.push({
        id: '1',
        label: '全部分类'
      })
      data.forEach(element => {
        const objOne = {}
        objOne.id = element.id
        objOne.label = element.name
        objOne.level = element.level
        if (element.hasOwnProperty('listTwo')) {
          if (element.listTwo.length !== 0) {
            objOne.children = []
            element.listTwo.forEach((elementTwo, i) => {
              const objTwo = {}
              objTwo.id = elementTwo.id
              objTwo.label = elementTwo.name
              objTwo.level = elementTwo.level
              objOne.children.push(objTwo)
              if (elementTwo.hasOwnProperty('listThree')) {
                if (elementTwo.listThree.length !== 0) {
                  objOne.children[i].children = []
                  elementTwo.listThree.forEach((elementThree, index) => {
                    const objThree = {}
                    objThree.id = elementThree.id
                    objThree.label = elementThree.name
                    objThree.level = elementThree.level
                    objOne.children[i].children.push(objThree)
                  })
                }
              }
            })
          }
        }
        arr.push(objOne)
      })
      return arr
    }
  },
  classAllFun(data, res) {
    if (!res) {
      const arr = []
      data.forEach(element => {
        const objOne = {}
        objOne.id = element.id
        objOne.label = element.name
        objOne.level = element.level
        if (element.hasOwnProperty('listTwo')) {
          if (element.listTwo.length !== 0) {
            objOne.children = []
            element.listTwo.forEach((elementTwo, i) => {
              const objTwo = {}
              objTwo.id = elementTwo.id
              objTwo.label = elementTwo.name
              objTwo.level = elementTwo.level
              objOne.children.push(objTwo)
              if (elementTwo.hasOwnProperty('listThree')) {
                if (elementTwo.listThree.length !== 0) {
                  objOne.children[i].children = []
                  elementTwo.listThree.forEach((elementThree, index) => {
                    const objThree = {}
                    objThree.id = elementThree.id
                    objThree.label = elementThree.name
                    objThree.level = elementThree.level
                    objOne.children[i].children.push(objThree)
                  })
                }
              }
            })
          }
        }
        arr.push(objOne)
      })
      return arr
    }
  },

  // 检验字符长度
  getByteLen(val) {
    var len = 0
    var len2 = 0
    var len3 = 0
    for (var i = 0; i < val.length; i++) {
      var patt = new RegExp(/[^\x00-\xff]/ig)
      var a = val[i]
      if (patt.test(a)) {
        len += 2
      } else {
        len2 += 1
      }
    }
    len3 = len + len2
    return {
      len,
      len2,
      len3
    }
  },
  sumAdd(data) {
    let num = 0
    data.forEach(element => {
      num += element * 1
    })
    console.log('最后的返回值', num.toFixed(2))
    return num.toFixed(2)
  },
  threeNumReplice(data) {
    const res = data + ''
    if (res) {
      var rf = parseFloat(res); if (isNaN(rf)) return false; var f = Math.round(res * 100) / 100; var s = f.toString(); var rs = s.indexOf('.'); if (rs < 0) { rs = s.length; s += '.' } while (s.length < (rs + 1) + 2) { s += '0' } // 每三位用一个逗号隔开
      var leftNum = s.split('.')[0]; var rightNum = '.' + s.split('.')[1]; var result // 定义数组记录截取后的价格
      var resultArray = []; if (leftNum.length > 3) {
        var ri = true; while (ri) { resultArray.push(leftNum.slice(-3)); leftNum = leftNum.slice(0, leftNum.length - 3); if (leftNum.length < 4) { ri = false } } // 由于从后向前截取，所以从最后一个开始遍历并存到一个新的数组，顺序调换
        var sortArray = []; for (var i = resultArray.length - 1; i >= 0; i--) { sortArray.push(resultArray[i]) } result = leftNum + ',' + sortArray.join(',') + rightNum
      } else { result = s } return result
    } else {
      return res
    }
  }
}
export default goodsUtilsFun
