package Model.Generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Model.Components.Expressions.NodeExp;
import Model.Components.Instruccions.NodeIns;

public class GeneratePy {
    private final static GeneratePy instance = new GeneratePy();

    public static GeneratePy getInstance() {
        return instance;
    }

    private int ambito = 1;
    private boolean flagMain = true;
    private boolean flagMethod = false;
    StringBuilder contentMain = new StringBuilder();
    StringBuilder contentMethod = new StringBuilder();
    ArrayList<StringBuilder> methods = new ArrayList<>();

    private void changeflag() {
        this.flagMain = !flagMain;
        this.flagMethod = !flagMethod;
    }

    private void addText(String instruccion) {
        if(flagMain) {
            contentMain.append(instruccion);
        } else {
            contentMethod.append(instruccion);
        }
    }

    private String generateTabs() {
        String tabs = "";

        for(int i = 0; i <= ambito; i++) {
            tabs += "\t";
        }

        return tabs;
    }

    public String printTraduction() {
        File directorio = new File("/home/jorge/Escritorio/COMPI/OLC1-201809938/Proyecto_1/src/Dist/Python");
        File[] files = directorio.listFiles();

        if(files.length > 0) {
            for(File file : files) {
                file.delete();
            }
        }

        try{
            File docPython = new File("src/Dist/Python/out.py");
            if(docPython.createNewFile()) {
                FileWriter fw = new FileWriter(docPython);
                BufferedWriter bw = new BufferedWriter(fw);
                
                for(StringBuilder method : methods) {
                    bw.write(method.toString());
                }

                bw.write(contentMain.toString());
                bw.close();
                fw.close();
                return "Documento Python Generado";
            }
        } catch (IOException err) {
            return err.getLocalizedMessage();
        }

        return "Documento Python Generado";
    }

    public void startTraduction(NodeIns nodo) {
        String instruccion = "";
        switch (nodo.getInstruccion()) {
            case "declaracion":
                ArrayList<String> ids = (ArrayList)nodo.getPropIns().get("identificadores");
                instruccion = "\n" + generateTabs();
                for(String id : ids) {
                    if(instruccion != "") {
                        instruccion += ", ";
                    }

                    instruccion += id;
                }

                instruccion += " = ";

                for(int i = 0; i < ids.size(); i++) {
                    instruccion += getExp((NodeExp)nodo.getPropIns().get("valor"));

                    if((i + 1) < ids.size()) {
                        instruccion += ", ";
                    }
                }

                instruccion += "\n";
                addText(instruccion);
                break;

            case "asignacion":
                instruccion = "\n" + generateTabs();
                ids = (ArrayList)nodo.getPropIns().get("identificadores");
                for(String id : ids) {
                    if(instruccion != "") {
                        instruccion += ", ";
                    }

                    instruccion += id;
                }

                instruccion += " = ";

                for(int i = 0; i < ids.size(); i++) {
                    instruccion += getExp((NodeExp)nodo.getPropIns().get("valor"));

                    if((i + 1) < ids.size()) {
                        instruccion += ", ";
                    }
                }

                instruccion += "\n";
                addText(instruccion);
                break;
            
            case "if-simple":
                instruccion = "\n" + generateTabs();
                instruccion = "if ";
                instruccion += getExp((NodeExp)nodo.getPropIns().get("condicion")) + " :\n";
                addText(instruccion);
                ambito++;

                if(nodo.getInstrucciones() != null) {
                    ArrayList<NodeIns>instrucciones = (ArrayList)nodo.getInstrucciones();

                    for(NodeIns inst : instrucciones) {
                        startTraduction(inst);
                    }
                }
                
                ambito--;
                break;

            case "if-else":
                instruccion = "\n" + generateTabs();
                instruccion = "if ";
                instruccion += getExp((NodeExp)nodo.getPropIns().get("condicion")) + " :\n";
                addText(instruccion);
                ambito++;

                if(nodo.getInstrucciones() != null) {
                    ArrayList<NodeIns>instrucciones = (ArrayList)nodo.getInstrucciones();

                    for(NodeIns inst : instrucciones) {
                        startTraduction(inst);
                    }
                }

                ambito--;
                instruccion = "\n" + generateTabs();
                instruccion += "else:\n";
                NodeIns nodoaux = (NodeIns) nodo.getPropIns().get("else");
                addText(instruccion);
                ambito++;

                if(nodo.getInstrucciones() != null) {
                    ArrayList<NodeIns>instrucciones = (ArrayList)nodo.getInstrucciones();

                    for(NodeIns inst : instrucciones) {
                        startTraduction(inst);
                    }
                }

                ambito--;
                break;

            case "if-elif":
                instruccion = "\n" + generateTabs();
                instruccion += "if ";
                instruccion += getExp((NodeExp)nodo.getPropIns().get("condicion")) + " :\n";
                addText(instruccion);
                ambito++;

                if(nodo.getInstrucciones() != null) {
                    ArrayList<NodeIns>instrucciones = (ArrayList)nodo.getInstrucciones();

                    for(NodeIns inst : instrucciones) {
                        startTraduction(inst);
                    }
                }

                ambito--;
                if(nodo.getPropIns().get("elif") != null) {
                    nodoaux = (NodeIns) nodo.getPropIns().get("elif");
                    startTraduction(nodoaux);
                }

                break;

            case "if-elif-else":
                instruccion = "\n" + generateTabs();
                instruccion += "if ";
                instruccion += getExp((NodeExp)nodo.getPropIns().get("condicion")) + " :\n";
                addText(instruccion);
                ambito++;

                if(nodo.getInstrucciones() != null) {
                    ArrayList<NodeIns> instrucciones = (ArrayList)nodo.getInstrucciones();

                    for(NodeIns inst : instrucciones) {
                        startTraduction(inst);
                    }
                }

                ambito--;
                if(nodo.getPropIns().get("elif") != null) {
                    nodoaux = (NodeIns) nodo.getPropIns().get("elif");
                    startTraduction(nodoaux);
                }

                instruccion = "\n" + generateTabs();
                instruccion += "else:\n";
                nodoaux = (NodeIns) nodo.getPropIns().get("else");
                addText(instruccion);
                ambito++;

                if(nodo.getInstrucciones() != null) {
                    ArrayList<NodeIns> instrucciones = (ArrayList)nodo.getInstrucciones();

                    for(NodeIns inst : instrucciones) {
                        startTraduction(inst);
                    }
                }

                ambito--;
                break;

            case "elif":
                instruccion = "\n" + generateTabs();
                instruccion += "elif ";
                instruccion += getExp((NodeExp)nodo.getPropIns().get("condicion")) + " :\n";
                addText(instruccion);
                ambito++;

                if(nodo.getInstrucciones() != null) {
                    ArrayList<NodeIns> instrucciones = (ArrayList)nodo.getInstrucciones();

                    for(NodeIns inst : instrucciones) {
                        startTraduction(inst);
                    }
                }

                ambito--;
                if(nodo.getPropIns().get("elif") != null) {
                    nodoaux = (NodeIns) nodo.getPropIns().get("elif");
                    startTraduction(nodoaux);
                }

                break;

            case "switch":
                instruccion = "";
                String var = getExp((NodeExp) nodo.getPropIns().get("variable"));
                ArrayList<NodeIns> casos = (ArrayList)nodo.getPropIns().get("casos");
                for(NodeIns caso : casos) {
                    if(instruccion == "") {
                        instruccion = "\n" + generateTabs();
                        instruccion += "if " + var + " == " + getExp((NodeExp)caso.getPropIns().get("expresion")) + ":\n";
                    } else {
                        if(caso.getInstruccion().equals("case")) {
                            instruccion += "elif " + var + " == " + getExp((NodeExp)caso.getPropIns().get("expresion")) + ":\n";
                        } else {
                            instruccion += "else:\n";
                        }
                    }

                    addText(instruccion);

                    if(nodo.getInstrucciones() != null) {
                        ArrayList<NodeIns> instrucciones = (ArrayList)nodo.getInstrucciones();
                        ambito++;

                        for(NodeIns inst : instrucciones) {
                            startTraduction(inst);
                        }

                        ambito--;
                    }

                    instruccion = "\n" + generateTabs();
                }

                break;

            case "for":
                instruccion = "\n" + generateTabs();
                instruccion += "for ";
                instruccion += getExp((NodeExp)nodo.getPropIns().get("variable")) + " ";
                instruccion += " in range( ";
                instruccion += getExp((NodeExp)nodo.getPropIns().get("inicio")) + ",";
                instruccion += getExp((NodeExp)nodo.getPropIns().get("fin"));

                if(nodo.getPropIns().get("incremento") != null) {
                    instruccion += " ," + getExp((NodeExp)nodo.getPropIns().get("incremento"));
                }

                instruccion += "):\n";
                addText(instruccion);
                ambito++;

                if(nodo.getInstrucciones() != null) {
                    ArrayList<NodeIns> instrucciones = (ArrayList)nodo.getInstrucciones();

                    for(NodeIns inst : instrucciones) {
                        startTraduction(inst);
                    }
                }

                ambito--;
                break;

            case "while": // while contador < 10:
                instruccion = "\n" + generateTabs();
                instruccion += "while ";
                instruccion += getExp((NodeExp)nodo.getPropIns().get("condicion")) + ":\n";
                addText(instruccion);
                ambito++;

                if(nodo.getInstrucciones() != null) {
                    ArrayList<NodeIns> instrucciones = (ArrayList)nodo.getInstrucciones();

                    for(NodeIns inst : instrucciones) {
                        startTraduction(inst);
                    }
                }
                
                ambito--;
                break;

            case "doWhile":
                instruccion = "\n" + generateTabs();
                instruccion = "while True:\n";
                addText(instruccion);
                ambito++;

                if(nodo.getInstrucciones() != null) {
                    ArrayList<NodeIns> instrucciones = (ArrayList)nodo.getInstrucciones();

                    for(NodeIns inst : instrucciones) {
                        startTraduction(inst);
                    }
                }

                ambito--;
                instruccion = "\n" + generateTabs();
                instruccion += "if " + getExp((NodeExp)nodo.getPropIns().get("condicion")) + ":\n";
                instruccion += "\n" + generateTabs() + "\tbreak\n";
                addText(instruccion);
                break;

            case "declaracion_metodo":
                changeflag();
                instruccion = "def ";
                instruccion += nodo.getPropIns().get("id") + " (";
                ambito = 1;

                if(nodo.getPropIns().get("parametros") != null) {
                    ArrayList<String> params = (ArrayList)nodo.getPropIns().get("parametros");

                    for(int i = 0; i < params.size(); i++) {
                        String[] parts = params.get(i).split("-");
                        instruccion += parts[0];

                        if((i + 1) < params.size()) {
                            instruccion += ", ";
                        }
                    }
                }

                instruccion += "):\n";
                addText(instruccion);
                ambito++;

                if(nodo.getInstrucciones() != null) {
                    ArrayList<NodeIns> instrucciones = (ArrayList)nodo.getInstrucciones();
                    for(NodeIns inst : instrucciones) {
                        startTraduction(inst);
                    }
                }

                ambito--;
                changeflag();
                methods.add(contentMethod);
                contentMethod = new StringBuilder();
                break;

            case "declaracion_funcion":
                changeflag();
                instruccion = "def ";
                instruccion += nodo.getPropIns().get("id") + " (";
                ambito = 1;
                
                if(nodo.getPropIns().get("parametros") != null) {
                    ArrayList<String> params = (ArrayList)nodo.getPropIns().get("parametros");

                    for(int i = 0; i < params.size(); i++) {
                        String[] parts = params.get(i).split("-");
                        instruccion += parts[0];

                        if((i + 1) < params.size()) {
                            instruccion += ", ";
                        }
                    }
                }

                instruccion += "):\n";
                ambito++;

                if(nodo.getInstrucciones() != null) {
                    ArrayList<NodeIns> instrucciones = (ArrayList)nodo.getInstrucciones();
                    for(NodeIns inst : instrucciones) {
                        startTraduction(inst);
                    }
                }

                ambito--;
                changeflag();
                methods.add(contentMethod);
                contentMethod = new StringBuilder();
                break;

            case "ejecutar":
                instruccion = "\n" + generateTabs();
                instruccion += (String)nodo.getPropIns().get("id") + "(";
                
                if(nodo.getPropIns().get("parametros") != null) {
                    ArrayList<NodeExp> params = (ArrayList)nodo.getPropIns().get("parametros");

                    for(int i = 0; i < params.size(); i++) {
                        instruccion += getExp(params.get(i));
                        if((i + 1) < params.size()) {
                            instruccion += ", ";
                        }
                    }
                }

                instruccion += ")";
                addText(instruccion);
                break;

            case "imprimir":
                instruccion = "\n" + generateTabs();
                instruccion += "print(" + getExp((NodeExp)nodo.getPropIns().get("expresion")) + ")";
                addText(instruccion);
                break;

            case "imprimir_nl":
                instruccion = "\n" + generateTabs();
                instruccion += "print(" + getExp((NodeExp)nodo.getPropIns().get("expresion")) + ")";
                addText(instruccion);
                break;

            case "return":
                instruccion = "\n" + generateTabs();
                instruccion += "return ";
                instruccion += getExp((NodeExp)nodo.getPropIns().get("expresion"));
                addText(instruccion);
                break;
        }
    }

    public String getExp(NodeExp nodo) {
        switch (nodo.getOperacion()) {
            case "M":
                return "-" + getExp(nodo.getOp1());

            case "+":
                return getExp(nodo.getOp1()) + " + " + getExp(nodo.getOp2());

            case "-":
                return getExp(nodo.getOp1()) + " - " + getExp(nodo.getOp2());

            case "*":
                return getExp(nodo.getOp1()) + " * " + getExp(nodo.getOp2());

            case "/":
                return getExp(nodo.getOp1()) + " / " + getExp(nodo.getOp2());

            case "^":
                return getExp(nodo.getOp1()) + " ** " + getExp(nodo.getOp2());

            case "%":
                return getExp(nodo.getOp1()) + " % " + getExp(nodo.getOp2());

            case "()":
                return "(" + getExp(nodo.getOp1()) + ")";

            case "==":
                return getExp(nodo.getOp1()) + " == " + getExp(nodo.getOp2());

            case "!=":
                return getExp(nodo.getOp1()) + " != " + getExp(nodo.getOp2());

            case ">":
                return getExp(nodo.getOp1()) + " > " + getExp(nodo.getOp2());
            
            case "<":
                return getExp(nodo.getOp1()) + " < " + getExp(nodo.getOp2());

            case ">=":
                return getExp(nodo.getOp1()) + " >= " + getExp(nodo.getOp2());

            case "<=":
                return getExp(nodo.getOp1()) + " <= " + getExp(nodo.getOp2());

            case "||":
                return getExp(nodo.getOp1()) + " or " + getExp(nodo.getOp2());

            case "&&":
                return getExp(nodo.getOp1()) + " and " + getExp(nodo.getOp2());

            case "!":
                return "not (" + getExp(nodo.getOp1()) + ")";
        
            default:
                if(nodo.getOperacion().contains("ejecutar")){
                    String[] detalle = nodo.getOperacion().split("\\|");
                    String expresion = detalle[1] + "(";

                    if(nodo.getParametros() != null) {
                        ArrayList<NodeExp> params = nodo.getParametros();

                        for(int i = 0; i < params.size(); i++) {
                            expresion += getExp(params.get(i));

                            if((i + 1) < params.size()) {
                                expresion += ", ";
                            }
                        }
                    }

                    expresion += ")";
                    return expresion;
                }

                return nodo.getOperacion();
        }
    }
}
