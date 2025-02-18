package org.persapiens.account.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EntryInsertUpdateDTO(String owner, LocalDateTime date, String inAccount, String outAccount,
		BigDecimal value, String note) {

}
