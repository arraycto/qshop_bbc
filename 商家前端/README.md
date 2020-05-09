

#### 项目简介
QSHOP-BBC基于当前流行技术组合的前后端分离商城系统： SpringBoot2+Jpa+MybatisPlus+SpringSecurity+jwt+redis+Vue的前后端分离的商城系统， 包含商城、拼团、砍价、商户管理、 秒杀、优惠券、积分、分销、会员等功能，更适合企业或个人二次开发；；

**开发文档**  【[查看文档]( https://code.aliyun.com/lq_b2c/node_frontend_h5/wikis/develop_wikis )】 

#### 体验地址

|     |   后台系统  |   前端(公众号)  |
|---  |--- | --- |
|   |  http://b2c-java-admin.leiqukeji.com  |H5:https://h5-b2c-java.leiqukeji.com 测试号：admin/123456,也可以自行注册 |
|    |  后台体验账号/密码：admin/123456   |  公众号:![输入图片说明](https://reslqmall.iyyou.com/wiki/wechat/qrcode_for_gh_2083a1bd3590_344.jpg "qrcode_for_gh_2083a1bd3590_344.jpg")   |


#### 项目源码

|     |  后台系统源码 |   后台系统前端源码  |
|---  |--- | --- |
|   阿里云  |  https://code.aliyun.com/lq_b2c/java_backend.git  | https://code.aliyun.com/lq_b2c/node_backend.git |

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
- lqb2c-api       移动端API模块
- lqb2c-mp        微信相关模块
- lqb2c-common    公共模块
- lqb2c-system    后台模块
- lqb2c-logging   日志模块
- lqb2c-tools     第三方工具模块
- lqb2c-generator 代码生成模块
- lqb2c-shop      商城模块

#### 系统预览
<table>
    <tr>
        <td><img src="https://images.gitee.com/uploads/images/2019/1211/161553_20c039ff_477893.jpeg"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2019/1129/234538_62ba99b7_477893.jpeg"/></td>
    </tr>
    <tr>
        <td><img src="https://images.gitee.com/uploads/images/2019/1129/234601_7fb028a6_477893.jpeg"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2019/1129/234622_6f593729_477893.jpeg"/></td>
    </tr>
    <tr>
        <td><img src="https://images.gitee.com/uploads/images/2019/1130/114845_9ed3c82c_477893.jpeg"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2019/1129/234703_49e8fe4f_477893.jpeg"/></td>
    </tr>
</table>

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
	* 1.18 Rocketmq
        
* 前端使用技术
    * 2.1 Vue 全家桶
    * 2.2 Element
	* 2.3 mpvue

#### 项目发布明细

- QSHOP-BBC1.0版本
- 1.1版本新增积分与优惠券抵扣
- 1.2版本分销功能已经发布
- 1.2.1增加了未付款订单取消功能库存销量退出、优惠券、积分功能，个人中心增加了积分流水
- 1.3版本新增拼团功能，已经发布
- 1.3.1版本手机端新增商户管理、后台新增统计
- 1.3.2新增后台微信相关及其支付配置，新增自动回复配置
- 1.3.3新增 后台微信图文发送功能，小程序配置，增加小程序授权等,修复一些bug等
- QSHOP-BBC2.0版本发布，更新如下：
  - 1.1、新增积分签到
  - 1.2、新增会员等级、任务等功能，新增会员价格等
  - 1.3、修复Redisson linux系统启动报错问题
  - 1.4、修复商户简单权限功能
  - 1.5、修复加入购物车覆盖问题
  - 1.6、修复拼团出现undefined
  - 1.7、会员后台新增余额调整
  - 1.8、修复新增配置数据有时候不成功问题等
- 2.0.1个人中心新增账单流水
- QSHOP-BBC2.0.2 发布更新如下：
   - 1.商品新增多图评价
   - 2.订单新增快递查询
- 2.0.3版本，后台图标更新,后台模块重新拆分,物流快递单独管理,导出最新sql
- 2.0.4版本，新增模板消息通知、H5端商家管理发货修改及其列表时间显示修复
- QSHOP-BBC3.0发布更新如下：
- 1、优化代码结构与名称，修改get请求参数其统一继承分页参数
- 2、新增redis监听未付款30分钟取消功能与7天自动收货功能，mq队列作为备选(注释掉)
- 3、新增门店到店核销功能
- 4、新增分销全局开关#I19HB1
- 5、积分新增消费限制#I19TUR
- 6、新增充值功能#I18V5D
- 7、后台菜单调整，新增财务模块
- 8、优化后台配置赋值写法
- 9、修复管理后台新增表单之后如果直接再新增数据导致默认数据缺失问题#I1AFBK
- 10、修复提交购物车可能查询多条数据的问题
- 11、后台登陆背景图固定#I1A0LS
- 12、增加常量与枚举优化硬编码问题
- 13、微信支付、公众号、模板消息修改
- 14、修复代码生成器不全的问题#I1AIO4
- 15、修复营销产品拼团等轮播图不能修改的问题#I1AHXR
- 17、修复退货理由文字错误#I1AQ7D
- 18、修复新增分类的图片自动显示上次一次的图片#I1AQBK  
- 19、新增微信jssdk接口返回所需js权限
- 20、新增余额充值变动模板消息通知
- 21、首页数据缓存优化
- 22、新增未支付订单显示到期时间
- 23、修复加入购物车购买后，后台订单中的商品信息数据重复#I1AXNX
- 24、修复后台-管理商品-规格属性 属性无法删除问题#I1AYL2
- 25、修复公众号商品直接微信分享标题不显示的问题#I1AX0R

	
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
