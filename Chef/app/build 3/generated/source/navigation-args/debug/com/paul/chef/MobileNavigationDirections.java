package com.paul.chef;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.paul.chef.data.Dish;
import com.paul.chef.data.Menu;
import com.paul.chef.data.Order;
import com.paul.chef.data.ProfileInfo;
import com.paul.chef.data.Review;
import com.paul.chef.data.SelectedDates;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class MobileNavigationDirections {
  private MobileNavigationDirections() {
  }

  @NonNull
  public static NavDirections actionGlobalNavigationHome() {
    return new ActionOnlyNavDirections(R.id.action_global_navigation_home);
  }

  @NonNull
  public static ActionGlobalChefEditFragment actionGlobalChefEditFragment(int editType,
      @NonNull ProfileInfo profile) {
    return new ActionGlobalChefEditFragment(editType, profile);
  }

  @NonNull
  public static NavDirections actionGlobalChefFragment() {
    return new ActionOnlyNavDirections(R.id.action_global_chefFragment);
  }

  @NonNull
  public static ActionGlobalMenuDetailFragment actionGlobalMenuDetailFragment(@NonNull Menu menu) {
    return new ActionGlobalMenuDetailFragment(menu);
  }

  @NonNull
  public static ActionGlobalMenuEditFragment actionGlobalMenuEditFragment(@Nullable Menu menu) {
    return new ActionGlobalMenuEditFragment(menu);
  }

  @NonNull
  public static NavDirections actionGlobalMenuFragment() {
    return new ActionOnlyNavDirections(R.id.action_global_menuFragment);
  }

  @NonNull
  public static ActionGlobalBookFragment actionGlobalBookFragment(@NonNull Menu menu,
      @NonNull Dish[] selectedDish, int type) {
    return new ActionGlobalBookFragment(menu, selectedDish, type);
  }

  @NonNull
  public static NavDirections actionGlobalCalendar() {
    return new ActionOnlyNavDirections(R.id.action_global_calendar);
  }

  @NonNull
  public static ActionGlobalCalendarSetting actionGlobalCalendarSetting(
      @NonNull SelectedDates selectedDates) {
    return new ActionGlobalCalendarSetting(selectedDates);
  }

  @NonNull
  public static NavDirections actionGlobalBookSetting() {
    return new ActionOnlyNavDirections(R.id.action_global_bookSetting);
  }

  @NonNull
  public static ActionGlobalDatePicker3 actionGlobalDatePicker3(@Nullable String chefId) {
    return new ActionGlobalDatePicker3(chefId);
  }

  @NonNull
  public static NavDirections actionGlobalOrderManageFragment() {
    return new ActionOnlyNavDirections(R.id.action_global_orderManageFragment);
  }

  @NonNull
  public static ActionGlobalOrderDetailFragment actionGlobalOrderDetailFragment(
      @NonNull Order order) {
    return new ActionGlobalOrderDetailFragment(order);
  }

  @NonNull
  public static ActionGlobalPickerBottomSheet actionGlobalPickerBottomSheet(int pickerType,
      @Nullable String chefId) {
    return new ActionGlobalPickerBottomSheet(pickerType, chefId);
  }

  @NonNull
  public static ActionGlobalChatRoomFragment actionGlobalChatRoomFragment(@NonNull String roomId) {
    return new ActionGlobalChatRoomFragment(roomId);
  }

  @NonNull
  public static ActionGlobalReviewFragment actionGlobalReviewFragment(int rating,
      @NonNull Order order) {
    return new ActionGlobalReviewFragment(rating, order);
  }

  @NonNull
  public static ActionGlobalReviewPage actionGlobalReviewPage(@NonNull Review[] review) {
    return new ActionGlobalReviewPage(review);
  }

  @NonNull
  public static ActionGlobalImageUploadFragment actionGlobalImageUploadFragment(int imageType) {
    return new ActionGlobalImageUploadFragment(imageType);
  }

  @NonNull
  public static NavDirections actionGlobalAddAddressFragment() {
    return new ActionOnlyNavDirections(R.id.action_global_addAddressFragment);
  }

  @NonNull
  public static ActionGlobalAddressListFragment actionGlobalAddressListFragment(int listType) {
    return new ActionGlobalAddressListFragment(listType);
  }

  @NonNull
  public static NavDirections actionGlobalAddTagFragment() {
    return new ActionOnlyNavDirections(R.id.action_global_addTagFragment);
  }

  @NonNull
  public static NavDirections actionGlobalLoginFragment() {
    return new ActionOnlyNavDirections(R.id.action_global_loginFragment);
  }

  @NonNull
  public static NavDirections actionGlobalFilterFragment() {
    return new ActionOnlyNavDirections(R.id.action_global_filterFragment);
  }

  @NonNull
  public static ActionGlobalDisplayChefFragment actionGlobalDisplayChefFragment(
      @NonNull String chefId) {
    return new ActionGlobalDisplayChefFragment(chefId);
  }

  @NonNull
  public static NavDirections actionGlobalTermsFragment() {
    return new ActionOnlyNavDirections(R.id.action_global_termsFragment);
  }

  @NonNull
  public static ActionGlobalDoneFragment actionGlobalDoneFragment(@NonNull String direction) {
    return new ActionGlobalDoneFragment(direction);
  }

  public static class ActionGlobalChefEditFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionGlobalChefEditFragment(int editType, @NonNull ProfileInfo profile) {
      this.arguments.put("editType", editType);
      if (profile == null) {
        throw new IllegalArgumentException("Argument \"profile\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("profile", profile);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionGlobalChefEditFragment setEditType(int editType) {
      this.arguments.put("editType", editType);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionGlobalChefEditFragment setProfile(@NonNull ProfileInfo profile) {
      if (profile == null) {
        throw new IllegalArgumentException("Argument \"profile\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("profile", profile);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("editType")) {
        int editType = (int) arguments.get("editType");
        __result.putInt("editType", editType);
      }
      if (arguments.containsKey("profile")) {
        ProfileInfo profile = (ProfileInfo) arguments.get("profile");
        if (Parcelable.class.isAssignableFrom(ProfileInfo.class) || profile == null) {
          __result.putParcelable("profile", Parcelable.class.cast(profile));
        } else if (Serializable.class.isAssignableFrom(ProfileInfo.class)) {
          __result.putSerializable("profile", Serializable.class.cast(profile));
        } else {
          throw new UnsupportedOperationException(ProfileInfo.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
        }
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_global_chefEditFragment;
    }

    @SuppressWarnings("unchecked")
    public int getEditType() {
      return (int) arguments.get("editType");
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public ProfileInfo getProfile() {
      return (ProfileInfo) arguments.get("profile");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionGlobalChefEditFragment that = (ActionGlobalChefEditFragment) object;
      if (arguments.containsKey("editType") != that.arguments.containsKey("editType")) {
        return false;
      }
      if (getEditType() != that.getEditType()) {
        return false;
      }
      if (arguments.containsKey("profile") != that.arguments.containsKey("profile")) {
        return false;
      }
      if (getProfile() != null ? !getProfile().equals(that.getProfile()) : that.getProfile() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + getEditType();
      result = 31 * result + (getProfile() != null ? getProfile().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionGlobalChefEditFragment(actionId=" + getActionId() + "){"
          + "editType=" + getEditType()
          + ", profile=" + getProfile()
          + "}";
    }
  }

  public static class ActionGlobalMenuDetailFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionGlobalMenuDetailFragment(@NonNull Menu menu) {
      if (menu == null) {
        throw new IllegalArgumentException("Argument \"menu\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("menu", menu);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionGlobalMenuDetailFragment setMenu(@NonNull Menu menu) {
      if (menu == null) {
        throw new IllegalArgumentException("Argument \"menu\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("menu", menu);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("menu")) {
        Menu menu = (Menu) arguments.get("menu");
        if (Parcelable.class.isAssignableFrom(Menu.class) || menu == null) {
          __result.putParcelable("menu", Parcelable.class.cast(menu));
        } else if (Serializable.class.isAssignableFrom(Menu.class)) {
          __result.putSerializable("menu", Serializable.class.cast(menu));
        } else {
          throw new UnsupportedOperationException(Menu.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
        }
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_global_menuDetailFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public Menu getMenu() {
      return (Menu) arguments.get("menu");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionGlobalMenuDetailFragment that = (ActionGlobalMenuDetailFragment) object;
      if (arguments.containsKey("menu") != that.arguments.containsKey("menu")) {
        return false;
      }
      if (getMenu() != null ? !getMenu().equals(that.getMenu()) : that.getMenu() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getMenu() != null ? getMenu().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionGlobalMenuDetailFragment(actionId=" + getActionId() + "){"
          + "menu=" + getMenu()
          + "}";
    }
  }

  public static class ActionGlobalMenuEditFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionGlobalMenuEditFragment(@Nullable Menu menu) {
      this.arguments.put("menu", menu);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionGlobalMenuEditFragment setMenu(@Nullable Menu menu) {
      this.arguments.put("menu", menu);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("menu")) {
        Menu menu = (Menu) arguments.get("menu");
        if (Parcelable.class.isAssignableFrom(Menu.class) || menu == null) {
          __result.putParcelable("menu", Parcelable.class.cast(menu));
        } else if (Serializable.class.isAssignableFrom(Menu.class)) {
          __result.putSerializable("menu", Serializable.class.cast(menu));
        } else {
          throw new UnsupportedOperationException(Menu.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
        }
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_global_menuEditFragment;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public Menu getMenu() {
      return (Menu) arguments.get("menu");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionGlobalMenuEditFragment that = (ActionGlobalMenuEditFragment) object;
      if (arguments.containsKey("menu") != that.arguments.containsKey("menu")) {
        return false;
      }
      if (getMenu() != null ? !getMenu().equals(that.getMenu()) : that.getMenu() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getMenu() != null ? getMenu().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionGlobalMenuEditFragment(actionId=" + getActionId() + "){"
          + "menu=" + getMenu()
          + "}";
    }
  }

  public static class ActionGlobalBookFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionGlobalBookFragment(@NonNull Menu menu, @NonNull Dish[] selectedDish, int type) {
      if (menu == null) {
        throw new IllegalArgumentException("Argument \"menu\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("menu", menu);
      if (selectedDish == null) {
        throw new IllegalArgumentException("Argument \"selectedDish\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("selectedDish", selectedDish);
      this.arguments.put("type", type);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionGlobalBookFragment setMenu(@NonNull Menu menu) {
      if (menu == null) {
        throw new IllegalArgumentException("Argument \"menu\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("menu", menu);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionGlobalBookFragment setSelectedDish(@NonNull Dish[] selectedDish) {
      if (selectedDish == null) {
        throw new IllegalArgumentException("Argument \"selectedDish\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("selectedDish", selectedDish);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionGlobalBookFragment setType(int type) {
      this.arguments.put("type", type);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("menu")) {
        Menu menu = (Menu) arguments.get("menu");
        if (Parcelable.class.isAssignableFrom(Menu.class) || menu == null) {
          __result.putParcelable("menu", Parcelable.class.cast(menu));
        } else if (Serializable.class.isAssignableFrom(Menu.class)) {
          __result.putSerializable("menu", Serializable.class.cast(menu));
        } else {
          throw new UnsupportedOperationException(Menu.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
        }
      }
      if (arguments.containsKey("selectedDish")) {
        Dish[] selectedDish = (Dish[]) arguments.get("selectedDish");
        __result.putParcelableArray("selectedDish", selectedDish);
      }
      if (arguments.containsKey("type")) {
        int type = (int) arguments.get("type");
        __result.putInt("type", type);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_global_bookFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public Menu getMenu() {
      return (Menu) arguments.get("menu");
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public Dish[] getSelectedDish() {
      return (Dish[]) arguments.get("selectedDish");
    }

    @SuppressWarnings("unchecked")
    public int getType() {
      return (int) arguments.get("type");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionGlobalBookFragment that = (ActionGlobalBookFragment) object;
      if (arguments.containsKey("menu") != that.arguments.containsKey("menu")) {
        return false;
      }
      if (getMenu() != null ? !getMenu().equals(that.getMenu()) : that.getMenu() != null) {
        return false;
      }
      if (arguments.containsKey("selectedDish") != that.arguments.containsKey("selectedDish")) {
        return false;
      }
      if (getSelectedDish() != null ? !getSelectedDish().equals(that.getSelectedDish()) : that.getSelectedDish() != null) {
        return false;
      }
      if (arguments.containsKey("type") != that.arguments.containsKey("type")) {
        return false;
      }
      if (getType() != that.getType()) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getMenu() != null ? getMenu().hashCode() : 0);
      result = 31 * result + java.util.Arrays.hashCode(getSelectedDish());
      result = 31 * result + getType();
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionGlobalBookFragment(actionId=" + getActionId() + "){"
          + "menu=" + getMenu()
          + ", selectedDish=" + getSelectedDish()
          + ", type=" + getType()
          + "}";
    }
  }

  public static class ActionGlobalCalendarSetting implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionGlobalCalendarSetting(@NonNull SelectedDates selectedDates) {
      if (selectedDates == null) {
        throw new IllegalArgumentException("Argument \"selectedDates\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("selectedDates", selectedDates);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionGlobalCalendarSetting setSelectedDates(@NonNull SelectedDates selectedDates) {
      if (selectedDates == null) {
        throw new IllegalArgumentException("Argument \"selectedDates\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("selectedDates", selectedDates);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("selectedDates")) {
        SelectedDates selectedDates = (SelectedDates) arguments.get("selectedDates");
        if (Parcelable.class.isAssignableFrom(SelectedDates.class) || selectedDates == null) {
          __result.putParcelable("selectedDates", Parcelable.class.cast(selectedDates));
        } else if (Serializable.class.isAssignableFrom(SelectedDates.class)) {
          __result.putSerializable("selectedDates", Serializable.class.cast(selectedDates));
        } else {
          throw new UnsupportedOperationException(SelectedDates.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
        }
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_global_calendarSetting;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public SelectedDates getSelectedDates() {
      return (SelectedDates) arguments.get("selectedDates");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionGlobalCalendarSetting that = (ActionGlobalCalendarSetting) object;
      if (arguments.containsKey("selectedDates") != that.arguments.containsKey("selectedDates")) {
        return false;
      }
      if (getSelectedDates() != null ? !getSelectedDates().equals(that.getSelectedDates()) : that.getSelectedDates() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getSelectedDates() != null ? getSelectedDates().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionGlobalCalendarSetting(actionId=" + getActionId() + "){"
          + "selectedDates=" + getSelectedDates()
          + "}";
    }
  }

  public static class ActionGlobalDatePicker3 implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionGlobalDatePicker3(@Nullable String chefId) {
      this.arguments.put("chefId", chefId);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionGlobalDatePicker3 setChefId(@Nullable String chefId) {
      this.arguments.put("chefId", chefId);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("chefId")) {
        String chefId = (String) arguments.get("chefId");
        __result.putString("chefId", chefId);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_global_datePicker3;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public String getChefId() {
      return (String) arguments.get("chefId");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionGlobalDatePicker3 that = (ActionGlobalDatePicker3) object;
      if (arguments.containsKey("chefId") != that.arguments.containsKey("chefId")) {
        return false;
      }
      if (getChefId() != null ? !getChefId().equals(that.getChefId()) : that.getChefId() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getChefId() != null ? getChefId().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionGlobalDatePicker3(actionId=" + getActionId() + "){"
          + "chefId=" + getChefId()
          + "}";
    }
  }

  public static class ActionGlobalOrderDetailFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionGlobalOrderDetailFragment(@NonNull Order order) {
      if (order == null) {
        throw new IllegalArgumentException("Argument \"order\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("order", order);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionGlobalOrderDetailFragment setOrder(@NonNull Order order) {
      if (order == null) {
        throw new IllegalArgumentException("Argument \"order\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("order", order);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
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

    @Override
    public int getActionId() {
      return R.id.action_global_orderDetailFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public Order getOrder() {
      return (Order) arguments.get("order");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionGlobalOrderDetailFragment that = (ActionGlobalOrderDetailFragment) object;
      if (arguments.containsKey("order") != that.arguments.containsKey("order")) {
        return false;
      }
      if (getOrder() != null ? !getOrder().equals(that.getOrder()) : that.getOrder() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getOrder() != null ? getOrder().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionGlobalOrderDetailFragment(actionId=" + getActionId() + "){"
          + "order=" + getOrder()
          + "}";
    }
  }

  public static class ActionGlobalPickerBottomSheet implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionGlobalPickerBottomSheet(int pickerType, @Nullable String chefId) {
      this.arguments.put("pickerType", pickerType);
      this.arguments.put("chefId", chefId);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionGlobalPickerBottomSheet setPickerType(int pickerType) {
      this.arguments.put("pickerType", pickerType);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionGlobalPickerBottomSheet setChefId(@Nullable String chefId) {
      this.arguments.put("chefId", chefId);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("pickerType")) {
        int pickerType = (int) arguments.get("pickerType");
        __result.putInt("pickerType", pickerType);
      }
      if (arguments.containsKey("chefId")) {
        String chefId = (String) arguments.get("chefId");
        __result.putString("chefId", chefId);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_global_pickerBottomSheet;
    }

    @SuppressWarnings("unchecked")
    public int getPickerType() {
      return (int) arguments.get("pickerType");
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public String getChefId() {
      return (String) arguments.get("chefId");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionGlobalPickerBottomSheet that = (ActionGlobalPickerBottomSheet) object;
      if (arguments.containsKey("pickerType") != that.arguments.containsKey("pickerType")) {
        return false;
      }
      if (getPickerType() != that.getPickerType()) {
        return false;
      }
      if (arguments.containsKey("chefId") != that.arguments.containsKey("chefId")) {
        return false;
      }
      if (getChefId() != null ? !getChefId().equals(that.getChefId()) : that.getChefId() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + getPickerType();
      result = 31 * result + (getChefId() != null ? getChefId().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionGlobalPickerBottomSheet(actionId=" + getActionId() + "){"
          + "pickerType=" + getPickerType()
          + ", chefId=" + getChefId()
          + "}";
    }
  }

  public static class ActionGlobalChatRoomFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionGlobalChatRoomFragment(@NonNull String roomId) {
      if (roomId == null) {
        throw new IllegalArgumentException("Argument \"roomId\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("roomId", roomId);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionGlobalChatRoomFragment setRoomId(@NonNull String roomId) {
      if (roomId == null) {
        throw new IllegalArgumentException("Argument \"roomId\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("roomId", roomId);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("roomId")) {
        String roomId = (String) arguments.get("roomId");
        __result.putString("roomId", roomId);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_global_chatRoomFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getRoomId() {
      return (String) arguments.get("roomId");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionGlobalChatRoomFragment that = (ActionGlobalChatRoomFragment) object;
      if (arguments.containsKey("roomId") != that.arguments.containsKey("roomId")) {
        return false;
      }
      if (getRoomId() != null ? !getRoomId().equals(that.getRoomId()) : that.getRoomId() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getRoomId() != null ? getRoomId().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionGlobalChatRoomFragment(actionId=" + getActionId() + "){"
          + "roomId=" + getRoomId()
          + "}";
    }
  }

  public static class ActionGlobalReviewFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionGlobalReviewFragment(int rating, @NonNull Order order) {
      this.arguments.put("rating", rating);
      if (order == null) {
        throw new IllegalArgumentException("Argument \"order\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("order", order);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionGlobalReviewFragment setRating(int rating) {
      this.arguments.put("rating", rating);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionGlobalReviewFragment setOrder(@NonNull Order order) {
      if (order == null) {
        throw new IllegalArgumentException("Argument \"order\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("order", order);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
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

    @Override
    public int getActionId() {
      return R.id.action_global_reviewFragment;
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

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionGlobalReviewFragment that = (ActionGlobalReviewFragment) object;
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
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + getRating();
      result = 31 * result + (getOrder() != null ? getOrder().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionGlobalReviewFragment(actionId=" + getActionId() + "){"
          + "rating=" + getRating()
          + ", order=" + getOrder()
          + "}";
    }
  }

  public static class ActionGlobalReviewPage implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionGlobalReviewPage(@NonNull Review[] review) {
      if (review == null) {
        throw new IllegalArgumentException("Argument \"review\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("review", review);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionGlobalReviewPage setReview(@NonNull Review[] review) {
      if (review == null) {
        throw new IllegalArgumentException("Argument \"review\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("review", review);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("review")) {
        Review[] review = (Review[]) arguments.get("review");
        __result.putParcelableArray("review", review);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_global_reviewPage;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public Review[] getReview() {
      return (Review[]) arguments.get("review");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionGlobalReviewPage that = (ActionGlobalReviewPage) object;
      if (arguments.containsKey("review") != that.arguments.containsKey("review")) {
        return false;
      }
      if (getReview() != null ? !getReview().equals(that.getReview()) : that.getReview() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + java.util.Arrays.hashCode(getReview());
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionGlobalReviewPage(actionId=" + getActionId() + "){"
          + "review=" + getReview()
          + "}";
    }
  }

  public static class ActionGlobalImageUploadFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionGlobalImageUploadFragment(int imageType) {
      this.arguments.put("imageType", imageType);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionGlobalImageUploadFragment setImageType(int imageType) {
      this.arguments.put("imageType", imageType);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("imageType")) {
        int imageType = (int) arguments.get("imageType");
        __result.putInt("imageType", imageType);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_global_imageUploadFragment;
    }

    @SuppressWarnings("unchecked")
    public int getImageType() {
      return (int) arguments.get("imageType");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionGlobalImageUploadFragment that = (ActionGlobalImageUploadFragment) object;
      if (arguments.containsKey("imageType") != that.arguments.containsKey("imageType")) {
        return false;
      }
      if (getImageType() != that.getImageType()) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + getImageType();
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionGlobalImageUploadFragment(actionId=" + getActionId() + "){"
          + "imageType=" + getImageType()
          + "}";
    }
  }

  public static class ActionGlobalAddressListFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionGlobalAddressListFragment(int listType) {
      this.arguments.put("listType", listType);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionGlobalAddressListFragment setListType(int listType) {
      this.arguments.put("listType", listType);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("listType")) {
        int listType = (int) arguments.get("listType");
        __result.putInt("listType", listType);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_global_addressListFragment;
    }

    @SuppressWarnings("unchecked")
    public int getListType() {
      return (int) arguments.get("listType");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionGlobalAddressListFragment that = (ActionGlobalAddressListFragment) object;
      if (arguments.containsKey("listType") != that.arguments.containsKey("listType")) {
        return false;
      }
      if (getListType() != that.getListType()) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + getListType();
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionGlobalAddressListFragment(actionId=" + getActionId() + "){"
          + "listType=" + getListType()
          + "}";
    }
  }

  public static class ActionGlobalDisplayChefFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionGlobalDisplayChefFragment(@NonNull String chefId) {
      if (chefId == null) {
        throw new IllegalArgumentException("Argument \"chefId\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("chefId", chefId);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionGlobalDisplayChefFragment setChefId(@NonNull String chefId) {
      if (chefId == null) {
        throw new IllegalArgumentException("Argument \"chefId\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("chefId", chefId);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("chefId")) {
        String chefId = (String) arguments.get("chefId");
        __result.putString("chefId", chefId);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_global_displayChefFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getChefId() {
      return (String) arguments.get("chefId");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionGlobalDisplayChefFragment that = (ActionGlobalDisplayChefFragment) object;
      if (arguments.containsKey("chefId") != that.arguments.containsKey("chefId")) {
        return false;
      }
      if (getChefId() != null ? !getChefId().equals(that.getChefId()) : that.getChefId() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getChefId() != null ? getChefId().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionGlobalDisplayChefFragment(actionId=" + getActionId() + "){"
          + "chefId=" + getChefId()
          + "}";
    }
  }

  public static class ActionGlobalDoneFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionGlobalDoneFragment(@NonNull String direction) {
      if (direction == null) {
        throw new IllegalArgumentException("Argument \"direction\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("direction", direction);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionGlobalDoneFragment setDirection(@NonNull String direction) {
      if (direction == null) {
        throw new IllegalArgumentException("Argument \"direction\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("direction", direction);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("direction")) {
        String direction = (String) arguments.get("direction");
        __result.putString("direction", direction);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_global_doneFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getDirection() {
      return (String) arguments.get("direction");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionGlobalDoneFragment that = (ActionGlobalDoneFragment) object;
      if (arguments.containsKey("direction") != that.arguments.containsKey("direction")) {
        return false;
      }
      if (getDirection() != null ? !getDirection().equals(that.getDirection()) : that.getDirection() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getDirection() != null ? getDirection().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionGlobalDoneFragment(actionId=" + getActionId() + "){"
          + "direction=" + getDirection()
          + "}";
    }
  }
}
