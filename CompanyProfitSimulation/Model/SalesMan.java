package CompanyProfitSimulation.Model;

@SuppressWarnings("serial")
public class SalesMan extends Employee{

	private int baseCash;
	private float percentToSalary;
	private int numOfSales;

	public SalesMan(ePreference pref, String name, String id, int timePref, int baseCash, float percentToSalary,
			int numOfSales) {
		super(pref, name, id, timePref);
		this.baseCash = baseCash;
		this.percentToSalary = percentToSalary;
		this.numOfSales = numOfSales;
		calcSalry();
	}

	public float calcSalry() {
		return salary = (numOfSales * percentToSalary) + baseCash;
	}

	public String toString() {
		String res = super.toString();
		return res.concat("Employee type: " + this.getClass().getSimpleName() + " Salary: " + salary);
	}
}
