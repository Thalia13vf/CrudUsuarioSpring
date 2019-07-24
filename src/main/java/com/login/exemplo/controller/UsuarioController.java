package com.login.exemplo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.login.exemplo.modelo.Usuario;
import com.login.exemplo.repositorio.RepositorioUsuario;
import com.login.exemplo.servico.ServicoUsuario;

@Controller
public class UsuarioController 
{
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@Autowired
	private ServicoUsuario servicoUsuario;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	Usuario usu = new Usuario();
	
	@GetMapping("/login")
	public String login() 
	{
		return "login";
	}
	
	@GetMapping("/registrar")
	public ModelAndView registrar() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("registrar");
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	@PostMapping("/registrar")
	public ModelAndView registrar(@Valid Usuario usuario, BindingResult result) 
	{
		ModelAndView mv = new ModelAndView();
		
		if(usuario.getDataNascimento() == null) {
			result.rejectValue("dataNascimento", "", "Preencha a data de Nascimento!");
		}
		if(result.hasErrors()) 
		{
			mv.addObject(usuario);
			mv.setViewName("registrar");
		}
		Usuario usr = servicoUsuario.encontrarPorEmail(usuario.getEmail());
		
		if(usr != null) 
		{
			result.rejectValue("email", "" ,"Esse email já está cadastrado!");
		}
		if(usuario.getSenha() == "") 
		{
			result.rejectValue("senha", "", "Preecha o campo senha.");
		}
		else 
		{
			usuario.setSenha(usuario.getSenha());
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			repositorioUsuario.save(usuario);
			mv.setViewName("login");
		}
		return mv;
	}
	@GetMapping("/")
	public ModelAndView bemVindo(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		String emailUsuario = request.getUserPrincipal().getName(); 
		Usuario usuarioLogado = servicoUsuario.encontrarPorEmail(emailUsuario);
		mv.addObject("usuario", usuarioLogado);
		mv.setViewName("bem-vindo");
		return mv;
	}
	@GetMapping("/alterar/{id}")
	public ModelAndView alterar(@PathVariable("id") Long id) 
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("conta");
		usu = repositorioUsuario.getOne(id);
		mv.addObject("usuario", usu);
		return mv;
	}
	@PostMapping("/alterar")
	public ModelAndView alterar(Usuario usuario, BindingResult result) 
	{
		ModelAndView mv = new ModelAndView();
		if(usuario.getDataNascimento() == null) 
		{
			result.rejectValue("dataNascimento", "usuario.dataNascimentoInvalida", "Preencha a data de Nascimento");
		}
		if(result.hasErrors()) 
		{
			mv.addObject(usuario);
			mv.setViewName("conta");
		}
	
		else 
		{
			if(usuario.getSenha() == "") 
			{
				usuario.setSenha(usu.getSenha());
			}
			else 
			{
				usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			}
			repositorioUsuario.save(usuario);
			mv.addObject(usuario);
			mv.setViewName("bem-vindo");
		}
		return mv;
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id) 
	{
		repositorioUsuario.deleteById(id);
		return "login";
	}
	
	
}
