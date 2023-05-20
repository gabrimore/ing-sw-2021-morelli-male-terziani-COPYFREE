package it.polimi.ingsw.view.cli.complete;

import it.polimi.ingsw.view.cli.Utilities;
import it.polimi.ingsw.view.cli.CliAdapter;

public class InitialLogoDrawing implements CompleteDrawing{
    private final int HEIGHT=13;
    private final int LENGTH=86;

    private char[][] logoBoard;

    private CliAdapter cliAdapter;

    public InitialLogoDrawing(CliAdapter cliAdapter){
        this.cliAdapter=cliAdapter;
        this.logoBoard=new char[HEIGHT][LENGTH];
    }


    /**
     * draws the logo on a 2dimensional array
     */
    public void draw(){
        Utilities.stringToChar(logoBoard, 0, 0, "███╗░░░███╗░█████╗░░██████╗████████╗███████╗██████╗ ░██████╗  ░█████╗░███████╗");
        Utilities.stringToChar(logoBoard, 1, 0, "████╗░████║██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝  ██╔══██╗██╔════╝");
        Utilities.stringToChar(logoBoard, 2, 0, "██╔████╔██║███████║╚█████╗░░░░██║░░░█████╗░░██████╔╝╚█████╗░  ██║░░██║█████╗░░");
        Utilities.stringToChar(logoBoard, 3, 0, "██║╚██╔╝██║██╔══██║░╚═══██╗░░░██║░░░██╔══╝░░██╔══██╗║░╚═══██  ██║░░██║██╔══╝░░");
        Utilities.stringToChar(logoBoard, 4, 0, "██║░╚═╝░██║██║░░██║██████╔╝░░░██║░░░███████╗██║░░██║║██████╔  ╚█████╔╝██║░░░░░");
        Utilities.stringToChar(logoBoard, 5, 0, "╚═╝░░░░░╚═╝╚═╝░░╚═╝╚═════╝░░░░╚═╝░░░╚══════╝╚═╝░░╚═╝╝╚═════╝  ░╚════╝░╚═╝░░░░░");
        Utilities.stringToChar(logoBoard, 7, 0, "██████╗░███████╗███╗░░██╗░█████╗░██╗░██████╗░██████╗░█████╗░███╗░░██╗░█████╗░███████╗");
        Utilities.stringToChar(logoBoard, 8, 0, "██╔══██╗██╔════╝████╗░██║██╔══██╗██║██╔════╝██╔════╝██╔══██╗████╗░██║██╔══██╗██╔════╝");
        Utilities.stringToChar(logoBoard, 9, 0, "██████╔╝█████╗░░██╔██╗██║███████║██║╚█████╗░╚█████╗░███████║██╔██╗██║██║░░╚═╝█████╗░░");
        Utilities.stringToChar(logoBoard, 10, 0, "██╔══██╗██╔══╝░░██║╚████║██╔══██║██║░╚═══██╗░╚═══██╗██╔══██║██║╚████║██║░░██╗██╔══╝░░");
        Utilities.stringToChar(logoBoard, 11, 0, "██║░░██║███████╗██║░╚███║██║░░██║██║██████╔╝██████╔╝██║░░██║██║░╚███║╚█████╔╝███████╗");
        Utilities.stringToChar(logoBoard, 12, 0, "╚═╝░░╚═╝╚══════╝╚═╝░░╚══╝╚═╝░░╚═╝╚═╝╚═════╝░╚═════╝░╚═╝░░╚═╝╚═╝░░╚══╝░╚════╝░╚══════╝");

    }

    /**
     * prints the logo
     */
    public void printElement(){
        for(int i=0; i<HEIGHT; i++){
            for(int j=0; j<LENGTH; j++){
                System.out.print(logoBoard[i][j]);
            }
            System.out.println();
        }
    }
}
