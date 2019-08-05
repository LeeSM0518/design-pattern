package solid_rule;

import java.util.Calendar;

public class TimeReminder {
  private MP3 m;

  public void reminder() {
    Calendar calendar = Calendar.getInstance();
    m = new MP3();
    int hour = calendar.get(Calendar.HOUR_OF_DAY);

    if (hour >= 22) {
      // TODO 문제 풀이중
//      m.playSong();
    }
  }
}
