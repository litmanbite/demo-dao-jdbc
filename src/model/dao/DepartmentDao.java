package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {
	void insert(Department d);
	void update(Department d);
	void deleteById(Integer n);
	Department findById(Integer n);
	List<Department> findAll();
			
}
