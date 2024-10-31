package org.persapiens.account.domain;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class EquityAccount extends Account {
    
    private Set<OwnerEquityAccountInitialValue> ownerEquityAccountInitialValues;

}
