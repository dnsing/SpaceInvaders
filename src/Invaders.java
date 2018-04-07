import javafx.scene.shape.Rectangle;

public class Invaders{
	double X;
	double Y;
	Object invader;
	Invaders next;

	public Invaders(Object invader){
		this.invader = invader;
		this.next = null;
		
		boolean alive;
		int speed;
	}
	
	public Object getRec() {
		return invader;
	}
	
	public void setNext(Invaders next) {
		this.next = next;
	}
	
	public Invaders getNext() {
		return next;
	}
		
	/*	Rectangle b = new Rectangle(X,Y,20,20);
		b.setFill(Color.WHITE);
		b.setTranslateX(X);
		b.setTranslateY(Y);
	*/
	
	
}
