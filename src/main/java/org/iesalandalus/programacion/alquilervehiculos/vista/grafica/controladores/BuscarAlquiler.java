package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class BuscarAlquiler extends Controlador {

	@FXML
	private DatePicker dpFechaAlquiler;

	@FXML
	private TextField tfCilindradaV;

	@FXML
	private TextField tfDni;

	@FXML
	private TextField tfMarcaV;

	@FXML
	private TextField tfMatricula;

	@FXML
	private TextField tfModeloV;

	@FXML
	private TextField tfNombreCliente;

	@FXML
	private TextField tfPlazasV;

	@FXML
	private TextField tfPmaV;

	@FXML
	private TextField tfTelefonoCliente;

	@FXML
	private TextField tfprecioAlq;

	@FXML
	private boolean cancelado;

	@FXML
	void initialize() {
		Controles.deshabilitarCamposTexto(tfNombreCliente, tfTelefonoCliente, tfMarcaV, tfModeloV, tfCilindradaV,
				tfprecioAlq, tfPlazasV, tfPmaV);

	}

	@FXML
	void buscarAlq(ActionEvent event) {

		try {
			Cliente cliente = VistaGrafica.getInstancia().getControlador()
					.buscar(Cliente.getClienteConDni(tfDni.getText()));

			Vehiculo vehiculo = VistaGrafica.getInstancia().getControlador()
					.buscar(Vehiculo.getVehiculoConMatricula(tfMatricula.getText()));
			LocalDate fechaAlquiler = dpFechaAlquiler.getValue();

			Alquiler alquiler = VistaGrafica.getInstancia().getControlador()
					.buscar(new Alquiler(cliente, vehiculo, fechaAlquiler));
			tfNombreCliente.setText(alquiler.getCliente().getNombre());
			tfTelefonoCliente.setText(alquiler.getCliente().getTelefono());
			tfMarcaV.setText(alquiler.getVehiculo().getMarca());
			tfModeloV.setText(alquiler.getVehiculo().getModelo());

			vehiculo = alquiler.getVehiculo();

			if (vehiculo instanceof Turismo turismo) {
				tfCilindradaV.setText(String.valueOf(turismo.getCilindrada()));
				tfPlazasV.setText("");
				tfPmaV.setText("");

			} else if (vehiculo instanceof Autobus autobus) { // es lo mismo que Autobus autobus = (Autobus) vehiculo;
				tfPlazasV.setText(String.format("%s", autobus.getPlazas()));
				tfPmaV.setText("");
				tfCilindradaV.setText("");
			} else if (vehiculo instanceof Furgoneta furgoneta) {
				tfPmaV.setText(String.format("%s", furgoneta.getPma()));
				tfPlazasV.setText(String.format("%s", furgoneta.getPlazas()));
				tfCilindradaV.setText("");
			}

			if (alquiler.getFechaDevolucion() == null) {
				tfprecioAlq.setText("");
			} else {
				tfprecioAlq.setText((String.valueOf(alquiler.getPrecio())));
			}

		} catch (Exception e) {
		}
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
	void acercaDe(ActionEvent event) {
		AcercaDe acercaDe = (AcercaDe) Controladores.get("vistas/AcercaDe.fxml", "Información", getEscenario());
		acercaDe.getEscenario().showAndWait();
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

	@FXML
	void cerrarVentana(ActionEvent event) {
		getEscenario().close();
	}

	public void limpiar() {
		tfDni.setText("");
		tfMatricula.setText("");
		dpFechaAlquiler.setValue(null);
		cancelado = true;

	}

}
