package application;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;
import sqljdbc1.DB;

public class App {

	public static void main(String[] args) {
		PreparedStatement st = null;
		LocalDate today = LocalDate.now();	
		Date sqlDate = Date.valueOf(today);
		ResultSet rs = null;
		DB.closeConn();
		SellerDao s = DaoFactory.createSellerDao();
		
		Department d = new Department(3,null);
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", sqlDate, 4000.0, d);	
	//	s.insert(newSeller);
		List<Seller> ss = s.findAll();
		ss.stream().forEach(System.out::println);
		/*try {
			Connection c = DB.getConn();
			st = c.prepareStatement("UPDATE seller SET BaseSalary = BaseSalary + ? WHERE (DepartmentId = ?)",Statement.RETURN_GENERATED_KEYS);
			
			st.setDouble(1,12.0);
			st.setInt(2, 2);
			
			int rowsAffected=st.executeUpdate();
			if (rowsAffected > 0) {
				rs = st.getGeneratedKeys();
				while (rs.next()) {
					int id = rs.getInt(1);
					System.out.println("Done! id = "+id);
				}
			}
			else {
				System.out.println(rowsAffected+" linhas afetadas!");
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConn();
		}*/
	}

}
