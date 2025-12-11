/*
 * Copyright (c) 2024 huipei.x
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.xphsc.web.strategy.proxy;


import cn.xphsc.web.strategy.IStrategy;
import cn.xphsc.web.strategy.annotation.Strategy;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 2.0.1
 */
public class IStrategyProxy<T> implements  IStrategy {
    T strategyClass ;
    ConfigurableListableBeanFactory beanFactory;
    ConcurrentHashMap<Object,Object> beans = new ConcurrentHashMap<>();
    volatile boolean isReady = false;

    public static final String DEFAULT_STRATEGY_KEY = "DEFAULT";
    public IStrategyProxy(T argType ) {
        strategyClass = argType;
    }
    public void setBeanFactory( ConfigurableListableBeanFactory beanFactory ){
        this.beanFactory = beanFactory;
    }
    public void init(){
        if( !isReady ) {
            Map<String, T> beansOfType = (Map<String, T>) beanFactory.getBeansOfType((Class) strategyClass);
            for (Map.Entry e : beansOfType.entrySet()) {
                T v = (T) e.getValue();
                findStrategyBean(v);
            }
            isReady = true;
        }
    }

    private void findStrategyBean(Object bean ){
        Object key = DEFAULT_STRATEGY_KEY ;
        try {
            Strategy annotation = AnnotationUtils.findAnnotation(bean.getClass(), Strategy.class);
            if( annotation != null ) {
                key = annotation.value();
            }
            if (beans.containsKey(key)) {
                throw new RuntimeException("[web][strategy] duplicate strategy name : " +bean.getClass().getSimpleName()+ " is "+ key);
            }
            beans.put(key, bean);
        }catch (Exception e ){
        }
    }
    @Override
    public Object strategy(Object strategy) {
        if( !isReady ){
            return beans.get( strategy );
        }
        Object bean = beans.get( strategy );
        return bean != null ? bean : beans.get( DEFAULT_STRATEGY_KEY );
    }
}
