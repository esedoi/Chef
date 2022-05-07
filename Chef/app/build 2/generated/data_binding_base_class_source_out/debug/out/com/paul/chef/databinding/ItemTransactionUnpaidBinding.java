// Generated by view binder compiler. Do not edit!
package com.paul.chef.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class ItemTransactionUnpaidBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final View divider5;

  @NonNull
  public final TextView unpaidChefReceive;

  @NonNull
  public final TextView unpaidDate;

  @NonNull
  public final TextView unpaidName;

  @NonNull
  public final TextView unpaidPeople;

  private ItemTransactionUnpaidBinding(@NonNull ConstraintLayout rootView, @NonNull View divider5,
      @NonNull TextView unpaidChefReceive, @NonNull TextView unpaidDate,
      @NonNull TextView unpaidName, @NonNull TextView unpaidPeople) {
    this.rootView = rootView;
    this.divider5 = divider5;
    this.unpaidChefReceive = unpaidChefReceive;
    this.unpaidDate = unpaidDate;
    this.unpaidName = unpaidName;
    this.unpaidPeople = unpaidPeople;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemTransactionUnpaidBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemTransactionUnpaidBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_transaction_unpaid, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemTransactionUnpaidBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.divider5;
      View divider5 = ViewBindings.findChildViewById(rootView, id);
      if (divider5 == null) {
        break missingId;
      }

      id = R.id.unpaid_chef_receive;
      TextView unpaidChefReceive = ViewBindings.findChildViewById(rootView, id);
      if (unpaidChefReceive == null) {
        break missingId;
      }

      id = R.id.unpaid_date;
      TextView unpaidDate = ViewBindings.findChildViewById(rootView, id);
      if (unpaidDate == null) {
        break missingId;
      }

      id = R.id.unpaid_name;
      TextView unpaidName = ViewBindings.findChildViewById(rootView, id);
      if (unpaidName == null) {
        break missingId;
      }

      id = R.id.unpaid_people;
      TextView unpaidPeople = ViewBindings.findChildViewById(rootView, id);
      if (unpaidPeople == null) {
        break missingId;
      }

      return new ItemTransactionUnpaidBinding((ConstraintLayout) rootView, divider5,
          unpaidChefReceive, unpaidDate, unpaidName, unpaidPeople);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
