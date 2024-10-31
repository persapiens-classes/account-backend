package org.persapiens.account.domain;

import lombok.experimental.SuperBuilder;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class DebitAccount extends Account {
}
