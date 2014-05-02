<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Practica | Spring DI Hello World</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">

	<script>
	 $(function() {
		    
		 //Array para dar formato en español
		  $.datepicker.regional['es'] = 
		  {
		  closeText: 'Cerrar', 
		  prevText: 'Previo', 
		  nextText: 'Próximo',
		  
		  monthNames: ['Enero','Febrero','Marzo','Abril','Mayo','Junio',
		  'Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre'],
		  monthNamesShort: ['Ene','Feb','Mar','Abr','May','Jun',
		  'Jul','Ago','Sep','Oct','Nov','Dic'],
		  monthStatus: 'Ver otro mes', yearStatus: 'Ver otro año',
		  dayNames: ['Domingo','Lunes','Martes','Miércoles','Jueves','Viernes','Sábado'],
		  dayNamesShort: ['Dom','Lun','Mar','Mie','Jue','Vie','Sáb'],
		  dayNamesMin: ['Do','Lu','Ma','Mi','Ju','Vi','Sa'],
		  dateFormat: 'dd/mm/yy', firstDay: 0, 
		  initStatus: 'Selecciona la fecha', isRTL: false};
		 $.datepicker.setDefaults($.datepicker.regional['es']);
		 
		 //miDate: fecha de comienzo D=días | M=mes | Y=año
		 //maxDate: fecha tope D=días | M=mes | Y=año
		    $( "#fecha" ).datepicker({ minDate: "-0D", maxDate: "+20M +10D" });
		  });
</script>
<style>
.error {
	color: #ff0000;
}
 
.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
body {
	font-size: 20px;
	color: teal;
	font-family: Calibri;
}

td {
	font-size: 15px;
	color: black;
	width: 100px;
	height: 22px;
	text-align: left;
}

.heading {
	font-size: 18px;
	color: white;
	font: bold;
	background-color: orange;
	border: thick;
}
</style>
</head>
<body>
	<center>
		<br /> <br /> <br /> <b>Practica | SISTEMAS INFORMATICOS III </b> <br />
		<br />
		<div>
			<form:form method="post" action="/si3/cliente/reservaOferta" modelAttribute="reservation">
				<table>
					
					<tr>
                		<td><label for="plazasrReservadas">Plazas</label></td>
                		<td><form:input path="plazasReservadas" /></td>
                		<td><form:errors path="plazasReservadas" cssClass="error" /></td>
            		</tr>
					
					<tr>
					
						<td><label for="fechaReserva">Fecha :</label></td>
						
	    				<td><form:input path="fechaReserva" /></td>
	    				<td><form:errors path="fechaReserva" cssClass="error" /></td>
	    				
	    			</tr>
					<tr>
						<td>&nbsp;</td>
						<td><input type="submit" value="Guardar" /></td>
					</tr>
					
				</table>
			</form:form>
		</div>
	</center>
</body>
</html>