package Error;

import java.util.ArrayList;

public class ListError {
    private final ArrayList<Error> listError = new ArrayList<>();

    private static final ListError instance = new ListError();

    public static ListError getInstance() {
        return instance;
    }

    public ArrayList<Error> getListError() {
        return listError;
    }

    public void addError(Error e) {
        listError.add(e);
    }

    public boolean isEmpty() {
        return listError.isEmpty();
    }

    public void clearList() {
        listError.clear();
    }
}
