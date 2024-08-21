package model.dao;

import model.dao.impl.SellerDaoJDBC;
import sqljdbc1.DB;

public class DaoFactory {
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConn());
	}
}
