package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXProgressBar;

import application.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ChargementController implements Initializable {

	
	 @FXML
	 private Label label;
	 
	 @FXML
	    private Label label1;
	
	@FXML
    private ProgressIndicator progrssIndicator;
    
    private Main main = new Main();
    
    public int i = 0 ;
    public int j = 0;
    
    Timeline counter;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		System.out.println(progrssIndicator.getAccessibleText());
		
		counter = new Timeline(new KeyFrame(Duration.millis(50), e -> Update()));
		counter.setCycleCount(Timeline.INDEFINITE);
		counter.play();
		
	}
	
	public void Update()
	{
		if(i <= 100)
		{
			progrssIndicator.setProgress(i/100.0);
			label.setText(i+"%");
			i++;
			if(j<10 || j>90)
				label1.setText("Chargement...");
			if(j<45 && j>=10)
				label1.setText("Chargement des fichiers .fxml...");
			if(j<55 && j>=45)
				label1.setText("Chargement des fichiers .png et .jpg...");
			if(j<=90 && j>=55)
				label1.setText("Chargement des fichiers .java");
			j++;
			
		}
		else{
			counter.stop();
			main.getStage().close();
			try {
				main.Login(new Stage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
