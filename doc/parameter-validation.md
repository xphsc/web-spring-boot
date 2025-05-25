#### 使用详情
1.在 springboot 默认的yml或者Properties文件中配置
 yml
```
web:
  validation:
    enabled: true

```


Properties
```
web.threadpool.enabled=true

~~~
public class TestModelDTO {
   @Length(min = 5, max = 16, message = "长度为 5-16 位")
    private String name;}
@RestController
@RequestMapping("/")
@Validated
public class TestModelController {
  public Object save(@Valid @RequestBody TestModel testModel){}
}
~~~
##支持自定义注解
- @Chinese(是否中文验证) 
- @CollectNotNull(验证集合中是否有空元素) 
- @Date(时间验证) @IdCard(身份证)
- @Identification(身份证件（身份证、居民证、护照、回乡证、台胞证、港澳通行证等）) 
- @LettersOrNumber(是否数字or字母)
- @Matches(两个字段一致)
- @Money(金额)
- @Number(数字) 
- @Phone(手机号) 
- @PropertyScriptAssert(脚本验证)
