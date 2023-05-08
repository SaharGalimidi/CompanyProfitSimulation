package CompanyProfitSimulation.View;

import java.util.ArrayList;

import CompanyProfitSimulation.listeners.EventUIListeners;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class createMenuScene{

	private transient Stage stage;
	private transient ArrayList<EventUIListeners> allListeners = new ArrayList<EventUIListeners>();

	private transient Label lbErrorMsg = new Label("");
	private transient Label lbInstruction = new Label(" Choose one of the following options ☺ ");
	private transient Label lbHeadLine = new Label(" Main Menu ");

	private transient Button btAddEmployee = new Button("Add Employee");
	private transient Button btAddRole = new Button("Add Role");
	private transient Button btAddDepartment = new Button("Add Department");
	private transient Button btCompanyInfo = new Button("Show the company information");
	private transient Button btChangeRole = new Button("Change working method for a specific role");
	private transient Button btChangeDrpartment = new Button("Change working method for a specific department");
	private transient Button btCheckSimulation = new Button("Check simulation efficiency");
	private transient Button btExit = new Button("Exit");

	public createMenuScene(Stage primaryStage, ArrayList<EventUIListeners> allListeners) {
		
		this.allListeners.addAll(allListeners);
		lbHeadLine.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		lbHeadLine.setUnderline(true);
		lbInstruction.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		lbErrorMsg.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 20));
		lbErrorMsg.setTextFill(Color.web("#ff0000", 1));
		

		btAddEmployee.setFont(Font.font("David", FontWeight.BOLD, 14));
		btAddRole.setFont(Font.font("David", FontWeight.BOLD, 14));
		btAddDepartment.setFont(Font.font("David", FontWeight.BOLD, 14));
		btCompanyInfo.setFont(Font.font("David", FontWeight.BOLD, 14));
		btChangeRole.setFont(Font.font("David", FontWeight.BOLD, 14));
		btChangeDrpartment.setFont(Font.font("David", FontWeight.BOLD, 14));
		btCheckSimulation.setFont(Font.font("David", FontWeight.BOLD, 14));
		btExit.setFont(Font.font("David", FontWeight.BOLD, 14));

		stage = primaryStage;
		stage.setScene(startScene());
		stage.centerOnScreen();

	}

	@SuppressWarnings("unused")
	protected Scene startScene() {

		lbErrorMsg.setTextFill(Color.web("#ff0000", 1));
		lbHeadLine.setText(" Main Menu ");
		lbInstruction.setText(" Choose one of the following options");

		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: azure");
		root.setStyle("-fx-background-color: azure");
		root.setPadding(new Insets(15, 20, 10, 10));

		VBox topBorder = new VBox();
		topBorder.setPadding(new Insets(10, 0, 10, 0));
		topBorder.setSpacing(15);
		topBorder.setAlignment(Pos.CENTER);
		topBorder.getChildren().addAll(lbErrorMsg, lbHeadLine, lbInstruction);

		VBox centerBorder = new VBox();
		centerBorder.setPadding(new Insets(5, 5, 5, 5));
		centerBorder.setSpacing(10);
		centerBorder.setAlignment(Pos.CENTER);

		btAddEmployee.setPrefSize(360, 30);
		btAddRole.setPrefSize(360, 30);
		btAddDepartment.setPrefSize(360, 30);
		btCompanyInfo.setPrefSize(360, 30);
		btChangeRole.setPrefSize(360, 30);
		btChangeDrpartment.setPrefSize(360, 30);
		btChangeDrpartment.setMinSize(360, 30);
		btCheckSimulation.setPrefSize(360, 30);
		btExit.setPrefSize(360, 30);

		centerBorder.getChildren().addAll(btAddEmployee, btAddRole, btAddDepartment, btCompanyInfo, btChangeRole,
				btChangeDrpartment, btCheckSimulation, btExit);

		root.setTop(topBorder);
		root.setCenter(centerBorder);

		btAddEmployee.setOnAction(e -> {
			createNewCompanyScreenPart4 part4 = new createNewCompanyScreenPart4(stage, allListeners,
					lbErrorMsg.getText());
		});
		btAddRole.setOnAction(e -> {
			createNewCompanyScreenPart3 part3 = new createNewCompanyScreenPart3(stage, allListeners,
					lbErrorMsg.getText());
		});
		btAddDepartment.setOnAction(e -> {
			createNewCompanyScreenPart2 part2 = new createNewCompanyScreenPart2(stage, allListeners,
					lbErrorMsg.getText());
		});
		btCompanyInfo.setOnAction(e -> {
			showCompanyInformationScene infoPart = new showCompanyInformationScene(stage, allListeners);
		});
		btChangeRole.setOnAction(e -> {
			changeRoleMethoodScene roleChangePart = new changeRoleMethoodScene(stage, allListeners);
		});

		btChangeDrpartment.setOnAction(e -> {
			changeDepartmentMethoodScene departmentChangePart = new changeDepartmentMethoodScene(stage, allListeners);
		});

		btCheckSimulation.setOnAction(e -> {
			displayCompanyEfficiencyScene results = new displayCompanyEfficiencyScene(stage, allListeners);
		});

		btExit.setOnAction(e -> {
			createExitScreen exit = new createExitScreen(stage, allListeners);
		});

		return new Scene(root, 500, 600);

	}
}
