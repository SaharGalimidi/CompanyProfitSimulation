package CompanyProfitSimulation.listeners;

import java.io.IOException;
import java.util.ArrayList;

import CompanyProfitSimulation.Model.Department;
import CompanyProfitSimulation.Model.Employee;
import CompanyProfitSimulation.Model.Role;
import CompanyProfitSimulation.Model.Employee.ePreference;

public interface EventUIListeners {

	void modelUpdateSetCompanyName(String companyName);

	void modelUpdateNewDepartment(String departmentName, boolean workersSynchronized, int syncedStartingHour);

	void modelUpdateNewRole(Department thisDeparment, String nameOfRole, boolean canWorkFromHome,
			boolean workersSynchronized, int syncedStartingHour);

	void modelUpdateNewEmployee(int thisDeparment, int thisRole, ePreference pref, String name, String id, int timePref,
			int baseCash);

	void modelUpdateNewEmployee(int thisDeparment, int thisRole, ePreference pref, String name, String id, int timePref,
			float cashPerHour);

	void modelUpdateNewEmployee(int thisDeparment, int thisRole, ePreference pref, String name, String id, int timePref,
			int baseCash, float percentToSalary, int numOfSales);

	ArrayList<Department> getAllDepartments();

	ArrayList<Role> getAllRolesInDepartment(Department thisDepartment);

	ArrayList<Employee> getAllEmployeesInRole(int indexDeparment, int indexRole);

	ArrayList<String> departmnetsOptions();

	ArrayList<String> roleOptions(int departmentIndex);

	ArrayList<String> employeeOptions(int departmentIndex, int roleIndex);
	
	ArrayList<String> employeeId();

	String showCompanyInformation();

	void changeWorkMethoodInRole(int departmentIndex, int roleIndex, boolean isWrokersSync, int startingHour,
			boolean canWorkFromHome);

	void changeWorkMethoodInDepartment(int departmentIndex, boolean isWrokersSync, int startingHour);

	float checkEfficiencyAfter();

	float checkEfficiencyAfter(Department checkDepartment);

	float checkEfficiencyAfter(Role checkRole, Employee checkEmployee);

	float checkEfficiencyBefore();

	float checkEfficiencyBefore(Department checkDepartment);

	float checkEfficiencyBefore(Role checkRole, Employee checkEmployee);

	void fireLoadFile() throws ClassNotFoundException, IOException;

	void fireFileWriter();

}
