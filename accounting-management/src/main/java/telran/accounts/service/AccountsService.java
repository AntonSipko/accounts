package telran.accounts.service;

import javax.security.auth.login.AccountNotFoundException;

import telran.accounts.model.AccountDto;

public interface AccountsService {
	AccountDto addAccount(AccountDto accountDto);
	AccountDto removeAccount(String email) throws Exception;
	void updatePassword(String email,String newPassowrd)throws AccountNotFoundException, IllegalAccessException;
	
	

}
