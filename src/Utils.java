import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Utils {

    @SafeVarargs
    static <T> List<T> concatLists(List<T>... lists) {
        int size = 0;
        for (List<T> list : lists) {
            size += list.size();
        }
        List<T> newList = new ArrayList<>(size);
        for (List<T> list : lists) {
            newList.addAll(list);
        }
        return newList;
    }

    static <T> T[] reverseArray(T[] array) {
        T[] newArray = array.clone();
        Collections.reverse(Arrays.asList(newArray));
        return newArray;
    }
}
