package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entidades.Bairro;
import entidades.DadosSensor;
import entidades.Sensor;

public class ExecutaSensor {

	private static final java.lang.String PERSISTENCE_UNIT_NAME = "projeto_dengue";
	private static EntityManagerFactory factory;

	// util
	private static BufferedReader bufferedReader;
	private static String input = null;
	private static int number = 0;

	public static void main(String args[]) throws Exception {

		// Pra usar o banco de dados
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		int op = 0;
		ExecutaSensor es = new ExecutaSensor();

		// abre transação com o banco de dados
		EntityTransaction t = em.getTransaction();

		while (true) {

			op = es.leOpcao();
			
			switch (op) {

			case 1:

				Bairro b = new Bairro();

				System.out.println("Informe o nome do bairro: ");
				bufferedReader = new BufferedReader(new InputStreamReader(System.in));
				String nome = bufferedReader.readLine();

				b.setNome(nome);

				// abriu transaÃ§Ã£o no banco
				t.begin();

				// agora insere no banco de dados depois comita e fecha o banco
				em.persist(b);

				// comitou transaÃ§Ã£o no banco
				t.commit();

				System.out.println("************ Bairro criado.**************");
				break;

			case 2:
				// Criando o novo sensor
				Sensor s = new Sensor();
				
				// Precisa recuperar as infos sobre o bairro no banco de dados
				b = new Bairro();
				
				// resgata b no BD com base na escolha do usuario //abre transação no BD
				b = resgataObjeto(b, em);			
				
				// abriu a transação no banco de dados
				t.begin();
				
				// relaciona b ao sensor
				s.setBairro(b);
				
				System.out.println("Informa o nome do sensor: ");
				bufferedReader = new BufferedReader(new InputStreamReader(System.in));
				String nomeSensor = bufferedReader.readLine();
				s.setNome(nomeSensor);

				System.out.println("Informe a latitude do sensor: ");
				bufferedReader = new BufferedReader(new InputStreamReader(System.in));
				String latitude = bufferedReader.readLine();
				s.setLatitude(latitude);
                                
                System.out.println("Informe as longitude do sensor: ");
				bufferedReader = new BufferedReader(new InputStreamReader(System.in));
				String longitude = bufferedReader.readLine();                                
                s.setLongitude(longitude);

				// Bairro pronto, agora insere no banco de dados depois comita e fecha o banco
				em.persist(s);

				// comitou transação no banco
				t.commit();

				System.out.println("\n************ Sensor criado.**************\n");
				break;

			case 3:
				DadosSensor d = new DadosSensor();

				// hora em que a operação foi realizada
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				d.setDataHora(dtf.format(LocalDateTime.now()));

				s = new Sensor();

				// abriu transaÃ§Ã£o no banco
				t.begin();

				// recupera sensor no banco de dados
				s = resgataObjeto(s, em);

				// atribui o sensor aos dados
				d.setSensor(s);

				System.out.println("Há presença do mosquito? ");
				Scanner scanner = new Scanner(System.in);
				String entrada = scanner.next();

				if (entrada.equalsIgnoreCase("s") || entrada.equalsIgnoreCase("sim")) {
					d.setPresencaMosquito(true);
				} else {
					d.setPresencaMosquito(false);
				}

				// Bairro criado, agora insere no banco de dados depois comita e fecha o banco
				em.persist(d);

				// comitou transaÃ§Ã£o no banco
				t.commit();

				System.out.println("\n************ Dados criados.**************\n");
				break;

			case 4:
				//System.out.println("\n## Bairros cadastrados: ##\n");
				b = new Bairro();
				b = lerObjetos(b, em);
				
			break;
			
			case 5:
				//System.out.println("\n## Sensores cadastrados: ##\n");
				s = new Sensor();
				s = lerObjetos(s, em);
				
			break;
			
			case 0:
				// encerra conexÃ£o com o banco de dados
				em.close();

				System.out.println("Programa encerrado!");
				System.exit(0);

			}

			System.out.println("\nSelecione a proxima operação:\n");
		}

	}

	/**
	 * Lista dados do banco de dados com base no parametro especificado
	 * 
	 * @param <T>
	 * 
	 * @param <T>
	 * 
	 * @return inteiro referente ao objeto que deseja-se restaurar.
	 * @throws IOException
	 */
	private static <T> T resgataObjeto(T objeto, EntityManager em) throws IOException {

		input = null;
		number = -1;

		if (objeto instanceof Sensor) {
			System.out.println("\nListando sensores:\n");

			Query query = em.createNativeQuery("SELECT * from sensor");
			List<Object[]> list = query.getResultList();
			List<Sensor> result = new ArrayList<>(list.size());
			Sensor sensor;

			for (Object[] o : list) {
				sensor = new Sensor();

				// restaura o obj sensor
				sensor.setId((Integer) o[0]);
				sensor.setLatitude((String) o[1]);
                sensor.setLongitude((String) o[2]);
                sensor.setNome((String) o[3]);

				int id = (Integer) o[4];
				Bairro b = em.find(Bairro.class, id);
				sensor.setBairro(b);

				result.add(sensor);
			}

			for (Sensor s : result){				
				System.out.println("Id: " + s.getId());
				System.out.println("Nome: " + s.getNome());
				System.out.println("Latitude: " + s.getLatitude());
                System.out.println("Longitude: " + s.getLongitude());
                System.out.println("Bairro: "+ s.getBairro().getNome());
			}

			System.out.println("Informe o ID do sensor desejado:\n");
			input = bufferedReader.readLine();
			number = Integer.parseInt(input);

			return (T) em.find(Sensor.class, number);

		}

		if (objeto instanceof Bairro) {

			System.out.println("\nListando bairros:\n");

			Query query = em.createNativeQuery("SELECT * from bairro");

			List<Object[]> list = query.getResultList();
			List<Bairro> result = new ArrayList<>(list.size());
			Bairro bairro;

			for (Object[] o : list) {
				bairro = new Bairro();

				// restaura o obj bairro
				bairro.setId((Integer) o[0]);
				bairro.setNome((String) o[1]);

				result.add(bairro);
			}

			for (Bairro b : result) {
				System.out.println("Id: " + b.getId());
				System.out.println("Nome: " + b.getNome());
			}

			System.out.println("\nInforme o ID do bairro desejado:\n");
			input = bufferedReader.readLine();
			number = Integer.parseInt(input);

			return (T) em.find(Bairro.class, number);

		}

		return null;
	}

	/**
	 * Método que faz apenas a leitura dos sensores e bairros para
	 * mostrar na tela
	 * @param objeto
	 * @param em
	 * @return
	 * @throws IOException
	 */
	private static <T> T lerObjetos(T objeto, EntityManager em) throws IOException {

		input = null;
		number = -1;

		if (objeto instanceof Sensor) {
			System.out.println("\nSensores Cadastrados:\n");

			Query query = em.createNativeQuery("SELECT * from sensor");
			List<Object[]> list = query.getResultList();
			List<Sensor> result = new ArrayList<>(list.size());
			Sensor sensor;

			for (Object[] o : list) {
				sensor = new Sensor();

				// restaura o obj sensor
				sensor.setId((Integer) o[0]);
				sensor.setLatitude((String) o[1]);
                sensor.setLongitude((String) o[2]);
                sensor.setNome((String) o[3]);

				int id = (Integer) o[4];
				Bairro b = em.find(Bairro.class, id);
				sensor.setBairro(b);

				result.add(sensor);
			}

			for (Sensor s : result){				
				System.out.println("Id: " + s.getId());
				System.out.println("Nome: " + s.getNome());
				System.out.println("Latitude: " + s.getLatitude());
                System.out.println("Longitude: " + s.getLongitude());
                System.out.println("Bairro: "+ s.getBairro().getNome());
                System.out.println("\n");
			}
			return (T) em.find(Sensor.class, number);

		}

		if (objeto instanceof Bairro) {

			System.out.println("\nBairros cadastrados:\n");

			Query query = em.createNativeQuery("SELECT * from bairro");

			List<Object[]> list = query.getResultList();
			List<Bairro> result = new ArrayList<>(list.size());
			Bairro bairro;

			for (Object[] o : list) {
				bairro = new Bairro();

				// restaura o obj bairro
				bairro.setId((Integer) o[0]);
				bairro.setNome((String) o[1]);

				result.add(bairro);
			}

			for (Bairro b : result) {
				System.out.println("Id: " + b.getId());
				System.out.println("Nome: " + b.getNome());
			}

			return (T) em.find(Bairro.class, number);

		}

		return null;
	}
	
	
	/**
	 * Mostra o menu da opcao desejada. Se um valor invalido for digitado, pede
	 * ao usuario que digite novamente
	 * 
	 * @return o numero da opcao escolhida
	 */
	private int leOpcao() {

		System.out.println("1) Inserir bairro\n"
                         + "2) Inserir sensor\n"
                         + "3) Inserir dados\n"
                         + "4) Visualizar bairros cadastrados\n"
                         + "5) Visualizar sensores cadastrados\n"
                         + "0) Sair");

		input = null;
		number = 0;

		while (true) {
			System.out.println("Digite a opção desejada:");
			try {
				bufferedReader = new BufferedReader(new InputStreamReader(System.in));
				input = bufferedReader.readLine();
				number = Integer.parseInt(input);
				if (number >= 0 && number <= 5)
					return number;
			} catch (Exception e){
				System.out.println("Número inválido!");
			}
		}
	}

}
