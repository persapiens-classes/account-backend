package org.persapiens.account.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = {"description", "category"})
@ToString
@SuperBuilder
@Getter
@Setter
public class Account {

    private String description;

    private Category category;

}
