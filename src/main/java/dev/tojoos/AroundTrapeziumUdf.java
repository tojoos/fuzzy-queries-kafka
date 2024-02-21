package dev.tojoos;

import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;

@UdfDescription(
    name = "around_trapez",
    description = "Return integrity level based on given trapezium and value",
    version = "1.0.0",
    author = "Jan Olszowka"
)
public class AroundTrapeziumUdf {

  @Udf(description = "Accepts trapezium as doubles and value, return integration level as double")
  public double aroundTrapezium(
      @UdfParameter(value = "x", description = "the value to be checked") final double x,
      @UdfParameter(value = "a", description = "first coordinate of trapezium") final double a,
      @UdfParameter(value = "b", description = "second coordinate of trapezium") final double b,
      @UdfParameter(value = "c", description = "third coordinate of trapezium") final double c,
      @UdfParameter(value = "d", description = "fourth coordinate of trapezium") final double d) {
    // validate trapezium
    if (a > b || b > c || c > d) throw new RuntimeException("Invalid trapezium");

    if (x < a || x > d) return 0.0f;
    if (x <= b) {
      if (b == a) return 1.0f;
      return (double) Math.round((x-a)/(b-a) * 10000) / 10000;
    }
    if (x <= c) return 1.0f;
    else {
      if (d == c) return 1.0f;
      return (double) Math.round((d-x)/(d-c) * 10000) / 10000;
    }
  }

  @Udf(description = "Accepts trapezium as ints and value, return integration level as double")
  public double aroundTrapezium(
      @UdfParameter(value = "x", description = "the value to be checked") final double x,
      @UdfParameter(value = "a", description = "first coordinate of trapezium") final int a,
      @UdfParameter(value = "b", description = "second coordinate of trapezium") final int b,
      @UdfParameter(value = "c", description = "third coordinate of trapezium") final int c,
      @UdfParameter(value = "d", description = "fourth coordinate of trapezium") final int d) {
    // validate trapezium
    if (a > b || b > c || c > d) throw new RuntimeException("Invalid trapezium");

    if (x < a || x > d) return 0.0f;
    if (x <= b) {
      if (b == a) return 1.0f;
      return (double) Math.round((x-a)/(b-a) * 10000) / 10000;
    }
    if (x <= c) return 1.0f;
    else {
      if (b == a) return 1.0f;
      return (double) Math.round((d-x)/(d-c) * 10000) / 10000;
    }
  }
}
