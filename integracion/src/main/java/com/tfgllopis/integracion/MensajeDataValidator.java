package com.tfgllopis.integracion;

import java.util.Locale;

import org.apache.commons.validator.routines.FloatValidator;
import org.apache.commons.validator.routines.IntegerValidator;

public class MensajeDataValidator 
{	
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
	

	public static boolean comprobarMensajeVacio(String mensaje)
	{
		mensaje = mensaje.replaceAll("\\s+","");
		
		return !mensaje.isEmpty();
	}
}
