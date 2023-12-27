** rest 调用**
## 使用详情
~~~
web:
  rest:
    enabled: true
  @Autowired
   private  RestBuilderTemplate restBuilderTemplate;
负载均衡
 使用RestBuilderTemplate默认配置了负载均衡
 使用RestTemplate 需配置
    @Bean
       @LoadBalanced
       public RestTemplate restTemplate(){
           return restTemplate;
       }
 web:
   loadbalancer:
         enabled: true
         services:
           tmall:
             server-list:
               - url: www.tmall.com
               - url: www.baidu.com
~~~