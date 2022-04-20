import java.util.ArrayList;
import java.util.Arrays;

public class State {
  private String value;
  private ArrayList<String> domain = new ArrayList<>(Arrays.asList("b", "w"));
  private ArrayList<String> remainingValues;
  private boolean isDefault;

  public State(String value) {
    if (!(domain.contains(value) || value.equals("e"))) {
      throw new IllegalArgumentException("Invalid value");
    }
    this.value = value;
    this.remainingValues = new ArrayList<>(domain);
  }

  public void set(String value, boolean isDefault) {
    if (!(domain.contains(value) || value.equals("e"))) {
      throw new IllegalArgumentException("Invalid value");
    }
    this.value = value;
    this.isDefault = isDefault;
  }

  public String getValue() {
    return value;
  }

  public boolean isDefault() {
    return isDefault;
  }

  public ArrayList<String> getDomain() {
    return domain;
  }

  public void removeFromRemainingValues(String value) {
    if (!remainingValues.contains(value)) {
      throw new IllegalArgumentException("Value not in remaining values");
    }
    remainingValues.remove(value);
  }

  public ArrayList<String> getRemainingValues() {
    return remainingValues;
  }

  public void resetRemainingValues() {
    this.remainingValues = new ArrayList<>(domain);
  }
}
