// IGNORE_K2
// !WITHOUT_SLOW_ASSERTIONS: true
import java.util.*;

class A {
    public static Map<String, String> foo() {
        Properties props = new Properties();
        return new HashMap<>((Map)props);
    }
}