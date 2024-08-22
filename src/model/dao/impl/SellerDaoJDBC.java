package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	public void insert(Seller obj) {
		PreparedStatement st = null;
		try {
			st = c.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			
			int r = st.executeUpdate();
			
			if (r>0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			}
			else 
				throw new DbExcep("ERROR! no additions were made");
		}
		catch(SQLException e ) {
			throw new DbExcep(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			
		}
		
	}

	@Override
	public void update(Seller obj) {
		PreparedStatement st = null;
		try {
			st = c.prepareStatement(
					"UPDATE seller SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?	WHERE Id = ?");
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());
			
			st.executeUpdate();
		}
		catch(SQLException e ) {
			throw new DbExcep(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			
		}		
	}

	@Override
	public void deleteById(Integer n) {
		PreparedStatement st = null;
	
		try {
			st = c.prepareStatement("DELETE FROM seller \r\n"
					+ "WHERE Id = ?");
			st.setInt(1, n);
			st.executeUpdate();
		}
		catch(SQLException e){
			throw new DbExcep(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
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
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = c.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name");
			rs = st.executeQuery();
			List<Seller> list = new ArrayList<>();
			while (rs.next()) {
				Department dep =  makeD(rs);
				list.add(makeS(dep, rs));
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
