package com.dikzz.soc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<TestTable> getAll() {
		Query quary = entityManager.createQuery("select tt from TestTable tt");
		return quary.getResultList();
	}

	@Override
	@Transactional
	public void addTestDao(TestTable arg) {
		entityManager.persist(arg);
	}
}