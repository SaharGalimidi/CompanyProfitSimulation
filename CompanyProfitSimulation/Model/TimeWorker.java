package CompanyProfitSimulation.Model;


@SuppressWarnings("serial")
public class TimeWorker extends Employee{

	private final int NUM_OF_HOURS = 8;
	private float cashPerHour;

	public TimeWorker(ePreference pref, String name, String id, int timePref, float cashPerHour) {
		super(pref, name, id, timePref);
		this.cashPerHour = cashPerHour;
		calcSalry();
	}

	public float calcSalry() {
		return salary = NUM_OF_HOURS * cashPerHour;
	}

	public String toString() {
		String res = super.toString();
		return res.concat("Employee type: " + this.getClass().getSimpleName() + " Salary: " + salary);
	}
}
