package launch;

import controlador.Controlador;
import modelo.Modelo;
import modelo.ModeloFich;
import vista.VistaPrincipal;

public class Launch {
  
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VistaPrincipal vistaPrincipal = new VistaPrincipal();
		Modelo modelo = new Modelo();
		Controlador controlador = new Controlador();
		ModeloFich modeloFich = new ModeloFich();
		
		
		
		controlador.setModelo(modelo);
		controlador.setModeloFich(modeloFich);
		controlador.setVistaPrincipal(vistaPrincipal);


		vistaPrincipal.setControlador(controlador);
		vistaPrincipal.setModelo(modelo);

		modelo.setVistaPrincipal(vistaPrincipal);
		modelo.setModeloFich(modeloFich);
		
		modeloFich.setModelo(modelo);
	
		vistaPrincipal.setVisible(true);
		
		//Quitar esto de aqui
		modelo.getConnection();
		
	}

	
	
}
