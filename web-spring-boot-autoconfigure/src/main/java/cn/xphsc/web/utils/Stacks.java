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
package cn.xphsc.web.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 当前栈内信息
 * @since 1.1.7
 */
public class Stacks {

    private Stacks() {
    }

    public static List<StackTrace> currentStacks() {
        return currentStacks(current());
    }

    public static List<StackTrace> currentStacks(Exception e) {
        return currentStacks(e.getStackTrace());
    }

    /**
     * 获取当前所有栈内信息
     * @param stackTrace stackTrace
     * @return 栈内信息
     */
    public static List<StackTrace> currentStacks(StackTraceElement[] stackTrace) {
        List<StackTrace> r = new ArrayList<>();
        for (StackTraceElement traceElement : stackTrace) {
            r.add(new StackTrace(traceElement));
        }
        return r;
    }

    public static StackTrace currentStack(int index) {
        return currentStack(current(), index);
    }

    public static StackTrace currentStack(Exception e, int index) {
        return currentStack(e.getStackTrace(), index);
    }

    /**
     * 获取当前栈内信息
     * @param index      栈下标
     * @param stackTrace stackTrace
     * @return 栈内信息
     */
    public static StackTrace currentStack(StackTraceElement[] stackTrace, int index) {
        return new StackTrace(stackTrace[index]);
    }

    public static StackTrace currentStack() {
        return currentStack(current());
    }

    public static StackTrace currentStack(Exception e) {
        return currentStack(e.getStackTrace());
    }

    /**
     * 获取当前栈内信息
     * @param stackTrace stackTrace
     * @return 栈内信息
     */
    public static StackTrace currentStack(StackTraceElement[] stackTrace) {
        return new StackTrace(ArrayUtils.last(stackTrace));
    }

    public static String currentFile() {
        return currentFile(current());
    }

    public static String currentFile(Exception e) {
        return currentFile(e.getStackTrace());
    }

    /**
     * 获取当前文件
     * @param stackTrace stackTrace
     * @return 当前文件
     */
    public static String currentFile(StackTraceElement[] stackTrace) {
        return ArrayUtils.last(stackTrace).getFileName();
    }

    public static String currentClass() {
        return currentClass(current());
    }

    public static String currentClass(Exception e) {
        return currentClass(e.getStackTrace());
    }

    /**
     * 获取当前类
     * @param stackTrace stackTrace
     * @return 当前类
     */
    public static String currentClass(StackTraceElement[] stackTrace) {
        return ArrayUtils.last(stackTrace).getClassName();
    }

    public static String currentMethod() {
        return currentMethod(current());
    }

    public static String currentMethod(Exception e) {
        return currentMethod(e.getStackTrace());
    }

    /**
     * 获取当前方法
     * @param stackTrace stackTrace
     * @return 栈内当前方法
     */
    public static String currentMethod(StackTraceElement[] stackTrace) {
        return ArrayUtils.last(stackTrace).getMethodName();
    }

    public static int currentLineNumber() {
        return currentLineNumber(current());
    }

    public static int currentLineNumber(Exception e) {
        return currentLineNumber(e.getStackTrace());
    }

    /**
     * 获取当前行
     * @param stackTrace stackTrace
     * @return 栈内信息
     */
    public static int currentLineNumber(StackTraceElement[] stackTrace) {
        return ArrayUtils.last(stackTrace).getLineNumber();
    }

    public static boolean currentNative() {
        return currentNative(current());
    }

    public static boolean currentNative(Exception e) {
        return currentNative(e.getStackTrace());
    }

    /**
     * 获取当前方法是否是本地方法
     * @param stackTrace stackTrace
     * @return true 本地方法
     */
    public static boolean currentNative(StackTraceElement[] stackTrace) {
        return ArrayUtils.last(stackTrace).isNativeMethod();
    }

    /**
     * StackTraceElement -> StackTrace
     * @param e StackTraceElement
     * @return StackTrace
     */
    public static StackTrace toStackTrace(StackTraceElement e) {
        return new StackTrace(e);
    }

    /**
     * StackTraceElement -> StackTrace
     * @param es StackTraceElement
     * @return StackTrace
     */
    public static List<StackTrace> toStackTraces(StackTraceElement[] es) {
        List<StackTrace> list = new ArrayList<>();
        for (StackTraceElement e : es) {
            list.add(new StackTrace(e));
        }
        return list;
    }

    private static StackTraceElement[] current() {
        return Thread.currentThread().getStackTrace();
    }

    /**
     * 栈内信息
     */
    public static class StackTrace implements Serializable {

        private final StackTraceElement e;

        private final String className;

        private final String fileName;

        private final Integer lineNumber;

        private final String methodName;

        private final boolean nativeMethod;

        private StackTrace(StackTraceElement e) {
            this.e = e;
            this.className = e.getClassName();
            this.fileName = e.getFileName();
            this.lineNumber = e.getLineNumber();
            this.methodName = e.getMethodName();
            this.nativeMethod = e.isNativeMethod();
        }

        public String getClassName() {
            return className;
        }

        public String getFileName() {
            return fileName;
        }

        public Integer getLineNumber() {
            return lineNumber;
        }

        public String getMethodName() {
            return methodName;
        }

        public boolean isNativeMethod() {
            return nativeMethod;
        }

        public String toRawString() {
            return e.toString();
        }

        @Override
        public String toString() {
            return e +
                    "\n     className ==> '" + className + '\'' +
                    "\n      fileName ==> '" + fileName + '\'' +
                    "\n    lineNumber ==> " + lineNumber +
                    "\n    methodName ==> '" + methodName + '\'' +
                    "\n  nativeMethod ==> " + nativeMethod;
        }

    }

}
