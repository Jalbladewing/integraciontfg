package com.tfgllopis.integracion;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
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
public class Recursos_tecnico_Ventana extends VerticalLayout {
	protected HorizontalLayout layoutInstalaciones;
	protected ComboBox<java.lang.String> generacionCombo;
	protected Label metalNiv1;
	protected Label metalNiv2;
	protected Label metalNiv3;
	protected Label oroNiv1;
	protected Label oroNiv2;
	protected Label oroNiv3;
	protected Label petroleoNiv1;
	protected Label petroleoNiv2;
	protected Label petroleoNiv3;
	protected Button guardarB;
	protected Button cancelarB;
	protected Label errorL;
	protected Label correctoL;

	public Recursos_tecnico_Ventana() {
		Design.read(this);
	}
}
