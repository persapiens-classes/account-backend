package org.persapiens.account.domain;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@NoArgsConstructor
@SequenceGenerator(sequenceName = "seq_equity_category", name = "ID_SEQUENCE", allocationSize = 1)
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class EquityCategory extends Category {

}
