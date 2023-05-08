package CompanyProfitSimulation.View;

import java.util.ArrayList;

import CompanyProfitSimulation.Model.Department;
import CompanyProfitSimulation.Model.Role;
import CompanyProfitSimulation.Model.Employee.ePreference;
import CompanyProfitSimulation.listeners.EventUIListeners;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class createNewCompanyScreenPart4{

	private transient Stage stage;
	private int indexD = 0;
	private int indexR = 0;

	private transient ArrayList<EventUIListeners> allListeners = new ArrayList<EventUIListeners>();
	private transient ArrayList<Department> allDepartments = new ArrayList<Department>();
	private transient ArrayList<Role> allRolesInDepartments = new ArrayList<Role>();
	private transient ArrayList<String> allEmployeesId = new ArrayList<String>();

	private transient Label lbErrorMsg = new Label("");
	private transient Label lbInstruction = new Label("Please fill the following information accordingly");
	private transient Label lbHeadLine = new Label("Creating Employees");
	private transient Label lbBaseSalary = new Label("Global Salary: ");
	private transient Label lbDepartmentName = new Label("Deparment name: ");
	private transient Label lbRoleName = new Label("Role name: ");
	private transient Label lbStartingHour = new Label("Starting hour: ");
	private transient Label lbEmployeeName = new Label("Employee name: ");
	private transient Label lbEmployeeId = new Label("Employee Id: ");
	private transient Label lbEmployeePref = new Label("Starting preferance: ");
	private transient Label lbEmployeeType = new Label("Employees type: ");
	private transient Label lbSalesPerMonth = new Label("Number of sales per month: ");
	private transient Label lbPercentPerSale = new Label("Percent per sale to salary: ");
	private transient Label lbCashPerHour = new Label("Cash per hour: ");

	private transient ObservableList<String> timeOptions = FXCollections.observableArrayList("AM", "PM");
	private transient ChoiceBox<String> choiceTime = new ChoiceBox<String>(timeOptions);
	private transient ObservableList<Integer> hoursOptions = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8,
			9, 10, 11, 12);
	private transient ChoiceBox<Integer> choiceHours = new ChoiceBox<Integer>(hoursOptions);
	private transient ObservableList<ePreference> preferanceOptions = FXCollections
			.observableArrayList(ePreference.values());
	private transient ChoiceBox<ePreference> choiceEmployeePreferance = new ChoiceBox<ePreference>(preferanceOptions);
	private transient ObservableList<String> employeesType = FXCollections.observableArrayList("Base Salary",
			"Clock Salary", "Sales-Men");
	private transient ChoiceBox<String> choiceSalariesTypes = new ChoiceBox<String>(employeesType);

	private transient VBox changingSalaryLeft = new VBox();
	private transient VBox changingSalaryRight = new VBox();

	private transient TextField tfBaseSalary = new TextField();
	private transient TextField tfDepartmentName = new TextField();
	private transient TextField tfRoleName = new TextField();
	private transient TextField tfEmployeeName = new TextField();
	private transient TextField tfEmployeeId = new TextField();
	private transient TextField tfCashPerHour = new TextField();
	private transient TextField tfNumSales = new TextField();
	private transient TextField tfPrecentOfSale = new TextField();

	private transient Button btNext = new Button("Next Stage");
	private transient Button btAdd = new Button("Add");

	public createNewCompanyScreenPart4(Stage primaryStage, ArrayList<EventUIListeners> allListeners, String error) {

		this.allListeners.addAll(allListeners);
		lbErrorMsg.setText(error);
		lbHeadLine.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		lbHeadLine.setUnderline(true);
		lbInstruction.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		lbErrorMsg.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 20));
		lbErrorMsg.setTextFill(Color.web("#ff0000", 1));
		updateDepartmentList();
		updateEmployeesName();

		stage = primaryStage;
		stage.setScene(startScene());
		stage.centerOnScreen();

	}

	private Scene startScene() { // adding employees to the roles

		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: azure");

		VBox topBorder = new VBox(lbErrorMsg, lbHeadLine, lbInstruction);
		topBorder.setPadding(new Insets(10, 10, 10, 10));
		topBorder.setSpacing(5);
		topBorder.setAlignment(Pos.CENTER);

		changingSalaryLeft.setSpacing(15);
		changingSalaryLeft.setPadding(new Insets(0, 5, 5, 5));
		changingSalaryLeft.setAlignment(Pos.CENTER);
		changingSalaryLeft.getChildren().setAll(lbBaseSalary);

		VBox centerLeft = new VBox(lbDepartmentName, lbRoleName, lbEmployeeName, lbEmployeeId, lbEmployeePref,
				lbStartingHour, lbEmployeeType, changingSalaryLeft);
		centerLeft.setSpacing(15);
		centerLeft.setPadding(new Insets(5, 5, 0, 5));
		centerLeft.setAlignment(Pos.CENTER);

		HBox choiceStartingHour = new HBox(choiceTime, choiceHours);
		choiceStartingHour.setSpacing(10);

		changingSalaryRight.setSpacing(7);
		changingSalaryRight.setPadding(new Insets(0, 5, 5, 5));
		changingSalaryRight.setAlignment(Pos.CENTER);
		changingSalaryRight.getChildren().setAll(tfBaseSalary);

		VBox centerRight = new VBox(tfDepartmentName, tfRoleName, tfEmployeeName, tfEmployeeId,
				choiceEmployeePreferance, choiceStartingHour, choiceSalariesTypes, changingSalaryRight);
		centerRight.setSpacing(7);
		centerRight.setPadding(new Insets(5, 5, 0, 5));
		centerRight.setAlignment(Pos.CENTER);

		tfDepartmentName.setText(allDepartments.get(indexD).getDepartmentName());
		tfDepartmentName.setEditable(false);
		tfRoleName.setText(allDepartments.get(indexD).getRolesInDepartments().get(indexR).getNameOfRole());
		tfRoleName.setEditable(false);
		choiceEmployeePreferance.getSelectionModel().select(3);
		choiceTime.getSelectionModel().selectFirst();
		choiceHours.getSelectionModel().selectFirst();
		choiceSalariesTypes.getSelectionModel().selectFirst();

		tfBaseSalary.setText("");
		tfCashPerHour.setText("");
		tfNumSales.setText("");
		tfPrecentOfSale.setText("");
		tfEmployeeId.setText("");
		tfEmployeeName.setText("");

		btNext.setVisible(
				!(allDepartments.get(indexD).getRolesInDepartments().get(indexR).getEmployeesInRole().isEmpty()));

		HBox mainCenter = new HBox(centerLeft, centerRight);
		mainCenter.setPadding(new Insets(5, 5, 5, 5));
		mainCenter.setSpacing(5);
		mainCenter.setAlignment(Pos.CENTER);

		HBox bottomBorder = new HBox(btNext, btAdd);
		bottomBorder.setSpacing(10);
		bottomBorder.setPadding(new Insets(5, 5, 5, 5));
		bottomBorder.setAlignment(Pos.TOP_CENTER);

		root.setTop(topBorder);
		root.setCenter(mainCenter);
		root.setBottom(bottomBorder);

		choiceEmployeePreferance.setOnAction(e -> {

			choiceTime.setDisable(false);
			choiceHours.setDisable(false);
			choiceHours.setItems(hoursOptions);
			choiceHours.getSelectionModel().select(8);

			if (choiceEmployeePreferance.getSelectionModel().isSelected(0)) { // EARLY
				choiceTime.getSelectionModel().selectFirst();
				choiceTime.setDisable(true);
				choiceHours.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7));
				choiceHours.getSelectionModel().selectLast();
			}
			if (choiceEmployeePreferance.getSelectionModel().isSelected(1)) { // LATE
				choiceTime.getSelectionModel().selectFirst();
				choiceHours.setItems(FXCollections.observableArrayList(9, 10, 11, 12));
				choiceHours.getSelectionModel().selectFirst();
			}
			if (choiceEmployeePreferance.getSelectionModel().isSelected(2)) { // SAME
				choiceTime.getSelectionModel().selectFirst();
				choiceHours.setValue(8);
				choiceTime.setDisable(true);
				choiceHours.setDisable(true);
			}
		});

		choiceTime.setOnAction(e -> {

			if (choiceEmployeePreferance.getSelectionModel().isSelected(1)) {
				if (choiceTime.getSelectionModel().isSelected(0)) {
					choiceHours.setItems(FXCollections.observableArrayList(9, 10, 11, 12));
				} else {
					choiceHours.setItems(hoursOptions);
				}
				choiceHours.getSelectionModel().selectFirst();
			}

		});

		choiceSalariesTypes.setOnAction(e -> {

			if (choiceSalariesTypes.getValue().equals("Sales-Men")) {
				changingSalaryLeft.getChildren().setAll(lbBaseSalary, lbSalesPerMonth, lbPercentPerSale);
				changingSalaryRight.getChildren().setAll(tfBaseSalary, tfNumSales, tfPrecentOfSale);
				tfBaseSalary.setText("");
				tfNumSales.setText("");
				tfPrecentOfSale.setText("");

			}

			if (choiceSalariesTypes.getValue().equals("Base Salary")) {
				changingSalaryLeft.getChildren().setAll(lbBaseSalary);
				changingSalaryRight.getChildren().setAll(tfBaseSalary);
				tfBaseSalary.setText("");
			}

			if (choiceSalariesTypes.getValue().equals("Clock Salary")) {
				changingSalaryLeft.getChildren().setAll(lbCashPerHour);
				changingSalaryRight.getChildren().setAll(tfCashPerHour);
				tfCashPerHour.setText("");
			}
		});

		btAdd.setOnAction(e -> {

			lbErrorMsg.setText("");

			if (!(tfEmployeeId.getText().matches("[0-9]{9}")))
				lbErrorMsg.setText("Employee's Id must contain only 9 numbers");

			if (tfEmployeeName.getText().equals("") || tfEmployeeId.getText().equals(""))
				lbErrorMsg.setText("Please fill all the information needed");

			if (lbErrorMsg.getText().equals("")) {
				if (choiceSalariesTypes.getValue().equals("Base Salary")) {
					if (tfBaseSalary.getText().equals("") || tfBaseSalary.getText().matches("[0-9]")) {
						lbErrorMsg.setText("Base Salary must contain only numbers");

					} else {
						for (EventUIListeners l : allListeners)
							l.modelUpdateNewEmployee(indexD, indexR, choiceEmployeePreferance.getValue(),
									tfEmployeeName.getText(), tfEmployeeId.getText(),
									calculateTime(choiceHours.getValue(), choiceTime.getValue().equals("PM")),
									Integer.parseInt(tfBaseSalary.getText()));
					}
				}

				if (choiceSalariesTypes.getValue().equals("Clock Salary")) {
					if (tfCashPerHour.getText().equals("")) {
						lbErrorMsg.setText("'Cash Per Hour' must contain only numbers");
					} else {
						for (EventUIListeners l : allListeners)
							l.modelUpdateNewEmployee(indexD, indexR, choiceEmployeePreferance.getValue(),
									tfEmployeeName.getText(), tfEmployeeId.getText(),
									calculateTime(choiceHours.getValue(), choiceTime.getValue().equals("PM")),
									Float.parseFloat(tfCashPerHour.getText()));
					}
				}

				if (choiceSalariesTypes.getValue().equals("Sales-Men")) {
					if (tfBaseSalary.getText().equals("") || tfPrecentOfSale.getText().equals("")
							|| tfNumSales.getText().equals("")) {
						lbErrorMsg.setText(
								"'Base Salary' & 'Number of sales' & 'Percent per sale' must contain only numbers");
					} else {
						for (EventUIListeners l : allListeners)
							l.modelUpdateNewEmployee(indexD, indexR, choiceEmployeePreferance.getValue(),
									tfEmployeeName.getText(), tfEmployeeId.getText(),
									calculateTime(choiceHours.getValue(), choiceTime.getValue().equals("PM")),
									Integer.parseInt(tfBaseSalary.getText()),
									Float.parseFloat(tfPrecentOfSale.getText()) / 100,
									Integer.parseInt(tfNumSales.getText()));
					}
				}

				checkIfExist(tfEmployeeId.getText());

				if (lbErrorMsg.getText().equals("")) {
					allEmployeesId.add(tfEmployeeId.getText());
					stage.setScene(startScene());
				}
			}
		});

		btNext.setOnAction(e -> {
			indexR++;

			if (indexR == allDepartments.get(indexD).getRolesInDepartments().size()) {
				indexR = 0;
				indexD++;

				if (indexD == allDepartments.size()) {
					@SuppressWarnings("unused")
					createMenuScene menuPart = new createMenuScene(stage, allListeners);
				} else {
					tfRoleName.setText(getRolesOptions(indexD).get(indexR));
					stage.setScene(startScene());
				}
			} else {
				tfRoleName.setText(getRolesOptions(indexD).get(indexR));
				stage.setScene(startScene());
			}
		});

		return new Scene(root, 500, 650);

	}

	private int calculateTime(int hours, boolean amOrPm) {

		if (amOrPm)
			return hours * 100 + 1200;
		return hours * 100;

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

	private void updateDepartmentList() {

		allDepartments.clear();

		for (EventUIListeners l : allListeners) {
			allDepartments.addAll(l.getAllDepartments());
		}
	}

	private void updateEmployeesName() {

		allEmployeesId.clear();

		for (EventUIListeners l : allListeners)
			allEmployeesId.addAll(l.employeeId());

	}

	private void checkIfExist(String newEmployeeId) {

		for (int counter = 0; counter < allEmployeesId.size(); counter++)
			if (allEmployeesId.get(counter).equals(newEmployeeId))

				lbErrorMsg.setText("employee id: " + newEmployeeId + " already exist");
	}
}