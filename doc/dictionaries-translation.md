> 在常见的web应用中，常见的数据库字段会使用字典值，但是在数据查询时，我们需要将存储的字典值转换成对应的字典标签(value>>name)。常见的转换方式为从数据库查询、逻辑包装等。

#### 使用详情
1.在 springboot 默认的yml或者Properties文件中配置
 yml
```
4.1 在类或者方法上 以下例子仅供参考
~~~~
     model
public class TestDictDTO {
    @DictField(dictName = "sex")
    private  int sex;
    private String sexName;
    controller
   @DictTranslation(dictTransHandler =TestDictHandler.class)
   public class TestModelController {}
    @DictTranslation(dictTransHandler =TestDictHandler.class)
      public  Response   test()  {}
```
4.2 支持枚举(2.0.4和3.0.4以上)  以下例子仅供参考
~~~~
     model
public class TestDictDTO {
    @DictField(dictName = "sex")
    private  int sex;
    private String sexName;
     @DictField(dictName = "user_status")  // 关联字典名称
    private UserDictStatus status;  
    controller
   public class TestModelController {}
    @DictTranslation(dictTransHandler =TestDictHandler.class)
      public  Response   test()  {}
@Component
public class UserDictTransHandler implements DictTransHandler {

    @Override
    public Map<String, String> dictTransByName(String dictName) {
        if ("user_status".equals(dictName)) {
            Map<String, String> dict = new HashMap<>();
            dict.put("ACTIVE", "激活");
            dict.put("INACTIVE", "未激活");
            dict.put("LOCKED", "锁定");
            return dict;
        }
        return Collections.emptyMap();
    }
}

}  
      
```
Properties
```
web.dict=true
```
使用其他管控方式的项目可以到中央仓库中搜索坐标查看配置方式*

2. 第二步，需要**实现DictTransHandler接口**，并将这个对象交给Spring 容器，否则扩展将不会自动生效

```
java
   @Component
public class TestDictHandler implements DictTransHandler {
    @Override
    public Map<String, String> dictTransByName(String dictName) {
        Map<String,String> map=new HashMap<>();
        switch (dictName) {
            case "sex":
                map.put("1", "男");
                map.put("2", "女");
                break;
        }
        return map;
    }
}
  
```

