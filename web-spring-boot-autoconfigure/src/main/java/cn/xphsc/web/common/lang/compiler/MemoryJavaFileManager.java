package cn.xphsc.web.common.lang.compiler;

import javax.tools.*;
import java.io.IOException;
import java.net.URI;
import java.util.*;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  MemoryJavaFileManager
 * @since 2.0.4
 */
class MemoryJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {

	private final MemoryClassLoader classLoader;
	private final Map<URI, JavaFileObject> fileObjects = new HashMap<>();

	public MemoryJavaFileManager(JavaFileManager fileManager, MemoryClassLoader classLoader) {
		super(fileManager);
		this.classLoader = classLoader;
	}

	@Override
	public FileObject getFileForInput(Location location, String packageName, String relativeName) throws IOException {
		URI uri = URI.create(location.getName() + '/' + packageName.replace('.', '/') + '/' + relativeName + JavaFileObject.Kind.SOURCE.extension);
		FileObject o = fileObjects.get(uri);
		if (o != null) {
			return o;
		}
		return super.getFileForInput(location, packageName, relativeName);
	}

	public void putFileForInput(StandardLocation location, String classFullName, JavaFileObject file) {
		URI uri = URI.create(location.getName() + '/' + classFullName.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension);
		fileObjects.put(uri, file);
	}

	@Override
	public JavaFileObject getJavaFileForOutput(Location location, String qualifiedName, JavaFileObject.Kind kind, FileObject outputFile)
		throws IOException {
		URI uri = URI.create(qualifiedName.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension);
		MemoryStreamableJavaFileObject file = new MemoryStreamableJavaFileObject(uri, kind);
		classLoader.add(qualifiedName, file);
		return file;
	}

	@Override
	public ClassLoader getClassLoader(Location location) {
		return classLoader;
	}


	@Override
	public String inferBinaryName(Location location, JavaFileObject file) {
		if (file instanceof MemoryJavaFileObject) {
			return file.getName();
		}
		return super.inferBinaryName(location, file);
	}

	@Override
	public Iterable<JavaFileObject> list(Location location, String packageName, Set<JavaFileObject.Kind> kinds, boolean recurse)
		throws IOException {
		Iterable<JavaFileObject> result = super.list(location, packageName, kinds, recurse);
		List<JavaFileObject> files = new ArrayList<>();
		if (location == StandardLocation.CLASS_PATH && kinds.contains(JavaFileObject.Kind.CLASS)) {
			for (JavaFileObject file : fileObjects.values()) {
				if (file.getKind() == JavaFileObject.Kind.CLASS && file.getName().startsWith(packageName)) {
					files.add(file);
				}
			}
			files.addAll(classLoader.files());
		} else if (location == StandardLocation.SOURCE_PATH && kinds.contains(JavaFileObject.Kind.SOURCE)) {
			for (JavaFileObject file : fileObjects.values()) {
				if (file.getKind() == JavaFileObject.Kind.SOURCE && file.getName().startsWith(packageName)) {
					files.add(file);
				}
			}
		}
		for (JavaFileObject file : result) {
			files.add(file);
		}
		return files;
	}
}
