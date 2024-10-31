package org.persapiens.account.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = {"owner", "inAccount", "outAccount", "value", "date"})
@ToString
@SuperBuilder
@Getter
@Setter
public class Entry implements Comparable<Entry> {

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

    @Override
    public int compareTo(Entry o) {
        return Comparator.comparing(Entry::getDate)
                .thenComparing(Entry::getValue)
                .thenComparing(Entry::getOwner)
                .compare(this, o);
    }
}
