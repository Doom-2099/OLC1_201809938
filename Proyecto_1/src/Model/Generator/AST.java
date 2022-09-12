package Model.Generator;

import java.util.ArrayList;

import Model.Components.Expressions.NodeExp;
import Model.Components.Instruccions.NodeIns;

public class AST {
    private final static AST instance = new AST();
    private ArrayList<NodeIns> root = null;
    private String grafo = "digraph G { \ngraph [shape = circle];\n";
    private StringBuilder nodos = new StringBuilder();
    private StringBuilder conexiones = new StringBuilder();
    private int contador = 0;

    public final static AST getInstance() {
        return instance;
    }

    public void setRoot(ArrayList<NodeIns> root) {
        this.root = root;
    }

    public void startWalk() {
        ArrayList<NodeIns> aux = root;
        String padre = "node" + contador++;
        String hijo = "node" + contador++;

        nodos.append(padre + "[label=\"INICIO\"];\n");
        nodos.append(hijo + "[label=\"INSTRUCCIONES\"];\n");
        conexiones.append(padre + "->" + hijo + "\n");
        padre = hijo;

        walkTree(aux, padre);

        System.out.println(grafo);
        System.out.println(nodos.toString());
        System.out.println(conexiones.toString());
        System.out.println("}");
    }

    public void walkTree(ArrayList<NodeIns> root, String padre) {
        String hijo;
        String hijoAux = "";
        for (int i = 0; i < root.size(); i++) {
            if ((i + 1) < root.size()) {
                hijo = "node" + contador++;
                hijoAux = "node" + contador++;
                nodos.append(hijo + "[label=\"INSTRUCCION\"];\n");
                nodos.append(hijoAux + "[label=\"INSTRUCCIONES\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                conexiones.append(padre + "->" + hijoAux + "\n");
                padre = hijo;
            } else {
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"INSTRUCCION\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                padre = hijo;
            }

            _walkTreeIns(root.get(i), padre);

            if (hijoAux != "") {
                padre = hijoAux;
                hijoAux = "";
            }
        }
    }

    public void _walkTreeIns(NodeIns nodo, String padre) {
        switch (nodo.getInstruccion()) {
            case "declaracion":
                String hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"DECLARACION\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                padre = hijo;

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"" + nodo.getPropIns().get("tipoDato") + "\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                ArrayList<String> ids = (ArrayList) nodo.getPropIns().get("identificadores");

                String aux = padre;
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"IDENTIFICADORES\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                padre = hijo;

                for (String id : ids) {
                    hijo = "node" + contador++;
                    nodos.append(hijo + "[label=\"" + id + "\"];\n");
                    conexiones.append(padre + "->" + hijo + "\n");
                }

                padre = aux;
                if (nodo.getPropIns().get("valor") != null) {
                    hijo = "node" + contador++;
                    nodos.append(hijo + "[label=\"EXPRESION\"];\n");
                    conexiones.append(padre + "->" + hijo + "\n");

                    _walkTreeExp((NodeExp) nodo.getPropIns().get("valor"), hijo);
                }

                break;

            case "asignacion":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"ASIGNACION\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                padre = hijo;

                // Agregar Propiedades De Nodo Asignacion
                ids = (ArrayList) nodo.getPropIns().get("identificadores");
                aux = padre;

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"IDENTIFICADORES\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                padre = hijo;

                for (String id : ids) {
                    hijo = "node" + contador++;
                    nodos.append(hijo + "[label=\"" + id + "\"];\n");
                    conexiones.append(padre + "->" + hijo + "\n");
                }

                padre = aux;
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"EXPRESION\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                _walkTreeExp((NodeExp) nodo.getPropIns().get("valor"), hijo);

                break;

            case "if-simple":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"IF\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                padre = hijo;

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"if\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"EXPRESION\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                _walkTreeExp((NodeExp) nodo.getPropIns().get("condicion"), hijo);

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"INSTRUCCIONES\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                padre = hijo;

                ArrayList<NodeIns> instrucciones = (ArrayList) nodo.getInstrucciones();
                walkTree(instrucciones, padre);
                break;

            case "if-else":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"IF-ELSE\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                padre = hijo;

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"if\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"EXPRESION\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                _walkTreeExp((NodeExp) nodo.getPropIns().get("condicion"), hijo);

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"INSTRUCCIONES\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                instrucciones = (ArrayList) nodo.getInstrucciones();
                walkTree(instrucciones, hijo);

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"else\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"INSTRUCCIONES\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                NodeIns nodoaux = (NodeIns) nodo.getPropIns().get("else");
                instrucciones = (ArrayList) nodoaux.getInstrucciones();
                walkTree(instrucciones, hijo);

                break;

            case "if-elif":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"IF-ELSE\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                padre = hijo;

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"if\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"EXPRESION\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                _walkTreeExp((NodeExp) nodo.getPropIns().get("condicion"), hijo);

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"INSTRUCCIONES\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                instrucciones = (ArrayList) nodo.getInstrucciones();
                walkTree(instrucciones, hijo);

                if (nodo.getPropIns().get("elif") != null) {
                    hijo = "node" + contador++;
                    nodos.append(hijo + "[label=\"ELSE-IF\"];\n");
                    conexiones.append(padre + "->" + hijo + "\n");
                    padre = hijo;

                    nodoaux = (NodeIns) nodo.getPropIns().get("elif");
                    _walkTreeIns(nodoaux, padre);
                }

                break;

            case "if-elif-else":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"IF-ELSE\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                padre = hijo;

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"if\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"EXPRESION\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                _walkTreeExp((NodeExp) nodo.getPropIns().get("condicion"), hijo);

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"INSTRUCCIONES\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                instrucciones = (ArrayList) nodo.getInstrucciones();
                walkTree(instrucciones, hijo);

                if (nodo.getPropIns().get("elif") != null) {
                    hijo = "node" + contador++;
                    nodos.append(hijo + "[label=\"ELSE-IF\"];\n");
                    conexiones.append(padre + "->" + hijo + "\n");
                    padre = hijo;

                    nodoaux = (NodeIns) nodo.getPropIns().get("elif");
                    _walkTreeIns(nodoaux, padre);
                }

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"else\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"INSTRUCCIONES\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                nodoaux = (NodeIns) nodo.getPropIns().get("else");
                instrucciones = (ArrayList) nodoaux.getInstrucciones();
                walkTree(instrucciones, hijo);

                break;

            case "elif":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"else if\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"EXPRESION\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                _walkTreeExp((NodeExp) nodo.getPropIns().get("condicion"), hijo);

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"INSTRUCCIONES\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                instrucciones = (ArrayList) nodo.getInstrucciones();
                walkTree(instrucciones, hijo);

                if (nodo.getPropIns().get("elif") != null) {
                    hijo = "node" + contador++;
                    nodos.append(hijo + "[label=\"ELSE-IF\"];\n");
                    conexiones.append(padre + "->" + hijo + "\n");
                    padre = hijo;

                    nodoaux = (NodeIns) nodo.getPropIns().get("elif");
                    _walkTreeIns(nodoaux, padre);
                }

                break;

            case "switch":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"SWITCH\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                padre = hijo;

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"switch\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"EXPRESION\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                _walkTreeExp((NodeExp) nodo.getPropIns().get("variable"), hijo);

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"CASES\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                ArrayList<NodeIns> casos = (ArrayList) nodo.getPropIns().get("casos");
                for (NodeIns caso : casos) {
                    _walkTreeIns(caso, hijo);
                }

                break;

            case "case":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"CASE\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                padre = hijo;

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"EXPRESION\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                _walkTreeExp((NodeExp) nodo.getPropIns().get("expresion"), hijo);

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"INSTRUCCIONES\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                instrucciones = (ArrayList) nodo.getInstrucciones();
                walkTree(instrucciones, hijo);

                break;

            case "case_default":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label\"CASE\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                padre = hijo;

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"default:\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"INSTRUCCIONES\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                instrucciones = (ArrayList) nodo.getInstrucciones();
                walkTree(instrucciones, hijo);
                break;

            case "for":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"FOR\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                padre = hijo;

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"for\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"EXPRESION\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                _walkTreeExp((NodeExp) nodo.getPropIns().get("variable"), hijo);

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"EXPRESION\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                _walkTreeExp((NodeExp) nodo.getPropIns().get("inicio"), hijo);

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"EXPRESION\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                _walkTreeExp((NodeExp) nodo.getPropIns().get("fin"), hijo);

                if (nodo.getPropIns().get("incremento") != null) {
                    hijo = "node" + contador++;
                    nodos.append(hijo + "[label=\"EXPRESION\"];\n");
                    conexiones.append(padre + "->" + hijo + "\n");

                    _walkTreeExp((NodeExp) nodo.getPropIns().get("incremento"), hijo);
                }

                if (nodo.getInstrucciones() != null) {
                    hijo = "node" + contador++;
                    nodos.append(hijo + "[label=\"INSTRUCCIONES\"];\n");
                    conexiones.append(padre + "->" + hijo + "\n");

                    instrucciones = (ArrayList) nodo.getInstrucciones();
                    walkTree(instrucciones, hijo);
                }

                break;

            case "while":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"WHILE\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                padre = hijo;

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"while\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"EXPRESION\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                _walkTreeExp((NodeExp) nodo.getPropIns().get("condicion"), hijo);

                if (nodo.getInstrucciones() != null) {
                    hijo = "node" + contador++;
                    nodos.append(hijo + "[label=\"INSTRUCCIONES\"];\n");
                    conexiones.append(padre + "->" + hijo + "\n");

                    instrucciones = (ArrayList) nodo.getInstrucciones();
                    walkTree(instrucciones, hijo);
                }
                break;

            case "doWhile":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"DO_WHILE\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                padre = hijo;

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"do\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                if (nodo.getInstrucciones() != null) {
                    hijo = "node" + contador++;
                    nodos.append(hijo + "[label=\"INSTRUCCIONES\"];\n");
                    conexiones.append(padre + "->" + hijo + "\n");

                    instrucciones = (ArrayList) nodo.getInstrucciones();
                    walkTree(instrucciones, hijo);
                }

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"while\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"EXPRESION\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                _walkTreeExp((NodeExp) nodo.getPropIns().get("condicion"), hijo);
                break;

            case "declaracion_metodo":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"DECLARACION_METODO\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                padre = hijo;

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"" + nodo.getPropIns().get("id") + "\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                if (nodo.getPropIns().get("parametros") != null) {
                    hijo = "node" + contador++;
                    nodos.append(hijo + "[label=\"PARAMETROS\"];\n");
                    conexiones.append(padre + "->" + hijo + "\n");
                    aux = hijo;

                    ArrayList<String> parametros = (ArrayList) nodo.getPropIns().get("parametros");

                    for (String id : parametros) {
                        hijo = "node" + contador++;
                        nodos.append(hijo + "[label=\"" + id + "\"];\n");
                        conexiones.append(aux + "->" + hijo + "\n");
                    }
                }

                if (nodo.getInstrucciones() != null) {
                    hijo = "node" + contador++;
                    nodos.append(hijo + "[label=\"INSTRUCCIONES\"];\n");
                    conexiones.append(padre + "->" + hijo + "\n");

                    instrucciones = (ArrayList) nodo.getInstrucciones();
                    walkTree(instrucciones, hijo);
                }
                break;

            case "declaracion_funcion":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"DECLARACION_FUNCION\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                padre = hijo;

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"" + nodo.getPropIns().get("id") + "\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"" + nodo.getPropIns().get("tipoRetorno") + "\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                if (nodo.getPropIns().get("parametros") != null) {
                    hijo = "node" + contador++;
                    nodos.append(hijo + "[label=\"PARAMETROS\"];\n");
                    conexiones.append(padre + "->" + hijo + "\n");
                    aux = hijo;

                    ArrayList<String> parametros = (ArrayList) nodo.getPropIns().get("parametros");

                    for (String id : parametros) {
                        hijo = "node" + contador++;
                        nodos.append(hijo + "[label=\"" + id + "\"];\n");
                        conexiones.append(aux + "->" + hijo + "\n");
                    }
                }

                if (nodo.getInstrucciones() != null) {
                    hijo = "node" + contador++;
                    nodos.append(hijo + "[label=\"INSTRUCCIONES\"];\n");
                    conexiones.append(padre + "->" + hijo + "\n");

                    instrucciones = (ArrayList) nodo.getInstrucciones();
                    walkTree(instrucciones, hijo);
                }

                break;

            case "ejecutar":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"EJECUTAR\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                padre = hijo;

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"" + nodo.getPropIns().get("id") + "\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                if (nodo.getPropIns().get("parametros") != null) {
                    hijo = "node" + contador++;
                    nodos.append(hijo + "[label=\"PARAMETROS\"];\n");
                    conexiones.append(padre + "->" + hijo + "\n");
                    aux = hijo;
                    ArrayList<NodeExp> params = (ArrayList) nodo.getPropIns().get("parametros");

                    for (NodeExp exp : params) {
                        hijo = "node" + contador++;
                        nodos.append(hijo + "[label=\"EXPRESION\"];\n");
                        conexiones.append(aux + "->" + hijo + "\n");

                        _walkTreeExp(exp, hijo);
                    }
                }

                break;

            case "imprimir":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"IMPRIMIR\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                padre = hijo;

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"EXPRESION\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                _walkTreeExp((NodeExp) nodo.getPropIns().get("expresion"), hijo);
                break;

            case "imprimir_nl":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"IMPRIMIR_NL\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                padre = hijo;

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"EXPRESION\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                _walkTreeExp((NodeExp) nodo.getPropIns().get("expresion"), hijo);
                break;

            case "return":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"RETURN\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                padre = hijo;

                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"EXPRESION\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");

                _walkTreeExp((NodeExp) nodo.getPropIns().get("expresion"), hijo);
                break;
        }
    }

    private void _walkTreeExp(NodeExp exp, String padre) {
        switch (exp.getOperacion()) {
            case "M":
                String hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"" + exp.getOperacion() + "\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                _walkTreeExp((NodeExp) exp.getOp1(), hijo);
                break;

            case "+":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"" + exp.getOperacion() + "\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                _walkTreeExp((NodeExp) exp.getOp1(), hijo);
                _walkTreeExp((NodeExp) exp.getOp2(), hijo);
                break;

            case "-":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"" + exp.getOperacion() + "\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                _walkTreeExp((NodeExp) exp.getOp1(), hijo);
                _walkTreeExp((NodeExp) exp.getOp2(), hijo);
                break;

            case "*":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"" + exp.getOperacion() + "\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                _walkTreeExp((NodeExp) exp.getOp1(), hijo);
                _walkTreeExp((NodeExp) exp.getOp2(), hijo);
                break;

            case "/":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"" + exp.getOperacion() + "\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                _walkTreeExp((NodeExp) exp.getOp1(), hijo);
                _walkTreeExp((NodeExp) exp.getOp2(), hijo);
                break;

            case "^":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"" + exp.getOperacion() + "\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                _walkTreeExp((NodeExp) exp.getOp1(), hijo);
                _walkTreeExp((NodeExp) exp.getOp2(), hijo);
                break;

            case "%":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"" + exp.getOperacion() + "\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                _walkTreeExp((NodeExp) exp.getOp1(), hijo);
                _walkTreeExp((NodeExp) exp.getOp2(), hijo);
                break;

            case "()":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"" + exp.getOperacion() + "\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                _walkTreeExp((NodeExp) exp.getOp1(), hijo);
                break;

            case "==":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"" + exp.getOperacion() + "\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                _walkTreeExp((NodeExp) exp.getOp1(), hijo);
                _walkTreeExp((NodeExp) exp.getOp2(), hijo);
                break;

            case "!=":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"" + exp.getOperacion() + "\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                _walkTreeExp((NodeExp) exp.getOp1(), hijo);
                _walkTreeExp((NodeExp) exp.getOp2(), hijo);
                break;

            case ">":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"" + exp.getOperacion() + "\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                _walkTreeExp((NodeExp) exp.getOp1(), hijo);
                _walkTreeExp((NodeExp) exp.getOp2(), hijo);
                break;

            case "<":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"" + exp.getOperacion() + "\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                _walkTreeExp((NodeExp) exp.getOp1(), hijo);
                _walkTreeExp((NodeExp) exp.getOp2(), hijo);
                break;

            case ">=":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"" + exp.getOperacion() + "\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                _walkTreeExp((NodeExp) exp.getOp1(), hijo);
                _walkTreeExp((NodeExp) exp.getOp2(), hijo);
                break;

            case "<=":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"" + exp.getOperacion() + "\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                _walkTreeExp((NodeExp) exp.getOp1(), hijo);
                _walkTreeExp((NodeExp) exp.getOp2(), hijo);
                break;

            case "||":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"" + exp.getOperacion() + "\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                _walkTreeExp((NodeExp) exp.getOp1(), hijo);
                _walkTreeExp((NodeExp) exp.getOp2(), hijo);
                break;

            case "&&":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"" + exp.getOperacion() + "\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                _walkTreeExp((NodeExp) exp.getOp1(), hijo);
                _walkTreeExp((NodeExp) exp.getOp2(), hijo);
                break;

            case "!":
                hijo = "node" + contador++;
                nodos.append(hijo + "[label=\"" + exp.getOperacion() + "\"];\n");
                conexiones.append(padre + "->" + hijo + "\n");
                _walkTreeExp((NodeExp) exp.getOp1(), hijo);
                break;

            default:
                if (exp.getOperacion().contains("ejecutar")) {
                    hijo = "node" + contador++;
                    nodos.append(hijo + "[label=\"EJECUTAR\"];\n");
                    conexiones.append(padre + "->" + hijo + "\n");
                    padre = hijo;

                    hijo = "node" + contador++;
                    String[] detalle = exp.getOperacion().split("|");
                    nodos.append(hijo + "[label=\"" + detalle[1] + "\"];\n");
                    conexiones.append(padre + "->" + hijo + "\n");

                    hijo = "node" + contador++;
                    nodos.append(hijo + "[label=\"PARAMETROS\"];\n");
                    conexiones.append(padre + "->" + hijo + "\n");
                    padre = hijo;

                    ArrayList<NodeExp> params = exp.getParametros();
                    for (NodeExp p : params) {
                        _walkTreeExp(p, padre);
                    }
                } else {
                    hijo = "node" + contador++;
                    nodos.append(hijo + "[label=\"" + exp.getOperacion() + "\"];\n");
                    conexiones.append(padre + "->" + hijo + "\n");
                }
                break;
        }
    }
}
