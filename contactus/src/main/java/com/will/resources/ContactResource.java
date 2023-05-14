package com.will.resources;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.will.domain.Contact;
import com.will.dto.ContactDTO;
import com.will.resources.util.URL;
import com.will.services.ContactService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/contacts")
public class ContactResource {

	@Autowired
	private ContactService service;

	@ApiOperation(value="Busca todos os contatos")
	@GetMapping
	public ResponseEntity<List<ContactDTO>> findAllContacts() {
		List<Contact> list = service.findAll();
		List<ContactDTO> listDto = list.stream().map(x -> new ContactDTO(x))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@ApiOperation(value="Busca um contato pelo Id")
	@GetMapping("/{id}")
	public ResponseEntity<ContactDTO> findContactById(@PathVariable String id) {
		Contact obj = service.findById(id);
		return ResponseEntity.ok().body(new ContactDTO(obj));
	}
	
	@ApiOperation(value="Insere um novo contato")
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody ContactDTO objDto){
		Contact obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation(value="Deleta um contato existente")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value="Atualiza os dados de um contato")
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody ContactDTO objDto, @PathVariable String id) {
		Contact obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value="Busca contatos pelo Título / Assunto")
	@GetMapping("/subjectsearch")
	public ResponseEntity<List<ContactDTO>> findContactBySubject(@RequestParam(value = "text", defaultValue = "") String text) {
		text = URL.decodeParam(text);
		List<Contact> list = service.findBySubject(text);
		List<ContactDTO> listDto = list.stream().map(x -> new ContactDTO(x))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@ApiOperation(value="Busca todos os contatos contendo um determinado Termo e/ou em um Intervalo de Datas")
	@GetMapping(value = "/textsearch")
	public ResponseEntity<List<ContactDTO>> searchTextByDate(
			@RequestParam(value = "text", defaultValue = "") String text,
			@RequestParam(value = "minDate", defaultValue = "") String minDate,
			@RequestParam(value = "maxDate", defaultValue = "") String maxDate) {
		text = URL.decodeParam(text);
		Date min = URL.convertDate(minDate, new Date(0L));
		Date max = URL.convertDate(maxDate, new Date());
		List<Contact> list = service.searchTextInContactOrByDate(text, min, max);
		List<ContactDTO> listDto = list.stream().map(x -> new ContactDTO(x))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
}