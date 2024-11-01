package org.persapiens.account.domain;

import java.util.Comparator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@SequenceGenerator(sequenceName = "seq_account", name = "ID_SEQUENCE", allocationSize = 1)
@Entity
@EqualsAndHashCode(of = {"description", "category"})
@ToString
@SuperBuilder
@Getter
@Setter
public class Account implements Comparable<Account>{

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQUENCE")
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String description;

    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_account_category"))
    @NonNull
    private Category category;

    @Override
    public int compareTo(Account o) {
        return Comparator.comparing(Account::getDescription)
                .thenComparing(Account::getCategory)
                .compare(this, o);
    }
}
