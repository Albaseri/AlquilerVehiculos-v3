package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Vehiculos implements IVehiculos {

	private static final File FICHERO_VEHICULOS = new File(
			String.format("%s%s%s", "datos", File.separator, "vehiculos.xml"));
	private static final String RAIZ = "vehiculos";
	private static final String VEHICULO = "vehiculo";
	private static final String MARCA = "marca";
	private static final String MODELO = "modelo";
	private static final String MATRICULA = "matricula";
	private static final String CILINDRADA = "cilindrada";
	private static final String PLAZAS = "plazas";
	private static final String PMA = "pma";
	private static final String TIPO = "tipo";
	private static final String TURISMO = "turismo";
	private static final String AUTOBUS = "autobus";
	private static final String FURGONETA = "furgoneta";

	private List<Vehiculo> coleccionVehiculos;
	private static Vehiculos instancia;

	private Vehiculos() {
		coleccionVehiculos = new ArrayList<>();
	}

	static Vehiculos getInstancia() {
		if (instancia == null) {
			instancia = new Vehiculos();
		}
		return instancia;
	}

	@Override
	public void comenzar() {
		Document documento = UtilidadesXml.leerXmlDeFichero(FICHERO_VEHICULOS);
		if (documento != null) {
			System.out.println("Fichero XML leído correctamente.");
			leerDom(documento);
		} else {
			System.out.printf("No se puede leer el fichero de entrada: %s.%n", FICHERO_VEHICULOS);
		}
	}

	private void leerDom(Document documentoXml) {
		NodeList vehiculos = documentoXml.getElementsByTagName(VEHICULO);
		for (int i = 0; i < vehiculos.getLength(); i++) {
			Node nVehiculo = vehiculos.item(i);
			if (nVehiculo.getNodeType() == Node.ELEMENT_NODE) {
				try {
					insertar(getVehiculo((Element) nVehiculo));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	private Vehiculo getVehiculo(Element elemento) {
		String marca = elemento.getAttribute(MARCA);
		String modelo = elemento.getAttribute(MODELO);
		String matricula = elemento.getAttribute(MATRICULA);

		int cilindrada = Integer.parseInt(elemento.getAttribute(CILINDRADA));
		int plazas = Integer.parseInt(elemento.getAttribute(PLAZAS));
		int pma = Integer.parseInt(elemento.getAttribute(PMA));

		Vehiculo vehiculoVariable = (Vehiculo) elemento;
		if (vehiculoVariable instanceof Turismo) {
			vehiculoVariable = new Turismo(marca, modelo, cilindrada, matricula);
		} else if (vehiculoVariable instanceof Autobus) {
			vehiculoVariable = new Autobus(marca, modelo, plazas, matricula);
		} else if (vehiculoVariable instanceof Furgoneta) {
			vehiculoVariable = new Furgoneta(marca, modelo, pma, plazas, matricula);
		}
		return vehiculoVariable;
	}

	@Override
	public void terminar() {
		UtilidadesXml.escribirXmlAFichero(crearDom(), FICHERO_VEHICULOS);

	}

	private Document crearDom() {
		DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
		Document documentoXml = null;
		if (constructor != null) {
			documentoXml = constructor.newDocument();
			documentoXml.appendChild(documentoXml.createElement(RAIZ));
			for (Vehiculo vehiculo : coleccionVehiculos) {
				Element elementoVehiculo = getElemento(documentoXml, vehiculo);
				documentoXml.getDocumentElement().appendChild(elementoVehiculo);
			}
		}
		return documentoXml;
	}

	private Element getElemento(Document documentoXml, Vehiculo vehiculo) {

		Element elementoVehiculo = documentoXml.createElement(VEHICULO);
		elementoVehiculo.setAttribute(MARCA, vehiculo.getMarca());
		elementoVehiculo.setAttribute(MATRICULA, vehiculo.getMatricula());
		elementoVehiculo.setAttribute(MODELO, vehiculo.getModelo());

		if (vehiculo instanceof Turismo turismoN) {
			elementoVehiculo.setAttribute(CILINDRADA, String.valueOf(turismoN.getCilindrada()));
			elementoVehiculo.setAttribute(TIPO, TURISMO);
		} else if (vehiculo instanceof Autobus autobusN) {
			elementoVehiculo.setAttribute(PLAZAS, String.valueOf((autobusN.getPlazas())));
			elementoVehiculo.setAttribute(TIPO, AUTOBUS);

		} else if (vehiculo instanceof Furgoneta furgonetaN) {
			elementoVehiculo.setAttribute(PMA, String.valueOf(furgonetaN.getPma()));
			elementoVehiculo.setAttribute(PLAZAS, String.valueOf(furgonetaN.getPlazas()));
			elementoVehiculo.setAttribute(TIPO, FURGONETA);

			// Con STRING.FORMAT--> elementoVehiculo.setAttribute(PLAZAS,
			// String.format("%s",furgonetaN.getPlazas()));

		}
		return elementoVehiculo;
	}

	@Override
	public List<Vehiculo> get() {
		return new ArrayList<>(coleccionVehiculos);
	}

	@Override
	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede insertar un vehículo nulo.");
		}
		if (!coleccionVehiculos.contains(vehiculo)) {
			coleccionVehiculos.add(vehiculo);
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un vehículo con esa matrícula.");
		}
	}

	@Override
	public Vehiculo buscar(Vehiculo vehiculo) {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede buscar un vehículo nulo.");
		}
		int indiceVehiculo = coleccionVehiculos.indexOf(vehiculo);
		if (indiceVehiculo != -1) {
			vehiculo = coleccionVehiculos.get(indiceVehiculo);
		} else {
			vehiculo = null;
		}
		return vehiculo;
	}

	@Override
	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede borrar un vehículo nulo.");
		}
		if (coleccionVehiculos.contains(vehiculo)) {
			coleccionVehiculos.remove(vehiculo);
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningún vehículo con esa matrícula.");
		}
	}
}