package org.generation.personalblog.security;

import java.util.Collection;
import java.util.List;

import org.generation.personalblog.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//Impletar o Usuario Objeto (Comparar Usuario e UsuarioLogin)
//Para ele conseguir logar

public class UserDetailsImpl implements UserDetails { // Classes de implentação possui Impl ea impleemts User Details pelo seus métodos
	private static final long serialVersionUID = 1L; // padrão
	
	private String userName;
	private String password;
	private List<GrantedAuthority> authorities; // nivel de permissão equivale Admin

	//--- Construtor Cheio ---
	
	public UserDetailsImpl(Usuario usuario) { // vai entender que quando eu chamar usuario (Model)
		// terá esses atributos entendidos com os equivalentes da model
		/*this.userName ou*/ userName = usuario.getUsuario();
		/*this.password ou*/ password = usuario.getSenha();
	}
	
	//--- Construtor Vazio --- (uso principal para teste)
	
	public UserDetailsImpl() {}
	
	// Criando os metodos Getters 
		
	@Override
	public String getPassword() { 
		return password; //para conseguir pegar a senha(Model) e responder como password
	}
	
	@Override
	public String getUsername() {
		return userName; //para conseguir pegar a usuario(Model) e responder como userName
	}
	
	// Metodo do nivel de segurança do usuario 
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	//--- Metodos Padrao de Basic Security --- 
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}

}
