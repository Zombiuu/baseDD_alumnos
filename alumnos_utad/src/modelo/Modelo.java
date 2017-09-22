package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import javax.swing.JOptionPane;

import vista.VistaPrincipal;

public class Modelo {

	private String url;
	private String usuario;
	private String clave;

	private VistaPrincipal vistaPrincipal;
	private ModeloFich modeloFich;

	public Modelo() {
		super();
		Properties propiedades = new Properties();
		InputStream entrada = null;
		try {
			File config = new File("conf/configuracion.ini");
			if (config.exists()) {
				entrada = new FileInputStream(config);

				propiedades.load(entrada);

				url = propiedades.getProperty("BaseDatos");
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

	public Connection getConnection() {
		Connection con;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, usuario, clave);
			System.out.println(con);
			return con;
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"No se pudo conectar con la base de datos, modifique la informacion en la configuracion");
			return null;
			// TODO: handle exception
		}
	}

	public void setVistaPrincipal(VistaPrincipal vistaPrincipal) {
		// TODO Auto-generated method stub
		this.vistaPrincipal = vistaPrincipal;
	}

	public void setModeloFich(ModeloFich modeloFich) {
		// TODO Auto-generated method stub
		this.modeloFich = modeloFich;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public VistaPrincipal getVistaPrincipal() {
		return vistaPrincipal;
	}

	public ModeloFich getModeloFich() {
		return modeloFich;
	}
	
	
}
