package principal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Danillo e Rodolfo Silva
 * 
 *         Classe criada automaticamente quando o Maven foi projetado. Aqui a
 *         ideia é que o coordenador faça a requisição dos serviços, faça os
 *         cáculos e envia alerta aos outros sistemas para provêr o
 *         comportamento emergente do SoS.
 */

public class CoordCentral {

	public static void main(String[] args) throws Exception {

		while (true) {
			iniciarSoS();
			obterDados();
			analisarDadosSensores();
			
			Thread.sleep(7200000); // 2 horas
			//Thread.sleep(28800000); // 8 horas
		}
	}

	// Instanciar uma lista de sistemas para verificar quais os sistemas estão no SoS
	static List<Sistema> sistemas = new ArrayList<Sistema>();

	// Instanciar uma lista de sensores para verificar quais são os sensores presentes no SoS
	static List<Sensor> sensores = new ArrayList<Sensor>();
	
	// Instanciar uma lista de bairros onde haverá presença do mosquito
	static List<String> bairros = new ArrayList<String>();

	
	//Método responsável por verificar quais sensores estão funcionando no SoS
	public static void iniciarSoS() throws Exception {

		// Coordenador central faz a busca por constituintes do SoS
		setSistemas();
	
		//Fazendo a solicitação de um serviçço para obter as informacoes dos sensores cadastrados
		String resultado = sendGet("service/obterInformacoesSensores/", "192.168.0.101", "9001"); // IP de acesso a rede da minha casa
		
		// string RESULTADO sem o [] inicial e final do formato JSON [[tirando os colchetes iniciais e finais que estao presentes nesse comentario]]
		resultado = resultado.substring(1, resultado.length() - 1);

		// tirando todas as aspas da String RESULTADO
		resultado = resultado.replaceAll("\"", "");

		// Troca a vírgula do fim de cada ocorrencia para ponto e vírgula
		resultado = resultado.replace("],[", "];[");
		
		// criando um vetor de strings "quebrando" a cada ocorrencia de ;
		String[] ocorrencia = resultado.split(";");

		for (int i = 0; i < ocorrencia.length; i++) {

			String[] atributo = ocorrencia[i].split(",");

			Sensor sensor = new Sensor();

			sensor.setId(Integer.parseInt(atributo[0].substring(1, atributo[0].length())));
			sensor.setLatitude(atributo[1].substring(0, atributo[1].length()));
			sensor.setLongitude(atributo[2].substring(0, atributo[2].length()));
			sensor.setNome(atributo[3].substring(0, atributo[3].length()));
			sensor.setIdBairro(Integer.parseInt(atributo[4].substring(0, atributo[4].length() - 1)));
			sensores.add(sensor);
		}

	}

	public static void obterDados() throws Exception {

		//String result = sendGet("service/obterDadosSensores/", "10.62.8.239", "9001"); // IP de acesso labes
		String result = sendGet("service/obterDadosSensores/", "192.168.0.101", "9001"); // IP de acesso minha casa
		
		// Retira todas as aspas do resultado
		result = result.replaceAll("\"", "");
		
		// Troca a vírgula do fim de cada ocorrencia para ponto e vírgula
		result = result.replace("},{", "};{");

		String[] ocorrencia = result.split(";");

		for (int i = 0; i < ocorrencia.length; i++) {
			String[] atributo = ocorrencia[i].split(",");
			int sensorId = Integer.parseInt(atributo[3].substring(11));

			for (int j = 0; j < sensores.size(); j++) {
				if (sensores.get(j).getId() == sensorId) {
					sensores.get(j).salvarLeitura(ocorrencia[i]);
				}
			}
		}
	}

	public static void analisarDadosSensores() throws Exception {
		// primeiro percorrer o vetor de sensores
		// segundo percorrer o vetor de leitura dos sensores

		int contadorPresenca = 0;		

		for (Sensor sensor : sensores) {
			for (Leitura leitura : sensor.getLeituras()) {
				//Date agora = new Date();
				if (leitura.isPresenca()) {
					// if ((contaDias(leitura.getData(), agora) <= 2)) {
					contadorPresenca++;
					//checando a repetição de bairro
					if (!bairros.contains(leitura.getBairro())){
						bairros.add(leitura.getBairro());
					}
					// }
				}
			}
		}

		if (contadorPresenca >= 2) {
			emitirAlerta(bairros);
		}
	}

	public static int contaDias(Date dataInicial, Date dataFinal) {

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		df.setLenient(false);
		// Date dataInicio = df.parse(dataInicial);
		// Date dataFim = df.parse(dataFinal);

		long dt = (dataFinal.getTime() - dataInicial.getTime()) + 3600000;
		Long diasCorridosAnoLong = (dt / 86400000L);
		Integer diasDecorridosInt = Integer.valueOf(diasCorridosAnoLong.toString());

		/*
		 * Para Numeros Formatados com 3 digitos (0 a Esquerda) NumberFormat nf =
		 * NumberFormat.getInstance(); nf.setMinimumIntegerDigits(3);
		 * nf.setMaximumIntegerDigits(3); String diasDecorridos =
		 * (nf.format(diasDecorridosInt));
		 */
		String diasDecorridos = String.valueOf(diasDecorridosInt); // Sem numeros formatados;

		return Integer.parseInt(diasDecorridos);
	}

	public static void emitirAlerta(List<String> bairros) throws Exception {

		for (Sistema sistema : sistemas) {
			System.out.println(sistema.emitirAlerta(bairros));
		}
	}

	public static void setSistemas() {
		Sistema secSaude = new Sistema();
		secSaude.setNomeSistema("Sistema da Secretaria de Saúde");
		secSaude.setIP("192.168.0.101");// rede da minha casa
		//secSaude.setIP("172.26.192.215"); // rede de acesso da rede ICMC 802
		secSaude.setPorta("9002");
		secSaude.setServico("service/receberAlertaSaude");
		sistemas.add(secSaude);

		Sistema secServPublicos = new Sistema();
		secServPublicos.setNomeSistema("Sistema da Secretaria de Serviços Publicos");
		secServPublicos.setIP("192.168.0.101");// rede da minha casa
		//secServPublicos.setIP("172.26.192.215"); // rede de acesso da rede ICMC 802
		secServPublicos.setPorta("9003");
		secServPublicos.setServico("service/receberAlertaSecServPublicos");
		sistemas.add(secServPublicos);
	}

	/*
	 *  Requisição do tipo GET para requisitar os dados do Banco de dados,
	 *  ou seja, captar os dados dos sensores por meio de requisição de serviço
	 */
	public static String sendGet(String servico, String ip, String porta) throws Exception {

		String USER_AGENT = "Mozilla/5.0";

		// acesso ao serviço de acordo com os parâmetros recebidos
		String url = "http://" + ip + ":" + porta + "/" + servico;

		// acesso a maquina pela própria máquina (servidor local)
		// String url = "http://localhost:9001/service/" + servico;

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

		// print result
		return response.toString();
	}

}
