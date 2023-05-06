package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Clientes implements IClientes {
	private static final File FICHERO_CLIENTES = new File(
			String.format("%s%s%s", "datos", File.separator, "clientes.xml"));
	private static final String RAIZ = "clientes";
	private static final String CLIENTE = "cliente";
	private static final String NOMBRE = "nombre";
	private static final String DNI = "dni";
	private static final String TELEFONO = "telefono";
	private List<Cliente> coleccionClientes;

	private static Clientes instancia;

	private Clientes() {
		coleccionClientes = new ArrayList<>();
	}

	static Clientes getInstancia() {
		if (instancia == null) {
			instancia = new Clientes();
		}
		return instancia;
	}

	@Override
	public void comenzar() {
		Document documento = UtilidadesXml.leerXmlDeFichero(FICHERO_CLIENTES);
		if (documento != null) {
			System.out.println("Fichero XML leído correctamente.");
			leerDom(documento);
		} else {
			System.out.printf("No se puede leer el fichero de entrada: %s.%n", FICHERO_CLIENTES);
		}
	}

	private void leerDom(Document documentoXml) {
		NodeList clientes = documentoXml.getElementsByTagName(CLIENTE);
		for (int i = 0; i < clientes.getLength(); i++) {
			Node nCliente = clientes.item(i);
			if (nCliente.getNodeType() == Node.ELEMENT_NODE) {
				try {
					insertar(getCliente((Element) nCliente));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	private Cliente getCliente(Element elemento) {
		String dni = elemento.getAttribute(DNI);
		String nombre = elemento.getAttribute(NOMBRE);
		String telefono = elemento.getAttribute(TELEFONO);
		return new Cliente(nombre, dni, telefono);
	}

	@Override
	public void terminar() {
		UtilidadesXml.escribirXmlAFichero(crearDom(), FICHERO_CLIENTES);
	}

	private Document crearDom() {
		DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
		Document documentoXml = null;
		if (constructor != null) {
			documentoXml = constructor.newDocument();
			documentoXml.appendChild(documentoXml.createElement(RAIZ));
			for (Cliente cliente : coleccionClientes) {
				Element elementoCliente = getElemento(documentoXml, cliente);
				documentoXml.getDocumentElement().appendChild(elementoCliente);
			}
		}
		return documentoXml;
	}

	private Element getElemento(Document documentoXml, Cliente cliente) {
		Element elementoCliente = documentoXml.createElement(CLIENTE);
		elementoCliente.setAttribute(NOMBRE, cliente.getNombre());
		elementoCliente.setAttribute(DNI, cliente.getDni());
		elementoCliente.setAttribute(TELEFONO, cliente.getTelefono());
		return elementoCliente;
	}

	@Override
	public List<Cliente> get() {
		return new ArrayList<>(coleccionClientes);
	}

	@Override
	public void insertar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede insertar un cliente nulo.");
		}
		if (!coleccionClientes.contains(cliente)) {
			coleccionClientes.add(cliente);
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un cliente con ese DNI.");
		}
	}

	@Override
	public Cliente buscar(Cliente cliente) {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede buscar un cliente nulo.");
		}
		int indiceCliente = coleccionClientes.indexOf(cliente); // declaro variable e inicializo
		if (indiceCliente != -1) { // si el índice de Cliente es diferente de null (-1), se mete dentro de la
									// colección y lo almacena.
			cliente = coleccionClientes.get(indiceCliente); // almaceno el cliente encontrado de la colección de
															// Clientes.
		} else {
			cliente = null;
		}
		return cliente;
	}

	@Override
	public void borrar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede borrar un cliente nulo.");
		}
		if (coleccionClientes.contains(cliente)) {
			coleccionClientes.remove(cliente);
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		}
	}

	@Override
	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
		if (cliente == null) { // compruebo si cliente pasado por parámetro es nulo
			throw new NullPointerException("ERROR: No se puede modificar un cliente nulo.");
		}
		Cliente clienteE = buscar(cliente); // busca y encuentra cliente. ClienteE es cliente encontrado

		if (clienteE == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		}
		if (nombre != null && !nombre.isBlank()) {
			clienteE.setNombre(nombre); // modifico el nombre
		}

		if (telefono != null && !telefono.isBlank()) {
			clienteE.setTelefono(telefono);
		}
	}
}
