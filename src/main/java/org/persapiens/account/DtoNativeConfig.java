package org.persapiens.account;

import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.EntryInsertUpdateDTO;
import org.persapiens.account.dto.ErrorDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueInsertDTO;
import org.persapiens.account.dto.OwnerNameEquityAccountDescription;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@Configuration
@RegisterReflectionForBinding({
	// dtos not automatically registered by spring boot
	AccountDTO.class, CategoryDTO.class, EntryDTO.class, EntryInsertUpdateDTO.class, ErrorDTO.class, OwnerDTO.class,
	OwnerEquityAccountInitialValueDTO.class, OwnerEquityAccountInitialValueInsertDTO.class, OwnerNameEquityAccountDescription.class })
@ImportRuntimeHints(DtoRuntimeHintsRegistrar.class)
public class DtoNativeConfig {

}
