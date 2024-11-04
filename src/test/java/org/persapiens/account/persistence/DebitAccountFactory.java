package org.persapiens.account.persistence;

import java.util.Optional;

import static org.persapiens.account.common.DebitAccountConstants.GASOLINE;
import org.persapiens.account.domain.Category;
import org.persapiens.account.domain.DebitAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DebitAccountFactory {

    @Autowired
    private DebitAccountRepository debitAccountRepository;

    @Autowired
    private CategoryFactory categoryFactory;

    public DebitAccount debitAccount(String description, Category category) {
        Optional<DebitAccount> findByDescription = this.debitAccountRepository.findByDescription(description);
        if (findByDescription.isEmpty()) {
            DebitAccount debitAccount = DebitAccount.builder()
                    .description(description)
                    .category(category)
                    .build();
            return this.debitAccountRepository.save(debitAccount);
        } else {
            return findByDescription.get();
        }
    }

    public DebitAccount gasoline() {
        return debitAccount(GASOLINE, this.categoryFactory.transport());
    }
}
