package cn.xphsc.web.dicttraslate.annotation;

import cn.xphsc.web.dicttraslate.handler.DictTransHandler;

import java.lang.annotation.*;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Dictionary transfer map
 *  @see  DictTransMapper
 * @see  DictTransHandler
 * @since 1.0.0
 */
@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictTransMap {
    /**
     *Dictionary transfer multiple attribute
     */
    DictTransMapper[] value();

    /**
     * Dictionary escape Handler
     */
    Class<? extends DictTransHandler> dictTransHandler() ;
}
