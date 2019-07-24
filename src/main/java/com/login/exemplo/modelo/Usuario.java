package com.login.exemplo.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="usuarios")
public class Usuario 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@NotNull(message="O nome é obrigatório.")
	@Column(name="nome", nullable=false, length=200)
	@Size(min=3, max=200, message="O nome deve ter mais de 3 caracteres.")
	private String nome;
	
	@NotNull(message="O email é obrigatório.")
	@Column(name="email", nullable=false, length=100)
	@Size(min=3, max=100, message="O email deve ter entre 3 e 100 caracteres.")
	private String email;
	
	@Column(name="data_nascimento", nullable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dataNascimento;
	
	@NotBlank(message="A senha é obrigatória.")
	@Column(name="senha", nullable=false)
	private String senha;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
