// Generated by view binder compiler. Do not edit!
package com.paul.chef.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.paul.chef.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class CalendarDayLegendBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout legendLayout;

  @NonNull
  public final TextView legendText1;

  @NonNull
  public final TextView legendText2;

  @NonNull
  public final TextView legendText3;

  @NonNull
  public final TextView legendText4;

  @NonNull
  public final TextView legendText5;

  @NonNull
  public final TextView legendText6;

  @NonNull
  public final TextView legendText7;

  private CalendarDayLegendBinding(@NonNull LinearLayout rootView,
      @NonNull LinearLayout legendLayout, @NonNull TextView legendText1,
      @NonNull TextView legendText2, @NonNull TextView legendText3, @NonNull TextView legendText4,
      @NonNull TextView legendText5, @NonNull TextView legendText6, @NonNull TextView legendText7) {
    this.rootView = rootView;
    this.legendLayout = legendLayout;
    this.legendText1 = legendText1;
    this.legendText2 = legendText2;
    this.legendText3 = legendText3;
    this.legendText4 = legendText4;
    this.legendText5 = legendText5;
    this.legendText6 = legendText6;
    this.legendText7 = legendText7;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CalendarDayLegendBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CalendarDayLegendBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.calendar_day_legend, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CalendarDayLegendBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      LinearLayout legendLayout = (LinearLayout) rootView;

      id = R.id.legendText1;
      TextView legendText1 = ViewBindings.findChildViewById(rootView, id);
      if (legendText1 == null) {
        break missingId;
      }

      id = R.id.legendText2;
      TextView legendText2 = ViewBindings.findChildViewById(rootView, id);
      if (legendText2 == null) {
        break missingId;
      }

      id = R.id.legendText3;
      TextView legendText3 = ViewBindings.findChildViewById(rootView, id);
      if (legendText3 == null) {
        break missingId;
      }

      id = R.id.legendText4;
      TextView legendText4 = ViewBindings.findChildViewById(rootView, id);
      if (legendText4 == null) {
        break missingId;
      }

      id = R.id.legendText5;
      TextView legendText5 = ViewBindings.findChildViewById(rootView, id);
      if (legendText5 == null) {
        break missingId;
      }

      id = R.id.legendText6;
      TextView legendText6 = ViewBindings.findChildViewById(rootView, id);
      if (legendText6 == null) {
        break missingId;
      }

      id = R.id.legendText7;
      TextView legendText7 = ViewBindings.findChildViewById(rootView, id);
      if (legendText7 == null) {
        break missingId;
      }

      return new CalendarDayLegendBinding((LinearLayout) rootView, legendLayout, legendText1,
          legendText2, legendText3, legendText4, legendText5, legendText6, legendText7);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
