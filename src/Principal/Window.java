package Principal;

import javafx.scene.paint.*;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Hileras.Basic;
import javafx.animation.PathTransition;
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
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import jdk.nashorn.internal.runtime.Undefined;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Window extends Application{
	Button button;
	Stage window;
	Scene scene1, scene2; 
	Rectangle r = new Rectangle(100,0,60,60);
	StackPane layout2 = new StackPane();
	
	Image image = new Image ("file:///C:/Users/danie/eclipse-workspace/JavaFX Tut/src/ship.jpg");
	
	Image invader = new Image ("file:///C:/Users/danie/eclipse-workspace/JavaFX Tut/src/invader.jpg");
	String musicfile = "shoot.wav";
	Media sound = new Media(new File(musicfile).toURI().toString());
	MediaPlayer mediaPlayer = new MediaPlayer(sound);
	
	Rectangle inv = new Rectangle(100,-0,30,30);
	Rectangle inv1 = new Rectangle(200,-0,30,30);
	Rectangle inv2 = new Rectangle(300,-0,30,30);
	Invaders_list listaEnemigos = new Invaders_list();

	int pos;

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
		
		
		addInvader();
		if (listaEnemigos.getSize()==0) {
			System.out.println("Game Over");
		}
		
		//teclado
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
					//mediaPlayer.play();
					System.out.println(listaEnemigos.getSize());
					for(int i=0;i<listaEnemigos.getSize();i++) {
						this.colision(addBullet(r), listaEnemigos.getRect(i+1));
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mediaPlayer.stop();
				//
				break;
			}
		});
			
		window.setScene(scene1);
		window.show();
		}
	
	public Rectangle addBullet(Rectangle rect) throws Exception{
		Bullet bullet = new Bullet(rect.getTranslateX(), rect.getTranslateY());
		Rectangle b = bullet.call();
		layout2.getChildren().add(b);
		return b;
	}
	

	public void colision(Rectangle b, Rectangle object) {
		TranslateTransition transition = new TranslateTransition();		
		transition.setDuration(Duration.seconds(1));
		transition.setToY(-420);
		transition.setNode(b);
		transition.play();
		
		BooleanBinding collision = Bindings.createBooleanBinding( () -> 
	    b.getBoundsInParent().intersects(object.getBoundsInParent()),
	    b.boundsInParentProperty(),
	    object.boundsInParentProperty());

		collision.addListener((obs, wasColliding, isNowColliding) -> {
			if (!layout2.getChildren().contains(object)) {
				return;
			}
			else if(listaEnemigos.getPos() <= 0) {
				return;
			}
			else if (isNowColliding) {
				System.out.println("choco");
				transition.stop();
				layout2.getChildren().removeAll(object,b); //cuando colisiona elimina el cudro
				
				System.out.println(listaEnemigos.getSize());
				System.out.println(listaEnemigos.getRect(listaEnemigos.getSize()));

				listaEnemigos.dele(listaEnemigos.getPos());
				listaEnemigos.getRect();
				
				try {
					pos=0;
					invaderDisplay(inv);
					pos=0;
					invaderDisplay(inv1);
					pos=0;
					invaderDisplay(inv2);
					pos=0;

					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		});
	}
	
	public void addInvader() throws Exception {
		listaEnemigos.add(inv);
		listaEnemigos.add(inv1);
		listaEnemigos.add(inv2);
		layout2.getChildren().addAll(inv,inv1, inv2);
		invaderDisplay(inv);
		invaderDisplay(inv1);
		invaderDisplay(inv2);
		int pos=0;
	}
	
	//movimiento invaders
	
	public void invaderDisplay(Rectangle inv) throws Exception{
		inv.setFill(new ImagePattern(invader));
		//inv.setFill(Color.RED);
		double rep = 0.0;
		PathTransition phtransition = new PathTransition();
		Polyline pl = new Polyline();
		pl.getPoints().addAll(new Double[] {
			450.0+pos , -350.0,
			-100.0+pos,-350.0,
			-100.0+pos,-250.0,
			450.0+pos,-250.0,
			450.0+pos,-150.0,
			-100.0+pos,-150.0,
			-100.0+pos,-50.0,
			450.0+pos,-50.0,
			450.0+pos,50.0,
			-100.0+pos,50.0,
			-100.0+pos,150.0,
			450.0+pos,150.0,
			450.0+pos,250.0,
			-100.0+pos,250.0,
			-100.0+pos,350.0,
			450.0+pos,350.0,
			450.0+pos,450.0		
		});
		switch(listaEnemigos.getSize()) {
		case(1): 
			phtransition.setNode(inv);
			phtransition.setDuration(Duration.seconds(30));
			phtransition.setPath(pl);
			phtransition.play();
			
		case(2):
			rep += 0.2;
			phtransition.setNode(inv);
			phtransition.setDuration(Duration.seconds(30+rep));
			phtransition.setPath(pl);
			phtransition.play();
			pos+=40;
			
		case(3):	
			rep += 0.2;
			phtransition.setNode(inv);
			phtransition.setDuration(Duration.seconds(30+rep));
			phtransition.setPath(pl);
			phtransition.play();
			pos+=40;
		}	
	}
}