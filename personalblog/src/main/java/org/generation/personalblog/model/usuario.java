package org.generation.personalblog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="tb_usuarios")
public class usuario {

	@Id // Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_incrment
	private Long id; // bigint
	
	@NotNull
	@Size(max=255)
	private String nome;
	
	@NotNull
	@Size(max=255)
	private String usuario;
	
	@NotNull
	@Size(max=255)
	private String senha;
	
	@Size(max=255)
	private String foto;

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
