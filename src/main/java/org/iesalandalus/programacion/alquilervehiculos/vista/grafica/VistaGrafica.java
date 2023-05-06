package org.iesalandalus.programacion.alquilervehiculos.vista.grafica;

import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class VistaGrafica extends Vista {

	private static VistaGrafica instancia;

	public void comenzar() {
		LanzadorVentanaPrincipal.comenzar();
		getControlador().terminar();
	}

	public void terminar() {
		System.out.println("Hasta otra.");
	}

	 public static VistaGrafica getInstancia() {
		if (instancia == null) {
			instancia = new VistaGrafica();
		}
		return instancia;
	}

}
