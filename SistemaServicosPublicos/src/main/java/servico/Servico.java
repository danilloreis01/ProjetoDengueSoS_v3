package servico;

import java.util.List;

import email.JavaMailApp;

/**
 * Classe que implementa os m�todos (servi�os) que foram definidos na interface
 * *InterfaceServico*
 * 
 * @author Danillo
 */

public class Servico implements InterfaceServico {
	

	public String receberAlertaSecServicosPublicos(List<String> bairros){
		JavaMailApp email = new JavaMailApp();
		email.enviarEmail(bairros);
		return "E-mail de alerta enviado a Secretaria de Serviços Públicos";
	}

	

	
}