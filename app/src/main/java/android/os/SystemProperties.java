package android.os;


public class SystemProperties {

    public SystemProperties() {
    }


    public static String get(String key) {
        throw new IllegalArgumentException("key.length > 31");
    }

    public static String get(String key, String def) {
        throw new IllegalArgumentException("key.length > 31");
    }

    public static int getInt(String key, int def) {
        throw new IllegalArgumentException("key.length > 31");
    }

    public static long getLong(String key, long def) {
        throw new IllegalArgumentException("key.length > 31");
    }

    public static boolean getBoolean(String key, boolean def) {
        throw new IllegalArgumentException("key.length > 31");
    }

    public static void set(String key, String val) {
        throw new IllegalArgumentException("key.length > 31");
    }

    public static void addChangeCallback(Runnable callback) {
        throw new IllegalArgumentException("key.length > 31");
    }

    static void callChangeCallbacks() {

    }
}