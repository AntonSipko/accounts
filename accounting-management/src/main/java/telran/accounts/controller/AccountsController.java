package telran.accounts.controller;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.accounts.api.UrlConstants;
import telran.accounts.model.AccountDto;
import telran.accounts.service.AccountsService;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("accounts")
public class AccountsController {
	final AccountsService accountService;
	@PostMapping
	AccountDto addAccount(@RequestBody @Valid AccountDto accountDto) {
	log.debug("addAccount:recieved Account Data {}",accountDto);
	return accountService.addAccount(accountDto);
	}
	@DeleteMapping(UrlConstants.REMOVE_ACCOUNT)
	AccountDto removeAccount(@RequestBody @Valid @PathVariable String email) {
		log.debug("removeAccount:recieved Account Email {}",email);
		return accountService.removeAccount(email);
	
}

}
