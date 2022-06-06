import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    Map<String, Integer> symbols;

    int nextVariable = 16;

    public SymbolTable(){

        symbols = new HashMap<>();

        symbols.put("SP", 0);
        symbols.put("LCL", 1);
        symbols.put("ARG", 2);
        symbols.put("THIS", 3);
        symbols.put("THAT", 4);

        for (int i = 0; i <= 15; i++) {
            symbols.put("R" + i, i);
        }

        symbols.put("SCREEN", 16384);
        symbols.put("KBD", 24576);

    }

    public void addEntry(String symbol, int address) {

        symbols.put(symbol, address);

    }

    public void addVariable(String symbol){

        if (symbols.containsKey(symbol)) {

            System.out.println("This variable has already been added!");

        }

        symbols.put(symbol, nextVariable);
        nextVariable++;

    }

    public boolean contains(String symbol) {

        return symbols.containsKey(symbol);

    }

    public int getAddress(String symbol) {

        return symbols.get(symbol);

    }

    public void printSymbols() {

        for (String symbol : symbols.keySet()) {

            System.out.println(symbol + " : " + symbols.get(symbol));

        }

    }

}
