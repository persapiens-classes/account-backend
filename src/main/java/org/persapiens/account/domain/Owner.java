package org.persapiens.account.domain;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.ToString;
import lombok.Singular;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "name")
@ToString(of = "name")
@SuperBuilder
@Getter
@Setter
public class Owner implements Comparable<Owner> {

    private String name;

    @Singular("entry")
    private Set<Entry> entries;

    @Singular
    private Set<OwnerEquityAccountInitialValue> ownerEquityAccountInitialValues;

    @Override
    public int compareTo(Owner o) {
        return this.name.compareTo(o.name);
    }
    
}
