package cn.xphsc.web.common.lang.compiler;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  Compiler
 * @since 2.0.4
 */
public interface Compiler {

	Class<?> compile(String className, String sourceCode) throws ClassNotFoundException;
}
