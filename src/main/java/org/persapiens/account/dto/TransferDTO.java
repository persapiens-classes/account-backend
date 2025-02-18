package org.persapiens.account.dto;

import java.math.BigDecimal;

public record TransferDTO(String debitOwner, String debitAccount, String creditOwner, String creditAccount,
		BigDecimal value) {

}
