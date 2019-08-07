package solid_rule;

public class TimeReminder2 {

  TimeProvider timeProvider;
  MP3 mp3 = new MP3();

  public void setTimeProvider(TimeProvider timeProvider) {
    this.timeProvider = timeProvider;
  }

  public void reminder() {
    int hours = timeProvider.getTime();
    if (hours >= 22) {
      mp3.playSong();
    }
  }

  public static void main(String[] args) {
    TimeReminder2 timeReminder = new TimeReminder2();
    TimeProvider timeProvider = new FakeTimeProvider();
    timeProvider.setHours(18);
    timeReminder.setTimeProvider(timeProvider);
    timeReminder.reminder();
  }

}