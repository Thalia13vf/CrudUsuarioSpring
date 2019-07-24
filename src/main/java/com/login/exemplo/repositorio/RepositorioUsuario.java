package com.login.exemplo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.login.exemplo.modelo.Usuario;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long>{
	
	Usuario findByEmail(String email);

}
