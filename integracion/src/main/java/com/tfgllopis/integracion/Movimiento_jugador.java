package com.tfgllopis.integracion;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;

public class Movimiento_jugador extends Movimiento_jugador_Ventana 
{
	static final int SECOND = 1000;        // no. of ms in a second
	static final int MINUTE = SECOND * 60; // no. of ms in a minute
	static final int HOUR = MINUTE * 60;   // no. of ms in an hour
	static final int DAY = HOUR * 24;      // no. of ms in a day
	
	private Movimiento movimiento;
	
	public Movimiento_jugador(Movimiento movimiento, PlanetaRepository planetaRepo, MovimientoRepository movimientoRepo, MovimientohasNaveRepository movimientoNaveRepo)
	{
		Planeta planetaOrigen = planetaRepo.findByUsuarioUsername(movimiento.getUsuariousername());
		Planeta planetaUsuario = planetaRepo.findByUsuarioUsername(((VaadinUI) UI.getCurrent()).getUsuario());
		
		if(movimiento.getPlaneta().equals(planetaUsuario))
		{
			regresarB.setEnabled(false);
		}
		
		if(planetaOrigen.equals(movimiento.getPlaneta()))
		{
			layoutOrigen.setVisible(false);
			coordenadasOrigenL.setVisible(false);
			regresarB.setEnabled(false);
		}
		
		this.movimiento = movimiento;
		imagenPlanetaOrigen.setSource( new FileResource(new File(new File("").getAbsolutePath() + "/images/" + "Planeta.png")));
		imagenMovimiento.setSource( new FileResource(new File(new File("").getAbsolutePath() + "/images/" + "Movimiento.png")));
		imagenPlanetaDestino.setSource( new FileResource(new File(new File("").getAbsolutePath() + "/images/" + "Planeta.png")));
		nombreOrigen.setValue(planetaOrigen.getNombrePlaneta());
		coordenadasOrigenL.setValue("(" + planetaOrigen.getCoordenadaX() + "," + planetaOrigen.getCoordenadaY() + ")");
		nombreDestino.setValue(movimiento.getPlaneta().getNombrePlaneta());
		coordenadasDestinoL.setValue("(" + movimiento.getPlaneta().getCoordenadaX() + "," + movimiento.getPlaneta().getCoordenadaY() + ")");
		setExpandRatio(movimiento);
		setTiempoRestante(movimiento);
		
		configurarBotones(movimiento, planetaOrigen, movimientoRepo, movimientoNaveRepo);
				
	}
	
	private void configurarBotones(Movimiento movimiento, Planeta planetaOrigen, MovimientoRepository movimientoRepo, MovimientohasNaveRepository movimientoNaveRepo)
	{
		verNavesB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				((VaadinUI) UI.getCurrent()).setMovimiento(movimiento);
				doNavigate(Ver_naves.VIEW_NAME);
			}
		});
		
		regresarB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				Movimiento_jugador.cancelarAtaque(((VaadinUI) UI.getCurrent()).getEntitymanager(), planetaOrigen, movimiento, movimientoRepo, movimientoNaveRepo);
				doNavigate(Ver_movimiento.VIEW_NAME);
			}
		});
	}
	
	private void setExpandRatio(Movimiento movimiento)
	{
		float expandRatio;
		Date fechaAhora = new Date();
		double secondsTotal = (movimiento.getTiempoLlegada().getTime() - movimiento.getTiempoEnvio().getTime())/1000;
		double secondsRestante = (movimiento.getTiempoLlegada().getTime() - fechaAhora.getTime())/1000;
		double seconds = ((secondsTotal - secondsRestante) / secondsTotal);
		expandRatio = (float) (100 - (seconds * 100)) / 10;
		
		if(expandRatio < 0.5) expandRatio = (float) 0.5;
		hLayout.setExpandRatio(layoutTiempoRestante, expandRatio);
	}
	
	private void setTiempoRestante(Movimiento movimiento)
	{
		Date fechaAhora = new Date();
		long tiempoRestante = movimiento.getTiempoLlegada().getTime() - fechaAhora.getTime();
		int hours   = (int)((tiempoRestante % DAY) / HOUR);
		int minutes = (int)((tiempoRestante % HOUR) / MINUTE);
		int seconds = (int)((tiempoRestante % MINUTE) / SECOND);
		tiempoRestanteL.setValue(String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
	
	@Transactional
	public static void cancelarAtaque(EntityManager em, Planeta planetaOrigen, Movimiento antiguoMovimiento, MovimientoRepository movimientoRepo, MovimientohasNaveRepository movimientoNaveRepo)
	{
		Movimiento movimiento;
		List<MovimientohasNave> naves = movimientoNaveRepo.findByMovimientoidMovimiento(antiguoMovimiento.getIdMovimiento());
		ArrayList<MovimientohasNave> nuevoNavesMovimiento = new ArrayList<>();
		
		Date fechaAhora = new Date();
		Timestamp fechaEnvio = new Timestamp(fechaAhora.getTime());
		Calendar nuevaFechaC = Calendar.getInstance();
		int seconds = (int) ((fechaEnvio.getTime() - antiguoMovimiento.getTiempoEnvio().getTime())/1000);
		nuevaFechaC.add(Calendar.SECOND, seconds);
		Timestamp nuevaFecha = new Timestamp(nuevaFechaC.getTime().getTime());

		if(fechaAhora.getTime() > antiguoMovimiento.getTiempoLlegada().getTime()) return;
		
		em = em.getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		Session session = em.unwrap(Session.class);
		String sentencia = "ALTER EVENT movimiento_" +antiguoMovimiento.getIdMovimiento() +"_ships ON SCHEDULE AT ? ;";
		String eventoVuelta = "DROP EVENT movimiento_" +antiguoMovimiento.getIdMovimiento() +"_return;";
		String cancelarEventoBatalla = "DROP EVENT movimiento_" +antiguoMovimiento.getIdMovimiento() + "_battleShips;";
		
		movimiento = new Movimiento(nuevaFecha, fechaEnvio, (short) 0);
		movimiento.setUsuariousername(planetaOrigen.getUsuariousername());
		movimiento.setPlaneta(planetaOrigen);
		movimiento = movimientoRepo.save(movimiento);
		
		antiguoMovimiento.cancelarMovimiento();
		movimientoRepo.save(antiguoMovimiento);
		
		for(int i = 0; i < naves.size(); i++)
		{
			nuevoNavesMovimiento.add(new MovimientohasNave(movimiento.getIdMovimiento(), naves.get(i).getNavenombreNave(), naves.get(i).getCantidad()));
		}
		
		movimientoNaveRepo.saveAll(nuevoNavesMovimiento);
		
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
					stmt.setTimestamp(1, nuevaFecha);
					stmt.executeUpdate();
					
					stmt = conn.prepareStatement(eventoVuelta);
					stmt.executeUpdate();
					
					stmt = conn.prepareStatement(cancelarEventoBatalla);
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
