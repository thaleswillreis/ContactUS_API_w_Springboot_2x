package com.will.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.will.domain.Contact;
import com.will.domain.User;
import com.will.dto.AuthorDTO;
import com.will.dto.ContactDTO;
import com.will.repository.ContactRepository;
import com.will.services.exception.ObjectNotFoundException;

class ContactServiceTest {

	private static final String BODY1 = "Exemplo de texto de reclamação qualquer!";

	private static final String BODY2 = "Exemplo de texto de pós venda qualquer!";

	private static final String SUBJECT1 = "Reclamação";

	private static final String SUBJECT2 = "Pós Venda";

	private static final String DATE1 = "01/05/2023";

	private static final String DATE2 = "28/04/2023";

	private static final String ID1 = "1";

	private static final String ID2 = "2";

	@InjectMocks
	private ContactService contactService;

	@Mock
	private ContactRepository contactRepository;

	private ContactDTO contactDto;
	private Optional<Contact> optional;
	private List<Contact> contactList;
	private Page<Contact> contactPage;
	private Contact contato1;
	private Contact contato2;
	private User pedro;
	private User joao;
	private AuthorDTO authorDto1;
	private AuthorDTO authorDto2;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@BeforeEach
	void setUp() throws Exception {

		MockitoAnnotations.openMocks(this);

		joao = new User("1", "João", "Silva", "joaosilva@gmail.com", "+5599992011234");
		pedro = new User("3", "Pedro", "Cortez", "pedrinhocortez@yahoo.com", "+5599992015432");

		authorDto1 = new AuthorDTO(pedro);
		authorDto2 = new AuthorDTO(joao);

		contato1 = new Contact(ID1, sdf.parse(DATE1), SUBJECT1, BODY1, authorDto1);
		contato2 = new Contact(ID2, sdf.parse(DATE2), SUBJECT2, BODY2, authorDto2);

	}

	@Test
	@DisplayName("Find All Wit Pagination")
	void whenFindAllContactsThenReturnAllContactsWitPagination() {

//		contactList =  new ArrayList<>();
		contactList = Arrays.asList(contato1, contato2);
//		contactPage = mock(Page.class);
		contactPage = new PageImpl<>(contactList);
//		when(contactPage.toList()).thenReturn(contactList);
		when(contactRepository.findAll(any(Pageable.class))).thenReturn(contactPage);

		Page<Contact> response = contactService.findAll(mock(Pageable.class));

		verify(contactRepository, times(1)).findAll(any(Pageable.class));
		assertEquals(contactList, response.toList());
		assertEquals(contactPage, response);
		assertEquals(ID1, response.getContent().get(0).getId());
		assertEquals(ID2, response.getContent().get(1).getId());
		assertEquals(DATE1, sdf.format(response.getContent().get(0).getDate()));
		assertEquals(DATE2, sdf.format(response.getContent().get(1).getDate()));
		assertEquals(SUBJECT1, response.getContent().get(0).getSubject());
		assertEquals(SUBJECT2, response.getContent().get(1).getSubject());
		assertEquals(BODY1, response.getContent().get(0).getBody());
		assertEquals(BODY2, response.getContent().get(1).getBody());
		assertEquals(authorDto1, response.getContent().get(0).getAuthor());
		assertEquals(authorDto2, response.getContent().get(1).getAuthor());
	}

	@Test
	@DisplayName("Find All Without Pagination")
	void whenFindAllContactsThenReturnAllContactsWithoutPagination() {
		contactList = Arrays.asList(contato1, contato2);
		when(contactRepository.findAll()).thenReturn(contactList);

		List<Contact> response = contactService.findAllWithoutPagination();

		verify(contactRepository, times(1)).findAll();
		assertEquals(contactList, response);
		assertEquals(contactList.getClass(), response.getClass());
		assertEquals(ID1, response.get(0).getId());
		assertEquals(ID2, response.get(1).getId());
		assertEquals(DATE1, sdf.format(response.get(0).getDate()));
		assertEquals(DATE2, sdf.format(response.get(1).getDate()));
		assertEquals(SUBJECT1, response.get(0).getSubject());
		assertEquals(SUBJECT2, response.get(1).getSubject());
		assertEquals(BODY1, response.get(0).getBody());
		assertEquals(BODY2, response.get(1).getBody());
		assertEquals(authorDto1, response.get(0).getAuthor());
		assertEquals(authorDto2, response.get(1).getAuthor());
	}

	@Test
	@DisplayName("Find By Id")
	void whenFindAContactByIdThenReturnThatContact() {
		optional = Optional.of(contato1);
		when(contactRepository.findById(ID1)).thenReturn(optional);

		Contact response = contactService.findById(ID1);

		verify(contactRepository, times(1)).findById(ID1);
		assertEquals(contato1, response);		
		assertEquals(ID1, response.getId());
		assertEquals(DATE1, sdf.format(response.getDate()));
		assertEquals(SUBJECT1, response.getSubject());
		assertEquals(BODY1, response.getBody());
		assertEquals(authorDto1, response.getAuthor());

	}

	@Test
	@DisplayName("Find By Id Not Found")
	void whenFindAContactByIdThenReturnAException() {
		optional = Optional.empty();
		when(contactRepository.findById(ID1)).thenReturn(optional);

		assertThrows(ObjectNotFoundException.class, () -> {
			contactService.findById(ID1);
		});

		verify(contactRepository, times(1)).findById(ID1);
	}

	@Test
	@DisplayName("Insert")
	void testInsert() {
		fail("Not yet implemented");
	}

	@Test
	@DisplayName("Delete")
	void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	@DisplayName("Update")
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	@DisplayName("Find By Subject")
	void testFindBySubject() {
		fail("Not yet implemented");
	}

	@Test
	@DisplayName("Search Text In Contact Or By Date")
	void testSearchTextInContactOrByDate() {
		fail("Not yet implemented");
	}

	@Test
	@DisplayName("Find Page")
	void testFindPage() {
		fail("Not yet implemented");
	}

	@Test
	@DisplayName("FromDTO")
	void testFromDTO() {
		fail("Not yet implemented");
	}

}
