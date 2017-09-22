package controlador;

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
		modelo.ShowJTable();
	}

	public void setVistaPrincipal(VistaPrincipal vistaPrincipal) {

		this.vistaPrincipal = vistaPrincipal;
	}

	public void setModeloFich(ModeloFich modeloFich) {

		this.modeloFich = modeloFich;
	}

	public void nuevoAlumno() {
		modelo.insertarAlumno(vistaPrincipal.getTxtDni(), vistaPrincipal.getTxtNombre(),
				vistaPrincipal.getTxtApellido(), Integer.parseInt(vistaPrincipal.getTxtTelefono()), vistaPrincipal.getTxtNacionalidad());
		// TODO Auto-generated method stub

	}

}
