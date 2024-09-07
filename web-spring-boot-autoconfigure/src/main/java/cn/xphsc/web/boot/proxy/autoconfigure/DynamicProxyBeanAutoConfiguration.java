package cn.xphsc.web.boot.proxy.autoconfigure;


import cn.xphsc.web.boot.proxy.DynamicProxyBeanProperties;
import cn.xphsc.web.proxy.DynamicDefPostProcessor;
import cn.xphsc.web.proxy.dynamic.DefaultDynamicProxyFactory;
import cn.xphsc.web.proxy.dynamic.DynamicBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static cn.xphsc.web.common.WebBeanTemplate.ENABLED;
import static cn.xphsc.web.common.WebBeanTemplate.TRUE;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.1.8
 */
@Configuration
@ConditionalOnProperty(prefix = DynamicProxyBeanProperties.DYNAMIC_BEANPREFIX, name = ENABLED,  havingValue=TRUE , matchIfMissing = true)
@EnableConfigurationProperties({DynamicProxyBeanProperties.class})
public class DynamicProxyBeanAutoConfiguration {
    @Bean
    public DynamicDefPostProcessor dynamicDefPostProcessor() {
        return new DynamicDefPostProcessor();
    }
    @Bean
    public DynamicBeanPostProcessor dynamicBeanPostProcessor() {
        return new DynamicBeanPostProcessor();
    }

    @Bean
    public DefaultDynamicProxyFactory defaultDynamicProxyFactory() {
        return new DefaultDynamicProxyFactory();
    }
}
