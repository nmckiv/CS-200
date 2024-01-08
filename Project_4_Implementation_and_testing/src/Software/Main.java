package Software;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;


/**
 * Runs the program and starts up the gui
 * 
 * @author Rowan Romanauskas
 */
public class Main extends Application {

	
	/**
	 *Starts the gui program on the main log in menu
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("LoginMenu.fxml"));
			Parent root = (Parent) loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Chocoholics Anonymous");
			//primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
			
			primaryStage.setOnCloseRequest(event -> {
				event.consume();
				closeProgram(primaryStage);
			});
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * closes the entire program when the user requests
	 * @param stage
	 */
	public void closeProgram(Stage stage) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Close Program");
		alert.setHeaderText("You are about to exit the program!");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			javafx.application.Platform.exit();
		}
	}
	
	/**
	 * Launches the program
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
}
