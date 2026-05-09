package src.tokenizer;

import src.InvalidJSONException;

public class KeywordHandler {
    public Token readKeyword(CharStream stream) throws InvalidJSONException {
        if(stream.peek() == 't' || stream.peek() == 'n') {
            StringBuilder trueOrNull = new StringBuilder(4);
            for (int i = 0; i < 4 && stream.hasNext(); i++) {
                trueOrNull.append(stream.next());
            }
            if (stream.hasNext()) {
                char ch = stream.peek();
                if (!Character.isWhitespace(ch) && ch != ',' && ch != '}' && ch != ']' && ch != ':') {
                    throw new InvalidJSONException("Invalid character after keyword: " + ch);
                }
            }
            //System.out.println(trueOrNull.toString());
            if(trueOrNull.toString().equals("true")) return new Token(TokenType.TRUE, "true");
            if(trueOrNull.toString().equals("null")) return new Token(TokenType.NULL, "null");
            throw new InvalidJSONException("Invalid keyword: " + trueOrNull);
        }

        StringBuilder falseCheck = new StringBuilder(4);
        for (int i = 0; i < 5 && stream.hasNext(); i++) {
            falseCheck.append(stream.next());
        }
        if (stream.hasNext()) {
            char ch = stream.peek();
            if (!Character.isWhitespace(ch) && ch != ',' && ch != '}' && ch != ']' && ch != ':') {
                throw new InvalidJSONException("Invalid character after keyword: " + ch);
            }
        }
        if(falseCheck.toString().equals("false")) return new Token(TokenType.FALSE, "false");
        throw new InvalidJSONException("Invalid keyword: " + falseCheck);

    }

}
