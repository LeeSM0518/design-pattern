package composite_pattern.used_pattern;

import java.util.ArrayList;
import java.util.List;

public class Computer extends ComputerDevice {

  private List<ComputerDevice> components = new ArrayList<>();

  public void addComponent(ComputerDevice component) {
    components.add(component);
  }

  public void removeComponent(ComputerDevice component) {
    components.remove(component);
  }

  @Override
  public int getPrice() {
    return components.stream().mapToInt(ComputerDevice::getPrice).sum();
  }

  @Override
  public int getPower() {
    return components.stream().mapToInt(ComputerDevice::getPower).sum();
  }
}
