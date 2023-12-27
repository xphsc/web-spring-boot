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
package cn.xphsc.web.common.exception;


import cn.xphsc.web.common.exception.argument.IndexArgumentException;
import cn.xphsc.web.common.exception.argument.InvalidArgumentException;
import cn.xphsc.web.common.exception.argument.NullArgumentException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.NoSuchElementException;

import static cn.xphsc.web.common.lang.constant.Constants.SPACE;


/**
 * {@link Exception}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.1.6
 */
public class Exceptions {

    private Exceptions() {
    }

    public static void impossible() {
        throw runtime("impossible exceptions...");
    }

    /**
     * 获取异常摘要信息
     * @param e e
     * @return 摘要
     */
    public static String getDigest(Throwable e) {
        if (e.getMessage() == null) {
            return e.getClass().getName();
        } else {
            return e.getClass().getName() + SPACE + e.getMessage();
        }
    }

    /**
     * Throwable ->  RuntimeException
     * @param e Throwable
     * @return RuntimeException
     */
    public static RuntimeException unchecked(Throwable e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        } else {
            return new RuntimeException(e);
        }
    }

    /**
     * 打印异常
     *
     * @param t Throwable
     */
    public static void printStacks(Throwable t) {
        t.printStackTrace();
    }


    /**
     * 获取异常栈内信息
     * @param t 异常
     * @return 栈信息
     */
    public static String getStackTraceAsString(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        t.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     * 判断异常是否由某些底层的异常引起
     * @param r                     异常
     * @param causeThrowableClasses checkClass
     * @return ignore
     */
    @SafeVarargs
    public static boolean isCausedBy(Throwable r, Class<? extends Exception>... causeThrowableClasses) {
        Throwable cause = r.getCause();
        while (cause != null) {
            for (Class<? extends Exception> causeClass : causeThrowableClasses) {
                if (causeClass.isInstance(cause)) {
                    return true;
                }
            }
            cause = cause.getCause();
        }
        return false;
    }

    // -------------------- new --------------------

    public static Exception exception() {
        return new Exception();
    }

    public static Exception exception(Throwable t) {
        return new Exception(t);
    }

    public static Exception exception(String s) {
        return new Exception(s);
    }

    public static Exception exception(String s, Throwable t) {
        return new Exception(s, t);
    }

    public static RuntimeException runtime() {
        return new RuntimeException();
    }

    public static RuntimeException runtime(Throwable t) {
        return new RuntimeException(t);
    }

    public static RuntimeException runtime(String s) {
        return new RuntimeException(s);
    }

    public static RuntimeException runtime(String s, Throwable t) {
        return new RuntimeException(s, t);
    }



    public static IOException io() {
        return new IOException();
    }

    public static IOException io(String s) {
        return new IOException(s);
    }

    public static IOException io(Throwable t) {
        return new IOException(t);
    }

    public static IOException io(String s, Throwable t) {
        return new IOException(s, t);
    }

    public static NullPointerException nullPoint() {
        return new NullPointerException();
    }

    public static NullPointerException nullPoint(String s) {
        return new NullPointerException(s);
    }

    public static IllegalArgumentException argument() {
        return new IllegalArgumentException();
    }

    public static IllegalArgumentException argument(Throwable t) {
        return new IllegalArgumentException(t);
    }

    public static IllegalArgumentException argument(String s) {
        return new IllegalArgumentException(s);
    }

    public static IllegalArgumentException argument(String s, Throwable t) {
        return new IllegalArgumentException(s, t);
    }

    public static NumberFormatException numberFormat() {
        return new NumberFormatException();
    }

    public static NumberFormatException numberFormat(String s) {
        return new NumberFormatException(s);
    }

    public static IndexOutOfBoundsException index() {
        return new IndexOutOfBoundsException();
    }

    public static IndexOutOfBoundsException index(String s) {
        return new IndexOutOfBoundsException(s);
    }

    public static ArrayIndexOutOfBoundsException arrayIndex() {
        return new ArrayIndexOutOfBoundsException();
    }

    public static ArrayIndexOutOfBoundsException arrayIndex(String s) {
        return new ArrayIndexOutOfBoundsException(s);
    }

    public static ArrayIndexOutOfBoundsException arrayIndex(int index) {
        return new ArrayIndexOutOfBoundsException(index);
    }


    public static InterruptedException interrupted() {
        return new InterruptedException();
    }

    public static InterruptedException interrupted(String s) {
        return new InterruptedException(s);
    }




    public static UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException();
    }

    public static UnsupportedOperationException unsupported(Throwable t) {
        return new UnsupportedOperationException(t);
    }

    public static UnsupportedOperationException unsupported(String s) {
        return new UnsupportedOperationException(s);
    }

    public static UnsupportedOperationException unsupported(String s, Throwable t) {
        return new UnsupportedOperationException(s, t);
    }

    public static IllegalStateException state() {
        return new IllegalStateException();
    }

    public static IllegalStateException state(Throwable t) {
        return new IllegalStateException(t);
    }

    public static IllegalStateException state(String s) {
        return new IllegalStateException(s);
    }

    public static IllegalStateException state(String s, Throwable t) {
        return new IllegalStateException(s, t);
    }


    public static NoSuchElementException noSuchElement() {
        return new NoSuchElementException();
    }

    public static NoSuchElementException noSuchElement(String s) {
        return new NoSuchElementException(s);
    }

    // -------------------- invalidException --------------------

    public static InvalidArgumentException invalidArgument() {
        return new InvalidArgumentException();
    }

    public static InvalidArgumentException invalidArgument(Throwable t) {
        return new InvalidArgumentException(t);
    }

    public static InvalidArgumentException invalidArgument(String s) {
        return new InvalidArgumentException(s);
    }

    public static InvalidArgumentException invalidArgument(String s, Throwable t) {
        return new InvalidArgumentException(s, t);
    }
    public static NullArgumentException nullArgument() {
        return new NullArgumentException();
    }

    public static NullArgumentException nullArgument(Throwable t) {
        return new NullArgumentException(t);
    }

    public static NullArgumentException nullArgument(String s) {
        return new NullArgumentException(s);
    }

    public static NullArgumentException nullArgument(String s, Throwable t) {
        return new NullArgumentException(s, t);
    }

    public static IndexArgumentException indexArgument() {
        return new IndexArgumentException();
    }

    public static IndexArgumentException indexArgument(Throwable t) {
        return new IndexArgumentException(t);
    }

    public static IndexArgumentException indexArgument(String s) {
        return new IndexArgumentException(s);
    }

    public static IndexArgumentException indexArgument(String s, Throwable t) {
        return new IndexArgumentException(s, t);
    }

    public static IndexArgumentException indexArgument(int index) {
        return new IndexArgumentException(index);
    }

    public static IndexArgumentException indexArgument(int index, Throwable t) {
        return new IndexArgumentException(index, t);
    }

    public static IndexArgumentException indexArgument(int index, String s) {
        return new IndexArgumentException(index, s);
    }

    public static IndexArgumentException indexArgument(int index, String s, Throwable t) {
        return new IndexArgumentException(index, s, t);
    }
    public static InvokeRuntimeException invoke() {
        return new InvokeRuntimeException();
    }

    public static InvokeRuntimeException invoke(Throwable t) {
        return new InvokeRuntimeException(t);
    }

    public static InvokeRuntimeException invoke(String s) {
        return new InvokeRuntimeException(s);
    }

    public static InvokeRuntimeException invoke(String s, Throwable t) {
        return new InvokeRuntimeException(s, t);
    }

    public static Error error() {
        return new Error();
    }

    public static Error error(Throwable t) {
        return new Error(t);
    }

    public static Error error(String s) {
        return new Error(s);
    }

    public static Error error(String s, Throwable t) {
        return new Error(s, t);
    }



}
