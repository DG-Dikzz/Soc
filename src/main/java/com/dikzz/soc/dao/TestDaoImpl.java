package com.dikzz.soc.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.dikzz.soc.model.TestTable;

public class TestDaoImpl implements TestDao {

	@PersistenceContext(unitName="SocUnit")
	private EntityManager entityManager;

	@Override
	public TestTable getById(Integer id) {
		TestTable table = entityManager.find(TestTable.class, id);
		return table;
	}

	@Override
	@Transactional
	public void addTestDao(TestTable arg) {
		entityManager.persist(arg);
	}
}