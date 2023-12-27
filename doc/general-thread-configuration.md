#### 使用详情
1.在 springboot 默认的yml或者Properties文件中配置
 yml
```
web:
 threadpool: true
 #核心线程大小 默认Runtime.getRuntime().availableProcessors()
 corePoolSize :
 #最大线程数 默认Runtime.getRuntime().availableProcessors()
 maximumPoolSize:
 #空闲线程等待工作的超时时间 默认0L
 keepAliveTime :
 #超时时间单位 默认
 timeUnit: milliseconds
 queueSize: 10000;
  #在执行饱和或关闭时调用处理策略
 rejectStrategy:
  #在执行饱和或关闭时调用处理策略,
  rejectStrategyBeanName:
 #线程工厂对应的bean的名称
 threadFactoryBeanName:

```
Properties
```
web.threadpool=true
