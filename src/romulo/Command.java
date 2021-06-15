package romulo;

import java.io.*;
import java.util.Scanner;

public class Command {

    private static void inheritIO(final InputStream src, final PrintStream dest) {
        new Thread(() -> {
            Scanner sc = new Scanner(src);
            while (sc.hasNextLine()) {
                dest.println(sc.nextLine());
            }
        }).start();
    }

    public static void Run(String command) {
        try {
            Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});
            System.out.println(command + " executed");
            inheritIO(p.getInputStream(), System.out);
            inheritIO(p.getErrorStream(), System.err);
            p.waitFor();
        } catch (Exception ignored) {
        }
    }
}
