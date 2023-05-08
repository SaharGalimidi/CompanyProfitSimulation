package CompanyProfitSimulation.Model;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Department implements Syncable, efficiencyCalc, Serializable {

	private String departmentName;
	private ArrayList<Role> rolesInDepartments;
	private boolean workersSynchronized;
	private int syncedStartingHour;

	public Department(String departmentName, boolean workersSynchronized, int syncedStartingHour) {
		this.workersSynchronized = workersSynchronized;
		this.syncedStartingHour = syncedStartingHour;
		this.departmentName = departmentName;
		rolesInDepartments = new ArrayList<Role>();

	}

	public boolean addRole(Role newRole) {
		if (rolesInDepartments.add(newRole))
			return true;
		return false;
	}

	public boolean isWorkersSynchronized() {
		return workersSynchronized;
	}

	public int getSyncedStartingHour() {
		return syncedStartingHour;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public ArrayList<Role> getRolesInDepartments() {
		return rolesInDepartments;
	}

	public boolean equals(Department other) {

		if (!(other.getClass().getSimpleName().equals("Department")))
			return false;

		if (this.departmentName.equals(other.getDepartmentName()))
			return true;

		return false;

	}

	@Override
	public void sync(boolean isWrokersSync, int startingHour) {

		workersSynchronized = isWrokersSync;

		if (workersSynchronized) {
			syncedStartingHour = startingHour;
			for (int counter = 0; counter < rolesInDepartments.size(); counter++)
				rolesInDepartments.get(counter).sync(workersSynchronized, startingHour);
		} else {
			syncedStartingHour = 0;
			for (int counter = 0; counter < rolesInDepartments.size(); counter++)
				rolesInDepartments.get(counter).sync(workersSynchronized, syncedStartingHour);
		}
	}

	@Override
	public float checkEfficiencyAfterExperiment() {
		float efficiencyPerHour = 0;

		for (int counter = 0; counter < rolesInDepartments.size(); counter++)
			efficiencyPerHour += rolesInDepartments.get(counter).checkEfficiencyAfterExperiment();

		return efficiencyPerHour;
	}

	@Override
	public float checkEfficiencyBeforeExperiment() {

		float sum = 0;

		for (int counter = 0; counter < rolesInDepartments.size(); counter++)
			sum += rolesInDepartments.get(counter).checkEfficiencyBeforeExperiment();

		return sum;
	}

	public String toString() {

		String res = "Department name: " + departmentName + "\nThe workers in the department sync: "
				+ workersSynchronized;
		if (workersSynchronized) {
			res = res.concat("\nThe sync hour is: " + syncedStartingHour / 100 + ":00"
					+ "\nHere are the roles in this department: \n");
		} else {
			res = res.concat("\nHere are the roles in this department: \n");
		}
		for (int counter = 0; counter < rolesInDepartments.size(); counter++)
			res = res.concat(rolesInDepartments.get(counter).toString(workersSynchronized) + "\n");

		res = res.concat("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n");

		return res;
	}

}
