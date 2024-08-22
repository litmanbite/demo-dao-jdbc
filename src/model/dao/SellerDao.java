package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {
	void insert(Seller d);
	void update(Seller d);
	void deleteById(Integer n);
	Seller findById(Integer n);
	List<Seller> findAll();
	List<Seller> findByDep(Department d);
}
