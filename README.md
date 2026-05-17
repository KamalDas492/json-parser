# JSON Validator

A lightweight, zero-dependency JSON syntax validator built from scratch in Java.

This project implements a custom **Tokenizer (Lexer)** and **Recursive-Descent Parser** to determine whether raw text input is syntactically valid according to the **RFC 8259 JSON specification**.

---

## Features

* Implements a custom lexical analyzer to convert raw character streams into JSON tokens.
* Uses a hand-written recursive-descent parser to validate JSON grammar and structure.
* Validates:
  - objects
  - arrays
  - strings
  - numbers
  - booleans
  - null values
* Detects malformed JSON such as:
  - unmatched braces/brackets
  - invalid commas
  - missing colons
  - malformed strings
  - invalid nesting
* Recursively traverses directory trees and validates multiple `.json` files in sequence.
* Prints clear validation results for each file (valid/invalid).
