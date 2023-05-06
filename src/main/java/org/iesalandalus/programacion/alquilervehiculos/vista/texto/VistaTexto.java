package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class VistaTexto extends Vista {

	public VistaTexto() {
		super();
		Accion.setVista(this);
	}

	@Override
	public void comenzar() {
		Accion eligeUnaOpcion;
		do {
			Consola.mostrarMenuAcciones();
			eligeUnaOpcion = Consola.elegirAccion();
			eligeUnaOpcion.ejecutar();
		} while (eligeUnaOpcion != Accion.SALIR);
	}

	@Override
	public void terminar() {
		getControlador().terminar();
	}

	public void insertarCliente() {
		try {
			getControlador().insertar(Consola.leerCliente());
			System.out.println("Ha insertado un cliente correctamente. ");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertarVehiculo() {
		try {
			getControlador().insertar(Consola.leerVehiculo());
			System.out.println("Ha insertado un vehículo correctamente. ");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertarAlquiler() {
		try {
			getControlador().insertar(Consola.leerAlquiler());
			System.out.println("Ha insertado un alquiler correctamente. ");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarCliente() {
		try {
			System.out.println(getControlador().buscar(Consola.leerClienteDni()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarVehiculo() {
		try {
			System.out.println(getControlador().buscar(Consola.leerVehiculoMatricula()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarAlquiler() {
		System.out.println(getControlador().buscar(Consola.leerAlquiler()));
	}

	public void modificarCliente() {
		try {
			getControlador().modificar(Consola.leerClienteDni(), Consola.leerNombre(), Consola.leerTelefono());
			System.out.println("Se ha modificado el cliente. ");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void devolverAlquilerCliente() {
		try {
			getControlador().devolver(Consola.leerClienteDni(), Consola.leerFechaDevolucion());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void devolverAlquilerVehiculo() {
		try {
			getControlador().devolver(Consola.leerVehiculoMatricula(), Consola.leerFechaDevolucion());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarCliente() {
		try {
			getControlador().borrar(Consola.leerCliente());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarVehiculo() {
		try {
			getControlador().borrar(Consola.leerVehiculo());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarAlquiler() {
		try {
			getControlador().borrar(Consola.leerAlquiler());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarClientes() {
		try {
			List<Cliente> clientesOrdenados = getControlador().getClientes();
			clientesOrdenados.sort(Comparator.comparing(Cliente::getNombre).thenComparing(Cliente::getDni));
			for (Cliente cliente : clientesOrdenados) {
				System.out.println(cliente.toString());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarVehiculos() {
		try {
			List<Vehiculo> vehiculosOrdenados = getControlador().getVehiculos();
			vehiculosOrdenados.sort(Comparator.comparing(Vehiculo::getMarca).thenComparing(Vehiculo::getModelo)
					.thenComparing(Vehiculo::getMatricula));
			for (Vehiculo vehiculo : getControlador().getVehiculos()) {
				System.out.println(vehiculo.toString());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarAlquileres() {
		try {
			Comparator<Cliente> clienteOrdenado = Comparator.comparing(Cliente::getNombre)
					.thenComparing(Cliente::getDni);
			List<Alquiler> alquileresOrdenados = getControlador().getAlquileres();
			alquileresOrdenados.sort(Comparator.comparing(Alquiler::getFechaAlquiler)
					.thenComparing(Alquiler::getCliente, clienteOrdenado));
			for (Alquiler alquiler : getControlador().getAlquileres()) {
				System.out.println(alquiler.toString());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarAlquileresCliente() {
		try {
			getControlador().getAlquileres(Consola.leerCliente());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarAlquileresVehiculo() {
		try {
			getControlador().getAlquileres(Consola.leerVehiculo());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void mostrarEstadisticasMensualesTipoVehiculo() {
		LocalDate mesI = Consola.leerMes();
		Map<TipoVehiculo, Integer> mapa = inicializarEstadisticas();
		for (Alquiler alquiler : getControlador().getAlquileres()) {
			if (alquiler.getFechaAlquiler().getMonthValue() == mesI.getMonthValue()
					&& alquiler.getFechaAlquiler().getYear() == mesI.getYear()) {
				mapa.put(TipoVehiculo.get(alquiler.getVehiculo()),
						mapa.getOrDefault(TipoVehiculo.get(alquiler.getVehiculo()), 0) + 1);
			}
		}
	}

	private Map<TipoVehiculo, Integer> inicializarEstadisticas() {
		Map<TipoVehiculo, Integer> inicializo = new TreeMap<>();
		for (TipoVehiculo tipoVehiculo : TipoVehiculo.values()) {
			inicializo.put(tipoVehiculo, 0);
		}
		return inicializo;
	}
}