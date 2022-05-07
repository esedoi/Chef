// Generated by view binder compiler. Do not edit!
package com.paul.chef.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.paul.chef.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentChefPageBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button bookSettingBtn;

  @NonNull
  public final TextView chefIntro;

  @NonNull
  public final TextView chefName;

  @NonNull
  public final Button chefPageAddressListBtn;

  @NonNull
  public final ImageView chefPageImgView;

  @NonNull
  public final Button chefPageLogout;

  @NonNull
  public final RecyclerView chefPageMenuRecycler;

  @NonNull
  public final TextView chefPageMenuTxt;

  @NonNull
  public final TextView chefPageReviewDown;

  @NonNull
  public final Button chefPageReviewMore;

  @NonNull
  public final RecyclerView chefPageReviewRecycler;

  @NonNull
  public final Button createMenu;

  @NonNull
  public final View divider;

  @NonNull
  public final View divider11;

  @NonNull
  public final View divider16;

  @NonNull
  public final View divider2;

  @NonNull
  public final View divider3;

  @NonNull
  public final View divider7;

  @NonNull
  public final Button editProfileBtn;

  @NonNull
  public final TextView textView32;

  @NonNull
  public final TextView textView5;

  @NonNull
  public final Button turnToUser;

  private FragmentChefPageBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button bookSettingBtn, @NonNull TextView chefIntro, @NonNull TextView chefName,
      @NonNull Button chefPageAddressListBtn, @NonNull ImageView chefPageImgView,
      @NonNull Button chefPageLogout, @NonNull RecyclerView chefPageMenuRecycler,
      @NonNull TextView chefPageMenuTxt, @NonNull TextView chefPageReviewDown,
      @NonNull Button chefPageReviewMore, @NonNull RecyclerView chefPageReviewRecycler,
      @NonNull Button createMenu, @NonNull View divider, @NonNull View divider11,
      @NonNull View divider16, @NonNull View divider2, @NonNull View divider3,
      @NonNull View divider7, @NonNull Button editProfileBtn, @NonNull TextView textView32,
      @NonNull TextView textView5, @NonNull Button turnToUser) {
    this.rootView = rootView;
    this.bookSettingBtn = bookSettingBtn;
    this.chefIntro = chefIntro;
    this.chefName = chefName;
    this.chefPageAddressListBtn = chefPageAddressListBtn;
    this.chefPageImgView = chefPageImgView;
    this.chefPageLogout = chefPageLogout;
    this.chefPageMenuRecycler = chefPageMenuRecycler;
    this.chefPageMenuTxt = chefPageMenuTxt;
    this.chefPageReviewDown = chefPageReviewDown;
    this.chefPageReviewMore = chefPageReviewMore;
    this.chefPageReviewRecycler = chefPageReviewRecycler;
    this.createMenu = createMenu;
    this.divider = divider;
    this.divider11 = divider11;
    this.divider16 = divider16;
    this.divider2 = divider2;
    this.divider3 = divider3;
    this.divider7 = divider7;
    this.editProfileBtn = editProfileBtn;
    this.textView32 = textView32;
    this.textView5 = textView5;
    this.turnToUser = turnToUser;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentChefPageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentChefPageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_chef_page, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentChefPageBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bookSettingBtn;
      Button bookSettingBtn = ViewBindings.findChildViewById(rootView, id);
      if (bookSettingBtn == null) {
        break missingId;
      }

      id = R.id.chefIntro;
      TextView chefIntro = ViewBindings.findChildViewById(rootView, id);
      if (chefIntro == null) {
        break missingId;
      }

      id = R.id.chefName;
      TextView chefName = ViewBindings.findChildViewById(rootView, id);
      if (chefName == null) {
        break missingId;
      }

      id = R.id.chef_page_address_list_btn;
      Button chefPageAddressListBtn = ViewBindings.findChildViewById(rootView, id);
      if (chefPageAddressListBtn == null) {
        break missingId;
      }

      id = R.id.chef_page_img_view;
      ImageView chefPageImgView = ViewBindings.findChildViewById(rootView, id);
      if (chefPageImgView == null) {
        break missingId;
      }

      id = R.id.chef_page_logout;
      Button chefPageLogout = ViewBindings.findChildViewById(rootView, id);
      if (chefPageLogout == null) {
        break missingId;
      }

      id = R.id.chef_page_menu_recycler;
      RecyclerView chefPageMenuRecycler = ViewBindings.findChildViewById(rootView, id);
      if (chefPageMenuRecycler == null) {
        break missingId;
      }

      id = R.id.chef_page_menu_txt;
      TextView chefPageMenuTxt = ViewBindings.findChildViewById(rootView, id);
      if (chefPageMenuTxt == null) {
        break missingId;
      }

      id = R.id.chef_page_review_down;
      TextView chefPageReviewDown = ViewBindings.findChildViewById(rootView, id);
      if (chefPageReviewDown == null) {
        break missingId;
      }

      id = R.id.chef_page_review_more;
      Button chefPageReviewMore = ViewBindings.findChildViewById(rootView, id);
      if (chefPageReviewMore == null) {
        break missingId;
      }

      id = R.id.chef_page_review_recycler;
      RecyclerView chefPageReviewRecycler = ViewBindings.findChildViewById(rootView, id);
      if (chefPageReviewRecycler == null) {
        break missingId;
      }

      id = R.id.createMenu;
      Button createMenu = ViewBindings.findChildViewById(rootView, id);
      if (createMenu == null) {
        break missingId;
      }

      id = R.id.divider;
      View divider = ViewBindings.findChildViewById(rootView, id);
      if (divider == null) {
        break missingId;
      }

      id = R.id.divider11;
      View divider11 = ViewBindings.findChildViewById(rootView, id);
      if (divider11 == null) {
        break missingId;
      }

      id = R.id.divider16;
      View divider16 = ViewBindings.findChildViewById(rootView, id);
      if (divider16 == null) {
        break missingId;
      }

      id = R.id.divider2;
      View divider2 = ViewBindings.findChildViewById(rootView, id);
      if (divider2 == null) {
        break missingId;
      }

      id = R.id.divider3;
      View divider3 = ViewBindings.findChildViewById(rootView, id);
      if (divider3 == null) {
        break missingId;
      }

      id = R.id.divider7;
      View divider7 = ViewBindings.findChildViewById(rootView, id);
      if (divider7 == null) {
        break missingId;
      }

      id = R.id.editProfileBtn;
      Button editProfileBtn = ViewBindings.findChildViewById(rootView, id);
      if (editProfileBtn == null) {
        break missingId;
      }

      id = R.id.textView32;
      TextView textView32 = ViewBindings.findChildViewById(rootView, id);
      if (textView32 == null) {
        break missingId;
      }

      id = R.id.textView5;
      TextView textView5 = ViewBindings.findChildViewById(rootView, id);
      if (textView5 == null) {
        break missingId;
      }

      id = R.id.turnToUser;
      Button turnToUser = ViewBindings.findChildViewById(rootView, id);
      if (turnToUser == null) {
        break missingId;
      }

      return new FragmentChefPageBinding((ConstraintLayout) rootView, bookSettingBtn, chefIntro,
          chefName, chefPageAddressListBtn, chefPageImgView, chefPageLogout, chefPageMenuRecycler,
          chefPageMenuTxt, chefPageReviewDown, chefPageReviewMore, chefPageReviewRecycler,
          createMenu, divider, divider11, divider16, divider2, divider3, divider7, editProfileBtn,
          textView32, textView5, turnToUser);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
