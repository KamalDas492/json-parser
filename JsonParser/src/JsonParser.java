package src;



import src.parser.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.lang.*;
import java.util.stream.Stream;

public class JsonParser {
    public static void parse(String file_path) throws IOException {
        String json = Files.readString(Path.of(file_path));
        try {
            Object result = new Parser(json).parse();
            System.out.println(file_path + " -> Valid JSON");
        } catch (src.InvalidJSONException e) {
            System.out.println(file_path + " -> Invalid JSON : " + e.getMessage());
        }
    }
    public static void main(String[] args) throws IOException {
        Path startPath = Paths.get(args[0]);
        //System.out.println(startPath.toString());
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