package br.com.cams7.embarcado.arduino;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Arduino {
	private OutputStream output;
	private InputStream input;

	private int baudRate;
	private String serialPort;

	/**
	 * Construtor da classe Arduino
	 * 
	 * @param serialPort
	 *            - Porta COM que ser� utilizada para enviar os dados para o
	 *            arduino
	 * @param bauldRate
	 *            - Taxa de transfer�ncia da porta serial geralmente � 9600
	 */
	public Arduino(String serialPort, int baudRate) throws ArduinoException {
		this.serialPort = serialPort;
		this.baudRate = baudRate;

		initialize();
	}

	/**
	 * M�doto que verifica se a comunica��o com a porta serial est� ok
	 */
	private void initialize() throws ArduinoException {

		// Define uma vari�vel portId do tipo CommPortIdentifier para
		// realizar a comunica��o serial
		CommPortIdentifier portId = null;
		try {
			// Tenta verificar se a porta COM informada existe
			portId = CommPortIdentifier.getPortIdentifier(this.serialPort);
		} catch (NoSuchPortException e) {
			// Caso a porta COM n�o exista ser� exibido um erro
			throw new ArduinoException("Porta '" + serialPort
					+ "' n�o encontrada", e.getCause());
		}

		try {
			// Abre a porta COM
			SerialPort port = (SerialPort) portId.open("Comunica��o serial",
					baudRate);

			output = port.getOutputStream();
			input = port.getInputStream();

			port.setSerialPortParams(baudRate, // taxa de transfer�ncia da
					// porta serial
					SerialPort.DATABITS_8, // taxa de 10 bits 8 (envio)
					SerialPort.STOPBITS_1, // taxa de 10 bits 1 (recebimento)
					SerialPort.PARITY_NONE); // receber e enviar dados
		} catch (PortInUseException | IOException
				| UnsupportedCommOperationException e) {
			throw new ArduinoException("Erro na comunica��o serial",
					e.getCause());
		}

	}

	/**
	 * M�todo que fecha a comunica��o com a porta serial
	 */
	public void close() throws ArduinoException {
		ArduinoException exception = new ArduinoException(
				"N�o foi poss�vel fechar a porta '" + serialPort + "'");
		try {
			if (input != null)
				input.close();
		} catch (IOException e) {
			exception.addSuppressed(e);
		}

		try {
			if (output != null)
				output.close();
		} catch (IOException e) {
			exception.addSuppressed(e);
		}

		if (exception.getSuppressed().length > 0)
			throw exception;
	}

	/**
	 * @param opcao
	 *            - Valor a ser enviado pela porta serial
	 */
	public void serialWrite(byte value) throws ArduinoException {
		if (output == null)
			throw new ArduinoException("O 'OutputStream' n�o foi inicializado");

		try {
			output.write(value);// escreve o valor na porta serial para ser
								// enviado
		} catch (IOException e) {
			throw new ArduinoException("N�o foi poss�vel enviar o dado",
					e.getCause());
		}
	}

	public int serialRead() throws ArduinoException {
		if (input == null)
			throw new ArduinoException("O 'InputStream' n�o foi inicializado");

		try {
			return input.read();
		} catch (IOException e) {
			throw new ArduinoException("N�o foi poss�vel receber o dado",
					e.getCause());
		}
	}
}