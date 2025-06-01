package org.persapiens.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueInsertDTO;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@RegisterReflectionForBinding({ BigDecimal.class, LocalDateTime.class,
	AccountDTO.class, EntryDTO.class, OwnerEquityAccountInitialValueInsertDTO.class })

public class DtoNativeConfig {

}
