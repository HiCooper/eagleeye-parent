# 消息队列说明

定义了两个默认队列

1. 消息发送担保队列 `coffeeBabyQueue` `routing_key = coffee.default`
> 此队列为 Java 投递担保队列，如果指定 routing_key 没有成功投递到目标队列，那么消息会被放置在此队列

2. 算法结果接收队列 `mustangQueue` `routing_key = mustang.#`
> 此队列为 Java 消费的默认队列，能接收 模式为 `mustang.#` 的消息

3. 处理状态通知队列 `noticeStatusQueue` `routing_key = notice.status`
> 此队列 接受记录处理状态 通知