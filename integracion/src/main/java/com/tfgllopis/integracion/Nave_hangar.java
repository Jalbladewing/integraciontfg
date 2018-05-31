package com.tfgllopis.integracion;

import java.io.File;

import com.vaadin.server.ClassResource;
import com.vaadin.server.FileResource;

public class Nave_hangar extends Nave_hangar_Ventana 
{
	
	public Nave_hangar(Nave nave, Usuario usuario,UsuarioHasNaveRepository usuarioNaveRepo)
	{
		nombreNaveL.setValue(nave.getNombreNave());
		imagenNave.setSource(nave.getImage().getSource());
		
		if(nave.getBloqueada() == 1 && usuarioNaveRepo.findByNavenombreNaveUsuarioUsername(nave.getNombreNave(), usuario.getUsername()).isEmpty())
			{
				imagenNave.addStyleName("my-grey-image");
				imagenCandado.setSource( new FileResource(new File(new File("").getAbsolutePath() + "/images/" + "bloqueado.png")));
				imagenCandado.setVisible(true);
			}
	}

}
