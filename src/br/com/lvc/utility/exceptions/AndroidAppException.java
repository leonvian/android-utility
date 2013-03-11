package br.com.lvc.utility.exceptions;

public class AndroidAppException extends Exception {

	private static final long serialVersionUID = -2870615513331845118L;
	
	/**
	 * exibe a mensagem de erro em formato de TOAST
	 */
	public static final int MODE_TOAST = 10;
	/**
	 * exibe a mensagem de erro em formato de dialog
	 */
	public static final int MODE_DIALOG = 11;
	
	private int mode;
	private int messageFromResource;
	
	public AndroidAppException() {
		super();
	}
	
	public AndroidAppException(int message) {
		super();
		setMessageFromResource(message);
	}
	
	public AndroidAppException(Throwable e) {
		super(e);
	}
	
	public AndroidAppException(String message, Throwable exception) {
		super(message, exception);
	}
	
	public AndroidAppException(int message, Throwable e) {
		super(e);
		setMessageFromResource(message);
	}

	public int getMessageFromResource() {
		return messageFromResource;
	}

	public void setMessageFromResource(int messageFromResource) {
		this.messageFromResource = messageFromResource;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
	
	
	
}
