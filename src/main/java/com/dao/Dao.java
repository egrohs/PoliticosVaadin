package com.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;

import com.modelo.Partido;
import com.modelo.Politico;
import com.modelo.Url;
import com.ui.MainUI;

public class Dao {
	public static void main(String[] args) {
		System.out.println(getPartidoBySigla("PSB"));
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
		} finally {
			factory.close();
		}
		return p;
	}

	public static Url getUrlByUrlOrNew(String url) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(MainUI.PERSISTENCE_UNIT);
		EntityManager em = factory.createEntityManager();
		Query q = em.createQuery("SELECT p FROM Url p WHERE p.url = :url");
		q.setParameter("url", url);
		Url p = null;
		try {
			p = (Url) q.getSingleResult();// q.getResultList();//
		} catch (NoResultException e) {
			return new Url();
		} finally {
			factory.close();
		}
		return p;
	}

	public static Partido getPartidoBySigla(String sigla) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(MainUI.PERSISTENCE_UNIT);
		EntityManager em = factory.createEntityManager();
		Query q = em.createQuery("SELECT p FROM Partido p WHERE p.sigla = :sigla");
		q.setParameter("sigla", sigla);
		Partido p = null;
		try {
			p = (Partido) q.getSingleResult();// q.getResultList();//
		} catch (NoResultException e) {
			return null;
		} finally {
			factory.close();
		}
		return p;
	}

	public static Partido getPartidoByNome(String nome) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(MainUI.PERSISTENCE_UNIT);
		EntityManager em = factory.createEntityManager();
		Query q = em.createQuery("SELECT p FROM Partido p WHERE p.nome = :nome");
		q.setParameter("nome", nome);
		Partido p = null;
		try {
			p = (Partido) q.getSingleResult();// q.getResultList();//
		} catch (NoResultException e) {
			return null;
		} finally {
			factory.close();
		}
		return p;
	}

	public static void atualizaPoliticos(List<Politico> politicos) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(MainUI.PERSISTENCE_UNIT);
		EntityManager em = factory.createEntityManager();
		for (Politico politico : politicos) {
			atualizaPolitico(politico);
		}
	}

	public static void atualizaPolitico(Politico politico) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(MainUI.PERSISTENCE_UNIT);
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		// em.persist(politico);
		em.merge(politico);
		try {
			em.getTransaction().commit();
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
		}
	}
}
