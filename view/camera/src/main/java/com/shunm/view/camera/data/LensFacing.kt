package com.shunm.view.camera.data

import androidx.camera.core.CameraSelector

internal sealed interface LensFacing {
    data object Front : LensFacing

    data object Back : LensFacing

    companion object {
        fun from(value: Int): LensFacing {
            return when (value) {
                CameraSelector.LENS_FACING_FRONT -> Front
                CameraSelector.LENS_FACING_BACK -> Back
                else -> throw IllegalArgumentException("Unknown lens facing: $value")
            }
        }
    }

    @CameraSelector.LensFacing
    fun toValue(): Int {
        return when (this) {
            Front -> CameraSelector.LENS_FACING_FRONT
            Back -> CameraSelector.LENS_FACING_BACK
        }
    }

    fun toInverse(): LensFacing {
        return when (this) {
            Front -> Back
            Back -> Front
        }
    }
}
