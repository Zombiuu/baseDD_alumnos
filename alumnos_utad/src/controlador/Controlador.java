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

	public void setVistaPrincipal(VistaPrincipal vistaPrincipal) {
		
		this.vistaPrincipal = vistaPrincipal;
	}

	public void setModeloFich(ModeloFich modeloFich) {
		
		this.modeloFich = modeloFich;
	}

}
