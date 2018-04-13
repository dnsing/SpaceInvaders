package Principal;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bullet extends Task<Rectangle>{
	double X;
	double Y;

	public Bullet(double X, double Y){
		this.X = X;
		this.Y = Y;
	}
		
	protected Rectangle call() throws Exception {
		Rectangle b = new Rectangle(X,Y,5,5);
		b.setFill(Color.GREEN);
		b.setTranslateX(X);
		b.setTranslateY(Y);
		return b;
	}
	
	
}
