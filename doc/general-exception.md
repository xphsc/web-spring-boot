## 统一业务异常处理
#### 使用详情
1.在 springboot 默认的yml或者Properties文件中配置
 yml
```
web:
     exception:
       enabled: true
```
自定义异常
~~~
web:
 exception:
   exceptionClassName:
    -com.x.x.xxx exception
~~~
Properties
```
web.exception.enabled=true
