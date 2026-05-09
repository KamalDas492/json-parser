package src.tokenizer;

import src.InvalidJSONException;

public class NumberHandler {
    public Token readNumber(CharStream stream) throws InvalidJSONException {
        StringBuilder num = new StringBuilder(16);
        if(stream.peek() == '-') {
            num.append('-');
            stream.next();
            if (!stream.hasNext() || !Character.isDigit(stream.peek()) ) throw new InvalidJSONException("Expected Digit after - ");
        }
        handleInteger(num, stream);
        if(stream.hasNext() && stream.peek() == '.') handleFraction(num, stream);
        if(stream.hasNext() && (stream.peek() == 'e' || stream.peek() == 'E')) handleExponent(num, stream);
        if(!stream.hasNext() || (stream.hasNext() && (Character.isWhitespace(stream.peek()) || "]},".contains(String.valueOf(stream.peek())) )))
            return new Token(TokenType.NUMBER, num.toString());

        throw new InvalidJSONException("Unexpected character in number: " + stream.peek());
    }

    private void handleExponent(StringBuilder num, CharStream stream) throws InvalidJSONException {
        if(num.isEmpty() || num.charAt(num.length() - 1) == '-') throw new InvalidJSONException("Exponent can't start number");
        num.append(stream.next());
        int digitCount = 0;
        if(stream.hasNext() && (stream.peek() == '-' || stream.peek() == '+')) {
            num.append(stream.next());
        }

        while(stream.hasNext() && (stream.peek() >= '0' && stream.peek() <= '9')) {
            num.append(stream.next());
            digitCount++;
        }

        if(digitCount == 0) throw new InvalidJSONException("Expected digits after Exponent");

    }

    private void handleFraction(StringBuilder num, CharStream stream) throws InvalidJSONException {
        if(num.isEmpty() || num.charAt(num.length() - 1) == '-') throw new InvalidJSONException("Decimal can't start number");
        num.append('.'); stream.next();
        int digitCount = 0;
        while(stream.hasNext() && (stream.peek() >= '0' && stream.peek() <= '9')) {
            num.append(stream.next());
            digitCount++;
        }
        if(digitCount == 0) throw new InvalidJSONException("Expected digits after decimal");
    }

    private void handleInteger(StringBuilder num, CharStream stream) throws InvalidJSONException {
        boolean zeroFirst = false;
        if(stream.peek() == '0') {
            zeroFirst = true;
            num.append(stream.next());
        }
        while(stream.hasNext() && (stream.peek() >= '0' && stream.peek() <= '9')) {
            if(zeroFirst) throw new InvalidJSONException("0 can't start number");
            num.append(stream.next());
        }
    }
}
