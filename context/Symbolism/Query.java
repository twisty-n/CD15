package context.symbolism;
import java.util.Map;
/**
 * Created by Tristan Newmann on 10/17/2015.
 */
public abstract class Query {

    public abstract Map<String, STRecord> query(SymbolTable st);

}
