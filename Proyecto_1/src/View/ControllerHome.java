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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

public class ControllerHome implements Initializable {

    private String pathfile;

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
            bw.write(textArea.getText());
            bw.close();
            fw.close();
            System.out.println("El file Se Guardo Correctamente");
        } catch (IOException ioex) {
            System.err.println("El file No Se Pudo Guardar");
        }
    }

    @FXML 
    private void showErrors(ActionEvent e) {
        System.out.println("Mostrar Errores");
    }

    @FXML
    private void showDiagram(ActionEvent e) {
        System.out.println("Mostar Diagrama De Flujo");
    }

    @FXML
    private void run(ActionEvent e) {
        System.out.println("Correr Programa");
        ListError.getInstance().clearList();
        if(!"".equals(textArea.getText())) {
            
            try{
                Sintactic s = new Sintactic(new Lex(new StringReader(textArea.getText())));
                s.parse();

                if(!ListError.getInstance().isEmpty()) {
                    ArrayList<Error> errors = ListError.getInstance().getListError();
                    countErrors.setText(errors.size() + "Errores");

                    for(int i = 0; i < errors.size(); i++) {
                        System.out.println(errors.get(i).getMsg());
                    }

                } else {
                    countErrors.setText("0 Errores");
                }
            } catch(Exception err) {
                System.out.println(err);
            }
        }
    }

    @FXML
    private void clear(ActionEvent e) {
        System.out.println("Limpiando Area De Texto");
        pathfile = "";
    }

    @FXML
    private void selectIn(ActionEvent e) {
        System.out.println("Mostar Contenido De Entrada");
    }

    @FXML
    private void selectPython(ActionEvent e) {
        System.out.println("Mostrar Contenido De Python");
    }

    @FXML
    private void selectGolang(ActionEvent e) {
        System.out.println("Mostrar Contenido De Golang");
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        pathfile = "";
    }
}
