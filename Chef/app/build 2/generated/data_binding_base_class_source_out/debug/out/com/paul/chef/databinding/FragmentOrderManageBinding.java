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
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.paul.chef.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentOrderManageBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TabLayout tabs;

  @NonNull
  public final TextView textView24;

  @NonNull
  public final ViewPager2 viewpager2;

  private FragmentOrderManageBinding(@NonNull ConstraintLayout rootView, @NonNull TabLayout tabs,
      @NonNull TextView textView24, @NonNull ViewPager2 viewpager2) {
    this.rootView = rootView;
    this.tabs = tabs;
    this.textView24 = textView24;
    this.viewpager2 = viewpager2;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentOrderManageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentOrderManageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_order_manage, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentOrderManageBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.tabs;
      TabLayout tabs = ViewBindings.findChildViewById(rootView, id);
      if (tabs == null) {
        break missingId;
      }

      id = R.id.order_manage_title;
      TextView textView24 = ViewBindings.findChildViewById(rootView, id);
      if (textView24 == null) {
        break missingId;
      }

      id = R.id.viewpager2;
      ViewPager2 viewpager2 = ViewBindings.findChildViewById(rootView, id);
      if (viewpager2 == null) {
        break missingId;
      }

      return new FragmentOrderManageBinding((ConstraintLayout) rootView, tabs, textView24,
          viewpager2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
