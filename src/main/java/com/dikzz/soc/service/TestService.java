package com.dikzz.soc.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikzz.soc.dao.TestDao;
import com.dikzz.soc.model.TestTable;

@Service
@Path("/test")
public class TestService {

	@Autowired
	private TestDao testDao;

	@GET
	@Path("/get/{id}")
	public Response getValue(@PathParam("id") String id) {
		return Response
				.status(200)
				.entity("This: "
						+ testDao.getById(Integer.parseInt(id)).getName())
				.build();
	}
	
	@GET
	@Path("/add/{id}/{name}")
	public String addValue(@PathParam("id") String id, @PathParam("name") String name) {
		TestTable in = new TestTable();
		in.setId(Integer.parseInt(id));
		in.setName(name);
		testDao.addTestDao(in);
		return "Done";
	}
	
	@GET
	@Path("/all")
	public String getAll() {
		List<TestTable> all = testDao.getAll();
		StringBuilder result = new StringBuilder();
		for(TestTable testTable : all) {
			result.append(testTable.toString()).append("\n");
		}
		return result.toString();
	}
}
