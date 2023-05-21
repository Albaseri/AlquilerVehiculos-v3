package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Vehiculos extends Controlador {

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
	void borrarVehiculo(ActionEvent event) {
		BorrarVehiculo borrarVehiculo = (BorrarVehiculo) Controladores.get("vistas/BorrarVehiculo.fxml",
				"Borrar vehiculo", getEscenario());
		borrarVehiculo.limpiar(); // método para limpiar campos

		borrarVehiculo.getEscenario().showAndWait();
		try {
			Vehiculo vehiculo = borrarVehiculo.getVehiculo();
			if (vehiculo != null) { // si es distinto de null, inserto.
				VistaGrafica.getInstancia().getControlador().borrar(vehiculo);
				Dialogos.mostrarDialogoAdvertencia("Borrar vehiculo", "Vehículo borrado correctamente.",
						getEscenario());
			}
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Borrar Vehículo", e.getMessage(), getEscenario());
		}

	}

	@FXML
	void buscarVehiculo(ActionEvent event) {
		BuscarVehiculo buscarVehiculo = (BuscarVehiculo) Controladores.get("vistas/BuscarVehiculo.fxml",
				"Buscar Cliente", getEscenario());
		buscarVehiculo.limpiar();
		buscarVehiculo.getEscenario().showAndWait();
	}

	@FXML
	void devolverVehiculo(ActionEvent event) {
		DevolverVehiculo devolverVehiculo = (DevolverVehiculo) Controladores.get("vistas/DevolverVehiculo.fxml",
				"Devolver vehículo", getEscenario());
		devolverVehiculo.limpiar(); // método para limpiar campos

		devolverVehiculo.getEscenario().showAndWait();
		try {
			Vehiculo vehiculo = devolverVehiculo.getDevolverVehiculo();
			LocalDate fechaD = devolverVehiculo.getFechaDevolucion();
			if (vehiculo != null) {
				VistaGrafica.getInstancia().getControlador().devolver(vehiculo, fechaD);
				Dialogos.mostrarDialogoAdvertencia("Devolver vehículo", "Alquiler de vehículo devuelto correctamente.",
						getEscenario());
			}
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Devuelto Vehículo", e.getMessage(), getEscenario());
		}
	}

	@FXML
	void leerVehiculo(ActionEvent event) {
		LeerVehiculo leerVehiculo = (LeerVehiculo) Controladores.get("vistas/LeerVehiculo.fxml", "Leer Vehículo",
				getEscenario());
		leerVehiculo.limpiar(); // método para limpiar campos
		leerVehiculo.getEscenario().showAndWait(); // muestre escenario y espere mientras de acpetar o cancelar
		try {
			Vehiculo vehiculo = leerVehiculo.getVehiculo();
			if (vehiculo != null) { // si es distinto de null, inserto.
				VistaGrafica.getInstancia().getControlador().insertar(vehiculo);
				Dialogos.mostrarDialogoAdvertencia("Insertar Vehículo", "Vehículo insertado correctamente.",
						getEscenario());
			}
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Leer Vehículo", e.getMessage(), getEscenario());
		}
	}

	@FXML
	void listarVehiculo(ActionEvent event) {
		ListarVehiculos listarVehiculos = (ListarVehiculos) Controladores.get("vistas/ListarVehiculos.fxml",
				"Listar Vehículos", getEscenario());
		listarVehiculos.actualizar(VistaGrafica.getInstancia().getControlador().getVehiculos());
		listarVehiculos.getEscenario().showAndWait();
	}

}
