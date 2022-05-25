package com.paul.chef

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider



class ProfileOutlineProvider : ViewOutlineProvider() {
    override fun getOutline(view: View, outline: Outline) {
        view.clipToOutline = true

        outline.setOval(0, 0, view.width, view.height)
    }
}
