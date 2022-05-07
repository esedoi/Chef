package com.paul.chef.ui.book;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import com.paul.chef.data.ChefMenu;
import com.paul.chef.data.Dish;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.lang.System;
import java.util.HashMap;

public class BookFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private BookFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private BookFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static BookFragmentArgs fromBundle(@NonNull Bundle bundle) {
    BookFragmentArgs __result = new BookFragmentArgs();
    bundle.setClassLoader(BookFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("chefMenu")) {
      ChefMenu chefMenu;
      if (Parcelable.class.isAssignableFrom(ChefMenu.class) || Serializable.class.isAssignableFrom(ChefMenu.class)) {
        chefMenu = (ChefMenu) bundle.get("chefMenu");
      } else {
        throw new UnsupportedOperationException(ChefMenu.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
      if (chefMenu == null) {
        throw new IllegalArgumentException("Argument \"chefMenu\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("chefMenu", chefMenu);
    } else {
      throw new IllegalArgumentException("Required argument \"chefMenu\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("selectedDish")) {
      Dish[] selectedDish;
      Parcelable[] __array = bundle.getParcelableArray("selectedDish");
      if (__array != null) {
        selectedDish = new Dish[__array.length];
        System.arraycopy(__array, 0, selectedDish, 0, __array.length);
      } else {
        selectedDish = null;
      }
      if (selectedDish == null) {
        throw new IllegalArgumentException("Argument \"selectedDish\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("selectedDish", selectedDish);
    } else {
      throw new IllegalArgumentException("Required argument \"selectedDish\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static BookFragmentArgs fromSavedStateHandle(@NonNull SavedStateHandle savedStateHandle) {
    BookFragmentArgs __result = new BookFragmentArgs();
    if (savedStateHandle.contains("chefMenu")) {
      ChefMenu chefMenu;
      chefMenu = savedStateHandle.get("chefMenu");
      if (chefMenu == null) {
        throw new IllegalArgumentException("Argument \"chefMenu\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("chefMenu", chefMenu);
    } else {
      throw new IllegalArgumentException("Required argument \"chefMenu\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("selectedDish")) {
      Dish[] selectedDish;
      selectedDish = savedStateHandle.get("selectedDish");
      if (selectedDish == null) {
        throw new IllegalArgumentException("Argument \"selectedDish\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("selectedDish", selectedDish);
    } else {
      throw new IllegalArgumentException("Required argument \"selectedDish\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public ChefMenu getChefMenu() {
    return (ChefMenu) arguments.get("chefMenu");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Dish[] getSelectedDish() {
    return (Dish[]) arguments.get("selectedDish");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("chefMenu")) {
      ChefMenu chefMenu = (ChefMenu) arguments.get("chefMenu");
      if (Parcelable.class.isAssignableFrom(ChefMenu.class) || chefMenu == null) {
        __result.putParcelable("chefMenu", Parcelable.class.cast(chefMenu));
      } else if (Serializable.class.isAssignableFrom(ChefMenu.class)) {
        __result.putSerializable("chefMenu", Serializable.class.cast(chefMenu));
      } else {
        throw new UnsupportedOperationException(ChefMenu.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
    }
    if (arguments.containsKey("selectedDish")) {
      Dish[] selectedDish = (Dish[]) arguments.get("selectedDish");
      __result.putParcelableArray("selectedDish", selectedDish);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("chefMenu")) {
      ChefMenu chefMenu = (ChefMenu) arguments.get("chefMenu");
      if (Parcelable.class.isAssignableFrom(ChefMenu.class) || chefMenu == null) {
        __result.set("chefMenu", Parcelable.class.cast(chefMenu));
      } else if (Serializable.class.isAssignableFrom(ChefMenu.class)) {
        __result.set("chefMenu", Serializable.class.cast(chefMenu));
      } else {
        throw new UnsupportedOperationException(ChefMenu.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
    }
    if (arguments.containsKey("selectedDish")) {
      Dish[] selectedDish = (Dish[]) arguments.get("selectedDish");
      __result.set("selectedDish", selectedDish);
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
    BookFragmentArgs that = (BookFragmentArgs) object;
    if (arguments.containsKey("chefMenu") != that.arguments.containsKey("chefMenu")) {
      return false;
    }
    if (getChefMenu() != null ? !getChefMenu().equals(that.getChefMenu()) : that.getChefMenu() != null) {
      return false;
    }
    if (arguments.containsKey("selectedDish") != that.arguments.containsKey("selectedDish")) {
      return false;
    }
    if (getSelectedDish() != null ? !getSelectedDish().equals(that.getSelectedDish()) : that.getSelectedDish() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getChefMenu() != null ? getChefMenu().hashCode() : 0);
    result = 31 * result + java.util.Arrays.hashCode(getSelectedDish());
    return result;
  }

  @Override
  public String toString() {
    return "BookFragmentArgs{"
        + "chefMenu=" + getChefMenu()
        + ", selectedDish=" + getSelectedDish()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull BookFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull ChefMenu chefMenu, @NonNull Dish[] selectedDish) {
      if (chefMenu == null) {
        throw new IllegalArgumentException("Argument \"chefMenu\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("chefMenu", chefMenu);
      if (selectedDish == null) {
        throw new IllegalArgumentException("Argument \"selectedDish\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("selectedDish", selectedDish);
    }

    @NonNull
    public BookFragmentArgs build() {
      BookFragmentArgs result = new BookFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setChefMenu(@NonNull ChefMenu chefMenu) {
      if (chefMenu == null) {
        throw new IllegalArgumentException("Argument \"chefMenu\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("chefMenu", chefMenu);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setSelectedDish(@NonNull Dish[] selectedDish) {
      if (selectedDish == null) {
        throw new IllegalArgumentException("Argument \"selectedDish\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("selectedDish", selectedDish);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public ChefMenu getChefMenu() {
      return (ChefMenu) arguments.get("chefMenu");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public Dish[] getSelectedDish() {
      return (Dish[]) arguments.get("selectedDish");
    }
  }
}
