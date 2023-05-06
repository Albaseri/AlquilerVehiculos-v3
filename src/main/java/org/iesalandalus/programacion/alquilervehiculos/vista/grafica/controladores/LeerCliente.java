package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LeerCliente extends Controlador {

    @FXML private Label tfDni;

    @FXML  private Label tfNombre;

    @FXML private Label tfTelefono;
    
    private boolean cancelado;
    
    public Cliente getCliente() { //permite leer los datos, si se ha pulsado aceptar, cancelar. Puede lanzar illegalArgu o NullPointer si hay datos rellenos vacíos. 
    	String nombre = tfNombre.getText();
    	String dni = tfDni.getText();
    	String telefono = tfTelefono.getText();
    	
    	return cancelado ? null : new Cliente(nombre, dni, telefono);  //si cancelado es true devuelve null. Si no, nuevo cliente
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
    
    public void limpiar() {
    	tfNombre.setText("");
    	tfDni.setText("");
    	tfTelefono.setText("");
    	cancelado = true;
    }
}
