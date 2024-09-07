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
package cn.xphsc.web.common.lang.mutable;




/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: variable short
 * @since 1.1.6
 */
public class MutableShort extends Number implements Comparable<MutableShort> {

    private static final long serialVersionUID = -718264587912374L;

    private short value;

    public MutableShort() {
    }

    public MutableShort(short value) {
        this.value = value;
    }

    public MutableShort(Number value) {
        this.value = value.shortValue();
    }

    public MutableShort(String value) {
        this.value = Short.parseShort(value);
    }

    public Short get() {
        return this.value;
    }


    public void set(Number value) {
        this.value = value.shortValue();
    }

    public void setValue(short value) {
        this.value = value;
    }

    public void increment() {
        ++this.value;
    }

    public short getAndIncrement() {
        return this.value++;
    }

    public short incrementAndGet() {
        return ++this.value;
    }

    public void decrement() {
        --this.value;
    }

    public short getAndDecrement() {
        return this.value--;
    }

    public short decrementAndGet() {
        return --this.value;
    }

    public void add(short b) {
        this.value += b;
    }

    public void add(Number b) {
        this.value += b.shortValue();
    }

    public void subtract(short b) {
        this.value -= b;
    }

    public void subtract(Number b) {
        this.value -= b.shortValue();
    }

    public short addAndGet(short b) {
        this.value += b;
        return this.value;
    }

    public short addAndGet(Number b) {
        this.value += b.shortValue();
        return this.value;
    }

    public short getAndAdd(short b) {
        short last = this.value;
        this.value += b;
        return last;
    }

    public short getAndAdd(Number b) {
        short last = this.value;
        this.value += b.shortValue();
        return last;
    }

    public Short toShort() {
        return this.value;
    }

    @Override
    public byte byteValue() {
        return (byte) this.value;
    }

    @Override
    public short shortValue() {
        return this.value;
    }

    @Override
    public int intValue() {
        return this.value;
    }

    @Override
    public long longValue() {
        return this.value;
    }

    @Override
    public float floatValue() {
        return this.value;
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MutableShort) {
            return this.value == ((MutableShort) obj).shortValue();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.value;
    }

    @Override
    public int compareTo(MutableShort other) {
        return Short.compare(this.value, other.value);
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

}
