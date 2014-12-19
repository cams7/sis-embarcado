package br.com.cams7.teste.embarcado;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import br.com.cams7.embarcado.arduino.Arduino;
import br.com.cams7.embarcado.arduino.ArduinoException;

/**
 *
 * @author klauder
 */
public class App extends JFrame {

	private static final long serialVersionUID = 1L;

	private final String SERIAL_PORT = "COM2";
	private final int BAUD_RATE = 9600;

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private JButton bClose;
	private JButton bLedOff;
	private JButton bLedOn;

	// End of variables declaration//GEN-END:variables

	private Arduino arduino;

	/**
	 * Creates new form JFInterface
	 */
	public App() {
		// Windows - porta e taxa de
		// transmissão
		// arduino = new ControlePorta("/dev/ttyUSB0",9600);//Linux - porta e
		// taxa de transmissão
		try {
			arduino = new Arduino(SERIAL_PORT, BAUD_RATE);
		} catch (ArduinoException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Serial",
					JOptionPane.PLAIN_MESSAGE);
		}

		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		bLedOn = new JButton();
		bLedOff = new JButton();
		bClose = new JButton();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("COM. Serial");

		bLedOn.setText("Ligar");
		bLedOn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				bLedOnMouseClicked(evt);
			}
		});

		bLedOff.setText("Desligar");
		bLedOff.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				bLedOffMouseClicked(evt);
			}
		});

		bClose.setText("Sair");
		bClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				bCloseMouseClicked(evt);
			}
		});

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addGroup(
								layout.createParallelGroup(
										GroupLayout.Alignment.LEADING)
										.addGroup(
												layout.createSequentialGroup()
														.addGap(31, 31, 31)
														.addComponent(bLedOn)
														.addGap(18, 18, 18)
														.addComponent(bLedOff))
										.addGroup(
												layout.createSequentialGroup()
														.addGap(75, 75, 75)
														.addComponent(bClose)))
						.addContainerGap(25, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
										.addComponent(bLedOn)
										.addComponent(bLedOff))
						.addPreferredGap(
								LayoutStyle.ComponentPlacement.RELATED, 12,
								Short.MAX_VALUE).addComponent(bClose)
						.addContainerGap()));
		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void bLedOnMouseClicked(MouseEvent evt) {// GEN-FIRST:event_jBLedOnMouseClicked
		// TODO add your handling code here:
		try {
			arduino.serialWrite((byte) 1);
		} catch (ArduinoException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Enviar dados",
					JOptionPane.PLAIN_MESSAGE);
			e.printStackTrace();
		}
		try {
			System.out.println("Potenciometro: " + arduino.serialRead());
		} catch (ArduinoException e) {
			JOptionPane.showMessageDialog(null,
					"Não foi possível receber o dado. ", "Receber dados",
					JOptionPane.PLAIN_MESSAGE);
			e.printStackTrace();
		}

		System.out.println(bLedOn.getText());// Imprime o nome do botão
												// pressionado
	}// GEN-LAST:event_jBLedOnMouseClicked

	private void bLedOffMouseClicked(MouseEvent evt) {// GEN-FIRST:event_jBLedOffMouseClicked
		try {
			arduino.serialWrite((byte) 0);
		} catch (ArduinoException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Enviar dados",
					JOptionPane.PLAIN_MESSAGE);
			e.printStackTrace();
		}
		System.out.println(bLedOff.getText());
	}// GEN-LAST:event_jBLedOffMouseClicked

	private void bCloseMouseClicked(MouseEvent evt) {// GEN-FIRST:event_jBCloseMouseClicked
		try {
			arduino.close();
		} catch (ArduinoException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),
					"Fechar porta COM", JOptionPane.PLAIN_MESSAGE);
			e.printStackTrace();
		}
		System.out.println(bClose.getText());
		System.exit(0);// fecha a aplicação
	}// GEN-LAST:event_jBCloseMouseClicked

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed"
		// desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (UIManager.LookAndFeelInfo info : UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(App.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(App.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(App.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(App.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>
		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new App().setVisible(true);
			}
		});
	}

}