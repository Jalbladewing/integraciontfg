package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;

public class Listar_naves extends Listar_naves_Ventana implements View
{
	@Autowired
	private NaveRepository naveRepo;
	
	public static String VIEW_NAME = "listarNaves";
	private List<Nave> navesL;
	
	public Listar_naves()
	{
		naveRepo = ((VaadinUI) UI.getCurrent()).getInterfazNave();
		
		navesL = naveRepo.findAll();
		navesTabla.setItems(navesL);
		
		//Columna imagen
		navesTabla.addComponentColumn(nave -> {
			Image imagen = nave.getImage();
			imagen.setWidth(50, Unit.PIXELS);
			imagen.setHeight(50, Unit.PIXELS);
			return imagen;
			
		}).setCaption("")
		.setId("imagenNave");
		
		//Columna botón
		navesTabla.addComponentColumn(nave -> {
			HorizontalLayout hl = new HorizontalLayout();
			Button editar = new Button("Editar");
			editar.addStyleName("link");
			editar.addClickListener(new Button.ClickListener()
			{
				
				@Override
				public void buttonClick(ClickEvent event) 
				{
					doNavigate(Editar_nave.VIEW_NAME + "/" + nave.getNombreNave());
				}
			});
			hl.addComponent(editar);
			return hl;
		}).setCaption("-")
		.setId("Buttons");
		
		
		navesTabla.setColumnOrder("imagenNave", "nombreNave", "tipoNavenombreTipoNave", "bloqueada", "Buttons");			
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}

}
