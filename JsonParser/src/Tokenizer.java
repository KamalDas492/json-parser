package src;


public class Tokenizer {
    private String input;
    private int index;

    public Tokenizer(String input) {
        this.input = input;
    }

    private void advance() {
        index++;
    }

    private void skipWhiteSpaces() {
        while(index < input.length() && Character.isWhitespace(input.charAt(index))) {
            advance();
        }
    }

    private void handleUniCode(StringBuilder sb) throws InvalidJSONException {
        advance();
        if(index + 4 > input.length()) throw new InvalidJSONException("Invalid Unicode expression");
        StringBuilder hex = new StringBuilder(4);
        for(int i = 0; i < 4; i++) {
            hex.append(input.charAt(index));
            advance();
        }
        sb.append(Integer.parseInt(hex.toString(), 16));
    }

    private Token readString() throws InvalidJSONException {
        StringBuilder sb = new StringBuilder(16);
        sb.append(input.charAt(index));
        while(index < input.length()) {
            if(input.charAt(index) == '\\') {
                advance();
                if(index == input.length()) throw new InvalidJSONException("Unexpected end with Escape character");
                switch (input.charAt(index)) {
                    case '"': sb.append('"'); advance(); break;
                    case '\\': sb.append('\\'); advance(); break;
                    case '/': sb.append('/'); advance(); break;
                    case 'b': sb.append('\b'); advance(); break;
                    case 'r': sb.append('\r'); advance(); break;
                    case 't': sb.append('\t'); advance(); break;
                    case 'n': sb.append('\n'); advance(); break;
                    case 'f': sb.append('\f'); advance(); break;
                    case 'u': handleUniCode(sb); advance(); break;
                    default:
                        throw new InvalidJSONException("Unknown escape Character");
                }

            } else if(input.charAt(index) == '"'){
                sb.append(input.charAt(index));
                return new Token(TokenType.STRING, sb.toString());
            } else {
                sb.append(input.charAt(index));
                advance();
            }
        }

        throw new InvalidJSONException("Unexpected end of String");
    }

    private Token readKeyword() throws InvalidJSONException {
        StringBuilder sb = new StringBuilder(16);
        if(input.charAt(index) == 't' || input.charAt(index) == 'n') {
            StringBuilder trueOrNull = new StringBuilder(4);
            while (index < input.length()) {
                trueOrNull.append(input.charAt(index));
                advance();
            }
            if(trueOrNull.toString().equals("true")) return new Token(TokenType.TRUE, "true");
            if(trueOrNull.toString().equals("null")) return new Token(TokenType.NULL, "null");
            throw new InvalidJSONException("Expected true or null");
        }

        StringBuilder falseCheck = new StringBuilder(4);
        while (index < input.length()) {
            falseCheck.append(input.charAt(index));
            advance();
        }
        if(falseCheck.toString().equals("false")) return new Token(TokenType.FALSE, "false");
        throw new InvalidJSONException("Expected false");
    }

    private Token readNumber() throws InvalidJSONException {
        int startIndex = index;
        StringBuilder num = new StringBuilder(16);
        if(input.charAt(index) == '-') {
            num.append('-');
            advance();
            if (index == input.length() || !Character.isDigit(input.charAt(index)) ) throw new InvalidJSONException("Expected Digit after - ");
        }
        handleInteger(num);
        if(index < input.length() && input.charAt(index) == '.') handleFraction(num);
        if(index < input.length() && (input.charAt(index) == 'e' || input.charAt(index) == 'E')) handleExponent(num);
        if(index == input.length() || (index < input.length() && (Character.isWhitespace(input.charAt(index)) || "]},".contains(String.valueOf(input.charAt(index))) )))
            return new Token(TokenType.NUMBER, num.toString());

        throw new InvalidJSONException("Unexpected character in number");
    }

    private void handleExponent(StringBuilder num) throws InvalidJSONException {
        if(num.isEmpty() || num.charAt(num.length() - 1) == '-') throw new InvalidJSONException("Exponent can't start number");
        num.append(input.charAt(index)); advance();
        int digitCount = 0;
        if(index < input.length() && (input.charAt(index) == '-' || input.charAt(index) == '+')) {
            num.append(input.charAt(index));
            advance();
        }

        while(index < input.length() && (input.charAt(index) >= '0' && input.charAt(index) <= '9')) {
            num.append(input.charAt(index));
            digitCount++;
            advance();
        }

        if(digitCount == 0) throw new InvalidJSONException("Expected digits after Exponent");

    }

    private void handleFraction(StringBuilder num) throws InvalidJSONException {
        if(num.isEmpty() || num.charAt(num.length() - 1) == '-') throw new InvalidJSONException("Decimal can't start number");
        num.append('.'); advance();
        int digitCount = 0;
        while(index < input.length() && (input.charAt(index) >= '0' && input.charAt(index) <= '9')) {
            num.append(input.charAt(index));
            advance();
            digitCount++;
        }
        if(digitCount == 0) throw new InvalidJSONException("Expected digits after decimal");
    }

    private void handleInteger(StringBuilder num) throws InvalidJSONException {
        boolean zeroFirst = false;
        if(input.charAt(index) == '0') {
            zeroFirst = true;
            num.append('0');
            advance();
        }
        while(index < input.length() && (input.charAt(index) >= '0' && input.charAt(index) <= '9')) {
            if(zeroFirst) throw new InvalidJSONException("0 can't start number");
            num.append(input.charAt(index));
            advance();
        }
    }

    public Token nextToken() throws InvalidJSONException {

        while(index < input.length()) {
            skipWhiteSpaces();
            switch (input.charAt(index)) {
                case '{': advance(); return new Token(TokenType.LEFT_BRACE, "{");
                case '}': advance(); return new Token(TokenType.RIGHT_BRACE, "}");
                case '[': advance(); return new Token(TokenType.LEFT_BRACKET, "[");
                case ']': advance(); return new Token(TokenType.RIGHT_BRACKET, "]");
                case ':': advance(); return new Token(TokenType.COLON, ":");
                case ',': advance(); return new Token(TokenType.COMMA, ",");
                case '"': return readString();
                case '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9': return readNumber();
                case 't', 'f', 'n': return readKeyword();
                default:
                    throw new InvalidJSONException("Unexpected Character");
            }
        }

        return new Token(TokenType.EOF, null);

    }
}
