package com.tfgllopis.integracion;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

/** 
 * !! DO NOT EDIT THIS FILE !!
 * 
 * This class is generated by Vaadin Designer and will be overwritten.
 * 
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class Informacion_movimiento_Ventana extends VerticalLayout {
	protected Image imagenPlanetaOrigen;
	protected Label nombreOrigen;
	protected Label coordenadasOrigenL;
	protected Label tiempoVueltaL;
	protected Label tiempoIdaL;
	protected Label coordenadasDestinoL;
	protected Image imagenPlanetaDestino;
	protected Label nombreDestino;

	public Informacion_movimiento_Ventana() {
		Design.read(this);
	}
}
