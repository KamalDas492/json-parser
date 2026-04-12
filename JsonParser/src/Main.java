package src;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {
    public static void parse(String file_path) throws IOException {
        String json = Files.readString(Path.of(file_path));
//        try {
//            boolean result = src.Parser.parseValue(json);
//            if(result) {
//                System.out.println("Valid JSON");
//            }
//        } catch (src.InvalidJSONException e) {
//            System.out.println("Invalid JSON : " + e.getMessage());
//        }
    }
    public static void main(String[] args) throws IOException {
        Path startPath = Paths.get(args[0]);
        try(Stream<Path> paths = Files.walk(startPath)) {
            paths.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".json"))
                    .forEach(p -> {
                        try {
                             parse(p.toAbsolutePath().toString());
                        } catch (IOException e) {
                            System.out.println("Something Went Wrong!" + e.getMessage());
                        }
                    });
        }

    }
}