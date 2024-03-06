### 简介
* 通过@ApiVersion注释，多版本api，支持uri、header、param。

## 
- URI:  /v1/test
- Header: /test, header: API-VERSION=1
- Param:  /test?api_version=1



## 使用详情
1.在 springboot 默认的yml或者Properties文件中配置
默认开启yml
```
web:
 apiversion:
 enabled: true
```
Properties
```
web.apiversion.enabled=true
``` 
2. spring boot 2.6-2.7 配置实训  RequestMappingPathPatternParserHandler
```
@Component
 public class ApiVersionRequestMappingPathPatternParserHandler implements RequestMappingPathPatternParserHandler{
  @Override
 public RequestMappingInfo.BuilderConfiguration patternParser(RequestMappingHandlerMapping requestMappingHandlerMapping) {
RequestMappingInfo.BuilderConfiguration builderConfiguration=new RequestMappingInfo.BuilderConfiguration();
builderConfiguration.setPatternParser(requestMappingHandlerMapping.getPatternParser());
 return builderConfiguration;}}
```

4. Controller
```
    ```
    @RestController
    @RequestMapping("/")
    @ApiVersion("1")
    public class testController {
    
        @GetMapping("/test")
        public String test() {
            return "hello;
        }
   
    }
```


```