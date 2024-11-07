package org.persapiens.account.controller;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.dto.CreditAccountDTO;
import org.persapiens.account.service.CategoryService;
import org.persapiens.account.service.CreditAccountService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/creditAccount")
public class CreditAccountController extends CrudController<CreditAccountDTO, CreditAccount, Long> {

	private CreditAccountService creditAccountService;

	private CategoryService categoryService;

	@Override
	protected CreditAccount toEntity(CreditAccountDTO dto) {
		return CreditAccount.builder()
			.description(dto.getDescription())
			.category(this.categoryService.findByDescription(dto.getCategory().getDescription()).get())
			.build();
	}

	@Override
	protected CreditAccountDTO toDTO(CreditAccount entity) {
		return CreditAccountDTO.builder().description(entity.getDescription()).build();
	}

	@GetMapping("/findByDescription")
	public Optional<CreditAccountDTO> findByDescription(@RequestParam String description) {
		return toDTOOptional(this.creditAccountService.findByDescription(description));
	}

}
