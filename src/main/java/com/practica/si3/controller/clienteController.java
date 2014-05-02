package com.practica.si3.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class clienteController {

	@Autowired
	UserService userService;
	@Autowired
	OfertaService ofertaService;
	@Autowired
	SubscriptionService subscriptionService;
	@Autowired
	ReservationService reservationService;

	@RequestMapping("index.htm")
    public ModelAndView main() {
        ModelAndView mov = new ModelAndView();
        mov.setViewName("main");
        return mov;
    }
}