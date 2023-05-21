package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Clientes extends Controlador {

	@FXML
	void acercaDe(ActionEvent event) {
		AcercaDe acercaDe = (AcercaDe) Controladores.get("vistas/AcercaDe.fxml", "Información", getEscenario());
		acercaDe.getEscenario().showAndWait();
	}

	@FXML
	void cerrarVentana(ActionEvent event) {
		getEscenario().close();

	}

	@FXML
	void leerCliente(ActionEvent event) {

		LeerCliente leerCliente = (LeerCliente) Controladores.get("vistas/LeerCliente.fxml", "Leer Cliente",
				getEscenario());
		leerCliente.limpiar(); // método para limpiar campos
		leerCliente.getEscenario().showAndWait(); // muestre escenario y espere mientras de acpetar o cancelar
		try {
			Cliente cliente = leerCliente.getCliente();
			if (cliente != null) { // si es distinto de null, inserto.
				VistaGrafica.getInstancia().getControlador().insertar(cliente);
				Dialogos.mostrarDialogoAdvertencia("Insertar cliente", "Cliente insertado correctamente.",
						getEscenario());
			}
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Insertar Cliente", e.getMessage(), getEscenario());
		}
	}

	@FXML
	void listarClientes(ActionEvent event) {
		ListarClientes listarClientes = (ListarClientes) Controladores.get("vistas/ListarClientes.fxml",
				"Listar Clientes", getEscenario());
		listarClientes.actualizar(VistaGrafica.getInstancia().getControlador().getClientes());
		listarClientes.getEscenario().showAndWait();
	}
	  @FXML
	    void buscarCliente(ActionEvent event) {
		  BuscarCliente buscarCliente = (BuscarCliente) Controladores.get("vistas/BuscarCliente.fxml",
					"Buscar Cliente", getEscenario());
			buscarCliente.limpiar(); 
			buscarCliente.getEscenario().showAndWait(); 

	    }
	 
	@FXML
	void modificarCliente(ActionEvent event) {
		ModificarCliente modificarCliente = (ModificarCliente) Controladores.get("vistas/ModificarCliente.fxml",
				"Modificar Cliente", getEscenario());
		modificarCliente.limpiar(); // método para limpiar campos
		modificarCliente.getEscenario().showAndWait(); // muestre escenario y espere mientras de acpetar o cancelar
		try {
			Cliente cliente = modificarCliente.getDevolverCliente();
			if (cliente != null) { // si es distinto de null, modifico.
				VistaGrafica.getInstancia().getControlador().modificar(cliente,modificarCliente.nombreDev(),modificarCliente.telefonoDev());
				Dialogos.mostrarDialogoAdvertencia("Modificar cliente", "Cliente modificado correctamente.",
						getEscenario());
			}
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Modificar Cliente", e.getMessage(), getEscenario());
		}
	}

	@FXML
	void borrarCliente(ActionEvent event) {

		BorrarCliente borrarCliente = (BorrarCliente) Controladores.get("vistas/BorrarClientes.fxml", "Borrar cliente",
				getEscenario());
		borrarCliente.limpiar(); // método para limpiar campos

		borrarCliente.getEscenario().showAndWait();

		try {
			Cliente cliente = borrarCliente.getCliente();
			if (cliente != null) { // si es distinto de null, inserto.
				VistaGrafica.getInstancia().getControlador().borrar(cliente);
				Dialogos.mostrarDialogoAdvertencia("Borrar cliente", "Cliente borrado correctamente.", getEscenario());
			}
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Borrar Cliente", e.getMessage(), getEscenario());
		}

	}
	
	  @FXML
	    void devolverCliente(ActionEvent event) {
		  DevolverCliente devolverCliente = (DevolverCliente) Controladores.get("vistas/DevolverCliente.fxml", "Devolver cliente",
					getEscenario());
			devolverCliente.limpiar(); // método para limpiar campos

			devolverCliente.getEscenario().showAndWait();

			try {
				Cliente cliente = devolverCliente.getDevolverCliente();
				LocalDate fechaD = devolverCliente.getFechaDevolucion();
				if (cliente != null) {
					VistaGrafica.getInstancia().getControlador().devolver(cliente, fechaD);
					Dialogos.mostrarDialogoAdvertencia("Devolver cliente", "Alquiler de cliente devuelto correctamente.", getEscenario());
				}
			} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
				Dialogos.mostrarDialogoError("Devuelto Cliente", e.getMessage(), getEscenario());
			}
	    }
}