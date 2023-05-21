
package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Alquileres extends Controlador {

	@FXML
	void acercaDe(ActionEvent event) {
		AcercaDe acercaDe = (AcercaDe) Controladores.get("vistas/AcercaDe.fxml", "Información", getEscenario());
		acercaDe.getEscenario().showAndWait();
	}

	@FXML
	void borrarAlquiler(ActionEvent event) {
		BorrarAlquiler borrarAlquiler = (BorrarAlquiler) Controladores.get("vistas/BorrarAlquiler.fxml", " Borrar Alquiler",
				getEscenario());
		borrarAlquiler.limpiar(); // método para limpiar campos

		borrarAlquiler.getEscenario().showAndWait();

		try {
			Alquiler alquiler = borrarAlquiler.getAlquiler();
			if (alquiler != null) { // si es distinto de null, inserto.
				VistaGrafica.getInstancia().getControlador().borrar(alquiler);
				Dialogos.mostrarDialogoAdvertencia("Borrar alquiler", "Alquiler borrado correctamente.", getEscenario());
			}
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Borrar Cliente", e.getMessage(), getEscenario());
		}
	}

	@FXML
	void buscarAlquiler(ActionEvent event) {
		BuscarAlquiler buscarAlquiler = (BuscarAlquiler) Controladores.get("vistas/BuscarAlquiler.fxml",
				"Buscar Alquiler", getEscenario());
		buscarAlquiler.limpiar();
		buscarAlquiler.getEscenario().showAndWait();
	}

	@FXML
	void cerrarVentana(ActionEvent event) {
		getEscenario().close();
	}


	@FXML
	void leerAlquiler(ActionEvent event) {
		LeerAlquiler leerAlquiler = (LeerAlquiler) Controladores.get("vistas/LeerAlquiler.fxml", "Leer Alquiler",
				getEscenario());
		leerAlquiler.limpiar();
		leerAlquiler.getEscenario().showAndWait();
		try {
			Alquiler alquiler = leerAlquiler.getAlquiler();
			if (alquiler != null) {
				VistaGrafica.getInstancia().getControlador().insertar(alquiler);
				Dialogos.mostrarDialogoAdvertencia("Insertar Alquiler", "Alquiler insertado correctamente.",
						getEscenario());
			}
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Leer Alquiler", e.getMessage(), getEscenario());
		}
	}

	@FXML
	void listarAlquileres(ActionEvent event) {
		ListarAlquileres listarAlquileres = (ListarAlquileres) Controladores.get("vistas/ListarAlquileres.fxml",
				"Listar Alquileres", getEscenario());
		listarAlquileres.actualizar(VistaGrafica.getInstancia().getControlador().getAlquileres());
		listarAlquileres.getEscenario().showAndWait();
	}

	@FXML
	void mostrarEstadísticas(ActionEvent event) {

	}

}
