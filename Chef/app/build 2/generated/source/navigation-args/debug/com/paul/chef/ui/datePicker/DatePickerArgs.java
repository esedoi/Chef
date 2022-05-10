package com.paul.chef.ui.datePicker;

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

public class DatePickerArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private DatePickerArgs() {
  }

  @SuppressWarnings("unchecked")
  private DatePickerArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static DatePickerArgs fromBundle(@NonNull Bundle bundle) {
    DatePickerArgs __result = new DatePickerArgs();
    bundle.setClassLoader(DatePickerArgs.class.getClassLoader());
    if (bundle.containsKey("chefId")) {
      String chefId;
      chefId = bundle.getString("chefId");
      if (chefId == null) {
        throw new IllegalArgumentException("Argument \"chefId\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("chefId", chefId);
    } else {
      throw new IllegalArgumentException("Required argument \"chefId\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static DatePickerArgs fromSavedStateHandle(@NonNull SavedStateHandle savedStateHandle) {
    DatePickerArgs __result = new DatePickerArgs();
    if (savedStateHandle.contains("chefId")) {
      String chefId;
      chefId = savedStateHandle.get("chefId");
      if (chefId == null) {
        throw new IllegalArgumentException("Argument \"chefId\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("chefId", chefId);
    } else {
      throw new IllegalArgumentException("Required argument \"chefId\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getChefId() {
    return (String) arguments.get("chefId");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("chefId")) {
      String chefId = (String) arguments.get("chefId");
      __result.putString("chefId", chefId);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("chefId")) {
      String chefId = (String) arguments.get("chefId");
      __result.set("chefId", chefId);
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
    DatePickerArgs that = (DatePickerArgs) object;
    if (arguments.containsKey("chefId") != that.arguments.containsKey("chefId")) {
      return false;
    }
    if (getChefId() != null ? !getChefId().equals(that.getChefId()) : that.getChefId() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getChefId() != null ? getChefId().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "DatePickerArgs{"
        + "chefId=" + getChefId()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull DatePickerArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull String chefId) {
      if (chefId == null) {
        throw new IllegalArgumentException("Argument \"chefId\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("chefId", chefId);
    }

    @NonNull
    public DatePickerArgs build() {
      DatePickerArgs result = new DatePickerArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setChefId(@NonNull String chefId) {
      if (chefId == null) {
        throw new IllegalArgumentException("Argument \"chefId\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("chefId", chefId);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getChefId() {
      return (String) arguments.get("chefId");
    }
  }
}
