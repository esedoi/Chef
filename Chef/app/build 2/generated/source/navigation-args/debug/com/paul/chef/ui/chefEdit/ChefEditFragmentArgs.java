package com.paul.chef.ui.chefEdit;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import com.paul.chef.data.ProfileInfo;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class ChefEditFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private ChefEditFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private ChefEditFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ChefEditFragmentArgs fromBundle(@NonNull Bundle bundle) {
    ChefEditFragmentArgs __result = new ChefEditFragmentArgs();
    bundle.setClassLoader(ChefEditFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("editType")) {
      int editType;
      editType = bundle.getInt("editType");
      __result.arguments.put("editType", editType);
    } else {
      throw new IllegalArgumentException("Required argument \"editType\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("profile")) {
      ProfileInfo profile;
      if (Parcelable.class.isAssignableFrom(ProfileInfo.class) || Serializable.class.isAssignableFrom(ProfileInfo.class)) {
        profile = (ProfileInfo) bundle.get("profile");
      } else {
        throw new UnsupportedOperationException(ProfileInfo.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
      if (profile == null) {
        throw new IllegalArgumentException("Argument \"profile\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("profile", profile);
    } else {
      throw new IllegalArgumentException("Required argument \"profile\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ChefEditFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    ChefEditFragmentArgs __result = new ChefEditFragmentArgs();
    if (savedStateHandle.contains("editType")) {
      int editType;
      editType = savedStateHandle.get("editType");
      __result.arguments.put("editType", editType);
    } else {
      throw new IllegalArgumentException("Required argument \"editType\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("profile")) {
      ProfileInfo profile;
      profile = savedStateHandle.get("profile");
      if (profile == null) {
        throw new IllegalArgumentException("Argument \"profile\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("profile", profile);
    } else {
      throw new IllegalArgumentException("Required argument \"profile\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  public int getEditType() {
    return (int) arguments.get("editType");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public ProfileInfo getProfile() {
    return (ProfileInfo) arguments.get("profile");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("editType")) {
      int editType = (int) arguments.get("editType");
      __result.putInt("editType", editType);
    }
    if (arguments.containsKey("profile")) {
      ProfileInfo profile = (ProfileInfo) arguments.get("profile");
      if (Parcelable.class.isAssignableFrom(ProfileInfo.class) || profile == null) {
        __result.putParcelable("profile", Parcelable.class.cast(profile));
      } else if (Serializable.class.isAssignableFrom(ProfileInfo.class)) {
        __result.putSerializable("profile", Serializable.class.cast(profile));
      } else {
        throw new UnsupportedOperationException(ProfileInfo.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("editType")) {
      int editType = (int) arguments.get("editType");
      __result.set("editType", editType);
    }
    if (arguments.containsKey("profile")) {
      ProfileInfo profile = (ProfileInfo) arguments.get("profile");
      if (Parcelable.class.isAssignableFrom(ProfileInfo.class) || profile == null) {
        __result.set("profile", Parcelable.class.cast(profile));
      } else if (Serializable.class.isAssignableFrom(ProfileInfo.class)) {
        __result.set("profile", Serializable.class.cast(profile));
      } else {
        throw new UnsupportedOperationException(ProfileInfo.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
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
    ChefEditFragmentArgs that = (ChefEditFragmentArgs) object;
    if (arguments.containsKey("editType") != that.arguments.containsKey("editType")) {
      return false;
    }
    if (getEditType() != that.getEditType()) {
      return false;
    }
    if (arguments.containsKey("profile") != that.arguments.containsKey("profile")) {
      return false;
    }
    if (getProfile() != null ? !getProfile().equals(that.getProfile()) : that.getProfile() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + getEditType();
    result = 31 * result + (getProfile() != null ? getProfile().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ChefEditFragmentArgs{"
        + "editType=" + getEditType()
        + ", profile=" + getProfile()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull ChefEditFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(int editType, @NonNull ProfileInfo profile) {
      this.arguments.put("editType", editType);
      if (profile == null) {
        throw new IllegalArgumentException("Argument \"profile\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("profile", profile);
    }

    @NonNull
    public ChefEditFragmentArgs build() {
      ChefEditFragmentArgs result = new ChefEditFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setEditType(int editType) {
      this.arguments.put("editType", editType);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setProfile(@NonNull ProfileInfo profile) {
      if (profile == null) {
        throw new IllegalArgumentException("Argument \"profile\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("profile", profile);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    public int getEditType() {
      return (int) arguments.get("editType");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public ProfileInfo getProfile() {
      return (ProfileInfo) arguments.get("profile");
    }
  }
}
