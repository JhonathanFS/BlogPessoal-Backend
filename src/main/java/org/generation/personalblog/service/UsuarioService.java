package org.generation.personalblog.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.generation.personalblog.model.Usuario;
import org.generation.personalblog.model.UsuarioLogin;
import org.generation.personalblog.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service //Mostrando que é uma classe Service (os metodos do controller de forma segura)
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	
	private String criptografarSenha(String senha) { //Função para criptografado
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // Codificador 
		return encoder.encode(senha); //Returna a senha criptografada
	}

	public Optional<Usuario> cadastrarUsuario(Usuario usuario){ //Função para cadastrar 
	// ^ public porque seu uso será em outras class
		if(repository.findByUsuario(usuario.getUsuario()).isPresent()) { //Verifica a presença daquele objeto usuario	
			return Optional.empty(); //Caso sim, returna vazio
		}
		
		usuario.setSenha(criptografarSenha(usuario.getSenha())); //Salva senha do objeto criptografado
		return Optional.of(repository.save(usuario)); //Salva objeto no banco após a senha criptografada
	}
	
	private boolean compararSenhas(String senhaDigitada, String senhaDoBanco) { //Função para comparar as senhas Objeto/Banco
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); //descodifica as senhas
		
		return encoder.matches(senhaDigitada, senhaDoBanco); //Compara se são iguais
	}
	
	private String geradorBasicToken(String email, String password) { //Função para criar Token

		String token = email + ":" + password; //Como os atributos seram usados
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII"))); //Criação de token e tecnologia usando Base64
		return "Basic " + new String(tokenBase64); //Returna o Head(token) em forma de String
	}
	
	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin){ //Função para Logar
	// ^ public porque seu uso será em outras class
		Optional<Usuario> usuario = repository.findByUsuario(usuarioLogin.get().getUsuario()); //Verificação se usuario existe
		
		if(usuario.isPresent()) { //Caso exista
			if(compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())){
				//Trás a função para comparar as senhas Objeto/Banco
				//Se sim faz os getters e setters atribuindo no UsuarioLogin(Objeto)
				usuarioLogin.get().setToken(geradorBasicToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha())); //Trazendo o metodo de criação de token
				usuarioLogin.get().setId(usuario.get().getId()); //Comparação de ID atribuindo em Objeto
				usuarioLogin.get().setNome(usuario.get().getNome()); //Comparação de Nome atribuindo em Objeto
				usuarioLogin.get().setUsuario(usuario.get().getUsuario()); //Comparação de Usuario atribuindo em Objeto
				usuarioLogin.get().setTipo(usuario.get().getTipo());
				usuarioLogin.get().setFoto(usuario.get().getFoto());
				//Token atribuindo em Objeto
				usuarioLogin.get().setSenha(usuario.get().getSenha()); //Comparação de Nome atribuindo em Objeto
	
				return usuarioLogin; //Objeto populado é retornado para logar
			}
		}
		return Optional.empty();  //Caso não tenha, returna vazio
	}
	
	public Optional<Usuario> atualizarUsuario(Usuario usuario) { //Função para Atualizar busca um usuario no banco
	// ^ public porque seu uso será em outras class
		if (repository.findById(usuario.getId()).isPresent()) { //Busca pelo ID 
			Optional<Usuario> search = repository.findByUsuario(usuario.getUsuario()); // Busca tudo
			
			if (search.isPresent()) { //Se sim			
				if (search.get().getId() != usuario.getId()) //Se o mesmo ID que foi buscado do Objeto
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null); 
				
			}
			
			usuario.setSenha(criptografarSenha(usuario.getSenha())); //Caso mude a senha ela é criptografada e salva
			return Optional.of(repository.save(usuario)); //Salva no banco
			
		}	
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!", null);		
	}
}