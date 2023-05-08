package CompanyProfitSimulation.Model;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class Employee implements Serializable {

	public enum ePreference {
		EARLY, LATE, SAME, FROM_HOME
	};

	protected String name;
	protected String id;
	protected float salary;
	protected ePreference pref;
	protected int timePref;

	public Employee(ePreference pref, String name, String id, int timePref) {
		this.name = name;
		this.pref = pref;
		this.id = id;
		this.timePref = timePref;
	}

	public float calcSalary() {
		return salary;
	}

	public int getTimePref() {
		return timePref;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public float getSalary() {
		return salary;
	}

	public ePreference getPref() {
		return pref;
	}

	public boolean equals(Employee other) {
		if (!(this.getClass().getSimpleName().equals(other.getClass().getSimpleName())))
			return false;
		if (id.equals(other.getId())) {
			return true;
		}
		return false;
	}

	public String toString() {
		String res = "Name: " + name + "\nId: " + id + "\nPreference: " + pref + "\n";
		return res;
	}

}
