package org.persapiens.account.graalvmnative;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;

public class HibernateCacheRuntimeHintsRegistrar implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		registerPublicConstructors(hints, "org.hibernate.cache.jcache.internal.JCacheRegionFactory");
		registerPublicConstructors(hints, "com.github.benmanes.caffeine.jcache.spi.CaffeineCachingProvider");
		registerPublicConstructors(hints, "com.github.benmanes.caffeine.jcache.copy.JavaSerializationCopier");
		registerPublicConstructors(hints, "org.persapiens.account.graalvmnative.NoOpCopier");

		hints.resources().registerPattern("META-INF/services/javax.cache.spi.CachingProvider");
		hints.resources().registerPattern("reference.conf");
		hints.resources().registerPattern("application.conf");
	}

	private void registerPublicConstructors(RuntimeHints hints, String className) {
		hints.reflection().registerType(TypeReference.of(className), MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
	}

}
