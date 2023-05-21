package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListarClientes extends Controlador {

	
	//mapeamos
	
	@FXML
	private TableColumn<Cliente, String> tcDni;  //cuando hagamos turismo hacemos un stringproperty y el get cilindrada lo paso a string (integer.toString

	@FXML
	private TableColumn<Cliente, String> tcNombre;

	@FXML
	private TableColumn<Cliente, String> tcTelefono;

	@FXML
	private TableView<Cliente> tvClientes;

	@FXML
	void salir(ActionEvent event) {
		getEscenario().close();

	}

	@FXML
	void initialize() {  //a la hora de ponerme los valores accedemos así a las tablas. Si no, de la forma de la segunda línea. 
		tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		tcDni.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getDni()));
		tcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

	}
	
	@FXML

	public void actualizar(List<Cliente> cliente) {
		tvClientes.setItems(FXCollections.observableArrayList(cliente));
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
