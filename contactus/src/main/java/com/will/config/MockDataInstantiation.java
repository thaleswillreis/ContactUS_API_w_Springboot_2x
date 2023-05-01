package com.will.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.will.domain.Contact;
import com.will.domain.User;
import com.will.dto.AuthorDTO;
import com.will.repository.ContactRepository;
import com.will.repository.UserRepository;

@Configuration
public class MockDataInstantiation implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Override
	public void run(String... args) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		contactRepository.deleteAll();
		
		User joao = new User(null, "João", "Silva", "joaosilva@gmail.com", "+5599992011234");
		User maria = new User(null, "Maria", "Reis", "mariareis@hotmail.com", "+5599992015678");
		User pedro = new User(null, "Pedro", "Cortez", "pedrinhocortez@yahoo.com", "+5599992015432");
		
		userRepository.saveAll(Arrays.asList(joao, maria, pedro));
		
		Contact contact1 = new Contact(null, sdf.parse("01/05/2023"), "Reclamação", "Exemplo de texto de reclamação qualquer!", new AuthorDTO(pedro));
		Contact contact2 = new Contact(null, sdf.parse("28/04/2023"), "Pós Venda", "Exemplo de texto de pós venda qualquer!", new AuthorDTO(joao));
		Contact contact3 = new Contact(null, sdf.parse("20/04/2023"), "Oportunidade de Parceria", "Exemplo de texto de oportunidade de parceria qualquer!", new AuthorDTO(maria));
		Contact contact4 = new Contact(null, sdf.parse("25/03/2023"), "Pedido de garantia de produto", "Exemplo de texto de pedido de garantia de produto qualquer!", new AuthorDTO(pedro));
		
		contactRepository.saveAll(Arrays.asList(contact1, contact2, contact3, contact4));
		
		joao.getContact().addAll(Arrays.asList(contact2));
		userRepository.save(joao);
		maria.getContact().addAll(Arrays.asList(contact3));
		userRepository.save(maria);
		pedro.getContact().addAll(Arrays.asList(contact1, contact4));
		userRepository.save(pedro);
		
	}
	
	

}
