package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

import java.util.Objects;

public class Cliente {
	private static final String ER_NOMBRE = "[A-Z������][a-z������]+( [A-Z������][a-z������]+)*";
	private static final String ER_DNI = "\\d{8}[A-HJ-NP-TV-Z]";
	private static final String ER_TELEFONO = "[6-9]\\d{8}";
	private String nombre;
	private String dni;
	private String telefono;

	// constructor con par�metros
	public Cliente(String nombre, String dni, String telefono) {
		setNombre(nombre);
		setDni(dni);
		setTelefono(telefono);
	}

//constructor copia
	public Cliente(Cliente cliente) {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No es posible copiar un cliente nulo.");
		}
		nombre = cliente.getNombre();
		telefono = cliente.getTelefono();
		dni = cliente.getDni();
	}

//m�todos getters and setters con excepciones
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre no puede ser nulo.");
		}
		if (!nombre.matches(ER_NOMBRE)) {
			throw new IllegalArgumentException("ERROR: El nombre no tiene un formato v�lido.");
		}
		this.nombre = nombre;
	}

	public String getDni() {
		return dni;
	}

	private void setDni(String dni) {
		if (dni == null) {
			throw new NullPointerException("ERROR: El DNI no puede ser nulo.");
		}
		if (!dni.matches(ER_DNI)) {
			throw new IllegalArgumentException("ERROR: El DNI no tiene un formato v�lido.");
		}
		if (!comprobarLetraDni(dni)) {
			throw new IllegalArgumentException("ERROR: La letra del DNI no es correcta.");
		}
		this.dni = dni;
	}

	private boolean comprobarLetraDni(String dni) {
		char[] letraDni = { 'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V',
				'H', 'L', 'C', 'K', 'E' };
		int dni2 = Integer.parseInt(dni.substring(0, 8)); // parseInt para convertir la cadena (dni) a entero
		return dni.charAt(8) == letraDni[dni2 % 23];
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		if (telefono == null) {
			throw new NullPointerException("ERROR: El tel�fono no puede ser nulo.");
		}
		if (!telefono.matches(ER_TELEFONO)) {
			throw new IllegalArgumentException("ERROR: El tel�fono no tiene un formato v�lido.");
		}
		this.telefono = telefono;
	}

	// m�todo de clase
	public static Cliente getClienteConDni(String dni) {
		return new Cliente("Isabel", dni, "653412531");
	}

	// m�todos hashcode y equals
	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(dni, other.dni);
	}

	// m�todo toString
	@Override
	public String toString() {
		return String.format("%s - %s (%s)", nombre, dni, telefono);
	}
}