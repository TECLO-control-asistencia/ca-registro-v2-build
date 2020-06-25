package mx.com.teclo.lectorhuella.base.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.log4j.Logger;

import mx.com.teclo.DigitalPersonMain;
import mx.com.teclo.digitalperson.business.vo.EstadoVO;

/**
 * Interfaz grafica para mostrar el estado del lector de huellas
 * 
 * @author beatriz.orosio@unitis.com.mx
 *
 */

public class LectorHuellasGUI implements Observer {
	private static Logger LOGGER = Logger.getLogger(LectorHuellasGUI.class);
	private JFrame frame;
	private final JLabel label;
	private Boolean appVisible;
	private DigitalPersonMain lector;
	
	public LectorHuellasGUI() {
		LOGGER.info("Iniciando interfaz gráfica");
		this.appVisible = AppConfiguracion.getInstance().getAppVisible();
		frame = new JFrame("Control de Asistencia");
		label = new JLabel();
	}

	public void mostrarInterfaz() {
		frame.setBounds(100, 100, 254, 152);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		Container content = frame.getContentPane();
		content.add(label, BorderLayout.CENTER);
		label.setText("Iniciando interfaz");
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setVisible(appVisible);
	}

	public void mostrarEstado(final EstadoVO estado) {
		frame.getContentPane().setBackground(estado.isConectado() ? Color.GREEN : Color.RED);
		label.setText(estado.getMensaje());
		frame.setVisible(appVisible);
	}

	@Override
	public void update(Observable o, Object arg) {
		lector = (DigitalPersonMain) o;
		mostrarEstado(lector.getEstado());
	}

}
