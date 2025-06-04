## 简介
专为追求极致性能与简洁设计的开发者而生。它摒弃了复杂的配置与依赖，专注于提供一个轻量级、无状态且高度可扩展的状态机解决方案
## 特性
- **极简设计**: 自动移除长时间未使用的缓存项，确保缓存的高效利用。
- **高性能**: 通过 Spring Boot 的配置文件进行配置。
- **零依赖**: 支持基于访问时间和写入时间的自动过期。
- **灵活扩展**: 可以添加监听器来监控缓存项的添加和移除。
- **全面兼容**: 提供缓存命中率和缓存项数量等统计信息。

## 快速开始
1**使用 statemachine**: 在你的 Spring Boot 应用中 `statemachine` 实例并使用。
1.1 创建枚举
~~~~
    public   enum WarehouseState {
        EMPTY, FULL
    }

    /**
     * 定义库位事件：取走，放入
     */
    public  enum WarehouseEvent {
        TAKE_AWAY, PUT_IN
    }
~~~~
1.2 使用状态机
~~~
StateMachineBuilder<WarehouseState, StateMachineImplTest.WarehouseEvent, Object> fsmBuilder = StateMachineBuilderFactory.create("库位状态机");
        fsmBuilder.transition()
                .from(WarehouseState.EMPTY)
                .to(WarehouseState.FULL)
                .on(WarehouseEvent.PUT_IN)
                .when(c -> c != null)
                .then((start, end, event, context) -> {
                    if(!context.equals("context")) {
                        System.out.println(String.format("sourceState:%s，targetState：%s,event:%s,context:%s", start, end, event, context));
                    }
                });

        StateMachine<WarehouseState, WarehouseEvent, Object> stateMachine = fsmBuilder.build();
       WarehouseState S1 = stateMachine.fire(WarehouseState.EMPTY, WarehouseEvent.PUT_IN, "context");
        Assertions.assertTrue(S1 == WarehouseState.FULL);
~~~