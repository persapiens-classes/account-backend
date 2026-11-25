package org.persapiens.account.graalvmnative;

import com.github.benmanes.caffeine.jcache.copy.Copier;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoOpCopier implements Copier {

	@Override
	public <T> T copy(T object, ClassLoader classLoader) {
		return object;
	}

}
