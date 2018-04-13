package Hileras;

import Principal.Invaders_list;
import Principal.Window;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.Scene;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Basic extends Window{
	Rectangle inv = new Rectangle(100,-0,30,30);
	Rectangle inv1 = new Rectangle(200,-0,30,30);
	Rectangle inv2 = new Rectangle(300,-0,30,30);
	int pos;
	StackPane layout2 = new StackPane();
	
	public Invaders_list getlista() {
		return listaEnemigos;
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
				System.out.println(listaEnemigos.getPos());
		
				System.out.println(listaEnemigos.getRect(listaEnemigos.getSize()));

				listaEnemigos.dele(listaEnemigos.getPos());
				listaEnemigos.getRect();
				
				try {
					pos=0;
					invaderDisplay(inv);
					invaderDisplay(inv1);
					invaderDisplay(inv2);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		});
	}
	
	
	
	//movimiento invaders
	
	public void invaderDisplay(Rectangle inv) throws Exception{
		//inv.setFill(new ImagePattern(image));
		inv.setFill(Color.RED);
		double rep = 0.0;
		PathTransition phtransition = new PathTransition();
		Polyline pl = new Polyline();
		pl.getPoints().addAll(new Double[] {
			450.0+pos , -350.0,
			-220.0+pos,-350.0,
			-220.0+pos,-250.0,
			450.0+pos,-250.0,
			450.0+pos,-150.0,
			-220.0+pos,-150.0,
			-220.0+pos,-50.0,
			450.0+pos,-50.0,
			450.0+pos,50.0,
			-220.0+pos,50.0,
			-220.0+pos,150.0,
			450.0+pos,150.0,
			450.0+pos,250.0,
			-220.0+pos,250.0,
			-220.0+pos,350.0,
			450.0+pos,350.0,
			450.0+pos,450.0		
		});
		switch(listaEnemigos.getSize()) {
		case(1): 
			phtransition.setNode(inv);
			phtransition.setDuration(Duration.seconds(20));
			phtransition.setPath(pl);
			phtransition.play();
			
		case(2):
			rep += 0.2;
			phtransition.setNode(inv);
			phtransition.setDuration(Duration.seconds(10+rep));
			phtransition.setPath(pl);
			phtransition.play();
			pos+=40;
			
		case(3):	
			rep += 0.2;
			phtransition.setNode(inv);
			phtransition.setDuration(Duration.seconds(10+rep));
			phtransition.setPath(pl);
			phtransition.play();
			pos+=40;
		}	
	}
}
