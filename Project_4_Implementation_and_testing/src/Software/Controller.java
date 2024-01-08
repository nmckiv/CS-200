package Software;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * The Controlller class is what controls all of what happens within the javaFX gui
 * 
 * @author Rowan Romanauskas
 */
public class Controller {
	//Create global variables
	private Stage stage;
	private Scene scene;
	private Parent root;
	private String database = "Provider";
	private String userType = "Operator";
	private String membNumber;
	private String provNumber;
	private String[] reportOptions = { "Manager Report", "Provider Report", "Member Report" };
	private Member member;
	public String memberName;
	public String memberNumber;
	public String memberStatus;
	private ProviderEntity provider;
	private Controller previousController;
	private String currentPane;
	private String serviceCode;
	FileChooser fileChooser = new FileChooser();
	//Initialize all of the elements used within the gui
	@FXML
	private TextField usernameTxt;
	@FXML
	private TextField passwordTxt;
	@FXML
	private Label logInLbl;
	@FXML
	private Button databaseBtn;
	@FXML
	private TextField provNameTxt;
	@FXML
	private TextField provAddressTxt;
	@FXML
	private TextField provCityTxt;
	@FXML
	private TextField provStateTxt;
	@FXML
	private TextField provZipTxt;
	@FXML
	private Button provAddBtn;
	@FXML
	private DialogPane addProviderDialog;
	@FXML
	private DialogPane addMemberDialog;
	@FXML
	private Button membAddBtn;
	@FXML
	private TextField membNameTxt;
	@FXML
	private TextField membAddressTxt;
	@FXML
	private TextField membCityTxt;
	@FXML
	private TextField membStateTxt;
	@FXML
	private TextField membZipTxt;
	@FXML
	private TextField membStatusTxt;
	@FXML
	private TextField provEditNameTxt;
	@FXML
	private TextField provEditAddressTxt;
	@FXML
	private TextField provEditCityTxt;
	@FXML
	private TextField provEditStateTxt;
	@FXML
	private TextField provEditZipTxt;
	@FXML
	private TextField provNumberTxt;
	@FXML
	private DialogPane editProviderDialog;
	@FXML
	private DialogPane editMemberDialog;
	@FXML
	private TextField membEditNameTxt;
	@FXML
	private TextField membEditAddressTxt;
	@FXML
	private TextField membEditCityTxt;
	@FXML
	private TextField membEditStateTxt;
	@FXML
	private TextField membEditZipTxt;
	@FXML
	private TextField membNumberTxt;
	@FXML
	private TextField membEditStatusTxt;
	@FXML
	private TextField membRemoveTxt;
	@FXML
	private TextField provRemoveTxt;
	@FXML
	private Label reportFileLocationLbl;
	@FXML
	private Label correctMembNameLbl;
	@FXML
	private Label correctMembNumberLbl;
	@FXML
	private Label correctMembStatusLbl;
	@FXML
	private TextField serviceCodeTxt;
	@FXML
	private ChoiceBox<String> reportChoiceBox;
	@FXML
	private TextField membBillTxt;
	@FXML
	private Button loadMembInfoBtn;
	@FXML
	private Label correctServiceLbl;
	@FXML
	private Label correctFeeLbl;
	@FXML
	private Label correctCodeLbl;
	@FXML
	private Button loadServiceInfoBtn;
	@FXML
	private Button loadInfoBtn;
	@FXML
	private DatePicker datePicker;
	@FXML
	private Label infoMembNumberLbl;
	@FXML
	private Label infoServiceLbl;
	@FXML
	private Label infoFeeLbl;
	@FXML
	private TextArea commentsTxt;
	@FXML
	private Label infoServiceCodeLbl;

	//Call other classes so methods can be easily referenfced
	ModifyMemberDatabase modifyMemberDatabase = new ModifyMemberDatabase();
	ModifyProviderDatabase modifyProviderDatabase = new ModifyProviderDatabase();
	ProviderController providerController = new ProviderController();
	RecordService recordService = new RecordService();
	ManagerController managerController = new ManagerController();

	/**
	 * logIn verifies that the user has entered the proepr username and password then takes them to the desired menu
	 * @param event
	 * @throws IOException
	 */
	public void logIn(ActionEvent event) throws IOException {
		//get the username and password that were entered
		String username = "";
		String password = "";
		try {
			username = usernameTxt.getText();
			password = passwordTxt.getText();
		} catch (Exception e) {
			System.out.println(e);
		}
		//check the username and password with the database let them in if correct otherwise send them an error
		LogIn loginAttempt = new LogIn();
		if (loginAttempt.VerifyUser(username, password, userType) == true) {
			switch (userType) {
			case "Manager":
				changePane(event, "ManagerMenu.fxml");
				break;
			case "Provider":
				changePane(event, "ProviderMenu.fxml");
				break;
			default:
				changePane(event, "OperatorMenu.fxml");
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Invalid Credentials");
			alert.setHeaderText("Your username or password is incorrect");
			alert.show();
		}
	}

	/**
	 * sendAlert is a method that makes it easier to send dialog alerts to the user
	 * @param type
	 * @param title
	 * @param headerText
	 */
	public void sendAlert(AlertType type, String title, String headerText) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.show();
	}

	/**
	 * changePane is amethod that makes it easier to change the current pane of the gui
	 * @param event
	 * @param pane
	 */
	public void changePane(ActionEvent event, String pane) {
		// create a new fxml loader
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource(pane));
		try {
			root = (Parent) loader.load();
			// Get the controller of the newly loaded FXML
			Controller newController = loader.getController();
			// Pass the reference of the current controller to the newly loaded controller
			newController.setPreviousController(this);
			newController.currentPane = pane;
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * setPreviousController allows data from previous panes to be accessed
	 * @param controller
	 */
	private void setPreviousController(Controller controller) {
		this.previousController = controller;
	}

	/**
	 * logOut brings the user back to the main menu
	 * @param event
	 * @throws IOException
	 */
	public void logOut(ActionEvent event) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Log Out?");
		alert.setHeaderText("You are about to log out?");
		if (alert.showAndWait().get() == ButtonType.OK) {
			changePane(event, "LogInMenu.fxml");
		}
	}

	/**
	 * A function to close the program after prompting the user if they really want to close
	 * @param event
	 */
	public void closeProgram(MouseEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Close Program");
		alert.setHeaderText("You are about to exit the program!");

		if (alert.showAndWait().get() == ButtonType.OK) {
			javafx.application.Platform.exit();
		}
	}

	/**
	 * swaps what menu the user is logging into
	 * @param event
	 */
	public void swapToProvider(ActionEvent event) {
		userType = "Provider";
		logInLbl.setText("Provider Log In");
	}

	/**
	 * swaps what menu the user is logging into
	 * @param event
	 */
	public void swapToManager(ActionEvent event) {
		userType = "Manager";
		logInLbl.setText("Manager Log In");
	}

	/**
	 * swaps what menu the user is logging into
	 * @param event
	 */
	public void swapToOperator(ActionEvent event) {
		userType = "Operator";
		logInLbl.setText("Operator Log In");
	}

	/**
	 * swap which database the provider is attempting to access
	 * @param event
	 */
	public void swapDatabase(ActionEvent event) {
		if (database.equals("Provider")) {
			database = "Member";
			databaseBtn.setText("Member Database");
		} else if (database.equals("Member")) {
			database = "Provider";
			databaseBtn.setText("Provider Database");
		}
	}

	/**
	 * An add function which routes the user to addMemver or addProvider
	 * @param event
	 */
	public void add(ActionEvent event) {
		if (database.equals("Provider")) {
			changePane(event, "AddProvider.fxml");
		} else if (database.equals("Member")) {
			changePane(event, "AddMember.fxml");
		}
	}

	/**
	 * A remove function which routes the user to removeMember or removeProvider
	 * @param event
	 */
	public void remove(ActionEvent event) {
		if (database.equals("Provider")) {
			changePane(event, "RemoveProvider.fxml");
		} else if (database.equals("Member")) {
			changePane(event, "RemoveMember.fxml");
		}
	}

	/**
	 * A modify function which routes the user to editMember or editProvider
	 * @param event
	 */
	public void modify(ActionEvent event) {
		if (database.equals("Provider")) {
			changePane(event, "EditProvider.fxml");
		} else if (database.equals("Member")) {
			changePane(event, "EditMember.fxml");
		}
	}

	/**
	 * Adds a member to the member database
	 * @param event
	 */
	public void addMember(ActionEvent event) {

		try {
			//gets the data input from the gui
			String name = membNameTxt.getText();
			String address = membAddressTxt.getText();
			String city = membCityTxt.getText();
			String state = membStateTxt.getText();
			String zip = membZipTxt.getText();
			String status = membStatusTxt.getText();
			//checks to make sure the input is valid
			if (name != "" && address != "" && city != "" && state != "" && zip != "" && status != "") {
				modifyMemberDatabase.addMember(name, address, city, state, zip, status);
				sendAlert(AlertType.CONFIRMATION, "Member Added", "A new member has been successfully added");
				changePane(event, "OperatorMenu.fxml");
			} else {
				sendAlert(AlertType.WARNING, "Error Invalid Information",
						"Please enter valid information for all fields");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Adds a provider to the provider database
	 * @param event
	 */
	public void addProvider(ActionEvent event) {
		try {
			//gets the data input from the gui
			String name = provNameTxt.getText();
			String address = provAddressTxt.getText();
			String city = provCityTxt.getText();
			String state = provStateTxt.getText();
			String zip = provZipTxt.getText();
			//checks to make sure the input is valid
			if (name != "" && address != "" && city != "" && state != "" && zip != "") {
				sendAlert(AlertType.CONFIRMATION, "Provider Added", "A new provider has been successfully added");
				modifyProviderDatabase.addProvider(name, address, city, state, zip);
				changePane(event, "OperatorMenu.fxml");
			} else {
				sendAlert(AlertType.WARNING, "Error Invalid Information",
						"Please enter valid information for all fields");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Edits a provider within the provider database
	 * @param event
	 */
	public void editProvider(ActionEvent event) {
		String name = "";
		String address = "";
		String city = "";
		String state = "";
		String zip = "";
		try {
			//gets the input from the gui
			name = provEditNameTxt.getText();
			address = provEditAddressTxt.getText();
			city = provEditCityTxt.getText();
			state = provEditStateTxt.getText();
			zip = provEditZipTxt.getText();
			//checks to make sure the input is valid
			if (name != "" && address != "" && city != "" && state != "" && zip != "") {
				sendAlert(AlertType.CONFIRMATION, "Provider Edited", "The selected provider info has been edited");
				modifyProviderDatabase.editProvider(provNumber, name, address, city, state, zip);
				changePane(event, "OperatorMenu.fxml");
			} else {
				sendAlert(AlertType.WARNING, "Error Invalid Information",
						"Please enter valid information for all fields");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * A helper function for editProvider
	 * @param event
	 */
	public void selectProvider(ActionEvent event) {
		provNumber = "";
		try {
			provNumber = provNumberTxt.getText();
			provider = modifyProviderDatabase.retrieveProvider(provNumber);
			//Make sure its a valid provider
			if (this.provider == null) {
				throw new IllegalArgumentException("Invalid provider number");
			}
			//update the gui based off the provider info
			provEditNameTxt.setText(provider.name);
			provEditAddressTxt.setText(provider.address);
			provEditCityTxt.setText(provider.city);
			provEditStateTxt.setText(provider.state);
			provEditZipTxt.setText(provider.zip);
			editProviderDialog.setVisible(false);
		} catch (Exception e) {
			sendAlert(AlertType.WARNING, "Error Invalid Provider Number", "Please enter a valid provider number");
		}
	}

	/**
	 * Edits a member within the member database
	 * @param event
	 */
	public void editMember(ActionEvent event) {
		String name = "";
		String address = "";
		String city = "";
		String state = "";
		String zip = "";
		String status = "";
		try {
			//gets the input from the gui
			name = membEditNameTxt.getText();
			address = membEditAddressTxt.getText();
			city = membEditCityTxt.getText();
			state = membEditStateTxt.getText();
			zip = membEditZipTxt.getText();
			status = membEditStatusTxt.getText();
			//makes sure that the input is valid
			if (name != "" && address != "" && city != "" && state != "" && zip != "" && status != "") {
				sendAlert(AlertType.CONFIRMATION, "Member Edited", "The selected member info has been edited");
				modifyMemberDatabase.editMember(name, membNumber, address, city, state, zip, status);
				changePane(event, "OperatorMenu.fxml");
			} else {
				sendAlert(AlertType.WARNING, "Error Invalid Information",
						"Please enter valid information for all fields");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * A helped function to assist editMember
	 * @param event
	 */
	public void selectMember(ActionEvent event) {
		membNumber = "";
		try {
			membNumber = membNumberTxt.getText();
			member = modifyMemberDatabase.retrieveMember(membNumber);
			//make sure user selected a valid member
			if (this.member == null) {
				throw new IllegalArgumentException("Invalid member number");
			}
			//Update the gui based off member info
			membEditNameTxt.setText(member.memberName);
			membEditAddressTxt.setText(member.memberAddress);
			membEditCityTxt.setText(member.memberCity);
			membEditStateTxt.setText(member.memberState);
			membEditZipTxt.setText(member.memberZIP);
			membEditStatusTxt.setText(member.memberStatus);
			editMemberDialog.setVisible(false);
		} catch (Exception e) {
			sendAlert(AlertType.WARNING, "Error Invalid Member Number", "Please enter a valid member number");
		}
	}

	/**
	 * removes a member from the member database
	 * @param event
	 */
	public void removeMember(ActionEvent event) {
		String membNumber = "";
		try {
			membNumber = membRemoveTxt.getText();
		} catch (Exception e) {
			System.out.println(e);
		}
		//makes sure member is valid
		if (modifyMemberDatabase.removeMember(membNumber) == 0) {
			//removes the member from the database
			sendAlert(AlertType.CONFIRMATION, "Member Removed", "The selected member has been removed");
			changePane(event, "OperatorMenu.fxml");
		} else {
			sendAlert(AlertType.WARNING, "Error Invalid Member Number", "Please enter a valid member number");
		}
	}

	/**
	 * removes a provide from the provider database
	 * @param event
	 */
	public void removeProvider(ActionEvent event) {
		provNumber = "";
		try {
			provNumber = provRemoveTxt.getText();
			modifyProviderDatabase.retrieveProvider(provNumber);
			//make sure the user is targeting a valid provider
			if (this.provider == null) {
				throw new IllegalArgumentException("Invalid provider number");
			}
			//removes the provider from the database
			modifyProviderDatabase.removeProvider(provNumber);
			sendAlert(AlertType.CONFIRMATION, "Provider Removed", "The selected provider has been removed");
			changePane(event, "OperatorMenu.fxml");
		} catch (Exception e) {
			sendAlert(AlertType.WARNING, "Error Invalid Providerr Number", "Please enter a valid provider number");
		}
	}

	/**
	 * returns the user to the opertor screen
	 * @param event
	 */
	public void retrunToOperator(ActionEvent event) {
		changePane(event, "OperatorMenu.fxml");
	}

	/**
	 * progresses the user to the next step in billing ChocAn
	 * @param event
	 */
	public void serviceCode(ActionEvent event) {
		try {
			serviceCode = serviceCodeTxt.getText();
			if (providerController.getService(Integer.parseInt(serviceCode)) == null) {
				throw new IllegalArgumentException("Enter valid service code");
			}
			membNumber = previousController.membNumber;
			changePane(event, "ServiceConfirmation.fxml");
		} catch (Exception e) {
			sendAlert(AlertType.WARNING, "Error Invalid Service Code", "Please enter a valid service code");
		}
	}

	/**
	 * generates the selected report at the selected file location
	 * @param event
	 */
	public void generateReport(ActionEvent event) {
		String path = "";
		try {
			path = reportFileLocationLbl.getText();
			String choice = reportChoiceBox.getValue();
			switch (choice) {
			case ("Member Report"):
				path = path + "/MemberReport.txt";
				break;
			case ("Provider Report"):
				path = path + "/ProviderReport.txt";
				break;
			case ("Manager Report"):
				path = path + "/ManagerReport.txt";
			}
			ManagerController.generateIndividualReport(choice, path, modifyMemberDatabase, modifyProviderDatabase,
					recordService, providerController);
			sendAlert(AlertType.CONFIRMATION, "Report Generated", "Your report was successfully generated");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * progresses the user through the bill ChocAn process in the gui
	 * @param event
	 */
	public void billChocAn(ActionEvent event) {
		changePane(event, "BillChocAn.fxml");
	}

	/**
	 * The provider selects a member to bill 
	 * @param event
	 */
	public void billChocAnSelectMember(ActionEvent event) {
		membNumber = "";
		try {
			membNumber = membBillTxt.getText();
			member = modifyMemberDatabase.retrieveMember(membNumber);
			if (this.member == null) {
				throw new IllegalArgumentException("Invalid Member Number");
			}
			changePane(event, "MemberConfirmation.fxml");
		} catch (Exception e) {
			sendAlert(AlertType.WARNING, "Error Invalid Member Number", "Please enter a valid member number");
		}

	}

	/**
	 * A helper function to load info into the gui
	 * @param event
	 */
	public void loadMembInfo(ActionEvent event) {
		member = modifyMemberDatabase.retrieveMember(previousController.membNumber);
		correctMembNameLbl.setText(member.memberName);
		correctMembNumberLbl.setText(member.memberNumber);
		correctMembStatusLbl.setText(member.memberStatus);
		loadMembInfoBtn.setVisible(false);
	}

	/**
	 * A helper function to load info into the gui
	 * @param event
	 */
	public void loadServiceInfo(ActionEvent event) {
		membNumber = previousController.membNumber;
		serviceCode = previousController.serviceCode;
		correctServiceLbl.setText(providerController.getService(Integer.parseInt(previousController.serviceCode)));
		correctFeeLbl.setText(
				Double.toString(providerController.getPrice(Integer.parseInt(previousController.serviceCode))));
		correctCodeLbl.setText(serviceCode);
		loadServiceInfoBtn.setVisible(false);
	}

	/**
	 * Brings the user back to the last pane they were on in the gui
	 * @param event
	 */
	public void lastPane(ActionEvent event) {
		changePane(event, previousController.currentPane);
	}

	/**
	 * the provider verifies the selected member to bill is correct
	 * @param event
	 */
	public void correctMember(ActionEvent event) {
		membNumber = previousController.membNumber;
		changePane(event, "ServiceCode.fxml");
	}

	/**
	 * The provider verifies the selected service is correct
	 * @param event
	 */
	public void correctService(ActionEvent event) {
		membNumber = previousController.membNumber;
		serviceCode = previousController.serviceCode;
		changePane(event, "AdditionalInfo.fxml");
	}

	/**
	 * A helper function to load info into the gui
	 * @param event
	 */
	public void loadInfo(ActionEvent event) {
		membNumber = previousController.memberNumber;
		serviceCode = previousController.serviceCode;
		datePicker.setVisible(true);
		provNumberTxt.setVisible(true);
		infoMembNumberLbl.setText(previousController.membNumber);
		infoServiceCodeLbl.setText(previousController.serviceCode);
		infoServiceLbl.setText(providerController.getService(Integer.parseInt(previousController.serviceCode)));
		infoFeeLbl.setText(
				Double.toString(providerController.getPrice(Integer.parseInt(previousController.serviceCode))));
		commentsTxt.setVisible(true);
		loadInfoBtn.setVisible(false);
	}

	/**
	 * Completes the billing process
	 * @param event
	 */
	public void completeBilling(ActionEvent event) {
		String provNumber = "";
		String comments = "";
		LocalDate servDate = datePicker.getValue();
		try {
			provNumber = provNumberTxt.getText();
			comments = commentsTxt.getText();
			if (provNumber != "" && servDate != null) {
				String formattedDate = servDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
				recordService.addServiceRecord(formattedDate, previousController.membNumber, provNumber,
						previousController.serviceCode,
						providerController.getPrice(Integer.parseInt(previousController.serviceCode)), comments);
				changePane(event, "ProviderMenu.fxml");
			} else {
				sendAlert(AlertType.WARNING, "Error Invalid Information",
						"Please enter valid information for all fields");
			}
		} catch (Exception e) {

		}

	}

	/**
	 * Runs the main accounting procedure
	 * @param event
	 */
	public void runMainAccounting(ActionEvent event) {
		try {
			String path = System.getProperty("user.dir") + "/report.txt";
			ManagerController.mainAccountingProcedure(path, modifyMemberDatabase, modifyProviderDatabase,
					recordService, providerController);
			sendAlert(AlertType.CONFIRMATION, "Accounting Procedure", "The main accounting procedure has been run");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * selects a directory for reports to be downloaded to
	 * @param event
	 */
	public void directorySelecter(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File file = directoryChooser.showDialog(stage);
		if (file != null) {
			reportFileLocationLbl.setText(file.getAbsolutePath());
		}
		reportChoiceBox.getItems().addAll(reportOptions);
	}

	/**
	 * brings the user back to main login menu
	 * @param event
	 */
	public void backToLogIn(MouseEvent event) {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("LogInMenu.fxml"));
		try {
			root = (Parent) loader.load();
			// Get the controller of the newly loaded FXML
			Controller newController = loader.getController();
			// Pass the reference of the current controller to the newly loaded controller
			newController.setPreviousController(this);
			newController.currentPane = "LogInMenu.fxml";
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Completes the request provider directory operation
	 * @param event
	 */
	public void requestDirectory(ActionEvent event) {
		providerController.printProviderDirectory();
		sendAlert(AlertType.CONFIRMATION, "Provider Directory Created",
				"You have downloaded the provider directory report.txt");
	}

}
