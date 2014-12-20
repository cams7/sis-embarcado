/**
 * 
 */
package br.com.cams7.arduino.jmx;

import br.com.cams7.arduino.Arduino;
import br.com.cams7.arduino.ArduinoException;

/**
 * @author cesar
 *
 */
public class AppArduinoService extends Arduino implements
		AppArduinoServiceMBean {

	private final int LED_AMARELA_APAGADA = 10;
	private final int LED_AMARELA_ACESA = 11;

	private final int LED_VERDE_APAGADA = 12;
	private final int LED_VERDE_ACESA = 13;

	private final int BTN_LED_AMARELA_SOLTO = 14;
	private final int BTN_LED_AMARELA_PRESSIONADO = 15;

	private final int BTN_LED_VERDE_SOLTO = 16;
	private final int BTN_LED_VERDE_PRESSIONADO = 17;

	private final int MIN_POTENCIOMETRO = 100;
	private final int MAX_POTENCIOMETRO = 200;

	private final byte EVENTO_MUDA_STATUS_LED_AMARELA = EVENTO_NAO_INFORMADO + 1;
	private final byte EVENTO_MUDA_STATUS_LED_VERDE = EVENTO_NAO_INFORMADO + 2;

	private boolean ledAmarelaLigada = false;
	private boolean ledVerdeLigada = false;

	public AppArduinoService() throws ArduinoException {
		super();
		System.out.println("Novo Servico");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.cams7.embarcado.arduino.jmx.ArduinoServiceMBean#mudaStatusLED()
	 */
	public void mudaStatusLEDAmarela() {
		try {
			if (ledAmarelaLigada)
				serialWrite(LED_AMARELA_APAGADA);
			else
				serialWrite(LED_AMARELA_ACESA);

			setEventoAtual(EVENTO_MUDA_STATUS_LED_AMARELA);

		} catch (ArduinoException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
		}

	}

	private void mudaStatusLEDArmarela(boolean ledAmarelaLigada) {
		System.out.println(ledAmarelaLigada ? "LED Amarela acesa"
				: "LED Amarela apagada");
	}

	private void mudaStatusLEDArmarelaOK() {
		ledAmarelaLigada = !ledAmarelaLigada;

		mudaStatusLEDArmarela(ledAmarelaLigada);
	}

	private void mudaStatusLEDArmarelaError() {
		System.err
				.println("Ocorreu um erro ao tentar acender ou apagar o LED Amarelo");
	}

	public void mudaStatusLEDVerde() {
		try {
			if (ledVerdeLigada)
				serialWrite(LED_VERDE_APAGADA);
			else
				serialWrite(LED_VERDE_ACESA);

			setEventoAtual(EVENTO_MUDA_STATUS_LED_VERDE);

		} catch (ArduinoException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
		}
	}

	private void mudaStatusLEDVerde(boolean ledVerdeLigada) {
		System.out.println(ledVerdeLigada ? "LED Verde acesa"
				: "LED Verde apagada");
	}

	private void mudaStatusLEDVerdeOK() {
		ledVerdeLigada = !ledVerdeLigada;
		mudaStatusLEDVerde(ledVerdeLigada);
	}

	private void mudaStatusLEDVerdeError() {
		System.err
				.println("Ocorreu um erro ao tentar acender ou apagar o LED Verde");
	}

	protected void envioOK() {
		switch (getEventoAtual()) {
		case EVENTO_NAO_INFORMADO:
			break;
		case EVENTO_MUDA_STATUS_LED_AMARELA:
			mudaStatusLEDArmarelaOK();
			break;
		case EVENTO_MUDA_STATUS_LED_VERDE:
			mudaStatusLEDVerdeOK();
			break;

		default:
			break;
		}
	}

	protected void envioError() {
		switch (getEventoAtual()) {
		case EVENTO_NAO_INFORMADO:
			break;
		case EVENTO_MUDA_STATUS_LED_AMARELA:
			mudaStatusLEDArmarelaError();
			break;
		case EVENTO_MUDA_STATUS_LED_VERDE:
			mudaStatusLEDVerdeError();
			break;

		default:
			break;
		}

	}

	protected void setDadoRecebido(int dadoRecebido) {
		if (dadoRecebido >= MIN_POTENCIOMETRO
				&& dadoRecebido <= MAX_POTENCIOMETRO)
			System.out.println("Valor atual do potenciometro '"
					+ (dadoRecebido - 100) + "%'");
		else
			switch (dadoRecebido) {
			case BTN_LED_AMARELA_SOLTO:
				ledAmarelaLigada = false;
				mudaStatusLEDArmarela(ledAmarelaLigada);
				break;
			case BTN_LED_AMARELA_PRESSIONADO:
				ledAmarelaLigada = true;
				mudaStatusLEDArmarela(ledAmarelaLigada);
				break;
			case BTN_LED_VERDE_SOLTO:
				ledVerdeLigada = false;
				mudaStatusLEDVerde(ledVerdeLigada);
				break;
			case BTN_LED_VERDE_PRESSIONADO:
				ledVerdeLigada = true;
				mudaStatusLEDVerde(ledVerdeLigada);
				break;
			default:
				System.err
						.println("O dado '" + dadoRecebido + "' nao e valido");
				break;
			}

	}

	protected void recebimentoError() {
		System.out
				.println("O prototipo no Proteus nao esta rodando ou a porta '"
						+ getSerialPort() + "' esta fechada");

	}

	public boolean isLedAmarelaLigada() {
		return ledAmarelaLigada;
	}

	public boolean isLedVerdeLigada() {
		return ledVerdeLigada;
	}

}
