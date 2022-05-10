package com.paul.chef.ui.calendarSetting;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import com.paul.chef.data.SelectedDates;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class CalendarSettingArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private CalendarSettingArgs() {
  }

  @SuppressWarnings("unchecked")
  private CalendarSettingArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static CalendarSettingArgs fromBundle(@NonNull Bundle bundle) {
    CalendarSettingArgs __result = new CalendarSettingArgs();
    bundle.setClassLoader(CalendarSettingArgs.class.getClassLoader());
    if (bundle.containsKey("selectedDates")) {
      SelectedDates selectedDates;
      if (Parcelable.class.isAssignableFrom(SelectedDates.class) || Serializable.class.isAssignableFrom(SelectedDates.class)) {
        selectedDates = (SelectedDates) bundle.get("selectedDates");
      } else {
        throw new UnsupportedOperationException(SelectedDates.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
      if (selectedDates == null) {
        throw new IllegalArgumentException("Argument \"selectedDates\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("selectedDates", selectedDates);
    } else {
      throw new IllegalArgumentException("Required argument \"selectedDates\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static CalendarSettingArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    CalendarSettingArgs __result = new CalendarSettingArgs();
    if (savedStateHandle.contains("selectedDates")) {
      SelectedDates selectedDates;
      selectedDates = savedStateHandle.get("selectedDates");
      if (selectedDates == null) {
        throw new IllegalArgumentException("Argument \"selectedDates\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("selectedDates", selectedDates);
    } else {
      throw new IllegalArgumentException("Required argument \"selectedDates\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SelectedDates getSelectedDates() {
    return (SelectedDates) arguments.get("selectedDates");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("selectedDates")) {
      SelectedDates selectedDates = (SelectedDates) arguments.get("selectedDates");
      if (Parcelable.class.isAssignableFrom(SelectedDates.class) || selectedDates == null) {
        __result.putParcelable("selectedDates", Parcelable.class.cast(selectedDates));
      } else if (Serializable.class.isAssignableFrom(SelectedDates.class)) {
        __result.putSerializable("selectedDates", Serializable.class.cast(selectedDates));
      } else {
        throw new UnsupportedOperationException(SelectedDates.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("selectedDates")) {
      SelectedDates selectedDates = (SelectedDates) arguments.get("selectedDates");
      if (Parcelable.class.isAssignableFrom(SelectedDates.class) || selectedDates == null) {
        __result.set("selectedDates", Parcelable.class.cast(selectedDates));
      } else if (Serializable.class.isAssignableFrom(SelectedDates.class)) {
        __result.set("selectedDates", Serializable.class.cast(selectedDates));
      } else {
        throw new UnsupportedOperationException(SelectedDates.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
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
    CalendarSettingArgs that = (CalendarSettingArgs) object;
    if (arguments.containsKey("selectedDates") != that.arguments.containsKey("selectedDates")) {
      return false;
    }
    if (getSelectedDates() != null ? !getSelectedDates().equals(that.getSelectedDates()) : that.getSelectedDates() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getSelectedDates() != null ? getSelectedDates().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "CalendarSettingArgs{"
        + "selectedDates=" + getSelectedDates()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull CalendarSettingArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull SelectedDates selectedDates) {
      if (selectedDates == null) {
        throw new IllegalArgumentException("Argument \"selectedDates\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("selectedDates", selectedDates);
    }

    @NonNull
    public CalendarSettingArgs build() {
      CalendarSettingArgs result = new CalendarSettingArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setSelectedDates(@NonNull SelectedDates selectedDates) {
      if (selectedDates == null) {
        throw new IllegalArgumentException("Argument \"selectedDates\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("selectedDates", selectedDates);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public SelectedDates getSelectedDates() {
      return (SelectedDates) arguments.get("selectedDates");
    }
  }
}
