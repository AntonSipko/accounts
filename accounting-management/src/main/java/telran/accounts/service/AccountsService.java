package telran.accounts.service;

import telran.accounts.model.AccountDto;

public interface AccountsService {
	AccountDto addAccount(AccountDto accountDto);
	AccountDto removeAccount(String email);
	

}
