package com.pluralsight.security;

import static com.pluralsight.security.entity.Type.BUY;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import com.pluralsight.security.entity.CryptoCurrency;
import com.pluralsight.security.entity.Portfolio;
import com.pluralsight.security.entity.SupportQuery;
import com.pluralsight.security.entity.Transaction;
import com.pluralsight.security.entity.User;
import com.pluralsight.security.repository.CryptoCurrencyRepository;
import com.pluralsight.security.repository.PortfolioRepository;
import com.pluralsight.security.repository.SupportQueryRepository;
import com.pluralsight.security.repository.UserRepository;

@SpringBootApplication
public class AuthBasicWebApplication {

	@Autowired
	private UserRepository userRespository;
	@Autowired
	private PortfolioRepository portfolioRepository;
	@Autowired
	private CryptoCurrencyRepository cryptoRepository;
	@Autowired
	private SupportQueryRepository supportRepository;	
	
	@EventListener(ApplicationReadyEvent.class)
	public void intializeUserData() {
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		CryptoCurrency bitcoin = new CryptoCurrency("BTC", "Bitcoin");
		CryptoCurrency litecoin = new CryptoCurrency("LTC", "Litecoin");
		cryptoRepository.save(bitcoin);
		cryptoRepository.save(litecoin);
		transactions.add(new Transaction(bitcoin, BUY,new BigDecimal(3.1), new BigDecimal(15000.00), System.currentTimeMillis()));
		transactions.add(new Transaction(litecoin, BUY,new BigDecimal(20.1), new BigDecimal(13000.00), System.currentTimeMillis()));
		transactions.add(new Transaction(litecoin, BUY,new BigDecimal(200.1), new BigDecimal(130000.00), System.currentTimeMillis()));
		userRespository.save(new User("snakamoto", "Satoshi", "Nakamoto", "snakamoto@gmail.com","pa55word"));
		userRespository.save(new User("bob", "Bob", "Jones", "bjones@gmail.com","password"));
		portfolioRepository.save(new Portfolio("snakamoto", transactions));
		portfolioRepository.save(new Portfolio("bob", new ArrayList<>()));
		SupportQuery query = new SupportQuery("snakamoto", "Cannot remove transaction");
		query.addPost("Please Help","snakamoto");
		supportRepository.save(query);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AuthBasicWebApplication.class, args);
	}
	
}