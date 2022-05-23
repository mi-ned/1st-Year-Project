//IMPORT FILES
//Import files are an important tool in order for the program to be able to work.
//Here are the following import files:
import edu.cmu.ri.createlab.terk.robot.finch.Finch;//This is the most important file in which the Finch is able to interact with the user.
import java.io.IOException; //The IOException file is needed in case an exception is needed to be thrown
import java.io.PrintWriter; //The PrintWriter file is needed so that the .txt file has access to whatever is being written
import java.util.Date; //The Date, DateFormat and SimpleDateFormat files are needed so that the time is formatted in a specific way (24-hour format)
import java.text.DateFormat; 
import java.text.SimpleDateFormat;
import java.util.ArrayList; //The ArrayList file is required in order for the ArrayList storing to work
import javax.swing.JOptionPane; //The JOptionPane is needed for the GUI to work

//DECLARATION OF VARIABLES
//For the program to work, all of the variables
//are either public static or static, so that
//the methods are able to work with other variables
public abstract class FinchControl //A public abstract class is created so that the global variables are able to work regardless of data type
{ 
	static Finch finch = new Finch(); //The Finch is set as finch is then set to new Finch()
	static String input; // String input is the input in which the GUI will take from the user's input
	public static char x; //Character x is whenever the user inputs a single character at the start
	public static int d; //Integer d is the user input for duration in seconds
	public static int s; //Integer s is the user input for speed
	public static int a; //Integer a is the user input for maximum user input
	public static ArrayList<String> t = new ArrayList<String>(); //The ArrayList is set as a String t. This stores the user commands
	public static ArrayList<String> w = new ArrayList<String>(); //The ArrayList is set as a String w. This stores the user commands plus the Trace commands
	public static int aux; //Integer aux is the size of ArrayList t
	public static int log; //Integer log is the size of ArrayList w

	//DECLARATION OF METHODS
	//For the methods to be able to call on different methods, methods
	//must be declared before hand. All in all, there are 11 methods
	public static void main(String args[]) //This method is called as a public static void main(String args[]). This main creates the different methods required
	{
		Main(); //The Main method is where the user inputs a character
		Movement(); //The Movement method is where the user inputs character 'F' or 'B', and has to input duration and speed
		Turn(); //The Turn method is where the user inputs character 'L' or 'R', and has to input duration and speed, and the finch turns left or right at a 90 degree angle
		Random(); //The Random method randomly generates a command, as well as the duration and speed
		Trace(); //The Trace method retraces the previous number of user inputs
		Write(); //The Write method writes the entire log of user inputs, including the T commands
		Help(); //The Help method shows the user what keys are valid, and what the maximum numbers are allowed
		Quit(); //The Quit method first asks the user if they want to quit. If user clicks yes, then the program terminates
		reTraceArrayList(); //The reTraceArrayList method stores the user's commands
		CombinedArrayList(); //The CombinedArrayList method stores the user's commands, including the trace commands
		Document(); //The Document method gets called from the Write method, and prints the entire log of user inputs
	}

	//MAIN
	public static void Main() //This method (as well as the rest of the methods) is declared as a public static void, as the method above has already declared it as a String args[]
	{
		finch.buzz(255, 1500); //The Finch buzzes at 255 frequency, in 1.5 seconds
		JOptionPane.showMessageDialog(null, "Welcome", "Navigate", 1); //A message dialog box says 'Welcome'
		finch.setLED(25, 25, 25, 100); //Once the user presses OK, then the Finch lights up from black to white in 1 second using 10 different shades of white
		finch.setLED(50, 50, 50, 100);
		finch.setLED(75, 75, 75, 100);
		finch.setLED(100, 100, 100, 100);
		finch.setLED(125, 125, 125, 100);
		finch.setLED(150, 150, 150, 100);
		finch.setLED(175, 175, 175, 100);
		finch.setLED(200, 200, 200, 100);
		finch.setLED(225, 225, 225, 100);
		finch.setLED(255, 255, 255);
		while (true) //The while (true) determines if the Finch is connected or not. If it's not, then the program will not run until the Finch is connected
		{
			finch.setLED(255, 255, 255); //The Finch's default colour is white
			//An input dialog box is initialised to String input. The box asks the user to enter a valid character. If the user isn't sure then a suggestion says that the user
			//can press H for more information. The counter is also present which always starts off with 0 and the more inputs there are, the higher the count will be as the counter
			//is equal to the t.size()
			input = JOptionPane.showInputDialog(null, "Please enter a valid key and press SPACE or ENTER\n" + "For more information, press H\n" + "Counter: " + aux, "Navigate", 1);
			x = input.toUpperCase().charAt(0); //Once the user enters Character x (a user input) then the variable is initialised as the user input from the 1st character that they see, regardless whether or not the character is lower or upper case
			if (x == 'F' || x == 'B') //If the user inputs F or B, then the Movement method is called, followed by reTraceArrayList and CombinedArrayList methods
			{
				Movement();
				reTraceArrayList();
				CombinedArrayList();
			}
			else if (x == 'L' || x == 'R') //If the user inputs L or R, then the Turn method is called, followed by reTraceArrayList and CombinedArrayList methods
			{
				Turn();
				reTraceArrayList();
				CombinedArrayList();
			}
			else if (x == 'O') //If the user inputs O, then the CombinedArrayList method method is called, followed by the Random method. This is so that the Write log can display the correct information
			{
				CombinedArrayList();
				Random();
			}
			else if (x == 'T') //If the user inputs T, then the CombinedArrayList method is called, followed by the Trace method. Again, this is so that the Write log can display the correct information
			{
				CombinedArrayList();
				Trace();
			}
			else if (x == 'W') //If the user inputs W, then the Write method is called
			{
				Write();
			}
			else if (x == 'H') //If the user inputs H, then the Help method is called
			{
				Help();
			}
			else if (x == 'Q') //If the user inputs Q, then the Quit method is called
			{
				Quit();
			}
			else //If the above conditions are not met, then the following will occur
			{
				finch.setLED(0, 0, 0); //The Finch's lights are switched off
				finch.buzz(5000, 500); //The Finch's buzzer is switched on at 5000 Hz for 0,5 seconds; I have purposefully made it sound unpleasant
				finch.saySomething("Error"); //The Finch's text-to-speech voice would be heard from the computer's speakers. In this case it will say 'Error'
				JOptionPane.showMessageDialog(null, "Input not valid:\n" + "Please press SPACE or ENTER to close this message", "Error", 0, null); //A message box will appear and the user must re-enter a valid character
				finch.setLED(255, 255, 255); //The Finch's lights will turn back to white
			}
		}
	}

	//MOVEMENT
	public static void Movement()
	{
		if (x == 'F') //An if statement determines whether F is typed in
		{
			finch.setLED(0, 0, 255); //The lights are switched to blue
			finch.saySomething("F"); //The speaker says 'F' is declared
			JOptionPane.showMessageDialog(null, x, "Forwards", 1); //Around the same time as the blue colour and the speech a message box shows what the user has inputted
			input = JOptionPane.showInputDialog(null, "Please enter a duration that is 6 or under\n" + "and press SPACE or ENTER", "Forwards", 1); //An input dialog box asks the user to input a speed that is 6 seconds or under
			d = Integer.valueOf(input); //Integer d stores whatever the user has input
			while (d > 6) //If the duration is over 6, then the following will occur
			{
				finch.setLED(0, 0, 0); //The lights will be switched off
				finch.buzz(1000, 500); //The buzzer frequency is 1000Hz, for 0,5 seconds
				finch.saySomething("Error"); //The voice will say 'Error'
				JOptionPane.showMessageDialog(null, "Duration exceeds 6:\n" + "Please press SPACE or ENTER to close this message", "Error", 0, null); //A message box appears to tell the user what went wrong
				finch.setLED(0, 0, 255); //The finch's lights are turned on back to blue
				input = JOptionPane.showInputDialog(null, "Please enter a duration that is 6 or under\n" + "and press SPACE or ENTER", "Forwards", 1); //The input dialog box asks the user to re-enter a duration that is 6 seconds or under
				d = Integer.valueOf(input); //The original value (higher than 6) gets removed, and the new value gets stored into variable d
			}
			if (d <= 6) //If the duration is 6 or under, then the following will occur
			{
				finch.setLED(0, 0, d*40); //The finch's lights will be blue, depending on what the user has input, times by 40; the lower the value, the lower the brightness it will be
				finch.saySomething("F" + " " + Integer.toString(d)); //The Finch will say 'F' then whatever the user has input for duration
				JOptionPane.showMessageDialog(null, x + " " + Integer.toString(d), d + " Second(s)", 1); //A message box appears to tell the user what they have input so far, as well as how many seconds they have input as the title
				input = JOptionPane.showInputDialog(null, "Please enter a speed that is 200 or under\n" + "and press SPACE or ENTER", "Forwards", 1); //Another input box asks the user to input a speed that is 200 or under
				s = Integer.valueOf(input); //Integer s stores whatever the user has input
				while(s > 200) //If the speed is over 200, then the following will occur
				{
					finch.setLED(0, 0, 0); //The lights will be switched off
					finch.buzz(1000, 500); //The buzzer frequency is 1000Hz, for 0,5 seconds
					finch.saySomething("Error"); //The voice will say 'Error'
					JOptionPane.showMessageDialog(null, "Speed exceeds 200:\n " + "Please press SPACE or ENTER to close this message", "Error", 0, null); //A message box appears to tell the user what went wrong
					finch.setLED(0, 0, d*40); //The finch's lights are turned on back to blue, depending on what the user has input before multiplied by 40
					input = JOptionPane.showInputDialog(null, "Please enter a speed that is 200 or under\n" + "and press SPACE or ENTER", "Forwards", 1);  //The input dialog box asks the user to re-enter a speed that is 200 seconds or under
					s = Integer.valueOf(input); //The original value (higher than 200) gets removed, and the new value gets stored into variable s
				}
				if(s <= 200) //If the speed is 200 or under, then the following will occur
				{
					finch.setLED(0, 0, s); //The finch's lights will be blue, depending on what the user has input as the speed
					finch.saySomething(x + " " + Integer.toString(d) + " " + Integer.toString(s)); //The finch will say 'F', then whatever the user has input for duration, followed by user input for speed
					JOptionPane.showMessageDialog(null, x + " " + Integer.toString(d) + " " + Integer.toString(s), s + " Speed", 1); //A message box appears to tell the user what they have input thus far, as well as the speed that they have input as the title
					finch.setWheelVelocities(s, s, d*1000); //The finch's wheel velocities (both left and right) are set to the user input for speed, and the duration in seconds is multiplied by 1000, since the program measures in milliseconds
					finch.setLED(255, 255, 255); //Once the operation is complete, the lights are switched on to white
					finch.buzz(10000, 100); //The buzzer is played at a very high pitch frequency for 0,1 seconds
					finch.saySomething("Operation is Successful: " + x + " " + Integer.toString(d) + " " + Integer.toString(s)); //The Finch's voice then says 'Operation is Successfull: ', followed by whatever the user has input as a character, then duration (in seconds) then speed
					JOptionPane.showMessageDialog(null, "Operation is Successful: " + x + " " + Integer.toString(d) + " " + Integer.toString(s), "Navigate", 1); //A message box appears which says that the commands have been successfully completed
				}
			}
		}
		else if (x == 'B') //An else if statement determines whether B is typed in
		{
			finch.setLED(0, 0, 255); //The lights are switched to blue
			finch.saySomething("B"); //The speaker says 'B' is declared
			JOptionPane.showMessageDialog(null, x, "Backwards", 1); //Around the same time as the blue colour and the speech a message box shows what the user has inputted
			input = JOptionPane.showInputDialog(null, "Please enter a duration that is 6 or under\n" +  "and press SPACE or ENTER", "Backwards", 1); //An input dialog box asks the user to input a speed that is 6 seconds or under
			d = Integer.valueOf(input); //Integer d stores whatever the user has input
			while (d > 6) //If the duration is over 6, then the following will occur
			{
				finch.setLED(0, 0, 0); //The lights will be switched off
				finch.buzz(1000, 500); //The buzzer frequency is 1000Hz, for 0,5 seconds
				finch.saySomething("Error"); //The voice will say 'Error'
				JOptionPane.showMessageDialog(null, "Duration exceeds 6:\n" + "Please press SPACE or ENTER to close this message", "Error", 0, null); //A message box appears to tell the user what went wrong
				finch.setLED(0, 0, 255); //The finch's lights are turned on back to blue
				input = JOptionPane.showInputDialog(null, "Please enter a duration that is 6 or under\n" +  "and press SPACE or ENTER", "Backwards", 1); //The input dialog box asks the user to re-enter a duration that is 6 seconds or under
				d = Integer.valueOf(input); //The original value (higher than 6) gets removed, and the new value gets stored into variable d
			}
			if (d <= 6) //If the duration is 6 or under, then the following will occur
			{
				finch.setLED(0, 0, d*40); //The finch's lights will be blue, depending on what the user has input, times by 40; the lower the value, the lower the brightness it will be
				finch.saySomething("B" + " " + Integer.toString(d)); //The finch will say 'B' then whatever the user has input for duration			
				JOptionPane.showMessageDialog(null, x + " " + Integer.toString(d), d + " Seconds", 1); //A message box appears to tell the user what they have input so far, as well as how many seconds they have input as the title
				input = JOptionPane.showInputDialog(null, "Please enter a speed that is 200 or under\n" + "and press SPACE or ENTER", "Backwards", 1); //Another input box asks the user to input a speed that is 200 or under
				s = Integer.valueOf(input); //Integer s stores whatever the user has input
				while(s > 200) //If the speed is over 200, then the following will occur
				{
					finch.setLED(0, 0, 0); //The lights will be switched off
					finch.buzz(1000, 500); //The buzzer frequency is 1000Hz, for 0,5 seconds
					finch.saySomething("Error"); //The voice will say 'Error'
					JOptionPane.showMessageDialog(null, "Speed exceeds 200:\n" + "Please press SPACE or ENTER to close this message", "Error", 0, null); //A message box appears to tell the user what went wrong
					finch.setLED(0, 0, d*40); //The finch's lights are turned on back to blue, depending on what the user has input before multiplied by 40
					input = JOptionPane.showInputDialog(null, "Please enter a speed that is 200 or under\n" + "and press SPACE or ENTER", "Backwards", 1); //The input dialog box asks the user to re-enter a speed that is 200 seconds or under
					s = Integer.valueOf(input); //The original value (higher than 200) gets removed, and the new value gets stored into variable s
				}
				if(s <= 200) //If the speed is 200 or under, then the following will occur
				{
					finch.setLED(0, 0, s); //The finch's lights will be blue, depending on what the user has input as the speed
					finch.saySomething(x + " " + Integer.toString(d) + " " + Integer.toString(s)); //The finch will say 'B', then whatever the user has input for duration, followed by user input for speed
					JOptionPane.showMessageDialog(null, x + " " + Integer.toString(d) + " " + Integer.toString(s), s + " Speed", 1); //A message box appears to tell the user what they have input thus far, as well as the speed that they have input as the title
					finch.setWheelVelocities(0-s, 0-s, d*1000); //The finch's wheel velocities (both left and right) are set to the user input for speed, and the duration in seconds is multiplied by 1000, since the program measures in milliseconds
					finch.setLED(255, 255, 255); //Once the operation is complete, the lights are switched on to white
					finch.buzz(10000, 500); //The buzzer is played at a very high pitch frequency for 0,1 seconds
					finch.saySomething("Operation is Successful: " + x + " " + Integer.toString(d) + " " + Integer.toString(s)); //The Finch's voice then says 'Operation is Successful: ', followed by whatever the user has input as a character, then duration (in seconds) then speed
					JOptionPane.showMessageDialog(null, "Operation is Successful: " + x + " " + Integer.toString(d) + " " + Integer.toString(s), "Navigate", 1); //A message box appears which says that the commands have been successfully completed
				}
			}
		}
	}

	//TURN
	public static void Turn()
	{
		if (x == 'L') //An if statement determines whether L is typed in
		{
			finch.setLED(255, 0, 0); //The lights are switched to red
			finch.saySomething("L"); //The speaker says 'L' is declared
			JOptionPane.showMessageDialog(null, x, "Left", 1); //Around the same time as the red colour and the speech a message box shows what the user has inputted
			input = JOptionPane.showInputDialog(null, "Please enter a duration that is 6 or under\n" + "and press SPACE or ENTER", "Left", 1); //An input dialog box asks the user to input a speed that is 6 seconds or under
			d = Integer.valueOf(input); //Integer d stores whatever the user has input
			while (d > 6) //If the duration is over 6, then the following will occur
			{
				finch.setLED(0, 0, 0); //The lights will be switched off
				finch.buzz(1000, 500); //The buzzer frequency is 1000Hz, for 0,5 seconds
				finch.saySomething("Error"); //The voice will say 'Error'
				JOptionPane.showMessageDialog(null, "Duration exceeds 6:\n" + "Please press SPACE or ENTER to close this message", "Error", 0); //A message box appears to tell the user what went wrong
				finch.setLED(255, 0, 0); //The finch's lights are turned on back to red
				input = JOptionPane.showInputDialog(null, "Please enter a duration that is 6 or under\n" + "and press SPACE or ENTER", "Left", 1); //The input dialog box asks the user to re-enter a duration that is 6 seconds or under
				d = Integer.valueOf(input); //The original value (higher than 6) gets removed, and the new value gets stored into variable d
			}
			if (d <= 6) //If the duration is 6 or under, then the following will occur
			{
				finch.setLED(d*40, 0, 0); //The finch's lights will be red, depending on what the user has input, times by 40; the lower the value, the lower the brightness it will be
				finch.saySomething("L" + " " + Integer.toString(d)); //The Finch will say 'L' then whatever the user has input for duration
				JOptionPane.showMessageDialog(null, x + " " + Integer.toString(d), d + " Seconds", 1); //A message box appears to tell the user what they have input so far, as well as how many seconds they have input as the title
				input = JOptionPane.showInputDialog(null, "Please enter a speed that is 255 or under\n" + "and press SPACE or ENTER", "Left", 1); //Another input box asks the user to input a speed that is 255 or under
				s = Integer.valueOf(input); //Integer s stores whatever the user has input
				while(s > 255) //If the speed is over 255, then the following will occur
				{
					finch.setLED(0, 0, 0); //The lights will be switched off
					finch.buzz(1000, 500); //The buzzer frequency is 1000Hz, for 0,5 seconds
					finch.saySomething("Error"); //The voice will say 'Error'
					JOptionPane.showMessageDialog(null, "Speed exceeds 255:\n" + "Please press SPACE or ENTER to close this message", "Error", 0, null); //A message box appears to tell the user what went wrong
					finch.setLED(d*40, 0, 0); //The finch's lights are turned on back to red, depending on what the user has input before multiplied by 40
					input = JOptionPane.showInputDialog(null, "Please enter a speed that is 255 or under\n" + "and press SPACE or ENTER", "Left", 1); //The input dialog box asks the user to re-enter a speed that is 255 seconds or under
					s = Integer.valueOf(input); //The original value (higher than 255) gets removed, and the new value gets stored into variable s
				}
				if(s <= 255) //If the speed is 255 or under, then the following will occur
				{
					finch.setLED(s, 0, 0); //The finch's lights will be red, depending on what the user has input as the speed
					finch.saySomething(x + " " + Integer.toString(d) + " " + Integer.toString(s)); //The finch will say 'L', then whatever the user has input for duration, followed by user input for speed
					JOptionPane.showMessageDialog(null, x + " " + Integer.toString(d) + " " + Integer.toString(s), s + " Seconds", 1); //A message box appears to tell the user what they have input thus far, as well as the speed that they have input as the title
					finch.setWheelVelocities(-150, 150, 500); //The finch's wheel velocities (left wheel is -150 and right wheel is 150) are set for 0,5 seconds. This turns the Finch left at a 90-degree angle
					finch.setWheelVelocities(s, s, d*1000); //The finch's wheel velocities (both left and right) are set to the user input for speed, and the duration in seconds is multiplied by 1000, since the program measures in milliseconds
					finch.setLED(255, 255, 255); //Once the operation is complete, the lights are switched on to white
					finch.buzz(10000, 500); //The buzzer is played at a very high pitch frequency for 0,1 seconds
					finch.saySomething("Operation is Successful: " + x + " " + Integer.toString(d) + " " + Integer.toString(s)); //The Finch's voice then says 'Operation is Successful: ', followed by whatever the user has input as a character, then duration (in seconds) then speed
					JOptionPane.showMessageDialog(null, "Operation is Successful: " + x + " " + Integer.toString(d) + " " + Integer.toString(s), "Navigate", 1); //A message box appears which says that the commands have been successfully completed
				}
			}
		}
		else if (x == 'R') //An if statement determines whether R is typed in
		{
			finch.setLED(255, 0, 0); //The lights are switched to red
			finch.saySomething("R"); //The speaker says 'R' is declared
			JOptionPane.showMessageDialog(null, x, "Right", 1); //Around the same time as the red colour and the speech a message box shows what the user has inputed
			input = JOptionPane.showInputDialog(null, "Please enter a duration that is 6 or under\n"  +  "and press SPACE or ENTER", "Right", 1); //An input dialog box asks the user to input a speed that is 6 seconds or under
			d = Integer.valueOf(input); //Integer d stores whatever the user has input
			while (d > 6) //If the duration is over 6, then the following will occur
			{
				finch.setLED(0, 0, 0); //The lights will be switched off
				finch.buzz(1000, 500); //The buzzer frequency is 1000Hz, for 0,5 seconds
				finch.saySomething("Error"); //The voice will say 'Error'
				JOptionPane.showMessageDialog(null, "Duration exceeds 6:\n" + "Please press SPACE or ENTER to close this message", "Error", 0, null); //A message box appears to tell the user what went wrong
				finch.setLED(255, 0, 0); //The finch's lights are turned on back to red
				input = JOptionPane.showInputDialog(null, "Please enter a duration that is 6 or under\n" + "and press SPACE or ENTER", "Right", 1); //The input dialog box asks the user to re-enter a duration that is 6 seconds or under
				d = Integer.valueOf(input); //The original value (higher than 6) gets removed, and the new value gets stored into variable d
			}
			if (d <= 6) //If the duration is 6 or under, then the following will occur
			{
				finch.setLED(d*40, 0, 0); //The finch's lights will be red, depending on what the user has input, times by 40; the lower the value, the lower the brightness it will be
				finch.saySomething("R" + " " + Integer.toString(d)); //The Finch will say 'R' then whatever the user has input for duration
				JOptionPane.showMessageDialog(null, x + " " + Integer.toString(d), d + " Seconds", 1); //A message box appears to tell the user what they have input so far, as well as how many seconds they have input as the title
				input = JOptionPane.showInputDialog(null, "Please enter a speed that is 255 or under\n" + "and press SPACE or ENTER" , "Right", 1); //Another input box asks the user to input a speed that is 255 or under
				s = Integer.valueOf(input); //Integer s stores whatever the user has input
				while(s > 255) //If the speed is over 255, then the following will occur
				{
					finch.setLED(0, 0, 0); //The lights will be switched off
					finch.buzz(1000, 500); //The buzzer frequency is 1000Hz, for 0,5 seconds
					finch.saySomething("Error"); //The voice will say 'Error'
					JOptionPane.showMessageDialog(null, "Speed exceeds 255:\n" + "Please press SPACE or ENTER to close this message", "Error", 0, null); //A message box appears to tell the user what went wrong
					finch.setLED(d*40, 0, 0); //The finch's lights are turned on back to red, depending on what the user has input before multiplied by 40
					input = JOptionPane.showInputDialog(null, "Please enter a speed that is 200 or under\n" + "and press SPACE or ENTER" , "Right", 1); //The input dialog box asks the user to re-enter a speed that is 255 seconds or under
					s = Integer.valueOf(input); //The original value (higher than 255) gets removed, and the new value gets stored into variable s
				}
				if(s <= 255) //If the speed is 255 or under, then the following will occur
				{
					finch.setLED(s, 0, 0); //The finch's lights will be red, depending on what the user has input as the speed
					finch.saySomething(x + " " + Integer.toString(d) + " " + Integer.toString(s)); //The finch will say 'R', then whatever the user has input for duration, followed by user input for speed
					JOptionPane.showMessageDialog(null, x + " " + Integer.toString(d) + " " + Integer.toString(s), s + " Seconds", 1); //A message box appears to tell the user what they have input thus far, as well as the speed that they have input as the title
					finch.setWheelVelocities(150, -150, 500); //The finch's wheel velocities (left wheel is 150 and right wheel is -150) are set for 0,5 seconds. This turns the Finch right at a 90-degree angle
					finch.setWheelVelocities(s, s, d*1000); //The finch's wheel velocities (both left and right) are set to the user input for speed, and the duration in seconds is multiplied by 1000, since the program measures in milliseconds
					finch.setLED(255, 255, 255); //Once the operation is complete, the lights are switched on to white
					finch.buzz(10000, 500); //The buzzer is played at a very high pitch frequency for 0,1 seconds
					finch.saySomething("Operation is Successful: " + " " + x + " " + Integer.toString(d) + " " + Integer.toString(s)); //The Finch's voice then says 'Operation is Successful: ', followed by whatever the user has input as a character, then duration (in seconds) then speed
					JOptionPane.showMessageDialog(null, "Operation is Successful: " + x + " " + Integer.toString(d) + " " + Integer.toString(s), "Navigate", 1); //A message box appears which says that the commands have been successfully completed
				}
			}
		}
	}

	//RANDOM
	public static void Random()
	{
		finch.setLED(255, 0, 127); //The finch's lights will be magenta
		java.util.Random o = new java.util.Random(); //A random java input is made. There are two of them; 'o' and 'rnd'. Variable 'o' is used to randomly generate 4 possible commands, whereas 'rnd' is the randomly generated values for duration and speed
		java.util.Random rnd = new java.util.Random();
		int nav; //A new Integer 'nav' is made
		nav = o.nextInt(4); //The 'nav' variable is initialised as 4 different commands (Forwards, Backwards, Left and Right)
		switch(nav) //The 'nav' is then switched into four different cases
		{
		case 0: //Forward case
			int d = rnd.nextInt((6-1)+1)+1; //A random duration (variable d) number is generated between 1 to 6
			int s = rnd.nextInt((200-50)+1)+50; //A random speed (variable s) number is generated between 50 to 200
			finch.setWheelVelocities(s, s, d*1000); //The finch's duration and speed is set from random
			finch.setLED(255, 255, 255); //Once the operation is complete, the lights are switched on to white
			finch.buzz(10000, 500); //The buzzer is played at a very high pitch frequency for 0,1 seconds
			finch.saySomething("Operation is successful: " + "F" + " " + d + " " + s); //The Finch's voice then says 'Operation is Successful: ', followed by whatever the user has input as a character, then duration (in seconds) then speed
			JOptionPane.showMessageDialog(null, "Operation is successful: " + "F" + " " + d + " " + s, "Navigate", 1); //A message box appears which says that the commands have been successfully completed
			break; //Break is initiated so that the cases can be moved from this to another
		case 1: //Backward case
			d = rnd.nextInt((6-1)+1)+1; //A random duration (variable d) number is generated between 1 to 6
			s = rnd.nextInt((200-50)+1)+50; //A random speed (variable s) number is generated between 50 to 200
			finch.setWheelVelocities(0-s, 0-s, d*1000); //The finch's duration and speed is set from random
			finch.setLED(255, 255, 255); //Once the operation is complete, the lights are switched on to white
			finch.buzz(10000, 500); //The buzzer is played at a very high pitch frequency for 0,1 seconds
			finch.saySomething("Operation is successful: " + "B" + " " + d + " " + s); //The Finch's voice then says 'Operation is Successful: ', followed by whatever the user has input as a character, then duration (in seconds) then speed
			JOptionPane.showMessageDialog(null, "Operation is successful: " + "B" + " " + d + " " + s, "Navigate", 1); //A message box appears which says that the commands have been successfully completed
			break; //Break is initiated so that the cases can be moved from this to another
		case 2: //Left case
			d = rnd.nextInt((6-1)+1)+1; //A random duration (variable d) number is generated between 1 to 6
			s = rnd.nextInt((255-50)+1)+50; //A random speed (variable s) number is generated between 50 to 200
			finch.setWheelVelocities(-150, 150, 500); //The finch's wheel velocities (left wheel is -150 and right wheel is 150) are set for 0,5 seconds. This turns the Finch left at a 90-degree angle
			finch.setWheelVelocities(s, s, d*1000); //The finch's duration and speed is set from random
			finch.setLED(255, 255, 255); //Once the operation is complete, the lights are switched on to white
			finch.buzz(10000, 500); //The buzzer is played at a very high pitch frequency for 0,1 seconds
			finch.saySomething("Operation is successful: " + "L" + " " + d + " " + s); //The Finch's voice then says 'Operation is Successful: ', followed by whatever the user has input as a character, then duration (in seconds) then speed
			JOptionPane.showMessageDialog(null, "Operation is successful: " + "L" + " " + d + " " + s, "Navigate", 1); //A message box appears which says that the commands have been successfully completed
			break; //Break is initiated so that the cases can be moved from this to another
		case 3: //Right case
			d = rnd.nextInt((6 - 1)+1)+1; //A random duration (variable d) number is generated between 1 to 6
			s = rnd.nextInt((255 - 50)+1)+50; //A random speed (variable s) number is generated between 50 to 200
			finch.setWheelVelocities(150, -150, 500); //The finch's wheel velocities (left wheel is 150 and right wheel is -150) are set for 0,5 seconds. This turns the Finch right at a 90-degree angle
			finch.setWheelVelocities(s, s, d*1000); //The finch's duration and speed is set from random
			finch.setLED(255, 255, 255); //Once the operation is complete, the lights are switched on to white
			finch.buzz(10000, 500); //The buzzer is played at a very high pitch frequency for 0,1 seconds
			finch.saySomething("Operation is successful: " + "R" + " " + d + " " + s); //The Finch's voice then says 'Operation is Successful: ', followed by whatever the user has input as a character, then duration (in seconds) then speed
			JOptionPane.showMessageDialog(null, "Operation is successful: " + "R" + " " + d + " " + s, "Navigate", 1); //A message box appears which says that the commands have been successfully completed
			break; //Break is initiated so that the cases can be moved from this to another
		default: //If the finch happens to be disconnected, then the following will happen
			JOptionPane.showMessageDialog(null, "Finch has lost connection", "Navigate", 0); //A message box will appear to inform the user that the finch has been disconnected
		}
	}

	//TRACE
	public static void Trace()
	{
		finch.setLED(255, 255, 0); //The finch's lights will be yellow
		finch.saySomething("T"); //The speaker says 'T' is declared
		JOptionPane.showMessageDialog(null, x, "Trace", 1); //Around the same time as the red colour and the speech a message box shows what the user has inputed
		input = JOptionPane.showInputDialog(null, "Please enter a value that is " + aux + " or under \n" +  "and press SPACE or ENTER", "Trace", 1); //An input dialog box asks the user to input a value that is under the counter
		a = Integer.valueOf(input); //Integer a stores whatever the user has input
		while (a > aux) //If integer a is bigger than integer aux, then the following will occur
		{
			finch.setLED(0, 0, 0); //The lights will be switched off
			finch.buzz(1000, 500); //The buzzer frequency is 1000Hz, for 0,5 seconds
			finch.saySomething("Error"); //The voice will say 'Error'
			JOptionPane.showMessageDialog(null, "Number exceeds " + aux + "\n"+ "Please press SPACE or ENTER to close this message", "Error", 0, null); //A message box appears to tell the user what went wrong
			finch.setLED(255, 255, 0); //The finch's lights are turned on back to yellow
			input = JOptionPane.showInputDialog(null, "Please enter a value that is " + aux + " or under\n" +  "and press SPACE or ENTER", "Trace", 1);  //An input dialog box asks the user to re-enter a value that is under the counter
			a = Integer.valueOf(input); //The original value (higher than Counter) gets removed, and the new value gets stored into variable a
		}
		if (a <= aux) //If variable 'a' is less than or equal to 'aux', the following will occur
		{
			finch.setLED(a*3, a*3, 0); //The lights will be switched to yellow, depending on the user input, and multiplied by 3
			finch.saySomething("T" + " " + Integer.toString(a)); //The finch's voice says 'T' followed by the number that the user has input
			JOptionPane.showMessageDialog(null, "T" + " " + Integer.toString(a), "Trace " + aux, 1); //A message box appears which tells the user what they have input
			int b = aux; //A new integer variable 'b' is created and is initialised as aux
			for(int i=a; i > 0; --i) //A for loop starts off with an integer variable 'i' which is equal to a, followed by i being greater than 0, followed by i being decremented
			{
				b = b-1; //Variable b is then re-initialised as 1 subtracted by b
				x = t.get(b).charAt(0); //Variable x is then initialised as variable t (trace arraylist size); it gets the variable b, and it then gets the character at 0, which means that the user's input character is caught
				switch(x) //A switch statement is declared
				{
				case 'F': //Forward case
					finch.setLED(255, 255, 0); //Finch's lights are switched on to yellow
					finch.setWheelVelocities(0+s, 0+s, d*1000); //Wheel velocities are set to the finch from previous user input
					finch.buzz(250, 500); //The finch's buzzer is set to 250Hz at 0,5 seconds
					finch.saySomething(x + " " + Integer.toString(d) + " " + Integer.toString(s)); //The finch's voice says what character has been retraced, followed by speed and duration
					JOptionPane.showMessageDialog(null, x + " " + Integer.toString(d) + " " + Integer.toString(s), "Forwards", 1); //A message box appears, which says what movement has been initiated
					break; //Break is initiated so that the cases can be moved from this to another
				case 'B': //Backward case
					finch.setLED(255, 255, 0); //Finch's lights are switched on to yellow
					finch.setWheelVelocities(0-s, 0-s, d*1000); //Wheel velocities are set to the finch from previous user input. In order for the finch to go backwards, the speed is set from s minus 0
					finch.buzz(250, 500); //The finch's buzzer is set to 250Hz at 0,5 seconds
					finch.saySomething(x + " " + Integer.toString(d) + " " + Integer.toString(s)); //The finch's voice says what character has been retraced, followed by speed and duration
					JOptionPane.showMessageDialog(null, x + " " + Integer.toString(d) + " " + Integer.toString(s), "Backwards", 1); //A message box appears, which says what movement has been initiated
					break;
				case 'L': //Left case
					finch.setLED(255, 255, 0); //Finch's lights are switched on to yellow
					finch.setWheelVelocities(-150, 150, 500); //The finch's wheel velocities (left wheel is -150 and right wheel is 150) are set for 0,5 seconds. This turns the Finch left at a 90-degree angle
					finch.setWheelVelocities(s, s, d*1000); //Wheel velocities are set to the finch from previous user input
					finch.buzz(250, 500); //The finch's buzzer is set to 250Hz at 0,5 seconds
					finch.saySomething(x + " " + Integer.toString(d) + " " + Integer.toString(s)); //The finch's voice says what character has been retraced, followed by speed and duration
					JOptionPane.showMessageDialog(null, x + " " + Integer.toString(d) + " " + Integer.toString(s), "Left", 1); //A message box appears, which says what movement has been initiated
					break; 
				case 'R': //Right case
					finch.setLED(255, 255, 0); //Finch's lights are switched on to yellow
					finch.setWheelVelocities(150, -150, 500); //The finch's wheel velocities (left wheel is 150 and right wheel is -150) are set for 0,5 seconds. This turns the Finch right at a 90-degree angle
					finch.setWheelVelocities(s, s, d*1000); //Wheel velocities are set to the finch from previous user input
					finch.buzz(250, 500); //The finch's buzzer is set to 250Hz at 0,5 seconds
					finch.saySomething(x + " " + Integer.toString(d) + " " + Integer.toString(s)); //The finch's voice says what character has been retraced, followed by speed and duration
					JOptionPane.showMessageDialog(null, x + " " + Integer.toString(d) + " " + Integer.toString(s), "Right", 1); //A message box appears, which says what movement has been initiated
					break;
				}
			}
			finch.setLED(255, 255, 255); //Once the operation is complete, the lights are switched on to white
			finch.buzz(10000, 100); //The buzzer is played at a very high pitch frequency for 0,1 seconds
			finch.saySomething("Operation is Successful: " + "T" + " " + Integer.toString(a)); //The Finch's voice then says 'Operation is Successful: ', followed by whatever the user has input as a character, then the user input
			JOptionPane.showMessageDialog(null, "Operation is Successful: " + "T" + " " + Integer.toString(a), "Navigate", 1); //A message box appears which says that the commands have been successfully completed
		}
	}

	//WRITE
	public static void Write()
	{
		finch.setLED(0, 255, 0); //The finch's lights are switched on to green
		Date date = new Date(); //A new variable is made called date. This is how the date is imported
		String strDateFormat = "HH:mm:ss"; //A new string variable is made, which shows the 24-hour clock format
		DateFormat dateFormat = new SimpleDateFormat(strDateFormat); //To display the time only, the dateFormat becomes simplified as strDateFormat
		String formattedDate = dateFormat.format(date); //A new string is made as formattedDate which is initialised as the format of the time
		finch.saySomething("The current time is..." + formattedDate); //The finch's voice then says what the current time is in the 24-hour format
		JOptionPane.showMessageDialog(null, "The current time is...\n" + formattedDate, "Time", 1); //A output box then tells the user the time
		if (log == 0) //If the log (write arraylist size) is empty, then the following will occur
		{
			finch.setLED(0, 0, 0); //The lights will be switched off
			finch.buzz(750, 500); //A buzzing sound is played at 750Hz for 0,5 seconds
			finch.saySomething("Invalid"); //The finch's voice will say 'Invalid
			JOptionPane.showMessageDialog(null, "Write is not valid", "Invalid", 0); //A message box is shown which tells the user that the Write document is not valid
			JOptionPane.showMessageDialog(null, "Navigate Restarting", "Navigate", 1); //Then a message will tell the user that the program is 'restarting'
			finch.sleep(5000); //The finch will then be shut off for 5 seconds
			Main(); //Once 5 seconds are over, then the program will restart
		}
		Document(); //If the log size is not 0, then the Document method is called
	}

	//HELP
	public static void Help()
	{
		finch.setLED(0, 175, 255); //The finch's light will turn sky blue
		JOptionPane.showMessageDialog(null,"F: Forward - Maximum duration = 6, Maximum speed = 200\n" + "B: Backwards - Maximum duration = 6, Maximum speed = 200", "Movement", 3, null); //This message box shows the instructions for movement
		JOptionPane.showMessageDialog(null,"L: Left - Maximum duration = 6, Maximum speed = 255\n" + "R: Right - Maximum duration = 6, Maximum speed = 255" , "Turn", 3, null); //This message box shows the instructions for turn
		JOptionPane.showMessageDialog(null,"T: Trace - Maximum value that you can input right now: " + aux, "Trace", 3, null); //This message box shows the instructions for trace
		JOptionPane.showMessageDialog(null,"W: Write - Previous User Inputs required", "Write", 3, null); //This message box shows the instructions for write
		JOptionPane.showMessageDialog(null,"O: Random\n" + "Q: Quit\n" + "H: Help", "Miscellaneous", 3, null); //This message box shows the instructions for the rest of the program
	}

	//QUIT
	public static void Quit()
	{
		finch.setLED(255, 127, 0); //The finch's light will turn orange
		int q = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION); //A conformation box (initialised as integer q) asks the user if they want to quit
		if (q == JOptionPane.YES_OPTION) //If the user clicks on yes, then the following will occur
		{
			finch.setLED(250, 250, 250, 100); //The lights will turn back to white, all the way to black, in 1 second
			finch.setLED(225, 225, 225, 100);
			finch.setLED(200, 200, 200, 100);
			finch.setLED(175, 175, 175, 100);
			finch.setLED(150, 150, 150, 100);
			finch.setLED(125, 125, 125, 100);
			finch.setLED(100, 100, 100, 100);
			finch.setLED(75, 75, 75, 100);
			finch.setLED(50, 50, 50, 100);
			finch.setLED(25, 25, 25, 100);
			JOptionPane.showMessageDialog(null,"Turning off..." , "Goodbye", 1, null); //A message box tells the user that the program is 'Turning off...'
			finch.buzz(100, 250);
			finch.buzz(75, 250);
			finch.buzz(50, 250);
			finch.buzz(25, 250);
			System.exit(0); //The system shuts down
		}
	}

	//RE TRACE ARRAY LIST
	public static void reTraceArrayList()
	{
		t.add(Character.toString(x)+","+Integer.toString(d)+","+Integer.toString(s)); //Whatever the user has input for Movement or Turn, then the character, duration and speed gets added into the t arraylist
		aux = t.size(); //Variable aux is initialised as the arraylist size
	}

	//COMBINED ARRAY LIST
	public static void CombinedArrayList()
	{
		if (x == 'T') //If the character x is equal to T...
		{
			w.add(Character.toString(x)+","+Integer.toString(a)); //Whatever the user has input the character and user input gets added into the w arraylist
		}
		else //Other characters
		{
			w.add(Character.toString(x)+","+Integer.toString(d)+","+Integer.toString(s)); //Whatever the user has input for Movement or Turn, then the character, duration and speed gets added into the w arraylist
		}
		log = w.size(); //Variable log is initialised as the arraylist size
	}

	//DOCUMENT
	public static void Document()
	{
		try //A try is thrown
		{
			PrintWriter pw = new PrintWriter("Navigate.txt"); //A PrintWriter is created. First it is set as pw, and a new .txt file is created
			Date date = new Date(); //A new variable is made called date. This is how the date is imported
			String strDateFormat = "HH:mm:ss"; //A new string variable is made, which shows the 24-hour clock format
			DateFormat dateFormat = new SimpleDateFormat(strDateFormat); //To display the time only, the dateFormat becomes simplified as strDateFormat
			String formattedDate = dateFormat.format(date); //A new string is made as formattedDate which is initialised as the format of the time
			pw.println(formattedDate); //Instead of using System.out, pw. is used. It prints out the 24-hour clock on top of the document
			pw.println(""); //To seperate the time from the list, I have made an empty statement
			for(int i=0; i < log; i++) //A for loop is made. Integer i is 0, then it makes sure that the i is lesser than log, then it increments the i
			{
				String value = w.get(i); //A new String variable 'value' is made. It is initialised as w.get (i). This gets the index of the w arraylist
				w.set(i, value + 1); //w is set as the index, then the value which is added by 1
				x = value.charAt(0); //x is initialised as value which gets the character
				switch(x) //A switch statement is made
				{
				case 'F': //Forward case
					pw.println(Character.toString(x) + " " + Integer.toString(d) + " " + Integer.toString(s)); //It prints out the block of code
					break;
				case 'B': //Backward case
					pw.println(Character.toString(x) + " " + Integer.toString(d) + " " + Integer.toString(s)); //It prints out the block of code
					break;
				case 'L': //Left case
					pw.println(Character.toString(x) + " " + Integer.toString(d) + " " + Integer.toString(s)); //It prints out the block of code
					break;
				case 'R': //Right case
					pw.println(Character.toString(x) + " " + Integer.toString(d) + " " + Integer.toString(s)); //It prints out the block of code
					break;
				case 'T': //Trace case
					pw.println(Character.toString(x) + " " + Integer.toString(a)); //It prints out the block of code
					break;
				case 'O': //Random case
					pw.println(Character.toString(x) + " " + Integer.toString(d) + " " + Integer.toString(s)); //It prints out the block of code
				}
			}
			pw.close(); //In order to prevent a code leak, I have closed the pw, to prevent any leakage
		}
		catch (IOException e) //If an exception is required to be caught
		{
			e.printStackTrace(); //Then the IOException e is printed as a Stack Trace
		}
		finch.buzz(15000, 100); //The buzzer is set at 15,000 Hz for 0,1 seconds
		finch.setLED(255, 255, 255); //The LED colours are set as white
		JOptionPane.showMessageDialog(null, "Navigate.txt file saved", "Navigate", 1); //A message box tells the user that the text file is saved in a location
	}
}