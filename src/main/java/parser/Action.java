package parser;

public class Action {
  private Act action;
  //if action = shift : number is state
  //if action = reduce : number is number of rule
  private int number;


  public Action(Act action, int number) {
    this.action = action;
    this.number = number;
  }

  public int getNumber() {
    return number;
  }

  public Act getAction(){
    return this.action;
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
