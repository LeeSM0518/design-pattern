package decorator_pattern.used_pattern;

public class Client {

  public static void main(String[] args) {
//    Display road = new RoadDisplay();
//    road.draw();  // 기본 도로 표시
//
//    Display roadWithLane = new LaneDecorator(new RoadDisplay());
//    roadWithLane.draw();  // 기본 도로 표시 + 차선 표시
//
//    Display roadWithTraffic = new TrafficDecorator(new RoadDisplay());
//    roadWithTraffic.draw();  // 기본 도로 표시 + 교통량 표시
//
//    Display roadWithLaneAndTraffic =
//        new TrafficDecorator(new LaneDecorator(new RoadDisplay()));
//    roadWithLaneAndTraffic.draw();
//
//    Display roadWithCrossingAndLaneAndTraffic =
//        new LaneDecorator(new TrafficDecorator(new CrossingDecorator(new RoadDisplay())));
//    roadWithCrossingAndLaneAndTraffic.draw();
    Display road = new RoadDisplay();
    for (String decoratorName : args) {
      if (decoratorName.equalsIgnoreCase("Lane"))
        road = new LaneDecorator(road);     // 차선 표시 기능을 동적으로 추가
      if (decoratorName.equalsIgnoreCase("Traffic"))
        road = new TrafficDecorator(road);  // 교통량 표시 기능을 동적으로 추가
      if (decoratorName.equalsIgnoreCase("Crossing"))
        road = new CrossingDecorator(road); // 교차로 표시 기능을 동적으로 추가
    }
    road.draw();
  }

}
