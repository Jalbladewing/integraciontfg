package com.tfgllopis.integracion;

import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;

public class UserDataValidator 
{
	public static boolean comprobarEmail(String email)
	{
		return EmailValidator.getInstance().isValid(email);
	}
	
	public static boolean comprobarUser(String user)
	{
		//Detecta todo lo que no sean letras ni n�meros
		Pattern p = Pattern.compile("[^a-zA-Z0-9]+");
		return !p.matcher(user).find();
	}
	
	public static boolean comprobarPassword(String pass1, String pass2)
	{
		pass1 = pass1.replaceAll("\\s+","");
		pass2 = pass2.replaceAll("\\s+","");
		
		if(pass1.isEmpty()) return false;
		
		return pass1.equals(pass2);
	}
	
	public static boolean comprobarEmailBD(String email, UsuarioRepository repo)
	{
		email = email.replaceAll("\\s+","");
		
		return !repo.findByEmailLikeIgnoreCase(email).isEmpty();
	}
	
	public static boolean comprobarUsuarioBD(String usuario, UsuarioRepository repo)
	{
		usuario = usuario.replaceAll("\\s+","");
		
		return !repo.findByUsername(usuario).isEmpty();
	}
	
	public static boolean comprobarPassword(String usuario, String password, UsuarioRepository repo)
	{
		usuario = usuario.replaceAll("\\s+","");
		password = password.replaceAll("\\s+","");
		
		return CheckPassword.verifyHash(password, repo.findByUsername(usuario).get(0).getPassword());
	}
	
	public static boolean comprobarActivo(String usuario, UsuarioRepository repo)
	{
		usuario = usuario.replaceAll("\\s+","");
		
		return repo.findByUsername(usuario).get(0).getActivo();
	}

}
