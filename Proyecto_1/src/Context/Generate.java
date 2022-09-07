package Context;

public class Generate {
    public static void main(String[] args) throws Exception{
        String ruta = "src/Context/";
        String pflex[] = { ruta + "Lex.flex", "-d", ruta };
        String cup[] = { "-destdir", ruta, "-parser", "Sintactic", ruta + "Sintact.cup" };

        jflex.Main.generate(pflex);
        java_cup.Main.main(cup);
    }
}
