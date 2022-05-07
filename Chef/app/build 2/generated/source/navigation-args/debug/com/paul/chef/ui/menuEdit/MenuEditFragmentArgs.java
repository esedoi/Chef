package com.paul.chef.ui.menuEdit;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import com.paul.chef.data.Dish;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class MenuEditFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private MenuEditFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private MenuEditFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static MenuEditFragmentArgs fromBundle(@NonNull Bundle bundle) {
    MenuEditFragmentArgs __result = new MenuEditFragmentArgs();
    bundle.setClassLoader(MenuEditFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("dish")) {
      Dish dish;
      if (Parcelable.class.isAssignableFrom(Dish.class) || Serializable.class.isAssignableFrom(Dish.class)) {
        dish = (Dish) bundle.get("dish");
      } else {
        throw new UnsupportedOperationException(Dish.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
      __result.arguments.put("dish", dish);
    } else {
      throw new IllegalArgumentException("Required argument \"dish\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static MenuEditFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    MenuEditFragmentArgs __result = new MenuEditFragmentArgs();
    if (savedStateHandle.contains("dish")) {
      Dish dish;
      dish = savedStateHandle.get("dish");
      __result.arguments.put("dish", dish);
    } else {
      throw new IllegalArgumentException("Required argument \"dish\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @Nullable
  public Dish getDish() {
    return (Dish) arguments.get("dish");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("dish")) {
      Dish dish = (Dish) arguments.get("dish");
      if (Parcelable.class.isAssignableFrom(Dish.class) || dish == null) {
        __result.putParcelable("dish", Parcelable.class.cast(dish));
      } else if (Serializable.class.isAssignableFrom(Dish.class)) {
        __result.putSerializable("dish", Serializable.class.cast(dish));
      } else {
        throw new UnsupportedOperationException(Dish.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("dish")) {
      Dish dish = (Dish) arguments.get("dish");
      if (Parcelable.class.isAssignableFrom(Dish.class) || dish == null) {
        __result.set("dish", Parcelable.class.cast(dish));
      } else if (Serializable.class.isAssignableFrom(Dish.class)) {
        __result.set("dish", Serializable.class.cast(dish));
      } else {
        throw new UnsupportedOperationException(Dish.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
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
    MenuEditFragmentArgs that = (MenuEditFragmentArgs) object;
    if (arguments.containsKey("dish") != that.arguments.containsKey("dish")) {
      return false;
    }
    if (getDish() != null ? !getDish().equals(that.getDish()) : that.getDish() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getDish() != null ? getDish().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "MenuEditFragmentArgs{"
        + "dish=" + getDish()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull MenuEditFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@Nullable Dish dish) {
      this.arguments.put("dish", dish);
    }

    @NonNull
    public MenuEditFragmentArgs build() {
      MenuEditFragmentArgs result = new MenuEditFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setDish(@Nullable Dish dish) {
      this.arguments.put("dish", dish);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @Nullable
    public Dish getDish() {
      return (Dish) arguments.get("dish");
    }
  }
}
