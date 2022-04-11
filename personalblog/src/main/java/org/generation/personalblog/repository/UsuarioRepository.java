package org.generation.personalblog.repository;

import java.util.List;
import java.util.Optional;

import org.generation.personalblog.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Optional<Usuario> findByUsuario(String usuario); 
	public List<Usuario> findAllByNomeContainingIgnoreCase(String nome);
}
