package org.persapiens.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueInsertDTO;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@Configuration
@RegisterReflectionForBinding({
	// types not automatically registered by spring boot
	BigDecimal.class, LocalDateTime.class,
	// dtos not automatically registered by spring boot
	AccountDTO.class, EntryDTO.class, OwnerEquityAccountInitialValueInsertDTO.class })
@ImportRuntimeHints(DtoRuntimeHintsRegistrar.class)
public class DtoNativeConfig {

}
