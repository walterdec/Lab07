package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.poweroutages.db.PowerOutageDAO;
import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PowerOutagesController{
	
	Model model;

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<Nerc> choiceBox;

    @FXML
    private TextField yearsTxtField;

    @FXML
    private TextField hoursTxtField;

    @FXML
    private Button button;

    @FXML
    private TextArea txtArea;

    @FXML
    void doCalcolaSoluzione(ActionEvent event) {
    	txtArea.clear();
    	Nerc selectedNerc = choiceBox.getSelectionModel().getSelectedItem();
    	try{
    		int anniMax = Integer.parseInt(yearsTxtField.getText());
    		int oreMax = Integer.parseInt(hoursTxtField.getText());
    		Set<PowerOutage> outages = model.cercaSoluzione(selectedNerc, anniMax, oreMax);
    		txtArea.appendText("Total people affected: "+Integer.toString(model.calcolaPersone(outages))+"\n");
    		for(PowerOutage p : outages) {
    			txtArea.appendText(p.toString()+"\n");
    		}
    	} catch(NumberFormatException e) {
    		txtArea.setText("Inserire numeri validi");
    	}
    	
    }

    @FXML
    void initialize() {
        assert choiceBox != null : "fx:id=\"choiceBox\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert yearsTxtField != null : "fx:id=\"yearsTxtField\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert hoursTxtField != null : "fx:id=\"hoursTxtField\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert button != null : "fx:id=\"button\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtArea != null : "fx:id=\"txtArea\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        model = new Model();
        ObservableList<Nerc> nercs = FXCollections.observableArrayList(model.getNercList());
        choiceBox.setItems(nercs);
        choiceBox.setValue(nercs.get(0));

    }
    
    public void setModel(Model model) {
  		this.model = model;
  	}
}
