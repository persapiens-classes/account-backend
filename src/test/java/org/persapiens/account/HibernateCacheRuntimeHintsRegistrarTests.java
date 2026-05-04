package org.persapiens.account;

import java.lang.reflect.Constructor;

import com.github.benmanes.caffeine.jcache.spi.CaffeineCachingProvider;
import org.hibernate.cache.jcache.internal.JCacheRegionFactory;
import org.junit.jupiter.api.Test;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.predicate.RuntimeHintsPredicates;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateCacheRuntimeHintsRegistrarTests {

	@Test
	void shouldRegisterHints() throws NoSuchMethodException {
		RuntimeHints hints = new RuntimeHints();
		new HibernateCacheRuntimeHintsRegistrar().registerHints(hints, getClass().getClassLoader());

		Constructor<JCacheRegionFactory> jCacheRegionFactoryConstructor = JCacheRegionFactory.class.getConstructor();
		assertThat(RuntimeHintsPredicates.reflection().onType(JCacheRegionFactory.class)).accepts(hints);
		assertThat(RuntimeHintsPredicates.reflection().onConstructorInvocation(jCacheRegionFactoryConstructor)).accepts(hints);

		Constructor<CaffeineCachingProvider> caffeineCachingProviderConstructor = CaffeineCachingProvider.class.getConstructor();
		assertThat(RuntimeHintsPredicates.reflection().onType(CaffeineCachingProvider.class)).accepts(hints);
		assertThat(RuntimeHintsPredicates.reflection().onConstructorInvocation(caffeineCachingProviderConstructor)).accepts(hints);
	}

}
