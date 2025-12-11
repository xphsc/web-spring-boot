#### 使用详情
1.在 springboot 默认的yml或者Properties文件中配置
 yml
```
web:
 validation
 enabled: true

```
Properties
```
web.validation.enabled=true
在类或者方法上
~~~
 @RestController
 @RequestMapping("/")
   @ResponseResult
   public class TestModelController {}
~~~
## 自定义Response结果类
~~~
 @ResponseResult(responseClass=Result.class,responseOk = "ok")
   public class TestModelController {}
~~~
##   全局配置   注解大于配置（2.0.4以及3.0.3以上）
web:
 validation
   enabled: true
   defaultClass： //默认  Response
   defaultOkMethod = "ok";  //默认  ok
