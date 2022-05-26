package com.paul.chef.ui.reviewPage;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import com.paul.chef.data.Review;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.lang.System;
import java.util.HashMap;

public class ReviewPageArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private ReviewPageArgs() {
  }

  @SuppressWarnings("unchecked")
  private ReviewPageArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ReviewPageArgs fromBundle(@NonNull Bundle bundle) {
    ReviewPageArgs __result = new ReviewPageArgs();
    bundle.setClassLoader(ReviewPageArgs.class.getClassLoader());
    if (bundle.containsKey("review")) {
      Review[] review;
      Parcelable[] __array = bundle.getParcelableArray("review");
      if (__array != null) {
        review = new Review[__array.length];
        System.arraycopy(__array, 0, review, 0, __array.length);
      } else {
        review = null;
      }
      if (review == null) {
        throw new IllegalArgumentException("Argument \"review\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("review", review);
    } else {
      throw new IllegalArgumentException("Required argument \"review\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ReviewPageArgs fromSavedStateHandle(@NonNull SavedStateHandle savedStateHandle) {
    ReviewPageArgs __result = new ReviewPageArgs();
    if (savedStateHandle.contains("review")) {
      Review[] review;
      review = savedStateHandle.get("review");
      if (review == null) {
        throw new IllegalArgumentException("Argument \"review\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("review", review);
    } else {
      throw new IllegalArgumentException("Required argument \"review\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Review[] getReview() {
    return (Review[]) arguments.get("review");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("review")) {
      Review[] review = (Review[]) arguments.get("review");
      __result.putParcelableArray("review", review);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("review")) {
      Review[] review = (Review[]) arguments.get("review");
      __result.set("review", review);
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
    ReviewPageArgs that = (ReviewPageArgs) object;
    if (arguments.containsKey("review") != that.arguments.containsKey("review")) {
      return false;
    }
    if (getReview() != null ? !getReview().equals(that.getReview()) : that.getReview() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + java.util.Arrays.hashCode(getReview());
    return result;
  }

  @Override
  public String toString() {
    return "ReviewPageArgs{"
        + "review=" + getReview()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull ReviewPageArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull Review[] review) {
      if (review == null) {
        throw new IllegalArgumentException("Argument \"review\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("review", review);
    }

    @NonNull
    public ReviewPageArgs build() {
      ReviewPageArgs result = new ReviewPageArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setReview(@NonNull Review[] review) {
      if (review == null) {
        throw new IllegalArgumentException("Argument \"review\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("review", review);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public Review[] getReview() {
      return (Review[]) arguments.get("review");
    }
  }
}
