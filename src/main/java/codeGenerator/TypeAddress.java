package codeGenerator;

/**
 * Created by mohammad hosein on 6/28/2015.
 */
public enum TypeAddress {
    Direct (""),
    Indirect ("@"),
    Imidiate ("#");

    public final String label;

    TypeAddress(String label) {
        this.label = label;
    }
}