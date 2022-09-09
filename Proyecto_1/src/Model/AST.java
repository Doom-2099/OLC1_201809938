package Model;

import java.util.ArrayList;

//import Model.Components.Expressions.NodeExp;
import Model.Components.Instruccions.NodeIns;

public class AST {
    private final static AST instance = new AST();
    private ArrayList<NodeIns> root = null;

    public final static AST getInstance() {
        return instance;
    }

    public void setRoot(ArrayList<NodeIns> root) {
        this.root = root;
    }

    public void walkTree() {
        ArrayList<NodeIns> aux = root;
        System.out.println(aux.size());
    }
}
