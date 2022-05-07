// Generated by view binder compiler. Do not edit!
package com.paul.chef.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
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

public final class FragmentChatRoomBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final RecyclerView chatRoomRecycler;

  @NonNull
  public final EditText editText;

  @NonNull
  public final ImageButton imgBtn;

  @NonNull
  public final ImageButton textbtn;

  private FragmentChatRoomBinding(@NonNull ConstraintLayout rootView,
      @NonNull RecyclerView chatRoomRecycler, @NonNull EditText editText,
      @NonNull ImageButton imgBtn, @NonNull ImageButton textbtn) {
    this.rootView = rootView;
    this.chatRoomRecycler = chatRoomRecycler;
    this.editText = editText;
    this.imgBtn = imgBtn;
    this.textbtn = textbtn;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentChatRoomBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentChatRoomBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_chat_room, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentChatRoomBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.chatRoomRecycler;
      RecyclerView chatRoomRecycler = ViewBindings.findChildViewById(rootView, id);
      if (chatRoomRecycler == null) {
        break missingId;
      }

      id = R.id.editText;
      EditText editText = ViewBindings.findChildViewById(rootView, id);
      if (editText == null) {
        break missingId;
      }

      id = R.id.imgBtn;
      ImageButton imgBtn = ViewBindings.findChildViewById(rootView, id);
      if (imgBtn == null) {
        break missingId;
      }

      id = R.id.textbtn;
      ImageButton textbtn = ViewBindings.findChildViewById(rootView, id);
      if (textbtn == null) {
        break missingId;
      }

      return new FragmentChatRoomBinding((ConstraintLayout) rootView, chatRoomRecycler, editText,
          imgBtn, textbtn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
