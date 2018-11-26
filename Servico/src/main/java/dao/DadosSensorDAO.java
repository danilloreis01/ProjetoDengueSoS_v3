package dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.DadosSensor;
import entidades.Sensor;

/**
 * Classe DAO que faz acesso aos dados da entidade DadosSensor
 * 
 * @author Danillo
 */

public class DadosSensorDAO extends DAOManager {

	public DadosSensorDAO(EntityManager entityManager) {
		super(entityManager);
	}

	public void salvarDadosSensores(DadosSensor ds) {
		super.beginTransaction();

		super.getEntityManager().persist(ds);

		super.commitTransaction();
	}

	/*
	 * Método que retorna uma lista de todos os dados que os sensores geraram
	 */
	public List<DadosSensor> obterDadosSensor() {

		// antes da consulta
		super.beginTransaction();

		Query query = super.getEntityManager().createNativeQuery("SELECT * FROM dados_sensor", DadosSensor.class);

		// depois da consulta
		super.commitTransaction();

		return query.getResultList();
	}

}
