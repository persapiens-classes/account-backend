package org.persapiens.account.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@ToString
@NoArgsConstructor
public class EntryInsertUpdateDTO {

	private LocalDateTime date;

	private BigDecimal value;

	private String inAccount;

	private String outAccount;

	private String owner;

	private String note;

}
