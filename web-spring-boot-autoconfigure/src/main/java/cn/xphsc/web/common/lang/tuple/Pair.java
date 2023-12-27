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
package cn.xphsc.web.common.lang.tuple;



/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class Pair<L, R> {
    private static final Pair<Object, Object> EMPTY = new Pair<>(null, null);

    private final L left;
    private final R right;

    /**
     * Returns an empty pair.
     */
    @SuppressWarnings("unchecked")
    public static <L, R> Pair<L, R> empty() {
        return (Pair<L, R>) EMPTY;
    }

    /**
     * Constructs a pair with its left value being {@code left}, or returns an empty pair if
     * {@code left} is null.
     *
     * @return the constructed pair or an empty pair if {@code left} is null.
     */
    public static <L, R> Pair<L, R> createLeft(L left) {
        if (left == null) {
            return empty();
        } else {
            return new Pair<>(left, null);
        }
    }

    /**
     * Constructs a pair with its right value being {@code right}, or returns an empty pair if
     * {@code right} is null.
     *
     * @return the constructed pair or an empty pair if {@code right} is null.
     */
    public static <L, R> Pair<L, R> createRight(R right) {
        if (right == null) {
            return empty();
        } else {
            return new Pair<>(null, right);
        }
    }

    public static <L, R> Pair<L, R> create(L left, R right) {
        if (right == null && left == null) {
            return empty();
        } else {
            return new Pair<>(left, right);
        }
    }

    private Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static Pair<Object, Object> getEMPTY() {
        return EMPTY;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }
}
