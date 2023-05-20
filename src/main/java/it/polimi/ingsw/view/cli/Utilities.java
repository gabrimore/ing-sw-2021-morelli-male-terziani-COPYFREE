package it.polimi.ingsw.view.cli;

public class Utilities {

    private static int MAX_RES_NUM=3; // 999 max number of resource;

    /**
     * inserts an array of characters  into a precise position of a 2dimensional array of characters
     * @param base 2 dimensional array of characters
     * @param toInsert array of characters to be inserted
     * @param row row of the 2 dimensional array where to insert the character
     * @param initialColumn column of the 2 dimensional array where to insert the first character of the array toInsert
     */
    public static void insertCharToBase(char[][] base, char[] toInsert, int row, int initialColumn){
        for(int i=0; i<toInsert.length; i++){
            base[row][initialColumn+i]=toInsert[i];
        }
    }

    /**
     * inserts the single characters of a String into a 2 dimensional array of characters
     * @param base 2 dimensional array of characters where the string has to be inserted
     * @param row row of the 2 dimensional array where to insert the characters of the string
     * @param initialColumn column of the 2 dimensional array where to insert the first character of the array toInsert
     * @param toWrite string to be inserted into the 2dimensional array
     */
    public static void stringToChar(char[][] base,  int row, int initialColumn, String toWrite){

        char[] toWriteConverted= toWrite.toCharArray();

        insertCharToBase(base, toWriteConverted, row, initialColumn);

    }



    /**
     * inserts a single character inside a 2 dimensional array of characters if the indexes given are inside
     * the 2dimensional array dimensions
     * @param base 2 dimensional array of characters where the character has to be inserted
     * @param symbol character to be inserted
     * @param y row of the 2dimensional array where to insert the character
     * @param x column of the 2dimensional array where to insert the character
     */
    public static void drawChar(char base[][], char symbol, int y, int x) {
        if (y < 0 || y >= base.length || x < 0 || x >= base[0].length) return;
        else base[y][x] = symbol;//funziona solo per l'intero deckfield
    }

    /**
     * adds empty chars inside every position of a 2dimensional array of characters
     * @param base 2dimensional array of characters
     */
    public static void addEmptyChars(char[][] base){
        for(int i=0; i<base.length; i++){
            for(int j=0;j<base[0].length; j++){
                drawChar(base, ' ', i, j);
            }
        }
    }

    /**
     * transforms int number 2,3,4 into 2dimension array of character
     * @param numToDraw
     * @param base 2dimensional character array on which to draw
     * @param y_position row of the 2dimensional array where the card starts
     * @param x_position column of the 2dimensional array where the card starts
     */
    public static void drawNumber(int numToDraw, char[][] base, int y_position, int x_position){
        switch (numToDraw) {
            case 2:
                //TransformIntoDrawing.stringToChar(base, y_position, x_position, "┌---------┐");

                Utilities.stringToChar(base, y_position+1, x_position, "|  ____   |");
                Utilities.stringToChar(base, 2 + y_position, x_position, "| |___ \\  |");
                Utilities.stringToChar(base,3 + y_position, x_position, "|   __) | |");
                Utilities.stringToChar(base,4 + y_position, x_position, "|  / __/  |");
                Utilities.stringToChar(base, 5 + y_position, x_position, "| |_____| |");

                Utilities.stringToChar(base, y_position+6, x_position, "└---------┘");
                break;

            case 3:
                Utilities.stringToChar(base, y_position, x_position, "┌---------┐");

                Utilities.stringToChar(base,  1+y_position, x_position, "|  _____  |");
                Utilities.stringToChar(base, 2 + y_position, x_position, "| |___ /  |");
                Utilities.stringToChar(base, 3 + y_position, x_position, "|   |_ \\  |");
                Utilities.stringToChar(base, 4 + y_position, x_position, "|  ___) | |");
                Utilities.stringToChar(base, 5 + y_position, x_position, "| |____/  |");

                //TransformIntoDrawing.stringToChar(base, y_position+6, x_position, "└---------┘");
                break;

            case 4:
                //TransformIntoDrawing.stringToChar(base, y_position, x_position, "┌----------┐");

                Utilities.stringToChar(base,  1+y_position, x_position, "|  _  _    |");
                Utilities.stringToChar(base, 2 + y_position, x_position, "| | || |   |");
                Utilities.stringToChar(base, 3 + y_position, x_position, "| | || |_  |");
                Utilities.stringToChar(base, 4 + y_position, x_position, "| |__   _| |");
                Utilities.stringToChar(base, 5 + y_position, x_position, "|    |_|   |");

                Utilities.stringToChar(base, y_position+6, x_position, "└----------┘");
                break;
        }
    }

    /**
     * converts a 3 digits int into an array of 3 characters
     * @param number int number to be converted
     * @return array of 3 characters representing the number
     */
    public static char[] convertIntToCharArrayOfThree(int number){

        char[] toWriteReversed = new char[3];

        for(int i=0; i<toWriteReversed.length; i++) {
            if (number > 0) {
                toWriteReversed[i] = Character.forDigit(number % 10, 10);
                number = number / 10;
            }
            else toWriteReversed[i]=' ';
        }
        char[] toWriteConversed = new char[toWriteReversed.length];

        for(int i=0; i<toWriteConversed.length; i++){
            toWriteConversed[i]=toWriteReversed[toWriteReversed.length-1-i];
        }
        return toWriteConversed;

    }

    /**
     * inserts an int of a maximum 3 digits inside a 2 dimensional array of characters
     * @param base 2 dimensional array of characters where the int has to be inserted
     * @param row row of the 2 dimensional array of characters where to insert the int
     * @param initialColumn column of the 2 dimensional array where to insert the first digit of the int toWrite
     * @param toWrite maximum 3-digits int number
     */
    public static void intToChar(char[][] base,  int row, int initialColumn, int toWrite){

        insertCharToBase(base, Utilities.convertIntToCharArrayOfThree(toWrite), row, initialColumn);

    }
}
