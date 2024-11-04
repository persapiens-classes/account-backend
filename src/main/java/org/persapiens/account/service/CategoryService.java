package org.persapiens.account.service;

import org.persapiens.account.domain.Category;
import org.persapiens.account.persistence.CategoryRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends CrudService<Category, Long> {
    @Autowired
    private CategoryRepository categoryRepository;
    
    public Optional<Category> findByDescription(String description) {
        return categoryRepository.findByDescription(description);
    }
    
    private Category category(String description) {
        Optional<Category> findByDescricao = findByDescription(description);
        if (findByDescricao.isEmpty()) {
            Category result = Category.builder().description(description).build();
            return save(result);
        } else {
            return findByDescricao.get();
        }
    }
    
    public Category expenseTransfer() {
        return category(Category.EXPENSE_TRANSFER_CATEGORY);
    }
    
    public Category incomeTransfer() {
        return category(Category.INCOME_TRANSFER_CATEGORY);
    }
}
