package com.paul.chef.ui.doneDialog;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class DoneFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private DoneFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private DoneFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static DoneFragmentArgs fromBundle(@NonNull Bundle bundle) {
    DoneFragmentArgs __result = new DoneFragmentArgs();
    bundle.setClassLoader(DoneFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("direction")) {
      String direction;
      direction = bundle.getString("direction");
      if (direction == null) {
        throw new IllegalArgumentException("Argument \"direction\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("direction", direction);
    } else {
      throw new IllegalArgumentException("Required argument \"direction\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static DoneFragmentArgs fromSavedStateHandle(@NonNull SavedStateHandle savedStateHandle) {
    DoneFragmentArgs __result = new DoneFragmentArgs();
    if (savedStateHandle.contains("direction")) {
      String direction;
      direction = savedStateHandle.get("direction");
      if (direction == null) {
        throw new IllegalArgumentException("Argument \"direction\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("direction", direction);
    } else {
      throw new IllegalArgumentException("Required argument \"direction\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getDirection() {
    return (String) arguments.get("direction");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("direction")) {
      String direction = (String) arguments.get("direction");
      __result.putString("direction", direction);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("direction")) {
      String direction = (String) arguments.get("direction");
      __result.set("direction", direction);
    }
    return __result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    DoneFragmentArgs that = (DoneFragmentArgs) object;
    if (arguments.containsKey("direction") != that.arguments.containsKey("direction")) {
      return false;
    }
    if (getDirection() != null ? !getDirection().equals(that.getDirection()) : that.getDirection() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getDirection() != null ? getDirection().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "DoneFragmentArgs{"
        + "direction=" + getDirection()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull DoneFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull String direction) {
      if (direction == null) {
        throw new IllegalArgumentException("Argument \"direction\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("direction", direction);
    }

    @NonNull
    public DoneFragmentArgs build() {
      DoneFragmentArgs result = new DoneFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setDirection(@NonNull String direction) {
      if (direction == null) {
        throw new IllegalArgumentException("Argument \"direction\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("direction", direction);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getDirection() {
      return (String) arguments.get("direction");
    }
  }
}
