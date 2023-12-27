package cn.xphsc.web.validation.constraints;


import cn.xphsc.web.validation.validator.InEnumValidator;
import cn.xphsc.web.validation.valuable.IntArrayValuable;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;



/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 枚举
 * For reference, the example
 * public enum UserTypeEnum implements IntArrayValuable {
 * MEMBER(1, "会员"),
 * ADMIN(2, "管理员");
 * public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(UserTypeEnum::getValue).toArray();
 * private final Integer value;
 * private final String name;
 * public int[] array() {return ARRAYS;}
 * @since 1.0.0
 */
@Target({
        ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER,
        ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = InEnumValidator.class
)
public @interface InEnum {

    /**
     * 实现 EnumValuable 接口的
     */
    Class<? extends IntArrayValuable> value();

    String message() default "必须在指定范围 {value}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
