package CompanyProfitSimulation;

import java.io.Serializable;
import CompanyProfitSimulation.Controller.SystemController;
import CompanyProfitSimulation.Model.Company;
import CompanyProfitSimulation.View.createStartScene;
import javafx.application.Application;
import javafx.stage.Stage;

@SuppressWarnings("serial")
public class Main extends Application implements Serializable {

	public void start(Stage primaryStage) {

		Company theModel = new Company();
		createStartScene theView = new createStartScene(primaryStage);
		SystemController theController = new SystemController(theModel, theView);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
