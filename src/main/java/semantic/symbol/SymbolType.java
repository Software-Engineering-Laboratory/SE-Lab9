package semantic.symbol;

/**
 * Created by mohammad hosein on 6/28/2015.
 */
public enum SymbolType{

    Int (""),
    Bool ("@");

    public final String label;

    SymbolType(String label) {
        this.label = label;
    }
}
