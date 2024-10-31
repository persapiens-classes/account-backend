package org.persapiens.account.domain;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class Owner {

    private String name;

    private Set<Entry> entries;

    private Set<OwnerEquityAccountInitialValue> ownerEquityAccountInitialValues;

}
