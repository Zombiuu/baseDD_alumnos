package controlador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import modelo.Modelo;
import modelo.ModeloFich;
import vista.VistaPrincipal;

public class Controlador {
	private Modelo modelo;
	private ModeloFich modeloFich;
	private VistaPrincipal vistaPrincipal;

	public void setModelo(Modelo modelo) {

		this.modelo = modelo;

	}

	public void MostrarTabla() {
		modelo.mostrarTabla();
	}

	public void setVistaPrincipal(VistaPrincipal vistaPrincipal) {

		this.vistaPrincipal = vistaPrincipal;
	}

	public void setModeloFich(ModeloFich modeloFich) {

		this.modeloFich = modeloFich;
	}

	public void nuevoAlumno() {
		modelo.insertarAlumno(vistaPrincipal.getTxtDni(), vistaPrincipal.getTxtNombre(),
				vistaPrincipal.getTxtApellido(), Integer.parseInt(vistaPrincipal.getTxtTelefono()),
				vistaPrincipal.getTxtNacionalidad());
		// TODO Auto-generated method stub

	}

	public void mostrarDatos() {

		vistaPrincipal.getTxtNombreMod().setText(String.valueOf(
				this.vistaPrincipal.getTable().getValueAt(this.vistaPrincipal.getTable().getSelectedRow(), 1)));
		vistaPrincipal.getTxtApellidoMod().setText(String.valueOf(
				this.vistaPrincipal.getTable().getValueAt(this.vistaPrincipal.getTable().getSelectedRow(), 2)));
		vistaPrincipal.getTxtTelefonoMod().setText(String.valueOf(
				this.vistaPrincipal.getTable().getValueAt(this.vistaPrincipal.getTable().getSelectedRow(), 3)));
		vistaPrincipal.getTxtNacionalidadMod().setText(String.valueOf(
				this.vistaPrincipal.getTable().getValueAt(this.vistaPrincipal.getTable().getSelectedRow(), 4)));
	}

	public void eliminarPersona() {
		modelo.eliminarPersona(String.valueOf(
				this.vistaPrincipal.getTable().getValueAt(this.vistaPrincipal.getTable().getSelectedRow(), 0)));
		
		
	}
	
	public void guardaTabla(){
    modelo.guardaTabla();
    }


}
