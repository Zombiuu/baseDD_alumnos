package modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import vista.VistaPrincipal;

public class Modelo {

	private String url;
	private String usuario;
	private String clave;

	private VistaPrincipal vistaPrincipal;
	private ModeloFich modeloFich;

	private int cod;
	private String dni;
	private String nombre;
	private String apellido;
	private int telefono;
	private String nacionalidad;

	private int codMod;
	private String dniMod;
	private String nombreMod;
	private String apellidoMod;
	private int telefonoMod;
	private String nacionalidadMod;

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

	public Modelo(int cod, String dni, String nombre, String apellido, int telefono, String nacionalidad) {
		this.cod = cod;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.nacionalidad = nacionalidad;
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

	public ArrayList<Modelo> getInscripcionesList() {
		ArrayList<Modelo> inscripcionesList = new ArrayList<Modelo>();
		Connection connection = getConnection();

		String query = "SELECT * from alumnos";
		Statement st;
		ResultSet rs;
		try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			Modelo database;
			while (rs.next()) {
				database = new Modelo(rs.getInt("cod"), rs.getString("dni"), rs.getString("nombre"),
						rs.getString("apellido"), rs.getInt("telefono"), rs.getString("nacionalidad"));
				inscripcionesList.add(database);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return inscripcionesList;
	}

	public void mostrarTabla() {
		ArrayList<Modelo> list = getInscripcionesList();
		DefaultTableModel model = (DefaultTableModel) vistaPrincipal.getTablaInfo();

		Object[] row = new Object[5];
		for (int i = 0; i < list.size(); i++) {
			row[0] = list.get(i).getDni();
			row[1] = list.get(i).getNombre();
			row[2] = list.get(i).getApellido();
			row[3] = list.get(i).getTelefono();
			row[4] = list.get(i).getNacionalidad();

			model.addRow(row);
		}
	}

	public void insertarAlumno(String txtDni, String txtNombre, String txtApellido, int telefono,
			String txtNacionalidad) {
		Connection con = getConnection();
		String query = "INSERT INTO `alumnos` (`dni`, `nombre`, `apellido`, `telefono`, `nacionalidad`) VALUES ( ?,?,?,?,? )";
		PreparedStatement ps;

		try {
			ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, txtDni);
			ps.setString(2, txtNombre);
			ps.setString(3, txtApellido);
			ps.setInt(4, telefono);
			ps.setString(5, txtNacionalidad);

			if (ps.executeUpdate() == 1) {
				DefaultTableModel model = (DefaultTableModel) vistaPrincipal.getTablaInfo();
				model.setRowCount(0);
				mostrarTabla();

				JOptionPane.showMessageDialog(null, "Informacion almacenada satisfactoriamente");
			} else {
				JOptionPane.showMessageDialog(null, "La informacion no pudo ser almacenada");
			}
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}

	}

	public void modificarAlumno(String txtDniMod, String txtNombreMod, String txtApellidoMod, int txtTelefonoMod,
			String txtNacionalidadMod) {
		Connection con = getConnection();
		int r = 0;

		String query3 = "UPDATE alumnos SET dni=?,nombre=?,apellido=?,direccion=?,telefono=?,nacionalidad=? WHERE `alumnos`.`dni` = ?";
		PreparedStatement pstmt;

		try {
			pstmt = con.prepareStatement(query3, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, txtDniMod);
			pstmt.setString(2, txtNombreMod);
			pstmt.setString(3, txtApellidoMod);
			pstmt.setInt(4, txtTelefonoMod);
			pstmt.setString(5, txtNacionalidadMod);
			pstmt.setString(6, txtDniMod);

			r = pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				mostrarTabla();
				System.out.println(r);

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

	}
	public void eliminarPersona(String txtDniMod) {
		try 
		 {  
		Connection con = getConnection();
		PreparedStatement st = con.prepareStatement("DELETE FROM alumnos WHERE alumnos.dni = ?");
		st.setString(1,txtDniMod);
		st.executeUpdate(); 
		DefaultTableModel model =(DefaultTableModel) vistaPrincipal.getTablaInfo();
		model.setRowCount(0);
		mostrarTabla();
		 }
		 catch(Exception e)
		 {
		     System.out.println(e);
		 }
		
	}

	// public void obtenerDatosAlumno(String dni) {
	//
	// Connection connection = getConnection();
	//
	//
	//
	// String query = "SELECT * from alumnos WHERE dni = "+dniMod;
	// Statement st;
	// ResultSet rs;
	// try {
	// st = connection.createStatement();
	// rs = st.executeQuery(query);
	// Modelo database;
	// while (rs.next()) {
	// database = new Modelo(
	// rs.getInt("cod"),
	// rs.getString("dni"),
	// rs.getString("nombre"),
	// rs.getString("apellido"),
	// rs.getInt("telefono"),
	// rs.getString("nacionalidad"));
	//
	// }
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	//
	// this.vistaPrincipal.datosAlumno();
	//
	// }

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

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public void guardaTabla() {
	        try {

	            String sucursalesCSVFile = "src/archivos/DatosTabla.txt";
	            BufferedWriter bfw = new BufferedWriter(new FileWriter(sucursalesCSVFile ));

	            for (int i = 0 ; i < vistaPrincipal.getTable().getRowCount(); i++) //realiza un barrido por filas.
	            {
	                for(int j = 0 ; j < vistaPrincipal.getTable().getColumnCount();j++) //realiza un barrido por columnas.
	                {
	                    bfw.write((String)(vistaPrincipal.getTable().getValueAt(i,j)));
	                    if (j < vistaPrincipal.getTable().getColumnCount() -1) { //agrega separador "," si no es el ultimo elemento de la fila.
	                        bfw.write(",");
	                    }
	                }
	                bfw.newLine(); //inserta nueva linea.
	            }

	            bfw.close(); //cierra archivo!
	            System.out.println("El archivo fue salvado correctamente!");
	        } catch (IOException e) {
	            System.out.println("ERROR: Ocurrio un problema al salvar el archivo!" + e.getMessage());
	        }
	    }
		
	}




