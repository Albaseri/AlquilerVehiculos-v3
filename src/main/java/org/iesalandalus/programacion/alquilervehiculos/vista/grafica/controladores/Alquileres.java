package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Alquileres extends Controlador{

	@FXML
	void acercaDe(ActionEvent event) {
		AcercaDe acercaDe = (AcercaDe) Controladores.get("vistas/AcercaDe.fxml", "Información", getEscenario());
		acercaDe.getEscenario().showAndWait();
	}

	@FXML
	void cerrarSesion(ActionEvent event) {
		getEscenario().close();

	}
    
    @FXML
    void listarAlquileres(ActionEvent event) {
		ListarAlquileres listarAlquileres = (ListarAlquileres) Controladores.get("vistas/ListarAlquileres.fxml",
				"Listar Alquileres", getEscenario());
		listarAlquileres.actualizar(VistaGrafica.getInstancia().getControlador().getAlquileres());
		listarAlquileres.getEscenario().showAndWait();
    }

    @FXML
    void borrarAlquiler(ActionEvent event) {

    }

    @FXML
    void buscarAlquiler(ActionEvent event) {

    }

 
    @FXML
    void devolverAlquiler(ActionEvent event) {

    }

    @FXML
    void leerAlquileres(ActionEvent event) {

    }

 

    @FXML
    void mostrarEstadísticas(ActionEvent event) {

    }

}
