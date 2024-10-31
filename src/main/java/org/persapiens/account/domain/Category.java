package org.persapiens.account.domain;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.ToString;
import lombok.Singular;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "description")
@ToString(of = "description")
@SuperBuilder
@Getter
@Setter
public class Category {

    private String description;

    @Singular
    private Set<Account> accounts;

}