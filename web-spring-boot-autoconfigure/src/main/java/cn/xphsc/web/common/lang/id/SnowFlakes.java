/*
 * Copyright (c) 2022 huipei.x
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
package cn.xphsc.web.common.lang.id;


import cn.xphsc.web.utils.RandomUtils;
import cn.xphsc.web.utils.Systems;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import static cn.xphsc.web.utils.StringUtils.codePoints;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 雪花算法 id 生成工具
 * @since 1.1.6
 */
public class SnowFlakes {

    private static final SnowFlakeIdWorker ID_WORKER;

    static {
        ID_WORKER = new SnowFlakeIdWorker(getWorkId(), getDataCenterId());
    }

    /**
     * 获取id
     * @return id
     */
    public static Long nextId() {
        return ID_WORKER.nextId();
    }


    /**
     * 获取工作机器id
     *
     * @return 工作机器id
     */
    private static Long getWorkId() {
        try {
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            int[] code = codePoints(hostAddress);
            int sums = 0;
            for (int b : code) {
                sums += b;
            }
            return (long) (sums % 32);
        } catch (UnknownHostException e) {
            // 如果获取失败, 则使用随机数备用
            return (long) RandomUtils.randomInt(0, 31);
        }
    }

    /**
     * 通过 hostName 获取机器id
     *
     * @return 机器id
     */
    private static Long getDataCenterId() {
        int[] cps = codePoints(Systems.HOST_NAME);
        int sums = 0;
        for (int i : cps) {
            sums += i;
        }
        return (long) (sums % 32);
    }

}

