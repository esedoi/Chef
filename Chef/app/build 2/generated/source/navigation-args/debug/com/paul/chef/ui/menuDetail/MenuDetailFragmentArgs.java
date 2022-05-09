package com.paul.chef.ui.menuDetail;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
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

public class MenuDetailFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private MenuDetailFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private MenuDetailFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static MenuDetailFragmentArgs fromBundle(@NonNull Bundle bundle) {
    MenuDetailFragmentArgs __result = new MenuDetailFragmentArgs();
    bundle.setClassLoader(MenuDetailFragmentArgs.class.getClassLoader());
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
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static MenuDetailFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    MenuDetailFragmentArgs __result = new MenuDetailFragmentArgs();
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
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public ChefMenu getChefMenu() {
    return (ChefMenu) arguments.get("chefMenu");
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
    MenuDetailFragmentArgs that = (MenuDetailFragmentArgs) object;
    if (arguments.containsKey("chefMenu") != that.arguments.containsKey("chefMenu")) {
      return false;
    }
    if (getChefMenu() != null ? !getChefMenu().equals(that.getChefMenu()) : that.getChefMenu() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getChefMenu() != null ? getChefMenu().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "MenuDetailFragmentArgs{"
        + "chefMenu=" + getChefMenu()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull MenuDetailFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull ChefMenu chefMenu) {
      if (chefMenu == null) {
        throw new IllegalArgumentException("Argument \"chefMenu\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("chefMenu", chefMenu);
    }

    @NonNull
    public MenuDetailFragmentArgs build() {
      MenuDetailFragmentArgs result = new MenuDetailFragmentArgs(arguments);
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

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public ChefMenu getChefMenu() {
      return (ChefMenu) arguments.get("chefMenu");
    }
  }
}
