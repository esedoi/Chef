package com.paul.chef.ui.chatRoom;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class ChatRoomFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private ChatRoomFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private ChatRoomFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ChatRoomFragmentArgs fromBundle(@NonNull Bundle bundle) {
    ChatRoomFragmentArgs __result = new ChatRoomFragmentArgs();
    bundle.setClassLoader(ChatRoomFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("roomId")) {
      String roomId;
      roomId = bundle.getString("roomId");
      if (roomId == null) {
        throw new IllegalArgumentException("Argument \"roomId\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("roomId", roomId);
    } else {
      throw new IllegalArgumentException("Required argument \"roomId\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ChatRoomFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    ChatRoomFragmentArgs __result = new ChatRoomFragmentArgs();
    if (savedStateHandle.contains("roomId")) {
      String roomId;
      roomId = savedStateHandle.get("roomId");
      if (roomId == null) {
        throw new IllegalArgumentException("Argument \"roomId\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("roomId", roomId);
    } else {
      throw new IllegalArgumentException("Required argument \"roomId\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getRoomId() {
    return (String) arguments.get("roomId");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("roomId")) {
      String roomId = (String) arguments.get("roomId");
      __result.putString("roomId", roomId);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("roomId")) {
      String roomId = (String) arguments.get("roomId");
      __result.set("roomId", roomId);
    }
    return __result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    ChatRoomFragmentArgs that = (ChatRoomFragmentArgs) object;
    if (arguments.containsKey("roomId") != that.arguments.containsKey("roomId")) {
      return false;
    }
    if (getRoomId() != null ? !getRoomId().equals(that.getRoomId()) : that.getRoomId() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getRoomId() != null ? getRoomId().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ChatRoomFragmentArgs{"
        + "roomId=" + getRoomId()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull ChatRoomFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull String roomId) {
      if (roomId == null) {
        throw new IllegalArgumentException("Argument \"roomId\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("roomId", roomId);
    }

    @NonNull
    public ChatRoomFragmentArgs build() {
      ChatRoomFragmentArgs result = new ChatRoomFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setRoomId(@NonNull String roomId) {
      if (roomId == null) {
        throw new IllegalArgumentException("Argument \"roomId\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("roomId", roomId);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getRoomId() {
      return (String) arguments.get("roomId");
    }
  }
}
