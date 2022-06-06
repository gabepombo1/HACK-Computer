import java.io.*;
import java.util.Scanner;

public class Assembler {

    public static void main(String[] args) {

        String fileName = "Max";

        FileInputStream file = null;
        FileInputStream file2 = null;

        try {

            file = new FileInputStream(fileName + ".asm");
            file2 = new FileInputStream(fileName + ".asm");

        } catch (FileNotFoundException e) {

            e.printStackTrace();
            System.exit(1);

        }

        File output = new File(fileName + ".hack");

        if (output.exists()) {

            output.delete();

        }

        PrintWriter writer = null;

        try {

            output.createNewFile();
            writer = new PrintWriter(output);

        } catch (IOException e) {

            e.printStackTrace();
            System.exit(1);

        }

        SymbolTable st = buildSymbolTable(file2);
        //buildSymbolTable(file2).printSymbols();

        parse(file, writer, st);

    }

    public static SymbolTable buildSymbolTable(FileInputStream in){

        Parser parser = new Parser(in);

        int romAddress = 0;

        SymbolTable symbolTable = new SymbolTable();

        while (parser.hasMoreCommands()){

            parser.advance();

            if(parser.commandType() == CommandType.L_COMMAND){

                symbolTable.addEntry(parser.symbol(), romAddress);

            }else {

                romAddress++;

            }

        }

        return symbolTable;

    }

    public static void parse(FileInputStream in, PrintWriter out, SymbolTable symbolTable){

        Parser parser = new Parser(in);

        while (parser.hasMoreCommands()){

            parser.advance();

            switch (parser.commandType()){

                case A_COMMAND:
                    String symbol = parser.symbol();

                    int decimalNum;

                    try{

                        decimalNum = Integer.parseInt(symbol);

                    } catch(NumberFormatException e){

                        if (!symbolTable.contains(symbol)) {

                            symbolTable.addVariable(symbol);

                        }
                        decimalNum = symbolTable.getAddress(symbol);

                    }

                    String binary = Integer.toBinaryString(decimalNum);
                    binary = ("0".repeat(15 - binary.length())) + binary;
                    binary = "0" + binary;
                    out.print(binary);
                    break;
                case C_COMMAND:
                    out.print("111");
                    out.print(Code.comp(parser.comp()));
                    out.print(Code.dest(parser.dest()));
                    out.print(Code.jump(parser.jump()));
                    break;
                case L_COMMAND:
                    continue;

            }

            out.println();

        }

        out.close();

    }

}
