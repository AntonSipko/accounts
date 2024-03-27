package telran.accounts.service;
import telran.exceptions.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.accounts.model.Account;
import telran.accounts.model.AccountDto;
import telran.accounts.repo.AccountsRepo;
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountsService {
	@Autowired
	AccountsRepo accountsRepo;

	@Override
	public AccountDto addAccount(AccountDto accountDto) {
		log.debug("service recieved AccountData {}",accountDto);
		if(accountsRepo.existsById(accountDto.email())) {
			throw new AccountAlreadyExistsException();
		}
		Account accountToAdd =Account.of(accountDto);
		accountsRepo.save(accountToAdd);
		log.debug("Account {} was added",accountToAdd);
		return accountDto;
	}

	@Override
	public AccountDto removeAccount(String email) {
		if(!accountsRepo.existsById(email)) {
			throw new AccountNotFoundException();
			
		}
		Account accountToDelete = accountsRepo.findById(email).orElseThrow();
		accountsRepo.delete(accountToDelete);
		log.debug("Account {} was deleted ",accountToDelete);
		return accountToDelete.build();
	}

}
