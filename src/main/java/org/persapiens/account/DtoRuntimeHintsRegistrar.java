package org.persapiens.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

public class DtoRuntimeHintsRegistrar implements RuntimeHintsRegistrar {

	/**
	 * Register constructor and public methods of types BigDecimal and LocalDateTime.
	 * Dtos use these types.
	 * These types are not automatically registered by spring boot.
	 */
	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		registerFullReflection(hints, BigDecimal.class);
		registerFullReflection(hints, LocalDateTime.class);
	}

	private void registerFullReflection(RuntimeHints hints, Class<?> clazz) {
		// Register all public constructors and public methods
		hints.reflection().registerType(clazz, MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS, MemberCategory.INVOKE_PUBLIC_METHODS);
	}
}
