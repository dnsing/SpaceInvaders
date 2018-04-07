import javafx.scene.paint.*;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
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
import javafx.util.Duration;

public class Window extends Application{
	Button button;
	Stage window;
	Scene scene1, scene2; 
	Rectangle r = new Rectangle(100,0,60,60);
	StackPane layout2 = new StackPane();
	
	Image image = new Image ("file:///C:/Users/danie/eclipse-workspace/JavaFX Tut/src/ship.jpg");
	//Image image = new Image (file.toURI().toString());
	ImageView iv = new ImageView(image);
	
	Rectangle col = new Rectangle(0,-300,400,20);

	
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
		
		//teclado
		col.setFill(Color.BLUE);
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
					this.addBullet();					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//
				break;

			}
				
			
		});
		
		addInvader();
		
		window.setScene(scene1);
		window.show();
		
	}
	
	private void addBullet() throws Exception{
		Bullet bullet = new Bullet(r.getTranslateX(), r.getTranslateY());
		Thread thread = new Thread(bullet);
		thread.start();
		Rectangle b = bullet.call();
		layout2.getChildren().add(b);
		TranslateTransition transition = new TranslateTransition();		
		transition.setDuration(Duration.seconds(1));
		transition.setToY(-400);
		transition.setNode(b);
		transition.play();
		
		//if (b.getBoundsInParent().intersects(col.getBoundsInParent())) {
		//	System.out.println("choco");
		//}
		
		BooleanBinding collision = Bindings.createBooleanBinding( () -> 
	    b.getBoundsInParent().intersects(col.getBoundsInParent()),
	    b.boundsInParentProperty(),
	    col.boundsInParentProperty());

	collision.addListener((obs, wasColliding, isNowColliding) -> {
	    if (isNowColliding) {
	    	System.out.println("choco");
	    	transition.stop();
	    	layout2.getChildren().remove(b); // cuando colisiona elimina el cudro
	    	
	    }
	});
	}
	
	private void addInvader() {
		Rectangle inv = new Rectangle(200,-0,30,30);
		Rectangle inv1 = new Rectangle(100,-0,30,30);
		inv.setFill(new ImagePattern(image));
		inv1.setFill(new ImagePattern(image));
		inv.setTranslateX(100);
		layout2.getChildren().addAll(inv,inv1);
		
		while(inv.getTranslateY()==400) {
			inv.setTranslateX(inv.getTranslateX()+30);
			if (inv.getTranslateX()==400 || inv.getTranslateX()==-400) {
				inv.setTranslateY(inv.getTranslateY()+20);
			}
			
		}
		
		Invaders_list listaEnemigos = new Invaders_list();
		listaEnemigos.add(inv);
		listaEnemigos.add(inv1);
		//listaEnemigos.getRect();
		
		
		
		//layout2.getChildren().addAll(inv,inv1);
	}
}


































