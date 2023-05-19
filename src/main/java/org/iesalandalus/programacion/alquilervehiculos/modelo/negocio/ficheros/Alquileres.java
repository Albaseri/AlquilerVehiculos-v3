package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Alquileres implements IAlquileres {

	private static final File FICHERO_ALQUILERES = new File(
			String.format("%s%s%s", "datos", File.separator, "alquileres.xml"));;
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final String RAIZ = "alquileres";
	private static final String ALQUILER = "alquiler";
	private static final String CLIENTE = "cliente";
	private static final String VEHICULO = "vehiculo";
	private static final String FECHA_ALQUILER = "fechaAlquiler";
	private static final String FECHA_DEVOLUCION = "fechaDevolucion";

	private List<Alquiler> coleccionAlquileres;

	private static Alquileres instancia;

	private Alquileres() {
		coleccionAlquileres = new ArrayList<>();
	}

	static Alquileres getInstancia() {
		if (instancia == null) {
			instancia = new Alquileres();
		}
		return instancia;
	}

	@Override
	public void comenzar() {
		Document documento = UtilidadesXml.leerXmlDeFichero(FICHERO_ALQUILERES);
		if (documento != null) {
			System.out.println("Fichero XML leído correctamente.");
			leerDom(documento);
		} else {
			System.out.printf("No se puede leer el fichero de entrada: %s.%n", FICHERO_ALQUILERES);
		}
	}

	private void leerDom(Document documentoXml) {
		NodeList alquileres = documentoXml.getElementsByTagName(ALQUILER);
		for (int i = 0; i < alquileres.getLength(); i++) {
			Node nAlquiler = alquileres.item(i);
			if (nAlquiler.getNodeType() == Node.ELEMENT_NODE) {
				try {
					insertar(getAlquiler((Element) nAlquiler));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	private Alquiler getAlquiler(Element elemento) throws OperationNotSupportedException {
		String cliente = elemento.getAttribute(CLIENTE);
		Cliente buscarCliente = Clientes.getInstancia().buscar(Cliente.getClienteConDni(cliente));
		String fechaAlquiler = elemento.getAttribute(FECHA_ALQUILER);
		LocalDate localDate = LocalDate.parse(fechaAlquiler, FORMATO_FECHA);
		String vehiculo = elemento.getAttribute(VEHICULO);
		Vehiculo buscarVehiculo = Vehiculos.getInstancia().buscar(Vehiculo.getVehiculoConMatricula(vehiculo));

		if (buscarCliente == null) {
			throw new NullPointerException("No se puede buscar un cliente nulo. ");
		}
		if (buscarVehiculo == null) {
			throw new NullPointerException("No se puede buscar un vehículo nulo. ");
		}
		Alquiler alquiler = new Alquiler(buscarCliente, buscarVehiculo, localDate);

		if (elemento.hasAttribute(FECHA_DEVOLUCION)) {
			// String fechaDevolucion = elemento.getAttribute(FECHA_DEVOLUCION);
			LocalDate localD = LocalDate.parse(elemento.getAttribute(FECHA_DEVOLUCION), FORMATO_FECHA);
			alquiler.devolver(localD);
		}
		return alquiler;
	}

	@Override
	public void terminar() {
		UtilidadesXml.escribirXmlAFichero(crearDom(), FICHERO_ALQUILERES);
	}

	private Document crearDom() {
		DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
		Document documentoXml = null;
		if (constructor != null) {
			documentoXml = constructor.newDocument();
			documentoXml.appendChild(documentoXml.createElement(RAIZ));
			for (Alquiler alquiler : coleccionAlquileres) {
				Element elementoAlquiler = getElemento(documentoXml, alquiler);
				documentoXml.getDocumentElement().appendChild(elementoAlquiler);
			}
		}
		return documentoXml;

	}

	private Element getElemento(Document documentoXml, Alquiler alquiler) {
		Element elementoAlquiler = documentoXml.createElement(ALQUILER);
		elementoAlquiler.setAttribute(CLIENTE, alquiler.getCliente().getDni());
		elementoAlquiler.setAttribute(FECHA_ALQUILER,
				String.format("%s", alquiler.getFechaAlquiler().format(FORMATO_FECHA)));
		LocalDate fechaDevolucion = alquiler.getFechaDevolucion();
		if (fechaDevolucion != null) {
			elementoAlquiler.setAttribute(FECHA_DEVOLUCION, String.format("%s", fechaDevolucion.format(FORMATO_FECHA)));
		}
		elementoAlquiler.setAttribute(VEHICULO, alquiler.getVehiculo().getMatricula());
		return elementoAlquiler;
	}

	@Override
	public List<Alquiler> get() {
		return new ArrayList<>(coleccionAlquileres);
	}

	@Override
	public List<Alquiler> get(Cliente cliente) {
		List<Alquiler> listaAlquileres = new ArrayList<>();
		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getCliente().equals(cliente)) {
				listaAlquileres.add(alquiler);
			}
		}
		return listaAlquileres;
	}

	@Override
	public List<Alquiler> get(Vehiculo vehiculo) {
		List<Alquiler> listaAlquileres = new ArrayList<>();
		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getVehiculo().equals(vehiculo)) {
				listaAlquileres.add(alquiler);
			}
		}
		return listaAlquileres;
	}

	private void comprobarAlquiler(Cliente cliente, Vehiculo vehiculo, LocalDate fechaAlquiler)
			throws OperationNotSupportedException {
		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getFechaDevolucion() == null) {

				if (alquiler.getCliente().equals(cliente)) {
					throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
				}
				if (alquiler.getVehiculo().equals(vehiculo)) {
					throw new OperationNotSupportedException("ERROR: El vehículo está actualmente alquilado.");
				}
			} else {
				if (alquiler.getCliente().equals(cliente) && (alquiler.getFechaDevolucion().isAfter(fechaAlquiler)
						|| alquiler.getFechaDevolucion().isEqual(fechaAlquiler))) {
					throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");
				}
				if (alquiler.getVehiculo().equals(vehiculo) && (alquiler.getFechaDevolucion().isAfter(fechaAlquiler)
						|| alquiler.getFechaDevolucion().isEqual(fechaAlquiler))) {
					throw new OperationNotSupportedException("ERROR: El vehículo tiene un alquiler posterior.");
				}
			}
		}
	}

	@Override
	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo.");
		}
		comprobarAlquiler(alquiler.getCliente(), alquiler.getVehiculo(), alquiler.getFechaAlquiler());
		coleccionAlquileres.add(alquiler);
	}

	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un cliente nulo.");
		}
		Alquiler alquiler = getAlquilerAbierto(cliente);
		if (alquiler == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese cliente.");
		} else {
			alquiler.devolver(fechaDevolucion); // corregido
		}
	}

	private Alquiler getAlquilerAbierto(Cliente cliente) {
		Alquiler alquilerAbierto = null;
		for (Iterator<Alquiler> iterator = coleccionAlquileres.iterator(); alquilerAbierto == null
				&& iterator.hasNext();) {
			Alquiler alquiler = iterator.next();
			if (alquiler.getCliente().equals(cliente) && alquiler.getFechaDevolucion() == null) {
				alquilerAbierto = alquiler;
			}
		}
		return alquilerAbierto;
	}

	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un vehículo nulo.");
		}
		Alquiler alquiler = getAlquilerAbierto(vehiculo);
		if (alquiler == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese vehículo.");
		}
		alquiler.devolver(fechaDevolucion);
	}

	private Alquiler getAlquilerAbierto(Vehiculo vehiculo) {
		Alquiler alquilerAbiertoVehiculo = null;
		for (Iterator<Alquiler> iterator = coleccionAlquileres.iterator(); alquilerAbiertoVehiculo == null
				&& iterator.hasNext();) {
			Alquiler alquiler = iterator.next();
			if (alquiler.getVehiculo().equals(vehiculo) && alquiler.getFechaDevolucion() == null) {
				alquilerAbiertoVehiculo = alquiler;
			}
		}
		return alquilerAbiertoVehiculo;
	}

	@Override
	public Alquiler buscar(Alquiler alquiler) {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede buscar un alquiler nulo.");
		}
		if (coleccionAlquileres.indexOf(alquiler) == -1) { // si es diferente de -1 significa que lo contiene
			alquiler = null; // si no lo contiene, lo inicializa a null
		} else {
			coleccionAlquileres.get(coleccionAlquileres.indexOf(alquiler));
		}
		return alquiler;
	}

	@Override
	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede borrar un alquiler nulo.");
		}
		if (coleccionAlquileres.contains(alquiler)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
		}
		coleccionAlquileres.remove(alquiler);

	}
}
