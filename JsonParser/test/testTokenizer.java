package test;

import src.InvalidJSONException;
import src.tokenizer.Token;
import src.tokenizer.TokenType;
import src.tokenizer.Tokenizer;

public class testTokenizer {
    public static void main(String[] args) throws InvalidJSONException {
//        numberTests test1 = new numberTests();
//        test1.testNumbers();
       String input = //"""
//                {
//                  "string": "Hello \\"World\\" \\n Unicode: \\u0041",
//                  "number_int": 123,
//                  "number_negative": -456,
//                  "number_fraction": 78.90,
//                  "number_exponent": 1.23e-4,
//                  "number_zero": 0,
//                  "boolean_true": true,
//                  "boolean_false": false,
//                  "null_value": null,
//                  "array": [1, "text", false, null, 3.14e2],
//                  "nested_object": {
//                    "inner_key": "inner_value"
//                  }
//                }
//                """;

//               """
//                       ""\"
//                                       {
//                                         "bad_string": "Hello World",
//                                         "bad_unicode": "\\\\u12G4",
//                                         "bad_number1": 01,
//                                         "bad_number2": 1.,
//                                         "bad_number3": 1e,
//                                         "bad_number4": 1e+,
//                                         "bad_number5": 1e2.3,
//                                         "bad_keyword": trueX,
//                                         "bad_escape": "\\\\x",
//                                         "unterminated_string": "abc,
//                                         "trailing_comma": [1,2,3,],
//                                         "extra_token": nullabc
//                                       }
//                                       ""\";
//                       """
        """
                {
                  "bad_string": "Hello World",
                  "bad_unicode": "\\u123",
                  "bad_number1": 1,
                  "bad_number2": 0.1,
                  "bad_number3": 1e0,
                  "bad_number4": 1e+0,
                  "bad_number5": 1e23,
                  "bad_keyword": true,
                  "bad_escape": "\\n",
                  "unterminated_string": "abc",
                  "trailing_comma": [1,2,3,],
                  "extra_token": null
                }
                """;
//                """
//                {"a":"b"}""";
//                """
//                       [ {"a":"hello\\nworld"},
//                                {"num": },
//                                {"a":"\\u2160"}
//                                {"a":"x"}
//                                {"num":-1.1}
//                                {"num":12}
//                                {"num":1.2}
//                                {"num":1e01n},
//                                {"num":1e+0},
//                                {"num":1.2},
//                                {"num":12},
//                                {"key":falsen},
//                                {"key":true},
//                                {"a":1 "b":2},
//                                {"a":1,}
//                                ]
//                """;

        Tokenizer tokenizer = new Tokenizer(input);
        int ind = 0;
        while(ind < 100) {
            Token token = tokenizer.nextToken();
            if(token.getType() == TokenType.EOF) {
                System.out.println("EOF");
                break;
            }
            System.out.println(token.getType() + " Value: " + token.getValue());
            ind++;
        }
    }
}
