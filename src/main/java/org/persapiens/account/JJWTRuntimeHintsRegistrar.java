package org.persapiens.account;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;

public class JJWTRuntimeHintsRegistrar implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		registerSupplierClass(hints, "io.jsonwebtoken.impl.DefaultJwtBuilder$Supplier");
		registerSupplierClass(hints, "io.jsonwebtoken.impl.DefaultJwtParserBuilder$Supplier");
		registerSupplierClass(hints, "io.jsonwebtoken.impl.DefaultJwtHeaderBuilder$Supplier");
		registerSupplierClass(hints, "io.jsonwebtoken.impl.DefaultClaimsBuilder$Supplier");

		registerProviderClass(hints, "io.jsonwebtoken.jackson.io.JacksonSerializer");
		registerProviderClass(hints, "io.jsonwebtoken.jackson.io.JacksonDeserializer");

		hints.resources().registerPattern("META-INF/services/io.jsonwebtoken.io.Serializer");
		hints.resources().registerPattern("META-INF/services/io.jsonwebtoken.io.Deserializer");
	}

	private void registerSupplierClass(RuntimeHints hints, String className) {
		hints.reflection().registerType(TypeReference.of(className), MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
	}

	private void registerProviderClass(RuntimeHints hints, String className) {
		hints.reflection().registerType(TypeReference.of(className), MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS);
	}

}
