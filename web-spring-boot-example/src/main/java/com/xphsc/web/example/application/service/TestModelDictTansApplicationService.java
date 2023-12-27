package com.xphsc.web.example.application.service;

import cn.xphsc.web.dicttraslate.annotation.DictTransMap;
import cn.xphsc.web.dicttraslate.annotation.DictTransMapper;
import cn.xphsc.web.dicttraslate.annotation.DictTranslation;
import com.xphsc.web.example.infrastructure.TestModelDictTansHandler;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@Service
public class TestModelDictTansApplicationService {

    @DictTranslation(dictTransHandler = TestModelDictTansHandler.class)
    public  <S> S  DictTansEntity(S entity){
        return entity;
    }


    @DictTransMap(value={@DictTransMapper(dictName="age",dictTransField ="ageName" )},dictTransHandler = TestModelDictTansHandler.class)
       public Map DictTansEntity(Map map){
        return map;
    }
}
