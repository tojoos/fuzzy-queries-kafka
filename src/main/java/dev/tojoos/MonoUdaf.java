package dev.tojoos;

import io.confluent.ksql.function.udaf.Udaf;
import io.confluent.ksql.function.udaf.UdafDescription;
import io.confluent.ksql.function.udaf.UdafFactory;
import io.confluent.ksql.function.udf.UdfParameter;

import java.util.LinkedList;
import java.util.List;

@UdafDescription(
    name = "mono",
    description = "Return a trend String ('asc' - ascending / 'desc' - descending / 'const' - constant) of the provided data points",
    version = "1.0.0",
    author = "Jan Olszowka"
)
public class MonoUdaf {
  @UdafFactory(description = "Return a trend, analyzing provided number of elements")
  public static Udaf<Double, List<Double>, String> createUdaf(@UdfParameter(value = "window_size") final int window_size) {
    return new MonoUdfImpl(window_size);
  }

  // UDAFs must be parameterized with three generic types.
  // The first parameter represents the type of the column(s) to aggregate over.
  // The second column represents the internal representation of the aggregation, which is established in initialize().
  // The third parameter represents the type that the query interacts with, which is converted by map().
  private static class MonoUdfImpl implements Udaf<Double, List<Double>, String> {

    public MonoUdfImpl(Integer capacity) {
      this.WINDOW_SIZE = capacity;
    }

    private final int WINDOW_SIZE;

    @Override
    public List<Double> initialize() {
      // the internal representation of state is a LinkedList
      // the List is needed to keep a running history of values
      return new LinkedList<>();
    }

    @Override
    public List<Double> aggregate(Double newValue, List<Double> currentValues) {
      currentValues.add(newValue);

      if (currentValues.size() > WINDOW_SIZE) {
        currentValues = currentValues.subList(1, WINDOW_SIZE + 1); //discard oldest element when new one arrives
      }

      return currentValues;
    }

    @Override
    public String map(List<Double> currentValues) {
      // when ksqlDB interacts with the aggregation value, map() is called, which perform calculation and return val for query.

      // optional -> remove 0.0 when not enough elements in the window:
      // if (currentValues.size < WINDOW_SIZE) return 0.0;

      int ascCount = 0, descCount = 0;
      double prev_val = 0.0;
      for (int i = 0 ; i < currentValues.size(); i++) {
        double current = currentValues.get(i);
        if (i == 0) {
          prev_val = current;
          continue;
        }

        if (current < prev_val) descCount++;
        else if (current > prev_val) ascCount++;

        prev_val = current;
      }

      // the result of the aggregation for specified value (currently aggregated)
      if (ascCount > descCount) return "asc";
      else if (ascCount < descCount) return "desc";
      else return "const";
    }

    // The merge method controls how two session windows fuse together when one extends and overlaps another.
    // The content of the "later" aggregate is simply taken since it by definition contains values from a later window of time.
    @Override
    public List<Double> merge(List<Double> aggOne, List<Double> aggTwo) {
      return aggTwo;
    }
  }
}
