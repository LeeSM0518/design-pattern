package factory_method_pattern;

import template_method_pattern.unused_pattern.Direction;

import java.util.ArrayList;
import java.util.List;

public abstract class ElevatorManager {

  private List<ElevatorController> controllers;

  public ElevatorManager(int controllerCount) {
    controllers = new ArrayList<>(controllerCount);
    for (int i = 0; i < controllerCount; i++) {
      ElevatorController controller = new ElevatorController(i + 1);
      controllers.add(controller);
    }
  }

  // primitive 또는 hook 메서드
  protected abstract ElevatorScheduler getScheduler();

  // 템플릿 메서드
  void requestElevator(int destination, Direction direction) {
    // 히위 클래스에서 오버라이드된 getScheduler 호출
    ElevatorScheduler scheduler = getScheduler();
    int selectedElevator = scheduler.selectElevator(this, destination, direction);
    controllers.get(selectedElevator).gotoFloor(destination);
  }

}