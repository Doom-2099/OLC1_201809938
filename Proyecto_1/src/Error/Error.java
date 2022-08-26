package Error;

public class Error {
    private int column;    
    private int line;
    private String lexem;
    private String msg;
    private String type;

    public Error(int column, int line, String lexem, String msg, String type) {
        this.column = column;
        this.line = line;
        this.lexem = lexem;
        this.msg = msg;
        this.type = type;
    }

    public int getColumn() {
        return column;
    }

    public int getLine() {
        return line;
    }

    public String getLexem() {
        return lexem;
    }

    public String getMsg() {
        return msg;
    }

    public String getType() {
        return type;
    }
}
