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
package cn.xphsc.web.utils;


import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class RandomUtils {


    public static int randomInt(int min, int max) {
        return getRandom().nextInt(min, max);
    }

    public static int randomInt() {
        return getRandom().nextInt();
    }

    public static int randomInt(int limit) {
        return getRandom().nextInt(limit);
    }

    public static long randomLong(long min, long max) {
        return getRandom().nextLong(min, max);
    }

    public static long randomLong() {
        return getRandom().nextLong();
    }

    public static long randomLong(long limit) {
        return getRandom().nextLong(limit);
    }

    public static double randomDouble(double min, double max) {
        return getRandom().nextDouble(min, max);
    }


    public static double randomDouble() {
        return getRandom().nextDouble();
    }


    public static byte[] randomBytes(int length) {
        byte[] bytes = new byte[length];
        getRandom().nextBytes(bytes);
        return bytes;
    }


    public static SecureRandom getSecureRandom() {
        return getSecureRandom((byte[])null);
    }

    public static SecureRandom getSecureRandom(byte[] seed) {
        return createSecureRandom(seed);


    }
    public static SecureRandom createSecureRandom(byte[] seed) {
        return null == seed ? new SecureRandom() : new SecureRandom(seed);
    }
    public static ThreadLocalRandom getRandom() {
        return ThreadLocalRandom.current();
    }
}
