package com.paul.chef.ui.imageUpload;

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

public class ImageUploadFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private ImageUploadFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private ImageUploadFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ImageUploadFragmentArgs fromBundle(@NonNull Bundle bundle) {
    ImageUploadFragmentArgs __result = new ImageUploadFragmentArgs();
    bundle.setClassLoader(ImageUploadFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("imageType")) {
      int imageType;
      imageType = bundle.getInt("imageType");
      __result.arguments.put("imageType", imageType);
    } else {
      throw new IllegalArgumentException("Required argument \"imageType\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ImageUploadFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    ImageUploadFragmentArgs __result = new ImageUploadFragmentArgs();
    if (savedStateHandle.contains("imageType")) {
      int imageType;
      imageType = savedStateHandle.get("imageType");
      __result.arguments.put("imageType", imageType);
    } else {
      throw new IllegalArgumentException("Required argument \"imageType\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  public int getImageType() {
    return (int) arguments.get("imageType");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("imageType")) {
      int imageType = (int) arguments.get("imageType");
      __result.putInt("imageType", imageType);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("imageType")) {
      int imageType = (int) arguments.get("imageType");
      __result.set("imageType", imageType);
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
    ImageUploadFragmentArgs that = (ImageUploadFragmentArgs) object;
    if (arguments.containsKey("imageType") != that.arguments.containsKey("imageType")) {
      return false;
    }
    if (getImageType() != that.getImageType()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + getImageType();
    return result;
  }

  @Override
  public String toString() {
    return "ImageUploadFragmentArgs{"
        + "imageType=" + getImageType()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull ImageUploadFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(int imageType) {
      this.arguments.put("imageType", imageType);
    }

    @NonNull
    public ImageUploadFragmentArgs build() {
      ImageUploadFragmentArgs result = new ImageUploadFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setImageType(int imageType) {
      this.arguments.put("imageType", imageType);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    public int getImageType() {
      return (int) arguments.get("imageType");
    }
  }
}
