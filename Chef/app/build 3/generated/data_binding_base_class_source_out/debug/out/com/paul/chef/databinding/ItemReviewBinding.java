// Generated by view binder compiler. Do not edit!
package com.paul.chef.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.paul.chef.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemReviewBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView itemReviewAvatar;

  @NonNull
  public final TextView itemReviewContent;

  @NonNull
  public final TextView itemReviewDate;

  @NonNull
  public final ImageButton itemReviewMore;

  @NonNull
  public final TextView itemReviewUserName;

  @NonNull
  public final RatingBar ratingBar3;

  private ItemReviewBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView itemReviewAvatar,
      @NonNull TextView itemReviewContent, @NonNull TextView itemReviewDate,
      @NonNull ImageButton itemReviewMore, @NonNull TextView itemReviewUserName,
      @NonNull RatingBar ratingBar3) {
    this.rootView = rootView;
    this.itemReviewAvatar = itemReviewAvatar;
    this.itemReviewContent = itemReviewContent;
    this.itemReviewDate = itemReviewDate;
    this.itemReviewMore = itemReviewMore;
    this.itemReviewUserName = itemReviewUserName;
    this.ratingBar3 = ratingBar3;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemReviewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemReviewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_review, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemReviewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.item_review_avatar;
      ImageView itemReviewAvatar = ViewBindings.findChildViewById(rootView, id);
      if (itemReviewAvatar == null) {
        break missingId;
      }

      id = R.id.item_review_content;
      TextView itemReviewContent = ViewBindings.findChildViewById(rootView, id);
      if (itemReviewContent == null) {
        break missingId;
      }

      id = R.id.item_review_date;
      TextView itemReviewDate = ViewBindings.findChildViewById(rootView, id);
      if (itemReviewDate == null) {
        break missingId;
      }

      id = R.id.item_review_more;
      ImageButton itemReviewMore = ViewBindings.findChildViewById(rootView, id);
      if (itemReviewMore == null) {
        break missingId;
      }

      id = R.id.item_review_user_name;
      TextView itemReviewUserName = ViewBindings.findChildViewById(rootView, id);
      if (itemReviewUserName == null) {
        break missingId;
      }

      id = R.id.ratingBar3;
      RatingBar ratingBar3 = ViewBindings.findChildViewById(rootView, id);
      if (ratingBar3 == null) {
        break missingId;
      }

      return new ItemReviewBinding((ConstraintLayout) rootView, itemReviewAvatar, itemReviewContent,
          itemReviewDate, itemReviewMore, itemReviewUserName, ratingBar3);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
