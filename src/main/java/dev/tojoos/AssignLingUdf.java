package dev.tojoos;

import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;

import java.util.HashMap;
import java.util.Map;

@UdfDescription(
    name = "assign_ling",
    description = "Return linguistic string based on given trapezium string and value",
    version = "1.0.0",
    author = "Jan Olszowka"
)
public class AssignLingUdf {

  @Udf(description = "Integration level for trapezium as doubles, returns string")
  public String assignLing(
      @UdfParameter(value = "lingLevel", description = "string specifying trapeziums and their linguistic levels") final String lingLevel,
      @UdfParameter(value = "x", description = "the value to be checked") final double x) {
    Map<String, Double> lingNameToVal = new HashMap<>();
    AroundTrapeziumUdf aroundTrapeziumUdf = new AroundTrapeziumUdf(); //use around trapezium

    String[] tables = lingLevel.split("/");
    for (String tab : tables) {
      String[] elements = tab.split(";");
      double integrityLevel = aroundTrapeziumUdf.aroundTrapezium(x, Double.parseDouble(elements[1]), Double.parseDouble(elements[2]), Double.parseDouble(elements[3]), Double.parseDouble(elements[4]));
      lingNameToVal.put(elements[0], integrityLevel);
    }

    lingNameToVal.forEach((k, v) -> System.out.println("name: " + k + ", lvl:" + v));

    Map.Entry<String, Double> maxEntry = lingNameToVal.entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .orElse(null);

    if (maxEntry == null || maxEntry.getValue() == 0.0f) return "Not classified";

    return maxEntry.getKey();
  }
}
