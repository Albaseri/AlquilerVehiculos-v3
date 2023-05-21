package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class LeerAlquiler extends Controlador {
	private boolean cancelado;

	@FXML
	private DatePicker dpFechaAlquiler;

	@FXML
	private TextField tfDniC;

	@FXML
	private TextField tfMatriculaV;

	@FXML
	public Alquiler getAlquiler() {
		Cliente cliente = VistaGrafica.getInstancia().getControlador()
				.buscar(Cliente.getClienteConDni(tfDniC.getText()));
		Vehiculo vehiculo = VistaGrafica.getInstancia().getControlador()
				.buscar(Vehiculo.getVehiculoConMatricula(tfMatriculaV.getText()));
		LocalDate fechaAlquiler = dpFechaAlquiler.getValue();
		return cancelado ? null : new Alquiler(cliente, vehiculo, fechaAlquiler);
	}

	@FXML
	void acercaDe(ActionEvent event) {
		AcercaDe acercaDe = (AcercaDe) Controladores.get("vistas/AcercaDe.fxml", "Información", getEscenario());
		acercaDe.getEscenario().showAndWait();
	}

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
	void cerrarVentana(ActionEvent event) {
		getEscenario().close();
	}

	@FXML
	void limpiar() {
		tfDniC.setText("");
		tfMatriculaV.setText("");
		dpFechaAlquiler.setValue(null);
		cancelado = true;
	}

}
