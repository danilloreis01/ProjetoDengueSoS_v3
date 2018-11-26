package dao;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.DadosSensor;
import entidades.Sensor;

/**
 * Classe que faz acesso a entidade Sensor
 * 
 * @author Danillo
 */

public class SensorDAO extends DAOManager {

	public SensorDAO(EntityManager entityManager) {
		super(entityManager);
	}

	public void salvarDadosSensor(Sensor s) {
		super.beginTransaction();

		super.getEntityManager().persist(s);

		super.commitTransaction();
	}

	/**
	 * Método que faz o acesso a entidade Sensor e retorna uma lista de informações
	 * de sensores passando como parâmetro o ID do sensor
	 * 
	 * @param id
	 * @return lista de informações de sensores selecionados
	 */
	public List<Sensor> obterInformacoesID(int id) {

		// antes da consulta
		super.beginTransaction();

		Query infoSensor = super.getEntityManager().createNativeQuery("SELECT * from sensor WHERE id = ?", Sensor.class)
				.setParameter(1, id);

		// depois da consulta
		super.commitTransaction();

		return infoSensor.getResultList();
	}
	/*
	 * método que faz acesso a entidade sensor e retorna uma lista com todos
	 * os sensores cadastrados no BD
	 */
	public List obterSensores() {

		// antes da consulta
		super.beginTransaction();

		Query query = super.getEntityManager().createNativeQuery("SELECT * from sensor");

		// depois da consulta
		super.commitTransaction();
						
		return query.getResultList();
	}
}