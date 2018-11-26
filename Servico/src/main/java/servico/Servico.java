package servico;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dao.BairroDAO;
import dao.DadosSensorDAO;
import dao.SensorDAO;
import entidades.Bairro;
import entidades.DadosSensor;
import entidades.Sensor;

/**
 * Classe que implementa os m�todos (servi�os) que foram definidos na interface
 * *InterfaceServico*
 * 
 * @author Danillo
 */

public class Servico implements InterfaceServico {
	
	private static final String PERSISTENCE_UNIT_NAME = "projeto_dengue";
	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	EntityManager persistencia = factory.createEntityManager();

	private final SensorDAO persistenciaSensor;
	private final BairroDAO persistenciaBairro;
	private final DadosSensorDAO persistenciaDadosSensor;
	
	public Servico() {
		persistenciaSensor        = new SensorDAO(persistencia);
		persistenciaBairro        = new BairroDAO(persistencia);
		persistenciaDadosSensor   = new DadosSensorDAO(persistencia);
	}

	/**
	 * Servi�o que faz o envio dos dados coletados pelos sensores para o BD que ser�
	 * disponibilizado como servi�os
	 * 
	 * @param s
	 */
	@Override
	public void enviarDados(Sensor s) {
		persistenciaSensor.salvarDadosSensor(s);
	}
	
	
	/**
	 * Servi�o que obtem os dados que os sensores geraram em determinado tempo.
	 * 
	 * @param sensor
	 * @return lista de dados que todos os sensores forneceram.
	 */
	@Override
	public List obterDadosSensores() {
            return persistenciaDadosSensor.obterDadosSensor();
	}

	/**
	 * Servi�o que obt�m os bairros cadastrados da cidade de S�o Carlos/SP
	 * 
	 * @param bairro
	 * @return lista de bairros cadastrados na Base de DadosSensor
	 */
	@Override
	public Bairro obterDadosBairro(int bairro) {
            return persistenciaBairro.obterBairro(bairro);
	}
	
	/**
	 * Servi�o que lista todos os bairros cadastrados no BD
	 */
	@Override
	public List obterTodosBairro() {		
		return persistenciaBairro.obterTodosBairros();
	}

	
	/**
	 * Servi�o que lista todas as informa��es de todos os sensores
	 * cadastrados no BD
	 */
	@Override
	public List obterInformacoesSensores() {		
            return persistenciaSensor.obterSensores();
	}

	
}