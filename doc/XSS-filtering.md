#### 使用详情
1.在 springboot 默认的yml或者Properties文件中配置
 yml
```
web:
  xss:
    enabled: true
 #路径 默认匹配所有
    urlPatterns："/*";
    #排除路径
    excludes："";
    #是否过滤关键字 默认false返回xss注入异常
    parameterEabled：
```
Properties
```
web.xss.enabled=true