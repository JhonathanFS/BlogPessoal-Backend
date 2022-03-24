package org.generation.personalblog.controller;

import java.util.List;

import org.generation.personalblog.model.postagem;
import org.generation.personalblog.repository.postagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/postagens")
@CrossOrigin("*")
public class postagemController {
	
	@Autowired
	private postagemRepository repository;
	
	@GetMapping
	public ResponseEntity<List<postagem>> GetAll(){
		return ResponseEntity.ok(repository.findAll());
	}
}	
		