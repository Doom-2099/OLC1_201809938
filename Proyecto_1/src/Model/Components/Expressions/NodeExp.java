package Model.Components.Expressions;

import java.util.ArrayList;

public class NodeExp {
    private String operacion;
    private NodeExp op1;
    private NodeExp op2;
    private ArrayList<NodeExp> parametros;

    public NodeExp(String operacion) {
        this.operacion = operacion;
        this.op1 = null;
        this.op2 = null;
        this.parametros = null;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public NodeExp getOp1() {
        return op1;
    }

    public void setOp1(NodeExp op1) {
        this.op1 = op1;
    }

    public NodeExp getOp2() {
        return op2;
    }

    public void setOp2(NodeExp op2) {
        this.op2 = op2;
    }
    
    public ArrayList<NodeExp> getParametros() {
        return parametros;
    }

    public void setParametros(ArrayList<NodeExp> parametros) {
        this.parametros = parametros;
    }

    
}
