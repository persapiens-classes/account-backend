package org.persapiens.account.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SequenceGenerator(sequenceName = "seq_debit_category", name = "ID_SEQUENCE", allocationSize = 1)
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class DebitCategory extends Category {

}
