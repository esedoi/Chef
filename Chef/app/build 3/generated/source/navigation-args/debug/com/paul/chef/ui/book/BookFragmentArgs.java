package com.paul.chef.ui.book;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import com.paul.chef.data.Dish;
import com.paul.chef.data.Menu;
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
    if (bundle.containsKey("menu")) {
      Menu menu;
      if (Parcelable.class.isAssignableFrom(Menu.class) || Serializable.class.isAssignableFrom(Menu.class)) {
        menu = (Menu) bundle.get("menu");
      } else {
        throw new UnsupportedOperationException(Menu.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
      if (menu == null) {
        throw new IllegalArgumentException("Argument \"menu\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("menu", menu);
    } else {
      throw new IllegalArgumentException("Required argument \"menu\" is missing and does not have an android:defaultValue");
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
    if (bundle.containsKey("type")) {
      int type;
      type = bundle.getInt("type");
      __result.arguments.put("type", type);
    } else {
      throw new IllegalArgumentException("Required argument \"type\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static BookFragmentArgs fromSavedStateHandle(@NonNull SavedStateHandle savedStateHandle) {
    BookFragmentArgs __result = new BookFragmentArgs();
    if (savedStateHandle.contains("menu")) {
      Menu menu;
      menu = savedStateHandle.get("menu");
      if (menu == null) {
        throw new IllegalArgumentException("Argument \"menu\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("menu", menu);
    } else {
      throw new IllegalArgumentException("Required argument \"menu\" is missing and does not have an android:defaultValue");
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
    if (savedStateHandle.contains("type")) {
      int type;
      type = savedStateHandle.get("type");
      __result.arguments.put("type", type);
    } else {
      throw new IllegalArgumentException("Required argument \"type\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Menu getMenu() {
    return (Menu) arguments.get("menu");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Dish[] getSelectedDish() {
    return (Dish[]) arguments.get("selectedDish");
  }

  @SuppressWarnings("unchecked")
  public int getType() {
    return (int) arguments.get("type");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("menu")) {
      Menu menu = (Menu) arguments.get("menu");
      if (Parcelable.class.isAssignableFrom(Menu.class) || menu == null) {
        __result.putParcelable("menu", Parcelable.class.cast(menu));
      } else if (Serializable.class.isAssignableFrom(Menu.class)) {
        __result.putSerializable("menu", Serializable.class.cast(menu));
      } else {
        throw new UnsupportedOperationException(Menu.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
    }
    if (arguments.containsKey("selectedDish")) {
      Dish[] selectedDish = (Dish[]) arguments.get("selectedDish");
      __result.putParcelableArray("selectedDish", selectedDish);
    }
    if (arguments.containsKey("type")) {
      int type = (int) arguments.get("type");
      __result.putInt("type", type);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("menu")) {
      Menu menu = (Menu) arguments.get("menu");
      if (Parcelable.class.isAssignableFrom(Menu.class) || menu == null) {
        __result.set("menu", Parcelable.class.cast(menu));
      } else if (Serializable.class.isAssignableFrom(Menu.class)) {
        __result.set("menu", Serializable.class.cast(menu));
      } else {
        throw new UnsupportedOperationException(Menu.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
    }
    if (arguments.containsKey("selectedDish")) {
      Dish[] selectedDish = (Dish[]) arguments.get("selectedDish");
      __result.set("selectedDish", selectedDish);
    }
    if (arguments.containsKey("type")) {
      int type = (int) arguments.get("type");
      __result.set("type", type);
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
    if (arguments.containsKey("menu") != that.arguments.containsKey("menu")) {
      return false;
    }
    if (getMenu() != null ? !getMenu().equals(that.getMenu()) : that.getMenu() != null) {
      return false;
    }
    if (arguments.containsKey("selectedDish") != that.arguments.containsKey("selectedDish")) {
      return false;
    }
    if (getSelectedDish() != null ? !getSelectedDish().equals(that.getSelectedDish()) : that.getSelectedDish() != null) {
      return false;
    }
    if (arguments.containsKey("type") != that.arguments.containsKey("type")) {
      return false;
    }
    if (getType() != that.getType()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getMenu() != null ? getMenu().hashCode() : 0);
    result = 31 * result + java.util.Arrays.hashCode(getSelectedDish());
    result = 31 * result + getType();
    return result;
  }

  @Override
  public String toString() {
    return "BookFragmentArgs{"
        + "menu=" + getMenu()
        + ", selectedDish=" + getSelectedDish()
        + ", type=" + getType()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull BookFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull Menu menu, @NonNull Dish[] selectedDish, int type) {
      if (menu == null) {
        throw new IllegalArgumentException("Argument \"menu\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("menu", menu);
      if (selectedDish == null) {
        throw new IllegalArgumentException("Argument \"selectedDish\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("selectedDish", selectedDish);
      this.arguments.put("type", type);
    }

    @NonNull
    public BookFragmentArgs build() {
      BookFragmentArgs result = new BookFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setMenu(@NonNull Menu menu) {
      if (menu == null) {
        throw new IllegalArgumentException("Argument \"menu\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("menu", menu);
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

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setType(int type) {
      this.arguments.put("type", type);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public Menu getMenu() {
      return (Menu) arguments.get("menu");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public Dish[] getSelectedDish() {
      return (Dish[]) arguments.get("selectedDish");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    public int getType() {
      return (int) arguments.get("type");
    }
  }
}
