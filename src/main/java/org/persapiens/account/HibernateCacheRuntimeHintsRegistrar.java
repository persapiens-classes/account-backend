package org.persapiens.account;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;

public class HibernateCacheRuntimeHintsRegistrar implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		registerPublicConstructors(hints, "org.hibernate.cache.jcache.internal.JCacheRegionFactory");
		registerPublicConstructors(hints, "com.github.benmanes.caffeine.jcache.spi.CaffeineCachingProvider");

		hints.resources().registerPattern("META-INF/services/javax.cache.spi.CachingProvider");
	}

	private void registerPublicConstructors(RuntimeHints hints, String className) {
		hints.reflection().registerType(TypeReference.of(className), MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
	}

}
