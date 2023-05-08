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

public class createExitScreen{

	private transient ArrayList<EventUIListeners> allListeners = new ArrayList<EventUIListeners>();
	private transient Stage stage;
	private transient Label lbInstruction = new Label("♥");
	private transient Label lbHeadLine = new Label(" TY Please Come Again :)");
	private transient Button btExit = new Button("Exit");

	public createExitScreen(Stage primaryStage, ArrayList<EventUIListeners> allListeners) {
		
		this.allListeners.addAll(allListeners);
		lbInstruction.setStyle("-fx-background-color: darkcyan;");
		lbInstruction.setFont(Font.font("", FontWeight.BOLD, FontPosture.ITALIC, 60));
		lbInstruction.setTextFill(Color.CRIMSON);
		lbHeadLine.setFont(Font.font("David", FontWeight.BOLD, FontPosture.ITALIC, 30));
		
		
		stage = primaryStage;
		stage.setScene(startScene());
		stage.centerOnScreen();

	}

	@SuppressWarnings("static-access")
	private Scene startScene() {

		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: darkcyan");
		VBox topBorder = new VBox();
		topBorder.setPadding(new Insets(10, 10, 10, 10));
		topBorder.setAlignment(Pos.CENTER);
		topBorder.getChildren().addAll(lbHeadLine, lbInstruction);
		topBorder.setSpacing(10);
		lbHeadLine.setText("TY Please Come Again :)");
		lbInstruction.setText("♥");
		root.setTop(topBorder);
		root.setCenter(btExit);
		root.setAlignment(btExit, Pos.CENTER);

		btExit.setOnAction(e -> {
			for (EventUIListeners l : allListeners)
				l.fireFileWriter(); 
			stage.close();
		});

		return new Scene(root, 400, 200);
	}
}