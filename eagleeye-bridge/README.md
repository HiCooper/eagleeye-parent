# AI 任务处理桥接服务


任务处理服务 与 算法的唯一接口

仅负责投递消息至算法处理，并处理算法结果，提供结果查询，已经结果回调通知


> MQ 使用的 rabbit-mq

## 1. 提供了如下服务

- [x] 提交任务处理
- [x] 任务处理状态查询
- [x] 任务处理结果查询
- [x] 任务处理回调通知

##2. 注意

eagleeye-bridge 自身会维护一个 `记录状态表`，算法将处理状态 发送到 `mq`，本服务会将记录状态 持久化到 `记录状态表`

## 3. 支持模式

- [x] 完整消息提交任务处理
- [ ] 分片消息提交任务处理 (二期支持)

### 完整消息提交任务处理
> 特点：一次性提交所有参数，流程简单

提交任务处理的参数，包含 该办件 所需要的 所有信息

### 分片消息提交任务处理
> 特点：对于算法耗时处理的步骤，提前进行，加快速度

1. 可多次提交图片，进行分类，信息提取
2. 补充剩余信息，开始任务处理
> 分段提交结果 和 剩余信息整合 由 本服务负责 


## 特点
1. 调用内部服务使用 oauth2 客户端验证
