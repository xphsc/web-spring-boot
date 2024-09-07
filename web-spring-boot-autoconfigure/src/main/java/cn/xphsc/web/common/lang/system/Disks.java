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
package cn.xphsc.web.common.lang.system;


import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 获取电脑磁盘分区信息
 * @since 1.2.0
 */
public class Disks {

    /**
     * @return disks 返回ArrayList形式的硬盘分区
     */
    public static ArrayList<File> diskInformation() {
        File[] disks = File.listRoots();
        if (disks.length == 0) {
            return null;
        }
        return new ArrayList<>(Arrays.asList(disks));
    }

    /**
     * @return 获取内存使用情况
     */
    public static Map<String, String> getMemoryInfo() {
        Map<String, String> map = new ConcurrentHashMap<>(1);
        OperatingSystemMXBean mem = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        // 获取内存总容量
        map.put("totalMemorySize", transformation(mem.getTotalPhysicalMemorySize()));
        // 获取可用内存容量
        map.put("freeMemorySize", transformation(mem.getFreePhysicalMemorySize()));
        return map;
    }

    /**
     * 将字节容量转化为GB
     */
    public static String transformation(long size) {
        return size / 1024 / 1024 / 1024 + "GB";
    }
}