package org.persapiens.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.predicate.RuntimeHintsPredicates;

import static org.assertj.core.api.Assertions.assertThat;

class DtoRuntimeHintsRegistrarTests {

	@Test
	void shouldRegisterHints() {
		RuntimeHints hints = new RuntimeHints();
		new DtoRuntimeHintsRegistrar().registerHints(hints, getClass().getClassLoader());
		assertThat(RuntimeHintsPredicates.reflection().onType(BigDecimal.class)).accepts(hints);
		// testing BigDecimal constructor and public methods
		assertThat(RuntimeHintsPredicates.reflection().onConstructor(BigDecimal.class.getConstructors()[0])).accepts(hints);
		assertThat(RuntimeHintsPredicates.reflection().onConstructor(BigDecimal.class.getConstructors()[1])).accepts(hints);
		assertThat(RuntimeHintsPredicates.reflection().onMethod(BigDecimal.class, "shortValueExact")).accepts(hints);
		assertThat(RuntimeHintsPredicates.reflection().onMethod(BigDecimal.class, "ulp")).accepts(hints);
		// testing LocalDateTime public methods
		assertThat(RuntimeHintsPredicates.reflection().onType(LocalDateTime.class)).accepts(hints);
		assertThat(RuntimeHintsPredicates.reflection().onMethod(LocalDateTime.class, "isAfter")).accepts(hints);
		assertThat(RuntimeHintsPredicates.reflection().onMethod(LocalDateTime.class, "isBefore")).accepts(hints);
	}

}
