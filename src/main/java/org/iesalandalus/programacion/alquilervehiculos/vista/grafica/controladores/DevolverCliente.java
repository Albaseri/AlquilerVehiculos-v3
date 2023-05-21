package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class DevolverCliente extends Controlador {
	@FXML
	private TextField tfDni;

	@FXML
	private DatePicker dpFechaDev;

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

	public Cliente getDevolverCliente() {
		String dni = tfDni.getText();
		return cancelado ? null : VistaGrafica.getInstancia().getControlador().buscar((Cliente.getClienteConDni(dni)));
		// true : false si cancelado es false, devuelve el controlador.buscar....
	}

	public LocalDate getFechaDevolucion() {
		LocalDate fechaD = dpFechaDev.getValue();
		return cancelado ? null : fechaD;

	}

	public void limpiar() {
		tfDni.setText("");
		dpFechaDev.setValue(null);
		cancelado = true;
	}

}
