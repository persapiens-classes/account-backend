package org.persapiens.account.graalvmnative;

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
		registerSupplierClass(hints, "io.jsonwebtoken.impl.security.DefaultDynamicJwkBuilder$Supplier");
		registerSupplierClass(hints, "io.jsonwebtoken.impl.security.DefaultJwkParserBuilder$Supplier");
		registerSupplierClass(hints, "io.jsonwebtoken.impl.security.DefaultJwkSetBuilder$Supplier");
		registerSupplierClass(hints, "io.jsonwebtoken.impl.security.DefaultJwkSetParserBuilder$Supplier");
		registerSupplierClass(hints, "io.jsonwebtoken.impl.security.DefaultKeyOperationBuilder$Supplier");
		registerSupplierClass(hints, "io.jsonwebtoken.impl.security.DefaultKeyOperationPolicyBuilder$Supplier");

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
