package com.paul.chef.ui.menuEdit;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import com.paul.chef.data.Menu;
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
    if (bundle.containsKey("menu")) {
      Menu menu;
      if (Parcelable.class.isAssignableFrom(Menu.class) || Serializable.class.isAssignableFrom(Menu.class)) {
        menu = (Menu) bundle.get("menu");
      } else {
        throw new UnsupportedOperationException(Menu.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
      __result.arguments.put("menu", menu);
    } else {
      throw new IllegalArgumentException("Required argument \"menu\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static MenuEditFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    MenuEditFragmentArgs __result = new MenuEditFragmentArgs();
    if (savedStateHandle.contains("menu")) {
      Menu menu;
      menu = savedStateHandle.get("menu");
      __result.arguments.put("menu", menu);
    } else {
      throw new IllegalArgumentException("Required argument \"menu\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @Nullable
  public Menu getMenu() {
    return (Menu) arguments.get("menu");
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
    if (arguments.containsKey("menu") != that.arguments.containsKey("menu")) {
      return false;
    }
    if (getMenu() != null ? !getMenu().equals(that.getMenu()) : that.getMenu() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getMenu() != null ? getMenu().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "MenuEditFragmentArgs{"
        + "menu=" + getMenu()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull MenuEditFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@Nullable Menu menu) {
      this.arguments.put("menu", menu);
    }

    @NonNull
    public MenuEditFragmentArgs build() {
      MenuEditFragmentArgs result = new MenuEditFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setMenu(@Nullable Menu menu) {
      this.arguments.put("menu", menu);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @Nullable
    public Menu getMenu() {
      return (Menu) arguments.get("menu");
    }
  }
}
