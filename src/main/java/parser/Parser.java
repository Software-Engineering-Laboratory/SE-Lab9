package parser;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import Log.LogUtil;
import codeGenerator.CodeGenerator;
import errorHandler.ErrorHandlerUtil;
import scanner.LexicalAnalyzer;
import scanner.token.Token;


public class Parser {
  private List<Rule> rules;
  private Stack<Integer> parsStack;
  private ParseTable parseTable;
  private CodeGenerator cg;

  public Parser() {
    parsStack = new Stack<Integer>();
    parsStack.push(0);
    try {
      parseTable = new ParseTable(Files.readAllLines(Paths.get("src/main/resources/parseTable")).get(0));
    } catch (Exception e) {
      e.printStackTrace();
    }
    rules = new ArrayList<Rule>();
    try {
      for (String stringRule : Files.readAllLines(Paths.get("src/main/resources/Rules"))) {
        rules.add(new Rule(stringRule));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    cg = new CodeGenerator();
  }

  public void startParse(java.util.Scanner sc) {
    LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(sc);
    Token lookAhead = lexicalAnalyzer.getNextToken();
    boolean finish = false;
    Action currentAction;
    while (!finish) {
      try {
        LogUtil.print(/*"lookahead : "+*/ lookAhead.toString() + "\t" + parsStack.peek());
        currentAction = parseTable.getActionTable(parsStack.peek(), lookAhead);
        LogUtil.print(currentAction.toString());

        switch (currentAction.getAction()) {
          case shift:
            parsStack.push(currentAction.getNumber());
            lookAhead = lexicalAnalyzer.getNextToken();

            break;
          case reduce:
            Rule rule = rules.get(currentAction.getNumber());
            for (int i = 0; i < rule.RHS.size(); i++) {
              parsStack.pop();
            }

            LogUtil.print(/*"state : " +*/ parsStack.peek() + "\t" + rule.LHS);
            parsStack.push(parseTable.getGotoTable(parsStack.peek(), rule.LHS));
            LogUtil.print(/*"new State : " + */parsStack.peek() + "");
            try {
              cg.semanticFunction(rule.semanticAction, lookAhead);
            } catch (Exception e) {
              LogUtil.print("Code Genetator Error");
            }
            break;
          case accept:
            finish = true;
            break;
        }
        LogUtil.print("");

      } catch (Exception ignored) {

        ignored.printStackTrace();

      }


    }
    if (!ErrorHandlerUtil.hasError)
      cg.printMemory();


  }


}
