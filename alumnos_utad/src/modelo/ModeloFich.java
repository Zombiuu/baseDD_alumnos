package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JOptionPane;



public class ModeloFich {
	private Modelo modelo;
	
	private String baseDatos;
	private String usuario;
	private String clave;

	public void guardar(String baseDatos, String usuario, String clave) {
		Properties propiedades = new Properties();
		OutputStream salida = null;
		OutputStream salidaLog = null;

		try {
			File config = new File("conf/configuracion.ini");
			File log = new File("conf/configuracion.log");
			if (config.exists() && log.exists()) {
				salida = new FileOutputStream(config);
				salidaLog = new FileOutputStream(log, true);

				propiedades.setProperty("BaseDatos", baseDatos);
				propiedades.setProperty("Usuario", usuario);
				propiedades.setProperty("Clave", clave);

				propiedades.store(salida, "Registro actual:");
				propiedades.store(salidaLog, "Nuevo registro");
				JOptionPane.showMessageDialog(null, "Los cambios efectuados tendran efecto tras reiniciar la aplicaci�n");
			} else
				System.err.println("Fichero no encontrado");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (salida != null) {
				try {
					salida.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void mostrar() {
		Properties propiedades = new Properties();
		InputStream entrada = null;
		try {
			File config = new File("conf/configuracion.ini");
			if (config.exists()) {
				entrada = new FileInputStream(config);

				propiedades.load(entrada);

				baseDatos = propiedades.getProperty("BaseDatos");
				usuario = propiedades.getProperty("Usuario");
				clave = propiedades.getProperty("Clave");
			

			} else
				System.err.println("Fichero no encontrado");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (entrada != null) {
				try {
					entrada.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getBaseDatos() {
		return baseDatos;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	

}
