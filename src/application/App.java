package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class App {

	public static void main(String[] args) {
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		Scanner sc = new Scanner(System.in);
;		/*System.out.println("=== TEST 1: findById =======");
		Department dep = departmentDao.findById(1);
		System.out.println(dep);
		
		System.out.println("\n=== TEST 2: findAll =======");
		List<Department> list = departmentDao.findAll();
		for (Department d : list) {
			System.out.println(d);
		}

		System.out.println("\n=== TEST 3: insert =======");
		Department newDepartment = new Department(null, "Music");
		departmentDao.insert(newDepartment);
		System.out.println("Inserted! New id: " + newDepartment.getId());*/

		System.out.println("\n=== TEST 4: update =======");
		Department dep2 = departmentDao.findById(1);
		dep2.setName("Food");
		departmentDao.update(dep2);
		System.out.println("Update completed");
		
		System.out.println("\n=== TEST 5: delete =======");
		System.out.print("Enter id for delete test: ");
		int id = sc.nextInt();
		departmentDao.deleteById(id);
		System.out.println("Delete completed");
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
