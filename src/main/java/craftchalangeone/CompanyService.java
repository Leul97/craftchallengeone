package craftchalangeone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class CompanyService {
	
	private List<Company> companyList = new ArrayList<>(); // List of companies
	private JSONArray employeList; //Variable to store employee list
	
	/**
	 * Updates company profile 
	 */
	public void updateCopmanyList () {

		JSONObject jObj;

		/* Iterate on every employee */
		for (int i = 0; i < this.employeList.size(); i++) {

			jObj =  (JSONObject)this.employeList.get(i); //Get employee description as a JSON object

			/* 
			 * We found a new employee. Add it to an existing company or
			 * create a new one if there is no such company on the list.
			 */
			if(companyList.isEmpty()) { 
				/* 
				 * Create a new company if the company list is empty
				 * using the employee information.  
				 * */
				Company comp = new Company((String)jObj.get("company"), 1);
				companyList.add(comp); //Add the company on the list. 
			}else {
				boolean companyFound = false;
				/*
				 * Search a company from the list that matches the employee 
				 * information. 
				 */
				for (Company parm : companyList) {
					if(parm.getName().equals(jObj.get("company").toString())) {
						/*
						 * We found a company, add the new employee to
						 * the company. 
						 */
						parm.addEmploye();
						companyFound = true;
						break;
					}
				}

				if(!companyFound) {
					/*
					 * The is no company the matches the employee info. So create
					 * a new company and add it to the list. 
					 */
					Company comp = new Company((String)jObj.get("company"), 1);
					companyList.add(comp);
				}
			}
		}
	}
	
	/**
	 * Update employee list 
	 * @param employeList - String form of JSON data 
	 */
	public String updateEmploye (String employeList) {
		
		JSONParser parser = new JSONParser(); //JSON Parser 
		
		try {
			
			Object obj = parser.parse(employeList); //Parse the JSON string
			this.employeList = (JSONArray)obj; //Assign to the global variable employee list
			
			updateCopmanyList (); //Update company info based on the received employee list
			
			return "ok";
			
		}catch (Exception e) {
			return e.getMessage();//Return the message of an exception 
		}
	}
	
	/**
	 * Returns list of companies with their information
	 * using as string.
	 * 
	 * @return List of companies 
	 */
	private String getCompanyListString () {
		
		StringBuilder result = new StringBuilder();
		JSONObject jObj;
		
		for (Company arg : this.companyList) {
			
			StringBuilder sub = new StringBuilder();
			sub.append(arg.getName());
			sub.append(":");
			
			for (int i = 0; i < this.employeList.size(); i++) {

				jObj =  (JSONObject)this.employeList.get(i); //Get employee description as a JSON object

				if(jObj.get("company").toString().equals(arg.getName())) {
					sub.append(jObj.get("id").toString() + "|" + jObj.get("name").toString() + "*");
				}
			}

			sub.setCharAt(sub.length() - 1, '~');
			result.append(sub.toString());
		}

		result.deleteCharAt(result.length()-1);

		return result.toString();
	}
	
	/**
	 * Returns list of companies with their information
	 * using JSON format.
	 * 
	 * @return List of companies 
	 */
	private List<HashMap<String,String>> getCompanyListJson () {
		
		List<HashMap<String,String>> jObj = new ArrayList<>();
		HashMap<String,String> var;
		
		for (Company arg : this.companyList) {
			var = new HashMap<String,String>();
			var.put("company", arg.getName());
			var.put("no_employees", String.valueOf(arg.getNoOfEmployees()));
			jObj.add(var);
		}
		
		return jObj;
	}
	
	/**
	 * Entry point for the mule flow
	 * @param employeList - String form of JSON data 
	 * @param returnType - Expected result type
	 * @return company information
	 */
	public Object getCompaniesInfo (String employeList, String returnType) {
		
		String str = this.updateEmploye(employeList);
		
		if (!str.equals("ok")) {
			
			return str;
			
		}else if (returnType.equals("string")) {
			
			return this.getCompanyListString();
			
		}else if (returnType.equals("json")) {
			
			return getCompanyListJson(); 
			
		}else {
			
			return "Unsupored return type";
		}
	}
}
