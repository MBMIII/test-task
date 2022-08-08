package abstarction;

import java.util.List;

public abstract class WriteToFile implements Write {
    public abstract List<String> getAll();
    public abstract void clear();
}
