package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class VentanaPrincipal extends Controlador {

	@FXML
	void acercaDe(ActionEvent event) {
		AcercaDe acercaDe = (AcercaDe) Controladores.get("vistas/AcercaDe.fxml", "Información", getEscenario());
		acercaDe.getEscenario().showAndWait();
	}

	@FXML
	void cerrarVentana(ActionEvent event) {
		getEscenario().close();

	}

	@FXML
	void clientes(ActionEvent event) {
		Clientes clientes = (Clientes) Controladores.get("vistas/Clientes.fxml", "Opciones en Cliente", getEscenario());
		clientes.getEscenario().showAndWait();
	}

	@FXML
	void vehiculos(ActionEvent event) {
		Vehiculos vehiculos = (Vehiculos) Controladores.get("vistas/Vehiculos.fxml", "Opciones en Vehículos",
				getEscenario());
		vehiculos.getEscenario().showAndWait();
	}

	@FXML
	void alquiler(ActionEvent event) {
		Alquileres alquileres = (Alquileres) Controladores.get("vistas/Alquileres.fxml", "Opciones en Alquileres",
				getEscenario());
		alquileres.getEscenario().showAndWait();
	}

}
