/*
 * Copyright (c) 2023 huipei.x
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
package cn.xphsc.web.proxy.dynamic;


import cn.xphsc.web.proxy.annotation.DynamicProxy;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 动态代理工厂接口
 *  该接口定义了创建动态代理对象的标准方法，用于在运行时动态生成代理实例
 *   动态代理可以在不修改目标对象的情况下，为其添加额外的功能或控制
 * @since 1.1.8
 */
public interface DynamicProxyFactory {

    default Class<?>[] collectProxyInterface(Class<?> interfaceType) {
        return new Class[]{interfaceType, IDynamicProxy.class};
    }

    <T> T createProxy(Class<T> dynamicInterface, DynamicProxy dynamicAnnotation);
}
