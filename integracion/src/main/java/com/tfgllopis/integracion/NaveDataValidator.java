package com.tfgllopis.integracion;

import java.util.Locale;

import org.apache.commons.validator.routines.FloatValidator;
import org.apache.commons.validator.routines.IntegerValidator;

public class NaveDataValidator 
{
	public static boolean comprobarNombre(String nombre)
	{
		nombre = nombre.replaceAll("\\s+","");
		
		return !nombre.isEmpty();
	}
	
	public static boolean comprobarImagen(String imagen)
	{
		imagen = imagen.replaceAll("\\s+","");
		
		return !imagen.isEmpty();
	}
	
	public static boolean comprobarValorFloat(String numero)
	{
		numero = numero.replaceAll("\\s+","");
		numero = numero.replaceAll(",",".");

		if(FloatValidator.getInstance().isValid(numero, Locale.US))
		{
			
			return Float.parseFloat(numero) >= 0;
		}
		
		return false;
		
	}
	
	public static boolean comprobarValorInteger(String numero)
	{
		numero = numero.replaceAll("\\s+","");
		numero = numero.replaceAll(",",".");
		
		if(IntegerValidator.getInstance().isValid(numero, Locale.US))
		{
			return Integer.parseInt(numero) >= 0;
		}
			
		
		return false;
		
	}
	
	public static boolean comprobarNaveBD(String nombreNave, NaveRepository repo)
	{
		return repo.findByNombreNave(nombreNave).isEmpty();
	}

}
