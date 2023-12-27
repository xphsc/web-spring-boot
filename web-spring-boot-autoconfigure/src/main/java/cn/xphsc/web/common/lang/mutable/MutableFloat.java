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
 * @description: variable float
 * @since 1.1.6
 */
public class MutableFloat extends Number implements Comparable<MutableFloat> {

    private static final long serialVersionUID = -8176512397589472L;

    private float value;

    public MutableFloat() {
    }

    public MutableFloat(float value) {
        this.value = value;
    }

    public MutableFloat(Number value) {
        this.value = value.floatValue();
    }

    public MutableFloat(String value) {
        this.value = Float.parseFloat(value);
    }

    public Float get() {
        return this.value;
    }


    public void set(Number value) {
        this.value = value.floatValue();
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isNaN() {
        return Float.isNaN(this.value);
    }

    public boolean isInfinite() {
        return Float.isInfinite(this.value);
    }

    public void increment() {
        ++this.value;
    }

    public float getAndIncrement() {
        return (this.value++);
    }

    public float incrementAndGet() {
        return ++this.value;
    }

    public void decrement() {
        --this.value;
    }

    public float getAndDecrement() {
        return this.value--;
    }

    public float decrementAndGet() {
        return --this.value;
    }

    public void add(float i) {
        this.value += i;
    }

    public void add(Number i) {
        this.value += i.floatValue();
    }

    public void subtract(float i) {
        this.value -= i;
    }

    public void subtract(Number i) {
        this.value -= i.floatValue();
    }

    public float addAndGet(float i) {
        this.value += i;
        return this.value;
    }

    public float addAndGet(Number i) {
        this.value += i.floatValue();
        return this.value;
    }

    public float getAndAdd(float i) {
        float last = this.value;
        this.value += i;
        return last;
    }

    public float getAndAdd(Number i) {
        float last = this.value;
        this.value += i.floatValue();
        return last;
    }

    public Float toFloat() {
        return this.floatValue();
    }

    @Override
    public byte byteValue() {
        return (byte) this.value;
    }

    @Override
    public short shortValue() {
        return (short) this.value;
    }

    @Override
    public int intValue() {
        return (int) this.value;
    }

    @Override
    public long longValue() {
        return (long) this.value;
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
        return obj instanceof MutableFloat && Float.floatToIntBits(((MutableFloat) obj).value) == Float.floatToIntBits(this.value);
    }

    @Override
    public int hashCode() {
        return Float.floatToIntBits(this.value);
    }

    @Override
    public int compareTo(MutableFloat other) {
        return Float.compare(this.value, other.value);
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

}

