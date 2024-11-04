package org.persapiens.account.dto;

import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@NoArgsConstructor
public class DebitAccountDTO extends AccountDTO {
}