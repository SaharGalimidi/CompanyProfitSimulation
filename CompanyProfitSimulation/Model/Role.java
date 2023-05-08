package CompanyProfitSimulation.Model;

import java.io.Serializable;
import java.util.ArrayList;

import CompanyProfitSimulation.Model.Employee.ePreference;

@SuppressWarnings("serial")
public class Role implements Syncable, efficiencyCalc, Serializable {

	private final int DEFULAT_STARTING_HOUR = 800;
	private final int WORKING_HOURS = 8;
	private String nameOfRole;
	private ArrayList<Employee> employeesInRole;
	private boolean canWorkFromHome;
	private boolean workersSynchronized;
	private int syncedStartingHour;

	public Role(String nameOfRole, boolean canWorkFromHome, boolean workersSynchronized, int syncedStartingHour) {
		this.nameOfRole = nameOfRole;
		this.canWorkFromHome = canWorkFromHome;
		this.workersSynchronized = workersSynchronized;
		this.syncedStartingHour = syncedStartingHour;
		employeesInRole = new ArrayList<Employee>();
	}

	public boolean addEmployees(Employee newEmployee) {
		if (employeesInRole.add(newEmployee))
			return true;
		return false;
	}

	public boolean setHomeWorkOption(boolean canWorkFromHome) {
		return this.canWorkFromHome = canWorkFromHome;
	}

	public String getNameOfRole() {
		return nameOfRole;
	}

	public ArrayList<Employee> getEmployeesInRole() {
		return employeesInRole;
	}

	public boolean isCanWorkFromHome() {
		return canWorkFromHome;
	}

	public boolean isWorkersSynchronized() {
		return workersSynchronized;
	}

	public int getSyncedStartingHour() {
		return syncedStartingHour;
	}

	@Override
	public void sync(boolean isWrokersSync, int startingHour) {
		
		if (isWrokersSync)
			syncedStartingHour = startingHour;
		else
			syncedStartingHour = 0;

		workersSynchronized = isWrokersSync;
	}

	public float checkIfMaxHourReached(float num) {
		if (num > WORKING_HOURS)
			return WORKING_HOURS;
		return num;
	}

	@Override
	public float checkEfficiencyAfterExperiment() {
		float efficiencyPerHour = 0;

		for (int counter = 0; counter < employeesInRole.size(); counter++)
			efficiencyPerHour += checkEfficiencyPerWorkerAfterExperiment(employeesInRole.get(counter));

		return efficiencyPerHour;
	}

	@Override
	public float checkEfficiencyBeforeExperiment() {

		float sum = 0;

		for (int counter = 0; counter < employeesInRole.size(); counter++)
			sum += checkEfficiencyPerWorkerBeforeExperiment(employeesInRole.get(counter));

		return sum;
	}

	public float checkEfficiencyPerWorkerAfterExperiment(Employee checkEmployee) {

		ePreference pref;
		float helper;

		if (checkEmployee.getPref().equals(ePreference.FROM_HOME))
			if (canWorkFromHome)
				return (float) (WORKING_HOURS * 1.1);
			else
				return (float) (WORKING_HOURS * 0.8);

		if (checkEmployee.getPref().equals(ePreference.SAME))
			if (workersSynchronized) {

				helper = checkIfMaxHourReached(Math.abs(DEFULAT_STARTING_HOUR - syncedStartingHour) / 100);
				return (float) (helper * 0.8) + (WORKING_HOURS - helper);
			} else
				return WORKING_HOURS;

		if (workersSynchronized) {
			if (syncedStartingHour > DEFULAT_STARTING_HOUR)
				pref = ePreference.LATE;
			else
				pref = ePreference.EARLY;

			helper = checkIfMaxHourReached(Math.abs(DEFULAT_STARTING_HOUR - syncedStartingHour) / 100);

			if (checkEmployee.getPref() == pref)
				return (float) (helper * 1.2) + (WORKING_HOURS - helper);
			else
				return (float) (helper * 0.8) + (WORKING_HOURS - helper);

		} else {
			helper = checkIfMaxHourReached((Math.abs(DEFULAT_STARTING_HOUR - checkEmployee.getTimePref())) / 100);
			return (float) (helper * 1.2) + (WORKING_HOURS - helper);
		}

	}

	public float checkEfficiencyPerWorkerBeforeExperiment(Employee checkEmployee) {
		return WORKING_HOURS;
	}

	public String toString(boolean departmentSync) {
		String homeOption = "___________________\nWork space: Office";
		if (canWorkFromHome)
			homeOption = homeOption.concat(" / Home");

		String res = "\n___________________Role name: " + nameOfRole + homeOption + "\nThe workers in the role sync: "
				+ workersSynchronized;

		if (!(departmentSync)) {
			res = res.concat(
					"\nThe sync hour is: " + syncedStartingHour / 100 + ":00" + "\nEmployees in this role are: \n");
		} else {
			res = res.concat("\nEmployees in this role are: \n");
		}
		for (int counter = 0; counter < employeesInRole.size(); counter++)
			res = res.concat(employeesInRole.get(counter).toString()) + "\n\n";

		return res;
	}

}
