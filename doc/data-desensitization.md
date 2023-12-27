> 基于springboot的数据脱敏
## 如何使用
1.在 springboot 默认的yml或者Properties文件中配置
```
web:
 sensitive: true

```
Properties
```
web.sensitive=true
```


## 默认使用jackson脱敏,基于jackson的JsonSerialize实现
```java
@Data
public class UserSensitive {

	@Sensitive(value = SensitiveType.CHINESE_NAME)
	String name = "张三";

	@Sensitive(value = SensitiveType.ID_CARD)
	String idCard = "363302189001092898";
	

	@Sensitive(value = SensitiveType.MOBILE_PHONE)
	String phone = "1234567890";

	@Sensitive(value = SensitiveType.FIXED_PHONE)
	String ext = "0791-8888888";

	@Sensitive(value = SensitiveType.ADDRESS)
	String address = "江西南昌";
	@Sensitive(value = SensitiveType.BANK_CARD)
	String bankCard = "62225000002996298837";
	
	@SensitiveInfo(value = SensitiveType.NULL)
	Integer id = 56789;
}
```

### 方法调用输出
```
@SpringBootTest
public class ApplicationTests {

    /**
     * jackson脱敏测试
     */
    @Test
    void testSensitive() {
        UserSensitive user = new UserSensitive();
        ObjectMapper objectMapper = new ObjectMapper();
        String str = objectMapper.writeValueAsString(user);
        System.out.println(str);
    }
    
}
```


## 自定义脱敏函数
```java
@Service
public class SensitiveServiceImpl extends SensitiveHandler {

   @Override
   public String idCardNumber(String idCardNumber, int front, int end) {
       return super.idCardNumber(idCardNumber, front, end);
   }

   @Override
   public String customJacksonHandler(String input, SensitiveInfo param) {
       return "JacksonHandler:" + input;
   }
}
```
