import javafx.scene.paint.*;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Window extends Application{
	Button button;
	Stage window;
	Scene scene1, scene2; 
	Rectangle r = new Rectangle(100,0,60,60);
	StackPane layout2 = new StackPane();
	
	Image image = new Image ("file:///C:/Users/danie/eclipse-workspace/JavaFX Tut/src/ship.jpg");
	//Image image = new Image (file.toURI().toString());
	ImageView iv = new ImageView(image);
	

	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Space Invaders");
		
		//Pantalla principal
		Label label1 = new Label("Este en un juego donde, para ganar, es necesario eliminar todas las naves enemigas");
		button = new Button("Emepezar Juego ");
		button.setOnAction(e -> window.setScene(scene2));
		VBox layout = new VBox(20);
		layout.getChildren().addAll(label1,button);
		scene1 = new Scene(layout, 500, 300);
		
		//pantalla Juego
		scene2 = new Scene(layout2, 800,800);
		layout2.setStyle("-fx-background-color: black");
		r.setFill(new ImagePattern(image));
		r.setTranslateX(0);
		r.setTranslateY(370);
		layout2.getChildren().addAll(r);
		scene2.setOnKeyPressed(e -> {
			switch(e.getCode()) {
			case LEFT:
				r.setTranslateX(r.getTranslateX()-10);
				break;
			case RIGHT:
				r.setTranslateX(r.getTranslateX()+10);
				break;
			case SPACE:	
				try {
					Rectangle b = this.addBullet();
					int i = 370;
					while(i != 0)
					{
						layout2.getChildren().remove(b);
						b.setTranslateY(b.getTranslateY()-10);
						layout2.getChildren().add(b);
						System.out.println(b.getTranslateY());
						Thread.sleep(100);
						i = i -10;
						
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;

			}
				
			
		});
		
		window.setScene(scene1);
		window.show();
		
	}
	
	private Rectangle addBullet() throws Exception{
		Task<Rectangle> task = new Bullet(r.getTranslateX(), r.getTranslateY());
		//Bullet bullet = new Bullet(r.getTranslateX(), r.getTranslateY());
		Thread thread = new Thread(task);
		thread.start();
		Rectangle b = task.get();
		layout2.getChildren().add(b);
		return b;
		b.setTranslateY(b.getTranslateY()-10);
		
		
		
	//	Rectangle b = bullet.get();
		//layout2.getChildren().add(b);
		//return b;
		

	}
}


































