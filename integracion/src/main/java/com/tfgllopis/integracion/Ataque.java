package com.tfgllopis.integracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextField;

public class Ataque extends Ataque_Ventana implements View
{
	@Autowired
	PlanetaRepository planetaRepo;
	
	@Autowired
	PlanetaHasNaveRepository planetaNaveRepo;
	
	@Autowired
	private MovimientoRepository movimientoRepo;
	
	@Autowired
	private MovimientohasNaveRepository movimientoNaveRepo;
	
	private EntityManager em;
	
	public static String VIEW_NAME = "ataque";
		
	@SuppressWarnings("unchecked")
	public Ataque()
	{
		Informacion_movimiento infoMovimiento;
		Nave_ataque aux;
		List<PlanetaHasNave> planetaNavesL;
		Planeta planetaUsuario;
		Planeta planetaAtaque = ((VaadinUI) UI.getCurrent()).getPlanetaAtaque();
		
		if(planetaAtaque == null) return;
		
		tituloAtaque.setValue("Ataque Vs " +planetaAtaque.getNombrePlaneta());
		
		em = ((VaadinUI) UI.getCurrent()).getEntitymanager();
		((VaadinUI) UI.getCurrent()).setPlanetaAtaque(null);
		planetaRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlaneta();
		planetaNaveRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaNave();
		movimientoRepo = ((VaadinUI) UI.getCurrent()).getInterfazMovimiento();
		movimientoNaveRepo = ((VaadinUI) UI.getCurrent()).getInterfazMovimientoNave();
		planetaUsuario = planetaRepo.findByUsuarioUsername(((VaadinUI) UI.getCurrent()).getUsuario());
		planetaNavesL = planetaNaveRepo.findByPlaneta(planetaUsuario.getCoordenadaX(), planetaUsuario.getCoordenadaY(), planetaUsuario.getSistemanombreSistema());
		
		infoMovimiento = new Informacion_movimiento(planetaUsuario, planetaAtaque);
		layoutInformacion.addComponent(infoMovimiento);		
		
		for(int i = 0; i < planetaNavesL.size(); i++)
		{
			aux = new Nave_ataque(planetaNavesL.get(i));
			
			aux.cantidadF.addValueChangeListener(new ValueChangeListener() 
			{
				@SuppressWarnings("rawtypes")
				@Override
				public void valueChange(ValueChangeEvent event) 
				{
					TextField aux = (TextField) event.getComponent();
					if(NaveDataValidator.comprobarValorInteger(aux.getValue()))
					{
						infoMovimiento.actualizarTiempo(layoutNaves);
					}
					
				}
	        });
			
			layoutNaves.addComponent(aux);
		}
		
		atacarB.addClickListener(new Button.ClickListener() 
		{
			

			@Override
			public void buttonClick(ClickEvent event) 
			{
				String value = Ataque.confirmarAtaque((Iterator<Nave_ataque>)(Object) layoutNaves.iterator(), infoMovimiento.getFechaLlegada(), infoMovimiento.getFechaVuelta(), ((VaadinUI) UI.getCurrent()).getUsuario(), planetaUsuario, planetaAtaque, movimientoRepo, movimientoNaveRepo, planetaNaveRepo, em);
				errorL.setValue(value);
				
				if(value.isEmpty())
				{
					errorL.setVisible(false);
					doNavigate(Ver_naves.VIEW_NAME);
					
				}else
				{
					errorL.setVisible(true);
				}
				
			}
		});
		
		cancelarB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				doNavigate(Galaxia.VIEW_NAME);				
			}
		});
		
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
	
	public static String confirmarAtaque(Iterator<Nave_ataque> iterator, Date fechaLlegada, Date fechaVuelta, Usuario usuario, Planeta planetaOrigen, Planeta planetaDestino, MovimientoRepository movimientoRepo, MovimientohasNaveRepository movimientoNaveRepo, PlanetaHasNaveRepository planetaNaveRepo, EntityManager em)
	{
		ArrayList<Nave_ataque> naves = new ArrayList<>();
		ArrayList<MovimientohasNave> navesMovimiento = new ArrayList<>();
		ArrayList<PlanetaHasNave> navesPlaneta = new ArrayList<>();
		Nave_ataque aux;
		PlanetaHasNave auxPlaneta;
		Movimiento movimiento;
		String setNaves = "";
		int cantidadTotal = 0;
		
		//Comprobacion
		while(iterator.hasNext())
		{
			aux = iterator.next();
			if(!NaveDataValidator.comprobarValorInteger(aux.cantidadF.getValue())) return "Cantidad de " + aux.getNave().getNavenombreNave() +" incorrecta";
			if(Integer.parseInt(aux.cantidadF.getValue()) > aux.getNave().getCantidad()) return "No puedes enviar más naves de las que tienes";
			cantidadTotal += Integer.parseInt(aux.cantidadF.getValue());
				
			naves.add(aux);
		}
		
		if(cantidadTotal <= 0) return "Debes de enviar alguna nave";
		
		movimiento = new Movimiento(fechaLlegada, new Date(), (short) 0);
		movimiento.setPlaneta(planetaDestino);
		movimiento.setUsuariousername(usuario);
		movimiento = movimientoRepo.save(movimiento);
		
		for(int i = 0; i < naves.size(); i++)
		{			
			if(Integer.parseInt(naves.get(i).cantidadF.getValue()) > 0)
			{
				navesMovimiento.add(new MovimientohasNave(movimiento.getIdMovimiento(), naves.get(i).getNave().getNavenombreNave(), Integer.parseInt(naves.get(i).cantidadF.getValue())));
				auxPlaneta = planetaNaveRepo.findByNavenombreNavePlaneta(naves.get(i).getNave().getNavenombreNave(), planetaOrigen.getCoordenadaX(), planetaOrigen.getCoordenadaY(), planetaOrigen.getSistemanombreSistema()).get(0);
				auxPlaneta.setCantidad(auxPlaneta.getCantidad() - Integer.parseInt(naves.get(i).cantidadF.getValue()));
				navesPlaneta.add(auxPlaneta);
				setNaves += "UPDATE Planeta_has_Nave\nSET cantidad = cantidad+" +naves.get(i).getNave().getCantidad()
						+"\nWHERE Nave_NombreNave = '" +naves.get(i).getNave().getNavenombreNave() +"'"
						+ " AND Planeta_coordenadaX =" +planetaOrigen.getCoordenadaX() 
						+ " AND Planeta_coordenadaY=" +planetaOrigen.getCoordenadaY()
						+ " AND Planeta_Sistema_nombreSistema= '" +planetaOrigen.getSistemanombreSistema() +"';\n";
			}
		}
		
		movimientoNaveRepo.saveAll(navesMovimiento);
		planetaNaveRepo.saveAll(navesPlaneta);
		
		//Crear el cronjob tocho
		createEventScheduler(em, new Timestamp(fechaLlegada.getTime()), new Timestamp(fechaVuelta.getTime()), usuario, planetaOrigen, planetaDestino, movimiento, setNaves);
		((VaadinUI) UI.getCurrent()).setMovimiento(movimiento);
		return "";
	}
	
	@Transactional
	private static void createEventScheduler(EntityManager em, Timestamp fechaLlegada, Timestamp fechaVuelta, Usuario usuario, Planeta planetaOrigen, Planeta planetaDestino, Movimiento movimiento, String setNaves)
	{
		//Comprobar si el usuario del destino es null y ponerle un valor para luego el string
		String usuarioDestino = null;
		int idPirata = -1;
		int idBatalla = -1;
		if(planetaDestino.getUsuariousername() != null) usuarioDestino = "'" +planetaDestino.getUsuariousername().getUsername() +"'";
		if(planetaDestino.getPirataidPirata() != null) idPirata = planetaDestino.getPirataidPirata().getIdPirata();
		if(planetaDestino.getInformeBatallaidBatalla() != null) idBatalla = planetaDestino.getInformeBatallaidBatalla().getIdBatalla();
		
		em = em.getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		Session session = em.unwrap(Session.class);
		String sentencia = "CREATE EVENT IF NOT EXISTS movimiento_" +movimiento.getIdMovimiento() +"_return" +"\nON SCHEDULE AT '" +fechaLlegada +"'\nDO BEGIN\n"
				+ "DECLARE done INT DEFAULT FALSE;\n"
				+ "DECLARE idMov INT;\n"
				+ "DECLARE v1 INT DEFAULT 0;\n"
				+ "DECLARE victoria BOOLEAN DEFAULT FALSE;\n"
				+ "DECLARE nom Varchar(45);\n"
				+ "DECLARE randomNumber INT DEFAULT 0;\n"
				+ "DECLARE cantidad_aux INT;\n"
				+ "DECLARE cantidad_enviada INT;\n"
				+ "DECLARE atAl FLOAT;\n"
				+ "DECLARE nomEnem Varchar(45);\n"
				+ "DECLARE atEnem FLOAT;\n"
				+ "DECLARE shieldEnem FLOAT;\n"
				+ "DECLARE hullEnem FLOAT;\n"
				+ "DECLARE agEnem FLOAT;\n"
				+ "DECLARE cantEnem INT;\n"
				+ "DECLARE atOne FLOAT;\n"
				+ "DECLARE shieldOne FLOAT;\n"
				+ "DECLARE hullOne FLOAT;\n"
				+ "DECLARE agOne FLOAT;\n"
				+ "DECLARE navesRestantes INT;\n"
				+ "DECLARE idBat INT;\n"
				+ "DECLARE idMen INT;\n"
				+ "DECLARE metalObtenido INT;\n"
				+ "DECLARE oroObtenido INT;\n"
				+ "DECLARE petroleoObtenido INT;\n"
				+ "DECLARE offsetRecursos INT;\n"
				+ "DECLARE totalCargo INT DEFAULT 0;\n"
				+ "DECLARE naveDesbloqueada Varchar(45);\n"
				+ "DECLARE naveYaDesbloqueada INT;\n"
				+ "DECLARE randomNumberFloat FLOAT;\n"
				+ "DECLARE tiempoUltimoAtaque TIMESTAMP;\n"
				+ "DECLARE horasUltimoAtaque INT;\n"
				+ "DECLARE atacanteNom CURSOR FOR SELECT Nave_nombreNave "
					+ "FROM Movimiento_has_Nave "
					+ "WHERE Movimiento_idMovimiento =" +movimiento.getIdMovimiento() +";\n"
				+ "DECLARE defensorNom CURSOR FOR SELECT Nave_nombreNave "
					+ "FROM Planeta_has_Nave "
					+ "WHERE Planeta_coordenadaX =" +planetaDestino.getCoordenadaX() 
					+" AND Planeta_coordenadaY=" +planetaDestino.getCoordenadaY()
					+ " AND Planeta_Sistema_nombreSistema= '" +planetaDestino.getSistemanombreSistema() +"';\n"

				+ "DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;\n"
				+ "CREATE TEMPORARY TABLE IF NOT EXISTS NaveCombate ("
					+ "nombreNave VARCHAR(45) NOT NULL,"
					+ "ataqueNave FLOAT NOT NULL,hullNave FLOAT NOT NULL,"
					+ "escudoNave FLOAT NOT NULL,velocidadNave FLOAT NOT NULL,"
					+ "agilidadNave FLOAT NOT NULL,"
					+ "capacidadCarga FLOAT NOT NULL,"
					+ "cantidad INT,"
					+ "PRIMARY KEY (nombreNave),"
					+ "UNIQUE INDEX nombreNave_UNIQUE (nombreNave ASC));\n"
				+ "CREATE TEMPORARY TABLE IF NOT EXISTS NaveDefensa ("
					+ "nombreNave VARCHAR(45) NOT NULL,"
					+ "ataqueNave FLOAT NOT NULL,hullNave FLOAT NOT NULL,"
					+ "escudoNave FLOAT NOT NULL,velocidadNave FLOAT NOT NULL,"
					+ "agilidadNave FLOAT NOT NULL,"
					+ "capacidadCarga FLOAT NOT NULL,"
					+ "cantidad INT,"
					+ "PRIMARY KEY (nombreNave),"
					+ "UNIQUE INDEX nombreNave_UNIQUE (nombreNave ASC));\n"
				+ "SELECT NOW() INTO @fecha;\n"
				+ "IF " + idPirata  +" > 0 AND " +idBatalla + " < 0 THEN\n"
					+ "UPDATE Planeta_has_Nave planeta\n"
						+ "LEFT JOIN Pirata_has_Nave pirata ON planeta.Nave_nombreNave = pirata.Nave_nombreNave\n"
						+ "SET planeta.cantidad = pirata.cantidadDefecto\n"
						+ "WHERE pirata.Pirata_idPirata =" + idPirata + "\n"
						+ " AND Planeta_coordenadaX = " +planetaDestino.getCoordenadaX()
						+ " AND Planeta_coordenadaY = " +planetaDestino.getCoordenadaY()
						+ " AND Planeta_Sistema_nombreSistema= '" +planetaDestino.getSistemanombreSistema() +"';\n"
				+ "END IF;\n"
				+ "IF " + idPirata  +" > 0 AND " +idBatalla + " > 0 THEN\n"
	   				+"SELECT tiempoLlegada INTO tiempoUltimoAtaque FROM Movimiento\n"
	   					+"WHERE idMovimiento = (SELECT Movimiento_idMovimiento \n"
	   					+"FROM InformeBatalla\n"
	   					+"WHERE idBatalla = (SELECT InformeBatalla_idBatalla\n" 
										+"FROM Planeta \n"
										+ " WHERE coordenadaX = " +planetaDestino.getCoordenadaX()
							   			+ " AND coordenadaY = " +planetaDestino.getCoordenadaY()
							   			+ " AND Sistema_nombreSistema= '" +planetaDestino.getSistemanombreSistema() +"'));\n"
						+ "SELECT TIMESTAMPDIFF(HOUR, tiempoUltimoAtaque,NOW()) INTO horasUltimoAtaque;\n"
						+ "IF horasUltimoAtaque >= 5 THEN\n"
							+ "UPDATE Planeta_has_Nave planeta\n"
								+ "LEFT JOIN Pirata_has_Nave pirata ON planeta.Nave_nombreNave = pirata.Nave_nombreNave\n"
								+ "SET planeta.cantidad = pirata.cantidadDefecto\n"
								+ "WHERE pirata.Pirata_idPirata =" + idPirata + "\n"
								+ " AND Planeta_coordenadaX = " +planetaDestino.getCoordenadaX()
								+ " AND Planeta_coordenadaY = " +planetaDestino.getCoordenadaY()
								+ " AND Planeta_Sistema_nombreSistema= '" +planetaDestino.getSistemanombreSistema() +"';\n"
						+ "END IF;\n"
				+ "END IF;\n"
				+ "SET @ultimaGeneracion= '2018-05-23 18:45:20';"
				+ "SET @nivelInstalacion= 0;"
				+ "SET @generacionBase= 2.4;"
				+ "SET @segundosGenRecursos= 0;"
				+ "SELECT nivelInstalacion, ultimaGeneracion INTO @nivelInstalacion, @ultimaGeneracion "
					+ "FROM Planeta_has_Instalacion "
					+ "WHERE Planeta_coordenadaX =" +planetaDestino.getCoordenadaX()
	   				+ " AND Planeta_coordenadaY =" +planetaDestino.getCoordenadaY()
	   				+ " AND Planeta_Sistema_nombreSistema ='" +planetaDestino.getSistemanombreSistema()  
	   				+ "' AND Instalacion_name='Mina de Metal';\n"
	   			+ "IF " + idPirata  +" > 0 THEN\n"
	   				+ "SELECT nivelDefecto INTO @nivelInstalacion "
	   					+ "FROM Pirata_has_Instalacion "
	   					+ "WHERE Pirata_idPirata =" +idPirata
	   					+ " AND Instalacion_name='Mina de Metal';\n"
	   			+ "END IF;\n"
	   			+ "SELECT generacionBase INTO @generacionBase "
	   				+ "FROM Instalacion "
	   				+ "WHERE name = 'Mina de Metal';\n"
	   			+ "SELECT TIMESTAMPDIFF(SECOND, @ultimaGeneracion,NOW()) INTO @segundosGenRecursos;\n"
	   			+ "UPDATE Planeta_has_Instalacion "
	   				+ "SET ultimaGeneracion = NOW() "
	   				+ "WHERE Planeta_coordenadaX =" +planetaDestino.getCoordenadaX()
	   				+ " AND Planeta_coordenadaY =" +planetaDestino.getCoordenadaY()
	   				+ " AND Planeta_Sistema_nombreSistema ='" +planetaDestino.getSistemanombreSistema()  
	   				+ "' AND Instalacion_name='Mina de Metal';\n"
	   			+"UPDATE  Planeta_has_Recurso "
			   				+ "SET cantidad = cantidad + ((@nivelInstalacion * @generacionBase) * @segundosGenRecursos) "
			   				+ "WHERE Recurso_name = 'Metal' "
			   				+ "AND Recurso_instalacion_name = 'Mina de Metal' "
			   				+ "AND Planeta_coordenadaX = " + planetaDestino.getCoordenadaX()
			   				+ " AND Planeta_coordenadaY = " + planetaDestino.getCoordenadaY()
			   				+ " AND Planeta_Sistema_nombreSistema = '" + planetaDestino.getSistemanombreSistema() + "';\n"
			   	+ "SELECT nivelInstalacion, ultimaGeneracion INTO @nivelInstalacion, @ultimaGeneracion "
			   				+ "FROM Planeta_has_Instalacion "
			   				+ "WHERE Planeta_coordenadaX =" +planetaDestino.getCoordenadaX()
			   		   		+ " AND Planeta_coordenadaY =" +planetaDestino.getCoordenadaY()
			   		   		+ " AND Planeta_Sistema_nombreSistema ='" +planetaDestino.getSistemanombreSistema()  
			   		   		+ "' AND Instalacion_name='Mina de Oro';\n"
			    + "IF " + idPirata  +" > 0 THEN\n"
	   				+ "SELECT nivelDefecto INTO @nivelInstalacion "
	   					+ "FROM Pirata_has_Instalacion "
	   					+ "WHERE Pirata_idPirata =" +idPirata
	   					+ " AND Instalacion_name='Mina de Oro';\n"
	   			+ "END IF;\n"
			   	+ "SELECT generacionBase INTO @generacionBase "
			   		   		+ "FROM Instalacion "
			   		   		+ "WHERE name = 'Mina de Oro';\n"
			   	+ "SELECT TIMESTAMPDIFF(SECOND, @ultimaGeneracion,NOW()) INTO @segundosGenRecursos;\n"
			   	+ "UPDATE Planeta_has_Instalacion "
			   		   		+ "SET ultimaGeneracion = NOW() "
			   		   		+ "WHERE Planeta_coordenadaX =" +planetaDestino.getCoordenadaX()
			   		   		+ " AND Planeta_coordenadaY =" +planetaDestino.getCoordenadaY()
			   		   		+ " AND Planeta_Sistema_nombreSistema ='" +planetaDestino.getSistemanombreSistema()  
			   		   		+ "' AND Instalacion_name='Mina de Oro';\n"
			   	+"UPDATE  Planeta_has_Recurso "
			   				+ "SET cantidad = cantidad + ((@nivelInstalacion * @generacionBase) * @segundosGenRecursos) "
			   				+ "WHERE Recurso_name = 'Oro' "
			   				+ "AND Recurso_instalacion_name = 'Mina de Oro' "
			   				+ "AND Planeta_coordenadaX = " + planetaDestino.getCoordenadaX()
			   				+ " AND Planeta_coordenadaY = " + planetaDestino.getCoordenadaY()
			   				+ " AND Planeta_Sistema_nombreSistema = '" + planetaDestino.getSistemanombreSistema() + "';\n"
			   	+ "SELECT nivelInstalacion, ultimaGeneracion INTO @nivelInstalacion, @ultimaGeneracion "
					   		+ "FROM Planeta_has_Instalacion "
					   		+ "WHERE Planeta_coordenadaX =" +planetaDestino.getCoordenadaX()
					   		+ " AND Planeta_coordenadaY =" +planetaDestino.getCoordenadaY()
					   		+ " AND Planeta_Sistema_nombreSistema ='" +planetaDestino.getSistemanombreSistema()  
					   		+ "' AND Instalacion_name='Plataforma Petrolifera';\n"
				 + "IF " + idPirata  +" > 0 THEN\n"
	   				+ "SELECT nivelDefecto INTO @nivelInstalacion "
	   					+ "FROM Pirata_has_Instalacion "
	   					+ "WHERE Pirata_idPirata =" +idPirata
	   					+ " AND Instalacion_name='Plataforma Petrolifera';\n"
	   			+ "END IF;\n"
				+ "SELECT generacionBase INTO @generacionBase "
					   		+ "FROM Instalacion "
					   		+ "WHERE name = 'Plataforma Petrolifera';\n"
				+ "SELECT TIMESTAMPDIFF(SECOND, @ultimaGeneracion,NOW()) INTO @segundosGenRecursos;\n"
				+ "UPDATE Planeta_has_Instalacion "
					   		+ "SET ultimaGeneracion = NOW() "
					   		+ "WHERE Planeta_coordenadaX =" +planetaDestino.getCoordenadaX()
					   		+ " AND Planeta_coordenadaY =" +planetaDestino.getCoordenadaY()
					   		+ " AND Planeta_Sistema_nombreSistema ='" +planetaDestino.getSistemanombreSistema()  
					   		+ "' AND Instalacion_name='Plataforma Petrolifera';\n"
			    +"UPDATE  Planeta_has_Recurso "
					   		+ "SET cantidad = cantidad + ((@nivelInstalacion * @generacionBase) * @segundosGenRecursos) "
					   		+ "WHERE Recurso_name = 'Petroleo' "
					   		+ "AND Recurso_instalacion_name = 'Plataforma Petrolifera' "
					   		+ "AND Planeta_coordenadaX = " + planetaDestino.getCoordenadaX()
					   		+ " AND Planeta_coordenadaY = " + planetaDestino.getCoordenadaY()
					   		+ " AND Planeta_Sistema_nombreSistema = '" + planetaDestino.getSistemanombreSistema() + "';\n"
				+ "OPEN atacanteNom;\n"
				+ "atacantes_loopInicio: LOOP\n"
					+ "Fetch atacanteNom INTO nom;\n"
					+ "IF done THEN\n"
						+ "LEAVE atacantes_loopInicio;\n"
					+ "END IF;\n"
					+ "INSERT INTO NaveCombate (nombreNave, ataqueNave, hullNave, escudoNave, velocidadNave, agilidadNave, capacidadCarga)\n"
					+ "SELECT nombreNave, ataqueNave, hullNave, escudoNave, velocidadNave, agilidadNave, capacidadCarga\n"
						+ "FROM Nave WHERE nombreNave = nom;\n"
					+ "SELECT cantidad INTO cantidad_aux "
						+ "FROM Movimiento_has_Nave "
						+ "WHERE Movimiento_idMovimiento =" +movimiento.getIdMovimiento()
						+ " AND Nave_nombreNave = nom;\n"
					+"UPDATE NaveCombate "
						+"SET "
							+ "ataqueNave = ataqueNave * cantidad_aux,"
							+ "hullNave = hullNave * cantidad_aux,"
							+ "escudoNave = escudoNave * cantidad_aux,"
							+ "agilidadNave = agilidadNave * cantidad_aux,"
							+ "cantidad = cantidad_aux "
						+ "WHERE nombreNave = nom;\n"
				+ "END LOOP;\n"
				+ "SET done = FALSE;\n"
				+ "CLOSE atacanteNom;\n"
				+ "OPEN defensorNom;\n"
				+ "defensores_loopInicio: LOOP\n"
					+ "Fetch defensorNom INTO nom;\n"
					+ "IF done THEN\n"
						+ "LEAVE defensores_loopInicio;\n"
					+ "END IF;\n"
					+ "INSERT INTO NaveDefensa (nombreNave, ataqueNave, hullNave, escudoNave, velocidadNave, agilidadNave, capacidadCarga)\n"
					+ "SELECT nombreNave, ataqueNave, hullNave, escudoNave, velocidadNave, agilidadNave, capacidadCarga\n"
						+ "FROM Nave WHERE nombreNave = nom;\n"
					+ "SELECT cantidad INTO cantidad_aux " 
						+ "FROM Planeta_has_Nave "
						+ "WHERE Planeta_coordenadaX =" +planetaDestino.getCoordenadaX()
						+ " AND Planeta_coordenadaY =" +planetaDestino.getCoordenadaY()
						+ " AND Planeta_Sistema_nombreSistema= '" +planetaDestino.getSistemanombreSistema()
						+ "' AND Nave_nombreNave = nom;\n"
					+ "UPDATE NaveDefensa "
						+ "SET ataqueNave = ataqueNave * cantidad_aux,"
							+ "hullNave = hullNave * cantidad_aux, "
							+ "escudoNave = escudoNave * cantidad_aux,"
							+ "agilidadNave = agilidadNave * cantidad_aux, "
							+ "cantidad = cantidad_aux "
						+ "WHERE nombreNave = nom;\n"
				+ "END LOOP;\n"
				+ "SET done = FALSE;\n"
				+ "CLOSE defensorNom;\n"
				+"INSERT INTO Mensaje (asunto, descripcion, TipoMensaje_name, fechaEnvio) VALUES ('LOG', 'Tras defensor', 'Sistema', @fecha);\n"
				+ "loopPrincipal: WHILE v1 < 6 DO\n"
					+ "SELECT COUNT(*) INTO navesRestantes "
						+ "FROM NaveCombate "
						+ "WHERE cantidad > 0;\n"
					+ "IF navesRestantes <= 0 THEN\n"
			   			+ "SET victoria = FALSE;\n"
			   			+ "SET v1 = 6;\n"
			   			+ "leave loopPrincipal;\n"
			   		+ "END IF;\n"
			   		+ "SELECT COUNT(*) INTO navesRestantes "
			   			+ "FROM NaveDefensa "
			   			+ "WHERE cantidad > 0;\n"
			   		+ "IF navesRestantes <= 0 THEN\n"
			   			+ "SET victoria = TRUE;\n"
			   			+ "SET v1 = 6;\n"
			   			+ "leave loopPrincipal;\n"
			   		+ "END IF;\n"
			   		+ "OPEN atacanteNom;\n"
			   		+ "atacantes_loop: LOOP\n"
			   			+ "Fetch atacanteNom INTO nom;\n"
			   			+ "IF done THEN\n"
			   				+ "LEAVE atacantes_loop;\n"
			   				+ "CLOSE atacanteNom;\n"
			   			+ "END IF;\n"
			   			+ "SELECT COUNT(*) INTO @rank "
			   				+ "FROM NaveDefensa "
			   				+ "WHERE cantidad > 0;\n"
			   			+ "SET randomNumber = 0;\n"
			   			+ "SELECT FLOOR(RAND() * (@rank - 1 + 1)) + 1 INTO randomNumber;\n"
			   			+ "SET @rank = 0;\n"
			   			+ "SELECT "
			   				+ "nombreNave,"
			   				+ "ataqueNave,"
			   				+ "hullNave,"
			   				+ "escudoNave,"
			   				+ "agilidadNave,"
			   				+ "cantidad	"
			   				+ "INTO nomEnem , atEnem , hullEnem , shieldEnem , agEnem , cantEnem FROM "
			   				+ "("
			   					+ "SELECT @rank := @rank + 1 AS posicion,"
			   						+ "nombreNave,"
			   						+ "ataqueNave,"
			   						+ "hullNave,"
			   						+ "escudoNave,"
			   						+ "agilidadNave,"
			   						+ "cantidad "
			   						+ "FROM NaveDefensa	"
			   						+ "WHERE cantidad > 0"
			   				+ ") AS RankTable "
			   				+ "WHERE posicion = randomNumber;\n"
			   			+ "SELECT ataqueNave INTO atAl "
			   				+ "FROM NaveCombate "
			   				+ "WHERE nombreNave = nom;\n"
			   			+ "IF atAl > shieldEnem THEN\n"
			   				+ "SET hullEnem = hullEnem - (atAl - shieldEnem);\n"
			   				+ "SET shieldEnem = 0;\n"
			   			+ "ELSE\n"
			   				+ "SET shieldEnem = shieldEnem - atAl;\n"
			   			+ "END IF;\n"
			   			+ "IF hullEnem <= 0 THEN\n"
			   				+ "UPDATE NaveDefensa SET cantidad = 0 WHERE nombreNave = nomEnem;\n"
			   			+ "ELSE\n"
			   				+ "SELECT hullNave, escudoNave, ataqueNave, agilidadNave INTO hullOne, shieldOne, atOne, agOne "
			   				+ "FROM Nave "
			   				+ "WHERE nombreNave = nomEnem;\n"
			   				+ "SET cantidad_aux  = FLOOR(hullEnem/hullOne);\n"
			   				+ "UPDATE NaveDefensa "
			   					+ "SET "
			   						+ "cantidad = cantidad_aux, "
			   						+ "hullNave = hullEnem,"
			   						+ "escudoNave = shieldEnem,"
			   						+ "ataqueNave = atOne * cantidad_aux,"
			   						+ "agilidadNave = agOne * cantidad_aux "
			   					+ "WHERE nombreNave = nomEnem;\n"
					   	+ "END IF;\n"
			   		+ "END LOOP;\n"
			   		+ "SET done = FALSE;\n"
			   		+ "CLOSE atacanteNom;\n"
			   		+ "SELECT COUNT(*) INTO navesRestantes "
			   			+ "FROM NaveDefensa "
			   			+ "WHERE cantidad > 0;\n"
			   		+ "IF navesRestantes <= 0 THEN\n"
			   			+ "SET victoria = TRUE;\n"
			   		    + "SET v1 = 6;\n"
			   		    + "leave loopPrincipal;\n"
			   		+ "END IF;\n"
			   		+ "OPEN defensorNom;\n"
			   		+ "defensores_loop: LOOP\n"
		   				+ "Fetch defensorNom INTO nom;\n"
		   				+ "IF done THEN\n"
		   					+ "LEAVE defensores_loop;\n"
		   					+ "CLOSE defensorNom;\n"
		   				+ "END IF;\n"
		   				+ "SELECT COUNT(*) INTO @rank "
		   					+ "FROM NaveCombate "
		   					+ "WHERE cantidad > 0;\n"
		   				+ "SET randomNumber = 0;\n"
		   				+ "SELECT FLOOR(RAND() * (@rank - 1 + 1)) + 1 INTO randomNumber;\n"
		   				+ "SET @rank = 0;\n"
		   				+ "SELECT "
		   					+ "nombreNave,"
		   					+ "ataqueNave,"
		   					+ "hullNave,"
		   					+ "escudoNave,"
		   					+ "agilidadNave,"
		   					+ "cantidad	"
		   					+ "INTO nomEnem , atEnem , hullEnem , shieldEnem , agEnem , cantEnem FROM "
		   					+ "("
		   						+ "SELECT @rank := @rank + 1 AS posicion,"
		   							+ "nombreNave,"
		   							+ "ataqueNave,"
		   							+ "hullNave,"
		   							+ "escudoNave,"
		   							+ "agilidadNave,"
		   							+ "cantidad "
		   							+ "FROM NaveCombate	"
		   							+ "WHERE cantidad > 0"
		   					+ ") AS RankTable "
		   					+ "WHERE posicion = randomNumber;\n"
		   				+ "SELECT ataqueNave INTO atAl "
		   					+ "FROM NaveDefensa "
		   					+ "WHERE nombreNave = nom;\n"
		   				+ "IF atAl > shieldEnem THEN\n"
		   					+ "SET hullEnem = hullEnem - (atAl - shieldEnem);\n"
		   					+ "SET shieldEnem = 0;\n"
		   				+ "ELSE\n"
		   					+ "SET shieldEnem = shieldEnem - atAl;\n"
		   				+ "END IF;\n"
		   				+ "IF hullEnem <= 0 THEN\n"
		   					+ "UPDATE NaveCombate SET cantidad = 0 WHERE nombreNave = nomEnem;\n"
		   				+ "ELSE\n"
		   					+ "SELECT hullNave, escudoNave, ataqueNave, agilidadNave INTO hullOne, shieldOne, atOne, agOne "
		   						+ "FROM Nave "
		   						+ "WHERE nombreNave = nomEnem;\n"
		   					+ "SET cantidad_aux  = CEIL(hullEnem/hullOne);\n"
		   					+ "UPDATE NaveCombate "
		   						+ "SET "
		   							+ "cantidad = cantidad_aux, "
		   							+ "hullNave = hullEnem,"
		   							+ "escudoNave = shieldEnem,"
		   							+ "ataqueNave = atOne * cantidad_aux,"
		   							+ "agilidadNave = agOne * cantidad_aux "
		   						+ "WHERE nombreNave = nomEnem;\n"
		   				+ "END IF;\n"
		   			+ "END LOOP;\n"
		   			+ "SET done = FALSE;\n"
		   			+ "CLOSE defensorNom;\n"
		   			+ "SET v1 = v1 +1;\n"
		   		+ "END WHILE;\n"
		   		+ "INSERT INTO InformeBatalla (Movimiento_idMovimiento) VALUES(" +movimiento.getIdMovimiento() +");\n"
		   		+ "SELECT idBatalla INTO idBat FROM InformeBatalla WHERE Movimiento_idMovimiento = " +movimiento.getIdMovimiento() +";\n"
		   		+ "UPDATE Planeta"
		   			+ " SET InformeBatalla_idBatalla = idBat"
		   			+ " WHERE coordenadaX = " +planetaDestino.getCoordenadaX()
		   			+ " AND coordenadaY = " +planetaDestino.getCoordenadaY()
		   			+ " AND Sistema_nombreSistema= '" +planetaDestino.getSistemanombreSistema() +"';\n"
		   		+ "OPEN defensorNom;\n"
		   		+ "defensores_loopResultado: LOOP\n"
		   			+ "Fetch defensorNom INTO nom;\n"
		   			+ "IF done THEN\n"
		   				+ "LEAVE defensores_loopResultado;\n"
		   			+ "END IF;\n"
		   			+ "SELECT cantidad INTO cantidad_aux "
		   				+ "FROM NaveDefensa "
		   				+ "WHERE nombreNave = nom;\n"
		   			+ "SELECT cantidad INTO cantidad_enviada "
		   				+ "FROM Planeta_has_Nave "
		   				+ "WHERE Nave_nombreNave = nom "
		   				+ "AND Planeta_coordenadaX =" +planetaDestino.getCoordenadaX()
		   				+ " AND Planeta_coordenadaY =" +planetaDestino.getCoordenadaY()
		   				+ " AND Planeta_Sistema_nombreSistema ='" +planetaDestino.getSistemanombreSistema()  +"';\n"
		   			+ "UPDATE Planeta_has_Nave "
		   				+ "SET cantidad = cantidad_aux  "
		   				+ "WHERE Nave_nombreNave = nom "
		   				+ "AND Planeta_coordenadaX =" +planetaDestino.getCoordenadaX()
		   				+ " AND Planeta_coordenadaY =" +planetaDestino.getCoordenadaY()
		   				+ " AND Planeta_Sistema_nombreSistema ='" +planetaDestino.getSistemanombreSistema()  +"';\n"
		   			+ "IF " + usuarioDestino + " is not null THEN\n"
		   				+ "UPDATE Usuario_has_Nave\n"
		   				+ "SET cantidad = cantidad - (cantidad_enviada - cantidad_aux)\n"
		   				+"WHERE Usuario_username = " + usuarioDestino
		   				+ " AND Nave_nombreNave = nom;\n"
		   			+ "END IF;\n"
		   			+ "INSERT INTO InformeBatalla_has_Nave_Defensa VALUES(idBat, nom, cantidad_enviada, cantidad_enviada - cantidad_aux);\n"
		   		+ "END LOOP;\n"
		   		+ "SET done = FALSE;\n"
		   		+ "CLOSE defensorNom;\n"
		   		+ "DROP EVENT movimiento_" +movimiento.getIdMovimiento() + "_ships;\n"
		   		+ "IF victoria THEN\n"
		   			+ "INSERT into Movimiento (tiempoLlegada, tiempoEnvio,Usuario_username, "
		   				+ "Planeta_coordenadaX,Planeta_coordenadaY,Planeta_Sistema_nombreSistema,movimientoCancelado, Movimiento_ida) Values(?,?,?,?,?,?,0,?);\n"
					+ "Select idMovimiento INTO idMov "
						+ "FROM Movimiento"
						+" WHERE Movimiento_ida=" + movimiento.getIdMovimiento() +";\n"
					+ "OPEN atacanteNom;\n"
		   			+ "atacantes_loopResultado: LOOP\n"
		   				+ "Fetch atacanteNom INTO nom;\n"
		   				+ "IF done THEN\n"
		   					+ "LEAVE atacantes_loopResultado;\n"
		   				+ "END IF;\n"
		   				+ "SELECT cantidad INTO cantidad_aux "
		   					+ "FROM NaveCombate  "
		   					+ "WHERE nombreNave = nom;\n"
		   				+" SET totalCargo = totalCargo + (SELECT capacidadCarga*cantidad FROM NaveCombate WHERE nombreNave = nom);\n"
		   				+"SELECT FLOOR(cantidad * 0.3) INTO metalObtenido "
		   					+ "FROM Planeta_has_Recurso "
		   					+ "WHERE Recurso_name = 'Metal' "
		   					+ "AND Recurso_instalacion_name = 'Mina de Metal' "
		   					+ "AND Planeta_coordenadaX = " + planetaDestino.getCoordenadaX()
		   					+ " AND Planeta_coordenadaY = " + planetaDestino.getCoordenadaY()
		   					+ " AND Planeta_Sistema_nombreSistema = '" + planetaDestino.getSistemanombreSistema() + "';\n"
		   				+"SELECT FLOOR(cantidad * 0.3) INTO oroObtenido "
				   			+ "FROM Planeta_has_Recurso "
				   			+ "WHERE Recurso_name = 'Oro' "
				   			+ "AND Recurso_instalacion_name = 'Mina de Oro' "
				   			+ "AND Planeta_coordenadaX = " + planetaDestino.getCoordenadaX()
				   			+ " AND Planeta_coordenadaY = " + planetaDestino.getCoordenadaY()
				   			+ " AND Planeta_Sistema_nombreSistema = '" + planetaDestino.getSistemanombreSistema() + "';\n"
				   		+"SELECT FLOOR(cantidad * 0.3) INTO petroleoObtenido "
				   			+ "FROM Planeta_has_Recurso "
				   			+ "WHERE Recurso_name = 'Petroleo' "
				   			+ "AND Recurso_instalacion_name = 'Plataforma Petrolifera' "
				   			+ "AND Planeta_coordenadaX = " + planetaDestino.getCoordenadaX()
				   			+ " AND Planeta_coordenadaY = " + planetaDestino.getCoordenadaY()
				   			+ " AND Planeta_Sistema_nombreSistema = '" + planetaDestino.getSistemanombreSistema() + "';\n"
				   		+ "SET offsetRecursos = 0;\n"
				   		+ "IF metalObtenido < totalCargo/3 THEN\n"
				   			+ "SET offsetRecursos = FLOOR(totalCargo/3 - metalObtenido);\n"
				   		+ "ELSE\n"
				   			+ "SET metalObtenido = FLOOR(totalCargo/3);\n"
				   		+ "END IF;\n"
				   		+ "IF oroObtenido < (totalCargo/3 + offsetRecursos) THEN\n"
			   				+ "SET offsetRecursos = FLOOR((totalCargo/3 + offsetRecursos) - oroObtenido);\n"
			   			+ "ELSE\n"
			   				+ "SET oroObtenido = (totalCargo/3 + offsetRecursos);\n"
			   			+ "END IF;\n"
			   			+ "IF petroleoObtenido >= (totalCargo/3 + offsetRecursos) THEN\n"
			   				+ "SET petroleoObtenido = (totalCargo/3 + offsetRecursos);\n"
			   			+ "END IF;\n"
			   			+"UPDATE  Planeta_has_Recurso "
			   				+ "SET cantidad = cantidad - metalObtenido "
			   				+ "WHERE Recurso_name = 'Metal' "
			   				+ "AND Recurso_instalacion_name = 'Mina de Metal' "
			   				+ "AND Planeta_coordenadaX = " + planetaDestino.getCoordenadaX()
			   				+ " AND Planeta_coordenadaY = " + planetaDestino.getCoordenadaY()
			   				+ " AND Planeta_Sistema_nombreSistema = '" + planetaDestino.getSistemanombreSistema() + "';\n"
			   			+"UPDATE  Planeta_has_Recurso "
					   		+ "SET cantidad = cantidad - oroObtenido "
					   		+ "WHERE Recurso_name = 'Oro' "
					   		+ "AND Recurso_instalacion_name = 'Mina de Oro' "
					   		+ "AND Planeta_coordenadaX = " + planetaDestino.getCoordenadaX()
					   		+ " AND Planeta_coordenadaY = " + planetaDestino.getCoordenadaY()
					   		+ " AND Planeta_Sistema_nombreSistema = '" + planetaDestino.getSistemanombreSistema() + "';\n"
					   	+"UPDATE  Planeta_has_Recurso "
					   		+ "SET cantidad = cantidad - petroleoObtenido "
					   		+ "WHERE Recurso_name = 'Petroleo' "
					   		+ "AND Recurso_instalacion_name = 'Plataforma Petrolifera' "
					   		+ "AND Planeta_coordenadaX = " + planetaDestino.getCoordenadaX()
					   		+ " AND Planeta_coordenadaY = " + planetaDestino.getCoordenadaY()
					   		+ " AND Planeta_Sistema_nombreSistema = '" + planetaDestino.getSistemanombreSistema() + "';\n"
		   				+ "INSERT INTO Movimiento_has_Nave (Movimiento_idMovimiento, Nave_nombreNave, cantidad) VALUES (idMov, nom, cantidad_aux);\n"
		   				+ "SELECT cantidad INTO cantidad_enviada "
		   					+ "FROM Movimiento_has_Nave "
		   					+ "WHERE Movimiento_idMovimiento = " +movimiento.getIdMovimiento()
		   					+ " AND Nave_nombreNave = nom;\n"
		   				+ "UPDATE Usuario_has_Nave\n"
		   					+ "SET cantidad = cantidad - (cantidad_enviada - cantidad_aux)\n"
		   					+"WHERE Usuario_username = '" + planetaOrigen.getUsuariousername().getUsername()
							+ "' AND Nave_nombreNave = nom;\n"
						+ "INSERT INTO InformeBatalla_has_Nave_Ataque VALUES(idBat, nom, cantidad_enviada, cantidad_enviada - cantidad_aux);\n"
		   			+ "END LOOP;\n"
		   			+ "SET done = FALSE;\n"
		   			+ "CLOSE atacanteNom;\n"
		   			+ "ALTER EVENT movimiento_" +movimiento.getIdMovimiento() + "_battleShips ON SCHEDULE AT '" +fechaVuelta +"';\n"
				   	+ "INSERT INTO InformeBatalla_has_Recurso VALUES(idBat, 'Metal', 'Mina de Metal', metalObtenido);\n"
				   	+ "INSERT INTO InformeBatalla_has_Recurso VALUES(idBat, 'Oro', 'Mina de Oro', oroObtenido);\n"
		   			+ "INSERT INTO InformeBatalla_has_Recurso VALUES(idBat, 'Petroleo', 'Plataforma petrolifera', petroleoObtenido);\n"
		   			+ "INSERT INTO Mensaje (asunto, descripcion, TipoMensaje_name, fechaEnvio, InformeBatalla_idBatalla) VALUES ('Victoria Vs "+ movimiento.getPlaneta().getNombrePlaneta()+"', 'Victoria', 'Batalla', @fecha, idBat);\n"
		   			+ "IF " + idPirata  +" > 0 THEN\n"
		   				+ "SET randomNumberFloat = 0;\n"
			   			+ "SELECT RAND() * (100 - 1 + 1) + 1 INTO randomNumberFloat;\n"
			   			+ "SELECT Nave_nombreNave INTO naveDesbloqueada"
			   				+ " FROM Pirata_has_Desbloqueo_Nave\n" 
			   				+ "WHERE Pirata_idPirata = " + idPirata +"\n"  
			   				+ "AND probabilidadDesbloqueo > randomNumberFloat\n"
			   				+ "AND Nave_nombreNave not IN (SELECT Nave_nombreNave as nombreNave\n" 
														  	+ "FROM Usuario_has_Nave\n"
															+ "WHERE Usuario_username = '" +planetaOrigen.getUsuariousername().getUsername()  +"')\n" 
			   				+ "ORDER BY probabilidadDesbloqueo\n" 
			   				+ "LIMIT 1;\n"
		   				+ "IF naveDesbloqueada is not null THEN\n"
		   					+ "UPDATE InformeBatalla "
		   						+ "SET Nave_nombreNaveDesbloqueada = naveDesbloqueada "
		   						+ "WHERE idBatalla = idBat;\n"
		   					+ "INSERT INTO Usuario_has_Nave (Usuario_username, Nave_nombreNave, cantidad) VALUES('" +planetaOrigen.getUsuariousername().getUsername() +"', naveDesbloqueada, 0);\n"
				   			+ "INSERT INTO Planeta_has_Nave (Planeta_coordenadaX, Planeta_coordenadaY, Planeta_Sistema_nombreSistema, Nave_nombreNave, cantidad) VALUES('" +planetaOrigen.getCoordenadaX() +"', '" +planetaOrigen.getCoordenadaY() +"', '" +planetaOrigen.getSistemanombreSistema() +"', naveDesbloqueada, 0);\n"
		   				+ "END IF;\n"
		   			+ "END IF;\n"
		   			+ "SELECT LAST_INSERT_ID() INTO idMen;\n"
				   	+ "INSERT INTO Usuario_has_Mensaje (Usuario_username, Mensaje_idMensaje, descartado) VALUES ('" +planetaOrigen.getUsuariousername().getUsername() + "', idMen, 0);\n"
				   	+ "IF " + usuarioDestino + " is not null THEN\n"
		   				+ "INSERT INTO Mensaje (asunto, descripcion, TipoMensaje_name, fechaEnvio, InformeBatalla_idBatalla) VALUES ('Derrota Vs "+ planetaOrigen.getNombrePlaneta()+"', 'Derrota', 'Batalla', @fecha, idBat);\n"
		   				+ "SELECT LAST_INSERT_ID() INTO idMen;\n"
		   				+ "INSERT INTO Usuario_has_Mensaje (Usuario_username, Mensaje_idMensaje, descartado) VALUES (" +usuarioDestino + ", idMen, 0);\n"
		   			+ "END IF;\n"
		   		+ "ELSE\n"
		   			+ "DROP EVENT movimiento_" +movimiento.getIdMovimiento() + "_battleShips;\n"
		   			+ "OPEN atacanteNom;\n"
		   			+ "atacantes_loopActualizarNaves: LOOP\n"
		   				+ "Fetch atacanteNom INTO nom;\n"
		   				+ "IF done THEN\n"
		   					+ "LEAVE atacantes_loopActualizarNaves;\n"
		   				+ "END IF;\n"
		   				+ "SELECT cantidad INTO cantidad_enviada "
	   						+ "FROM Movimiento_has_Nave "
	   						+ "WHERE Movimiento_idMovimiento = " +movimiento.getIdMovimiento()
	   						+ " AND Nave_nombreNave = nom;\n"
		   				+ "UPDATE Usuario_has_Nave\n"
		   					+ "SET cantidad = cantidad - cantidad_enviada\n"
		   					+ "WHERE Usuario_username = '" + movimiento.getUsuariousername().getUsername()
		   					+ "' AND Nave_nombreNave = nom;\n"
		   				+ "INSERT INTO InformeBatalla_has_Nave_Ataque VALUES(idBat, nom, cantidad_enviada, cantidad_enviada);\n"
		   			+ "END LOOP;\n"
		   			+ "SET done = FALSE;\n"
		   			+ "CLOSE atacanteNom;\n"
		   			+ "INSERT INTO Mensaje (asunto, descripcion, TipoMensaje_name, fechaEnvio, InformeBatalla_idBatalla) VALUES ('Derrota Vs "+ movimiento.getPlaneta().getNombrePlaneta() +"', 'Derrota', 'Batalla', @fecha, idBat);\n"
					+ "SELECT idMensaje INTO idMen FROM Mensaje WHERE InformeBatalla_idBatalla = idBat;\n"
				   	+ "INSERT INTO Usuario_has_Mensaje (Usuario_username, Mensaje_idMensaje, descartado) VALUES ('" +planetaOrigen.getUsuariousername().getUsername() + "', idMen, 0);\n"
				   	+ "IF " + usuarioDestino + " is not null THEN\n"
			   			+ "INSERT INTO Mensaje (asunto, descripcion, TipoMensaje_name, fechaEnvio, InformeBatalla_idBatalla) VALUES ('Victoria Vs "+ planetaOrigen.getNombrePlaneta()+"', 'Victoria', 'Batalla', @fecha, idBat);\n"
			   			+ "SELECT LAST_INSERT_ID() INTO idMen;\n"
			   			+ "INSERT INTO Usuario_has_Mensaje (Usuario_username, Mensaje_idMensaje, descartado) VALUES (" +usuarioDestino + ", idMen, 0);\n"
			   		+ "END IF;\n"
				+ "END IF;\n"
		   		+ "DROP TABLE NaveCombate;\n"
		   		+ "DROP TABLE NaveDefensa;\n"
		   		+ "END;";
		
		String recuperarNavesTrasBatalla = "CREATE EVENT movimiento_" +movimiento.getIdMovimiento() + "_battleShips ON SCHEDULE AT '2037-12-12 23:59:59' DO BEGIN\n"
												+ "DECLARE nom Varchar(45);\n"
												+ "DECLARE cantidad_aux INT;\n"
												+ "DECLARE done INT DEFAULT FALSE;\n"
												+ "DECLARE atacanteNom CURSOR FOR SELECT Nave_nombreNave "
													+ "FROM Movimiento_has_Nave, (SELECT idMovimiento AS maximo "
																					+ "FROM Movimiento "
																					+ "WHERE Movimiento_ida=" + movimiento.getIdMovimiento() +" ) as max_table "
													+ "WHERE Movimiento_idMovimiento = max_table.maximo;\n"
												+ "DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;\n"
												+ "OPEN atacanteNom;\n"
												+ "atacantes_loopResultado: LOOP\n"
	   												+ "Fetch atacanteNom INTO nom;\n"
	   												+ "IF done THEN\n"
	   													+ "LEAVE atacantes_loopResultado;\n"
	   												+ "END IF;\n"
	   												+"SELECT cantidad INTO cantidad_aux "
	   													+ "FROM Movimiento_has_Nave,  (SELECT idMovimiento AS maximo "
	   																				+ "FROM Movimiento "  
	   																				+ "WHERE Movimiento_ida=" + movimiento.getIdMovimiento() +" ) as max_table "
	   													+ "WHERE Movimiento_idMovimiento = max_table.maximo"
	   													+ " AND Nave_nombreNave = nom;\n"
	   												+ "UPDATE Planeta_has_Nave\n"
	   													+ "SET cantidad = cantidad + cantidad_aux\n"
	   													+"WHERE Nave_NombreNave = nom"
	   													+ " AND Planeta_coordenadaX = " +planetaOrigen.getCoordenadaX() 
														+ " AND Planeta_coordenadaY = " +planetaOrigen.getCoordenadaY()
														+ " AND Planeta_Sistema_nombreSistema ='" +planetaOrigen.getSistemanombreSistema() +"';\n"
	   											+ "END LOOP;\n"
												+ "CLOSE atacanteNom;\n"
												+ "SET done = FALSE;\n"
												+ "END;";
		String recuperarNaves = "CREATE EVENT IF NOT EXISTS movimiento_" +movimiento.getIdMovimiento() +"_ships" +"\nON SCHEDULE AT '2037-12-12 23:59:59'\nDO BEGIN\n" +setNaves +"END;";
		
		session.doWork(new Work()
		{

			@Override
			public void execute(Connection conn) throws SQLException 
			{
				PreparedStatement stmt = null;
				
				try
				{		
					stmt = conn.prepareStatement(sentencia);
					stmt.setInt(1, 0);
					stmt.setTimestamp(1, fechaVuelta);
					stmt.setTimestamp(2, fechaLlegada);
					stmt.setString(3, planetaOrigen.getUsuariousername().getUsername());
					stmt.setInt(4, planetaOrigen.getCoordenadaX());
					stmt.setInt(5, planetaOrigen.getCoordenadaY());
					stmt.setString(6, planetaOrigen.getSistemanombreSistema());   
					stmt.setInt(7, movimiento.getIdMovimiento());
					stmt.executeUpdate();
					
					stmt = conn.prepareStatement(recuperarNavesTrasBatalla);
					stmt.executeUpdate();
					
					stmt = conn.prepareStatement(recuperarNaves);
					stmt.executeUpdate();
					
				}catch(Exception e)
				{
					System.out.println(e);
				}finally
				{
					stmt.close();
				}
						
			}
			
		});
		
		em.getTransaction().commit();
		session.close();
	}
	
}
