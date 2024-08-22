package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;
import sqljdbc1.DB;
import sqljdbc1.DbExcep;

public class SellerDaoJDBC implements SellerDao{
	private Connection c;
	public SellerDaoJDBC(Connection c) {
		this.c=c;
	}
	@Override
	public void insert(Seller d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer n) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = c.prepareStatement("SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?");
			st.setInt(1, n);
			rs = st.executeQuery();
			if (rs.next()) {	
				Department dd = makeD(rs);
				return makeS(dd,rs);				
			}
			return null;
		}
		catch(SQLException e ) {
			throw new DbExcep(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	@Override
	public List<Seller> findByDep(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = c.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");
			
			st.setInt(1, department.getId());
			
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null) {
					dep = makeD(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller obj = makeS(dep, rs);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbExcep(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private Seller makeS(Department dep,ResultSet rs) throws SQLException {
		Seller s = new Seller();

		s.setId(rs.getInt("Id"));
		s.setName(rs.getString("Name"));
		s.setEmail(rs.getString("Email"));
		s.setBaseSalary(rs.getDouble("BaseSalary"));
		s.setBirthDate(rs.getDate("BirthDate"));
		s.setDepartment(dep);
	
		return s;
	}
	private Department makeD(ResultSet rs)throws SQLException  {
		Department d = new Department ();
		
			d.setName(rs.getString("DepName"));
			d.setId(rs.getInt("DepartmentId"));
		
		return d;
	}
	
}
