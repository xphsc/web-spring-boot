package cn.xphsc.web.logger.support;



import cn.xphsc.web.common.lang.constant.Constants;
import cn.xphsc.web.logger.Logger;
import cn.xphsc.web.utils.StringUtils;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: DynamicSlf4jLogger
 * @since 2.0.4
 */
public class DynamicSlf4jLogger implements Logger {

	private final Class<?> classLogger;
	private final Object instanceLogger;
	private final MethodHandle isTraceEnabled;
	private final MethodHandle isDebugEnabled;
	private final MethodHandle isInfoEnabled;
	private final MethodHandle isWarnEnabled;
	private final MethodHandle isErrorEnabled;
	private final MethodHandle trace1;
	private final MethodHandle trace2;
	private final MethodHandle trace3;
	private final MethodHandle debug1;
	private final MethodHandle debug2;
	private final MethodHandle debug3;
	private final MethodHandle info1;
	private final MethodHandle info2;
	private final MethodHandle info3;
	private final MethodHandle warn1;
	private final MethodHandle warn2;
	private final MethodHandle warn3;
	private final MethodHandle error1;
	private final MethodHandle error2;
	private final MethodHandle error3;

	private org.slf4j.Logger log;

	public DynamicSlf4jLogger(Class<?> classLogger,   Object instanceLogger) throws NoSuchMethodException, IllegalAccessException {
		this.classLogger = classLogger;
		this.instanceLogger = instanceLogger;
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		this.isTraceEnabled = lookup.findVirtual(classLogger, "isTraceEnabled", MethodType.methodType(boolean.class));
		this.isDebugEnabled = lookup.findVirtual(classLogger, "isDebugEnabled", MethodType.methodType(boolean.class));
		this.isInfoEnabled = lookup.findVirtual(classLogger, "isInfoEnabled", MethodType.methodType(boolean.class));
		this.isWarnEnabled = lookup.findVirtual(classLogger, "isWarnEnabled", MethodType.methodType(boolean.class));
		this.isErrorEnabled = lookup.findVirtual(classLogger, "isErrorEnabled", MethodType.methodType(boolean.class));
		this.trace1 = lookup.findVirtual(classLogger, "trace", MethodType.methodType(void.class, String.class));
		this.trace2 = lookup.findVirtual(classLogger, "trace", MethodType.methodType(void.class, String.class, Object[].class));
		this.trace3 = lookup.findVirtual(classLogger, "trace", MethodType.methodType(void.class, String.class, Throwable.class));
		this.debug1 = lookup.findVirtual(classLogger, "debug", MethodType.methodType(void.class, String.class));
		this.debug2 = lookup.findVirtual(classLogger, "debug", MethodType.methodType(void.class, String.class, Object[].class));
		this.debug3 = lookup.findVirtual(classLogger, "debug", MethodType.methodType(void.class, String.class, Throwable.class));
		this.info1 = lookup.findVirtual(classLogger, "info", MethodType.methodType(void.class, String.class));
		this.info2 = lookup.findVirtual(classLogger, "info", MethodType.methodType(void.class, String.class, Object[].class));
		this.info3 = lookup.findVirtual(classLogger, "info", MethodType.methodType(void.class, String.class, Throwable.class));
		this.warn1 = lookup.findVirtual(classLogger, "warn", MethodType.methodType(void.class, String.class));
		this.warn2 = lookup.findVirtual(classLogger, "warn", MethodType.methodType(void.class, String.class, Object[].class));
		this.warn3 = lookup.findVirtual(classLogger, "warn", MethodType.methodType(void.class, String.class, Throwable.class));
		this.error1 = lookup.findVirtual(classLogger, "error", MethodType.methodType(void.class, String.class));
		this.error2 = lookup.findVirtual(classLogger, "error", MethodType.methodType(void.class, String.class, Object[].class));
		this.error3 = lookup.findVirtual(classLogger, "error", MethodType.methodType(void.class, String.class, Throwable.class));
	}

	@Override
	public boolean isTraceEnabled() {
		try {
			return (boolean) isTraceEnabled.invoke(instanceLogger);
		} catch (Throwable e) {
			return false;
		}
	}

	@Override
	public boolean isDebugEnabled() {
		try {
			return (boolean) isDebugEnabled.invoke(instanceLogger);
		} catch (Throwable e) {
			return false;
		}
	}

	@Override
	public boolean isInfoEnabled() {
		try {
			return (boolean) isInfoEnabled.invoke(instanceLogger);
		} catch (Throwable e) {
			return false;
		}
	}

	@Override
	public boolean isWarnEnabled() {
		try {
			return (boolean) isWarnEnabled.invoke(instanceLogger);
		} catch (Throwable e) {
			return false;
		}
	}

	@Override
	public boolean isErrorEnabled() {
		try {
			return (boolean) isErrorEnabled.invoke(instanceLogger);
		} catch (Throwable e) {
			return false;
		}
	}

	@Override
	public void trace(String msg) {
		trace(msg, Constants.OBJECT_ARR_EMPTY, null);
	}

	@Override
	public void trace(String msg, Object... arguments) {
		trace(msg, arguments, null);
	}

	@Override
	public void trace(String msg, Throwable t) {
		trace(msg, Constants.OBJECT_ARR_EMPTY, t);
	}

	@Override
	public void trace(String msg, Object[] arguments, Throwable t) {
		if (instanceLogger != null && isTraceEnabled()) {
			try {
				if (t == null) {
					if (arguments == null || arguments.length == 0) {
						trace1.invoke(instanceLogger, msg);
					} else {
						trace2.invoke(instanceLogger, msg, arguments);
					}
				} else {
					if (arguments == null || arguments.length == 0) {
						trace3.invoke(instanceLogger, msg, t);
					} else {
						trace3.invoke(instanceLogger, StringUtils.format(msg, arguments), t);
					}
				}
			} catch (Throwable e) {
				// noinspection CallToPrintStackTrace
				e.printStackTrace();
			}
		}
	}

	@Override
	public void trace(Throwable t, String msg, Object... arguments) {
		trace(msg, arguments, t);
	}

	@Override
	public void debug(String msg) {
		debug(msg, Constants.OBJECT_ARR_EMPTY, null);
	}

	@Override
	public void debug(String msg, Object... arguments) {
		debug(msg, arguments, null);
	}

	@Override
	public void debug(String msg, Throwable t) {
		debug(msg, Constants.OBJECT_ARR_EMPTY, t);
	}

	@Override
	public void debug(String msg, Object[] arguments, Throwable t) {
		if (instanceLogger != null && isDebugEnabled()) {
			try {
				if (t == null) {
					if (arguments == null || arguments.length == 0) {
						debug1.invoke(instanceLogger, msg);
					} else {
						debug2.invoke(instanceLogger, msg, arguments);
					}
				} else {
					if (arguments == null || arguments.length == 0) {
						debug3.invoke(instanceLogger, msg, t);
					} else {
						debug3.invoke(instanceLogger, StringUtils.format(msg, arguments), t);
					}
				}
			} catch (Throwable e) {
				// noinspection CallToPrintStackTrace
				e.printStackTrace();
			}
		}
	}

	@Override
	public void debug(Throwable t, String msg, Object... arguments) {
		debug(msg, arguments, t);
	}

	@Override
	public void info(String msg) {
		info(msg, Constants.OBJECT_ARR_EMPTY, null);
	}

	@Override
	public void info(String msg, Object... arguments) {
		info(msg, arguments, null);
	}

	@Override
	public void info(String msg, Throwable t) {
		info(msg, Constants.OBJECT_ARR_EMPTY, t);
	}

	@Override
	public void info(String msg, Object[] arguments, Throwable t) {
		if (instanceLogger != null && isInfoEnabled()) {
			try {
				if (t == null) {
					if (arguments == null || arguments.length == 0) {
						info1.invoke(instanceLogger, msg);
					} else {
						info2.invoke(instanceLogger, msg, arguments);
					}
				} else {
					if (arguments == null || arguments.length == 0) {
						info3.invoke(instanceLogger, msg, t);
					} else {
						info3.invoke(instanceLogger, StringUtils.format(msg, arguments), t);
					}
				}
			} catch (Throwable e) {
				// noinspection CallToPrintStackTrace
				e.printStackTrace();
			}
		}
	}

	@Override
	public void info(Throwable t, String msg, Object... arguments) {
		info(msg, arguments, t);
	}

	@Override
	public void warn(String msg) {
		warn(msg, Constants.OBJECT_ARR_EMPTY, null);
	}

	@Override
	public void warn(String msg, Object... arguments) {
		warn(msg, arguments, null);
	}

	@Override
	public void warn(String msg, Throwable t) {
		warn(msg, Constants.OBJECT_ARR_EMPTY, t);
	}

	@Override
	public void warn(String msg, Object[] arguments, Throwable t) {
		if (instanceLogger != null && isWarnEnabled()) {
			try {
				if (t == null) {
					if (arguments == null || arguments.length == 0) {
						warn1.invoke(instanceLogger, msg);
					} else {
						warn2.invoke(instanceLogger, msg, arguments);
					}
				} else {
					if (arguments == null || arguments.length == 0) {
						warn3.invoke(instanceLogger, msg, t);
					} else {
						warn3.invoke(instanceLogger, StringUtils.format(msg, arguments), t);
					}
				}
			} catch (Throwable e) {
				// noinspection CallToPrintStackTrace
				e.printStackTrace();
			}
		}
	}

	@Override
	public void warn(Throwable t, String msg, Object... arguments) {
		warn(msg, arguments, t);
	}

	@Override
	public void error(String msg) {
		error(msg, Constants.OBJECT_ARR_EMPTY, null);
	}

	@Override
	public void error(String msg, Object... arguments) {
		error(msg, arguments, null);
	}

	@Override
	public void error(String msg, Throwable t) {
		error(msg, Constants.OBJECT_ARR_EMPTY, t);
	}


	@Override
	public void error(String msg, Object[] arguments, Throwable t) {
		if (instanceLogger != null && isErrorEnabled()) {
			try {
				if (t == null) {
					if (arguments == null || arguments.length == 0) {
						error1.invoke(instanceLogger, msg);
					} else {
						error2.invoke(instanceLogger, msg, arguments);
					}
				} else {
					if (arguments == null || arguments.length == 0) {
						error3.invoke(instanceLogger, msg, t);
					} else {
						error3.invoke(instanceLogger, StringUtils.format(msg, arguments), t);
					}
				}
			} catch (Throwable e) {
				// noinspection CallToPrintStackTrace
				e.printStackTrace();
			}
		}
	}

	@Override
	public void error(Throwable t, String msg, Object... arguments) {
		error(msg, arguments, t);
	}


}
