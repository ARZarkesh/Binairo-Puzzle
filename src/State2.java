import java.util.ArrayList;
import java.util.Arrays;

public class State2 {
  private String value;
  private ArrayList<String> domain = new ArrayList<>(Arrays.asList("w", "b"));
  private boolean isDefault;

  public State2(String value) {
    if (!(domain.contains(value) || value.equals("e"))) {
      throw new IllegalArgumentException("Invalid value");
    }
    this.value = value;
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
}
