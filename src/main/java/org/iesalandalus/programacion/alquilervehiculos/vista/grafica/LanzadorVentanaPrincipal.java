package org.iesalandalus.programacion.alquilervehiculos.vista.grafica;

import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LanzadorVentanaPrincipal extends Application {
	private static final String TITULO = "Vistas Ventanas Alquiler Vehículos";

	@Override
	public void start(Stage escenarioPrincipal) throws Exception {
		try {
			

			Controlador ventanaPrincipal = Controladores.get("vistas/VentanaPrincipal.fxml", TITULO, null);

			/*
			 * FXMLLoader cargadorVentanaPrincipal = new
			 * FXMLLoader(LocalizadorRecursos.class.getResource(
			 * "vistas/VentanaPrincipal.fxml")); Parent raiz =
			 * cargadorVentanaPrincipal.load();
			 */

			ventanaPrincipal.getEscenario().setOnCloseRequest(e -> confirmarSalida(ventanaPrincipal.getEscenario(), e));

			/*
			 * Scene escena = new Scene(raiz);
			 * escenarioPrincipal.setTitle("Vista Gráfica de Alquiler de Vehículos");
			 * escenarioPrincipal.setScene(escena); escenarioPrincipal.show();
			 */

			ventanaPrincipal.getEscenario().show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void comenzar() {
		launch(LanzadorVentanaPrincipal.class);
	}

	private void confirmarSalida(Stage escenario, WindowEvent e) {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Está seguro de que quiere salir de la aplicación?",
				escenario)) {
			escenario.close();
		} else {
			e.consume();
		}

	}
}

/*
 * cargador para cargar la vista fxml le pasamos el sitio donde está la vista
 * que quiero de él con el load lee el fichero y lo interpreta devuelve la
 * jerarquía de nodos. Para no cambiar de código segun sea hbox o vbox ponemos
 * el PARENT. Tengo el raiz (antes añadi getChildren etc y ahora simplemente lo
 * leo con el codigo creado)
 * 
 * 
 * diseñamos ficheros xml, leemos y mostramos. Eso hacemos en el Lanzador
 */