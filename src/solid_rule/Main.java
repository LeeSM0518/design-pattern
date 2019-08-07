package solid_rule;

public class Main {

  public static void main(String[] args) {
    Toy t = new Lego();
    Kid k = new Kid();
    k.setToy(t);
    k.play();

    t = new Robot();
    k.setToy(t);
    k.play();
  }

}
