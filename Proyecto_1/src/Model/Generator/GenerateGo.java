package Model.Generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Model.Components.Expressions.NodeExp;
import Model.Components.Instruccions.NodeIns;

public class GenerateGo {
    // Escribir Traduccion A Golang
    private final static GenerateGo instance = new GenerateGo();

    public static GenerateGo getInstance() {
        return instance;
    }

    private int ambito = 1;
    private int ambitoAux = 1;
    private boolean flagMain = true;
    private boolean flagMethod = false;
    private boolean flagCase = false;
    StringBuilder contentMain = new StringBuilder();
    StringBuilder contentMethod = new StringBuilder();
    ArrayList<StringBuilder> methods = new ArrayList<>();
    ArrayList<String> imports = new ArrayList<>();

    private String getTipoDato(String tipoDato) {
        switch (tipoDato = tipoDato.toLowerCase()) {
            case "numero":
                return "float64";
            
            case "caracter":
                return "byte";

            case "cadena":
                return "string";

            case "boolean":
                return "bool";
        }
        return null;
    }

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

    public void changeflagCase() {
        flagCase = !flagCase;
    }

    public void restAmbit() {
        ambito--;

        if(flagMethod && !flagCase) {
            String instruccion = generateTabs();
            instruccion += "}\n";
            contentMethod.append(instruccion);
            flagCase = false;
        } else if(!flagCase) {
            String instruccion = generateTabs();
            instruccion += "}\n";
            contentMain.append(instruccion);
            flagCase = false;
        }
    }

    private String generateTabs() {
        String tabs = "";

        for(int i = 0; i < ambito; i++) {
            tabs += "\t";
        }

        return tabs;
    }

    public String printTraduction() {
        File directorio = new File("/home/jorge/Escritorio/COMPI/OLC1-201809938/Proyecto_1/src/Dist/Golang");
        File[] files = directorio.listFiles();

        if(files.length > 0) {
            for(File file : files) {
                if(!file.getName().equals("README.md")){
                    file.delete();
                }
            }
        }

        try {
            File docGolang = new File("src/Dist/Golang/out.go");
            if(docGolang.createNewFile()) {
                FileWriter fw = new FileWriter(docGolang);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write("package main\n\n");

                if(imports.size() == 1) {
                    bw.write("import \"" + imports.get(0) + "\"");
                } else {
                    bw.write("import( \n");

                    for(String importCode:imports) {
                        bw.write("\t\"" + importCode + "\",\n");
                    }

                    bw.write(")\n");
                }

                bw.write("\n\n");
                bw.write("func main() {\n");
                bw.write(contentMain.toString());
                bw.write("\n}\n\n");

                for(StringBuilder method : methods) {
                    bw.write(method.toString());
                }

                bw.close();
                fw.close();
                return "Documento Golang Generado";
            }
        } catch (IOException err) {
            return err.getLocalizedMessage();
        }

        return "Documento Golang Generado";
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
                        instruccion = generateTabs() + "var ";
                    }

                    instruccion += id;
                }

                instruccion += " " + getTipoDato((String)nodo.getPropIns().get("tipoDato")) + " = ";

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
                ids = (ArrayList)nodo.getPropIns().get("identificadores");

                for(String id : ids) {
                    instruccion += generateTabs() + "var " + id + " = " + getExp((NodeExp)nodo.getPropIns().get("valor")) + "\n";
                }

                addText(instruccion);
                break;
            
            case "if-simple":
                instruccion = generateTabs() + "if " + getExp((NodeExp)nodo.getPropIns().get("condicion")) + " {\n";
                addText(instruccion);
                ambito++;
                break;

            case "if-else":
                instruccion = generateTabs();
                instruccion += "if " + getExp((NodeExp)nodo.getPropIns().get("condicion")) + " {\n";
                addText(instruccion);
                ambito++;
                break;

            case "if-elif":
                instruccion = generateTabs();
                instruccion += "if " + getExp((NodeExp)nodo.getPropIns().get("condicion")) + " {\n";
                addText(instruccion);
                ambito++;
                break;

            case "else":
                instruccion = generateTabs();
                instruccion += "} else {\n";
                addText(instruccion);
                ambito++;
                break;

            case "if-elif-else":
                instruccion = generateTabs();
                instruccion += "if " + getExp((NodeExp)nodo.getPropIns().get("condicion")) + " {\n";
                addText(instruccion);
                ambito++;
                break;

            case "elif":
                instruccion = generateTabs();
                instruccion += "} else " + getExp((NodeExp)nodo.getPropIns().get("condicion")) + " {\n";
                addText(instruccion);
                ambito++;
                break;

            case "switch":
                instruccion = generateTabs();
                instruccion += "switch " + getExp((NodeExp)nodo.getPropIns().get("variable")) + " {\n";
                addText(instruccion);
                ambito++;
                break;

            case "case":
                flagCase = true;
                instruccion = generateTabs();
                instruccion += "case " + getExp((NodeExp)nodo.getPropIns().get("expresion")) + ":\n";
                addText(instruccion);
                ambito++;
                break;
            
            case "case_default":
                flagCase = true;
                instruccion = generateTabs();
                instruccion += "default:\n";
                addText(instruccion);
                ambito++;
                break;

            case "for":
                instruccion = generateTabs();
                instruccion += "for " + getExp((NodeExp)nodo.getPropIns().get("variable")) + ":=" + getExp((NodeExp)nodo.getPropIns().get("inicio")) + "; ";
                instruccion += getExp((NodeExp)nodo.getPropIns().get("variable")) + " < " + getExp((NodeExp)nodo.getPropIns().get("fin")) + "; ";

                if(nodo.getPropIns().get("incremento") != null) {
                    instruccion += getExp((NodeExp)nodo.getPropIns().get("variable")) + "+=" + getExp((NodeExp)nodo.getPropIns().get("incremento")) + "{\n";
                } else {
                    instruccion += getExp((NodeExp)nodo.getPropIns().get("variable")) + "++ {\n";
                }

                addText(instruccion);
                ambito++;
                break;

            case "while":
                instruccion = generateTabs();
                instruccion += "for true {\n";
                ambito++;
                instruccion += generateTabs();
                instruccion += "if !(" + getExp((NodeExp)nodo.getPropIns().get("condicion")) + ") {\n";
                ambito++;
                instruccion += generateTabs() + "break\n";  
                ambito--;
                instruccion += generateTabs() + "}\n";

                addText(instruccion);
                break;

            case "doWhile":
                instruccion = generateTabs();
                instruccion += "for true {\n";
                ambito++;
                instruccion += generateTabs() + "if (" + getExp((NodeExp)nodo.getPropIns().get("condicion")) + "){\n";
                ambito++;
                instruccion += generateTabs() + "break\n";
                ambito--;
                instruccion += generateTabs() + "}\n";

                addText(instruccion);
                break;

            case "declaracion_metodo":
                changeflag();
                instruccion = "\nfunc " + nodo.getPropIns().get("id") + "(";

                if(nodo.getPropIns().get("parametros") != null) {
                    ArrayList<String> params = (ArrayList)nodo.getPropIns().get("parametros");

                    for(int i = 0; i < params.size(); i++) {
                        String[] parts = params.get(i).split("-");
                        instruccion += parts[0] + " " + getTipoDato(parts[1]);

                        if((i + 1) < params.size()) {
                            instruccion += ", ";
                        }
                    }
                }

                instruccion += ") {\n";
                addText(instruccion);
                break;

            case "declaracion_funcion":
                changeflag();
                instruccion = "\nfunc " + nodo.getPropIns().get("id") + "(";

                if(nodo.getPropIns().get("parametros") != null) {
                    ArrayList<String> params = (ArrayList)nodo.getPropIns().get("parametros");

                    for(int i = 0; i < params.size(); i++) {
                        String[] parts = params.get(i).split("-");
                        instruccion += parts[0] + " " + getTipoDato(parts[1]);

                        if((i + 1) < params.size()) {
                            instruccion += ", ";
                        }
                    }
                }

                instruccion += ") " + getTipoDato((String)nodo.getPropIns().get("tipoRetorno")) + " {\n";
                addText(instruccion);
                break;

            case "ejecutar":
                instruccion = generateTabs();
                instruccion += (String)nodo.getPropIns().get("id") + "(";

                if(nodo.getPropIns().get("parametros") != null) {
                    ArrayList<NodeExp> params = (ArrayList)nodo.getPropIns().get("parametros");

                    for(int i = 0; i < params.size(); i++) {
                        instruccion += getExp((params.get(i)));
                        if((i + 1) < params.size()) {
                            instruccion += ", ";
                        }
                    }
                }

                instruccion += ")\n";
                addText(instruccion);
                break;

            case "imprimir":
                if(!imports.contains("fmt")) { 
                    imports.add("fmt"); 
                }
                instruccion = generateTabs();
                instruccion += "fmt.Print(" + getExp((NodeExp)nodo.getPropIns().get("expresion")) + ")\n";
                addText(instruccion);
                break;

            case "imprimir_nl":
                if(!imports.contains("fmt")) { 
                    imports.add("fmt"); 
                }
                instruccion = generateTabs();
                instruccion += "fmt.Println(" + getExp((NodeExp)nodo.getPropIns().get("expresion")) + ")\n";
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
                if(!imports.contains("math")) { 
                    imports.add("math");
                }
                return "math.Pow(float64(" + getExp(nodo.getOp1()) + "), float64(" + getExp(nodo.getOp2())  + "))";

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
                return getExp(nodo.getOp1()) + " || " + getExp(nodo.getOp2());

            case "&&":
                return getExp(nodo.getOp1()) + " && " + getExp(nodo.getOp2());

            case "!":
                return "!(" + getExp(nodo.getOp1()) + ")";
        
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
