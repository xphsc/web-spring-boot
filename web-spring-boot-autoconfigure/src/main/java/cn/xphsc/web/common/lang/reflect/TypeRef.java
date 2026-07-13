package cn.xphsc.web.common.lang.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: TypeRef
 * @since 2.0.4
 */
public abstract class TypeRef<T> {

    protected final Type type;

    protected TypeRef() {
        Type superClass = getClass().getGenericSuperclass();
        type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    public Type getType() {
        return type;
    }

}
