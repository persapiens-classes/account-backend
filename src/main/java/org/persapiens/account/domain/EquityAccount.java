package org.persapiens.account.domain;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.ToString;
import lombok.Singular;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true, exclude = "ownerEquityAccountInitialValues")
@ToString(callSuper = true, exclude = "ownerEquityAccountInitialValues")
@SuperBuilder
@Getter
@Setter
public class EquityAccount extends Account {

	@OneToMany(mappedBy = "equityAccount")
	@Singular
	private Set<OwnerEquityAccountInitialValue> ownerEquityAccountInitialValues;

}
