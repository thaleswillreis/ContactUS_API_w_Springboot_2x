package com.will.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.will.domain.User;
import com.will.repository.UserRepository;

@Configuration
public class MockDataInstantiation implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {

		userRepository.deleteAll();
		
		User joao = new User(null, "João", "Silva", "joaosilva@gmail.com", "+5599992011234");
		User maria = new User(null, "Maria", "Reis", "mariareis@hotmail.com", "+5599992015678");
		User raimaundo = new User(null, "Raimundo", "Nonato", "nonato@gmx.com", "+5599992019012");
		User pedro = new User(null, "Pedro", "Cortez", "pedrinhocortez@yahoo.com", "+5599992015432");
		User luis = new User(null, "Luis", "Costa", "lucosta@gmail.com", "+5599992015674");
		
		userRepository.saveAll(Arrays.asList(joao, maria, raimaundo, pedro, luis));
		
	}
	
	

}
