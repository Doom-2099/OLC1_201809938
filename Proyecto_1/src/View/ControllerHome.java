package View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Context.Lex;
import Context.Sintactic;
import Error.Error;
import Error.ListError;
import Model.Generator.AST;
import Model.Generator.GenerateGo;
import Model.Generator.GeneratePy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

public class ControllerHome implements Initializable {

    private String pathfile;

    private String codeIn;
    private String codeOutPython;
    private String codeOutGolang;
    private String erroresTxt;

    private boolean flagCodeIn = false;
    private boolean flagCodeOutPython = false;
    private boolean flagCodeOutGolang = false;
    private boolean flagErrors = false;

    @FXML
    private Label countErrors;

    @FXML
    private TextArea textArea;

    @FXML
    private void openFile(ActionEvent e) {
        FileChooser fChooser = new FileChooser();
        fChooser.setTitle("Abrir file");

        File file = null;

        try {
            file = fChooser.showOpenDialog(ControllerWindow.getInstance().getWindow());
            if (file != null) {
                pathfile = file.getAbsolutePath();
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line;

                while ((line = br.readLine()) != null) {
                    textArea.appendText(line + '\n');
                }

                br.close();
                fr.close();
                System.out.println("El file Se Ha Cargado Correctamente");
                setFlags(1);
            }

        } catch (NullPointerException ex) {
            System.err.println("No Se Ha Seleccionado Ningun file");
        } catch (FileNotFoundException fnfex) {
            System.err.println("No Se Ha Encontrado El file");
        } catch (IOException ioex) {
            System.err.println("Error En La Lectura Del Archivi");
        }
    }

    @FXML
    private void saveFile(ActionEvent e) {
        try {
            File file = new File(pathfile);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(codeIn);
            bw.close();
            fw.close();
            System.out.println("El file Se Guardo Correctamente");
            setFlags(5);
        } catch (IOException ioex) {
            System.err.println("El file No Se Pudo Guardar");
        }
    }
 
    @FXML
    private void showDiagram(ActionEvent e) {
        System.out.println("Mostar Diagrama De Flujo");
    }

    @FXML
    private void run(ActionEvent e){
        System.out.println("Correr Programa");
        ListError.getInstance().clearList();
        if(!"".equals(textArea.getText())) {
            
            try{
                Sintactic s = new Sintactic(new Lex(new StringReader(textArea.getText())));
                s.parse();
                
                if(!ListError.getInstance().isEmpty()) {
                    ArrayList<Error> errors = ListError.getInstance().getListError();
                    countErrors.setText(errors.size() + " Errores");

                    File file = new File(pathfile);
                    FileReader fr = new FileReader(file);
                    BufferedReader br = new BufferedReader(fr);
                    String linea = "";
                    int contador = 1;
                    int index = 0;

                    while((linea = br.readLine()) != null) {
                        if(index < errors.size()){
                            if(contador == errors.get(index).getLine()) {
                                while (true) {
                                    if(index >= errors.size()) {
                                        break;
                                    } else if(errors.get(index).getLine() != contador) {
                                        break;
                                    } else {
                                        int aux = 0;
                                        for(int x = 0; x < linea.length(); x++) {
                                            if(linea.charAt(x) == '\t') {
                                                aux++;
                                            }
                                        }

                                        erroresTxt += "\n-------------------------------\n";
                                        erroresTxt += linea + "\n";
                                        erroresTxt += getTabs(aux) + getSpaces(errors.get(index).getColumn()) + "^\n";
                                        erroresTxt += getTabs(aux) + getSpaces(errors.get(index).getColumn()) + "|\n";
                                        erroresTxt += getTabs(aux) + getSpaces(errors.get(index).getColumn()) + errors.get(index).getType() + "\n";
                                        erroresTxt += errors.get(index).getMsg().replace("\n", " ") + "\n";
                                        erroresTxt += "\n-------------------------------\n";
                                        index++;
                                    }
                                }
                            } else {
                                erroresTxt += linea + "\n";
                            }
                        } else {
                            erroresTxt += linea + "\n";
                        }
                        contador++;
                    }

                    br.close();
                    fr.close();
                    generateErrorFile();
                    String message = "Error En La Traduccion";
                    message += "\nEl Archivo De Entrada Tiene Errores";
                    message += "\nEl Archivo Con El Reporte Se Ha Generado";
                    message += "\nVer Carpeta /src/Error/Log";
                    alertError(message, "Error", "Error");

                } else {
                    countErrors.setText("0 Errores");
                    String message = AST.getInstance().generate_AST_Img();
                    message += "\n" + GeneratePy.getInstance().printTraduction();
                    message += "\n" + GenerateGo.getInstance().printTraduction();
                    if(!message.contains("AST")) {
                        alertError(message, "Error AST", "Error");
                    } else {
                        alertInformation(message, "Generacion AST", "Informacion");
                        File file = new File("/home/jorge/Escritorio/COMPI/OLC1-201809938/Proyecto_1/src/Dist/Python/out.py");
                        FileReader fr = new FileReader(file);
                        BufferedReader br = new BufferedReader(fr);
                        String linea = "";
                        while((linea = br.readLine()) != null) {
                            codeOutPython += linea + "\n";
                        }

                        br.close();
                        fr.close();

                        file = new File("/home/jorge/Escritorio/COMPI/OLC1-201809938/Proyecto_1/src/Dist/Golang/out.go");
                        fr = new FileReader(file);
                        br = new BufferedReader(fr);
                        linea = "";
                        while((linea = br.readLine()) != null) {
                            codeOutGolang += linea + "\n";
                        }

                        br.close();
                        fr.close();
                    }
                }
            } catch(Exception err) {
                System.out.println(err);
                StackTraceElement[] s = err.getStackTrace();
                System.out.println("---------------------------------");
                System.out.println(s[0].getFileName());
                System.out.println(s[0].getMethodName());
                System.out.println(s[0].getLineNumber());
                System.out.println("---------------------------------");
            }
        }
    }

    @FXML
    private void clear(ActionEvent e) {
        setFlags(5);
        codeIn = "";
        codeOutPython = "";
        codeOutGolang = "";
        erroresTxt = "";
        System.out.println("Limpiando Area De Texto");
        pathfile = "";
        textArea.clear();
    }

    @FXML
    private void selectIn(ActionEvent e) {
        saveText();
        textArea.setText(codeIn);
        setFlags(1);
    }

    @FXML
    private void selectPython(ActionEvent e) {
        saveText();
        textArea.setText(codeOutPython);
        setFlags(2);
    }

    @FXML
    private void selectGolang(ActionEvent e) {
        saveText();
        textArea.setText(codeOutGolang);
        setFlags(3);
    }

    @FXML
    private void selectError(ActionEvent e) {
        saveText();
        textArea.setText(erroresTxt);
        setFlags(4);
    }

    @FXML
    private void alertError(String message, String title, String header) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML void alertInformation(String message, String title, String header) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setFlags(int valor) {
        if(valor == 1) {
            flagCodeIn = true;
            flagCodeOutPython = false;
            flagCodeOutGolang = false;
            flagErrors = false;
        } else if(valor == 2) {
            flagCodeIn = false;
            flagCodeOutPython = true;
            flagCodeOutGolang = false;
            flagErrors = false;
        } else if(valor == 3) {
            flagCodeIn = false;
            flagCodeOutPython = false;
            flagCodeOutGolang = true;
            flagErrors = false;
        } else if(valor == 4) {
            flagCodeIn = false;
            flagCodeOutPython = false;
            flagCodeOutGolang = false;
            flagErrors = true;
        } else {
            flagCodeIn = false;
            flagCodeOutPython = false;
            flagCodeOutGolang = false;
            flagErrors = false;
        }
    }

    private void saveText() {
        if(flagCodeIn) {
            codeIn = textArea.getText();
        } else if(flagCodeOutGolang) {
            codeOutGolang = textArea.getText();
        } else if(flagCodeOutPython){
            codeOutPython = textArea.getText();
        } else if(flagErrors) {
            erroresTxt = textArea.getText();
        }
    }

    private String getSpaces(int spaces) {
        String space = "";
        for(int i = 0; i < (spaces - 3); i++) {
            space += " ";
        }
        return space;
    }

    private String getTabs(int tabsNum) {
        String tabs = "";
        for(int i = 0; i < tabsNum; i++) {
            tabs += "\t";
        }

        return tabs;
    }

    private void generateErrorFile() {
        File directorio = new File("/home/jorge/Escritorio/COMPI/OLC1-201809938/Proyecto_1/src/Error/Log/");
        File[] files = directorio.listFiles();

        if(files.length > 0) {
            for(File file : files) {
                file.delete();
            }
        }

        try {
            File docError = new File("src/Error/Log/out.txt");
            if(docError.createNewFile()) {
                FileWriter fw = new FileWriter(docError);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(erroresTxt);

                bw.close();
                fw.close();
            }
        } catch (Exception err) {
            System.out.println(err);
            StackTraceElement[] s = err.getStackTrace();
            System.out.println("---------------------------------");
            System.out.println(s[0].getFileName());
            System.out.println(s[0].getMethodName());
            System.out.println(s[0].getLineNumber());
            System.out.println("---------------------------------");
        }
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        pathfile = "";
    }
}
