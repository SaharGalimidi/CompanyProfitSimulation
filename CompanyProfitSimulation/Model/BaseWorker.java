package CompanyProfitSimulation.Model;

@SuppressWarnings("serial")
public class BaseWorker extends Employee{

	private int baseCash;
	
	public BaseWorker(ePreference pref, String name, String id, int timePref, int baseCash) {
		super(pref, name, id, timePref);
		this.baseCash = baseCash;
		calcSalary();
	}
	
	public float calcSalary() {
		return salary = baseCash;
	}
	
	public String toString() {
		String res = super.toString();
		return res.concat("Employee type: " + this.getClass().getSimpleName() + " Salary: " + salary);
	}
}
