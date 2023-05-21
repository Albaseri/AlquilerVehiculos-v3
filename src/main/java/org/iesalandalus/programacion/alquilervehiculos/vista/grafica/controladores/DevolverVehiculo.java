package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class DevolverVehiculo extends Controlador {

	@FXML
	private DatePicker dpFechaDev;

	@FXML
	private TextField tfMatricula;

	private boolean cancelado;

	@FXML
	void aceptar(ActionEvent event) {
		cancelado = false;
		getEscenario().close();
	}

	@FXML
	void cancelar(ActionEvent event) {
		cancelado = true;
		getEscenario().close();
	}

	@FXML
	void acercaDe(ActionEvent event) {
		AcercaDe acercaDe = (AcercaDe) Controladores.get("vistas/AcercaDe.fxml", "Información", getEscenario());
		acercaDe.getEscenario().showAndWait();
	}

	@FXML
	void cerrarVentana(ActionEvent event) {
		getEscenario().close();

	}

	public Vehiculo getDevolverVehiculo() {
		String matricula = tfMatricula.getText();
		return cancelado ? null
				: VistaGrafica.getInstancia().getControlador().buscar((Vehiculo.getVehiculoConMatricula(matricula)));
		// true : false si cancelado es false, devuelve el controlador.buscar....
	}

	public LocalDate getFechaDevolucion() {
		LocalDate fechaD = dpFechaDev.getValue();
		return cancelado ? null : fechaD;

	}

	public void limpiar() {
		tfMatricula.setText("");
		dpFechaDev.setValue(null);
		cancelado = true;
	}

}
