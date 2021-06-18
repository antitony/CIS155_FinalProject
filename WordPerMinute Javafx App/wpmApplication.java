//@author Anthony Bonilla, cs&141 Prof. Veliz, final project
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.event.*;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.stage.*;
import java.util.*;
import java.util.Random;
import java.time.LocalTime;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.text.DecimalFormat;

public class wpmApplication extends Application {
	//init variables that need to be defined outside of enclosing scope
	private static final Integer seconds = 3;
	private Integer timer = seconds;
	private Timeline timeline;
	private Boolean buttonPressed = false;
	
		public void start(Stage stage)  throws Exception {
			//build sentence for the user to type
			String wordsUsed = sentence();
		
			//create an array of chars for wordsUsed
			String[] strArray = wordsUsed.split("");
			
			//put sentence on the screen
			Label sentenceToType = new Label(wordsUsed);
			sentenceToType.setWrapText(true);
			sentenceToType.setTextAlignment(TextAlignment.JUSTIFY);
			sentenceToType.setFont(Font.font("Arial", FontWeight.NORMAL, 22));
			sentenceToType.setTranslateX(25);
			sentenceToType.setTranslateY(50);
			sentenceToType.setMaxWidth(750);
			sentenceToType.setTextFill(Color.BLACK);
			
			Label textColor = new Label("Text color: ");
			textColor.setTranslateX(25);
			textColor.setTranslateY(25);
			textColor.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
			
			//Area for user to type 
			TextField userText = new TextField();
			//dont want it to be editable until countdown hits 0
			userText.setEditable(false);
			userText.setLayoutX(25);
			userText.setLayoutY(300);
			userText.setPrefWidth(750);
			userText.setMaxWidth(750);
			userText.setMaxHeight(750);
			userText.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
			
			Label mistakeText = new Label();
			mistakeText.setTranslateX(25);
			mistakeText.setTranslateY(250);
			mistakeText.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
			mistakeText.setTextFill(Color.ALICEBLUE);
			mistakeText.setText("Mistakes: "+ 0);
			
			Label countdownText = new Label();
			countdownText.setTranslateX(375);
			countdownText.setTranslateY(125);
			countdownText.setFont(Font.font("Arial", FontWeight.NORMAL, 60));
			countdownText.setTextFill(Color.BLACK);
			
			Label wpm = new Label();
			wpm.setTranslateX(125);
			wpm.setTranslateY(475);
			wpm.setFont(Font.font("Arial", FontWeight.NORMAL, 40));
			wpm.setTextFill(Color.BLACK);
			
			//countdown from three and log start time
			Button startApp = new Button("Start");
			startApp.setLayoutX(375);
			startApp.setLayoutY(400);
			startApp.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent event){
					buttonPressed = true;
					if(timeline!= null){
						timeline.stop();
					}
					timer = seconds;
					countdownText.setText(timer.toString());
					timeline = new Timeline();
					timeline.setCycleCount(Timeline.INDEFINITE);
					timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),
												new EventHandler<ActionEvent>(){
						//code to handle timeline event/ decrement timer
						public void handle(ActionEvent event){
							timer--;
							countdownText.setText(timer.toString());
							//makes textfield editable
							if (timer <= 0){
								timeline.stop();
								userText.setEditable(true);
								userText.requestFocus();
								startApp.setVisible(false);
								countdownText.setVisible(false);
								}
							}
						}));
					timeline.playFromStart();
					}
				});
			
			double startTime = LocalTime.now().toNanoOfDay(); 
			
			//init char position and num of mistakes
			int[] charIndex = {0};
			int[] mistakes = {0};
			
			//events on key-press
			userText.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>(){
				public void handle(KeyEvent keyEvent){
					//this code is for debugging input mismatch
					System.out.println("strArray: "+strArray[charIndex[0]]);
					System.out.println("keyevent: "+keyEvent.toString());
					
					if(buttonPressed!=false) {
						//check if user char and system char are the same if they are check the next
						if (keyEvent.getCharacter().equals(strArray[charIndex[0]]) 
									&& strArray[charIndex[0]]!=strArray[strArray.length-1]) {
								sentenceToType.setTextFill(Color.LIMEGREEN);
								textColor.setText("Text color: green");
								charIndex[0] += 1;
						}
			
						//events on mistake
						else if(strArray[charIndex[0]]!=strArray[strArray.length-1]){
								sentenceToType.setTextFill(Color.RED);
								textColor.setText("Text color: red");
								mistakes[0] += 1;
								mistakeText.setText("Mistakes: "+mistakes[0]);
						}
						//events on completion of sentence	
						else if(strArray[charIndex[0]]==strArray[strArray.length-1] && keyEvent.getCharacter().equals(strArray[charIndex[0]])){
							//logs time on completion
							Long endTime = LocalTime.now().toNanoOfDay();
							//calculates wpm
							double elapsedTime = (endTime-startTime)/ 1000000000;
							double wpmNum = ((((double)strArray.length/5)/elapsedTime)*60);
							DecimalFormat df = new DecimalFormat();
							df.setMaximumFractionDigits(2);
							//prints wpm
							wpm.setText("Your WPM is: "+df.format(wpmNum));
							userText.setEditable(false);
						}	
					}
				}
			});	
			
			//makes it so backspace doesnt count as a mistake
			userText.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>(){
				public void handle(KeyEvent kr){
					if(kr.getCode()== KeyCode.BACK_SPACE){
						mistakes[0] -=1;
						sentenceToType.setTextFill(Color.LIMEGREEN);
						textColor.setText("Text color: green");
						mistakeText.setText("Mistakes: "+mistakes[0]);
					}
				}
			});
			
			//restarts the aplication with default values and a random sentence
			Button reset = new Button("Reset");
			reset.setLayoutX(25);
			reset.setLayoutY(550);
			reset.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent event){
					try{
					start(stage);
					}
					catch(Exception e){
					e.printStackTrace();
					}	
				}
			});
			//builds application
			Group root = new Group(sentenceToType, userText, mistakeText, countdownText, startApp, wpm, reset, textColor);
			Scene scene = new Scene(root, 800, 600,Color.DIMGRAY);
			stage.setTitle("Anthony's WPM calculator");
			stage.setScene(scene);
			stage.show();
		}
		
		public static String sentence() throws FileNotFoundException  {
			Random rand = new Random();
			String word = "";
			try {
				word = new String (Files.readAllBytes(Paths.get("file.txt")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			String[] words = word.split("&");
			String wordsUsed = "";
			System.out.println(Arrays.toString(words));
			wordsUsed = (words[rand.nextInt(words.length)]);
				return wordsUsed;
		}

	public static void main(String[] args) {
				launch(args);
			}
}

//sources:

//huge shoutout to alex lee as he is the one who gave me the idea for this program!

//https://www.youtube.com/watch?v=Dzx0-9cTIMc&t=241s&ab_channel=AlexLee
		
//and also to Prof. Veliz who had really helpful demo code

//quotes: https://www.keepinspiring.me/funny-quotes/

//https://docs.oracle.com/javase/8/javafx/get-started-tutorial/form.htm
//https://docs.oracle.com/javase/8/javafx/api/javafx/animation/KeyFrame.html
//https://asgteach.com/2011/10/javafx-animation-and-binding-simple-countdown-timer-2/#:~:text=To%20start%20the%20timer%2C%20you,which%20we'll%20see%20later.
//https://i.pinimg.com/originals/98/22/95/98229568e3f9a89db86514f655726a50.jpg
//https://stackoverflow.com/questions/2538787/how-to-print-a-float-with-2-decimal-places-in-java
//https://docs.oracle.com/javafx/2/text/jfxpub-text.htm
//https://www.programcreek.com/java-api-examples/?class=javafx.scene.control.Button&method=setVisible
//https://docs.oracle.com/javase/8/javafx/api/javafx/animation/Timeline.html
//https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Button.html
//https://stackoverflow.com/questions/21695175/jtextfield-setenabled-vs-seteditable
//https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TextInputControl.html#appendText-java.lang.String-
//https://stackoverflow.com/questions/19843469/set-textfield-width-in-javafx
//https://www.tutorialspoint.com/how-to-wrap-the-text-of-a-label-in-javafx
//https://stackoverflow.com/questions/2535723/try-catch-on-stack-overflows-in-java
//https://stackoverflow.com/questions/49564002/keycode-event-for-backspace-in-javafx


