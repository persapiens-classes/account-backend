package org.persapiens.account.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SequenceGenerator(sequenceName = "seq_category", name = "ID_SEQUENCE", allocationSize = 1)
@Entity
@EqualsAndHashCode(of = "description")
@ToString(of = "description")
@SuperBuilder
@Getter
@Setter
public class Category implements Comparable<Category> {

	/**
	 * Expense transfer category. It is a debit category.
	 */
	public static final String EXPENSE_TRANSFER_CATEGORY = "expense transfer category";

	/**
	 * Income transfer category. It is a credit category.
	 */
	public static final String INCOME_TRANSFER_CATEGORY = "income transfer category";

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQUENCE")
	@Id
	private Long id;

	@Column(nullable = false, unique = true)
	private String description;

	@Override
	public int compareTo(Category o) {
		return this.description.compareTo(o.getDescription());
	}

}
