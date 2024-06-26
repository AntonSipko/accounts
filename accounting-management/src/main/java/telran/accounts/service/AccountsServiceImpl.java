package telran.accounts.service;
import telran.exceptions.*;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.accounts.model.Account;
import telran.accounts.model.AccountDto;
import telran.accounts.repo.AccountsRepo;
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountsServiceImpl implements AccountsService {
	@Autowired
	final MongoTemplate mongoTemplate;
	final PasswordEncoder passEncoder;
	AccountsRepo accountRepo;
	

	@Override
	public AccountDto addAccount(AccountDto accountDto) {
		String email = accountDto.email();
		Account account = null;
		AccountDto encodedAccount = getEncoded(accountDto);
		try {
			account = mongoTemplate.insert(Account.of(encodedAccount));
		} catch (DuplicateKeyException e) {
			throw new AccountAlreadyExistsException();
		}
		log.debug("account {} has been saved",email);
		return account.build();
	}

	private AccountDto getEncoded(AccountDto accountDto) {
		return new AccountDto(accountDto.email(), passEncoder.encode(accountDto.password()), accountDto.roles());
	}

	@Override
	public AccountDto removeAccount(String email) throws Exception {
		Account account = mongoTemplate.findAndRemove(new Query(Criteria.where("email").is(email)), Account.class);
		if(account==null) {
			throw new AccountNotFoundException();
		}
		log.debug("account with email {} has been removed",email);
		return account.build();
	}
	@Override
	public void updatePassword(String email, String newPassowrd) throws AccountNotFoundException, IllegalAccessException {
		String currentUserEmail=SecurityContextHolder.getContext().getAuthentication().getName();
		String userToUpdateEmail = accountRepo.findById(email).orElseThrow(()-> new AccountNotFoundException()).getEmail();
		if(currentUserEmail!=userToUpdateEmail) {
			throw new IllegalAccessException();
		}
		Update update= new Update().set("password",passEncoder.encode(newPassowrd));
		mongoTemplate.findAndModify(new Query(Criteria.where("email").is(userToUpdateEmail)), update, null, Account.class );
		
	}
}
