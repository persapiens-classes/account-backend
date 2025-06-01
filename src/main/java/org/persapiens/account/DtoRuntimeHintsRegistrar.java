package org.persapiens.account;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

public class DtoRuntimeHintsRegistrar implements RuntimeHintsRegistrar {

	/**
	 * Register constructor and public methods of types BigDecimal and LocalDateTime.
	 * Dtos use these types.
	 */
	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		registerFullReflection(hints, BigDecimal.class);
		registerFullReflection(hints, LocalDateTime.class);
	}

	private void registerFullReflection(RuntimeHints hints, Class<?> clazz) {
		// Register all public constructors
		for (Constructor<?> constructor : clazz.getConstructors()) {
			hints.reflection().registerConstructor(constructor, ExecutableMode.INVOKE);
		}

		// Register all public methods
		for (Method method : clazz.getMethods()) {
			hints.reflection().registerMethod(method, ExecutableMode.INVOKE);
		}
	}
}
