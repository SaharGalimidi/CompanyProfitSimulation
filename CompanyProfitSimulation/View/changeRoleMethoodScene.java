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
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class changeRoleMethoodScene{

	private transient ArrayList<Department> allDepartments = new ArrayList<Department>();
	private transient ArrayList<Role> allRolesInDepartments = new ArrayList<Role>();
	private transient ArrayList<EventUIListeners> allListeners = new ArrayList<EventUIListeners>();
	private transient Stage stage;

	private transient Label lbInstruction = new Label("Please fill the following information accordingly");
	private transient Label lbHeadLine = new Label("Changing role working methood");
	private transient Label lbIsChange = new Label("The working method changed successfully");
	private transient Label lbStartingHour = new Label("Starting hour: ");
	private transient Label lbWorkersSync = new Label("Workeres should be synced: ");
	private transient Label lbHomeWork = new Label("Enable work from home: ");

	private transient ObservableList<Integer> hoursOptions = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8,
			9, 10, 11, 12);
	private transient ChoiceBox<Integer> choiceHours = new ChoiceBox<Integer>(hoursOptions);
	private transient ObservableList<String> timeOptions = FXCollections.observableArrayList("AM", "PM");
	private transient ChoiceBox<String> choiceTime = new ChoiceBox<String>(timeOptions);
	private transient ChoiceBox<String> departmentOptions = new ChoiceBox<String>();
	private transient ChoiceBox<String> rolesOptions = new ChoiceBox<String>();

	private transient RadioButton rbYesSync = new RadioButton("Yes");
	private transient RadioButton rbNoSync = new RadioButton("No");
	private transient ToggleGroup groupSync = new ToggleGroup();
	private transient RadioButton rbYesHome = new RadioButton("Enable");
	private transient RadioButton rbNoHome = new RadioButton("Disable");
	private transient ToggleGroup groupHome = new ToggleGroup();

	private transient Button btBack = new Button("Back");
	private transient Button btChange = new Button("Change");

	public changeRoleMethoodScene(Stage primaryStage, ArrayList<EventUIListeners> allListeners) {
		
		this.allListeners.addAll(allListeners);
		lbIsChange.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		lbIsChange.setTextFill(Color.web("#00fa9a", 1));
		lbHeadLine.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		lbHeadLine.setUnderline(true);
		lbInstruction.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		rbYesSync.setToggleGroup(groupSync);
		rbNoSync.setToggleGroup(groupSync);
		rbYesHome.setToggleGroup(groupHome);
		rbNoHome.setToggleGroup(groupHome);
		departmentOptions.getItems().setAll(getDepartmentOptions());
		rolesOptions.getItems().setAll(getRolesOptions(0));
		
		stage = primaryStage;
		stage.setScene(startScene());
	}

	protected Scene startScene() {

		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: azure");

		VBox topBorder = new VBox(lbHeadLine, lbInstruction, lbIsChange);
		topBorder.setPadding(new Insets(5, 5, 5, 5));
		topBorder.setSpacing(5);
		topBorder.setAlignment(Pos.CENTER);
		lbIsChange.setVisible(false);

		VBox leftCenter = new VBox(20);
		leftCenter.setAlignment(Pos.CENTER_LEFT);
		leftCenter.setSpacing(5);
		leftCenter.setPadding(new Insets(5, 5, 5, 5));
		leftCenter.getChildren().addAll(new Label("Choose Department: "), new Label("Choose Role: "), lbHomeWork,
				lbWorkersSync, lbStartingHour);

		VBox rightCenter = new VBox(20);
		rightCenter.setAlignment(Pos.CENTER_LEFT);
		rightCenter.setSpacing(5);
		rightCenter.setPadding(new Insets(5, 5, 5, 5));

		HBox choiceStartingHour = new HBox(choiceTime, choiceHours);
		choiceStartingHour.setSpacing(5);
		choiceStartingHour.setVisible(false);

		HBox syncButtons = new HBox(rbYesSync, rbNoSync);
		syncButtons.setSpacing(5);

		HBox homeWorkButtons = new HBox(rbYesHome, rbNoHome);
		homeWorkButtons.setSpacing(5);
		rightCenter.getChildren().addAll(departmentOptions, rolesOptions, homeWorkButtons, syncButtons,
				choiceStartingHour);

		lbStartingHour.setVisible(false);
		rbNoSync.setSelected(true);
		rbNoHome.setSelected(true);
		departmentOptions.getSelectionModel().selectFirst();
		rolesOptions.getSelectionModel().selectFirst();

		HBox mainCenter = new HBox(leftCenter, rightCenter);
		mainCenter.setSpacing(10);
		mainCenter.setAlignment(Pos.CENTER);

		HBox bottomBorder = new HBox(btBack, btChange);
		bottomBorder.setAlignment(Pos.CENTER);
		bottomBorder.setSpacing(5);
		bottomBorder.setPadding(new Insets(5, 5, 30, 5));

		choiceTime.getSelectionModel().selectFirst();
		choiceHours.getSelectionModel().selectFirst();

		root.setTop(topBorder);
		root.setCenter(mainCenter);
		root.setBottom(bottomBorder);

		departmentOptions.setOnAction(e -> {
			rolesOptions.getItems().setAll(getRolesOptions(departmentOptions.getSelectionModel().getSelectedIndex()));
			rolesOptions.getSelectionModel().selectFirst();
		});

		rolesOptions.setOnAction(e -> {

		});

		btBack.setOnAction(e -> {
			lbIsChange.setVisible(false);
			@SuppressWarnings("unused")
			createMenuScene menuPart = new createMenuScene(stage, allListeners);
		});

		btChange.setOnAction(e -> {
			for (EventUIListeners l : allListeners) {
				l.changeWorkMethoodInRole(departmentOptions.getSelectionModel().getSelectedIndex(),
						rolesOptions.getSelectionModel().getSelectedIndex(), rbYesSync.isSelected(),
						calculateTime(choiceHours.getValue(), choiceTime.getValue().equals("PM")),
						rbYesHome.isSelected());
			}
			lbIsChange.setVisible(true);
		});

		rbYesSync.setOnMouseClicked(e -> {
			choiceStartingHour.setVisible(true);
			lbStartingHour.setVisible(true);

		});

		rbNoSync.setOnMouseClicked(e -> {
			choiceStartingHour.setVisible(false);
			lbStartingHour.setVisible(false);
		});

		return new Scene(root, 500, 450);
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

	private int calculateTime(int hours, boolean amOrPm) {

		if (amOrPm)
			return hours * 100 + 1200;
		return hours * 100;

	}

}
