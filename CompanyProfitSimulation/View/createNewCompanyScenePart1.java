package CompanyProfitSimulation.View;

import java.util.ArrayList;

import CompanyProfitSimulation.listeners.EventUIListeners;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class createNewCompanyScenePart1{

	private transient Stage stage;
	private transient ArrayList<EventUIListeners> allListeners = new ArrayList<EventUIListeners>();

	private transient Button btCreate = new Button("Create");

	private transient TextField tfCompanyName = new TextField();

	private transient Label lbCompanyName = new Label("Company name: ");
	private transient Label lbErrorMsg = new Label("");
	private transient Label lbInstruction = new Label("Please fill the following information accordingly");
	private transient Label lbHeadLine = new Label("Creating a new company");

	public createNewCompanyScenePart1(Stage primaryStage, ArrayList<EventUIListeners> allListeners, String error) {
		
		this.allListeners.addAll(allListeners);
		lbErrorMsg.setText(error);
		lbHeadLine.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		lbHeadLine.setUnderline(true);
		lbInstruction.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		lbErrorMsg.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 20));
		lbErrorMsg.setTextFill(Color.web("#ff0000", 1));
		
		stage = primaryStage;
		stage.setScene(startScene());
		stage.centerOnScreen();


	}

	protected Scene startScene() { // setting the name of the company

		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: azure");

		VBox topBorder = new VBox();
		lbInstruction.setText("Please fill the following information accordingly");
		lbHeadLine.setText("Creating a new company");
		topBorder.setAlignment(Pos.CENTER);
		topBorder.getChildren().addAll(lbErrorMsg, lbHeadLine, lbInstruction);
		topBorder.setPadding(new Insets(5, 5, 5, 5));
		topBorder.setSpacing(5);

		HBox centerBorder = new HBox();
		centerBorder.setAlignment(Pos.CENTER);
		centerBorder.getChildren().addAll(lbCompanyName, tfCompanyName);

		HBox bottomBorder = new HBox(btCreate);
		bottomBorder.setPadding(new Insets(5, 5, 30, 5));
		bottomBorder.setAlignment(Pos.TOP_CENTER);

		root.setCenter(centerBorder);
		root.setTop(topBorder);
		root.setBottom(bottomBorder);

		btCreate.setOnAction(e -> {
			lbErrorMsg.setText("");

			if (tfCompanyName.getText().equals(""))
				lbErrorMsg.setText("Please fill all the text fields");

			else {
				for (EventUIListeners l : allListeners)
					l.modelUpdateSetCompanyName(tfCompanyName.getText());
				@SuppressWarnings("unused")
				createNewCompanyScreenPart2 part2 = new createNewCompanyScreenPart2(stage, allListeners,lbErrorMsg.getText());
			}
		});

		return new Scene(root, 500, 250);

	}

}
