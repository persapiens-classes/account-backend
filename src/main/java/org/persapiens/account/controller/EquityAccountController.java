package org.persapiens.account.controller;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.service.CategoryService;
import org.persapiens.account.service.EquityAccountService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/equityAccount")
public class EquityAccountController extends CrudController<EquityAccountDTO, EquityAccount, Long> {

	private EquityAccountService equityAccountService;

	private CategoryService categoryService;

	@Override
	protected EquityAccount toEntity(EquityAccountDTO dto) {
		return EquityAccount.builder()
			.description(dto.getDescription())
			.category(this.categoryService.findByDescription(dto.getCategory().getDescription()).get())
			.build();
	}

	@Override
	protected EquityAccountDTO toDTO(EquityAccount entity) {
		return EquityAccountDTO.builder().description(entity.getDescription()).build();
	}

	@GetMapping("/findByDescription")
	public Optional<EquityAccountDTO> findByDescription(@RequestParam String description) {
		return toDTOOptional(this.equityAccountService.findByDescription(description));
	}

}
