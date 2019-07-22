package object_oriented_modeling;

import java.util.Vector;

public class Course {

  private String name;
  private Vector<Transcript> transcripts;

  public Course(String name) {
    this.name = name;
    transcripts = new Vector<>();
  }

  public void addTranscript(Transcript transcript) {
    transcripts.add(transcript);
  }

  public Vector<Transcript> getTranscripts() {
    return transcripts;
  }

  public String getName() {
    return name;
  }

}