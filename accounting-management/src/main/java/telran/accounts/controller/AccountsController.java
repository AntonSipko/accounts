package telran.accounts.controller;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.accounts.model.AccountDto;
import telran.accounts.service.AccountsService;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/accounts")
public class AccountsController {
	final AccountsService accountService;
	@PostMapping("/add-account")
	AccountDto addAccount(@RequestBody @Valid AccountDto accountDto) {
	log.debug("addAccount:recieved Account Data {}",accountDto);
	return accountService.addAccount(accountDto);
	}
	@DeleteMapping("/remove/{email}")
	AccountDto removeAccount(@RequestBody @Valid @PathVariable String email) throws Exception {
		log.debug("removeAccount:recieved Account Email {}",email);
		return accountService.removeAccount(email);
	
}
	@PutMapping("/update-password/{email}")
	public ResponseEntity<String> updatePassword(@RequestBody @Valid @PathVariable String email,@RequestBody @Valid String newPassowrd) throws AccountNotFoundException, IllegalAccessException {
		log.debug("updatePassword:recieved Email {} for password updating",email);
		try {
			accountService.updatePassword(email, newPassowrd);
			return ResponseEntity.ok("Password updated successfully");
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
			
		
	}
	

