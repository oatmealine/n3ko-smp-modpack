package zone.oat.n3komod.util;

public class Util {
    public static boolean isOK(int statusCode) {
        return Integer.toString(statusCode).startsWith("2");
    }
}
