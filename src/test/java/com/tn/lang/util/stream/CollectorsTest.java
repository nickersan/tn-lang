package com.tn.lang.util.stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.tn.lang.util.stream.Collectors.by;
import static com.tn.lang.util.stream.MergeFunctions.first;

import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class CollectorsTest
{
  @Test
  void shouldCollectBy()
  {
    Subject subject1 = new Subject(1, "One");
    Subject subject2 = new Subject(2, "Two");
    Subject subject3 = new Subject(3, "Three");

    assertEquals(
      Map.of(subject1.id(), subject1, subject2.id(), subject2, subject3.id(), subject3),
      Stream.of(subject1, subject2, subject3).collect(by(Subject::id))
    );
  }

  @Test
  void shouldCollectByWithMerge()
  {
    Subject subject1 = new Subject(1, "One");
    Subject subject2 = new Subject(2, "Two");
    Subject subject2Duplicate = new Subject(2, "Two Duplicate");
    Subject subject3 = new Subject(3, "Three");

    assertEquals(
      Map.of(subject1.id(), subject1, subject2.id(), subject2, subject3.id(), subject3),
      Stream.of(subject1, subject2, subject2Duplicate, subject3).collect(by(Subject::id, first()))
    );
  }

  private record Subject(int id, String name) {}
}
