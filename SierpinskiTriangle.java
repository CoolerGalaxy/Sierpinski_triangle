import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class SierpinskiTriangleFractal extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		// Create components
		TrianglePane trianglePane = new TrianglePane();
		Label lblEntry = new Label("Enter an order:");
		TextField tfOrder = new TextField();
		tfOrder.setOnAction(e -> trianglePane.setOrder(Integer.parseInt(tfOrder.getText())));
		tfOrder.setPrefColumnCount(4);
		tfOrder.setAlignment(Pos.BOTTOM_RIGHT);
		
		// Group entry components
		HBox entryBox = new HBox(15);
		entryBox.setAlignment(Pos.CENTER);
		entryBox.getChildren().addAll(lblEntry, tfOrder);
		
		// Place components
		BorderPane appPane = new BorderPane();
		appPane.setCenter(trianglePane);
		appPane.setBottom(entryBox);
		
		// Set scene
        Scene scene = new Scene(appPane, 210, 220);
        scene.widthProperty().addListener(e -> trianglePane.drawTriangle());
        scene.heightProperty().addListener(e -> trianglePane.drawTriangle());
		
		// Set stage
		primaryStage.setTitle("SierpinskiTriangleFractal");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	static class TrianglePane extends Pane {
		
		private int triangleOrder = 0;
		
		public void setOrder(int triangleOrder) {
			this.triangleOrder = triangleOrder;
			drawTriangle(); // redraw when order member changes
		}
		
		public void drawTriangle() {
			
	         // remove previous triangles
         	this.getChildren().clear();
			
			// point2d constructor: Point2D(double x, double y)
            Point2D topPoint = new Point2D(this.getWidth()/2, 15);
            Point2D leftPoint = new Point2D(15, this.getHeight()-15);
            Point2D rightPoint = new Point2D(this.getWidth()-15, this.getHeight()-15);
            
            displayTriangle(triangleOrder, topPoint, leftPoint, rightPoint);
		}
		
		public void displayTriangle(int triangleOrder, Point2D topPoint, Point2D leftPoint, Point2D rightPoint) {
			
			if (triangleOrder == 0) { // base case
				
				// polygon constructor: Polygon(double... points)
				Polygon triangle = new Polygon(
						topPoint.getX(), topPoint.getY(),
						leftPoint.getX(), leftPoint.getY(),
						rightPoint.getX(), rightPoint.getY());
				
				triangle.setStroke(Color.BLACK);
				triangle.setFill(Color.BLACK);
				
				this.getChildren().add(triangle);
				
			} else { // recursive calls to midpoints
				
				// get midpoints
                Point2D midLeft = getMid(topPoint, leftPoint);
                Point2D midBottom = getMid(leftPoint, rightPoint);
                Point2D midRight = getMid(rightPoint, topPoint);
                
                // perform recursion on the function
                displayTriangle(triangleOrder-1, topPoint, midLeft, midRight);
                displayTriangle(triangleOrder-1, midLeft, leftPoint, midBottom);
                displayTriangle(triangleOrder-1, midRight, midBottom, rightPoint);
			}
		}
		
		private Point2D getMid(Point2D point1, Point2D point2) {
			return new Point2D((point1.getX() + point2.getX())/2, (point1.getY() + point2.getY())/2);
		}
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}


