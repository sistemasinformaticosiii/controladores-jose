package com.practica.si3.dao;

import javax.sql.DataSource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.practica.si3.domain.CriterioBusqueda;
import com.practica.si3.domain.Oferta;
import com.practica.si3.domain.User;
import com.practica.si3.jdbc.OfertaRowMapper;


public class OfertaDaoImpl implements OfertaDao {
	
	@Autowired
	DataSource dataSource;

	public void insertData(Oferta oferta) {
		
		String sql = "INSERT INTO oferta " 
			+ "(codUsuario,tipo,categoria, titulo,fechaInicio,fechaFin,localidad,direccion,descripcion,plazasTotal, plazasDisponibles,precio,descuento) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(sql, new Object[] { oferta.getCodUsuario(), oferta.getTipo(), oferta.getCategoria(), oferta.getTitulo(), oferta.getFechaInicio(),		
			oferta.getFechaFin(), oferta.getLocalidad().toUpperCase(), oferta.getDireccion(), oferta.getDescripcion(), oferta.getPlazasTotal(), oferta.getPlazasDisponibles(),
			oferta.getPrecio(), oferta.getDescuento() });
	}
	
	@Override
	public void updateData(Oferta oferta) {

		String sql = "UPDATE oferta set codusuario = ?, tipo = ?, categoria = ?, titulo = ?, fechainicio = ?, fechafin = ?, localidad = ?, direccion = ?, descripcion = ?,"
				+ "plazastotal = ?, plazasdisponibles = ?, precio = ?, descuento = ? where codoferta = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(sql, new Object[] { oferta.getCodUsuario(), oferta.getTipo(), oferta.getCategoria(), oferta.getTitulo(), oferta.getFechaInicio(), oferta.getFechaFin(), oferta.getLocalidad(),
				oferta.getDireccion(), oferta.getDescripcion(), oferta.getPlazasTotal(), oferta.getPlazasDisponibles(), oferta.getPrecio(), oferta.getDescuento(), oferta.getCodOferta() });
	}
	
	@Override
	public void deleteData(String id) {
		
		String sql = "delete from oferta where codoferta =" + id;
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql);

	}

	/**
	 * Obtiene un listado de todas las ofertas existentes en la BD.
	 * @return listado de ofertas obtenido de la BD.
	 */
	public List<Oferta> getOfertasList() {
		
		List ofertaList = new ArrayList();
		
		String sql = "select * from oferta";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		ofertaList = jdbcTemplate.query(sql, new OfertaRowMapper());
		return ofertaList;
	}
	
	
	/**
	 *  Obtiene un listado de ofertas por tipo de producto, en base a los criterios fecha y plazas disponibles.
	 *  @param tipo - tipo de oferta
	 *  @return lista de ofertas del tipo "tipo" y que cumplen los criterios: fecha_inicio<=fecha_actual<=fecha_fin AND plazas disponibles>0
	 */
	public List<Oferta> getOfertaByProducto(String tipo) {
		
		List<Oferta> ofertaList = new ArrayList<Oferta>();
		String sql = "select * from oferta where tipo =" + "'" + tipo + "'" + " AND " + "fechainicio <= CURRENT_DATE" + " AND " + "fechafin >= CURRENT_DATE" + " AND " + "plazasdisponibles > 0";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		ofertaList = jdbcTemplate.query(sql, new OfertaRowMapper());
		return ofertaList;
	}
	
	/**
	 * Obtiene un listado de ofertas que cumplen los criteros.
	 * @param criterioBusqueda - todos los parametros agrupados en un objeto
	 * @return lista de ofertas que cumplen con los criterios anteriores.
	 */
	public List<Oferta> filterOferta(CriterioBusqueda criterioBusqueda) {
		
//		Herramienta para cambiar el formato de la fecha
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		
		List<Oferta> ofertaList = new ArrayList<>();
		String where = "";
		if (criterioBusqueda.getTipo()!="") {
			where = where + " tipo = " + "'" + criterioBusqueda.getTipo() + "'" + " AND ";
		}
		if (criterioBusqueda.getLocalidad()!="") {
			where = where + " localidad = " + "'" + criterioBusqueda.getLocalidad() + "'" + " AND ";
		}
		
		if (criterioBusqueda.getPrecio()!=0) {
			where = where + " precio <= " + criterioBusqueda.getPrecio() + " AND ";
		}
//		Introduzco las modificaciones que sugiri� Miguel Angel a la fecha df.format formatea la fecha a texto
		String mifecha= df.format(criterioBusqueda.getFecha());
		String sql = "select * from oferta where" + where + " fechainicio <= " + "'" + mifecha + "'" + " AND " +  "'" + mifecha + "'" + "<= fechafin" + " AND " + "plazasdisponibles >= " + criterioBusqueda.getPlazas();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		ofertaList = jdbcTemplate.query(sql, new OfertaRowMapper());
		return ofertaList;
	}

	/**
	 * Disminuye el campo plazasdisponibles del registro id  en un numero "number" de plazas de la tabla Oferta
	 * @param id - identificador de oferta
	 * @param number - numero de plazas a disminuir
	 */
	public void decreasePlazasDisponibles(int id, int number) {
		
		String sql = "UPDATE oferta set plazasdisponibles = plazasdisponibles - " + number + "where codoferta = " + id;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(sql);
	}
	
	/**
	 * Incrementa el campo plazasdisponibles del registro id en un numero "number" de plazas de la tabla Oferta.
	 * @param id - identificador de oferta
	 * @param number - numero de plazas a incrementar
	 */
	public void increasePlazasDisponibles(int id, int number) {
		
		String sql = "UPDATE oferta set plazasdisponibles = plazasdisponibles + " + number + "where codoferta = " + id;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(sql);	
	}
	
	
	public Oferta getOferta(String id) {
		
		List<Oferta> ofertaList = new ArrayList<Oferta>();
		String sql = "select * from oferta where codoferta= " + id;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		ofertaList = jdbcTemplate.query(sql, new OfertaRowMapper());
		return ofertaList.get(0);
	}
	
}
