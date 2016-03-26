package src;

import java.awt.Point;
import java.util.Vector;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class InertiaUI extends Application {

	Polygon ply = new Polygon();

	@Override
	public void start(Stage stage) throws Exception {
		//Layout the layouts
		BorderPane bpMaster = new BorderPane();
		HBox title = new HBox();
		title.setAlignment(Pos.CENTER);
		VBox input = new VBox();
		HBox output = new HBox();
		input.setAlignment(Pos.CENTER);
		output.setAlignment(Pos.CENTER);

		BorderPane grid = new BorderPane();
		Pane pane = new Pane();
		GridPane gp = new GridPane();
		pane.getChildren().add(gp);
		HBox xAxis = new HBox(117);
		xAxis.setAlignment(Pos.CENTER_RIGHT);
		VBox yAxis = new VBox(110);
		grid.setCenter(pane);
		grid.setBottom(xAxis);
		grid.setLeft(yAxis);

		bpMaster.setCenter(grid);
		bpMaster.setLeft(input);
		bpMaster.setBottom(output);
		bpMaster.setTop(title);

		//Initialize the grid
		Rectangle[][] cells = new Rectangle[20][20];
		for (int i = 0; i <= 19; i++) {
			for (int j = 0; j<= 19; j++){
				Rectangle temp = new Rectangle(25, 25, Color.WHITE);
				temp.setStroke(Color.BLACK);
				cells[i][j] = temp;
			}
		}

		for(int i = 0; i <= 19; i++){
			for (int j = 0; j <= 19; j++){
				gp.add(cells[i][j], i, j);
			}
		}

		//Display title
		Text header = new Text("Moment of Intertia Calculator");
		header.setStyle("-fx-font-size: 36");
		header.setTextAlignment(TextAlignment.CENTER);
		title.getChildren().add(header);

		//Display axes
		for (int i = 0; i <= 20; i=i+5){
			int tempInt = 20-i;
			Text temp = new Text("" + tempInt);
			yAxis.getChildren().add(temp);
		}

		for (int i = 0; i <= 20; i=i+5){
			Text temp = new Text("" + i);
			xAxis.getChildren().add(temp);
		}

		//Display output
		Label outputText = new Label();
		outputText.setTextAlignment(TextAlignment.CENTER);
		outputText.setMinHeight(50);
		output.getChildren().add(outputText);

		//Collect Input
		Text xTextBox = new Text("Enter x coordinate \nof top left corner");
		xTextBox.setTextAlignment(TextAlignment.CENTER);
		TextField xTextField = new TextField();
		Label filler1 = new Label("\n");
		Text yTextBox = new Text("Enter y coordinate \nof top left corner");
		yTextBox.setTextAlignment(TextAlignment.CENTER);
		TextField yTextField = new TextField();
		Label filler2 = new Label("\n");
		Text hTextBox = new Text("Enter height (going down)\n of the rectangle");
		hTextBox.setTextAlignment(TextAlignment.CENTER);
		TextField hTextField = new TextField();
		Label filler3 = new Label("\n");
		Text wTextBox = new Text("Enter width (going right)\n of the rectangle");
		wTextBox.setTextAlignment(TextAlignment.CENTER);
		TextField wTextField = new TextField();
		Label filler4 = new Label("\n");

		Button mkShape = new Button("Make Rectangle");
		Label filler5 = new Label("\n");
		Button inertia = new Button("Calculate Intertia");
		Label filler6 = new Label("\n");
		Button reset = new Button("Reset");

		input.getChildren().addAll(xTextBox, xTextField, filler1, yTextBox, yTextField,
				filler2, hTextBox, hTextField, filler3, wTextBox, wTextField, filler4,
				mkShape, filler5, inertia, filler6, reset);

		//Button Event Handlers
		Vector<Rectangle> recVec = new Vector<Rectangle>();

		mkShape.setOnAction(e1 -> {
			int xPos1 = Integer.parseInt(xTextField.getText());
			int yPos1 = Integer.parseInt(yTextField.getText());
			int yPos2 = yPos1 - Integer.parseInt(hTextField.getText());
			int xPos2 = xPos1 + Integer.parseInt(wTextField.getText());

			recVec.add(new Rectangle((double) xPos1, (double) yPos1,
					Double.parseDouble(wTextField.getText()),
					Double.parseDouble(hTextField.getText())));

			xTextField.clear();
			yTextField.clear();
			hTextField.clear();
			wTextField.clear();

			for (int i = 20-yPos1; i <= 20-(yPos2+1); i++){
				for (int j = xPos1; j <= xPos2-1; j++){
					cells[j][i].setFill(Color.SANDYBROWN);
					cells[j][i].setStroke(Color.SANDYBROWN);
				}
			}
		});

		inertia.setOnAction(e2 -> {
			Rectangle[] param = new Rectangle[recVec.size()];
			for (int i = 0; i <= recVec.size()-1; i++){
				param[i] = recVec.elementAt(i);
			}

			double MOI = InertiaCalc(param);
			outputText.setText("The moment of Inertia for this system is " + MOI);
		});

		reset.setOnAction(e3 -> {
			xTextField.clear();
			yTextField.clear();
			hTextField.clear();
			wTextField.clear();

			for (int i = 0; i <= 19; i++) {
				for (int j = 0; j<= 19; j++){
					cells[i][j].setFill(Color.WHITE);
					cells[i][j].setStroke(Color.BLACK);
				}
			}

			pane.getChildren().clear();
			pane.getChildren().add(gp);
			ply.points.clear();
			outputText.setText("");
			recVec.clear();
		});

		//mouse listener
		pane.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent me) {

				ply.add(new Point((int)me.getX(),(int)me.getY()));

				if (ply.points.size()>1){

					//check that this point is not the last
					if((Math.abs(me.getX()-ply.points.get(0).getX()) < 20 &&
						Math.abs(me.getY()-ply.points.get(0).getY()) < 20)) {
						ply.points.remove(ply.points.size()-1);
						ply.points.add(ply.points.get(0));

						ply.compute();

						outputText.setText("I_x="+ply.moiX+", I_y="+ply.moiY+", A="+ply.area);
					}

					int plX_old=ply.points.get(ply.points.size()-2).x;
					int plY_old=ply.points.get(ply.points.size()-2).y;
					Line l1 = new Line(plX_old,plY_old,(int)me.getX(),(int)me.getY());

					pane.getChildren().add(l1);
				}
			}
		});

		//Create Scene and stage and display and stuff
		Scene scene = new Scene(bpMaster);
		stage.setTitle("Inertia Calculator");
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	public double InertiaCalc(Rectangle[] rectangleArray){
		int length = rectangleArray.length;
		double[] Yarray = new double [length];
		double Ytop = 0;
		double[] widtharray = new double [length];
		double[] heightarray = new double [length];
		double[] areaarray = new double [length];
		double NAnum = 0;
		double NAdenom = 0;
		double Inertia = 0;

		for(int i = 0; i <= (length-1); i++){
			Yarray[i] = rectangleArray[i].getY();

			if( Ytop < Yarray[i]){
				Ytop = Yarray[i];
			}

			heightarray[i] = rectangleArray[i].getHeight();
			widtharray[i] = rectangleArray[i].getWidth();
			areaarray[i] = (heightarray[i]*widtharray[i]);
		}

		for(int i = 0; i<= (length-1); i++){
			NAnum = NAnum + areaarray[i]*(Ytop-Yarray[i]+heightarray[i]/2);
			NAdenom = NAdenom +areaarray[i];
		}

		double YNeutralAxis = Ytop-NAnum/NAdenom;

		for(int i = 0; i<= (length-1); i++){
			double deltaY = YNeutralAxis - (Yarray[i]-heightarray[i]/2);
			Inertia = Inertia + (0.0833333333333333333333333333333)*
					areaarray[i]*(heightarray[i])*(heightarray[i]) + areaarray[i]*deltaY*deltaY;
		}

		return Inertia;
	}
}
