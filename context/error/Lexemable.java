package context.error;

/**
 * Created by Tristan Newmann on 10/17/2015.
 */
public interface Lexemable {

    public int getLineIndexInFile();
    public int getStartLineIndex();
    public int getEndLineIndex();
    public String getLexemeVal();

}
