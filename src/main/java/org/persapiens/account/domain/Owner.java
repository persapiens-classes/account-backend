package org.persapiens.account.domain;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.ToString;
import lombok.Singular;

@ToString(of = "name")
@SuperBuilder
@Getter
@Setter
public class Owner {

    private String name;

    @Singular("entry")
    private Set<Entry> entries;

    @Singular
    private Set<OwnerEquityAccountInitialValue> ownerEquityAccountInitialValues;

}
