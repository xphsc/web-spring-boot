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