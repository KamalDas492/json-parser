package src.parser;

import src.InvalidJSONException;
import src.tokenizer.Token;
import src.tokenizer.TokenType;
import src.tokenizer.Tokenizer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Parser {
    private final List<Token> tokens;
    private int currIndex;

    public Parser(String input) throws InvalidJSONException {
        Tokenizer tokenizer = new Tokenizer(input);
        tokens = tokenizer.getTokens();
        currIndex = 0;
    }

    private Token peek() {
        return tokens.get(currIndex);
    }

    private Token advance() {
        Token token = peek();
        if(!isAtEnd()) currIndex++;
        return token;
    }

    private boolean matches(TokenType type) {
        return peek().getType() == type;
    }

    private boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    private Token consume(TokenType type, String message) throws InvalidJSONException {
        if(!matches(type)) throw new InvalidJSONException("Parser error:" + message);
        return advance();
    }

    public Object parse() throws InvalidJSONException {
        Object result = parseValue();
        consume(TokenType.EOF, "Unexpected tokens after JSON");
        return result;
    }

    public Object parseValue() throws InvalidJSONException {
        if(matches(TokenType.LEFT_BRACE)) {
            advance();
            return parseObject();
        }
        if(matches(TokenType.LEFT_BRACKET)) {
            advance();
            return parseArray();
        }
        if(matches(TokenType.STRING)) return advance().getValue();
        if(matches(TokenType.NUMBER)) {
            String number = advance().getValue();
            if(number.contains(".") || number.contains("e") || number.contains("E")) return new BigDecimal(number);
            return new BigInteger(number);
        }
        if(matches(TokenType.FALSE)) {
            advance();
            return false;
        }
        if(matches(TokenType.NULL)) {
            advance();
            return null;
        }
        if(matches(TokenType.TRUE)) {
            advance();
            return true;
        }

        throw new InvalidJSONException("Invalid JSON Value");
    }

    public List<Object> parseArray() throws InvalidJSONException {
        List<Object> arr = new ArrayList<>();
        if(matches(TokenType.RIGHT_BRACKET)) {
            advance();
            return arr;
        }
        while(true) {
            if(isAtEnd()) throw new InvalidJSONException("Unexpected end of Array!");
            arr.add(parseValue());
            if(matches(TokenType.RIGHT_BRACKET)) {
                advance();
                return arr;
            }
            consume(TokenType.COMMA, "Expected ',' after array element");
        }
    }

    public Map<String, Object> parseObject() throws InvalidJSONException {
        Map<String, Object> obj = new HashMap<>();
        if(matches(TokenType.RIGHT_BRACE)) {
            advance();
            return obj;
        }
        while(true) {
            if(isAtEnd()) throw new InvalidJSONException("Unexpected end of Object!");
            Token field = consume(TokenType.STRING, "String expected!!");
            consume(TokenType.COLON, "Colon expected!");
            obj.put(field.getValue(), parseValue());
            if(matches(TokenType.RIGHT_BRACE)) {
                advance();
                return obj;
            }
            consume(TokenType.COMMA, "Comma expected!!");
        }
    }



}
