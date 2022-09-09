package Model.Components.Instruccions;

import java.util.ArrayList;
import java.util.Hashtable;

public class NodeIns {
    private String instruccion;
    private Hashtable<String, Object> propIns;
    private ArrayList<Object> instrucciones;

    public NodeIns(String instruccion) {
        this.instruccion = instruccion;
        this.propIns = new Hashtable<>();
        this.instrucciones = null;
    }

    public String getInstruccion() {
        return instruccion;
    }

    public void setInstruccion(String instruccion) {
        this.instruccion = instruccion;
    }

    public Hashtable<String, Object> getPropIns() {
        return propIns;
    }

    public void putFeatures(String clave, Object valor) {
        this.propIns.put(clave, valor);
    }

    public ArrayList<Object> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(ArrayList<Object> instrucciones) {
        this.instrucciones = instrucciones;
    }
}
