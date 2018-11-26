package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.Bairro;

/**
 * Classe DAO que faz o acesso aos dados da tabela Bairro
 * 
 * @author Danillo
 */

public class BairroDAO extends DAOManager {

	
	public BairroDAO(EntityManager entityManager) {
		super(entityManager);
	}

	
	/**
	 * Implementação do metodo que retorna um bairro passando o ID
	 * 
	 * @param idBairro
	 * @return
	 */
	public Bairro obterBairro(int idBairro) {
		Bairro b = new Bairro();
		//antes da consulta
		super.beginTransaction();

		b = super.getEntityManager().find(Bairro.class, idBairro);
		
//		Query query = super.getEntityManager().createNativeQuery("SELECT * from bairro WHERE id = ?", Bairro.class)
//				.setParameter(1, idBairro);
		
		//depois da consulta
		super.commitTransaction();

		return b;
	}
	
	
	/**
	 * Implementação do método que retorna todos os bairros cadastrados no BD
	 * @return
	 */
	public List<Bairro> obterTodosBairros() {
		
		//antes da consulta
		super.beginTransaction();

		Query query = super.getEntityManager().createNativeQuery("SELECT * from bairro");
		
		//depois da consulta
		super.commitTransaction();

		return query.getResultList();
	}

}
