package com.paul.chef.ui.review;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import com.paul.chef.data.Order;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class ReviewFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private ReviewFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private ReviewFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ReviewFragmentArgs fromBundle(@NonNull Bundle bundle) {
    ReviewFragmentArgs __result = new ReviewFragmentArgs();
    bundle.setClassLoader(ReviewFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("rating")) {
      int rating;
      rating = bundle.getInt("rating");
      __result.arguments.put("rating", rating);
    } else {
      throw new IllegalArgumentException("Required argument \"rating\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("order")) {
      Order order;
      if (Parcelable.class.isAssignableFrom(Order.class) || Serializable.class.isAssignableFrom(Order.class)) {
        order = (Order) bundle.get("order");
      } else {
        throw new UnsupportedOperationException(Order.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
      if (order == null) {
        throw new IllegalArgumentException("Argument \"order\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("order", order);
    } else {
      throw new IllegalArgumentException("Required argument \"order\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ReviewFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    ReviewFragmentArgs __result = new ReviewFragmentArgs();
    if (savedStateHandle.contains("rating")) {
      int rating;
      rating = savedStateHandle.get("rating");
      __result.arguments.put("rating", rating);
    } else {
      throw new IllegalArgumentException("Required argument \"rating\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("order")) {
      Order order;
      order = savedStateHandle.get("order");
      if (order == null) {
        throw new IllegalArgumentException("Argument \"order\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("order", order);
    } else {
      throw new IllegalArgumentException("Required argument \"order\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  public int getRating() {
    return (int) arguments.get("rating");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Order getOrder() {
    return (Order) arguments.get("order");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("rating")) {
      int rating = (int) arguments.get("rating");
      __result.putInt("rating", rating);
    }
    if (arguments.containsKey("order")) {
      Order order = (Order) arguments.get("order");
      if (Parcelable.class.isAssignableFrom(Order.class) || order == null) {
        __result.putParcelable("order", Parcelable.class.cast(order));
      } else if (Serializable.class.isAssignableFrom(Order.class)) {
        __result.putSerializable("order", Serializable.class.cast(order));
      } else {
        throw new UnsupportedOperationException(Order.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("rating")) {
      int rating = (int) arguments.get("rating");
      __result.set("rating", rating);
    }
    if (arguments.containsKey("order")) {
      Order order = (Order) arguments.get("order");
      if (Parcelable.class.isAssignableFrom(Order.class) || order == null) {
        __result.set("order", Parcelable.class.cast(order));
      } else if (Serializable.class.isAssignableFrom(Order.class)) {
        __result.set("order", Serializable.class.cast(order));
      } else {
        throw new UnsupportedOperationException(Order.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
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
    ReviewFragmentArgs that = (ReviewFragmentArgs) object;
    if (arguments.containsKey("rating") != that.arguments.containsKey("rating")) {
      return false;
    }
    if (getRating() != that.getRating()) {
      return false;
    }
    if (arguments.containsKey("order") != that.arguments.containsKey("order")) {
      return false;
    }
    if (getOrder() != null ? !getOrder().equals(that.getOrder()) : that.getOrder() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + getRating();
    result = 31 * result + (getOrder() != null ? getOrder().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ReviewFragmentArgs{"
        + "rating=" + getRating()
        + ", order=" + getOrder()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull ReviewFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(int rating, @NonNull Order order) {
      this.arguments.put("rating", rating);
      if (order == null) {
        throw new IllegalArgumentException("Argument \"order\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("order", order);
    }

    @NonNull
    public ReviewFragmentArgs build() {
      ReviewFragmentArgs result = new ReviewFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setRating(int rating) {
      this.arguments.put("rating", rating);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setOrder(@NonNull Order order) {
      if (order == null) {
        throw new IllegalArgumentException("Argument \"order\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("order", order);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    public int getRating() {
      return (int) arguments.get("rating");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public Order getOrder() {
      return (Order) arguments.get("order");
    }
  }
}
