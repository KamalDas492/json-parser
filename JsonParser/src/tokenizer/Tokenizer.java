package src.tokenizer;


import src.InvalidJSONException;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private final CharStream stream;
    private final StringHandler stringHandler;
    private final KeywordHandler keywordHandler;
    private final NumberHandler numberHandler;


    public Tokenizer(String input) {
        this.stream = new CharStream(input);
        this.stringHandler = new StringHandler();
        this.keywordHandler = new KeywordHandler();
        this.numberHandler = new NumberHandler();
    }

    public List<Token> getTokens() throws InvalidJSONException {
        List<Token> tokens = new ArrayList<>();
        while(true){
            Token token = this.nextToken();
            if(token.getType() == TokenType.EOF) {
                tokens.add(token);
                return tokens;
            }
            tokens.add(token);
        }
    }


    public Token nextToken() throws InvalidJSONException {
            stream.skipWhitespace();
            if(!stream.hasNext()) return new Token(TokenType.EOF, null);
            char currChar = stream.peek();
            return switch (currChar) {
                case '{' -> {
                    stream.next();
                    yield new Token(TokenType.LEFT_BRACE, "{");
                }
                case '}' -> {
                    stream.next();
                    yield new Token(TokenType.RIGHT_BRACE, "}");
                }
                case '[' -> {
                    stream.next();
                    yield new Token(TokenType.LEFT_BRACKET, "[");
                }
                case ']' -> {
                    stream.next();
                    yield new Token(TokenType.RIGHT_BRACKET, "]");
                }
                case ':' -> {
                    stream.next();
                    yield new Token(TokenType.COLON, ":");
                }
                case ',' -> {
                    stream.next();
                    yield new Token(TokenType.COMMA, ",");
                }
                case '"' -> stringHandler.readString(stream);
                case '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> numberHandler.readNumber(stream);
                case 't', 'f', 'n' -> keywordHandler.readKeyword(stream);
                default -> throw new InvalidJSONException("Unexpected Character: " + stream.peek());
            };


    }
}
