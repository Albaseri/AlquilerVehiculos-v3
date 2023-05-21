
package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class BuscarVehiculo extends Controlador {

	@FXML
	private RadioButton rbAutobus;

	@FXML
	private RadioButton rbFurgoneta;

	@FXML
	private RadioButton rbTurismo;

	@FXML
	private TextField tfMarca;

	@FXML
	private TextField tfMatricula;

	@FXML
	private TextField tfModelo;

	private boolean cancelado;

	@FXML
	void initialize() {
		Controles.deshabilitarCamposTexto(tfMarca, tfModelo);
	}

	@FXML
	void buscarV(ActionEvent event) {
		String matricula = tfMatricula.getText();
		Vehiculo vehiculo = VistaGrafica.getInstancia().getControlador().buscar(Vehiculo.getVehiculoConMatricula(matricula));
		tfMarca.setText(vehiculo.getMarca());
		tfModelo.setText(vehiculo.getModelo());

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

	public void limpiar() {
		Controles.limpiarCamposTexto(tfMarca, tfMatricula, tfModelo);
	}

}
