package org.persapiens.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.persapiens.account.dto.AccountDTO;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@RegisterReflectionForBinding({ BigDecimal.class, LocalDateTime.class, AccountDTO.class })

public class DtoNativeConfig {

}
