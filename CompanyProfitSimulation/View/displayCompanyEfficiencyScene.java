package CompanyProfitSimulation.View;

import java.util.ArrayList;

import CompanyProfitSimulation.Model.Department;
import CompanyProfitSimulation.Model.Employee;
import CompanyProfitSimulation.Model.Role;
import CompanyProfitSimulation.listeners.EventUIListeners;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class displayCompanyEfficiencyScene{
	private transient Stage stage;
	private float sumAfter = 0;
	private float sumBefore = 0;
	private int indexD = 0;
	private int indexR = 0;

	private transient ArrayList<EventUIListeners> allListeners = new ArrayList<EventUIListeners>();
	private transient ArrayList<Role> allRolesInDepartments = new ArrayList<Role>();
	private transient ArrayList<Employee> allEmployees = new ArrayList<Employee>();
	private transient ArrayList<Department> allDepartments = new ArrayList<Department>();

	private transient Label lbInstruction = new Label(
			"Here are the profits / loses according to the information given");
	private transient Label lbHeadLine = new Label("Simulation Results");
	private transient Label lbCompanyRevenue = new Label();
	private transient Label lbDepartmentsRevenue = new Label();
	private transient Label lbEmployeesRevenue = new Label();
	private transient Label lbCompanyResults = new Label("Company Results");
	private transient Label lbDepartmentsRsults = new Label("Choose a Department");
	private transient Label lbWorkerResults = new Label("Choose a Worker");

	private transient PieChart pChartCompany = new PieChart();
	private transient PieChart pChartDepartment = new PieChart();
	private transient PieChart pChartEmployee = new PieChart();

	private transient ChoiceBox<String> departmentOptions = new ChoiceBox<String>();
	private transient ChoiceBox<String> departmentOptions2 = new ChoiceBox<String>();
	private transient ChoiceBox<String> rolesOptions = new ChoiceBox<String>();
	private transient ChoiceBox<String> employeeOptions = new ChoiceBox<String>();

	private transient Button btBack = new Button("Back");

	public displayCompanyEfficiencyScene(Stage primaryStage, ArrayList<EventUIListeners> allListeners) {
		
		this.allListeners.addAll(allListeners);
		lbHeadLine.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		lbHeadLine.setUnderline(true);
		lbInstruction.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		lbCompanyResults.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		lbCompanyResults.setStyle("-fx-underline: true");
		lbDepartmentsRsults.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		lbDepartmentsRsults.setStyle("-fx-underline: true");
		lbWorkerResults.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		lbWorkerResults.setStyle("-fx-underline: true");
		pChartCompany.setLabelsVisible(false);
		pChartDepartment.setLabelsVisible(false);
		pChartEmployee.setLabelsVisible(false);
		prepForPieCharts();

		stage = primaryStage;
		stage.setScene(startScene());
		stage.centerOnScreen();

	}

	protected Scene startScene() {

		double x = stage.getX();
		double y = stage.getY();

		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: azure");
		VBox topBorder = new VBox(lbHeadLine, lbInstruction);
		topBorder.setSpacing(5);
		topBorder.setPadding(new Insets(5, 5, 5, 5));
		topBorder.setAlignment(Pos.CENTER);

		VBox companyPart = new VBox(lbCompanyResults, pChartCompany, lbCompanyRevenue);
		companyPart.setAlignment(Pos.CENTER);
		companyPart.setSpacing(65);

		VBox departmentPart = new VBox(lbDepartmentsRsults, departmentOptions, pChartDepartment, lbDepartmentsRevenue);
		departmentPart.setAlignment(Pos.CENTER);
		departmentPart.setSpacing(25);
		departmentOptions.getItems().setAll(getDepartmentOptions());
		departmentOptions.getSelectionModel().selectFirst();

		VBox workerPart = new VBox(lbWorkerResults, departmentOptions2, rolesOptions, employeeOptions, pChartEmployee,
				lbEmployeesRevenue);
		workerPart.setAlignment(Pos.CENTER);
		workerPart.setSpacing(5);

		departmentOptions2.getItems().setAll(getDepartmentOptions());
		rolesOptions.getItems().setAll(getRolesOptions(0));
		employeeOptions.getItems().setAll(getEmplyoeesOptions(0, 0));
		departmentOptions2.getSelectionModel().selectFirst();
		rolesOptions.getSelectionModel().selectFirst();
		employeeOptions.getSelectionModel().selectFirst();

		HBox bottomBorder = new HBox(btBack);
		bottomBorder.setPadding(new Insets(5, 5, 5, 5));
		bottomBorder.setAlignment(Pos.CENTER);

		HBox mainCenter = new HBox(companyPart, departmentPart, workerPart);
		mainCenter.setSpacing(10);
		mainCenter.setPadding(new Insets(5, 5, 5, 5));
		mainCenter.setAlignment(Pos.CENTER);

		root.setTop(topBorder);
		root.setCenter(mainCenter);
		root.setBottom(bottomBorder);

		departmentOptions.setOnAction(e -> {
			sumAfter = 0;
			sumBefore = 0;
			indexD = checkIndex(departmentOptions.getSelectionModel().getSelectedIndex());

			getRolesOptions(indexD);

			for (EventUIListeners l : allListeners) {
				sumAfter += l.checkEfficiencyAfter(allDepartments.get(indexD));
				sumBefore += l.checkEfficiencyBefore(allDepartments.get(indexD));
			}
			lbDepartmentsRevenue.setText("Revenue before: " + sumBefore + " houres | " + sumBefore * 10
					+ " $\nRevenue after: " + sumAfter + " houres | " + sumAfter * 10 + " $");

			pChartDepartment.setData(adjustPieChart(pChartDepartment, sumAfter, sumBefore));
		});

		departmentOptions2.setOnAction(e -> {
			sumAfter = 0;
			sumBefore = 0;
			indexR = 0;
			indexD = checkIndex(departmentOptions2.getSelectionModel().getSelectedIndex());

			rolesOptions.getItems().setAll(getRolesOptions(indexD));
			employeeOptions.getItems().setAll(getEmplyoeesOptions(indexD, indexR));

			for (EventUIListeners l : allListeners) {
				sumAfter += l.checkEfficiencyAfter(allRolesInDepartments.get(0), allEmployees.get(0));
				sumBefore += l.checkEfficiencyBefore(allRolesInDepartments.get(0), allEmployees.get(0));
			}

			lbEmployeesRevenue.setText("Revenue before: " + sumBefore + " houres | " + sumBefore * 10
					+ " $\nRevenue after: " + sumAfter + " houres | " + sumAfter * 10 + " $");

			rolesOptions.getSelectionModel().selectFirst();
			employeeOptions.getSelectionModel().selectFirst();

			pChartEmployee.setData(adjustPieChart(pChartEmployee, sumAfter, sumBefore));
		});

		rolesOptions.setOnAction(e -> {
			sumAfter = 0;
			sumBefore = 0;
			indexD = checkIndex(departmentOptions2.getSelectionModel().getSelectedIndex());
			indexR = checkIndex(rolesOptions.getSelectionModel().getSelectedIndex());


			employeeOptions.getItems().setAll(getEmplyoeesOptions(indexD, indexR));

			for (EventUIListeners l : allListeners) {
				sumAfter += l.checkEfficiencyAfter(allRolesInDepartments.get(indexR), allEmployees.get(0));
				sumBefore += l.checkEfficiencyBefore(allRolesInDepartments.get(indexR), allEmployees.get(0));
			}

			lbEmployeesRevenue.setText("Revenue before: " + sumBefore + " houres | " + sumBefore * 10
					+ " $\nRevenue after: " + sumAfter + " houres | " + sumAfter * 10 + " $");

			employeeOptions.getSelectionModel().selectFirst();
			pChartEmployee.setData(adjustPieChart(pChartEmployee, sumAfter, sumBefore));
		});

		employeeOptions.setOnAction(e -> {
			sumAfter = 0;
			sumBefore = 0;
			int indexE;

			indexD = checkIndex(departmentOptions2.getSelectionModel().getSelectedIndex());
			indexR = checkIndex(rolesOptions.getSelectionModel().getSelectedIndex());
			indexE = checkIndex(employeeOptions.getSelectionModel().getSelectedIndex());

			for (EventUIListeners l : allListeners) {
				sumAfter += l.checkEfficiencyAfter(allRolesInDepartments.get(indexR), allEmployees.get(indexE));
				sumBefore += l.checkEfficiencyBefore(allRolesInDepartments.get(indexR), allEmployees.get(indexE));
			}

			lbEmployeesRevenue.setText("Revenue before: " + sumBefore + " houres | " + sumBefore * 10
					+ " $\nRevenue after: " + sumAfter + " houres | " + sumAfter * 10 + " $");

			pChartEmployee.setData(adjustPieChart(pChartEmployee, sumAfter, sumBefore));
		});

		btBack.setOnAction(e -> {
			stage.setX(x);
			stage.setY(y);
			@SuppressWarnings("unused")
			createMenuScene menuPart = new createMenuScene(stage, allListeners);
		});

		stage.setX(150);
		stage.setY(100);
		return new Scene(root, 1400, 700);

	}

	private ArrayList<String> getEmplyoeesOptions(int indexD, int indexR) {

		ArrayList<String> options = new ArrayList<String>();
		allEmployees.clear();

		for (EventUIListeners l : allListeners) {
			allEmployees.addAll(l.getAllEmployeesInRole(indexD, indexR));
			options.addAll(l.employeeOptions(indexD, indexR));
		}
		return options;
	}

	private ArrayList<String> getRolesOptions(int index) {

		ArrayList<String> options = new ArrayList<String>();
		allRolesInDepartments.clear();

		if (index >= 0) {
			for (EventUIListeners l : allListeners) {
				allRolesInDepartments.addAll(l.getAllRolesInDepartment(allDepartments.get(index)));
				options.addAll(l.roleOptions(index));
			}
		}
		return options;
	}

	private ArrayList<String> getDepartmentOptions() {

		ArrayList<String> options = new ArrayList<String>();
		allDepartments.clear();

		for (EventUIListeners l : allListeners) {
			allDepartments.addAll(l.getAllDepartments());
			options.addAll(l.departmnetsOptions());
		}
		return options;

	}
	
	private ObservableList<Data> adjustPieChart(PieChart pChart, float after, float before) {
		pChart = new PieChart();
		pChart.setData(FXCollections.observableArrayList(new PieChart.Data("After Simulation", after),
				new PieChart.Data("Before Simulation", before)));

		pChart.getData().forEach(data -> {
			String percentage = String.format("%.2f%%", (data.getPieValue() / (after + before) * 100));
			Tooltip toolTip = new Tooltip(percentage);
			Tooltip.install(data.getNode(), toolTip);
		});
		return pChart.getData();
	}

	private void prepForPieCharts() {
		indexD = 0;
		indexR = 0;
		float after = 0, before = 0;
		allDepartments.clear();

		for (EventUIListeners l : allListeners)
			allDepartments.addAll(l.getAllDepartments());

		for (EventUIListeners l : allListeners) {
			after += l.checkEfficiencyAfter();
			before += l.checkEfficiencyBefore();
		}
		lbCompanyRevenue.setText("Revenue before: " + before + " houres | " + before * 10 + " $\nRevenue after: "
				+ after + " houres | " + after * 10 + " $");
		pChartCompany.setData(adjustPieChart(pChartCompany, after, before));

		after = 0;
		before = 0;
		for (EventUIListeners l : allListeners) {
			after += l.checkEfficiencyAfter(allDepartments.get(0));
			before += l.checkEfficiencyBefore(allDepartments.get(0));
		}
		lbDepartmentsRevenue.setText("Revenue before: " + before + " houres | " + before * 10 + " $\nRevenue after: "
				+ after + " houres | " + after * 10 + " $");

		pChartDepartment.setData(adjustPieChart(pChartDepartment, after, before));

		after = 0;
		before = 0;
		for (EventUIListeners l : allListeners) {
			after += l.checkEfficiencyAfter(allDepartments.get(0).getRolesInDepartments().get(0),
					allDepartments.get(0).getRolesInDepartments().get(0).getEmployeesInRole().get(0));
			before += l.checkEfficiencyBefore(allDepartments.get(0).getRolesInDepartments().get(0),
					allDepartments.get(0).getRolesInDepartments().get(0).getEmployeesInRole().get(0));
		}

		lbEmployeesRevenue.setText("Revenue before: " + before + " houres | " + before * 10 + " $\nRevenue after: "
				+ after + " houres | " + after * 10 + " $");

		pChartEmployee.setData(adjustPieChart(pChartEmployee, after, before));
	}

	private int checkIndex(int index) {
		if (index < 0)
			return 0;
		return index;
	}
}