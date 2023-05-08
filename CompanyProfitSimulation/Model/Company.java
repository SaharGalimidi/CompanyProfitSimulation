package CompanyProfitSimulation.Model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import CompanyProfitSimulation.listeners.EventListeners;

@SuppressWarnings("serial")
public class Company implements efficiencyCalc, Serializable {

	private String companyName;
	private ArrayList<Department> allDepartments;
	private ArrayList<EventListeners> allListeners;
	private ArrayList<Employee> allEmployees;

	public Company() {
		allEmployees = new ArrayList<Employee>();
		allDepartments = new ArrayList<Department>();
		allListeners = new ArrayList<EventListeners>();
	}

	public boolean setCompanyName(String newName) {
		this.companyName = newName;
		return true;
	}

	public void registerListener(EventListeners newListener) {
		allListeners.add(newListener);
	}

	public boolean addNewDepartment(Department newDepartment) {
		for (int counter = 0; counter < allDepartments.size(); counter++) {
			if (allDepartments.get(counter).equals(newDepartment)) {
				sendErrorToView("Department name already existed");
				return false;
			}
		}
		return allDepartments.add(newDepartment);
	}

	public boolean addNewRoleToDepartment(Role newRole, Department addToThisDepartment) {

		int departmentIndex = allDepartments.indexOf(addToThisDepartment);

		if (allDepartments.get(departmentIndex).isWorkersSynchronized())
			newRole.sync(true, allDepartments.get(departmentIndex).getSyncedStartingHour());

		allDepartments.get(departmentIndex).addRole(newRole);

		return true;
	}

	public boolean addNewEmployee(Employee newEmployee, int thisRole, int thisDepartment) {
		for (int i = 0; i < allEmployees.size(); i++) {
			if (allEmployees.get(i).equals(newEmployee)) {
				return false;
			}
		}
		allEmployees.add(newEmployee);
		return allDepartments.get(thisDepartment).getRolesInDepartments().get(thisRole).addEmployees(newEmployee);
	}

	public void changeWorkStyleInRole(int departmentIndex, int roleIndex, boolean isWrokersSync, int startingHour,
			boolean canWorkFromHome) {

		if (!(allDepartments.get(departmentIndex).isWorkersSynchronized()))
			allDepartments.get(departmentIndex).getRolesInDepartments().get(roleIndex).sync(isWrokersSync,
					startingHour);

		allDepartments.get(departmentIndex).getRolesInDepartments().get(roleIndex).setHomeWorkOption(canWorkFromHome);
	}

	public void changeWorkStyleInDepartment(int index, boolean isWrokersSync, int startingHour) {
		allDepartments.get(index).sync(isWrokersSync, startingHour);
	}

	@Override
	public float checkEfficiencyAfterExperiment() {
		float efficiencyPerHour = 0;

		for (int counter = 0; counter < allDepartments.size(); counter++)
			efficiencyPerHour += allDepartments.get(counter).checkEfficiencyAfterExperiment();

		return efficiencyPerHour;

	}

	public float checkEfficiencyAfterExperiment(Department checkDepartment) {
		return checkDepartment.checkEfficiencyAfterExperiment();
	}

	public float checkEfficiencyAfterExperiment(Role checkRole, Employee checkEmployee) {
		return checkRole.checkEfficiencyPerWorkerAfterExperiment(checkEmployee);
	}

	@Override
	public float checkEfficiencyBeforeExperiment() {
		float sum = 0;

		for (int counter = 0; counter < allDepartments.size(); counter++)
			sum += allDepartments.get(counter).checkEfficiencyBeforeExperiment();

		return sum;
	}

	public float checkEfficiencyBeforeExperiment(Department checkDepartment) {
		return checkDepartment.checkEfficiencyBeforeExperiment();
	}

	public float checkEfficiencyBeforeExperiment(Role checkRole, Employee checkEmployee) {
		return checkRole.checkEfficiencyPerWorkerBeforeExperiment(checkEmployee);
	}

	public ArrayList<Department> getAllDepartments() {
		return allDepartments;
	}

	public ArrayList<Role> getAllRolesInDepartment(Department thisDepartment) {
		return thisDepartment.getRolesInDepartments();
	}

	public ArrayList<Employee> getAllEmployeesInRole(int indexDeparment, int indexRole) {
		return allDepartments.get(indexDeparment).getRolesInDepartments().get(indexRole).getEmployeesInRole();
	}

	public ArrayList<String> departmentOptions() {
		ArrayList<String> options = new ArrayList<String>();

		for (int counter = 0; counter < allDepartments.size(); counter++)
			options.add(allDepartments.get(counter).getDepartmentName());

		return options;
	}

	public ArrayList<String> roleOptions(int departmentIndex) {
		ArrayList<String> options = new ArrayList<String>();

		for (int counter = 0; counter < allDepartments.get(departmentIndex).getRolesInDepartments().size(); counter++)
			options.add(allDepartments.get(departmentIndex).getRolesInDepartments().get(counter).getNameOfRole());

		return options;
	}

	public ArrayList<String> employeesOptions(int departmentIndex, int roleIndex) {
		ArrayList<String> options = new ArrayList<String>();

		for (int counter = 0; counter < allDepartments.get(departmentIndex).getRolesInDepartments().get(roleIndex)
				.getEmployeesInRole().size(); counter++)
			options.add(allDepartments.get(departmentIndex).getRolesInDepartments().get(roleIndex).getEmployeesInRole()
					.get(counter).getName());

		return options;
	}
	
	public ArrayList<String> employeesId() {
		ArrayList<String> options = new ArrayList<String>();

		for (int counter = 0; counter < allEmployees.size(); counter++)
			options.add(allEmployees.get(counter).getId());

		return options;
	}

	public void fileWriter(Company newCompany) {
		try {
			ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("output.dat"));
			outFile.writeObject(newCompany);
			outFile.close();
		} catch (IOException e) {
		}
	}

	public Company fileLoader() throws ClassNotFoundException, IOException {
		Company newCompany;
		ObjectInputStream inFile;
		try {
			inFile = new ObjectInputStream(new FileInputStream("output.dat"));
			newCompany = (Company) inFile.readObject();
			inFile.close();

		} catch (IOException e) {
			sendErrorToView("File was not found!\nStart new simulation");
			return null;
		}
		return newCompany;
	}

	public void sendErrorToView(String error) {
		for (EventListeners l : allListeners) {
			l.passingToView(error);
		}
	}

	public String toString() {
		String res = "Company name: " + companyName
				+ "\nHere are our departments: \n--------------------------------------- \n";
		for (Department l : allDepartments) {
			res = res.concat(l.toString());
		}
		return res;
	}

}
