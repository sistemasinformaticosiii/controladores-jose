package com.practica.si3.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.practica.si3.domain.CriterioBusqueda;
import com.practica.si3.domain.User;
import com.practica.si3.services.ReservationService;
import com.practica.si3.domain.Reservation;
import com.practica.si3.services.UserService;
import com.practica.si3.domain.Oferta;
import com.practica.si3.services.OfertaService;
import com.practica.si3.domain.Subscription;
import com.practica.si3.services.SubscriptionService;


@Controller
public class HomePageController {

	@Autowired
	UserService userService;
	@Autowired
	OfertaService ofertaService;
	@Autowired
	SubscriptionService subscriptionService;
	@Autowired
	ReservationService reservationService;

	@RequestMapping("/register")
	public ModelAndView registerUser(@ModelAttribute User user) {

		List<String> tipoList = new ArrayList<String>();
		tipoList.add("Entradas");
		tipoList.add("Restaurantes");
		tipoList.add("Actividades");

		List<String> perfilList = new ArrayList<String>();
		perfilList.add("admin");
		perfilList.add("cliente");
		perfilList.add("proveedor");

		Map<String, List> map = new HashMap<String, List>();
		map.put("tipoList", tipoList);
		map.put("perfilList", perfilList);
		return new ModelAndView("register", "map", map);
	}
	
	@RequestMapping("/registerSubscription")
	public ModelAndView registerUser(@ModelAttribute Subscription subscription) {

		List<String> tipoList = new ArrayList<String>();
		tipoList.add("Entradas");
		tipoList.add("Restaurantes");
		tipoList.add("Actividades");

		Map<String, List> map = new HashMap<String, List>();
		map.put("tipoList", tipoList);
		return new ModelAndView("registerSubscription", "map", map);
	}
	
	@RequestMapping("/registerOferta")
	public ModelAndView registerOferta(@ModelAttribute Oferta oferta) {

		List<String> tipoList = new ArrayList<String>();
		tipoList.add("Entradas");
		tipoList.add("Restaurantes");
		tipoList.add("Actividades");

		Map<String, List> map = new HashMap<String, List>();
		map.put("tipoList", tipoList);
		return new ModelAndView("registerOferta", "map", map);
	}
	
	@RequestMapping("/insert")
	public String inserData(@ModelAttribute User user) {
		
		if (!userService.existUser(user.getEmail()) && (user != null)) {
			userService.insertData(user);
			return "redirect:/getList";
		}
		else return "redirect:/errorUser";
	}
	
	@RequestMapping("/insertSubscription")
	public String inserData(@ModelAttribute Subscription subscription) {
		if (subscription != null)
			subscriptionService.insertData(subscription);
		return "redirect:/getListSubscription";
	}	
	
	@RequestMapping("/insertOferta")
	public String inserData(@ModelAttribute Oferta oferta) {
		if (oferta != null)
			ofertaService.insertData(oferta);
		return "redirect:/getListOffer";
	}
	
	@RequestMapping("/getList")
	public ModelAndView getUserLIst() {
		List<User> userList = userService.getUserList();
		return new ModelAndView("userList", "userList", userList);
	}
	
	@RequestMapping("/getListSubscription")
	public ModelAndView getSubscriptionLIst() {
		List<Subscription> subscriptionList = subscriptionService.getSubscriptionList();
		return new ModelAndView("subscriptionList", "subscriptionList", subscriptionList);
	}
	
	@RequestMapping("/getListOffer")
	public ModelAndView getOfertaList() {
		List<Oferta> ofertaList = ofertaService.getOfertaList();
		return new ModelAndView("ofertaList", "ofertaList", ofertaList);
	}
	
	@RequestMapping(value="/filtroOfertas", method = RequestMethod.GET)
	public ModelAndView filtroOfertas(@ModelAttribute CriterioBusqueda criterioBusqueda) {
		
		
		ModelAndView modeloYVista=new ModelAndView();
		List<Oferta> listaOfertas = new ArrayList<Oferta>();
		
		List<String> tipoList = new ArrayList<String>();
		tipoList.add("Entradas");
		tipoList.add("Restaurantes");
		tipoList.add("Actividades");
		

		Map<String, List> map = new HashMap<String, List>();
		map.put("tipoList", tipoList);
		map.put("listaOfertas", listaOfertas);
		return new ModelAndView("filtroOfertas", "map", map);	
		
	}
	
	/**
	 * Permitimos que nos asocie parametros de jsp a parametros del método
	 * para utilizar criterioBusqueda como un objeto
	 */
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
     
    }

	
	
	@RequestMapping(value="/filtroOfertas", method = RequestMethod.POST)
	public String mostrarOfertas(ModelMap model, @Valid CriterioBusqueda criterioBusqueda, BindingResult result) {
		
		//	Listado con los tipos de ofertas !!! pienso que no debería ir aquí
		List<String> tipoList = new ArrayList<String>();
		tipoList.add("Entradas");
		tipoList.add("Restaurantes");
		tipoList.add("Actividades");
		//	Añadimos el la lista anterior al modelo
		Map<String, List> map = new HashMap<String, List>();
		map.put("tipoList", tipoList);		
		model.addAttribute("map",map);
		
		if(result.hasErrors()) {
			//Salimos si hay un error en la validación
			return "filtroOfertas";
        }		
		//Creamos un listado con todas las ofertas que cumplen el criterio 
		List<Oferta> listaOfertas = new ArrayList<Oferta>();
		listaOfertas=this.ofertaService.filterOferta(criterioBusqueda);
		// Lo añadimos al modelo		
		model.addAttribute("listaOfertas",listaOfertas);
		return "filtroOfertas";
	}
	
	@RequestMapping("/errorUser")
	public ModelAndView errorUser() {
		
		return new ModelAndView("errorUser");
	}
	
	@RequestMapping("/getListOfertaProducto")
	public ModelAndView getFilterOferta(@RequestParam("tipo") String id) {
	    CriterioBusqueda criterioBusqueda = new CriterioBusqueda();
	    criterioBusqueda.setTipo(id);
		List<Oferta> ofertaList = ofertaService.filterOferta(criterioBusqueda);
		return new ModelAndView("ofertaList", "ofertaList", ofertaList);
	}
	
	@RequestMapping("/edit")
	public ModelAndView editUser(@RequestParam String id,
			@ModelAttribute User user) {

		user = userService.getUser(id);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);

		return new ModelAndView("edit", "map", map);
	}
	
	@RequestMapping("/editSubscription")
	public ModelAndView editSubscription(@RequestParam String id,
			@ModelAttribute Subscription subscription) {

		subscription = subscriptionService.getSubscription(id);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subscription", subscription);

		return new ModelAndView("editSubscription", "map", map);
	}
	
	@RequestMapping("/editOferta")
	public ModelAndView editOferta(@RequestParam String id,
			@ModelAttribute Oferta oferta) {

		oferta = ofertaService.getOferta(id);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("oferta", oferta);

		return new ModelAndView("editOferta", "map", map);
	}
	
	@RequestMapping("/reservaOferta")
	public ModelAndView reservaOferta(@ModelAttribute Reservation reservation) {
		
		return new ModelAndView("reservaOferta");
	}
	
	@RequestMapping("/update")
	public String updateUser(@ModelAttribute User user) {
		userService.updateData(user);
		return "redirect:/getList";
	}
	
	@RequestMapping("/updateSubscription")
	public String updateSubscription(@ModelAttribute Subscription subscription) {
		subscriptionService.updateData(subscription);
		return "redirect:/getListSubscription";
	}
	
	@RequestMapping("/updateOferta")
	public String updateOferta(@ModelAttribute Oferta oferta) {
		ofertaService.updateData(oferta);
		return "redirect:/getListOffer";
	}

	@RequestMapping("/delete")
	public String deleteUser(@RequestParam String id) {
		System.out.println("id = " + id);
		userService.deleteData(id);
		return "redirect:/getList";
	}
	
	@RequestMapping("/deleteSubscription")
	public String deleteSubscription(@RequestParam String id) {
		System.out.println("id = " + id);
		subscriptionService.deleteData(id);
		return "redirect:/getListSubscription";
	}
	
	@RequestMapping("/deleteOferta")
	public String deleteOferta(@RequestParam String id) {
		System.out.println("id = " + id);
		ofertaService.deleteData(id);
		return "redirect:/getListOffer";
	}

}