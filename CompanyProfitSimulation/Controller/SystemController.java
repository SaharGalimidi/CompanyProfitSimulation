package CompanyProfitSimulation.Controller;

import java.io.IOException;
import java.util.ArrayList;

import CompanyProfitSimulation.Model.BaseWorker;
import CompanyProfitSimulation.Model.Company;
import CompanyProfitSimulation.Model.Department;
import CompanyProfitSimulation.Model.Employee;
import CompanyProfitSimulation.Model.Role;
import CompanyProfitSimulation.Model.SalesMan;
import CompanyProfitSimulation.Model.TimeWorker;
import CompanyProfitSimulation.Model.Employee.ePreference;
import CompanyProfitSimulation.View.createStartScene;
import CompanyProfitSimulation.listeners.EventListeners;
import CompanyProfitSimulation.listeners.EventUIListeners;


public class SystemController implements EventUIListeners, EventListeners {

	private createStartScene systemView;
	private Company systemModel;

	public SystemController(Company theModel, createStartScene theView) {

		systemView = theView;
		systemView.registerListener(this);
		systemModel = theModel;
		systemModel.registerListener(this);
	}

	@Override
	public void modelUpdateSetCompanyName(String companyName) {
		systemModel.setCompanyName(companyName);
	}

	@Override
	public void modelUpdateNewDepartment(String departmentName, boolean workersSynchronized, int syncedStartingHour) {
		systemModel.addNewDepartment(new Department(departmentName, workersSynchronized, syncedStartingHour));
	}

	@Override
	public void modelUpdateNewRole(Department thisDeparment, String nameOfRole, boolean canWorkFromHome,
			boolean workersSynchronized, int syncedStartingHour) {
		systemModel.addNewRoleToDepartment(
				new Role(nameOfRole, canWorkFromHome, workersSynchronized, syncedStartingHour), thisDeparment);

	}

	@Override
	public void modelUpdateNewEmployee(int thisDeparment, int thisRole, ePreference pref, String name, String id,
			int timePref, int baseCash) { // Base Wroker
		systemModel.addNewEmployee(new BaseWorker(pref, name, id, timePref, baseCash), thisRole, thisDeparment);
	}

	@Override
	public void modelUpdateNewEmployee(int thisDeparment, int thisRole, ePreference pref, String name, String id,
			int timePref, float cashPerHour) { // Time Worker
		systemModel.addNewEmployee(new TimeWorker(pref, name, id, timePref, cashPerHour), thisRole, thisDeparment);

	}

	@Override
	public void modelUpdateNewEmployee(int thisDeparment, int thisRole, ePreference pref, String name, String id,
			int timePref, int baseCash, float percentToSalary, int numOfSales) { // Sales Man
		systemModel.addNewEmployee(new SalesMan(pref, name, id, timePref, baseCash, percentToSalary, numOfSales),
				thisRole, thisDeparment);

	}

	public String showCompanyInformation() {
		return systemModel.toString();
	}

	@Override
	public void changeWorkMethoodInRole(int departmentIndex, int roleIndex, boolean isWrokersSync, int startingHour,
			boolean canWorkFromHome) {
		systemModel.changeWorkStyleInRole(departmentIndex, roleIndex, isWrokersSync, startingHour, canWorkFromHome);

	}

	@Override
	public void changeWorkMethoodInDepartment(int index, boolean isWrokersSync, int startingHour) {
		systemModel.changeWorkStyleInDepartment(index, isWrokersSync, startingHour);

	}

	@Override
	public void passingToView(String error) {
		systemView.updaetingError(error);
	}

	@Override
	public ArrayList<Department> getAllDepartments() {
		return systemModel.getAllDepartments();
	}

	@Override
	public ArrayList<Role> getAllRolesInDepartment(Department thisDepartment) {
		return systemModel.getAllRolesInDepartment(thisDepartment);
	}

	@Override
	public ArrayList<Employee> getAllEmployeesInRole(int indexDeparment, int indexRole) {
		return systemModel.getAllEmployeesInRole(indexDeparment, indexRole);
	}

	@Override
	public ArrayList<String> departmnetsOptions() {
		return systemModel.departmentOptions();
	}

	@Override
	public ArrayList<String> roleOptions(int departmentIndex) {
		return systemModel.roleOptions(departmentIndex);
	}

	@Override
	public ArrayList<String> employeeOptions(int departmentIndex, int roleIndex) {
		return systemModel.employeesOptions(departmentIndex, roleIndex);
	}

	@Override
	public float checkEfficiencyAfter() {
		return systemModel.checkEfficiencyAfterExperiment();
	}

	@Override
	public float checkEfficiencyAfter(Department checkDepartment) {
		return systemModel.checkEfficiencyAfterExperiment(checkDepartment);
	}

	@Override
	public float checkEfficiencyAfter(Role checkRole, Employee checkEmployee) {
		return systemModel.checkEfficiencyAfterExperiment(checkRole, checkEmployee);
	}

	@Override
	public float checkEfficiencyBefore() {
		return systemModel.checkEfficiencyBeforeExperiment();
	}

	@Override
	public float checkEfficiencyBefore(Department checkDepartment) {
		return systemModel.checkEfficiencyBeforeExperiment(checkDepartment);
	}

	@Override
	public float checkEfficiencyBefore(Role checkRole, Employee checkEmployee) {
		return systemModel.checkEfficiencyBeforeExperiment(checkRole, checkEmployee);
	}

	@Override
	public void fireLoadFile() throws ClassNotFoundException, IOException {

		Company temp = systemModel.fileLoader();

		if (temp != null) {
			systemModel = temp;
		}
	}

	@Override
	public void fireFileWriter() {
		systemModel.fileWriter(systemModel);
	}
	
	@Override
	public ArrayList<String> employeeId() {
		return systemModel.employeesId();
	}

}
