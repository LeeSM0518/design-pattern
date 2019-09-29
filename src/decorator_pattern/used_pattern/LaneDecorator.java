package decorator_pattern.used_pattern;

// 차선 표시를 추가하는 클래스
public class LaneDecorator extends DisplayDecorator {

  // 기존 표시 클래스의 설정
  public LaneDecorator(Display decoratedDisplay) {
    super(decoratedDisplay);
  }

  @Override
  public void draw() {
    super.draw();      // 설정된 기존 표시 기능을 수행
    drawLane();        // 추가적으로 차선을 표시
  }

  // 교통량 표시를 추가하는 클래스
  private void drawLane() {
    // 기존 표시 클래스의 설정
    System.out.println("\t차선 표시");
  }

}
