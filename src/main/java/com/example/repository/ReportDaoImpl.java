package com.example.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;



public class ReportDaoImpl {

	private EntityManager em;
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em=em;
	}
	
	public void getall() {
		Query q=em.createNativeQuery("select * from candidate_profile");
		List<Object[]> r=q.getResultList();
	}
}
