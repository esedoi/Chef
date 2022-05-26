package com.paul.chef.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavDirections;
import com.paul.chef.MobileNavigationDirections;
import com.paul.chef.data.Dish;
import com.paul.chef.data.Menu;
import com.paul.chef.data.Order;
import com.paul.chef.data.ProfileInfo;
import com.paul.chef.data.Review;
import com.paul.chef.data.SelectedDates;
import java.lang.String;

public class LoginFragmentDirections {
  private LoginFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionGlobalNavigationHome() {
    return MobileNavigationDirections.actionGlobalNavigationHome();
  }

  @NonNull
  public static MobileNavigationDirections.ActionGlobalChefEditFragment actionGlobalChefEditFragment(
      int editType, @NonNull ProfileInfo profile) {
    return MobileNavigationDirections.actionGlobalChefEditFragment(editType, profile);
  }

  @NonNull
  public static NavDirections actionGlobalChefFragment() {
    return MobileNavigationDirections.actionGlobalChefFragment();
  }

  @NonNull
  public static MobileNavigationDirections.ActionGlobalMenuDetailFragment actionGlobalMenuDetailFragment(
      @NonNull Menu menu) {
    return MobileNavigationDirections.actionGlobalMenuDetailFragment(menu);
  }

  @NonNull
  public static MobileNavigationDirections.ActionGlobalMenuEditFragment actionGlobalMenuEditFragment(
      @Nullable Menu menu) {
    return MobileNavigationDirections.actionGlobalMenuEditFragment(menu);
  }

  @NonNull
  public static NavDirections actionGlobalMenuFragment() {
    return MobileNavigationDirections.actionGlobalMenuFragment();
  }

  @NonNull
  public static MobileNavigationDirections.ActionGlobalBookFragment actionGlobalBookFragment(
      @NonNull Menu menu, @NonNull Dish[] selectedDish, int type) {
    return MobileNavigationDirections.actionGlobalBookFragment(menu, selectedDish, type);
  }

  @NonNull
  public static NavDirections actionGlobalCalendar() {
    return MobileNavigationDirections.actionGlobalCalendar();
  }

  @NonNull
  public static MobileNavigationDirections.ActionGlobalCalendarSetting actionGlobalCalendarSetting(
      @NonNull SelectedDates selectedDates) {
    return MobileNavigationDirections.actionGlobalCalendarSetting(selectedDates);
  }

  @NonNull
  public static NavDirections actionGlobalBookSetting() {
    return MobileNavigationDirections.actionGlobalBookSetting();
  }

  @NonNull
  public static MobileNavigationDirections.ActionGlobalDatePicker3 actionGlobalDatePicker3(
      @Nullable String chefId) {
    return MobileNavigationDirections.actionGlobalDatePicker3(chefId);
  }

  @NonNull
  public static NavDirections actionGlobalOrderManageFragment() {
    return MobileNavigationDirections.actionGlobalOrderManageFragment();
  }

  @NonNull
  public static MobileNavigationDirections.ActionGlobalOrderDetailFragment actionGlobalOrderDetailFragment(
      @NonNull Order order) {
    return MobileNavigationDirections.actionGlobalOrderDetailFragment(order);
  }

  @NonNull
  public static MobileNavigationDirections.ActionGlobalPickerBottomSheet actionGlobalPickerBottomSheet(
      int pickerType, @Nullable String chefId) {
    return MobileNavigationDirections.actionGlobalPickerBottomSheet(pickerType, chefId);
  }

  @NonNull
  public static MobileNavigationDirections.ActionGlobalChatRoomFragment actionGlobalChatRoomFragment(
      @NonNull String roomId) {
    return MobileNavigationDirections.actionGlobalChatRoomFragment(roomId);
  }

  @NonNull
  public static MobileNavigationDirections.ActionGlobalReviewFragment actionGlobalReviewFragment(
      int rating, @NonNull Order order) {
    return MobileNavigationDirections.actionGlobalReviewFragment(rating, order);
  }

  @NonNull
  public static MobileNavigationDirections.ActionGlobalReviewPage actionGlobalReviewPage(
      @NonNull Review[] review) {
    return MobileNavigationDirections.actionGlobalReviewPage(review);
  }

  @NonNull
  public static MobileNavigationDirections.ActionGlobalImageUploadFragment actionGlobalImageUploadFragment(
      int imageType) {
    return MobileNavigationDirections.actionGlobalImageUploadFragment(imageType);
  }

  @NonNull
  public static NavDirections actionGlobalAddAddressFragment() {
    return MobileNavigationDirections.actionGlobalAddAddressFragment();
  }

  @NonNull
  public static MobileNavigationDirections.ActionGlobalAddressListFragment actionGlobalAddressListFragment(
      int listType) {
    return MobileNavigationDirections.actionGlobalAddressListFragment(listType);
  }

  @NonNull
  public static NavDirections actionGlobalAddTagFragment() {
    return MobileNavigationDirections.actionGlobalAddTagFragment();
  }

  @NonNull
  public static NavDirections actionGlobalLoginFragment() {
    return MobileNavigationDirections.actionGlobalLoginFragment();
  }

  @NonNull
  public static NavDirections actionGlobalFilterFragment() {
    return MobileNavigationDirections.actionGlobalFilterFragment();
  }

  @NonNull
  public static MobileNavigationDirections.ActionGlobalDisplayChefFragment actionGlobalDisplayChefFragment(
      @NonNull String chefId) {
    return MobileNavigationDirections.actionGlobalDisplayChefFragment(chefId);
  }

  @NonNull
  public static NavDirections actionGlobalTermsFragment() {
    return MobileNavigationDirections.actionGlobalTermsFragment();
  }

  @NonNull
  public static MobileNavigationDirections.ActionGlobalDoneFragment actionGlobalDoneFragment(
      @NonNull String direction) {
    return MobileNavigationDirections.actionGlobalDoneFragment(direction);
  }
}
