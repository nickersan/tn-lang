package com.tn.lang.util.stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.tn.lang.util.stream.Collectors.by;
import static com.tn.lang.util.stream.Collectors.toSortedList;
import static com.tn.lang.util.stream.Collectors.toSortedSet;
import static com.tn.lang.util.stream.MergeFunctions.first;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
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

  @Test
  void shouldCollectAndSortAsList()
  {
    assertEquals(
      List.of(1, 1, 2, 3),
      Stream.of(2, 1, 3, 1).collect(toSortedList(Integer::compareTo))
    );
  }

  @Test
  void shouldCollectAndSortAsSet()
  {
    assertEquals(
      new TreeSet<>(List.of(1, 2, 3)),
      Stream.of(2, 1, 3, 1).collect(toSortedSet(Integer::compareTo))
    );
  }


  private record Subject(int id, String name) {}
}
