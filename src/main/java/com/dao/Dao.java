package com.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.modelo.Partido;
import com.modelo.Politico;
import com.ui.MainUI;

public class Dao {
	public static void main(String[] args) {
		System.out.println(getPartidoBySiglaOrNew("PSB"));
	}

	public static Politico getPoliticoByCPFOrNew(String cpf) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(MainUI.PERSISTENCE_UNIT);
		EntityManager em = factory.createEntityManager();
		Query q = em.createQuery("SELECT p FROM Politico p WHERE p.cpf = :cpf");
		q.setParameter("cpf", cpf);
		Politico p = null;
		try {
			p = (Politico) q.getSingleResult();// q.getResultList();//
		} catch (NoResultException e) {
			return new Politico();
		}
		return p;
	}

	public static Partido getPartidoBySiglaOrNew(String sigla) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(MainUI.PERSISTENCE_UNIT);
		EntityManager em = factory.createEntityManager();
		Query q = em.createQuery("SELECT p FROM Partido p WHERE p.sigla = :sigla");
		q.setParameter("sigla", sigla);
		Partido p = null;
		try {
			p = (Partido) q.getSingleResult();// q.getResultList();//
		} catch (NoResultException e) {
			return new Partido();
		}
		return p;
	}

	public static void salvaPolitico(Politico p) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(MainUI.PERSISTENCE_UNIT);
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(p);
		em.getTransaction().commit();
	}
}
