package org.persapiens.account;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@Configuration
@ImportRuntimeHints(HibernateCacheRuntimeHintsRegistrar.class)
public class HibernateCacheNativeConfig {

}
