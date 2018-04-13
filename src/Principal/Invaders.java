package Principal;
import javafx.scene.shape.Rectangle;

public class Invaders{
	double X;
	double Y;
	Rectangle invader;
	Invaders next;

	public Invaders(Rectangle invader){
		this.invader = invader;
		this.next = null;
		
		boolean alive;
		int speed;
	}
	
	public Rectangle getRec() {
		return invader;
	}
	
	public void setNext(Invaders next) {
		this.next = next;
	}
	
	public Invaders getNext() {
		return next;
	}
	
}
