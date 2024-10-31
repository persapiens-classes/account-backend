package org.persapiens.account.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(of = {"description", "category"})
@ToString
@SuperBuilder
@Getter
@Setter
public class Account {

    private String description;

    @NonNull
    private Category category;

}
