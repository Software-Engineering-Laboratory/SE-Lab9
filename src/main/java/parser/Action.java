package parser;

public class Action {
  public Act action;
  //if action = shift : number is state
  //if action = reduce : number is number of rule
  public int number;

  public Action(Act action, int number) {
    this.action = action;
    this.number = number;
  }

  public String toString() {

    if (action == Act.accept){
      return action.label;
    }
    else if (action == Act.shift || action == Act.reduce) {
      return action.label + number;
    }
    return action.toString() + number;
  }
}

enum Act {

  accept ("acc"),
  reduce ("r"),
  shift ("s");

  public final String label;

  Act(String label) {
    this.label = label;
  }
}
