package src;

public interface Parser {

}








//    public static boolean parseValue(String str) throws src.InvalidJSONException{
//        String curr = str.trim();
//        char firstChar = curr.charAt(0);
//        switch (firstChar) {
//            case '{' : return parseObject(curr);
//            case '[' : return parseArray(curr);
//            case '"' : return parseString(curr);
//            case 't', 'f': return parseBoolean(curr);
//            case 'n' : return parseNull(curr);
//            default:
//                if(Character.isDigit(firstChar) || firstChar == '-') return parseNumber(curr);
//                throw new src.InvalidJSONException("Unexpected Character: " + firstChar);
//        }
//
//    }
//
//
//    public static boolean parseObject(String str) throws src.InvalidJSONException {
//        int len = str.length();
//        if(str.charAt(0) != str.charAt(len - 1))
//            throw new src.InvalidJSONException("Object Check: Expected \"}\" but has \"" + str.charAt(len - 1) + "\"");
//        String curr = str.substring(1, len - 1).trim();
////        List<String> keyValueStrList = Arrays.asList(curr.split(","));
////        for(String keyValueStr: keyValueStrList) {
////            if(keyValueStr.trim().isEmpty()) throw new src.InvalidJSONException("Unexpected , at end!!");
////            List<String> keyValue = Arrays.asList(curr.split(":"));
////            if(keyValue.size() != 2) throw new src.InvalidJSONException("Expected Key Value but not found!!");
////            if(!parseString(keyValue.getFirst().trim())) throw new src.InvalidJSONException("Expected Key as String but it is not");
////            if(!parseValue(keyValue.getLast().trim())) throw new src.InvalidJSONException("Expected Value as Object but it is not");
////        }
//
//        return true;
//    }
//
//
//    public static boolean parseArray(String str) throws src.InvalidJSONException {
//        int len = str.length();
//        if(str.charAt(0) != str.charAt(len - 1))
//            throw new src.InvalidJSONException("Array Check: Expected \"]\" but has \"" + str.charAt(len - 1) + "\"");
//        String curr = str.substring(1, len - 1);
//        List<String> keyValueStrList = Arrays.asList(curr.split(","));
//        for(String keyValueStr: keyValueStrList) {
//            if(keyValueStr.trim().isEmpty()) throw new src.InvalidJSONException("Unexpected , at end!!");
//            boolean res = parseValue(keyValueStr);
//        }
//        return true;
//    }
//
//
//    public static boolean parseString(String str) throws src.InvalidJSONException {
//        if(str.charAt(0) != '"') throw new src.InvalidJSONException("String expected but it is not");
//        int len = str.length();
//        if(str.charAt(0) != str.charAt(len - 1))
//            throw new src.InvalidJSONException("String Check: Expected \" but has " + str.charAt(len - 1));
//        return true;
//    }
//
//
//    public static boolean parseNumber(String str) throws src.InvalidJSONException {
//        int len = str.length();
//        for(int i = 1; i < len; i++) {
//            if(!Character.isDigit(str.charAt(i))) throw new src.InvalidJSONException("Expected Number but it is not");
//        }
//        return true;
//    }
//
//
//    public static boolean parseBoolean(String str) throws src.InvalidJSONException {
//        if(str.equals("true") || str.equals("false")) return true;
//        throw new src.InvalidJSONException("Boolean check: Expected Boolean but it is not");
//    }
//
//
//    public static boolean parseNull(String str) throws src.InvalidJSONException {
//        if(str.equals("null")) return true;
//        throw new src.InvalidJSONException("Null check: Expected null but it is not");
//    }


