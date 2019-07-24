package com.login.exemplo.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.login.exemplo.modelo.Usuario;
import com.login.exemplo.repositorio.RepositorioUsuario;

@Service
public class ServicoUsuario {
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	public Usuario encontrarPorEmail(String email) 
	{
		return repositorioUsuario.findByEmail(email);
	}
	
}
