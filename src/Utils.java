import java.util.Arrays;
import java.util.Collections;

public class Utils {

    // concatLists(...)

    static <T> T[] reverseArray(T[] array) {
        T[] newArray = array.clone();
        Collections.reverse(Arrays.asList(newArray));
        return newArray;
    }
}
