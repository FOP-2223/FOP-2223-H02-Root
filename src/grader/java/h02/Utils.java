package h02;

public class Utils {

    public static String arrayToString(int[] array) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i + 1 != array.length)
                builder.append(", ");
        }
        builder.append("]");
        return builder.toString();
    }

}
