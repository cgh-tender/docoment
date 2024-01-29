1. 交换机（Exchange）：用于接收生产者发送的消息并将其路由到一个或多个队列。交换机有四种类型：直连交换机、主题交换机、头交换机和扇形交换机。

2. 队列（Queue）：用于存储消息的容器。消费者从队列中获取消息并进行处理。

3. 虚拟主机（Virtual Host）：RabbitMQ 允许在一个物理服务器上创建多个虚拟主机，每个虚拟主机相当于一个独立的 RabbitMQ 服务器，拥有自己的交换机、队列和绑定等。

4. 连接（Connection）：生产者或消费者与 RabbitMQ 服务器之间的连接。

5. 通道（Channel）：在连接上创建的一个虚拟连接，用于发送和接收消息。一个连接可以有多个通道。

6. 路由键（Routing Key）：用于将消息从交换机路由到队列的规则。

关系

1. 生产者将消息发送到交换机，交换机根据路由键将消息路由到一个或多个队列。

2. 消费者从队列中获取消息并进行处理。

3. 虚拟主机可以将多个交换机、队列和绑定等逻辑上隔离开来，提高了系统的安全性和可靠性。

4. 连接是生产者或消费者与 RabbitMQ 服务器之间的通信通道，一个连接可以创建多个通道。

5. 通道是在连接上创建的虚拟连接，用于发送和接收消息。一个连接可以有多个通道。

6. 路由键是将消息从交换机路由到队列的规则，可以根据不同的路由键将消息路由到不同的队列。

ConnectionFactory

- host：RabbitMQ服务器的主机名，默认为localhost。
- port：RabbitMQ服务器的端口号，默认为5672。
- username：连接RabbitMQ服务器的用户名，默认为guest。
- password：连接RabbitMQ服务器的密码，默认为guest。
- virtualHost：连接RabbitMQ服务器的虚拟主机，默认为/。
- connectionTimeout：连接超时时间，默认为0（无限制）。
- requestedHeartbeat：请求的心跳超时时间，默认为0（无限制）。

Channel

1. basicPublish：用于将消息发送到指定的交换机和路由键。

   参数含义：
    - exchange：消息发送到的交换机名称
    - routingKey：消息发送到的队列名称
    - props：消息的属性
    - body：消息体
      作用：将消息发送到指定的交换机上，等待被消费者消费。

2. basicConsume：用于从指定队列中消费消息。

   参数含义：
    - queue：要消费的队列名称
    - autoAck：是否自动确认消息
    - callback：消费者接收到消息后的回调函数
      作用：订阅队列中的消息，等待被消费者消费。

3. basicAck：用于确认已经处理完毕的消息。

   参数含义：
    - deliveryTag：消息标签
    - multiple：是否批量确认
      作用：确认消息已被消费，告诉RabbitMQ可以删除该消息。

4. basicNack：用于拒绝处理某个消息，并可以选择是否重新将消息放回队列。

   参数含义：
    - deliveryTag：消息标签
    - multiple：是否批量拒绝
    - requeue：是否重新入队列
      作用：拒绝消息，并可选择是否重新入队列。

5. basicReject：用于拒绝处理某个消息，并可以选择是否重新将消息放回队列。


   参数含义：
    - deliveryTag：消息标签
    - requeue：是否重新入队列
      作用：拒绝消息，并重新入队列。

6. queueDeclare：用于声明一个队列。

   参数含义：
    - queue：队列名称
    - durable：是否持久化
    - exclusive：是否独占
    - autoDelete：是否自动删除
    - arguments：队列参数
      作用：声明队列，如果队列不存在则创建。

7. exchangeDeclare：用于声明一个交换机。

   参数含义：
   - exchange：交换机名称
   - type：交换机类型
   - durable：是否持久化
   - autoDelete：是否自动删除
   - internal：是否内部使用
   - arguments：交换机参数
   作用：声明交换机，如果交换机不存在则创建。

8. queueBind：用于将队列绑定到指定的交换机和路由键。

   参数含义：
    - queue：队列名称
    - exchange：交换机名称
    - routingKey：路由键
    - arguments：绑定参数
      作用：将队列绑定到交换机上，等待被消费者消费。

DefaultConsumer

1. handleDelivery：处理从RabbitMQ服务器接收到的消息。
 
   void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException;

   参数含义：
    - consumerTag：消费者标签，用于标识消费者。
    - envelope：消息的信封，包含了消息的元数据，如交换机、路由键等。
    - properties：消息的属性，包含了消息的元数据，如消息ID、消息类型等。
    - body：消息的内容，即消息体。
    
2. handleShutdownSignal：处理与RabbitMQ服务器的连接关闭信号。

   void handleShutdownSignal(String consumerTag, ShutdownSignalException sig)；

   参数含义：
    - consumerTag：消费者标签，用于标识消费者。
    - sig：关闭信号异常，包含了关闭的原因和异常信息。
    
3. handleConsumeOk：处理与RabbitMQ服务器的消费者注册成功信号。

   void handleConsumeOk(String consumerTag);

   参数含义：
    - consumerTag：消费者标签，用于标识消费者。
    
4. handleCancelOk：处理与RabbitMQ服务器的消费者取消注册成功信号。

   void handleCancelOk(String consumerTag);

   参数含义：
    - consumerTag：消费者标签，用于标识消费者。
