package com.paul.chef.ui.bottomSheetPicker;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class PickerBottomSheetArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private PickerBottomSheetArgs() {
  }

  @SuppressWarnings("unchecked")
  private PickerBottomSheetArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static PickerBottomSheetArgs fromBundle(@NonNull Bundle bundle) {
    PickerBottomSheetArgs __result = new PickerBottomSheetArgs();
    bundle.setClassLoader(PickerBottomSheetArgs.class.getClassLoader());
    if (bundle.containsKey("pickerType")) {
      int pickerType;
      pickerType = bundle.getInt("pickerType");
      __result.arguments.put("pickerType", pickerType);
    } else {
      throw new IllegalArgumentException("Required argument \"pickerType\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("chefId")) {
      String chefId;
      chefId = bundle.getString("chefId");
      __result.arguments.put("chefId", chefId);
    } else {
      throw new IllegalArgumentException("Required argument \"chefId\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static PickerBottomSheetArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    PickerBottomSheetArgs __result = new PickerBottomSheetArgs();
    if (savedStateHandle.contains("pickerType")) {
      int pickerType;
      pickerType = savedStateHandle.get("pickerType");
      __result.arguments.put("pickerType", pickerType);
    } else {
      throw new IllegalArgumentException("Required argument \"pickerType\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("chefId")) {
      String chefId;
      chefId = savedStateHandle.get("chefId");
      __result.arguments.put("chefId", chefId);
    } else {
      throw new IllegalArgumentException("Required argument \"chefId\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  public int getPickerType() {
    return (int) arguments.get("pickerType");
  }

  @SuppressWarnings("unchecked")
  @Nullable
  public String getChefId() {
    return (String) arguments.get("chefId");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("pickerType")) {
      int pickerType = (int) arguments.get("pickerType");
      __result.putInt("pickerType", pickerType);
    }
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
    if (arguments.containsKey("pickerType")) {
      int pickerType = (int) arguments.get("pickerType");
      __result.set("pickerType", pickerType);
    }
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
    PickerBottomSheetArgs that = (PickerBottomSheetArgs) object;
    if (arguments.containsKey("pickerType") != that.arguments.containsKey("pickerType")) {
      return false;
    }
    if (getPickerType() != that.getPickerType()) {
      return false;
    }
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
    result = 31 * result + getPickerType();
    result = 31 * result + (getChefId() != null ? getChefId().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "PickerBottomSheetArgs{"
        + "pickerType=" + getPickerType()
        + ", chefId=" + getChefId()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull PickerBottomSheetArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(int pickerType, @Nullable String chefId) {
      this.arguments.put("pickerType", pickerType);
      this.arguments.put("chefId", chefId);
    }

    @NonNull
    public PickerBottomSheetArgs build() {
      PickerBottomSheetArgs result = new PickerBottomSheetArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setPickerType(int pickerType) {
      this.arguments.put("pickerType", pickerType);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setChefId(@Nullable String chefId) {
      this.arguments.put("chefId", chefId);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    public int getPickerType() {
      return (int) arguments.get("pickerType");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @Nullable
    public String getChefId() {
      return (String) arguments.get("chefId");
    }
  }
}
