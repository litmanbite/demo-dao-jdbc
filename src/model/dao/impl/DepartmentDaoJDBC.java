package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;
import sqljdbc1.DB;
import sqljdbc1.DbExcep;

public class DepartmentDaoJDBC implements DepartmentDao {
	private Connection c;
	public DepartmentDaoJDBC(Connection c) {
		this.c=c;
	}
	@Override
	public void insert(Department d) {
		PreparedStatement st = null;
		try {
			st = c.prepareStatement(
					"INSERT INTO department "
					+ "(Name) "
					+ "VALUES "
					+ "(?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, d.getName());
			
			
			int r = st.executeUpdate();
			
			if (r>0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					d.setId(id);
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
	public void update(Department obj) {
		PreparedStatement st = null;
		try {
			st = c.prepareStatement(
					"UPDATE department " +
							"SET Name = ? " +
							"WHERE Id = ?");

						st.setString(1, obj.getName());
						st.setInt(2, obj.getId());

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
			st = c.prepareStatement("DELETE FROM department WHERE Id = ?");
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
	public Department findById(Integer n) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = c.prepareStatement("SELECT * FROM department WHERE Id = ?");
			st.setInt(1, n);
			rs = st.executeQuery();
			if (rs.next()) {	
				Department dd = new Department(rs.getInt("Id"),rs.getString("Name"));
				return dd;				
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
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Department> list = new ArrayList<>();
		try {
			st = c.prepareStatement("SELECT * FROM department ORDER BY Name");
		
			rs = st.executeQuery();
			if (rs.next()) {	
				Department dd = new Department(rs.getInt("Id"),rs.getString("Name"));
				list.add(dd);
								
			}
			return list;
		}
		catch(SQLException e ) {
			throw new DbExcep(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
