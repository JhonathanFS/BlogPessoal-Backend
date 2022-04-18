package org.generation.personalblog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.generation.personalblog.model.Usuario;
import org.generation.personalblog.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Test
	@Order(1)
	@DisplayName("Cadastrar Um Usuario")
	public void deveCriarUmUsuario() {
		
		HttpEntity<Usuario>requisicao =new HttpEntity<Usuario>
		(new Usuario(0L, "Igor da Silva", "igor@igor.com", "12345678", "linkdefoto"));
		
		ResponseEntity<Usuario>resposta=testRestTemplate
				.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
		assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());
	}
	
	@Test
	@Order(2)
	@DisplayName("Não deve permitir duplicação do Usuario")
	public void naoDeveDuplicarUsuario() {
		
		usuarioService.cadastrarUsuario(new Usuario(0L,
				"Gabriel Silva", "gabriel@gabriel.com","12345678","linkdefoto"));
		
		HttpEntity<Usuario>requisicao=new HttpEntity<Usuario>(new Usuario(0L,
				"Gabriel Silva", "gabriel@gabriel.com","12345678","linkdefoto"));
		
		ResponseEntity<Usuario> resposta=testRestTemplate
				.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
				
	}
	
	@Test
	@Order(3)
	@DisplayName("Alterar um Usuario")
	public void deveAtualizarUmUsuario() {
		
		Optional<Usuario>usuarioCreate=usuarioService.cadastrarUsuario(new Usuario(0L,
				"Catarina Silva", "catarina@catarina.com","12345678","linkdefoto"));
		
		Usuario usuarioUpdate=new Usuario(usuarioCreate.get().getId(),
				"Catarina Silva","catarina@catarina.com","12345678","linkdefoto");
		
		HttpEntity<Usuario>requisicao=new HttpEntity<Usuario>(usuarioUpdate);
		
		ResponseEntity<Usuario>resposta=testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, requisicao, Usuario.class);
		
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(usuarioUpdate.getNome(), resposta.getBody().getNome());
		assertEquals(usuarioUpdate.getUsuario(), resposta.getBody().getUsuario());
	}
	
	@Test
	@Order(4)
	@DisplayName("Listar todos os Usuarios")
	public void deveMostrarTodosUsuarios() {
		
		usuarioService.cadastrarUsuario(new Usuario(0L,
				"Isabela Caetano", "isabela@isabela.com","12345678","linkdefoto"));
		
		usuarioService.cadastrarUsuario(new Usuario(0L,
				"Gabriel Silva", "gabriel@gabriel.com","12345678","linkdefoto"));
		
		ResponseEntity<String> resposta= testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/all", HttpMethod.GET, null, String.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
}
