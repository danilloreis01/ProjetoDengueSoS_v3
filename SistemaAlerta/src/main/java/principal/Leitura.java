package principal;

import java.util.Date;

/*
 * Classe responsável para salvar as 
 * informações das leituras dos sensores
 */

public class Leitura {
	
	private boolean presenca;
	private Date data;
	private String bairro;
	
	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public boolean isPresenca() {
		return presenca;
	}
	
	public void setPresenca(boolean presenca) {
		this.presenca = presenca;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
}
