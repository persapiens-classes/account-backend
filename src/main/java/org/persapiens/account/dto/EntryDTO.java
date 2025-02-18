package org.persapiens.account.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EntryDTO(long id, String owner, LocalDateTime date, AccountDTO inAccount, AccountDTO outAccount,
		BigDecimal value, String note) {
}
