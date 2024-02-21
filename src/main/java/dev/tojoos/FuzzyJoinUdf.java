package dev.tojoos;

import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;

@UdfDescription(
    name = "fuzzy_join",
    description = "Return integrity level using intersection of two joined triangles",
    version = "1.0.0",
    author = "Jan Olszowka"
)
public class FuzzyJoinUdf {

  @Udf(description = "Return integrity level given two triangles (given peak x-coordinate and border width)")
  public double fuzzyJoin(
      @UdfParameter(value = "a", description = "the center coordinate of first triangle") final double a,
      @UdfParameter(value = "aStep", description = "half width of base of first triangle") final double aStep,
      @UdfParameter(value = "b", description = "the center coordinate of second triangle") final double b,
      @UdfParameter(value = "bStep", description = "half width of base of second triangle") final double bStep) {
    double innerLeft = calculateIntersection(b-bStep, 0, b, 1,
        a, 1, a+aStep, 0);

    double innerRight = calculateIntersection(a-aStep, 0, a, 1,
        b, 1, b+bStep, 0);

    // round values to 4 decimal places
    innerLeft = (double) Math.round(innerLeft * 10000) / 10000;
    innerRight = (double) Math.round(innerRight * 10000) / 10000;

    if (innerRight > 1.0f) {
      return innerLeft >= 0 ? innerLeft : 0.0;
    } else if (innerLeft > 1.0f) {
      return innerRight >= 0 ? innerRight : 0.0;
    } else {
      return Math.max(innerRight, innerLeft);
    }
  }

  private double calculateIntersection(double a1, double b1, double a2, double b2,
                                     double c1, double d1, double c2, double d2) {
    // Find slopes (m1 and m2) and y-intercepts (b1 and b2) for each line
    double m1 = (b2 - b1) / (a2 - a1);
    double b_line1 = b1 - m1 * a1;

    double m2 = (d2 - d1) / (c2 - c1);
    double b_line2 = d1 - m2 * c1;

    // Find x-coordinate of intersection
    double x_intersect = (b_line2 - b_line1) / (m1 - m2);

    // Find y-coordinate of intersection
    return m1 * x_intersect + b_line1;
  }
}
