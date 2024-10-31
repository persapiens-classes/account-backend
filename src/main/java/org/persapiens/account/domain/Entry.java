package org.persapiens.account.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class Entry {

    private Owner owner;

    private Account inAccount;

    private Account outAccount;

    private BigDecimal value;

    private LocalDateTime date;

    private String note;

    public void verifyAttributes() {
        if (this.inAccount instanceof CreditAccount) {
            throw new IllegalArgumentException("In account cannot be of type CreditAccount: " + this.inAccount);
        }
        if (this.outAccount instanceof DebitAccount) {
            throw new IllegalArgumentException("Out account cannot be of type DebitAccount: " + this.outAccount);
        }
    }
}
