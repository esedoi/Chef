// Generated by view binder compiler. Do not edit!
package com.paul.chef.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.paul.chef.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentTransactionBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView textView43;

  @NonNull
  public final TextView textView44;

  @NonNull
  public final TextView textView46;

  @NonNull
  public final Button transactionApplyBtn;

  @NonNull
  public final TextView transactionCompletedTxt;

  @NonNull
  public final TextView transactionPenddingTxt;

  @NonNull
  public final TextView transactionProcessingTxt;

  @NonNull
  public final TabLayout transactionTabs;

  @NonNull
  public final ViewPager2 transactionViewpager2;

  private FragmentTransactionBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextView textView43, @NonNull TextView textView44, @NonNull TextView textView46,
      @NonNull Button transactionApplyBtn, @NonNull TextView transactionCompletedTxt,
      @NonNull TextView transactionPenddingTxt, @NonNull TextView transactionProcessingTxt,
      @NonNull TabLayout transactionTabs, @NonNull ViewPager2 transactionViewpager2) {
    this.rootView = rootView;
    this.textView43 = textView43;
    this.textView44 = textView44;
    this.textView46 = textView46;
    this.transactionApplyBtn = transactionApplyBtn;
    this.transactionCompletedTxt = transactionCompletedTxt;
    this.transactionPenddingTxt = transactionPenddingTxt;
    this.transactionProcessingTxt = transactionProcessingTxt;
    this.transactionTabs = transactionTabs;
    this.transactionViewpager2 = transactionViewpager2;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentTransactionBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentTransactionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_transaction_, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentTransactionBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.textView43;
      TextView textView43 = ViewBindings.findChildViewById(rootView, id);
      if (textView43 == null) {
        break missingId;
      }

      id = R.id.textView44;
      TextView textView44 = ViewBindings.findChildViewById(rootView, id);
      if (textView44 == null) {
        break missingId;
      }

      id = R.id.textView46;
      TextView textView46 = ViewBindings.findChildViewById(rootView, id);
      if (textView46 == null) {
        break missingId;
      }

      id = R.id.transaction_apply_btn;
      Button transactionApplyBtn = ViewBindings.findChildViewById(rootView, id);
      if (transactionApplyBtn == null) {
        break missingId;
      }

      id = R.id.transaction_completed_txt;
      TextView transactionCompletedTxt = ViewBindings.findChildViewById(rootView, id);
      if (transactionCompletedTxt == null) {
        break missingId;
      }

      id = R.id.transaction_pendding_txt;
      TextView transactionPenddingTxt = ViewBindings.findChildViewById(rootView, id);
      if (transactionPenddingTxt == null) {
        break missingId;
      }

      id = R.id.transaction_processing_txt;
      TextView transactionProcessingTxt = ViewBindings.findChildViewById(rootView, id);
      if (transactionProcessingTxt == null) {
        break missingId;
      }

      id = R.id.transaction_tabs;
      TabLayout transactionTabs = ViewBindings.findChildViewById(rootView, id);
      if (transactionTabs == null) {
        break missingId;
      }

      id = R.id.transaction_viewpager2;
      ViewPager2 transactionViewpager2 = ViewBindings.findChildViewById(rootView, id);
      if (transactionViewpager2 == null) {
        break missingId;
      }

      return new FragmentTransactionBinding((ConstraintLayout) rootView, textView43, textView44,
          textView46, transactionApplyBtn, transactionCompletedTxt, transactionPenddingTxt,
          transactionProcessingTxt, transactionTabs, transactionViewpager2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
