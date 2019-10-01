# MessengerDemo
IPC方式Messenger的使用
## 参考网站：https://blog.csdn.net/lmj623565791/article/details/47017485
### 结构：
demo:客户端  demo_2:服务端
### 优点：
功能一般，支持一对多串行通信，支持实时通信。
### 缺点：
不能很好处理高并发情形，不支持RPC，数据通过Message进行传输，因此只能传输Bundle支持的数据类型
### 适用场景：
低并发的一对多即时通信，无RPC需求，或者无须返回结果的RPC需求

