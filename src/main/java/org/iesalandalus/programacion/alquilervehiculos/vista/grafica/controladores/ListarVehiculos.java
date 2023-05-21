package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ListarVehiculos extends Controlador {

	@FXML
	private TableColumn<Vehiculo, String> tcCilindrada;

	@FXML
	private TableColumn<Vehiculo, String> tcMarca;

	@FXML
	private TableColumn<Vehiculo, String> tcMatricula;

	@FXML
	private TableColumn<Vehiculo, String> tcModelo;

	@FXML
	private TableColumn<Vehiculo, String> tcPlazas;

	@FXML
	private TableColumn<Vehiculo, String> tcPma;

	@FXML
	private TableView<Vehiculo> tvVehiculos;

	@FXML
	void salir(ActionEvent event) {
		getEscenario().close();

	}

	@FXML
	void initialize() {
		tcMarca.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getMarca()));
		tcModelo.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getModelo()));
		tcMatricula.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getMatricula()));
		tcCilindrada.setCellValueFactory(fila -> new SimpleStringProperty(formatearCilindrada(fila.getValue())));
		tcPlazas.setCellValueFactory(fila -> new SimpleStringProperty(formatearPlazas(fila.getValue())));
		tcPma.setCellValueFactory(fila -> new SimpleStringProperty(formatearPma(fila.getValue())));

	}

	@FXML
	public void actualizar(List<Vehiculo> vehiculos) {
		tvVehiculos.setItems(FXCollections.observableArrayList(vehiculos));
	}

	@FXML
	private String formatearCilindrada(Vehiculo vehiculo) {
		return vehiculo instanceof Turismo turismo ? String.format("%s", turismo.getCilindrada()) : "";
		// al vehiculo le hago una instancia de turismo
		// si es falso devuelve ""
	}

	@FXML
	private String formatearPlazas(Vehiculo vehiculo) {
		String plazas = "";
		if (vehiculo instanceof Autobus autobus) {
			plazas = String.format("%s", autobus.getPlazas());
		} else if (vehiculo instanceof Furgoneta furgoneta) {
			plazas = String.format("%s", furgoneta.getPlazas());
		}
		return plazas;
	}

	@FXML
	private String formatearPma(Vehiculo vehiculo) {
		return vehiculo instanceof Furgoneta furgoneta ? String.format("%s", furgoneta.getPma()) : "";
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
}
