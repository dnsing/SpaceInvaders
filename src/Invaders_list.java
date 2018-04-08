import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Invaders_list{
	Invaders head;
	Invaders temp;
	int size;
	
	public Invaders_list() {
		head = null;
		temp = null;
		size=0;
		
	}
	
	public void add(Rectangle inv) {
		if(head==null) {
			head =  new Invaders(inv);
			head.next = null;
			temp = head;
		}
		else {
			Invaders temp1 = this.head;
			while(temp1.getNext()!=null) {
				temp1 = temp1.getNext();
			}
			temp1.setNext((new Invaders(inv)));
		}
		size++;
	}
	
	public void deleteF() {
		head = head.getNext();
		size--;
	}
	
	public void dele(int i) {

		if(i>size) {
			throw new IndexOutOfBoundsException("indice no valido");
		}
		else if(i==0) {
			this.deleteF();
		} 
		else {
			int cont = 0;
			Invaders temporal = head;
			while(cont < i -1) {
				temporal = temporal.getNext();
				cont++;
			}
			temporal.setNext(temporal.getNext().getNext());
		}
		size--;
	}
	
	public int getPos() {
		return size-1;
	}
	public void getRect() {
		Invaders temp1 = head;
		while(temp1 != null) {
			System.out.println(temp1);
			temp1 = temp1.next;
			
		}
	}
}
