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

    private String var = "";
    private int ambito = 1;
    private int ambitoAux = 1;
    private boolean flagMain = true;
    private boolean flagMethod = false;
    private boolean flagSwitch = false;
    StringBuilder contentMain = new StringBuilder();
    StringBuilder contentMethod = new StringBuilder();
    ArrayList<StringBuilder> methods = new ArrayList<>();

    private void addText(String instruccion) {
        if(flagMain) {
            contentMain.append(instruccion);
        } else {
            contentMethod.append(instruccion);
        }
    }

    public void changeflag() {
        if(flagMethod) {
            methods.add(contentMethod);
            contentMethod = new StringBuilder();
            ambito = ambitoAux;
        } else {
            ambitoAux = ambito;
            ambito = 1;
        }

        this.flagMain = !flagMain;
        this.flagMethod = !flagMethod;
    }

    public void restAmbit() {
        ambito--;
    }

    private String generateTabs(){
        String tabs = "";

        for(int i = 0; i < ambito; i++) {
            tabs += "\t";
        }

        return tabs;
    }

    public String printTraduction() {
        File directorio = new File("/home/jorge/Escritorio/COMPI/OLC1-201809938/Proyecto_1/src/Dist/Python");
        File[] files = directorio.listFiles();

        if(files.length > 0) {
            for(File file : files) {
                if(!file.getName().equals("README.md")){
                    file.delete();
                }
            }
        }

        try{
            File docPython = new File("src/Dist/Python/out.py");
            if(docPython.createNewFile()) {
                FileWriter fw = new FileWriter(docPython);
                BufferedWriter bw = new BufferedWriter(fw);
                
                for(StringBuilder method : methods) {
                    bw.write(method.toString());
                    bw.write("\n");
                }

                bw.write("\n");
                bw.write("def main():\n");
                bw.write(contentMain.toString());
                bw.write("\n\n");
                bw.write("if __name__ == \"__main__\":\n\tmain()");
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
                for(String id : ids) {
                    if(instruccion != "") {
                        instruccion += ", ";
                    } else {
                        instruccion += generateTabs();
                    }

                    instruccion += id;
                }

                if(nodo.getPropIns().get("valor") != null) {
                    instruccion += " = ";

                    for(int i = 0; i < ids.size(); i++) {
                        instruccion += getExp((NodeExp)nodo.getPropIns().get("valor"));

                        if((i + 1) < ids.size()) {
                            instruccion += ", ";
                        }
                    }
                }
                
                instruccion += "\n";
                addText(instruccion);
                break;

            case "asignacion":
                ids = (ArrayList)nodo.getPropIns().get("identificadores");
                for(String id : ids) {
                    if(instruccion != "") {
                        instruccion += ", ";
                    } else {
                        instruccion += generateTabs();
                    }

                    instruccion += id;
                }

                if(nodo.getPropIns().get("valor") != null) {
                    instruccion += " = ";

                    for(int i = 0; i < ids.size(); i++) {
                        instruccion += getExp((NodeExp)nodo.getPropIns().get("valor"));

                        if((i + 1) < ids.size()) {
                            instruccion += ", ";
                        }
                    }
                }
                
                instruccion += "\n";
                addText(instruccion);
                break;
            
            case "if-simple":
                instruccion = "\n" + generateTabs();
                instruccion += "if " + getExp((NodeExp)nodo.getPropIns().get("condicion")) + " :\n";
                addText(instruccion);
                ambito++;
                break;

            case "if-else":
                instruccion = "\n" + generateTabs();
                instruccion += "if " + getExp((NodeExp)nodo.getPropIns().get("condicion")) + " :\n";
                addText(instruccion);
                ambito++;
                break;

            case "else":
                instruccion = generateTabs();
                instruccion += "else:\n";
                addText(instruccion);
                ambito++;
                break;

            case "if-elif":
                instruccion = "\n" + generateTabs();
                instruccion += "if " + getExp((NodeExp)nodo.getPropIns().get("condicion")) + " :\n";
                addText(instruccion);
                ambito++;
                break;

            case "if-elif-else":
                instruccion = "\n" + generateTabs();
                instruccion += "if " + getExp((NodeExp)nodo.getPropIns().get("condicion")) + " :\n";
                addText(instruccion);
                ambito++;
                break;

            case "elif":
                instruccion = generateTabs();
                instruccion += "elif " + getExp((NodeExp)nodo.getPropIns().get("condicion")) + " :\n";
                addText(instruccion);
                ambito++;
                break;

            case "switch":
                flagSwitch = false;
                instruccion = "";
                var = getExp((NodeExp) nodo.getPropIns().get("variable"));
                break;

            case "case":
                if(instruccion == "" && !flagSwitch) {
                    instruccion = "\n" + generateTabs();
                    instruccion += "if " + var + " == " + getExp((NodeExp)nodo.getPropIns().get("expresion")) + ":\n";
                    ambito++;
                    flagSwitch = !flagSwitch;
                } else {
                    if(nodo.getInstruccion().equals("case")) {
                        instruccion = generateTabs();
                        instruccion += "elif " + var + " == " + getExp((NodeExp)nodo.getPropIns().get("expresion")) + ":\n";
                        ambito++;
                    } else {
                        instruccion = generateTabs();
                        instruccion += "else:\n";
                        ambito++;
                    }
                }

                addText(instruccion);
                break;

            case "for":
                instruccion = "\n" + generateTabs();
                instruccion += "for " + getExp((NodeExp)nodo.getPropIns().get("variable")) + " ";
                instruccion += "in range(" + getExp((NodeExp)nodo.getPropIns().get("inicio")) + ",";
                instruccion += getExp((NodeExp)nodo.getPropIns().get("fin"));

                if(nodo.getPropIns().get("incremento") != null) {
                    instruccion += " ," + getExp((NodeExp)nodo.getPropIns().get("incremento"));
                }

                instruccion += "):\n";
                addText(instruccion);
                ambito++;
                break;

            case "while": // while contador < 10:
                instruccion = "\n" + generateTabs();
                instruccion += "while " + getExp((NodeExp)nodo.getPropIns().get("condicion")) + ":\n";
                addText(instruccion);
                ambito++;
                break;

            case "doWhile":
                instruccion = "\n" + generateTabs();
                instruccion += "while True:\n";
                addText(instruccion);
                ambito++;
                break;

            case "doWhileEnd":
                instruccion = "\n" + generateTabs();
                instruccion += "if " + getExp((NodeExp)nodo.getPropIns().get("condicion")) + ":\n";
                ambito++;
                instruccion += generateTabs() + "break\n";
                ambito--;

            case "declaracion_metodo":
                changeflag();
                instruccion = "def ";
                instruccion += nodo.getPropIns().get("id") + " (";

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
                addText(instruccion);
                break;

            case "ejecutar":
                instruccion = generateTabs();
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

                instruccion += ")\n";
                addText(instruccion);
                break;

            case "imprimir":
                instruccion = generateTabs();
                instruccion += "print(" + getExp((NodeExp)nodo.getPropIns().get("expresion")) + ")\n";
                addText(instruccion);
                break;

            case "imprimir_nl":
                instruccion = generateTabs();
                instruccion += "print(" + getExp((NodeExp)nodo.getPropIns().get("expresion")) + ")\n";
                addText(instruccion);
                break;

            case "return":
                instruccion = generateTabs();
                instruccion += "return " + getExp((NodeExp)nodo.getPropIns().get("expresion")) + "\n";
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
                
                if(nodo.getOperacion().equals("falso")) {
                    return "False";
                } else if(nodo.getOperacion().equals("verdadero")) {
                    return "True";
                } 

                return nodo.getOperacion();
        }
    }
}
