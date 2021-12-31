package scanner;

import errorHandler.ErrorHandlerUtil;
import scanner.token.Token;
import scanner.type.Type;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {

    private Matcher matcher;


    public LexicalAnalyzer(java.util.Scanner sc) {
        StringBuilder input = new StringBuilder();
        while (sc.hasNext()) {
            input.append(sc.nextLine());
        }
        StringBuilder tokenPattern = new StringBuilder();
        for (Type tokenType : Type.values())
            tokenPattern.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
        Pattern expression = Pattern.compile(tokenPattern.substring(1));
        matcher = expression.matcher(input.toString());
    }

    public Token getNextToken() {

        while (matcher.find()) {
            for (Type t : Type.values()) {
                if (matcher.group(t.name()) != null) {
                    if (areBothArgsNull(matcher.group(Type.COMMENT.name()), matcher.group(Type.ErrorID.name()))) {
                        return new Token(t, matcher.group(t.name()));

                    }

                    if(matcher.group(Type.ErrorID.name())!=null){
                        ErrorHandlerUtil.printError("The id must start with character");
                    }
                    break;

                }
            }
        }
        return new Token(Type.EOF,"$");
    }

    public Boolean areBothArgsNull(String s1, String s2) {
        return s1 == null && s2 == null;
    }
}
