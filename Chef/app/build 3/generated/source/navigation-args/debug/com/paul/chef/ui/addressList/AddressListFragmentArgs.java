package com.paul.chef.ui.addressList;

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

public class AddressListFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private AddressListFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private AddressListFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static AddressListFragmentArgs fromBundle(@NonNull Bundle bundle) {
    AddressListFragmentArgs __result = new AddressListFragmentArgs();
    bundle.setClassLoader(AddressListFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("listType")) {
      int listType;
      listType = bundle.getInt("listType");
      __result.arguments.put("listType", listType);
    } else {
      throw new IllegalArgumentException("Required argument \"listType\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static AddressListFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    AddressListFragmentArgs __result = new AddressListFragmentArgs();
    if (savedStateHandle.contains("listType")) {
      int listType;
      listType = savedStateHandle.get("listType");
      __result.arguments.put("listType", listType);
    } else {
      throw new IllegalArgumentException("Required argument \"listType\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  public int getListType() {
    return (int) arguments.get("listType");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("listType")) {
      int listType = (int) arguments.get("listType");
      __result.putInt("listType", listType);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("listType")) {
      int listType = (int) arguments.get("listType");
      __result.set("listType", listType);
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
    AddressListFragmentArgs that = (AddressListFragmentArgs) object;
    if (arguments.containsKey("listType") != that.arguments.containsKey("listType")) {
      return false;
    }
    if (getListType() != that.getListType()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + getListType();
    return result;
  }

  @Override
  public String toString() {
    return "AddressListFragmentArgs{"
        + "listType=" + getListType()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull AddressListFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(int listType) {
      this.arguments.put("listType", listType);
    }

    @NonNull
    public AddressListFragmentArgs build() {
      AddressListFragmentArgs result = new AddressListFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setListType(int listType) {
      this.arguments.put("listType", listType);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    public int getListType() {
      return (int) arguments.get("listType");
    }
  }
}
