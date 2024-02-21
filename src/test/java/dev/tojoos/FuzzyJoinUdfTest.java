package dev.tojoos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FuzzyJoinUdfTest {

  @Test
  void fuzzyJoin() {
    assertEquals(0.5, new FuzzyJoinUdf().fuzzyJoin(10, 5, 5, 5));
    assertEquals(0, new FuzzyJoinUdf().fuzzyJoin(2, 2, 6, 2));
    assertEquals(1, new FuzzyJoinUdf().fuzzyJoin(5, 5, 5, 5));
    assertEquals(0.9, new FuzzyJoinUdf().fuzzyJoin(5, 5, 6, 5));
    assertEquals(0.5833, new FuzzyJoinUdf().fuzzyJoin(10, 10, 15, 2));
    assertEquals(0.1667, new FuzzyJoinUdf().fuzzyJoin(10, 10, 20, 2));
  }
}