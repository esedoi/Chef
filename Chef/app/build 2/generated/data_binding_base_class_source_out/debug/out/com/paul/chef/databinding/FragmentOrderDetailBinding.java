// Generated by view binder compiler. Do not edit!
package com.paul.chef.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public final class FragmentOrderDetailBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView imageView3;

  @NonNull
  public final Button orderDetailAcceptBtn;

  @NonNull
  public final TextView orderDetailAddress;

  @NonNull
  public final Button orderDetailCancelBtn;

  @NonNull
  public final TextView orderDetailDate;

  @NonNull
  public final TextView orderDetailDiscount;

  @NonNull
  public final TextView orderDetailFee;

  @NonNull
  public final LinearLayout orderDetailLinear;

  @NonNull
  public final TextView orderDetailMenuName;

  @NonNull
  public final TextView orderDetailName;

  @NonNull
  public final TextView orderDetailNote;

  @NonNull
  public final TextView orderDetailOrginalPrice;

  @NonNull
  public final TextView orderDetailPaymentText;

  @NonNull
  public final TextView orderDetailPeople;

  @NonNull
  public final RatingBar orderDetailRatingBar;

  @NonNull
  public final TextView orderDetailReviewTxt;

  @NonNull
  public final Button orderDetailSendBtn;

  @NonNull
  public final TextView orderDetailStatus;

  @NonNull
  public final TextView orderDetailTime;

  @NonNull
  public final TextView orderDetailTotal;

  @NonNull
  public final TextView orderDetailType;

  @NonNull
  public final TextView textView45;

  @NonNull
  public final TextView textView47;

  @NonNull
  public final TextView textView49;

  @NonNull
  public final TextView textView53;

  @NonNull
  public final TextView textView7;

  private FragmentOrderDetailBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageView imageView3, @NonNull Button orderDetailAcceptBtn,
      @NonNull TextView orderDetailAddress, @NonNull Button orderDetailCancelBtn,
      @NonNull TextView orderDetailDate, @NonNull TextView orderDetailDiscount,
      @NonNull TextView orderDetailFee, @NonNull LinearLayout orderDetailLinear,
      @NonNull TextView orderDetailMenuName, @NonNull TextView orderDetailName,
      @NonNull TextView orderDetailNote, @NonNull TextView orderDetailOrginalPrice,
      @NonNull TextView orderDetailPaymentText, @NonNull TextView orderDetailPeople,
      @NonNull RatingBar orderDetailRatingBar, @NonNull TextView orderDetailReviewTxt,
      @NonNull Button orderDetailSendBtn, @NonNull TextView orderDetailStatus,
      @NonNull TextView orderDetailTime, @NonNull TextView orderDetailTotal,
      @NonNull TextView orderDetailType, @NonNull TextView textView45, @NonNull TextView textView47,
      @NonNull TextView textView49, @NonNull TextView textView53, @NonNull TextView textView7) {
    this.rootView = rootView;
    this.imageView3 = imageView3;
    this.orderDetailAcceptBtn = orderDetailAcceptBtn;
    this.orderDetailAddress = orderDetailAddress;
    this.orderDetailCancelBtn = orderDetailCancelBtn;
    this.orderDetailDate = orderDetailDate;
    this.orderDetailDiscount = orderDetailDiscount;
    this.orderDetailFee = orderDetailFee;
    this.orderDetailLinear = orderDetailLinear;
    this.orderDetailMenuName = orderDetailMenuName;
    this.orderDetailName = orderDetailName;
    this.orderDetailNote = orderDetailNote;
    this.orderDetailOrginalPrice = orderDetailOrginalPrice;
    this.orderDetailPaymentText = orderDetailPaymentText;
    this.orderDetailPeople = orderDetailPeople;
    this.orderDetailRatingBar = orderDetailRatingBar;
    this.orderDetailReviewTxt = orderDetailReviewTxt;
    this.orderDetailSendBtn = orderDetailSendBtn;
    this.orderDetailStatus = orderDetailStatus;
    this.orderDetailTime = orderDetailTime;
    this.orderDetailTotal = orderDetailTotal;
    this.orderDetailType = orderDetailType;
    this.textView45 = textView45;
    this.textView47 = textView47;
    this.textView49 = textView49;
    this.textView53 = textView53;
    this.textView7 = textView7;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentOrderDetailBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentOrderDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_order_detail_, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentOrderDetailBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imageView3;
      ImageView imageView3 = ViewBindings.findChildViewById(rootView, id);
      if (imageView3 == null) {
        break missingId;
      }

      id = R.id.order_detail_accept_btn;
      Button orderDetailAcceptBtn = ViewBindings.findChildViewById(rootView, id);
      if (orderDetailAcceptBtn == null) {
        break missingId;
      }

      id = R.id.order_detail_address;
      TextView orderDetailAddress = ViewBindings.findChildViewById(rootView, id);
      if (orderDetailAddress == null) {
        break missingId;
      }

      id = R.id.order_detail_cancel_btn;
      Button orderDetailCancelBtn = ViewBindings.findChildViewById(rootView, id);
      if (orderDetailCancelBtn == null) {
        break missingId;
      }

      id = R.id.order_detail_date;
      TextView orderDetailDate = ViewBindings.findChildViewById(rootView, id);
      if (orderDetailDate == null) {
        break missingId;
      }

      id = R.id.order_detail_discount;
      TextView orderDetailDiscount = ViewBindings.findChildViewById(rootView, id);
      if (orderDetailDiscount == null) {
        break missingId;
      }

      id = R.id.order_detail_fee;
      TextView orderDetailFee = ViewBindings.findChildViewById(rootView, id);
      if (orderDetailFee == null) {
        break missingId;
      }

      id = R.id.order_detail_linear;
      LinearLayout orderDetailLinear = ViewBindings.findChildViewById(rootView, id);
      if (orderDetailLinear == null) {
        break missingId;
      }

      id = R.id.order_detail_menuName;
      TextView orderDetailMenuName = ViewBindings.findChildViewById(rootView, id);
      if (orderDetailMenuName == null) {
        break missingId;
      }

      id = R.id.order_detail_name;
      TextView orderDetailName = ViewBindings.findChildViewById(rootView, id);
      if (orderDetailName == null) {
        break missingId;
      }

      id = R.id.order_detail_note;
      TextView orderDetailNote = ViewBindings.findChildViewById(rootView, id);
      if (orderDetailNote == null) {
        break missingId;
      }

      id = R.id.order_detail_orginal_price;
      TextView orderDetailOrginalPrice = ViewBindings.findChildViewById(rootView, id);
      if (orderDetailOrginalPrice == null) {
        break missingId;
      }

      id = R.id.order_detail_payment_text;
      TextView orderDetailPaymentText = ViewBindings.findChildViewById(rootView, id);
      if (orderDetailPaymentText == null) {
        break missingId;
      }

      id = R.id.order_detail_people;
      TextView orderDetailPeople = ViewBindings.findChildViewById(rootView, id);
      if (orderDetailPeople == null) {
        break missingId;
      }

      id = R.id.order_detail_rating_bar;
      RatingBar orderDetailRatingBar = ViewBindings.findChildViewById(rootView, id);
      if (orderDetailRatingBar == null) {
        break missingId;
      }

      id = R.id.order_detail_review_txt;
      TextView orderDetailReviewTxt = ViewBindings.findChildViewById(rootView, id);
      if (orderDetailReviewTxt == null) {
        break missingId;
      }

      id = R.id.order_detail_send_btn;
      Button orderDetailSendBtn = ViewBindings.findChildViewById(rootView, id);
      if (orderDetailSendBtn == null) {
        break missingId;
      }

      id = R.id.order_detail_status;
      TextView orderDetailStatus = ViewBindings.findChildViewById(rootView, id);
      if (orderDetailStatus == null) {
        break missingId;
      }

      id = R.id.order_detail_time;
      TextView orderDetailTime = ViewBindings.findChildViewById(rootView, id);
      if (orderDetailTime == null) {
        break missingId;
      }

      id = R.id.order_detail_total;
      TextView orderDetailTotal = ViewBindings.findChildViewById(rootView, id);
      if (orderDetailTotal == null) {
        break missingId;
      }

      id = R.id.order_detail_type;
      TextView orderDetailType = ViewBindings.findChildViewById(rootView, id);
      if (orderDetailType == null) {
        break missingId;
      }

      id = R.id.textView45;
      TextView textView45 = ViewBindings.findChildViewById(rootView, id);
      if (textView45 == null) {
        break missingId;
      }

      id = R.id.textView47;
      TextView textView47 = ViewBindings.findChildViewById(rootView, id);
      if (textView47 == null) {
        break missingId;
      }

      id = R.id.textView49;
      TextView textView49 = ViewBindings.findChildViewById(rootView, id);
      if (textView49 == null) {
        break missingId;
      }

      id = R.id.textView53;
      TextView textView53 = ViewBindings.findChildViewById(rootView, id);
      if (textView53 == null) {
        break missingId;
      }

      id = R.id.textView7;
      TextView textView7 = ViewBindings.findChildViewById(rootView, id);
      if (textView7 == null) {
        break missingId;
      }

      return new FragmentOrderDetailBinding((ConstraintLayout) rootView, imageView3,
          orderDetailAcceptBtn, orderDetailAddress, orderDetailCancelBtn, orderDetailDate,
          orderDetailDiscount, orderDetailFee, orderDetailLinear, orderDetailMenuName,
          orderDetailName, orderDetailNote, orderDetailOrginalPrice, orderDetailPaymentText,
          orderDetailPeople, orderDetailRatingBar, orderDetailReviewTxt, orderDetailSendBtn,
          orderDetailStatus, orderDetailTime, orderDetailTotal, orderDetailType, textView45,
          textView47, textView49, textView53, textView7);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}