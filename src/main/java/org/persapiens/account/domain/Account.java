package org.persapiens.account.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class Account {

    private String description;

    private Category category;

}
