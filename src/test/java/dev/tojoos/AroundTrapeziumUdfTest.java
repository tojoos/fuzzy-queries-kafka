package dev.tojoos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AroundTrapeziumUdfTest {

  @Test
  void aroundTrapezium1() {
    assertEquals( 0.5,
        new AroundTrapeziumUdf().aroundTrapezium(25.0,
            0.0,
            0.0,
            20.0,
            30.0));

    assertEquals( 1.0,
        new AroundTrapeziumUdf().aroundTrapezium(0,
            0.0,
            0.0,
            20.0,
            30.0));

    assertEquals( 0,
        new AroundTrapeziumUdf().aroundTrapezium(40,
            0.0,
            0.0,
            20.0,
            30.0));

    assertEquals( 1.0,
        new AroundTrapeziumUdf().aroundTrapezium(15,
            0.0,
            0.0,
            20.0,
            30.0));
  }

  @Test
  void aroundTrapezium2() {
    assertEquals( 1.0,
        new AroundTrapeziumUdf().aroundTrapezium(11.0,
            10.0,
            10.0,
            15.0,
            15.0));

    assertEquals( 1.0,
        new AroundTrapeziumUdf().aroundTrapezium(15.0,
            10.0,
            10.0,
            15.0,
            15.0));

    assertEquals( 0.0,
        new AroundTrapeziumUdf().aroundTrapezium(17.0,
            10.0,
            10.0,
            15.0,
            15.0));
  }

  @Test
  void aroundTrapezium3() {
    assertEquals( 0.6,
        new AroundTrapeziumUdf().aroundTrapezium(6.0,
            0.0,
            10.0,
            40.0,
            50.0));

    assertEquals( 0.2,
        new AroundTrapeziumUdf().aroundTrapezium(48.0,
            0.0,
            10.0,
            40.0,
            50.0));
  }
}