package org.persapiens.account;

import io.jsonwebtoken.impl.DefaultClaimsBuilder;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import io.jsonwebtoken.impl.DefaultJwtHeaderBuilder;
import io.jsonwebtoken.impl.DefaultJwtParser;
import io.jsonwebtoken.impl.DefaultJwtParserBuilder;
import io.jsonwebtoken.impl.io.StandardCompressionAlgorithms;
import io.jsonwebtoken.impl.security.DefaultDynamicJwkBuilder;
import io.jsonwebtoken.impl.security.DefaultJwkSetBuilder;
import io.jsonwebtoken.impl.security.DefaultJwkSetParserBuilder;
import io.jsonwebtoken.impl.security.DefaultKeyOperationBuilder;
import io.jsonwebtoken.impl.security.DefaultKeyOperationPolicyBuilder;
import io.jsonwebtoken.impl.security.JwksBridge;
import io.jsonwebtoken.impl.security.KeysBridge;
import io.jsonwebtoken.impl.security.StandardEncryptionAlgorithms;
import io.jsonwebtoken.impl.security.StandardKeyAlgorithms;
import io.jsonwebtoken.impl.security.StandardKeyOperations;
import io.jsonwebtoken.impl.security.StandardSecureDigestAlgorithms;
import io.jsonwebtoken.security.SignatureAlgorithm;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@RegisterReflectionForBinding({ DefaultClaimsBuilder.class, DefaultJwtParser.class, DefaultJwtHeaderBuilder.class,
		DefaultJwtBuilder.class, DefaultJwtParserBuilder.class, StandardCompressionAlgorithms.class,
		DefaultDynamicJwkBuilder.class, DefaultJwkSetBuilder.class, DefaultJwkSetParserBuilder.class,
		DefaultKeyOperationBuilder.class, DefaultKeyOperationPolicyBuilder.class, JwksBridge.class, KeysBridge.class,
		StandardEncryptionAlgorithms.class, StandardKeyAlgorithms.class, StandardKeyOperations.class,
		StandardSecureDigestAlgorithms.class, SignatureAlgorithm.class })

public class JJWTNativeConfig {

}
