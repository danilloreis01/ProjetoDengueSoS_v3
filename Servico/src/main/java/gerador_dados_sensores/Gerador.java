package gerador_dados_sensores;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dao.BairroDAO;

//import javax.persistence.EntityManager;

import dao.DadosSensorDAO;
import dao.SensorDAO;
import entidades.Bairro;
import entidades.DadosSensor;
import entidades.Sensor;

public class Gerador {

	private static final java.lang.String PERSISTENCE_UNIT_NAME = "projeto_dengue";
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	private static EntityManager persistencia = factory.createEntityManager();

	public static void main(String[] args) throws ParseException, InterruptedException {

		int quantSensores = 8;
		
		while (true) {

			for (int i = 0; i < quantSensores; i++) {

				// gera dados e salva no banco
				geraDados(i);

			}
			Thread.sleep(7200000); // 2 horas
			//Thread.sleep(28800000); // 8 horas
			//Thread.sleep(10000); // 10 segundos
		}

	}

	public static void geraDados(int i) {

		DadosSensor d = new DadosSensor();

		// hora em que a operação foi realizada
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		d.setDataHora(dtf.format(LocalDateTime.now()));

		// pegar lista de sensores
		SensorDAO sensores = new SensorDAO(persistencia);

		List<Object[]> lista = new ArrayList();
		lista = sensores.obterSensores();

		List<Sensor> listaSensores = new ArrayList<Sensor>(); 

		BairroDAO bd = new BairroDAO(persistencia);
		
		for (Object[] o : lista) {
			
			Sensor sensor = new Sensor();

			// restaura o obj sensor
			sensor.setId((Integer) o[0]);
			sensor.setLatitude((String) o[1]);
			sensor.setLongitude((String) o[2]);
			sensor.setNome((String) o[3]);

			int id = (Integer) o[4];
			Bairro b = bd.obterBairro(id);
			
			sensor.setBairro(b);

			listaSensores.add(sensor);
		}
		
		Sensor s = new Sensor();

		s = listaSensores.get(i);

		// atribui o sensor aos dados
		d.setSensor(s);

		// Gerar presença (true ou false) automatico
		Random randon = new Random(); // objeto randomico
		d.setPresencaMosquito(randon.nextBoolean());

		// salvar dados no banco
		DadosSensorDAO ds = new DadosSensorDAO(persistencia);
		ds.salvarDadosSensores(d);

	}
}