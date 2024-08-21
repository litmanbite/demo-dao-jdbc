package sqljdbc1;

public class DbExcep extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DbExcep(String s) {
		super(s);
	}
}
