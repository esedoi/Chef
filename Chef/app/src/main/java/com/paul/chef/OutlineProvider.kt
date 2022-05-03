package com.paul.chef

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import com.google.android.material.resources.MaterialResources.getDimensionPixelSize
import com.google.api.ResourceProto.resource

class OutlineProvider {
}

class ProfileOutlineProvider : ViewOutlineProvider() {
    override fun getOutline(view: View, outline: Outline) {
        view.clipToOutline = true

        outline.setOval(0, 0, view.width, view.height)
    }
}

