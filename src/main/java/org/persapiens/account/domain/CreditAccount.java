package org.persapiens.account.domain;

import jakarta.persistence.Entity;

import lombok.experimental.SuperBuilder;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class CreditAccount extends Account {
}
