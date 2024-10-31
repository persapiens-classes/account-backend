package org.persapiens.account.domain;

import java.math.BigDecimal;
import java.util.Comparator;

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
public class OwnerEquityAccountInitialValue implements Comparable<OwnerEquityAccountInitialValue>{

    private BigDecimal value;

    private Owner owner;

    private EquityAccount equityAccount;

    @Override
    public int compareTo(OwnerEquityAccountInitialValue o) {
        return Comparator.comparing(OwnerEquityAccountInitialValue::getValue)
                .thenComparing(OwnerEquityAccountInitialValue::getOwner)
                .thenComparing(OwnerEquityAccountInitialValue::getEquityAccount)
                .compare(this, o);
    }

}
