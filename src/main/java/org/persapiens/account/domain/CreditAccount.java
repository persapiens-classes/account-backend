package org.persapiens.account.domain;

import jakarta.persistence.Entity;

import lombok.experimental.SuperBuilder;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class CreditAccount extends Account {
}
