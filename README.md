# eagleeye-parent

#鹰眼-父级工程

以一个审批场景为例，实现服务间的一些调用以及集成一些常用实现

技术点：
1. 内部服务调用采用 oauth2.0 客户端授权（封装openFeign, 静默授权）
2. 服务回调通知实现（ 回调通知频率 15s/15s/30s/3m/10m/20m/30m/30m/30m/60m/3h/3h/3h/6h/6h）
3. 动态定时任务 quartz 及持久化
4. 封装服务bridge sdk，实现 sdk 授权验证
5. RestTemplate 调用服务注册中心实例，实现负载均衡
6. rabbitmq 消息可靠投递

## 模块

- eagleeye-auth 用户授权服务

- eagleeye-bridge 预审服务

- eagleeye-bridge-sdk 预审服务 sdk

- eagleeye-common 预审服务通用 module

- eagleeye-management 预审中心管理服务


## 开发环境服务启动

1. 启动 [consul](https://www.consul.io/) 注册中心
> consul agent -dev

2. 启动 对应的 application 主程序


