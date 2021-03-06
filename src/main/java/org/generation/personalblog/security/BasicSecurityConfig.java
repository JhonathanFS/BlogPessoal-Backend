package org.generation.personalblog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration	
@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter{ // trás métodos de segurança pre existente

	@Autowired //Escopo local para uso
	private UserDetailsService userDetailsService;
	
	@Bean //Escopo global no repository 
	public PasswordEncoder passwordEnconder() {  //inteiro que estará cryptografado
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) //Criação do metodo de autenticação (alias = apelido)
	throws Exception { //Exececao de usuario direto
		auth.userDetailsService(userDetailsService); // Metodo de autenticação
		auth.inMemoryAuthentication() // usuario em memoria
		.withUser("root") //login
		.password(passwordEnconder().encode("root")) // Senha Codificada
		.authorities("ROLE_USER"); // Função Usuario equivale nivel de usuario de autorização
	}
	
	@Override
	protected void configure(HttpSecurity http) //a rota de permição de login/cadastrar
	throws Exception { //Execeao de rotas não criptogafadas
		http.authorizeHttpRequests() // para autorizar requisições
		.antMatchers("/usuarios/logar").permitAll() // Permissão de todas as requisições por (rota):usuario/logar
		.antMatchers("/usuarios/cadastrar").permitAll() // Permissão de todas as requisições por (rota):cadastrar/cadastrar
		.antMatchers(HttpMethod.OPTIONS).permitAll() //Permitir qualquer metodos dos acima
		.anyRequest().authenticated() // todos os pedidos devem se autenticados (o resto)
		.and().httpBasic() // com uso dos metodos HTTP
		.and().sessionManagement() //limita a um usuario autenticado por vez
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // informar que um mesmo token não é permanente/infinito
		.and().cors()//auxiliar ao anotação CrossOrigin
		.and().csrf().disable();//permiti editar e atualizar e pagar (Put/Delete)
	}
}