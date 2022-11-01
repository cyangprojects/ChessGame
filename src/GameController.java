import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameController extends Application {



    public static void main(String[] args)
   {

      launch(args);
   }
  public void start(Stage primaryStage) {
    Board b = new Board();
    Scene scene = new Scene(b.getGridPane());
    primaryStage.setTitle("Chess");
    primaryStage.setScene(scene); // Place in scene in the stage
    primaryStage.show();
    
  }
}