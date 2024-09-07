package cn.xphsc.web.boot.proxy;

import org.springframework.boot.context.properties.ConfigurationProperties;
import static cn.xphsc.web.boot.proxy.DynamicProxyBeanProperties.DYNAMIC_BEANPREFIX;
import static cn.xphsc.web.common.WebBeanTemplate.PROXY_PREFIX;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.1.8
 */
@ConfigurationProperties(prefix = DYNAMIC_BEANPREFIX)
public class DynamicProxyBeanProperties {
    public static final String DYNAMIC_BEANPREFIX = PROXY_PREFIX;
    public boolean enabled;
}
