package br.org.generation.personalblog.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import br.org.generation.blogpessoal.model.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

	
	@Autowired
	private UsuarioRepository repository;
	
	@BeforeAll
	void start() {
		
		repository.save(new Usuario(0L, "Igor da Silva", "igor@igor.com","123","linkdefoto"));
		
		repository.save(new Usuario(0L, "Gabriel Silva", "gabriel@gabriel.com","123","linkdefoto"));
		
		repository.save(new Usuario(0L, "Catarina Silva", "catarina@catarina.com","123","linkdefoto"));
		
		repository.save(new Usuario(0L, "Isabela Caetano", "isabela@isabela.com","123","linkdefoto"));
		
	}
	
	@Test
	@DisplayName("Retorna 1 usuario")
	public void deveRetornarUmUsuario() {
		
		Optional<Usuario>usuario=repository.findByUsuario("igor@igor.com");
		assertTrue(usuario.get().getUsuario().equals("igor@igor.com"));
	}
	
	@Test
	@DisplayName("Retorna 3 usuarios")
	public void deveRetornarTresUsuarios() {
		
		List<Usuario>listaDeUsuarios=repository.findAllByNomeContainingIgnoreCase("Silva");
		assertEquals(3, listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("Igor da Silva"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Gabriel Silva"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Catarina Silva"));
	}
}
