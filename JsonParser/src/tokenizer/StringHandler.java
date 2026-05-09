package src.tokenizer;

import src.InvalidJSONException;

public class StringHandler {
    private void handleUnicode(CharStream stream, StringBuilder sb) throws InvalidJSONException {
        if (!stream.hasNext()) throw new InvalidJSONException("Invalid unicode");

        StringBuilder hex = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            if (!stream.hasNext()) throw new InvalidJSONException("Invalid unicode");
            hex.append(stream.next());
        }

        try {
            sb.append((char) Integer.parseInt(hex.toString(), 16));
        } catch (Exception e) {
            throw new InvalidJSONException("Invalid unicode escape: \\u" + hex);
        }
    }

    public Token readString(CharStream stream) throws InvalidJSONException {
        StringBuilder sb = new StringBuilder(16);
        //sb.append(input.charAt(index));
        stream.next();
        while(stream.hasNext()) {
            if(stream.peek() == '\\') {
                stream.next();
                if(!stream.hasNext()) throw new InvalidJSONException("Unexpected end with Escape character");
                switch (stream.peek()) {
                    case '"': sb.append('"'); stream.next(); break;
                    case '\\': sb.append('\\'); stream.next(); break;
                    case '/': sb.append('/'); stream.next(); break;
                    case 'b': sb.append('\b'); stream.next(); break;
                    case 'r': sb.append('\r'); stream.next(); break;
                    case 't': sb.append('\t'); stream.next(); break;
                    case 'n': sb.append('\n'); stream.next(); break;
                    case 'f': sb.append('\f'); stream.next(); break;
                    case 'u': stream.next(); handleUnicode(stream, sb); break;
                    default:
                        throw new InvalidJSONException("Unknown escape Character");
                }

            } else if(stream.peek() == '"'){
                //sb.append(input.charAt(index));
                stream.next();
                return new Token(TokenType.STRING, sb.toString());
            } else {
                char ch = stream.peek();
                if (ch < 0x20) {
                    throw new InvalidJSONException("Unescaped control character in string");
                }
                sb.append(ch);
                stream.next();
            }
        }

        throw new InvalidJSONException("Unexpected end of String");
    }
}
