package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class BuscarCliente extends Controlador {
	private boolean cancelado;

	@FXML
	private TextField tfDni;

	@FXML
	private TextField tfNombre;

	@FXML
	private TextField tfTelefono;

	@FXML
	void initialize() {
		tfNombre.setDisable(true);
		tfTelefono.setDisable(true);
	}

	@FXML
	void buscar(ActionEvent event) {
		String dni = tfDni.getText();
		Cliente cliente = VistaGrafica.getInstancia().getControlador().buscar(Cliente.getClienteConDni((dni)));
		if (cliente != null) {
			// Mostrar en los campos de texto
			tfNombre.setText(cliente.getNombre());
			tfTelefono.setText(cliente.getTelefono());
		} else {
			// Si no lo encuentra, se limpian los campos
			tfNombre.setText("");
			tfTelefono.setText("");
		}
	}

	public Cliente getCliente() {

		return cancelado ? null
				: VistaGrafica.getInstancia().getControlador().buscar(Cliente.getClienteConDni((tfDni.getText())));
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
		tfDni.setText("");
		cancelado = true;

	}

}
