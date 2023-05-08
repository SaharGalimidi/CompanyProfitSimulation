package CompanyProfitSimulation.View;

import java.io.IOException;
import java.util.ArrayList;

import CompanyProfitSimulation.listeners.EventUIListeners;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class createStartScene{

	private transient Stage stage;
	private transient ArrayList<EventUIListeners> allListeners = new ArrayList<EventUIListeners>();

	private transient Button btLoadFile = new Button("Load file");
	private transient Button btCreateNew = new Button("New Company");

	private transient Label lbErrorMsg = new Label("");
	private transient Label lbInstruction = new Label("Would you like to load an existing Company Simulation file?");
	private transient Label lbHeadLine = new Label("Welcome to our program");

	public createStartScene(Stage primaryStage) {

		lbHeadLine.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		lbHeadLine.setUnderline(true);
		lbInstruction.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		lbErrorMsg.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 20));
		lbErrorMsg.setTextFill(Color.web("#ff0000", 1));
		
		stage = primaryStage;
		stage.setScene(startScene());
		stage.setTitle("Efficiency Simulator");
		stage.setAlwaysOnTop(true);
		stage.setResizable(false);
		stage.show();
		stage.centerOnScreen();
	}
	
	public void registerListener(EventUIListeners newListener) {
		allListeners.add(newListener);
	}

	@SuppressWarnings("unused")
	public Scene startScene() {

		VBox startTitle = new VBox(lbHeadLine, lbInstruction);
		startTitle.setSpacing(25);
		startTitle.setAlignment(Pos.CENTER);
		startTitle.setPadding(new Insets(10, 30, 20, 30));

		BorderPane startRoot = new BorderPane();
		startRoot.setStyle("-fx-background-color: azure");
		startRoot.setPadding(new Insets(10, 20, 10, 20));

		HBox startOptions = new HBox();
		startOptions.setSpacing(50);
		startOptions.getChildren().addAll(btLoadFile, btCreateNew);
		startOptions.setAlignment(Pos.CENTER);

		startRoot.setCenter(startOptions);
		startRoot.setTop(startTitle);

		btLoadFile.setOnAction(e -> {
			for (EventUIListeners l : allListeners) {
				try {
					l.fireLoadFile();
				} catch (ClassNotFoundException | IOException error) {
				}
			}
			if (lbErrorMsg.getText().equals("")) {
				createMenuScene menuPart = new createMenuScene(stage, allListeners);
			} else {
				createNewCompanyScenePart1 part1 = new createNewCompanyScenePart1(stage, allListeners,lbErrorMsg.getText());
			}

		});

		btCreateNew.setOnAction(e -> {
			createNewCompanyScenePart1 part1 = new createNewCompanyScenePart1(stage, allListeners, lbErrorMsg.getText());
		});

		return new Scene(startRoot, 650, 300);
	}

	public void updaetingError(String error) {
		lbErrorMsg.setText(error);
	}

}
