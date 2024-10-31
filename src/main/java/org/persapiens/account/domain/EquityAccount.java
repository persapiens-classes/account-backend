package org.persapiens.account.domain;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.ToString;
import lombok.Singular;

@ToString(callSuper = true, exclude= "ownerEquityAccountInitialValues")
@SuperBuilder
@Getter
@Setter
public class EquityAccount extends Account {
    
    @Singular
    private Set<OwnerEquityAccountInitialValue> ownerEquityAccountInitialValues;

}
