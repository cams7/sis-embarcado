package br.com.cams7.arduino;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

public class SimpleRead implements Runnable, SerialPortEventListener {
	private static final String SERIAL_PORT = "COM2";
	private static final int BAUD_RATE = 9600;

	private static final long MILLIS = 500;

	private static CommPortIdentifier portId;

	private OutputStream output;
	private InputStream input;

	private SerialPort port;
	private Thread readThread;

	public static void main(String[] args) {
		Enumeration<?> portList = CommPortIdentifier.getPortIdentifiers();

		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				if (portId.getName().equals(SERIAL_PORT)) {
					// if (portId.getName().equals("/dev/term/a")) {
					System.out.println("Port: " + portId.getName());
					new SimpleRead();
				}
			}
		}
	}

	public SimpleRead() {
		try {
			// Abre a porta COM
			port = (SerialPort) portId.open("Comunicacao serial", BAUD_RATE);

			output = port.getOutputStream();
			input = port.getInputStream();

			port.addEventListener(this);

			port.notifyOnDataAvailable(true);

			port.setSerialPortParams(BAUD_RATE, // taxa de transferencia da
												// porta serial
					SerialPort.DATABITS_8, // taxa de 10 bits 8 (envio)
					SerialPort.STOPBITS_1, // taxa de 10 bits 1 (recebimento)
					SerialPort.PARITY_NONE); // receber e enviar dados
		} catch (PortInUseException | IOException | TooManyListenersException
				| UnsupportedCommOperationException e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			if (output != null)
				try {
					output.close();
				} catch (IOException e) {
					System.out.println("Error: " + e.getMessage());
				}
			if (input != null)
				try {
					input.close();
				} catch (IOException e) {
					System.out.println("Error: " + e.getMessage());
				}
		}

		readThread = new Thread(this);
		readThread.start();
	}

	public void run() {
		try {
			Thread.sleep(MILLIS);
		} catch (InterruptedException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE:
			int value = -1;
			try {
				while (input.available() > 0)
					value = serialRead();
			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			}
			System.out.println("Value: " + value);
			break;
		}
	}

	public void serialWrite(byte dado) throws IOException {
		output.write(dado);// escreve o valor na porta serial para ser
							// enviado

	}

	public int serialRead() throws IOException {
		return input.read();
	}
}
