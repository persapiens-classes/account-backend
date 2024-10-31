package org.persapiens.account.domain;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of={"value", "owner", "equityAccount"} )
@ToString
@SuperBuilder
@Getter
@Setter
public class OwnerEquityAccountInitialValue {

    private BigDecimal value;

    private Owner owner;

    private EquityAccount equityAccount;

}
