package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LeerCliente extends Controlador{

    @FXML
    private TextField tfDni;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfTelefono;
    
    private boolean cancelado;
    
    public Cliente getCliente() {
		String dni = tfDni.getText();
		String nombre = tfNombre.getText();
		String telefono = tfTelefono.getText();
		return cancelado ? null : new Cliente(nombre, dni, telefono);
	}

    @FXML
    void aceptar(ActionEvent event) {
    	cancelado = false;
		getEscenario().close();
    }

    @FXML
    void acercaDe(ActionEvent event) {
    	AcercaDe acercaDe = (AcercaDe) Controladores.get("vistas/AcercaDe.fxml", "Información", getEscenario());
		acercaDe.getEscenario().showAndWait();
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
    public void limpiar() {
		tfNombre.setText("");
		tfDni.setText("");
		tfTelefono.setText("");
		cancelado = true;

	}
}
