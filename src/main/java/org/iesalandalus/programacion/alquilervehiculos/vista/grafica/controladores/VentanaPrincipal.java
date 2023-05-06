package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class VentanaPrincipal extends Controlador {

	@FXML
	void initialize() {
		System.out.println("Método initialize de VentanaPrincipal");
	}

	@FXML
	void leerCliente() {// utilizo clase controladores para leer fichero FXML
		LeerCliente leerCliente = (LeerCliente) Controladores.get("vistas/LeerCliente.fxml", "Leer Cliente",
				getEscenario());
		leerCliente.limpiar(); //método para limpiar campos
		leerCliente.getEscenario().showAndWait(); // muestre escenario y espere mientras de acpetar o cancelar
		try {
			Cliente cliente = leerCliente.getCliente();
			if (cliente != null) { // si es distinto de null, inserto.
				VistaGrafica.getInstancia().getControlador().insertar(cliente);
				Dialogos.mostrarDialogoAdvertencia("Insertar cliente", "Cliente insertado correctamente.",
						getEscenario());
			}
		} catch (OperationNotSupportedException | NullPointerException e) {
			Dialogos.mostrarDialogoAdvertencia("Insertar Cliente", e.getMessage(), getEscenario());
		}
	}

	@FXML
	void listarClientes(ActionEvent event) {
		ListarClientes listarClientes = (ListarClientes) Controladores.get("vistas/ListarClientes.fxml",
				"Listar Clientes", getEscenario());
		listarClientes.actualizar(VistaGrafica.getInstancia().getControlador().getClientes());
		listarClientes.getEscenario().showAndWait();

	}
}