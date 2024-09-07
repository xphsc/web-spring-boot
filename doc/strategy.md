## 简介
 策略模式是一种行为设计模式，在运行时根据参数选择对应的算法。 程序并不是实现一个算法，而是在运行时根据参数在一组程序中选择对应的算法。 策略模式在复杂系统中非常常见。

#### 使用详情
1.在 springboot 默认的yml或者Properties文件中配置
默认开启
关闭：web.strategy.enabled=false
### 策略模式使用
1.新建测试策略枚举类和策略业务Interface
~~~
public enum TestStrategyEnum implements ContextStrategyStruct<TestStrategyEnum,IBusinessInterface> {
  A,
  B,
  C;

  @Override
  public TestStrategyEnum getEnum() {
    return this;
  }

  @Override
  public Class<IBusinessInterface> getType() {
    return IBusinessInterface.class;
  }
  #  业务Interface
  public interface IBusinessInterface extends ContextStrategyConstraint<TestStrategyEnum> {

  String echo();
}
~~~
2.新建测试策略枚举实现类
~~~
# 业务枚举a
@Service
public class AStrategyImpl implements IBusinessInterface{
  @Override
  public TestStrategyEnum strategy() {
    return TestStrategyEnum.A;
  }

  @Override
  public String echo() {
    return "业务枚举a";
  }
  # 业务枚举b
@Service
public class BStrategyImpl implements IBusinessInterface{
  @Override
  public TestStrategyEnum strategy() {
    return TestStrategyEnum.B;
  }

  @Override
  public String echo() {
    return "业务枚举b";
  }
}
 # 业务枚举c
@Service
public class CStrategyImpl implements IBusinessInterface{
  @Override
  public TestStrategyEnum strategy() {
    return TestStrategyEnum.C;
  }

  @Override
  public String echo() {
    return "业务枚举c";
  }
}
~~~
3.新建测试策略枚举测试
~~~
public class StrategyTest {
    @Autowired
    private IContextStrategy iContextStrategy;
    @Test
    public void strategy() {
        final IBusinessInterface iBusinessInterface = iContextStrategy.distribute(
                TestStrategyEnum.A, IBusinessInterface.class);
        System.out.println(iBusinessInterface.echo());
        final IBusinessInterface iBusinessInterface1 = iContextStrategy.distribute(TestStrategyEnum.B,
                IBusinessInterface.class);
        System.out.println(iBusinessInterface1.echo());
        final IBusinessInterface iBusinessInterface2 = iContextStrategy.distribute(TestStrategyEnum.C,
                IBusinessInterface.class);
        System.out.println(iBusinessInterface2.echo());
    }
~~~