package solid_rule;

import java.util.Calendar;

// 테스트 스텁
public class FakeTimeProvider implements TimeProvider {

  private Calendar calendar;

  public FakeTimeProvider() {
    this.calendar = Calendar.getInstance();
  }

  public FakeTimeProvider(int hours) {
    this.calendar = Calendar.getInstance();
    setHours(hours);
  }

  @Override
  public void setHours(int hours) {
    this.calendar.set(Calendar.HOUR_OF_DAY, hours);
  }

  @Override
  public int getTime() {
    return calendar.get(Calendar.HOUR_OF_DAY);
  }

}
