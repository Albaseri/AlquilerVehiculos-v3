package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {
	private static final String PATRON_FECHA = "dd/MM/yyyy";
	private static final String PATRON_MES = "MM/yyyy";
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern(PATRON_FECHA);

	private Consola() {
	}

	public static void mostrarCabecera(String mensaje) {
		StringBuilder cabecera = new StringBuilder(); // creo el objeto cabecera, lo inicializo con cadena vacía.
		cabecera.append("-");
		System.out.println(mensaje);
		for (int i = 0; i < mensaje.length(); i++) {
			System.out.print(cabecera);
		}
		System.out.println();
	}

	public static void mostrarMenuAcciones() {
		mostrarCabecera("PLATAFORMA PARA ALQUILERES DE COCHES.");
		for (Accion pruebaOp : Accion.values()) {
			System.out.println(pruebaOp.toString());
		}
	}

	public static Accion elegirAccion() {
		Accion ordinal = null;
		do {
			try {
				ordinal = Accion.get(leerEntero("Introduzca un número entero: "));
			} catch (Exception e) {
				System.out.println("El ordinal no es válido." + e.getMessage());
			}
		} while (ordinal == null && ordinal != Accion.SALIR);
		return ordinal;
	}

	private static String leerCadena(String mensaje) {
		System.out.print(mensaje);
		return Entrada.cadena();
	}

	private static Integer leerEntero(String mensaje) {
		System.out.print(mensaje);
		return Entrada.entero();
	}

	private static LocalDate leerFecha(String mensaje, String patron) {
		LocalDate fecha;
		String formato = leerCadena(mensaje);
		if (patron.equals(PATRON_MES)) {
			formato = "01/" + formato;
		}
		try {
			fecha = LocalDate.parse(formato, FORMATO_FECHA);
		} catch (Exception e) {
			fecha = null;
		}
		return fecha;
	}

	public static Cliente leerCliente() {
		return new Cliente(leerCadena("Introduzca su nombre: "), leerCadena("Introduzca su DNI: "),
				leerCadena("Introduzca su teléfono: "));
	}

	public static Cliente leerClienteDni() {
		return Cliente.getClienteConDni(leerCadena("Introduzca su dni: "));
	}

	public static String leerNombre() {
		return leerCadena("Introduzca su nombre: ");
	}

	public static String leerTelefono() {
		return leerCadena("Introduzca su número de teléfono: ");
	}

	public static Vehiculo leerVehiculo() {
		return Vehiculo.copiar(leerVehiculo(elegirTipoVehiculo()));
	}

	private static void mostrarMenuTipoVehiculos() {
		mostrarCabecera("Mostrando los distintos tipos de vehículos a elegir: ");
		for (TipoVehiculo pruebaOp : TipoVehiculo.values()) {
			System.out.printf("%s. %s", pruebaOp.ordinal(), pruebaOp.toString());
		}
	}

	private static TipoVehiculo elegirTipoVehiculo() {
		mostrarMenuTipoVehiculos();
		TipoVehiculo ordinal = null;
		do {
			try {
				System.out.println(); //añado esto para que aparezca en la línea de abajo de las 3 opciones de tipovehiculo. 
				ordinal = TipoVehiculo.get(leerEntero("Introduzca un número entero: "));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} while (ordinal == null);
		return ordinal;
	}

	private static Vehiculo leerVehiculo(TipoVehiculo tipoVehiculo) {
		//TipoVehiculo tVehiculo = elegirTipoVehiculo();
		String marca = leerCadena("Introduzca la marca: ");
		String modelo = leerCadena("Introduzca el modelo: ");
		String matricula = leerCadena("Introduzca la matrícula: ");
		Vehiculo vehiculoNuevo = null;
		switch (tipoVehiculo) {
		case TURISMO:
			vehiculoNuevo = new Turismo(marca, modelo, leerEntero("Introduzca la cilindrada: "), matricula);
			break;
		case AUTOBUS:
			vehiculoNuevo = new Autobus(marca, modelo, leerEntero("Introduzca el número de plazas: "), matricula);
			break;
		case FURGONETA:
			vehiculoNuevo = new Furgoneta(marca, modelo, leerEntero("Introduzca el Peso Máximo Permitido (PMA): "),
					leerEntero("Introduzca el número de plazas: "), matricula);
		}
		return vehiculoNuevo;
	}

	public static Vehiculo leerVehiculoMatricula() {
		String matricula = leerCadena("Introduzca su matrícula: ");
		return Vehiculo.getVehiculoConMatricula(matricula);
	}

	public static Alquiler leerAlquiler() {
		return new Alquiler(leerClienteDni(), leerVehiculoMatricula(),
				leerFecha("Introduzca la fecha de alquiler: ", PATRON_FECHA));
	}

	public static LocalDate leerFechaDevolucion() {
		return leerFecha("Introduzca la fecha de devolución: ", PATRON_FECHA);
	}

	public static LocalDate leerMes() {
		return leerFecha("Introduzca una fecha con el  siguiente formato: MM/yyyy: ", PATRON_MES);
	}
}