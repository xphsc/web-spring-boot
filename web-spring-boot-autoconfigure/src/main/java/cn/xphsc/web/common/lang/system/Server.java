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



import cn.xphsc.web.common.lang.constant.JdkConstant;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 服务器相关信息
 * @since 1.2.0
 */
public class Server {

    private static final int OSHI_WAIT_SECOND = 1000;

    /**
     * CPU相关信息
     */
    private SysCpu cpu = new SysCpu();

    /**
     * 內存相关信息
     */
    private SysMem mem = new SysMem();

    /**
     * JVM相关信息
     */
    private SysJvm jvm = new SysJvm();

    /**
     * 服务器相关信息
     */
    private SystemInfo sys = new SystemInfo();

    /**
     * 磁盘相关信息
     */
    private List<SysFile> sysFiles = new LinkedList<>();

    public SysCpu getCpu() {
        return cpu;
    }

    public void setCpu(SysCpu cpu) {
        this.cpu = cpu;
    }

    public SysMem getMem() {
        return mem;
    }

    public void setMem(SysMem mem) {
        this.mem = mem;
    }

    public SysJvm getJvm() {
        return jvm;
    }

    public void setJvm(SysJvm jvm) {
        this.jvm = jvm;
    }

    public SystemInfo getSys() {
        return sys;
    }

    public void setSys(SystemInfo sys) {
        this.sys = sys;
    }

    public List<SysFile> getSysFiles() {
        return sysFiles;
    }

    public void setSysFiles(List<SysFile> sysFiles) {
        this.sysFiles = sysFiles;
    }

    /**
     * 设置Java虚拟机
     */
    private void setJvmInfo() {
        Properties props =  java.lang.System.getProperties();
        jvm.setTotal(Runtime.getRuntime().totalMemory());
        jvm.setMax(Runtime.getRuntime().maxMemory());
        jvm.setFree(Runtime.getRuntime().freeMemory());
        jvm.setVersion(props.getProperty(JdkConstant.VERSION));
        jvm.setHome(props.getProperty(JdkConstant.HOME));
    }

    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值
     */
    public String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }
}