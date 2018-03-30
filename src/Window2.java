import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Window2 {
	
	public  static void display(String titulo, String mensaje) {
		Stage window =  new Stage();
		
		// obliga a no entrar en la ventana principal
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(titulo);
		window.setMinWidth(500);
		
		Label label = new Label(mensaje);
		Button closeB = new Button("cerrar ventana2");
		closeB.setOnAction(e -> window.close());
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(closeB,label);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}

}
