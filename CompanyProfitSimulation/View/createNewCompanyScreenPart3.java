package CompanyProfitSimulation.View;

import java.util.ArrayList;

import CompanyProfitSimulation.Model.Department;
import CompanyProfitSimulation.Model.Role;
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

public class createNewCompanyScreenPart3{

	private int indexD = 0;
	private transient Stage stage;
	private transient ArrayList<EventUIListeners> allListeners = new ArrayList<EventUIListeners>();
	private transient ArrayList<Department> allDepartments = new ArrayList<Department>();
	private transient ArrayList<Role> allRolesInDepartments = new ArrayList<Role>();

	private transient Label lbErrorMsg = new Label("");
	private transient Label lbInstruction = new Label("Please fill the following information accordingly");
	private transient Label lbHeadLine = new Label("Creating Roles");
	private transient Label lbDepartmentName = new Label("Deparment name: ");
	private transient Label lbRoleName = new Label("Role name: ");
	private transient Label lbHomeWork = new Label("Enable work from home: ");
	private transient Label lbWorkersSync = new Label("Workeres should be synced: ");
	private transient Label lbStartingHour = new Label("Starting hour: ");

	private transient RadioButton rbYesSync = new RadioButton("Yes");
	private transient RadioButton rbNoSync = new RadioButton("No");
	private transient ToggleGroup groupSync = new ToggleGroup();
	private transient RadioButton rbYesHome = new RadioButton("Enable");
	private transient RadioButton rbNoHome = new RadioButton("Disable");
	private transient ToggleGroup groupHome = new ToggleGroup();

	private transient TextField tfDepartmentName = new TextField();
	private transient TextField tfRoleName = new TextField();

	private transient Button btNext = new Button("Next Stage");
	private transient Button btAdd = new Button("Add");

	private transient ObservableList<String> timeOptions = FXCollections.observableArrayList("AM", "PM");
	private transient ChoiceBox<String> choiceTime = new ChoiceBox<String>(timeOptions);
	private transient ObservableList<Integer> hoursOptions = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8,
			9, 10, 11, 12);
	private transient ChoiceBox<Integer> choiceHours = new ChoiceBox<Integer>(hoursOptions);

	public createNewCompanyScreenPart3(Stage primaryStage, ArrayList<EventUIListeners> allListeners, String error) {
		
		this.allListeners.addAll(allListeners);
		lbErrorMsg.setText(error);
		lbHeadLine.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		lbHeadLine.setUnderline(true);
		lbInstruction.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		lbErrorMsg.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 20));
		lbErrorMsg.setTextFill(Color.web("#ff0000", 1));
		rbYesSync.setToggleGroup(groupSync);
		rbNoSync.setToggleGroup(groupSync);
		rbYesHome.setToggleGroup(groupHome);
		rbNoHome.setToggleGroup(groupHome);
		updateDepartmentList();
		
		stage = primaryStage;
		stage.setScene(startScene());
		stage.centerOnScreen();

	}

	protected Scene startScene() { // adding roles to the depatments
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: azure");

		VBox topBorder = new VBox(lbErrorMsg, lbHeadLine, lbInstruction);
		topBorder.setPadding(new Insets(10, 10, 10, 10));
		topBorder.setAlignment(Pos.CENTER);
		topBorder.setSpacing(5);

		tfDepartmentName.setText(allDepartments.get(indexD).getDepartmentName());
		tfDepartmentName.setEditable(false);
		tfRoleName.setEditable(true);

		VBox centerLeft = new VBox(lbDepartmentName, lbRoleName, lbHomeWork, lbWorkersSync, lbStartingHour);
		centerLeft.setPadding(new Insets(5, 5, 5, 5));
		centerLeft.setAlignment(Pos.CENTER);
		centerLeft.setSpacing(9);

		HBox toggleSync = new HBox(rbYesSync, rbNoSync);
		toggleSync.setSpacing(10);

		HBox toggleHome = new HBox(rbYesHome, rbNoHome);
		toggleHome.setSpacing(10);

		HBox choiceStartingHour = new HBox(choiceTime, choiceHours);
		choiceStartingHour.setSpacing(10);

		VBox centerRight = new VBox(tfDepartmentName, tfRoleName, toggleHome, toggleSync, choiceStartingHour);
		centerRight.setPadding(new Insets(5, 5, 5, 5));
		centerRight.setAlignment(Pos.CENTER);
		centerRight.setSpacing(5);

		HBox mainCenter = new HBox(centerLeft, centerRight);
		mainCenter.setPadding(new Insets(5, 5, 5, 5));
		mainCenter.setAlignment(Pos.CENTER);

		HBox bottomBorder = new HBox(btNext, btAdd);
		bottomBorder.setPadding(new Insets(5, 5, 5, 5));
		bottomBorder.setAlignment(Pos.TOP_CENTER);
		bottomBorder.setSpacing(10);

		tfRoleName.setText("");
		rbYesHome.setSelected(true);
		rbNoSync.setSelected(true);
		lbStartingHour.setVisible(false);
		choiceStartingHour.setVisible(false);
		choiceTime.getSelectionModel().selectFirst();
		choiceHours.getSelectionModel().selectFirst();
		btNext.setVisible(!(allDepartments.get(indexD).getRolesInDepartments().isEmpty()));

		root.setTop(topBorder);
		root.setCenter(mainCenter);
		root.setBottom(bottomBorder);

		rbYesSync.setOnMouseClicked(e -> {
			lbStartingHour.setVisible(true);
			choiceStartingHour.setVisible(true);
		});

		rbNoSync.setOnMouseClicked(e -> {
			lbStartingHour.setVisible(false);
			choiceStartingHour.setVisible(false);
		});

		btAdd.setOnAction(e -> {

			ArrayList<Department> temp = new ArrayList<Department>();
			lbErrorMsg.setText("");

			if (tfRoleName.getText().equals(""))
				lbErrorMsg.setText("Please fill all the information needed");

			if (lbErrorMsg.getText().equals("")) {
				for (EventUIListeners l : allListeners) {
					l.modelUpdateNewRole(allDepartments.get(indexD), tfRoleName.getText(), rbYesHome.isSelected(),
							rbYesSync.isSelected(),
							calculateTime(choiceHours.getValue(), choiceTime.getValue().equals("PM")));
					temp.addAll(l.getAllDepartments());
				}
				allDepartments = temp;
				stage.setScene(startScene());
			}
		});

		btNext.setOnAction(e -> {
			indexD++;

			if (indexD == allDepartments.size()) {
				indexD = 0;

				allDepartments.clear();

				for (EventUIListeners l : allListeners) {
					allDepartments.addAll(l.getAllDepartments());
					allRolesInDepartments.addAll(l.getAllRolesInDepartment(allDepartments.get(0)));
				}
				lbStartingHour.setVisible(true);
				@SuppressWarnings("unused")
				createNewCompanyScreenPart4 part4 = new createNewCompanyScreenPart4(stage, allListeners, lbErrorMsg.getText());
			} else {
				stage.setScene(startScene());
			}
		});

		return new Scene(root, 500, 400);
	}

	private int calculateTime(int hours, boolean amOrPm) {

		if (amOrPm)
			return hours * 100 + 1200;
		return hours * 100;

	}
	
	private void updateDepartmentList() {
		
		allDepartments.clear();
		
		for (EventUIListeners l : allListeners) {
			allDepartments.addAll(l.getAllDepartments());
		}
	}

}
