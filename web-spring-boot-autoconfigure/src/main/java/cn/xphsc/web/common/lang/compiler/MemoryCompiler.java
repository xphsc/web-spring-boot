package cn.xphsc.web.common.lang.compiler;


import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  MemoryCompiler
 * @since 2.0.4
 */
public class MemoryCompiler implements Compiler {

	private final JavaCompiler compiler;
	private final MemoryClassLoader memoryClassLoader;
	private final List<String> options = new ArrayList<>();
	private final List<File> classPathFiles;

	private static class Holder {
		private static final Map<ClassLoader, MemoryCompiler> COMPILERS = new ConcurrentHashMap<>();

		public static MemoryCompiler get(ClassLoader classLoader) {
			classLoader = classLoader == null ? Thread.currentThread().getContextClassLoader() : classLoader;
			return COMPILERS.computeIfAbsent(classLoader, MemoryCompiler::new);
		}
	}

	public static MemoryCompiler getInstance() {
		return Holder.get(Thread.currentThread().getContextClassLoader());
	}

	public static MemoryCompiler getInstance(ClassLoader classLoader) {
		return Holder.get(classLoader);
	}

	public static List<String> defaultOption() {
		return Arrays.asList("-source", "1.8", "-target", "1.8", "-encoding", Charset.defaultCharset().name());
	}

	public MemoryCompiler() {
		this(Thread.currentThread().getContextClassLoader(), defaultOption());
	}

	public MemoryCompiler(List<String> options) {
		this(Thread.currentThread().getContextClassLoader(), options);
	}

	public MemoryCompiler(ClassLoader parentClassLoader) {
		this(parentClassLoader, defaultOption());
	}

	public MemoryCompiler(ClassLoader loader, List<String> options) {
		this.options.addAll(options);
		this.compiler = ToolProvider.getSystemJavaCompiler();
		this.memoryClassLoader = MemoryClassLoader.getInstance(loader);
		Set<String> classPaths = memoryClassLoader.getClassPaths();
		List<File> files = new ArrayList<>();
		for (String classPath : classPaths) {
			files.add(new File(classPath));
		}
		this.classPathFiles = files;

	}


	public byte[] getClassBytes(String name) {
		return memoryClassLoader.getMemoryClassBytes(name);
	}

	public Class<?> compile(String className, String sourceCode) throws ClassNotFoundException {
		DiagnosticCollector<? super JavaFileObject> diagnostics = new DiagnosticCollector<>();
		StandardJavaFileManager manager = this.compiler.getStandardFileManager(diagnostics, null, null);
		try {
			manager.setLocation(StandardLocation.CLASS_PATH, classPathFiles);
		} catch (IOException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}

		MemoryStreamableJavaFileObject javaFileObject = new MemoryStreamableJavaFileObject(className, sourceCode);
		MemoryJavaFileManager javaFileManager = new MemoryJavaFileManager(manager, memoryClassLoader);
		javaFileManager.putFileForInput(StandardLocation.SOURCE_PATH, className, javaFileObject);
		JavaCompiler.CompilationTask task = compiler.getTask(null, javaFileManager, diagnostics, options,
			null, Collections.singletonList(javaFileObject));
		Boolean rs = task.call();
		if (rs == null || !rs) {
			throw new IllegalStateException(compileError(className, diagnostics));
		}
		return memoryClassLoader.loadClass(className);
	}


	String compileError(String name, DiagnosticCollector<? super JavaFileObject> diagnostics) {
		StringBuilder sb = new StringBuilder();
		sb.append("Compilation error. class: ").append(name).append(" , diagnostics:\n");
		for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
			sb.append(diagnostic.toString()).append("\n");
		}
		return sb.toString();
	}

}
