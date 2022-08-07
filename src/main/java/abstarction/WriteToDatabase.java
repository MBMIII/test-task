package abstarction;

import java.util.List;

public abstract class WriteToDatabase implements Write {

    public abstract void clearTable(String tableName);

    public abstract List<List<String>> getAll();
}
