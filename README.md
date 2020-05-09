<h1 style="text-align: center">QSHOP BBC商城系统</h1>


#### 项目简介
QSHOP BBC基于当前流行技术组合的前后端分离商城系统： SpringBoot2+Jpa+MybatisPlus+SpringSecurity+jwt+redis+Vue的前后端分离的商城系统， 包含商城、拼团、砍价、商户管理、 秒杀、优惠券、积分、分销、会员等功能，更适合企业或个人二次开发；；

**开发文档**  【[查看文档]( https://www.qshop.vip )】 

#### 体验地址

|     |   商家后台系统  |   平台后台系统  |   前端(公众号)  |
|---  |--- | --- | --- |
|   |  http://qshop-seller.iyyou.com  |  http://qshop-admin.iyyou.com   |H5:http://qshop-h5.iyyou.com 测试号：leiqu/leiqu123,也可以自行注册 |
|    |  后台体验账号/密码：admin/123456  |  后台体验账号/密码：admin/123456   |  公众号:![输入图片说明](https://images.gitee.com/uploads/images/2020/0508/151843_6e2a9f7c_7538456.jpeg "qrcode_for_gh_2083a1bd3590_344.jpg")   |


#### 项目源码

|     |  商家后台系统源码 |   商家后台系统前端源码  |
|---  |--- | --- |
|   阿里云  |  待更新  | 待更新 |

|     |  平台后台系统源码 |   平台后台系统前端源码  |
|---  |--- | --- |
|   阿里云  |  待更新  | 待更新 |

|     |  前台系统源码  |   其他源码  | 
|---  |--- | --- |
|   阿里云  |  待更新  |  待更新  |

## 商城功能

* 一：商品模块：商品添加、规格设置，商品上下架等
* 二：订单模块：下单、购物车、支付，发货、收货、评价、退款等
* 三：营销模块：积分、优惠券、分销、砍价、拼团、秒杀(、到店核销等
* 四：微信模块：自定义菜单、自动回复、微信授权、图文管理、模板消息推送
* 五：配置模块：各种配置
* 六：用户模块：登陆、注册、会员卡等
* 七：其他等
       

####  已经完成功能
- 可以具体查看演示地址查看当前版本已经完成的功能，不再絮叨啦

#### 项目结构
项目采用分模块开发方式
- qshop-api       公众号(H5)API模块
- qshop-mp        微信相关模块
- qshop-common    公共模块
- qshop-system    后台模块
- qshop-logging   日志模块
- qshop-tools     第三方工具模块
- qshop-generator 代码生成模块
- qshop-shop      商城模块

#### 系统预览
![image](https://images.gitee.com/uploads/images/2020/0508/150409_b7cdbe83_7538456.png)
![image](https://images.gitee.com/uploads/images/2020/0508/150409_b7cdbe83_7538456.png)
![image](https://images.gitee.com/uploads/images/2020/0508/150139_0d7a3518_7538456.png)
![image](https://images.gitee.com/uploads/images/2020/0508/150213_cf2b3a90_7538456.png)
![image](https://images.gitee.com/uploads/images/2020/0508/150213_cf2b3a90_7538456.png)
![image](https://images.gitee.com/uploads/images/2020/0508/150409_b7cdbe83_7538456.png)
![image](https://images.gitee.com/uploads/images/2020/0508/150436_6df7bbe6_7538456.png)
![image](https://images.gitee.com/uploads/images/2020/0508/150506_0c2b48ee_7538456.png)
![image](https://images.gitee.com/uploads/images/2020/0508/150535_3fb4185e_7538456.png)

## 技术选型
* 1 后端使用技术
    * 1.1 SpringBoot2
    * 1.2 mybatis、MyBatis-Plus
    * 1.3 SpringSecurity
    * 1.4 JPA
    * 1.5 Druid
    * 1.6 Slf4j
    * 1.7 Fastjson
    * 1.8 JWT
    * 1.9 Redis
    * 1.10 Quartz
    * 1.11 Mysql
    * 1.12 swagger
    * 1.13 WxJava
    * 1.14 Lombok
    * 1.15 Hutool
    * 1.16 Mapstruct
  * 1.17 Redisson
        
* 前端使用技术
    * 2.1 Vue 全家桶
    * 2.2 Element

#### 项目发布明细

- 1.0版本
- 1.1版本新增积分与优惠券抵扣
- 1.2版本分销功能已经发布
- 1.2.1增加了未付款订单取消功能库存销量退出、优惠券、积分功能，个人中心增加了积分流水
- 1.3版本新增拼团功能，已经发布
- 1.3.1版本手机端新增商户管理、后台新增统计
- 1.3.2新增后台微信相关及其支付配置，新增自动回复配置
- 1.3.3新增 后台微信图文发送功能，小程序配置，增加小程序授权等,修复一些bug等
- QSHOP BBC2.0版本发布，更新如下：
  - 1.1、新增积分签到
  - 1.2、新增会员等级、任务等功能，新增会员价格等
  - 1.3、修复Redisson linux系统启动报错问题
  - 1.4、修复商户简单权限功能
  - 1.5、修复加入购物车覆盖问题
  - 1.6、修复拼团出现undefined
  - 1.7、会员后台新增余额调整
  - 1.8、修复新增配置数据有时候不成功问题等
- 2.0.1个人中心新增账单流水
- QSHOP BBC2.0.2 发布更新如下：
   - 1.商品新增多图评价
   - 2.订单新增快递查询
- 2.0.3版本，后台图标更新,后台模块重新拆分,物流快递单独管理,导出最新sql
- 2.0.4版本，新增模板消息通知、H5端商家管理发货修改及其列表时间显示修复

  
#### 反馈交流
- QQ交流群：1051036630
- 喜欢这个商城后台的小伙伴留下你的小星星啦,star,star哦！

####  特别鸣谢
- eladmin:https://github.com/elunez/eladmin
- mybaitsplus:https://github.com/baomidou/mybatis-plus
- hutool:https://github.com/looly/hutool
- wxjava:https://github.com/Wechat-Group/WxJava
- vue:https://github.com/vuejs/vue
- element:https://github.com/ElemeFE/element
