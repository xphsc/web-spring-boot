### 简介
* 通过@ApiVersion注释，多版本api，支持uri、header、param。
## 特性
- **支持url**: URI:  /v1/test
- **支持header**: - Header: /test, header: API-VERSION=1
- **支持Param**: Param:  /test?api_version=1

## 快速开始
1**配置属性**: 在你的 `application.properties` 或 `application.yml` 中配置缓存的属性。
application.yml 示例:
   ```yaml
   web:
     apiversion:
       enabled: true
  ```
2. **使用 apiversion**: 在你的 Sspring boot 2.6-2.7 配置示例  RequestMappingPathPatternParserHandler实例并使用(3.0.x默认)。
```
@Component
 public class ApiVersionRequestMappingPathPatternParserHandler implements RequestMappingPathPatternParserHandler{
  @Override
 public RequestMappingInfo.BuilderConfiguration patternParser(RequestMappingHandlerMapping requestMappingHandlerMapping) {
RequestMappingInfo.BuilderConfiguration builderConfiguration=new RequestMappingInfo.BuilderConfiguration();
builderConfiguration.setPatternParser(requestMappingHandlerMapping.getPatternParser());
 return builderConfiguration;}}
```
3. Controller 使用示例
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