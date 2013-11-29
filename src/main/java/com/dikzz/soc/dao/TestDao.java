package com.dikzz.soc.dao;

import com.dikzz.soc.model.TestTable;

public interface TestDao {
	TestTable getById(Integer id);
	void addTestDao(TestTable arg);
}
