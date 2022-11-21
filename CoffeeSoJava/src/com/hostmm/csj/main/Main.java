package com.hostmm.csj.main;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = FXMLLoader.load(getClass().getResource("/com/hostmm/csj/login/view/LoginView.fxml"));
			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("/com/hostmm/csj/style/css/adminMainStyle.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setMaximized(false);
			primaryStage.setTitle("Coffee So JAVA");
			primaryStage.getIcons()
			.add(new Image(getClass().getResourceAsStream("/com/hostmm/csj/style/image/coffee-beans.png")));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
