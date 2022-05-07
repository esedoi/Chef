package com.paul.chef.ui.datePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavDirections;
import com.paul.chef.MobileNavigationDirections;
import com.paul.chef.data.ChefMenu;
import com.paul.chef.data.Dish;
import com.paul.chef.data.Order;
import com.paul.chef.data.ProfileInfo;
import com.paul.chef.data.Review;
import com.paul.chef.data.SelectedDates;
import java.lang.String;

public class DatePickerDirections {
  private DatePickerDirections() {
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
      @NonNull ChefMenu chefMenu) {
    return MobileNavigationDirections.actionGlobalMenuDetailFragment(chefMenu);
  }

  @NonNull
  public static MobileNavigationDirections.ActionGlobalMenuEditFragment actionGlobalMenuEditFragment(
      @Nullable Dish dish) {
    return MobileNavigationDirections.actionGlobalMenuEditFragment(dish);
  }

  @NonNull
  public static NavDirections actionGlobalMenuFragment() {
    return MobileNavigationDirections.actionGlobalMenuFragment();
  }

  @NonNull
  public static MobileNavigationDirections.ActionGlobalBookFragment actionGlobalBookFragment(
      @NonNull ChefMenu chefMenu, @NonNull Dish[] selectedDish) {
    return MobileNavigationDirections.actionGlobalBookFragment(chefMenu, selectedDish);
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
      @NonNull String chefId) {
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
  public static NavDirections actionGlobalAddressListFragment() {
    return MobileNavigationDirections.actionGlobalAddressListFragment();
  }
}
