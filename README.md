<p align="center">
	<a href="https://hutool.cn/"><img src="image/logo.png" width="45%"></a>
</p>
<p align="center">
	<strong>🍬 web Spring Boot低耦合集成的web高度扩展组件.</strong>
</p>

## 简介
web Spring Boot` web Spring Boot低耦合集成的web高度扩展组件
* cors（跨域配置）、crypto（参数以及加解密）、csrf（csrf攻击）、dict(字典转义)、exception(通用异常)、i18n、 jdk8time 、
* log(应用日志)、response(通用结果返回)、sensitive(数据脱敏) 、event消息推送 、 sqlInjection(SQL注入)
* apiversion（api 版本) 、threadpool(通用线程配置) 、validation(参数验证)、 xss(xss过滤) rest(调用)、 envencrypt（配置文件加密）
* tree（注解树构造）、strategy（策略模式）、logback log4j2-sensitive sensitive（日志脱敏） 、 通用工具
* LRU cache(缓存) 、statemachine(状态机)

[![JDK](https://img.shields.io/badge/JDK-1.8+-green.svg)]
[![Maven central](https://img.shields.io/maven-central/v/cn.xphsc.boot/web-spring-boot-starter.svg))]
[![APACHE 2 License](https://img.shields.io/badge/license-Apache2-blue.svg?style=flat)](LICENSE)
## 特性
- **极简设计**: 采用直观的API设计，上手即用，无需繁琐配置
- **高性能**: 通过 Spring Boot 的配置文件进行配置。
- **极少依赖**: 不依赖任何外部库，确保最小化引入的复杂度，只依赖于jsonp。
- **灵活扩展**:灵活扩展以及配置
- **全面兼容**: 无缝集成Java项目，无论是Spring Boot 以及 spring boot3 应用还是原生Java应用均能完美适配。

#### 安装教程
~~~
 <dependency>
 <groupId>cn.xphsc.boot</groupId>
    <artifactId>web-spring-boot-starter</artifactId>
    <version>2.0.2</version>
 </dependency>
~~~
spring boot3以上版本
~~~
 <dependency>
 <groupId>cn.xphsc.boot</groupId>
    <artifactId>web-spring-boot-starter</artifactId>
    <version>3.0.1</version>
 </dependency>
~~~
#### 包含组件
| 组件        | 介绍                                       |
|-----------|------------------------------------------|
| cors      | spring  boot  跨域配置                       |
| crypto    | 参数以及加解密                                  |
| csrf      | 过滤csrf攻击                                 |
| dict      | 枚举类型字符串字典转义                              |
| exception | 自定义通用异常                                  |
| i18n      | 自定义国际化                                   |
| jdk8time  | 自定义jdk8以上序列化日期                           |
| log       | 自定义操作应用日志                                |
| response                          | 自定义通用结果返回                                |
| sensitive                         | 自定义通用 数据脱敏                               |
| event                             | 自定义消息事件推送                                |
| sqlInjection                      | 过滤SQL注入                                  |
| apiversion                        | 自动添加api 版本                               |
| threadpool                        | 自定义通用线程配置                                |
| validation                        | 自定义参数验证                                  |
| xss                               | xss过滤                                    |
| rest                              | rest 调用                                  |
| envencrypt                        | 配置文件加密                                   |
| tree                              | 通过注解构造树                                  |
| strategy                          | 自定义策略模式                                  |
| logbacklog4j2-sensitive| 日志脱敏                                     |
| common tools                      | 基于Java的 实现的common long  以及工具             |
| LRUcache                   | 供易于配置和使用的 LRU (Least Recently Used) 缓存机制 |
| statemachine                  | 极简的状态机实现以及使用                             |



#### 使用说明

1. [cors](doc/cors.md)
2. [crypto](doc/crypto.md)
3. [csrf](doc/csrf.md)
4. [字典转义](doc/dictionaries-translation.md)
5. [通用异常](doc/general-exception.md)
6. [应用日志](doc/application-log.md)
7. [通用结果返回](doc/general-result-return.md)
8. [数据脱敏](doc/data-desensitization.md)
9. [SQL注入](doc/SQL-injection.md)
10. [通用线程配置](doc/general-thread-configuration.md)
11. [参数验证](doc/parameter-validation.md)
12. [xss过滤](doc/XSS-filtering.md)
13. [rest](doc/rest.md)
14. [event](doc/event.md)
15. [strategy](doc/strategy.md)
16. [logbackandlog4j2-sensitive](doc/logbackandlog4j2-sensitive.md)
17. [apiversion](doc/apiversion.md)
18. [LRUcache](doc/LRUcache.md)
19. [statemachine](doc/statemachine.md)

