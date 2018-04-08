import javafx.scene.paint.*;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import jdk.nashorn.internal.runtime.Undefined;

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

	Rectangle inv = new Rectangle(200,-0,30,30);
	Rectangle inv1 = new Rectangle(100,-0,30,30);
	Invaders_list listaEnemigos = new Invaders_list();
	
	int pos = 0;
	ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

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
					//this.addBullet();		
					this.colision(addBullet(r), inv1);
					this.colision(addBullet(r), inv);
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
	
	
	public Rectangle addBullet(Rectangle rect) throws Exception{
		Bullet bullet = new Bullet(rect.getTranslateX(), rect.getTranslateY());
		Rectangle b = bullet.call();
		layout2.getChildren().add(b);
		return b;
	}
	
	public void colision(Rectangle b, Rectangle inv) {
		TranslateTransition transition = new TranslateTransition();		
		transition.setDuration(Duration.seconds(1));
		transition.setToY(-400);
		transition.setNode(b);
		transition.play();
		
		BooleanBinding collision = Bindings.createBooleanBinding( () -> 
	    b.getBoundsInParent().intersects(inv.getBoundsInParent()),
	    b.boundsInParentProperty(),
	    inv.boundsInParentProperty());

		collision.addListener((obs, wasColliding, isNowColliding) -> {
			if (!layout2.getChildren().contains(inv)) {
				return;
			}
			else if(listaEnemigos.getPos() < 0) {
				return;
			}
			else if (isNowColliding) {
				System.out.println("choco");
				transition.stop();
				layout2.getChildren().removeAll(inv,b); //cuando colisiona elimina el cudro
				System.out.println(listaEnemigos.getPos());
				listaEnemigos.dele(listaEnemigos.getPos());
				executorService.shutdown();
				listaEnemigos.getRect();
				
			}
		});
	}

	public void addInvader() throws Exception {
		layout2.getChildren().addAll(inv,inv1);
		//System.out.println(layout2.getChildren().contains(inv));
		
		invaderDisplay(inv);
		pos += 50;
		invaderDisplay(inv1);
		
		listaEnemigos.add(inv);
		listaEnemigos.add(inv1);
		//listaEnemigos.getRect();
		
		
		//movimiento invaders
	}
	public void invaderDisplay(Rectangle inv) throws Exception{
		inv.setFill(new ImagePattern(image));
		inv.setTranslateY(-350);
		inv.setTranslateX(380 - pos);
		
		final Runnable tarea = () -> {
			Platform.runLater(() -> {
			    try {
					this.colisionI(inv);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			});
		};
		
		executorService.scheduleAtFixedRate(tarea, 0, 2, TimeUnit.SECONDS); 
		
		double rep = 0.0;
		TranslateTransition transition = new TranslateTransition();		
		transition.setDuration(Duration.seconds(3-rep));
		transition.setToX(-320 - pos);
		transition.setNode(inv);
		transition.setAutoReverse(true);
		transition.setCycleCount(100);	
		transition.play();
		rep += 0.2;
	}
	
	public void colisionI(Rectangle inv) throws Exception {
		Rectangle b = addBullet(inv);
		//b.setTranslateY(-350);
	//	b.setTranslateX(inv.getTranslateX());
		TranslateTransition transition0 = new TranslateTransition();		
		transition0.setDuration(Duration.seconds(3));
		transition0.setToY(400);
		transition0.setNode(b);
		transition0.play();
		
		BooleanBinding collision = Bindings.createBooleanBinding( () -> 
	    b.getBoundsInParent().intersects(r.getBoundsInParent()),
	    b.boundsInParentProperty(),
	    r.boundsInParentProperty());
		
		collision.addListener((obs, wasColliding, isNowColliding) -> {
			if (isNowColliding) {
				System.out.println("choco");
				transition0.stop();
				layout2.getChildren().removeAll(r,b); //cuando colisiona elimina el cudro				
			}
		});
		
	}
	

}