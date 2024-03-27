package telran.accounts.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mongodb.client.MongoDatabase;

import telran.accounts.model.Account;

public interface AccountsRepo extends MongoRepository<Account, String>{

}
