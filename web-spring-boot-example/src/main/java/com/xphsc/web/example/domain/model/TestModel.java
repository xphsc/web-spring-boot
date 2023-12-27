package com.xphsc.web.example.domain.model;

import cn.xphsc.web.dicttraslate.annotation.DictField;
import cn.xphsc.web.log.annotation.LogField;
import cn.xphsc.web.sensitive.SensitiveType;
import cn.xphsc.web.sensitive.annotation.Sensitive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@Data
public class TestModel {
    @Length(min = 5, max = 16, message = "账号长度为 5-16 位")
    @LogField(description = "用户名")
    @Sensitive(value = SensitiveType.AUTO)
    private  String name;
    @DictField(dictName ="sex",dictTransField="sexName" )
    private  int sex;
    private String sexName;
    private int age;

}
