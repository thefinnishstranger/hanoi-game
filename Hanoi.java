import java.io.*;
import java.util.Scanner;

public class Hanoi {
  public static block[] top = {null,null,null};
  public static boolean gameOver = false;
  public static int counter = 0;
  public static int lowestScore = 0;
  public static int gameCount = 0;
  public static void main(String[] args) {


//Let the games begin
    startGame();

    while (true) {
      System.out.println("Select y to play again, or q to quit the game");
      Scanner s1 = new Scanner(System.in);
      String playAgain = s1.nextLine();
      if (playAgain.equals("y") || playAgain.equals("Y")) {
        top[0] = null;
        top[1] = null;
        top[2] = null;
        gameOver = false;
        counter = 0;
        gameCount++;
        System.out.println("Current target to beat: " + lowestScore);
        startGame();
      } else if (playAgain.equals("q") || playAgain.equals("Q")) {
        System.out.println("Thanks for playing!");
        System.exit(0);
      }
    }
  }

  public static block pop(int stackNum)
  {
    block moveMe = top[stackNum];
    if (top[stackNum] == null){
      System.out.println("You chose an empty stack");
    } else {
      top[stackNum] = top[stackNum].next;
    }
    return moveMe;
  }
  public static boolean push(block moveMe, int stackNum) {
    if (moveMe == null) {
      return false;
    }
    if (top[stackNum] == null) {
      top[stackNum] = moveMe;
      moveMe.next = null;
      return true;
    } else {
      if (moveMe.boxSize > top[stackNum].boxSize) {
        return false;
      } else {
        moveMe.next = top[stackNum];
        top[stackNum] = moveMe;
        if ((top[0] == null) && ((top[1] == null) || (top[2] == null))) {
          System.out.println("You completed the game!");
          gameOver = true;
        }
        return true;
      }
    }
  }

  public static void startGame() {

    String dataExport = "output.txt";


    top[0] = new block();
    top[0].value = 1;
    top[0].boxSize = 100;
    for (int x = 2;x <=4; x++)
    {
      block temp = new block();
      temp.value = x;
      temp.boxSize = (100 - (25 * (x-1)));
      temp.next = top[0];
      top[0]=temp;
    }

    System.out.println("Let us start the games!");
    Scanner s = new Scanner(System.in);

    try {

      PrintWriter exportData = new PrintWriter(new FileWriter(dataExport, true));
      while (!gameOver) {
        System.out.println("Which stack are you choosing?");
        int choice = s.nextInt();
        if (choice > 2 || choice < 0){
          System.out.println("Try again");
          choice = s.nextInt();
        }
        block nextUp = pop(choice);


        if (nextUp != null) {
          System.out.println("Where are you going to put it?");
          int choice2 = s.nextInt();
          if (choice2 > 2 || choice2 < 0) {
            System.out.println("Try again");
            choice2 = s.nextInt();
          }
          System.out.println("You moved the block number " + nextUp.value + " to the stack number " + choice2);
          boolean pushSuccess = push(nextUp, choice2);
          counter++;

          if (pushSuccess) {
            exportData.println("Pop " + choice);
            exportData.println("Push " + choice2);
          } else {
            System.out.println("Invalid move! Putting back the block number " + nextUp.value + " to stack number " + choice);
            push(nextUp, choice);
          }
        }


      }

      if (gameCount == 0) {
        lowestScore = counter;
        exportData.println("Target to beat: " + lowestScore);

      } else {

        if (counter < lowestScore) {
          lowestScore = counter;
          exportData.println("Target to beat: " + lowestScore);
        } else {
          System.out.println("Current target to beat is: " + lowestScore);
        }

      }
      exportData.close();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}

