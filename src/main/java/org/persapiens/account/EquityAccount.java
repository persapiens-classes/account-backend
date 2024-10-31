package org.persapiens.account.domain;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquityAccount extends Account {
    
    private Set<OwnerEquityAccountInitialValue> ownerEquityAccountInitialValues;

}
