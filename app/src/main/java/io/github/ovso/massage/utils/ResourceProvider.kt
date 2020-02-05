package io.github.ovso.massage.utils

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.annotation.AnimRes
import android.support.annotation.ArrayRes
import android.support.annotation.BoolRes
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.annotation.FontRes
import android.support.annotation.IntegerRes
import android.support.annotation.PluralsRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import io.github.ovso.massage.App


object ResourceProvider {
  val context = App.instance
  val res: Resources = App.instance.resources
  @JvmStatic
  fun getText(@StringRes resId: Int): CharSequence {
    return res.getText(resId)
  }

  fun getTextArray(@ArrayRes resId: Int): Array<CharSequence> {
    return res.getTextArray(resId)
  }

  fun getQuantityText(@PluralsRes resId: Int, quantity: Int): CharSequence {
    return res.getQuantityText(resId, quantity)
  }

  fun getString(@StringRes resId: Int): String {
    return res.getString(resId)
  }

  fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
    return res.getString(resId, *formatArgs)
  }

  fun getStringArray(@ArrayRes resId: Int): Array<String> {
    return res.getStringArray(resId)
  }

  fun getQuantityString(@PluralsRes resId: Int, quantity: Int): String {
    return res.getQuantityString(resId, quantity)
  }

  fun getQuantityString(
    @PluralsRes resId: Int, quantity: Int,
    vararg formatArgs: Any
  ): String {
    return res.getQuantityString(resId, quantity, *formatArgs)
  }

  fun getInteger(@IntegerRes resId: Int): Int {
    return res.getInteger(resId)
  }

  fun getIntArray(@ArrayRes resId: Int): IntArray {
    return res.getIntArray(resId)
  }

  fun getBoolean(@BoolRes resId: Int): Boolean {
    return res.getBoolean(resId)
  }

  fun getDimension(@DimenRes resId: Int): Float {
    return res.getDimension(resId)
  }

  fun getDimensionPixelSize(@DimenRes resId: Int): Int {
    return res.getDimensionPixelSize(resId)
  }

  fun getDimensionPixelOffset(@DimenRes resId: Int): Int {
    return res.getDimensionPixelOffset(resId)
  }

  fun getDrawable(@DrawableRes resId: Int): Drawable? {
    return ContextCompat.getDrawable(context, resId)
  }

  @ColorInt
  fun getColor(@ColorRes resId: Int): Int {
    return ContextCompat.getColor(context, resId)
  }

  fun getColorStateList(@ColorRes resId: Int): ColorStateList? {
    return ContextCompat.getColorStateList(context, resId)
  }

  @Throws(Resources.NotFoundException::class)
  fun getFont(@FontRes id: Int): Typeface? {
    return ResourcesCompat.getFont(context, id)
  }

  fun loadAnimation(@AnimRes id: Int): Animation {
    return AnimationUtils.loadAnimation(context, id)
  }

  fun fromAssets(fileName: String): String {
    return try {
      val assetManager = context!!.resources.assets
      val inputStream = assetManager.open(fileName)
      val buffer = ByteArray(inputStream.available())
      inputStream.read(buffer)
      inputStream.close()
      String(buffer, Charsets.UTF_8)
    } catch (e: Exception) {
      ""
    }
  }

}