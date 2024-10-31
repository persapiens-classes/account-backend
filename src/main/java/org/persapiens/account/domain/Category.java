package org.persapiens.account.domain;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.ToString;

@ToString(of = "description")
@SuperBuilder
@Getter
@Setter
public class Category {

    private String description;

    private Set<Account> accounts;

}