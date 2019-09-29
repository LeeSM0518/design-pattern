package decorator_pattern.used_pattern;

// 다양한 추가 기능에 대한 공통 클래스
public class DisplayDecorator extends Display {

  private Display decoratedDisplay;

  public DisplayDecorator(Display decoratedDisplay) {
    this.decoratedDisplay = decoratedDisplay;
  }

  @Override
  public void draw() {
    decoratedDisplay.draw();
  }

}
