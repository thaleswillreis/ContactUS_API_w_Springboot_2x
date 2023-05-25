package com.will.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.will.domain.User;
import com.will.dto.UserDTO;
import com.will.repository.UserRepository;
import com.will.services.exception.ObjectNotFoundException;

@SpringBootTest
class UserServiceTest {

	private static final String PHONE = "+5599992015432";

	private static final String EMAIL = "pedrinhocortez@yahoo.com";

	private static final String LASTNAME = "Cortez";

	private static final String FISTNAME = "Pedro";

	private static final String ID = "1";

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	private User user;
	private UserDTO userDto;
	private Optional<User> optional;
	private Page<User> page;
	private Pageable pageable;
	private List<User> userList;

	@BeforeEach
	void setup() {

		MockitoAnnotations.openMocks(this);

		user = new User(ID, FISTNAME, LASTNAME, EMAIL, PHONE);
		userDto = new UserDTO(user);
		optional = Optional.of(new User(ID, FISTNAME, LASTNAME, EMAIL, PHONE));
		pageable = PageRequest.of(0, 10);
		
	}

			
	@Test
	@DisplayName("Buscar todos os usuários")
	void whenFindAllUsersThenReturnAllUsers() {
		
		userList = new ArrayList<>();
		userList.add(user);
        page = new PageImpl<>(userList, pageable, userList.size());
        
        when(userRepository.findAll(pageable)).thenReturn(page);
        
        Page<User> response = userService.findAll(pageable);

		assertNotNull(response);
		assertEquals(userList, response.getContent());
        assertEquals(userList.size(), response.getTotalElements());
		assertEquals(10, page.getSize());
		assertEquals(0, page.getNumber());
	}
	 		

	@Test
	@DisplayName("Retorna um User ao buscar um usuário pelo Id")
	void whenFindAUserByIdThenReturnThatUser() {
		
		when(userRepository.findById(Mockito.anyString())).thenReturn(optional);

		User response = userService.findById(ID);

		assertNotNull(response);
		assertEquals(User.class, response.getClass());
		
		assertEquals(ID, response.getId());
		assertEquals(FISTNAME, response.getFirstName());
		assertEquals(LASTNAME, response.getLastName());
		assertEquals(EMAIL, response.getEmail());
		assertEquals(PHONE, response.getPhone());
	}
	
	@Test
	@DisplayName("Retorna uma excessão ao falhar em buscar um usuário pelo Id")
	void returnsAnExceptionWhenFindAUserById() {
		
		when(userRepository.findById(Mockito.anyString())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));
		
		try {
			userService.findById(ID);
		} catch (Exception e) {
			assertEquals(ObjectNotFoundException.class, e.getClass());
			assertEquals("Objeto não encontrado", e.getMessage());
		}
	}
	
	@Test
	@DisplayName("Retorna uma excessão ao buscar um usuário pelo Id inválido")
	void returnsAnExceptionWhenFindAUserByInvalidId() {
		when(userRepository.findById(ID)).thenReturn(optional);

		assertThrows(ObjectNotFoundException.class, () -> userService.findById(null));
		assertThrows(ObjectNotFoundException.class, () -> userService.findById(""));
		assertThrows(ObjectNotFoundException.class, () -> userService.findById("B"));
		assertThrows(ObjectNotFoundException.class, () -> userService.findById(FISTNAME));
		assertThrows(ObjectNotFoundException.class, () -> userService.findById(LASTNAME));
		assertThrows(ObjectNotFoundException.class, () -> userService.findById(EMAIL));
		assertThrows(ObjectNotFoundException.class, () -> userService.findById(PHONE));
	}

	@Test
	void testInsert() {

		// arrange

		// action

		// assert

		assertEquals(true, true);
	}

	@Test
	void testDelete() {

		// arrange

		// action

		// assert

		assertEquals(true, true);
	}

	@Test
	void testUpdate() {

		// arrange

		// action

		// assert

		assertEquals(true, true);
	}

	@Test
	void testFindPage() {

		// arrange

		// action

		// assert

		assertEquals(true, true);
	}

	@Test
	void testFromDTO() {

		// arrange

		// action

		// assert

		assertEquals(true, true);
	}
}
