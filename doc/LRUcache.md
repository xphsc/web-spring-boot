## 简介
`LRUcache` 用于提供易于配置和使用的 LRU (Least Recently Used) 缓存机制。这个启动器通过简单的配置，允许开发者在 Spring Boot 应用中快速集成和使用高效的缓存策略。

## 特性
- **最近最少使用（LRU）缓存策略**: 自动移除长时间未使用的缓存项，确保缓存的高效利用。
- **易于配置**: 通过 Spring Boot 的配置文件进行配置。
- **过期策略**: 支持基于访问时间和写入时间的自动过期。
- **事件监听**: 可以添加监听器来监控缓存项的添加和移除。
- **缓存统计**: 提供缓存命中率和缓存项数量等统计信息。

## 快速开始
1**配置属性**: 在你的 `application.properties` 或 `application.yml` 中配置缓存的属性。
application.yml 示例:
   ```yaml
   web:
     cache:
      capacity: 10
      expireAfterAccess: 100
      expireAfterWrite: 9999
  ```
2. **使用 LRUCache**: 在你的 Spring Boot 应用中注入 `LRUCache` 实例并使用。

   Java 示例:

   ```java
   @Autowired
   private LRUCache<String, Objiect> cache;
   
   public void useCache() {
        cache.put("key1", "1");
        String cachedObject = cache.get("key1");
   
   }
  ```