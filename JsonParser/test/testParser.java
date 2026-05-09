package test;

import src.InvalidJSONException;
import src.parser.Parser;

public class testParser {
    public static void main(String[] args) throws InvalidJSONException {
        String json = """
                {
                  "name": "Kamal",
                  "age": 22,
                  "cgpa": 8.91,
                  "active": true,
                  "projects": [
                    "JSON Parser",
                    "Job Portal",
                    {
                      "title": "AI Assistant",
                      "completed": false,
                      "rating": null
                    }
                  ],
                  "address": {
                    "city": "Chennai",
                    "pincode": 600001,
                    "coordinates": {
                      "lat": 13.0827,
                      "long": 80.2707
                    }
                  },
                  "emptyObject": {},
                  "emptyArray": [],
                  "scientificNumber": 1.23e5
                }
                """;
        Parser p = new Parser(json);
        p.parse();
    }
}
