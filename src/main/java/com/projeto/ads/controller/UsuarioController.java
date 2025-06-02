package com.projeto.ads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.util.List;

import com.projeto.ads.model.Role;
import com.projeto.ads.model.Usuario;
import com.projeto.ads.repository.RoleRepository;
import com.projeto.ads.repository.UserRepository;
import com.projeto.ads.service.EmailService;
import com.projeto.ads.service.UserService;

import com.projeto.ads.util.Util;

@Controller
public class UsuarioController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	

	
	

	@GetMapping("/dashboard")
	public ModelAndView dashboard(Authentication authentication) {
		ModelAndView mv = new ModelAndView();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		// Recuperando o papel do usuario
		String papel = authentication.getAuthorities().iterator().next().getAuthority();
		mv.addObject("usuario", username);
		mv.addObject("role", papel);
		mv.setViewName("Login/index");
		return mv;
	}// fim metodo

	@GetMapping("/usuario/recuperarSenha")
	public ModelAndView recuperarSenhaGet() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("usuario", new Usuario());
		mv.setViewName("Login/recuperar");
		return mv;
	}
	@PostMapping("/usuario/recuperarSenha")
	public ModelAndView recuperarSenhaPost(Usuario usuario, RedirectAttributes redirectAttributes) throws Exception {
	    ModelAndView mv = new ModelAndView();
	    Usuario aux = userRepository.findByEmail(usuario.getEmail());

	    if (aux == null) {
	        mv.addObject("msg", "email não encontrado");
	        mv.addObject("usuario", usuario); 
	        mv.setViewName("Login/recuperar");
	        return mv;
	    } else {
	    	aux.setToken(Util.generateToken());
	        userRepository.save(aux);
	        
	        String corpo= "Use o seguinte token para redefinir sua senha: "+ aux.getToken();
	        
	        aux.setToken("");
	        mv.addObject("usuario", aux);
	        redirectAttributes.addFlashAttribute("usuario", aux);
	        //emailService.sendEmail("senaclpoo@gmail.com", aux.getEmail(), "Token de recuperação de senha", corpo);
	        
	        mv.setViewName("redirect:/usuario/atualizarUsuario");
	    }

	    return mv;
	}
	
	@GetMapping("/usuario/atualizarUsuario")
	public ModelAndView atualizarUsuario(Usuario usuario) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("usuario", usuario);
		mv.setViewName("Login/atualizar");
		return mv;
	}
	
	
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("usuario", new Usuario());
		mv.setViewName("Login/Login");
		return mv;
	}

	@GetMapping("usuario/inserir")
	public ModelAndView inserirGet() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("usuario", new Usuario());
		List<Role> roles = roleRepository.findAll();
		mv.addObject("papeis", roles);
		mv.setViewName("Login/inserir");
		return mv;
	}// fim metodo

	@PostMapping("/usuario/inserir")
	public ModelAndView inserirPost(Usuario user, @RequestParam("dataNasc") String dataNasc,
			@RequestParam("roleUser") String roleUser, @RequestParam("confirmarPassword") String confirmPass)
			throws ParseException {
		ModelAndView mv = new ModelAndView();
		String email = userService.validarEmail(user);
		String pass = userService.validarSenha(user, confirmPass);
		if (email != null || pass != null) {
			mv.addObject("usuario", user);
			String total = "";
			total += (email != null) ? email + ". " : "";
			total += (pass != null) ? pass : "";
			mv.addObject("msg", total);
			List<Role> roles = roleRepository.findAll();
			mv.addObject("papeis", roles);
			mv.setViewName("Login/inserir");
			return mv;
		} // fim if

		// data formato brasil
		user = userService.buildData(dataNasc, user);

		// definir papel
		user = userService.defineRole(user, roleUser);

		// definir Senha
		user = userService.encriptarSenha(user);
		// vincular username a email
		user.setUsername(user.getEmail());
		user.setAtivo(true);
		userRepository.save(user);
		mv.setViewName("redirect:/login");
		return mv;
	}

}// fim class
