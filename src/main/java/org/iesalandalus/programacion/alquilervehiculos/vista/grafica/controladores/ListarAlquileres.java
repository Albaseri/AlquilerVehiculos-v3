package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;
import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles.FormateadorCeldaFecha;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ListarAlquileres extends Controlador {

	@FXML
	private TableColumn<Alquiler, String> tcDni;

	@FXML
	private TableColumn<Alquiler, LocalDate> tcFechaA;

	@FXML
	private TableColumn<Alquiler, LocalDate> tcFechaD;

	@FXML
	private TableColumn<Alquiler, String> tcMatricula;
	@FXML
	private TableColumn<Alquiler, String> tcPrecio;

	@FXML
	private TableView<Alquiler> tvAlquileres;

	@FXML
	void initialize() {
		tcFechaA.setCellValueFactory(fila -> new SimpleObjectProperty<>(fila.getValue().getFechaAlquiler()));
		tcFechaA.setCellFactory(columna -> new FormateadorCeldaFecha());		
		tcFechaD.setCellValueFactory(fila -> new SimpleObjectProperty<>(fila.getValue().getFechaDevolucion()));
		tcFechaD.setCellFactory(columna -> new FormateadorCeldaFecha());		

		tcDni.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getCliente().getDni()));
		tcMatricula.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getVehiculo().getMatricula()));
		tcPrecio.setCellValueFactory(fila -> new SimpleStringProperty(formatearPrecio(fila.getValue())));
		
	}

	@FXML
	private String formatearPrecio(Alquiler alquiler) {
		String cadena = "";
		if (alquiler.getFechaDevolucion() != null) {
			cadena = String.format("%s", alquiler.getPrecio());
		}
		return cadena;

	}

	@FXML
	void salir(ActionEvent event) {
		getEscenario().close();

	}

	public void actualizar(List<Alquiler> alquileres) {
		tvAlquileres.setItems(FXCollections.observableArrayList(alquileres));

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
