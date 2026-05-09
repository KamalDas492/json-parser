package test;

import src.InvalidJSONException;
import src.tokenizer.Token;
import src.tokenizer.TokenType;
import src.tokenizer.Tokenizer;

public class numberTests {
    String[] valid = {
            "0",
            "1",
            "9",
            "10",
            "123",
            "-1",
            "-999",

            "0.0",
            "1.0",
            "3.14",
            "-0.5",
            "-10.25",
            "123.456",

            "1e10",
            "1E10",
            "-1e10",
            "2e+10",
            "2e-10",
            "-3E+7",
            "-4E-3",

            "0e0",
            "0e10",
            "-0e10",
            "1.5e10",
            "-1.5e-10",
            "123.456E+789",

            "0",
            "-0",
            "0.1",
            "-0.1",
            "1e0"
    };
    String[] invalid = {
            "01",
            "00",
            "0123",
            "-01",

            ".1",
            "-.5",

            "1.",
            "-1.",
            "0.",

            "1e",
            "1E",
            "1e+",
            "1e-",
            "1e+ ",
            "1e-]",

            "1.2.3",
            "1e2e3",

            "12a3",
            "1_000",

            "-",
            "+",

            "--1",
            "1-2",
            "1e--2",

            "00.1",
            "01.5"
    };

    public void testNumbers() throws InvalidJSONException {
        for (String s : valid) testValid(s);
        for (String s : invalid) testInvalid(s);
    }

    public void testValid(String s) throws InvalidJSONException {
        Tokenizer tokenizer = new Tokenizer(s);
        try {
            Token token = tokenizer.nextToken();
            assert token.getType() == TokenType.NUMBER;
        } catch (InvalidJSONException e) {
            System.out.println(s + " -> Valid Test Failed: " + e.getMessage());
        }

    }

    public void testInvalid(String s) throws InvalidJSONException {
        Tokenizer tokenizer = new Tokenizer(s);
        try {
            Token token = tokenizer.nextToken();
            System.out.println("Invalid Test failed: " + s);
        } catch (InvalidJSONException e) {
            //System.out.println(e.getMessage());
        }

    }

}
