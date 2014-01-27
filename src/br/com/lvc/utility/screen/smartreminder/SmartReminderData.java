package br.com.lvc.utility.screen.smartreminder;

import java.io.Serializable;

public class SmartReminderData implements Serializable {
	 
	private static final long serialVersionUID = 7853580862087869607L;
	
	public static final int TYPE_SUCESS = SmartReminderUtil.TYPE_SUCESS;
	public static final int TYPE_ALERT = SmartReminderUtil.TYPE_ALERT;
	public static final int TYPE_SUCESS_YES_NO = SmartReminderUtil.TYPE_SUCESS_YES_NO;
	
	private int mensagem;
	private String mensagemStr;
	private int tipo;
	private SmartReminderClickListener smartReminderClickListener;
	
	public SmartReminderData() {
	}

	public SmartReminderData(int mensagem, int tipo) {
		super();
		this.mensagem = mensagem;
		this.tipo = tipo;
	}
	
	public SmartReminderData(String mensagemStr, int tipo) {
		super();
		this.mensagemStr = mensagemStr;
		this.tipo = tipo;
	}
	
	

	public SmartReminderData(String mensagemStr, int tipo,
			SmartReminderClickListener smartReminderClickListener) {
		super();
		this.mensagemStr = mensagemStr;
		this.tipo = tipo;
		this.smartReminderClickListener = smartReminderClickListener;
	}

	public SmartReminderData(int mensagem, int tipo,
			SmartReminderClickListener smartReminderClickListener) {
		super();
		this.mensagem = mensagem;
		this.tipo = tipo;
		this.smartReminderClickListener = smartReminderClickListener;
	}

	public String getMensagemStr() {
		return mensagemStr;
	}
 
	public int getMensagem() {
		return mensagem;
	}
	
	public void setMensagem(int mensagem) {
		this.mensagem = mensagem;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	} 
	
	public SmartReminderClickListener getSmartReminderClickListener() {
		return smartReminderClickListener;
	}
	
	public void setSmartReminderClickListener(
			SmartReminderClickListener smartReminderClickListener) {
		this.smartReminderClickListener = smartReminderClickListener;
	}
 
}