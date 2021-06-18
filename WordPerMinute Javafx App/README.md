# FinalCS1
# Anthony Bonilla

# Description
My program is a simple words per minute calculator. To start a typing test just hit the start button and wait for the countdown. There is random quotes that appear on the screen that the user must type correctly(sorta) in order to calculate how fast they type. Currently the program only requires for the right character to be typed it does not care about the values in the textfield as long as the right sequence of characters has been typed. This is something that I might update in a future patch but I am quite happy with the results of the program currently. The most difficult challenges I faced while doing this project was:

- interfacing with and learning javafx and guis in general
- implementation of a timer to display a countdown.
this website helped me implement a timer: https://asgteach.com/2011/10/javafx-animation-and-binding-simple-countdown-timer-2/#:~:text=To%20start%20the%20timer%2C%20you,which%20we'll%20see%20later.

In the future im thinking about using player objects to store data about fastest times, avgs, and other details that might pertain to a user.

if you want to restart/ reset the text and contents just hit the reset button

# contents
this repository contatins my final project submission which includes these files:
- wpmApplication.java 
- file.txt

# Installation
make sure that you have your path set correctly for java and javafx as this program uses javafx to display and provide additional functionality.
after that you must insure that file.txt is in the same working directory as wpmApplication.java for the correct contents to be displayed on the screen.

after wpmApplication.java is run a GUI window will appear.
hit the start button to start and wait for the countdown.
hit the rest button if you want to change text/ restart
hit the x at the top right if you wish to close the program

## Compile and Execute
You need to tell Java where to find JavaFX when you compile and execute the GUI demo. Don't forget to substitute your path to JavaFX/lib.


### CLI: 

javac --module-path="/home/aveliz/javafx-sdk-11.0.2/lib" --add-modules="javafx.controls,javafx.media,javafx.fxml" Demo.java

java --module-path="/home/aveliz/javafx-sdk-11.0.2/lib" --add-modules="javafx.controls,javafx.media,javafx.fxml" Demo

### Geany: Configure Build Commands 

javafxc: javac --module-path="/home/aveliz/javafx-sdk-11.0.2/lib" --add-modules="javafx.controls,javafx.media,javafx.fxml" "%f"

javafx: java --module-path="/home/aveliz/javafx-sdk-11.0.2/lib" --add-modules="javafx.controls,javafx.media,javafx.fxml" "%e"
