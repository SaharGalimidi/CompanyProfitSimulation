package CompanyProfitSimulation.View;

import java.util.ArrayList;

import CompanyProfitSimulation.Model.Department;
import CompanyProfitSimulation.listeners.EventUIListeners;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class createNewCompanyScreenPart2{

	private boolean firstRun = false;
	private transient Stage stage;
	private transient ArrayList<EventUIListeners> allListeners = new ArrayList<EventUIListeners>();
	private transient ArrayList<Department> allDepartments = new ArrayList<Department>();
	private transient ArrayList<String> allDepartmentsNames = new ArrayList<String>();

	private transient Label lbErrorMsg = new Label("");
	private transient Label lbInstruction = new Label("Please fill the following information accordingly");
	private transient Label lbHeadLine = new Label("Creating Departments");
	private transient Label lbDepartmentName = new Label("Deparment name: ");
	private transient Label lbWorkersSync = new Label("Workeres should be synced: ");
	private transient Label lbStartingHour = new Label("Starting hour: ");

	private transient RadioButton rbYesSync = new RadioButton("Yes");
	private transient RadioButton rbNoSync = new RadioButton("No");
	private transient ToggleGroup groupSync = new ToggleGroup();

	private transient ObservableList<String> timeOptions = FXCollections.observableArrayList("AM", "PM");
	private transient ChoiceBox<String> choiceTime = new ChoiceBox<String>(timeOptions);
	private transient ObservableList<Integer> hoursOptions = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8,
			9, 10, 11, 12);
	private transient ChoiceBox<Integer> choiceHours = new ChoiceBox<Integer>(hoursOptions);

	private transient TextField tfDepartmentName = new TextField();

	private transient Button btNext = new Button("Next Stage");
	private transient Button btAdd = new Button("Add");

	public createNewCompanyScreenPart2(Stage primaryStage, ArrayList<EventUIListeners> allListeners, String error) {

		this.allListeners.addAll(allListeners);
		lbErrorMsg.setText(error);
		lbHeadLine.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		lbHeadLine.setUnderline(true);
		lbInstruction.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		lbErrorMsg.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 20));
		lbErrorMsg.setTextFill(Color.web("#ff0000", 1));
		rbYesSync.setToggleGroup(groupSync);
		rbNoSync.setToggleGroup(groupSync);
		updateDepartmentList();

		stage = primaryStage;
		stage.setScene(startScene());
		stage.centerOnScreen();

	}

	protected Scene startScene() { // adding the department

		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: azure");

		VBox topBorder = new VBox(lbErrorMsg, lbHeadLine, lbInstruction);
		topBorder.setPadding(new Insets(10, 10, 10, 10));
		topBorder.setAlignment(Pos.CENTER);
		topBorder.setSpacing(5);

		VBox centerLeft = new VBox(lbDepartmentName, lbWorkersSync, lbStartingHour);
		centerLeft.setPadding(new Insets(5, 5, 5, 5));
		centerLeft.setSpacing(10);
		centerLeft.setAlignment(Pos.CENTER);

		HBox togglePart = new HBox(rbYesSync, rbNoSync);
		togglePart.setSpacing(30);

		HBox choiceBoxPart = new HBox(choiceTime, choiceHours);
		choiceBoxPart.setSpacing(10);

		VBox centerRight = new VBox(tfDepartmentName, togglePart, choiceBoxPart);
		centerRight.setPadding(new Insets(5, 5, 5, 5));
		centerRight.setSpacing(10);
		centerRight.setAlignment(Pos.CENTER);

		HBox mainCenter = new HBox(centerLeft, centerRight);
		mainCenter.setPadding(new Insets(5, 5, 5, 5));
		mainCenter.setAlignment(Pos.CENTER);

		HBox bottomBorder = new HBox(btNext, btAdd);
		bottomBorder.setPadding(new Insets(5, 5, 30, 5));
		bottomBorder.setAlignment(Pos.TOP_CENTER);
		bottomBorder.setSpacing(15);

		rbNoSync.setSelected(true);
		choiceBoxPart.setVisible(false);
		lbStartingHour.setVisible(false);
		tfDepartmentName.setText("");
		btNext.setVisible(!(allDepartments.isEmpty()) || firstRun);
		choiceTime.getSelectionModel().selectFirst();
		choiceHours.getSelectionModel().selectFirst();

		root.setTop(topBorder);
		root.setCenter(mainCenter);
		root.setBottom(bottomBorder);

		rbYesSync.setOnMouseClicked(e -> {
			choiceBoxPart.setVisible(true);
			lbStartingHour.setVisible(true);
		});

		rbNoSync.setOnMouseClicked(e -> {
			choiceBoxPart.setVisible(false);
			lbStartingHour.setVisible(false);
		});

		btAdd.setOnAction(e -> {
			lbErrorMsg.setText("");

			if (tfDepartmentName.getText().equals(""))
				lbErrorMsg.setText("Please fill all the information needed");

			if (lbErrorMsg.getText().equals("")) {
				firstRun = true;
				for (EventUIListeners l : allListeners)
					l.modelUpdateNewDepartment(tfDepartmentName.getText(), rbYesSync.isSelected(),
							calculateTime(choiceHours.getValue(), choiceTime.getValue().equals("PM")));

				checkIfExist(tfDepartmentName.getText());

				if (lbErrorMsg.getText().equals("")) {
					allDepartmentsNames.add(tfDepartmentName.getText());
					stage.setScene(startScene());
				}
			}
		});

		btNext.setOnAction(e -> {
			@SuppressWarnings("unused")
			createNewCompanyScreenPart3 part3 = new createNewCompanyScreenPart3(stage, allListeners,
					lbErrorMsg.getText());
		});
		return new Scene(root, 450, 400);

	}

	private int calculateTime(int hours, boolean amOrPm) {

		if (amOrPm)
			return hours * 100 + 1200;
		return hours * 100;

	}

	private void updateDepartmentList() {

		allDepartments.clear();
		allDepartmentsNames.clear();

		for (EventUIListeners l : allListeners) {
			allDepartments.addAll(l.getAllDepartments());
			allDepartmentsNames.addAll(l.departmnetsOptions());
		}
	}

	private void checkIfExist(String departmentName) {

		for (int counter = 0; counter < allDepartmentsNames.size(); counter++)
			if (allDepartmentsNames.get(counter).equals(departmentName))
				lbErrorMsg.setText("the deparment: " + departmentName + " already exist");

	}

}
