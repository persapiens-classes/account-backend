package org.persapiens.account.graalvmnative;

import com.github.benmanes.caffeine.jcache.copy.Copier;

public class NoOpCopier implements Copier {

	public NoOpCopier() {
	}

	@Override
	public <T> T copy(T object, ClassLoader classLoader) {
		return object;
	}

}
