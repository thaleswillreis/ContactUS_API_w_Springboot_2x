package com.will.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.will.domain.User;
import com.will.dto.UserDTO;
import com.will.repository.UserRepository;
import com.will.services.exception.ObjectNotFoundException;


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
	private Page<User> userPage;
	private List<User> userList;

	@BeforeEach
	void setup() {

		MockitoAnnotations.openMocks(this);

		user = new User(ID, FISTNAME, LASTNAME, EMAIL, PHONE);
		userDto = new UserDTO(user);
		optional = Optional.of(new User(ID, FISTNAME, LASTNAME, EMAIL, PHONE));
	}

	@Test
	@DisplayName("Retorna todos os usuários")
	void whenFindAllUsersThenReturnAllUsers() {
		
		userList = new ArrayList<>();
		userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);
        
        List<User> response = userService.findAll();

        verify(userRepository, times(1)).findAll();
        assertEquals(userList, response);
		assertEquals(userList.getClass(), response.getClass());
		assertEquals(ID, response.get(0).getId());
		assertEquals(FISTNAME, response.get(0).getFirstName());
		assertEquals(LASTNAME, response.get(0).getLastName());
		assertEquals(EMAIL, response.get(0).getEmail());
		assertEquals(PHONE, response.get(0).getPhone());
		
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
	void whenFindAUserByIdReturnsAnException() {
		
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
	void whenFindAUserByInvalidIdReturnsAnException() {
		when(userRepository.findById(ID)).thenReturn(optional);

		assertThrows(ObjectNotFoundException.class, () -> userService.findById(null));
		assertThrows(ObjectNotFoundException.class, () -> userService.findById(""));
		assertThrows(ObjectNotFoundException.class, () -> userService.findById("B"));
		assertThrows(ObjectNotFoundException.class, () -> userService.findById("2"));
		assertThrows(ObjectNotFoundException.class, () -> userService.findById(FISTNAME));
		assertThrows(ObjectNotFoundException.class, () -> userService.findById(LASTNAME));
		assertThrows(ObjectNotFoundException.class, () -> userService.findById(EMAIL));
		assertThrows(ObjectNotFoundException.class, () -> userService.findById(PHONE));
	}

	@Test
	@DisplayName("Insere um novo usuário com sucesso")
	void whenInsertANewUserThenReturnSuccess() {

		when(userRepository.insert(user)).thenReturn(user);

        User response = userService.insert(user);

        assertNotNull(response);
        assertNull(response.getId());
        assertEquals(user, response);
        assertEquals(user.getFirstName(), response.getFirstName());
        assertEquals(user.getLastName(), response.getLastName());
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(user.getPhone(), response.getPhone());
        verify(userRepository, times(1)).insert(user);
        verify(userRepository).insert(eq(user));
	}

	@Test
	@DisplayName("Deleta um usuário com sucesso")
	void whenDeleteAUserThenReturnSuccess() {

		userList = new ArrayList<>();
		userList.add(user);
		when(userRepository.findById(ID)).thenReturn(Optional.of(user));

		userService.delete(ID);

		verify(userRepository, times(1)).findById(ID);
        verify(userRepository, times(1)).deleteById(ID);
        InOrder inOrder = inOrder(userRepository);
        inOrder.verify(userRepository).findById(ID);
        inOrder.verify(userRepository).deleteById(ID);
	}

	@Test
	@DisplayName("Atualiza um usuário existente com sucesso")
	void whenUpdateAUserThenReturnSuccess() {
		
		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(user);
		
		User updatedUser = userService.update(user);
		
		verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals(user, updatedUser);
		
	}

	@Test
	@DisplayName("Retorna todos os usuários com paginação personalizada")
	void whenFindAllUsersThenReturnPaginationWithAllUsers() {
		
		Integer page = 0;
        Integer linesPerPage = 24;
        String direction = "ASC";
        String orderBy = "firstName";

		userList = new ArrayList<>();
		userList.add(user);
		userPage = new PageImpl<>(userList);
		when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);
        
		Page<User> response = userService.findPage(page, linesPerPage, direction, orderBy);

		verify(userRepository, times(1)).findAll(any(Pageable.class));
		assertEquals(userPage, response);
		assertEquals(userList, response.getContent());
        assertEquals(userList.size(), response.getTotalElements());
		assertEquals(1, userPage.getSize());
		assertEquals(0, userPage.getNumber());
		assertEquals(ID, response.getContent().get(0).getId());
		assertEquals(FISTNAME, response.getContent().get(0).getFirstName());
		assertEquals(LASTNAME, response.getContent().get(0).getLastName());
		assertEquals(EMAIL, response.getContent().get(0).getEmail());
		assertEquals(PHONE, response.getContent().get(0).getPhone());
	}

	@Test
	@DisplayName("Retorna um objeto do tipo UserDTO")
	void whenFindAUserObjectReturnsAUserDTO() {

		userDto = new UserDTO(user);

        User user = userService.fromDTO(userDto);

        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getFirstName(), user.getFirstName());
        assertEquals(userDto.getLastName(), user.getLastName());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getPhone(), user.getPhone());
	}
}
