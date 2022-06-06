import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

public class Parser {

    private Scanner fileScanner;
    private String currentCommand;

    public Parser(FileInputStream fileInputStream){

        this.fileScanner = new Scanner(fileInputStream);

    }

    //checks to see if there are still commands remaining in the file
    public boolean hasMoreCommands(){

        return fileScanner.hasNextLine();

    }

    //gets the next line of assembly and loads it into the parser as the current command
    public void advance(){

        do{

            this.currentCommand = fileScanner.nextLine().trim();

        }while(this.currentCommand.isBlank() || this.currentCommand.charAt(0)=='/');

        if(this.currentCommand.contains("//")){

            this.currentCommand = this.currentCommand.substring(0, this.currentCommand.indexOf("//"));
            this.currentCommand = this.currentCommand.trim();

        }

    }

    //determines what kind of instruction is being read
    public CommandType commandType(){

        if(this.currentCommand.charAt(0) == '@'){

            return CommandType.A_COMMAND;

        }else if(this.currentCommand.charAt(0) == '('){

            return CommandType.L_COMMAND;

        }else {

            return CommandType.C_COMMAND;

        }

    }

    //three types of symbols: builtIn(R1, SCREEN), labels (ones in parentheses), and variables
    //(telling machine to mark out an unoccupied memory address to hold the data that relates to this name)
    //returns the number after the @ sign in an A-instruction
    public String symbol(){

        if(commandType() == CommandType.A_COMMAND){

            return this.currentCommand.substring(1);

        }else if(commandType() == CommandType.L_COMMAND){

            return this.currentCommand.substring(1, this.currentCommand.indexOf(')'));

        }else{

            throw new RuntimeException("tried to call symbol on a C-command");

        }

    }

    //returns the portion of the assembly command that corresponds to the "dest"
    public String dest(){

        if(this.currentCommand.contains("=")){

            return this.currentCommand.substring(0,this.currentCommand.indexOf('='));

        } else{

            return "";

        }

    }

    //returns the part of the command that involves computation
    //can be on its own, can be between jump and dest, could be just before jump, or after dest
    public String comp(){

        int equalsString = currentCommand.indexOf("=");
        int startIndex = equalsString + 1;

        if(this.currentCommand.contains(";")){

            return this.currentCommand.substring(startIndex, this.currentCommand.indexOf(';'));

        }else{

            return this.currentCommand.substring(startIndex);

        }

    }

    //returns the portion of the code that has to do with JUMP
    public String jump(){

        if(this.currentCommand.contains(";")){

            return this.currentCommand.substring(this.currentCommand.indexOf(';') + 1);

        } else{

            return "";

        }

    }

}
