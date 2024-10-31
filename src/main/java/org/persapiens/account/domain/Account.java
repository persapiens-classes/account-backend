package org.persapiens.account.domain;

import java.util.Comparator;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(of = {"description", "category"})
@ToString
@SuperBuilder
@Getter
@Setter
public class Account implements Comparable<Account>{

    private String description;

    @NonNull
    private Category category;

    @Override
    public int compareTo(Account o) {
        return Comparator.comparing(Account::getDescription)
                .thenComparing(Account::getCategory)
                .compare(this, o);
    }
}
