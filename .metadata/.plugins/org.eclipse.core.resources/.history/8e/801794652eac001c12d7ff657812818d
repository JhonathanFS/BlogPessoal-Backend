package org.generation.personalblog.repository;

import java.util.List;

import org.generation.personalblog.model.postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface postagemRepository extends JpaRepository <postagem, Long> 	{
	
	public List<postagem> findAllByTituloContainIgnoreCase (String titulo);
}
