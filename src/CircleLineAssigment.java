// Arsen Cui
// ICS3U1-01
// November 26, 2018
// Mr. Radulovic
// Assignment 2 - Circle Patterns Assignment
// This program explores some interesting properties of multiplication from a visual perspective.
// The program draws a circle with a specified amount of dots placed evenly around it, and the dots
// are all connected by lines through multiplying the dot number by a specified amount. The product
// of this multiplication will represent the number of the next dot that the program draws the line
// to. The user inputs values for the variables N and M. N represents the specified amount of dots
// placed around the circle. M represents the number that you multiply the dot number by to get the 
// value of the next dot. This program is able to "animate" between different images for changing
// values of N and M that are specified by the user.

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.lang.Math;
import java.text.DecimalFormat;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;

public class CircleLineAssigment extends Application {

	HBox root; // HBox containing the buttonLayout and all the objects
	
	VBox buttonLayout; // VBox containing all buttons, labels, and textfields

	private Group sceneObjects; // Group containing the circle, dots and lines

	// Initializes the main circle
	private Circle circlemain;
	private double circlex = 500;
	private double circley = 400;
	private double circler = 340;

	// Initializes N and M
	private int N;
	private double M;

	double multiply_N; // The product of the dot number and M multiplied together

	private double dotr = 3; // The radius of the dots

	private Border border; // Initializes the border around the buttons

	// Booleans controlling when to animate to specified N, to next M, or to specified M
	private boolean animate_N;
	private boolean animate_NEXT_M;
	private boolean animate_END_M;

	// Variables to store user inputted values
	private int N_END;
	private double M_INCREMENT;
	private double M_NEXT; // Variable used to store the value 1 larger than the specified M
	private double M_END;

	// Labels used to show the current N and M
	private Label CURRENT_N_LABEL;
	private Label CURRENT_M_LABEL;

	private DecimalFormat round; // Function used to round the numbers to fewer decimal places

	
	// Method used to initialize the values of the dots
	public double[][] initializeDots(int N, double circler, double circlex, double circley) { 

		double[][] dotarray = new double[N][2]; // Array used to store the coordinates of the dots

		double theta = Math.toRadians(360.0 / N); // Variable used to store the angle portions
		
		// Variables used to store the x and y components of the line value
		double xcomponent = 0; 
		double ycomponent = 0;

		// For loop that adds the values of all the dot coordinates to dotarray
		for (int i = 0; i < N; i += 1) {
			
			/* "angle" is the angle that the dot is positioned relative to the origin of the 
			circle calculated based on the order of the dot on the circle*/
			double angle = Math.toRadians(180.0) - (i * theta);
			
			// Calculates the x and y components of the dot position using the angle
			xcomponent = circler * Math.cos(angle);
			ycomponent = circler * Math.sin(angle);

			// Puts the values of the dot coordinates into the array
			dotarray[i][0] = circlex + xcomponent;
			dotarray[i][1] = circley + ycomponent;

		}

		return dotarray;

	}
	
	// Method used to add the dots to the scene
	public void addDots(int N) {
		
		// Initializes all coordinates of the dots
		double [][] dotarray = initializeDots(N, circler, circlex, circley);

		// Draws all the dots
		for (int i = 0; i < N; i += 1) 
		{
			Circle dot = new Circle(dotarray[i][0], dotarray[i][1], dotr);
			dot.setFill(Color.RED);

			sceneObjects.getChildren().add(dot);

		}
		
	}
	
	// Method used to add the lines to the scene
	public void addLines(int N, double M) {
		
		// Initializes all coordinates of the dots
		double [][] dotarray = initializeDots(N, circler, circlex, circley);
		
		// Draws all the lines
		for (int i = 0; i < N; i += 1) {
			multiply_N = i * M;
			Line line = new Line(dotarray[i][0], dotarray[i][1], 
			dotarray[(int) (multiply_N % N)][0], dotarray[(int) (multiply_N % N)][1]);

			sceneObjects.getChildren().add(line);
		
		}

	}

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		round = new DecimalFormat(".##"); // Rounds numbers to two decimal places
		
		root = new HBox();
		buttonLayout = new VBox(); 
		sceneObjects = new Group();

		// Draws a border around the buttons, labels and textfields
		border = new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, null, null));
		buttonLayout.setBorder(border);

		// Formats the buttons, labels, and textfields to look nicer
		buttonLayout.setSpacing(10); 
		Separator s1 = new Separator();
		Separator s2 = new Separator();
		Separator s3 = new Separator();
		
		// Sets initial N and M values
		N = 10;
		M = 2;
		
		// Initializes and draws the scene
		double sceneparameters = 800;
		Scene scene = new Scene(root, sceneparameters, sceneparameters);

		// Initializes and draws the main circle
		circlex = 600;
		circley = 500;
		circler = 340;
		circlemain = new Circle(circlex, circley, circler);
		circlemain.setFill(Color.WHITE);
		circlemain.setStroke(Color.BLACK);

		// Adds initial objects to the scene
		sceneObjects.getChildren().add(circlemain);
		initializeDots(N, circler, circlex, circley);
		addDots(N);
		addLines(N, M);

		// Initializes user interface for drawing a pattern with N dots
		Label N_LABEL = new Label("N:");
		TextField N_TEXT = new TextField("10");
		Button DRAW_N = new Button("Draw N");
		N_LABEL.setTranslateX(45);
		N_TEXT.setMaxWidth(100);

		/*Initializes user interface for animating line patterns
		starting and ending with specific N values*/
		Label N_END_LABEL = new Label("End N:");
		TextField N_END_TEXT = new TextField("100");
		CURRENT_N_LABEL = new Label("Current N = " + N);
		Button DRAW_N_END = new Button("Draw N to end");
		N_END_LABEL.setTranslateX(33);
		CURRENT_N_LABEL.setTranslateX(10);
		N_END_TEXT.setMaxWidth(100);

		/*Initializes user interface for animating line patterns starting from the current M
		and going to the next M in increments of the user inputted M increment*/
		Label M_LABEL = new Label("M:");
		TextField M_TEXT = new TextField("2");
		Label M_INCREMENT_LABEL = new Label("M increment:");
		TextField M_INCREMENT_TEXT = new TextField("0.1");
		Button DRAW_NEXT_M = new Button("Draw to Next M");
		M_LABEL.setTranslateX(45);
		M_INCREMENT_LABEL.setTranslateX(15);
		M_TEXT.setMaxWidth(100);
		M_INCREMENT_TEXT.setMaxWidth(100);

		/*Initializes user interface for animating line patterns starting 
		from the current M and going to the specified End M value*/
		Label M_END_LABEL = new Label("End M:");
		TextField M_END_TEXT = new TextField("20");
		Button DRAW_CHANGE_M = new Button("Draw Changing M");
		CURRENT_M_LABEL = new Label("Current M = " + M);
		M_END_LABEL.setTranslateX(30);
		CURRENT_M_LABEL.setTranslateX(10);
		M_END_TEXT.setMaxWidth(100);


		buttonLayout.getChildren().addAll(N_LABEL, N_TEXT, DRAW_N, s1, N_END_LABEL, N_END_TEXT, 
		CURRENT_N_LABEL, DRAW_N_END, s2, M_LABEL, M_TEXT, M_INCREMENT_LABEL, M_INCREMENT_TEXT, 
		DRAW_NEXT_M, s3, M_END_LABEL, M_END_TEXT, DRAW_CHANGE_M, CURRENT_M_LABEL);

		// Adds all the buttons, labels, textfields, and objects into the scene
		root.getChildren().addAll(buttonLayout, sceneObjects);

		// Initializes the Draw N button to draw the circle with the current N and M values
		DRAW_N.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {

				// Stops all other instances of animation and drawing if they are running
				animate_N = false; 
				animate_NEXT_M = false;
				animate_END_M = false;
				
				// Gets input from the user in the textfield and updates the current N
				String N_STRING = N_TEXT.getText();
				N = Integer.parseInt(N_STRING);

				// Clear all objects except for the circle
				sceneObjects.getChildren().clear();
				sceneObjects.getChildren().add(circlemain);

				// Initializes and draws all the dots and lines according to the current N and M
				initializeDots(N, circler, circlex, circley);
				addDots(N);
				addLines(N, M);

				// Updates the label for the current N
				CURRENT_N_LABEL.setText("Current N = " + N);

			}
		});

		/*Initializes the Draw N to End button to animate from the
		current N all the way to the specified End N*/
		DRAW_N_END.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {

				animate_N = true; // Update loop will now animate the scene for the changing N
				
				// Gets input from the user in the textfield for the specified End N
				String N_END_STRING = N_END_TEXT.getText();
				N_END = Integer.parseInt(N_END_STRING);

			}

		});

		// Initializes the Draw to Next M button to animate from the current M to the next M 
		DRAW_NEXT_M.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {

				// Stops other instance of animating M if it is running
				animate_END_M = false;
				
				// Update loop will now animate M to the next M value
				animate_NEXT_M = true;

				// Gets input from the user in the textfield for the current M
				String M_STRING = M_TEXT.getText();
				M = Double.parseDouble(M_STRING);

				// Gets input from the user in the textfield for the M increment value
				String M_INCREMENT_STRING = M_INCREMENT_TEXT.getText();
				M_INCREMENT = Double.parseDouble(M_INCREMENT_STRING);

				M_NEXT = M + 1; // Initializes the value of the next M

			}

		});

		/*Initializes the Draw Changing M button to animate from 
		the current M to the user specified End M value*/
		DRAW_CHANGE_M.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				
				// Stops other instance of animating M if it is running
				animate_NEXT_M = false;
				
				// Update loop will now animate M to the user specified End M value
				animate_END_M = true;

				// Gets input from the user in the textfield for the current M
				String M_STRING = M_TEXT.getText();
				M = Double.parseDouble(M_STRING);

				// Gets input from the user in the textfield for the M increment value
				String M_INCREMENT_STRING = M_INCREMENT_TEXT.getText();
				M_INCREMENT = Double.parseDouble(M_INCREMENT_STRING);

				// Gets input from the user in the textfield for the End M value
				String M_END_STRING = M_END_TEXT.getText();
				M_END = Double.parseDouble(M_END_STRING);

			}

		});
		
		// Initializes the timer for animating every tenth of a second
		AnimationTimer timer = new AnimationTimer() {
			
			long oldTime = 0;

			@Override
			public void handle(long time) {

				oldTime += 1;
				
				// Every tenth of a second, animate the next image
				if (time - oldTime > 100000000) {
					upDate();
					oldTime = time;
				}

			}
		};
		timer.start();

		
		// Initializes and shows the scene
		primaryStage.setTitle("Assignment2");
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	
	public void upDate() {

		// If the Draw N to End button is pressed, animate the scene with the changing N
		if (animate_N == true) { 

			/*While N has not reached the specified End N value, keep increasing N and animating.
			If N reaches the specified End N value, stop animating and stop increasing N*/
			
			if (N < N_END)
				N += 1; 
			
			else if (N >= N_END)
				animate_N = false;

			// Clear the scene except for the main circle
			sceneObjects.getChildren().clear();
			sceneObjects.getChildren().add(circlemain);

			// Initializes and draws all the dots and lines according to the current N and M
			initializeDots(N, circler, circlex, circley);
			addDots(N);
			addLines(N, M);

			// Updates the label displaying the current N
			CURRENT_N_LABEL.setText("Current N = " + N);

		}

		/*If the Draw to Next M button is pressed, animate 
		the scene with the changing M to the next M*/
		if (animate_NEXT_M == true) {

			/*While M has not reached the next M, keep increasing M and animating. 
			If M reaches the next M, stop animating and stop increasing M*/
		
			if (M < M_NEXT)
				M = Math.round((M + M_INCREMENT)*100.0)/100.0;
			
			else if (M >= M_NEXT)
				animate_NEXT_M = false;

			// Clear the scene except for the main circle
			sceneObjects.getChildren().clear();
			sceneObjects.getChildren().add(circlemain);

			// Initializes and draws all the dots and lines according to the current N and M
			initializeDots(N, circler, circlex, circley);
			addDots(N);
			addLines(N, M);

			// Updates the label displaying the current M
			CURRENT_M_LABEL.setText("Current M = " + round.format(M));
			
		}

		/*If the Draw Changing M button is pressed, animate the 
		scene with the changing M to the specified End M*/
		if (animate_END_M == true) {

			/*While M has not reached the specified End M value, keep increasing M and animating. 
			If M reaches the specified End M, stop animating and stop increasing M*/
			
			if (M < M_END)
				M = Math.round((M + M_INCREMENT)*100.0)/100.0;
			
			else if (M >= M_END)
				animate_END_M = false;

			// Clear the scene except for the main circle
			sceneObjects.getChildren().clear();
			sceneObjects.getChildren().add(circlemain);

			// Initializes and draws all the dots and lines according to the current N and M
			initializeDots(N, circler, circlex, circley);
			addDots(N);
			addLines(N, M);
			
			// Updates the label displaying the current M
			CURRENT_M_LABEL.setText("Current M = " + round.format(M));
			
		}

	}

}