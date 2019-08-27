package craftchalangeone;

public class Company {
	
	/**
	 *  Model of a company
	 */
	private String name; //Company name
	private int noOfEmploye; //Number of employees

	/**
	 * Constructor 
	 * @param name - company name
	 * @param no_employe - number of employees
	 */
	public Company(String name, int noOfEmploye) {
		this.name = name;
		this.noOfEmploye = noOfEmploye;
	}
	
	/**
	 * Add an employee
	 */
	public void addEmploye () {
		this.noOfEmploye += 1;
	}
	
	
	/**
	 * Get company name
	 * @return name - company name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Get number employees of a company
	 * @return no_employe - Number of employees
	 */
	public int getNoOfEmployees() {
		return this.noOfEmploye;
	}
	
}
