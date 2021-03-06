package org.generation.personalblog.security;

import java.util.Optional;

import org.generation.personalblog.model.Usuario;
import org.generation.personalblog.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//Impletar e comparar o Usuario Objeto com o banco de dados
@Service // equivale a repository mas deixando ele mais seguro com seus metodos
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired //Co-relação com o repository
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) 
    throws UsernameNotFoundException { //Tratativa de erro
        Optional<Usuario> usuario = usuarioRepository.findByUsuario(userName); //Busca de usuarios
        usuario.orElseThrow	//Caso não
        (() -> new UsernameNotFoundException(userName + "  não foi encontrado.")); //Impressão

        return usuario.map(UserDetailsImpl::new).get();	//Retornar a lista de usuarios existentes
    }
}
