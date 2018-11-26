package dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

/**
 * 
 * @author Danillo
 *
 */

public abstract class DAOManager {

	private EntityManager entityManager;

	public DAOManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	

	protected EntityManager getEntityManager() {
		return entityManager;
	}



	protected void beginTransaction() {
		try {
			entityManager.getTransaction().begin();
		} catch (IllegalStateException e) {
			rollBackTransaction();
		}
	}

	protected void commitTransaction() {
		try {
			entityManager.getTransaction().commit();
		} catch (IllegalStateException | RollbackException e) {
			rollBackTransaction();
		}
	}

	protected void rollBackTransaction() {
		try {
			entityManager.getTransaction().rollback();
		} catch (IllegalStateException | PersistenceException e) {
			e.printStackTrace();
		}
	}
}
