package CompanyProfitSimulation.View;

import java.util.ArrayList;

import CompanyProfitSimulation.listeners.EventUIListeners;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class showCompanyInformationScene{

	private transient ArrayList<EventUIListeners> allListeners = new ArrayList<EventUIListeners>();
	private transient Stage stage;
	private transient Label lbHeadLine = new Label("Here are all of our Company information");
	private transient Button btBack = new Button("Back");

	public showCompanyInformationScene(Stage primaryStage, ArrayList<EventUIListeners> allListeners) {
		
		this.allListeners.addAll(allListeners);
		lbHeadLine.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		lbHeadLine.setUnderline(true);
		
		stage = primaryStage;
		stage.setScene(startScene());
		stage.centerOnScreen();

	}

	@SuppressWarnings("static-access")
	protected Scene startScene() {

		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: azure");

		TextArea textArea = new TextArea(showCompanyInfo());
		textArea.setEditable(false);
		
		VBox center = new VBox(textArea);
		textArea.setPrefSize(400, 600);
		root.setPadding(new Insets(10, 10, 10, 10));
		root.setTop(lbHeadLine);
		root.setAlignment(lbHeadLine, Pos.CENTER);
		root.setBottom(btBack);
		root.setAlignment(btBack, Pos.TOP_CENTER);
		root.setCenter(center);

		btBack.setOnAction(e -> {
			@SuppressWarnings("unused")
			createMenuScene menuPart = new createMenuScene(stage, allListeners);
		});
		
		return new Scene(root, 600, 700);
	}

	private String showCompanyInfo() {
		String res = "";

		for (EventUIListeners l : allListeners)
			res = res.concat(l.showCompanyInformation());

		return res;
	}
}
