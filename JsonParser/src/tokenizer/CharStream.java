package src.tokenizer;

public class CharStream {
    private final String input;
    private int index = 0;

    CharStream(String input) {
        this.input = input;
    }

    boolean hasNext() {
        return index < input.length();
    }

    char peek() {
        return input.charAt(index);
    }

    char next() {
        return input.charAt(index++);
    }

    void skipWhitespace() {
        while (hasNext() && Character.isWhitespace(peek())) {
            next();
        }
    }
}
