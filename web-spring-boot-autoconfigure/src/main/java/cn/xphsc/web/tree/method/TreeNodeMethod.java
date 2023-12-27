/*
 * Copyright (c) 2021 huipei.x
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
package cn.xphsc.web.tree.method;

import cn.xphsc.web.tree.annotation.SingleTreeNode;
import cn.xphsc.web.utils.ClassUtils;
import cn.xphsc.web.utils.StringUtils;
import org.springframework.util.Assert;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class TreeNodeMethod {

    private static Map<Class, Method> GET_CHILD_METHOD_MAP;
    private static Map<Class, Method> GET_PARENT_METHOD_MAP;
    private static Map<Class, Method> GET_CHILDlIST_METHOD_MAP;
    private static Map<Class, Method> SET_CHILDlIST_METHOD_MAP;
    public  TreeNodeMethod(String packages){
        ConcurrentHashMap<Class, Method> getChildMethodMap = new ConcurrentHashMap<>();
        ConcurrentHashMap<Class, Method> getParentMethodMap = new ConcurrentHashMap<>();
        ConcurrentHashMap<Class, Method> getChildListMethodList = new ConcurrentHashMap<>();
        ConcurrentHashMap<Class, Method> setChildListMethodMap = new ConcurrentHashMap<>();
        Assert.notNull(packages,"packages must not be empty\n" +
                "\n");
        List<Class<?>> clazzList = ClassUtils.getAnnotatedClassOfPackages(packages, SingleTreeNode.class);

        for (Class<?> clazz : clazzList) {
            SingleTreeNode annotation = clazz.getAnnotation(SingleTreeNode.class);
            String childId = annotation.childId();
            String parentId = annotation.parentId();
            String childList = annotation.childList();
            try {
                Method getChildId = clazz.getMethod("get" + StringUtils.upperCaseFirst(childId));
                getChildMethodMap.put(clazz, getChildId);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("获取<" + clazz.getName() + ">类<" + childId + ">的get方法出现异常：" + e.getMessage());
            }
            try {
                Method getParentId = clazz.getMethod("get" + StringUtils.upperCaseFirst(parentId));
                getParentMethodMap.put(clazz, getParentId);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("获取<" + clazz.getName() + ">类<" + parentId + ">的get方法出现异常：" + e.getMessage());
            }
            try {
                Method getChildList = clazz.getMethod("get" + StringUtils.upperCaseFirst(childList));
                getChildListMethodList.put(clazz, getChildList);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("获取<" + clazz.getName() + ">类<" + childList + ">的get方法出现异常：" + e.getMessage());
            }
            try {
                Method setChildList = clazz.getMethod("set" + StringUtils.upperCaseFirst(childList), List.class);
                setChildListMethodMap.put(clazz, setChildList);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("获取<" + clazz.getName() + ">类<" + childList + ">的set方法出现异常：" + e.getMessage());
            }
            GET_CHILD_METHOD_MAP = Collections.unmodifiableMap(getChildMethodMap);
            GET_PARENT_METHOD_MAP = Collections.unmodifiableMap(getParentMethodMap);
            GET_CHILDlIST_METHOD_MAP = Collections.unmodifiableMap(getChildListMethodList);
            SET_CHILDlIST_METHOD_MAP = Collections.unmodifiableMap(setChildListMethodMap);
        }
    }

    public  <T> List<T> objectTreeNode(List<T> sourceList, @NotNull Class<?> clazz)  {
        if (sourceList == null || sourceList.size() < SOURCE_SIZE) {
            return sourceList;
        }
        SingleTreeNode annotation = clazz.getAnnotation(SingleTreeNode.class);
        if (annotation == null) {
            throw new RuntimeException("未找到<" + clazz + ">类的SingleTreeNode注解！");
        }
        Method getChildIdMethod = GET_CHILD_METHOD_MAP.get(clazz);
        Method getParentIdMethod = GET_PARENT_METHOD_MAP.get(clazz);
        Method getChildListMethod = GET_CHILDlIST_METHOD_MAP.get(clazz);
        Method setChildListMethod = SET_CHILDlIST_METHOD_MAP.get(clazz);
        List<T> resList = new ArrayList<>(sourceList.size());
        Map<Object, T> sourceMap = new HashMap<>(16);
        for (T t : sourceList) {
            Object childParam = null;
            try {
                childParam = getChildIdMethod.invoke(t);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            sourceMap.put(childParam, t);
        }
        for (T t : sourceList) {
            Object parentBean = null;
            try {
                parentBean = sourceMap.get(getParentIdMethod.invoke(t));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            if (parentBean == null) {
                resList.add(t);
            } else {
                List<T> childList = null;
                try {
                    childList = (List<T>) getChildListMethod.invoke(parentBean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                if (childList == null) {
                    childList = new ArrayList<>();
                    try {
                        setChildListMethod.invoke(parentBean, childList);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                childList.add(t);
            }
        }
        return resList;
    }

    private static int SOURCE_SIZE=2;


}
