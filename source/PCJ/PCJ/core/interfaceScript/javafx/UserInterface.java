package core.interfaceScript.javafx;

import java.io.IOException;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class UserInterface extends Application {
    
    // --- Start Methods  
    // --- Start Override
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			List<String> args = getParameters().getRaw();
			if (args.isEmpty()) {
				throw new IOException("Path manquant");
			}
			else {
				Font.loadFont(getClass().getResourceAsStream("/core/assets/font/GoodDogCool.ttf"), 14);
				FXMLLoader fxml = new FXMLLoader(getClass().getClassLoader().getResource(args.get(0)));
				Scene scene = new Scene(fxml.load(), 1920, 1080);
//				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // tempory

//				Rectangle2D screen= Screen.getPrimary().getVisualBounds();
//				double userWidth = screen.getWidth();
//				double userHeight = screen.getHeight();

//				double userWidth = screenSize.getWidth();
//				double userHeight = screenSize.getHeight();
//				
//				double sceneWidth = scene.getWidth();
//				double sceneHeight = scene.getHeight();
//				double ratio = sceneWidth / sceneHeight;
//			    double scaleFactor = userWidth / userHeight > ratio ? 
//			    		userHeight / sceneHeight : userWidth / sceneWidth;
//			    if (scaleFactor >= 1) {
//			          Scale scale = new Scale(1.5/scaleFactor, 1.5/scaleFactor);
//			          scale.setPivotX(0);
//			          scale.setPivotY(0);
//			          for(Node n : scene.getRoot().getChildrenUnmodifiable()) {
//			        	  n.getTransforms().setAll(scale);
//			          }
	//
//			    }
			    // Scaling isn't totaly oparationnal
				primaryStage.setTitle("MOW");
				primaryStage.setScene(scene);
				primaryStage.setFullScreen(true);
				primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
				primaryStage.show();
				
			}
		}
		catch(Exception e) {
			
		}
		
	}
	// --- End Override
	// --- End Methods
}
