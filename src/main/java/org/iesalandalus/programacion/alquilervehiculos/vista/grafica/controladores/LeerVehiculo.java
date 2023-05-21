package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class LeerVehiculo extends Controlador {

	@FXML
	private RadioButton rbAutobus;

	@FXML
	private RadioButton rbFurgoneta;

	@FXML
	private RadioButton rbTurismo;

	@FXML
	private TextField tfCilindrada;

	@FXML
	private TextField tfMarca;

	@FXML
	private TextField tfMatricula;

	@FXML
	private TextField tfModelo;

	@FXML
	private TextField tfPlazas;

	@FXML
	private TextField tfPma;

	private boolean cancelado;

	private ToggleGroup grupo;

	@FXML

	public void initialize() {
		grupo = new ToggleGroup();

		rbAutobus.setToggleGroup(grupo);
		rbFurgoneta.setToggleGroup(grupo);
		rbTurismo.setToggleGroup(grupo);
		grupo.selectedToggleProperty().addListener((observable, oldValue, newValue) -> mostrarTipo());

		Controles.deshabilitarCamposTexto(tfCilindrada, tfPlazas, tfPma); // es como : tfCilindrada.setDisable(true);
	}

	private void mostrarTipo() {
		RadioButton seleccionado = (RadioButton) grupo.getSelectedToggle(); // casting: en lugar de darme un ToggleGroup, me da un Radiobutton
		if (seleccionado == rbTurismo) {
			tfCilindrada.setDisable(false);
			tfPlazas.setDisable(true);
			tfPma.setDisable(true);
		} else if (seleccionado == rbAutobus) {
			tfCilindrada.setDisable(true);
			tfPlazas.setDisable(false);
			tfPma.setDisable(true);

		} else {
			tfCilindrada.setDisable(true);
			tfPlazas.setDisable(false);
			tfPma.setDisable(false);

		}
	}

	public Vehiculo getVehiculo() {
		RadioButton seleccionado = (RadioButton) grupo.getSelectedToggle();
		String matricula = tfMatricula.getText();
		String marca = tfMarca.getText();
		String modelo = tfModelo.getText();

		Vehiculo vehiculo = null;

		if (seleccionado == rbTurismo) {
			String cilindrada = tfCilindrada.getText();
			vehiculo = new Turismo(marca, modelo, Integer.parseInt(cilindrada), matricula);
			
			
		} else if (seleccionado == rbAutobus) {
			String plazas = tfPlazas.getText();
			vehiculo = new Autobus(marca, modelo, Integer.parseInt(plazas), matricula);
		} else {
			String plazas = tfPlazas.getText();
			String pma = tfPma.getText();

			vehiculo = new Furgoneta(marca, modelo, Integer.parseInt(pma), Integer.parseInt(plazas), matricula);
		}
		return cancelado ? null : vehiculo;

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
	public void limpiar() {
		Controles.limpiarCamposTexto(tfCilindrada, tfMarca, tfMatricula, tfModelo, tfPlazas, tfPma);
		cancelado = true;
	}

}
