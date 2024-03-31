package telran.accounts.auth;

import java.util.Arrays;

import org.springframework.security.core.*;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.accounts.model.Account;
import telran.accounts.repo.AccountsRepo;
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
final AccountsRepo accountRepo;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Account account = accountRepo.findById(email).orElseThrow(()->new UsernameNotFoundException(""));
		String[] roles = Arrays.stream(account.getRoles()).map(r -> "ROLE_" + r).toArray(String[]::new);
		return new User(email, account.getHashPassword(),
				AuthorityUtils.createAuthorityList(roles));
	}

}