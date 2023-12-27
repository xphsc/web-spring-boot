** event消息推送 调用**
## 使用详情
~~~
web:
  event:
    enabled: true
~~~
###发送消息
~~~
类注入
  @Autowired
  private EventPublisher eventPublisher;

   @Test
      public void test() {
          eventPublisher.publishEvent(TEST_EVENT_SERVICE, "【test-发送消息】");
      }
~~~

###接收消息
  @EventListener(name = "TEST_EVENT_SERVICE")
     public void listLocalSync(String message) {
         System.out.println("收到Sync：" + message);
     }

     @EventListener(name = "TEST_EVENT_SERVICE", eventType = EventType.SYNC)
     public void listLocalAsync(String message) {
         System.out.println("收到Async：" + message);
     }
~~~