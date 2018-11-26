package principal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Sistema {
	
	private String nomeSistema;
	private String ip;
	private String porta;
	private String mensagem;
	private String servico;
	
	
	public String emitirAlerta(List<String> bairros) throws Exception {
		
		String USER_AGENT = "Mozilla/5.0";
		
		//recebendo a lista de bairros que contém a presença do mosquito
		mensagem = bairros.toString();
		
		// modificando a string: tirando o espaço para colocar nada
		mensagem = mensagem.replace(" ", "");
		
		// tirando o abre colchete
		mensagem = mensagem.replace("[", "");
		
		// tirando o fecha colchete
		mensagem = mensagem.replace("]", "");
		
		// fazendo a comunicação por meio de chamada de serviço
		String url = "http://" + ip + ":" + porta + "/" + servico + "/" + mensagem;
		
		// trecho de codigo copiado da internet que faz todo o processo de chamada de servico
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("Enviando requisição 'GET' para a URL: " + url);

		if (responseCode == 200) {
			System.out.println("Requisição feita com sucesso! Código " + responseCode + "\n");
		} else
			System.out.println("Requisição não processada.");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
			
		return "Alerta enviado com sucesso!";
	}
	
	public String getServico() {
		return servico;
	}


	public void setServico(String servico) {
		this.servico = servico;
	}
	
	
	public String getNomeSistema() {
		return nomeSistema;
	}
	public void setNomeSistema(String nomeSistema) {
		this.nomeSistema = nomeSistema;
	}
	public String getIP() {
		return ip;
	}
	public void setIP(String iP) {
		ip = iP;
	}
	public String getPorta() {
		return porta;
	}
	public void setPorta(String porta) {
		this.porta = porta;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	

}
