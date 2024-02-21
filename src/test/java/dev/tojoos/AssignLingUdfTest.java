package dev.tojoos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignLingUdfTest {

  @Test
  void assignLing1() {
    assertEquals("normal", new AssignLingUdf().assignLing(
        "low;0;0;80;100/normal;80;110;320;360/high;320;360;400;400", 190));

    assertEquals("low", new AssignLingUdf().assignLing(
        "low;0;0;80;100/normal;80;110;320;360/high;320;360;400;400", 0));

    assertEquals("normal", new AssignLingUdf().assignLing(
        "low;0;0;80;100/normal;80;110;320;360/high;320;360;400;400", 320));

    assertEquals("normal", new AssignLingUdf().assignLing(
        "low;0;0;80;100/normal;80;110;320;360/high;320;360;400;400", 340));

    assertEquals("high", new AssignLingUdf().assignLing(
        "low;0;0;80;100/normal;80;110;320;360/high;320;360;400;400", 360));
  }

  @Test
  void assignLing2() {
    assertEquals("low", new AssignLingUdf().assignLing(
        "low;0;0;80;80/normal;90;90;100;100/high;120;120;140;140", 79));

    assertEquals("normal", new AssignLingUdf().assignLing(
        "low;0;0;80;80/normal;90;90;100;100/high;120;120;140;140", 99));

    assertEquals("Not classified", new AssignLingUdf().assignLing(
        "low;0;0;80;80/normal;90;90;100;100/high;120;120;140;140", 84));

    assertEquals("Not classified", new AssignLingUdf().assignLing(
        "low;0;0;80;80/normal;90;90;100;100/high;120;120;140;140", 999));

    assertEquals("high", new AssignLingUdf().assignLing(
        "low;0;0;80;80/normal;90;90;100;100/high;120;120;140;140", 122));
  }
}