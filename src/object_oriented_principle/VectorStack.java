package object_oriented_principle;

    import java.util.Vector;

public class VectorStack {

  private Vector<String> stacks = new Vector<>();

  public void push(String element) {
    stacks.add(element);
  }

  public String pop() {
    return stacks.remove(stacks.size() - 1);
  }

  public boolean isEmpty() {
    return stacks.isEmpty();
  }

  public int size() {
    return stacks.size();
  }

}
