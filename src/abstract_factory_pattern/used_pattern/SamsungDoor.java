package abstract_factory_pattern.used_pattern;

import abstract_factory_pattern.unused_pattern.Door;

public class SamsungDoor extends Door {

  @Override
  protected void doClose() {
    System.out.println("close Samsung Door");
  }

  @Override
  protected void doOpen() {
    System.out.println("open Samsung Door");
  }

}
