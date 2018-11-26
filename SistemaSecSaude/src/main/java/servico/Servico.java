package servico;

import java.util.List;
import email.JavaMailApp;

/**
 * Classe que implementa os metodos (servicos) que foram definidos na interface
 * *InterfaceServico*
 * 
 * @author Danillo
 */

public class Servico implements InterfaceServico {

	public String receberAlertaSaude(List<String> bairros) {
		JavaMailApp email = new JavaMailApp();
		email.enviarEmail(bairros);

		return "E-mail de alerta enviado a Secretaria de Saúde!";
	}

	public String emitirAlerta(String id) {
		System.out.println(id);
		return id;
	}

}