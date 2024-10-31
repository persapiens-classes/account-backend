package org.persapiens.account.domain;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {

    private String description;

    private Set<Account> accounts;

}