package com.xphsc.web.example.infrastructure;

import cn.xphsc.web.dicttraslate.handler.DictTransHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link }
 *
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@Component
public class TestModelDictTansHandler implements DictTransHandler {
    @Override
    public Map<String, String> dictTransByName(String dictName) {
        Map<String,String> map=new HashMap<>();
        switch (dictName) {

            case "sex":
                map.put("1", "男");
                map.put("2", "女");
                break;
        }
        return map;
    }
}
