package cn.xphsc.web.common.lang.object;

import cn.xphsc.web.common.collect.Iterables;
import cn.xphsc.web.common.lang.reflect.Methods;
import cn.xphsc.web.common.lang.reflect.Types;
import cn.xphsc.web.utils.*;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  Objs
 * @since 2.0.4
 */
@SuppressWarnings("unused")
public class Objs {

	// region equals/hash/toString

	/**
	 * 比较两个对象是否不相等。<br>
	 *
	 * @param obj1 对象1
	 * @param obj2 对象2
	 * @return 是否不等
	 */
	public static boolean notEqual(Object obj1, Object obj2) {
		return !equals(obj1, obj2);
	}

	public static boolean equalsAny(Object obj, Object... args) {
		for (Object arg : args) {
			if (equals(obj, arg)) {
				return true;
			}
		}
		return false;
	}

	@SafeVarargs
	public static <T> T getIfEquals(T obj, T... args) {
		for (int i = 0; i + 1 < args.length; i += 2) {
			if (equals(args[i], obj)) {
				return args[i + 1];
			}
		}
		if ((args.length & 1) == 0) {
			return null;
		}
		return args[args.length - 1];
	}

	/**
	 * 比较两个对象是否相等。<br>
	 * 相同的条件有两个，满足其一即可：<br>
	 * <ol>
	 * <li>obj1 == null &amp;&amp; obj2 == null</li>
	 * <li>obj1.equals(obj2)</li>
	 * <li>如果是BigDecimal比较，0 == obj1.compareTo(obj2)</li>
	 * </ol>
	 *
	 * @param obj1 对象1
	 * @param obj2 对象2
	 * @return 是否相等
	 * @see Objects#equals(Object, Object)
	 */
	public static boolean equals(Object obj1, Object obj2) {
		if (obj1 instanceof Number && obj2 instanceof Number) {
			return NumberUtils.equals((Number) obj1, (Number) obj2);
		}
		return Objects.equals(obj1, obj2);
	}

	/** @see Objects#deepEquals(Object, Object) */
	public static boolean deepEquals(Object a, Object b) {
		return Objects.deepEquals(a, b);
	}


	/** @see Objects#hashCode(Object) */
	public static int hashCode(Object o) {
		return Objects.hashCode(o);
	}

	/** @see Arrays#hashCode(Object[]) */
	public static int hashCode(Object... values) {
		return Arrays.hashCode(values);
	}

	/** @see Objects#toString(Object) */
	public static String toString(Object o) {
		return toString(o, null);
	}

	/** @see Objects#toString(Object, String) */
	public static String toString(Object o, String nullDefault) {
		return (o != null) ? Iterables.toArrayString(o) : nullDefault;
	}


	// endregion


	// region 默认值


	/**
	 * @see #coalesceNull(Object[])
	 */
	@SafeVarargs
	public static <T> T coalesce(T... args) {
		return coalesceNull(args);
	}

	/**
	 * 返回参数列表中第一个非空的值。如果所有参数均为null或参数列表为空，则返回null。
	 *
	 * @param args 可变数量的参数，按顺序检查每个参数是否为null
	 * @param <T>  参数及返回值的泛型类型
	 * @return 第一个非null的参数值，或null（若所有参数均为null或无参数）
	 */
	@SafeVarargs
	public static <T> T coalesceNull(T... args) {
		T v = null;
		for (T arg : args) {
			v = arg;
			if (v != null) {
				break;
			}
		}
		return v;
	}

	@SafeVarargs
	public static <T> T coalesceEmpty(T... args) {
		T v = null;
		for (T arg : args) {
			v = arg;
			if (!isEmpty(v)) {
				break;
			}
		}
		return v;
	}

	@SafeVarargs
	public static <T> T coalesceBlank(T... args) {
		T v = null;
		for (T arg : args) {
			v = arg;
			if (!isBlank(v)) {
				break;
			}
		}
		return v;
	}


	/**
	 * 如果给定对象为{@code null}返回默认值
	 *
	 * <pre>
	 * Objs.defaultIfNull(null, null)      = null
	 * Objs.defaultIfNull(null, "")        = ""
	 * Objs.defaultIfNull(null, "zz")      = "zz"
	 * Objs.defaultIfNull("abc", *)        = "abc"
	 * Objs.defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
	 * </pre>
	 *
	 * @param <T>          对象类型
	 * @param object       被检查对象，可能为{@code null}
	 * @param defaultValue 被检查对象为{@code null}返回的默认值，可以为{@code null}
	 * @return 被检查对象为{@code null}返回默认值，否则返回原值
	 */
	public static <T> T defaultIfNull(final T object, final T defaultValue) {
		return isNull(object) ? defaultValue : object;
	}

	/**
	 * 如果被检查对象为 {@code null}， 返回默认值（由 defaultValueSupplier 提供）；否则直接返回
	 *
	 * @param source               被检查对象
	 * @param defaultValueSupplier 默认值提供者
	 * @param <T>                  对象类型
	 * @return 被检查对象为{@code null}返回默认值，否则返回自定义handle处理后的返回值
	 * @throws NullPointerException {@code defaultValueSupplier == null} 时，抛出
	 */
	public static <T> T defaultIfNull(T source, Supplier<? extends T> defaultValueSupplier) {
		if (isNull(source)) {
			return defaultValueSupplier.get();
		}
		return source;
	}

	/**
	 * 如果被检查对象为 {@code null}， 返回默认值（由 defaultValueSupplier 提供）；否则直接返回
	 *
	 * @param source               被检查对象
	 * @param defaultValueSupplier 默认值提供者
	 * @param <T>                  对象类型
	 * @return 被检查对象为{@code null}返回默认值，否则返回自定义handle处理后的返回值
	 * @throws NullPointerException {@code defaultValueSupplier == null} 时，抛出
	 */
	public static <T> T defaultIfNull(T source, Function<T, ? extends T> defaultValueSupplier) {
		if (isNull(source)) {
			return defaultValueSupplier.apply(null);
		}
		return source;
	}

	/**
	 * 如果给定对象为{@code null} 返回默认值, 如果不为null 返回自定义handle处理后的返回值
	 *
	 * @param <T>          被检查对象为{@code null}返回默认值，否则返回自定义handle处理后的返回值
	 * @param <R>          被检查的对象类型
	 * @param source       Object 类型对象
	 * @param handle       非空时自定义的处理方法
	 * @param defaultValue 默认为空的返回值
	 * @return 处理后的返回值
	 */
	public static <T, R> T defaultIfNull(R source, Function<R, ? extends T> handle, final T defaultValue) {
		if (isNotNull(source)) {
			return handle.apply(source);
		}
		return defaultValue;
	}


	/**
	 * 如果给定对象为{@code null}或者""返回默认值, 否则返回自定义handle处理后的返回值
	 *
	 * @param str          String 类型
	 * @param handle       自定义的处理方法
	 * @param defaultValue 默认为空的返回值
	 * @param <T>          被检查对象为{@code null}或者 ""返回默认值，否则返回自定义handle处理后的返回值
	 * @return 处理后的返回值
	 */
	public static <T> T defaultIfEmpty(String str, Function<CharSequence, ? extends T> handle, final T defaultValue) {
		if (StringUtils.isNotEmpty(str)) {
			return handle.apply(str);
		}
		return defaultValue;
	}

	/**
	 * 如果给定对象为{@code null}或者 "" 返回默认值
	 *
	 * <pre>
	 * Objs.defaultIfEmpty(null, null)      = null
	 * Objs.defaultIfEmpty(null, "")        = ""
	 * Objs.defaultIfEmpty("", "zz")      = "zz"
	 * Objs.defaultIfEmpty(" ", "zz")      = " "
	 * Objs.defaultIfEmpty("abc", *)        = "abc"
	 * </pre>
	 *
	 * @param <T>          对象类型（必须实现CharSequence接口）
	 * @param str          被检查对象，可能为{@code null}
	 * @param defaultValue 被检查对象为{@code null}或者 ""返回的默认值，可以为{@code null}或者 ""
	 * @return 被检查对象为{@code null}或者 ""返回默认值，否则返回原值
	 */
	public static <T extends CharSequence> T defaultIfEmpty(final T str, final T defaultValue) {
		return StringUtils.isEmpty(str) ? defaultValue : str;
	}

	/**
	 * 如果被检查对象为 {@code null} 或 "" 时，返回默认值（由 defaultValueSupplier 提供）；否则直接返回
	 *
	 * @param str                  被检查对象
	 * @param defaultValueSupplier 默认值提供者
	 * @param <T>                  对象类型（必须实现CharSequence接口）
	 * @return 被检查对象为{@code null}返回默认值，否则返回自定义handle处理后的返回值
	 * @throws NullPointerException {@code defaultValueSupplier == null} 时，抛出
	 */
	public static <T extends CharSequence> T defaultIfEmpty(T str, Supplier<? extends T> defaultValueSupplier) {
		if (StringUtils.isEmpty(str)) {
			return defaultValueSupplier.get();
		}
		return str;
	}

	/**
	 * 如果被检查对象为 {@code null} 或 "" 时，返回默认值（由 defaultValueSupplier 提供）；否则直接返回
	 *
	 * @param str                  被检查对象
	 * @param defaultValueSupplier 默认值提供者
	 * @param <T>                  对象类型（必须实现CharSequence接口）
	 * @return 被检查对象为{@code null}返回默认值，否则返回自定义handle处理后的返回值
	 * @throws NullPointerException {@code defaultValueSupplier == null} 时，抛出
	 */
	public static <T extends CharSequence> T defaultIfEmpty(T str, Function<T, ? extends T> defaultValueSupplier) {
		if (StringUtils.isEmpty(str)) {
			return defaultValueSupplier.apply(null);
		}
		return str;
	}

	/**
	 * 如果给定对象为{@code null}或者""或者空白符返回默认值
	 * <pre>
	 * Objs.defaultIfBlank(null, null)      = null
	 * Objs.defaultIfBlank(null, "")        = ""
	 * Objs.defaultIfBlank("", "zz")      = "zz"
	 * Objs.defaultIfBlank(" ", "zz")      = "zz"
	 * Objs.defaultIfBlank("abc", *)        = "abc"
	 * </pre>
	 *
	 * @param <T>          对象类型（必须实现CharSequence接口）
	 * @param str          被检查对象，可能为{@code null}
	 * @param defaultValue 被检查对象为{@code null}或者 ""或者空白符返回的默认值，可以为{@code null}或者 ""或者空白符
	 * @return 被检查对象为{@code null}或者 ""或者空白符返回默认值，否则返回原值
	 */
	public static <T extends CharSequence> T defaultIfBlank(final T str, final T defaultValue) {
		return StringUtils.isBlank(str) ? defaultValue : str;
	}

	/**
	 * 如果被检查对象为 {@code null} 或 "" 或 空白字符串时，返回默认值（由 defaultValueSupplier 提供）；否则直接返回
	 *
	 * @param str                  被检查对象
	 * @param defaultValueSupplier 默认值提供者
	 * @param <T>                  对象类型（必须实现CharSequence接口）
	 * @return 被检查对象为{@code null}返回默认值，否则返回自定义handle处理后的返回值
	 * @throws NullPointerException {@code defaultValueSupplier == null} 时，抛出
	 */
	public static <T extends CharSequence> T defaultIfBlank(T str, Supplier<? extends T> defaultValueSupplier) {
		if (StringUtils.isBlank(str)) {
			return defaultValueSupplier.get();
		}
		return str;
	}

	/**
	 * 如果被检查对象为 {@code null} 或 "" 或 空白字符串时，返回默认值（由 defaultValueSupplier 提供）；否则直接返回
	 *
	 * @param str                  被检查对象
	 * @param defaultValueSupplier 默认值提供者
	 * @param <T>                  对象类型（必须实现CharSequence接口）
	 * @return 被检查对象为{@code null}返回默认值，否则返回自定义handle处理后的返回值
	 * @throws NullPointerException {@code defaultValueSupplier == null} 时，抛出
	 */
	public static <T extends CharSequence> T defaultIfBlank(T str, Function<T, ? extends T> defaultValueSupplier) {
		if (StringUtils.isBlank(str)) {
			return defaultValueSupplier.apply(null);
		}
		return str;
	}

	// endregion


	// region 序列化与克隆

	/**
	 * 序列化<br>
	 * 对象必须实现Serializable接口
	 *
	 * @param <T> 对象类型
	 * @param obj 要被序列化的对象
	 * @return 序列化后的字节码
	 */
	public static <T> byte[] serialize(T obj) {
		return org.springframework.util.SerializationUtils.serialize(obj);
	}


	@SuppressWarnings("unchecked")
/*
	public static <T> T deserialize(byte[] bytes, Class<?>... acceptClasses) {
		return (T) Serializations.deserialize(bytes, acceptClasses);
	}
*/

	/**
	 * 深度克隆对象<br>
	 * 如果对象实现Copyable接口，调用其copy方法<br>
	 * 如果对象是集合或Map，遍历元素进行克隆<br>
	 * 如果对象实现Cloneable接口，调用其clone方法<br>
	 * 如果实现Serializable接口，通过序列化方式克隆<br>
	 * 如果是无处理处理的类型或过程存在异常，返回{@code null}
	 *
	 * @param <T> 对象类型
	 * @param obj 被克隆对象
	 * @return 克隆后的对象
	 */
	public static <T> T clone(T obj) {
		return clone(obj, new HashMap<>());
	}
	public static <T> T cloneByStream(T obj) {
		return SerializationUtils.clone(obj);
	}

	private static <T> T clone(T obj, Map<Object, Object> resolvedObjects) {
		if (obj == null) {
			return null;
		}
		if (!resolvedObjects.isEmpty()) {
			Object v = resolvedObjects.get(obj);
			if (v != null) {
				return (T) v;
			}
		}
		try {
  if (obj instanceof Optional) {
				return (T) ((Optional<?>) obj).map(v -> clone(v, resolvedObjects));
			} else {
				Class<?> type = obj.getClass();
				if (obj instanceof Map) {
					Map rs = (Map) ReflectUtils.newInstanceIfPossible(type);
					if (rs != null) {
						resolvedObjects.put(obj, (T) rs);
						((Map) obj).forEach((k, v) -> {
							rs.put(clone(k, resolvedObjects), clone(v, resolvedObjects));
						});
						return (T) rs;
					}
				} else if (obj instanceof Collection) {
					Collection rs = (Collection) ReflectUtils.newInstanceIfPossible(type);
					if (rs != null) {
						resolvedObjects.put(obj, (T) rs);
						for (Object o : ((Collection<?>) obj)) {
							rs.add(clone(o, resolvedObjects));
						}
						return (T) rs;
					}
				} else if (type.isArray()) {
					final Object result;
					final Class<?> componentType = type.getComponentType();
					int length = Array.getLength(obj);
					result = Array.newInstance(componentType, length);
					while (length-- > 0) {
						Object o = clone(Array.get(obj, length), resolvedObjects);
						Array.set(result, length, o);
					}
					return (T) result;
				} else if (type.isPrimitive()
					|| type.isEnum()
					|| obj instanceof String
|| Types.isPrimitiveWrapper(type)

					|| obj instanceof Type
					|| obj instanceof Date
					|| obj instanceof BigDecimal
					|| obj instanceof BigInteger
				) {
					return obj;
				}
			}
			T cloned;
			if (obj instanceof Cloneable) {
				cloned = Methods.invokeMethod(obj, Methods.getAccessibleMethod(obj.getClass(), "clone"));
			} else {
				cloned = cloneByStream(obj);
			}
			resolvedObjects.put(obj, cloned);
			return cloned;
		} catch (Exception e) {
			// pass
		}
		return null;
	}

	/**
	 * 返回克隆后的对象，如果克隆失败，返回原对象
	 *
	 * @param <T> 对象类型
	 * @param obj 对象
	 * @return 克隆后或原对象
	 */
	public static <T> T cloneIfPossible(final T obj) {
		T clone = null;
		try {
			clone = clone(obj);
		} catch (Exception e) {
			// pass
		}
		return clone == null ? obj : clone;
	}


	/** @see Objects#compare(Object, Object, Comparator) */
	public static <T> int compare(T a, T b, Comparator<? super T> c) {
		return (a == b) ? 0 : c.compare(a, b);
	}


	/**
	 * 计算对象长度，如果是字符串调用其length函数，集合类调用其size函数，数组调用其length属性，其他可遍历对象遍历计算长度<br>
	 * 支持的类型包括：
	 * <ul>
	 * <li>CharSequence</li>
	 * <li>Map</li>
	 * <li>Iterator</li>
	 * <li>Enumeration</li>
	 * <li>Array</li>
	 * </ul>
	 * @param obj 被计算长度的对象
	 * @return 长度
	 */
	public static int length(Object obj) {
		if (obj == null) {
			return 0;
		}
		if (obj instanceof CharSequence) {
			return ((CharSequence) obj).length();
		}
		if (obj instanceof Collection) {
			return ((Collection<?>) obj).size();
		}
		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).size();
		}

		int count;
		if (obj instanceof Iterator) {
			final Iterator<?> iter = (Iterator<?>) obj;
			count = 0;
			while (iter.hasNext()) {
				count++;
				iter.next();
			}
			return count;
		}
		if (obj instanceof Enumeration) {
			final Enumeration<?> enumeration = (Enumeration<?>) obj;
			count = 0;
			while (enumeration.hasMoreElements()) {
				count++;
				enumeration.nextElement();
			}
			return count;
		}
		if (obj.getClass().isArray()) {
			return Array.getLength(obj);
		}
		return -1;
	}

	/**
	 * 对象中是否包含元素<br>
	 * 支持的对象类型包括：
	 * <ul>
	 * <li>String</li>
	 * <li>Collection</li>
	 * <li>Map</li>
	 * <li>Iterator</li>
	 * <li>Enumeration</li>
	 * <li>Array</li>
	 * </ul>
	 *
	 * @param obj     对象
	 * @param element 元素
	 * @return 是否包含
	 */
	public static boolean contains(Object obj, Object element) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof String) {
			if (element == null) {
				return false;
			}
			return ((String) obj).contains(element.toString());
		}
		if (obj instanceof Collection) {
			return ((Collection<?>) obj).contains(element);
		}
		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).containsValue(element);
		}

		if (obj instanceof Iterator) {
			final Iterator<?> iter = (Iterator<?>) obj;
			while (iter.hasNext()) {
				final Object o = iter.next();
				if (equals(o, element)) {
					return true;
				}
			}
			return false;
		}
		if (obj instanceof Enumeration) {
			final Enumeration<?> enumeration = (Enumeration<?>) obj;
			while (enumeration.hasMoreElements()) {
				final Object o = enumeration.nextElement();
				if (equals(o, element)) {
					return true;
				}
			}
			return false;
		}
		if (obj.getClass().isArray()) {
			final int len = Array.getLength(obj);
			for (int i = 0; i < len; i++) {
				final Object o = Array.get(obj, i);
				if (equals(o, element)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @see Objects#isNull(Object)
	 */
	public static boolean isNull(Object obj) {
		return obj == null;
	}

	/** @see Objects#nonNull(Object) */
	public static boolean isNotNull(Object obj) {
		return obj != null;
	}

	/**
	 * 判断指定对象是否为空，支持：
	 *
	 * <pre>
	 * 1. CharSequence
	 * 2. Map
	 * 3. Iterable
	 * 4. Iterator
	 * 5. Array
	 * </pre>
	 *
	 * @param obj 被判断的对象
	 * @return 是否为空，如果类型不支持，返回false
	 */
	public static boolean isEmpty(Object obj) {
		if (null == obj) {
			return true;
		}
		if (obj instanceof CharSequence) {
			return StringUtils.isEmpty((CharSequence) obj);
		} else if (obj instanceof Map) {
			return ((Map<?, ?>) obj).isEmpty();
		} else if (obj instanceof Iterable) {
			return !((Iterable<?>) obj).iterator().hasNext();
		} else if (obj instanceof Iterator) {
			return !((Iterator<?>) obj).hasNext();
		} else if (ArrayUtils.isArray(obj)) {
			return ObjectUtils.isEmpty(obj);
		}
		return false;
	}

	/**
	 * 判断指定对象是否为非空，支持：
	 *
	 * <pre>
	 * 1. CharSequence
	 * 2. Map
	 * 3. Iterable
	 * 4. Iterator
	 * 5. Array
	 * </pre>
	 *
	 * @param obj 被判断的对象
	 * @return 是否为空，如果类型不支持，返回true
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	/**
	 * 判断指定对象是否为空，支持：
	 *
	 * <pre>
	 * 1. CharSequence
	 * 2. Map
	 * 3. Iterable
	 * 4. Iterator
	 * 5. Array
	 * </pre>
	 *
	 * @param obj 被判断的对象
	 * @return 是否为空，如果类型不支持，返回false
	 */
	public static boolean isBlank(Object obj) {
		if (null == obj) {
			return true;
		}
		if (obj instanceof CharSequence) {
			return StringUtils.isBlank((CharSequence) obj);
		} else if (obj instanceof Map) {
			return ((Map<?, ?>) obj).isEmpty();
		} else if (obj instanceof Iterable) {
			return !((Iterable<?>) obj).iterator().hasNext();
		} else if (obj instanceof Iterator) {
			return !((Iterator<?>) obj).hasNext();
		} else if (ArrayUtils.isArray(obj)) {
			return ObjectUtils.isEmpty(obj);
		}
		return false;
	}

	/**
	 * 判断指定对象是否为非空，支持：
	 *
	 * <pre>
	 * 1. CharSequence
	 * 2. Map
	 * 3. Iterable
	 * 4. Iterator
	 * 5. Array
	 * </pre>
	 *
	 * @param obj 被判断的对象
	 * @return 是否为空，如果类型不支持，返回true
	 */
	public static boolean isNotBlank(Object obj) {
		return !isBlank(obj);
	}

	/**
	 * 获得给定类的第一个泛型参数
	 *
	 * @param obj 被检查的对象
	 * @return {@link Class}
	 */
	public static Class<?> getTypeArgument(Object obj) {
		return getTypeArgument(obj, 0);
	}

	/**
	 * 获得给定类的第一个泛型参数
	 *
	 * @param obj   被检查的对象
	 * @param index 泛型类型的索引号，即第几个泛型类型
	 * @return {@link Class}
	 */
	public static Class<?> getTypeArgument(Object obj, int index) {
		Type type = Types.getTypeArgument(obj.getClass(), index);
		return type == null ? null : Types.getClass(type);
	}




	/**
	 * 是否存在{@code null}对象，通过{@link Objs#isNull(Object)} 判断元素
	 *
	 * @param objs 被检查对象
	 * @return 是否存在
	 */
	public static boolean hasNull(Object... objs) {
		return ArrayUtils.isEmpty(objs);
	}

	/**
	 * 是否存在{@code null}或空对象，通过{@link Objs#isEmpty(Object)} 判断元素
	 *
	 * @param objs 被检查对象
	 * @return 是否存在
	 */
	public static boolean hasEmpty(Object... objs) {
		return ArrayUtils.isEmpty(objs);
	}

	/**
	 * 是否全都为{@code null}或空对象，通过{@link Objs#isEmpty(Object)} 判断元素
	 *
	 * @param objs 被检查的对象,一个或者多个
	 * @return 是否都为空
	 */
	public static boolean isAllEmpty(Object... objs) {
		return ArrayUtils.isEmpty(objs);
	}

	/**
	 * 是否全都不为{@code null}或空对象，通过{@link Objs#isEmpty(Object)} 判断元素
	 *
	 * @param objs 被检查的对象,一个或者多个
	 * @return 是否都不为空
	 */
	public static boolean isAllNotEmpty(Object... objs) {
		return ArrayUtils.isNotEmpty(objs);
	}

	// endregion

}
