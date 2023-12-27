package cn.xphsc.web.dicttraslate.annotation;

import java.lang.annotation.*;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Dictionary transfer multiple attribute
 * For reference, the example
 * DictTransMapper(dictName="sex",dictTransField="sexName")
 * @since 1.0.0
 */
@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictTransMapper {


    /**
     * 字典转义标识符，对应数据库字典表中的name
     */
    String dictName();

    /**
     * 字典转义值存放在这个字段中，默认情况为翻译字段+"Name"
     */
    String dictTransField() default "";

    /**
     * 如果字典转义字段(标记的这个字段)是null的时候，使用此值作为缺省值填充
     */
    String nullValueName() default "-";

    /**
     * 字段多个字典code,需要英文逗号分隔进行切割。例如，使用此注解的字段值为1,2，
     * 在用的字典中有{1-男,2-女}这样的定义，当 multiple 为true时会现将1,2(String)拆分为[1,2]，然后翻译为"男,女",
     * 最终存放在dictTransField指明的字段值中.
     */
    boolean multiple() default false;

    /**
     * 字典值未定义的时候的默认值
     */
    String undefinedValue() default "";
}
