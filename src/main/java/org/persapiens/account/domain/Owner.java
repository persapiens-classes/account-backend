package org.persapiens.account.domain;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.ToString;
import lombok.Singular;
import lombok.EqualsAndHashCode;

@SequenceGenerator(sequenceName = "seq_owner", name = "ID_SEQUENCE", allocationSize = 1)
@Entity
@EqualsAndHashCode(of = "name")
@ToString(of = "name")
@SuperBuilder
@Getter
@Setter
public class Owner implements Comparable<Owner> {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQUENCE")
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "owner")
    @Singular("entry")
    private Set<Entry> entries;

    @OneToMany(mappedBy = "owner")
    @Singular
    private Set<OwnerEquityAccountInitialValue> ownerEquityAccountInitialValues;

    @Override
    public int compareTo(Owner o) {
        return this.name.compareTo(o.name);
    }
    
}
