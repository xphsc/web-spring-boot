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
package cn.xphsc.web.common.lang.loader;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: SPI 服务加载工具类
 * @since 1.1.7
 */
public class ServiceLoaders {

    private ServiceLoaders() {
    }

    /**
     * 加载第一个可用服务, 如果定义多个接口实现类, 只获取第一个不报错的服务
     * @param clazz 服务接口
     * @param <T>   接口类型
     * @return 第一个服务接口实现对象
     */
    public static <T> T loadFirstAvailable(Class<T> clazz) {
        Iterator<T> iterator = load(clazz).iterator();
        while (iterator.hasNext()) {
            try {
                return iterator.next();
            } catch (ServiceConfigurationError e) {
                // ignore
            }
        }
        return null;
    }

    /**
     * 加载第一个服务, 如果定义了多个接口实现类, 只获取第一个
     * @param clazz 服务接口
     * @param <T>   接口类型
     * @return 第一个服务接口实现对象
     */
    public static <T> T loadFirst(Class<T> clazz) {
        Iterator<T> iterator = load(clazz).iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    /**
     * 加载服务
     * @param clazz 服务接口
     * @param <T>   接口类型
     * @return 服务接口实现
     */
    public static <T> ServiceLoader<T> load(Class<T> clazz) {
        return ServiceLoader.load(clazz);
    }

    /**
     * 加载服务
     * @param clazz  服务接口
     * @param loader 类加载器
     * @param <T>    接口类型
     * @return 服务接口实现
     */
    public static <T> ServiceLoader<T> load(Class<T> clazz, ClassLoader loader) {
        return ServiceLoader.load(clazz, loader);
    }

}
