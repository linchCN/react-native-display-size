package com.rn.displaysize


import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.content.res.Resources
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.Display
import android.view.Window
import android.view.WindowManager
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.Arguments


class DisplaySizeModule internal constructor(context: ReactApplicationContext) :
    DisplaySizeSpec(context)  {
  private val mReactContext: ReactContext = context

  override fun getName(): String {
    return NAME
  }

  companion object {
    const val NAME = "DisplaySize"
  }

  @ReactMethod
  override fun getDimensions(): WritableMap {
    val constants: WritableMap = Arguments.createMap()

    val metrics: DisplayMetrics = mReactContext.getResources().getDisplayMetrics()

    // Get the real display metrics if we are using API level 17 or higher.
    // The real metrics include system decor elements (e.g. soft menu bar).
    //
    // See: http://developer.android.com/reference/android/view/Display.html#getRealMetrics(android.util.DisplayMetrics)
    if (Build.VERSION.SDK_INT >= 17) {
        val display: Display =
            (mReactContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
                .getDefaultDisplay()
        try {
            Display::class.java.getMethod("getRealMetrics", DisplayMetrics::class.java)
                .invoke(display, metrics)
        } catch (e: java.lang.reflect.InvocationTargetException) {
        } catch (e: java.lang.IllegalAccessException) {
        } catch (e: java.lang.NoSuchMethodException) {
        }
    }

    constants.putDouble("realWindowHeight", getRealHeight(metrics).toDouble())
    constants.putDouble("realWindowWidth", getRealWidth(metrics).toDouble())
    constants.putDouble("statusBarHeight", getStatusBarHeight(metrics).toDouble())
    constants.putDouble("softMenuBarHeight", getSoftMenuBarHeight(metrics).toDouble())
    constants.putDouble("smartBarHeight", getSmartBarHeight(metrics).toDouble())
    constants.putBoolean("softMenuBarEnabled", !navigationGestureEnabled(mReactContext))
    return constants
  }

  protected fun getStatusBarHeight(metrics: DisplayMetrics): Float {
    val heightResId: Int =
        mReactContext.getResources().getIdentifier("status_bar_height", "dimen", "android")
    return if (heightResId > 0
    ) mReactContext.getResources().getDimensionPixelSize(heightResId) / metrics.density
    else 0f
  }


  protected val deviceInfo: String
    /**
     * 获取设备信息（目前支持几大主流的全面屏手机，华为、小米、oppo、魅族、vivo）
     *
     * @return
     */
    get() {
      val brand: String = Build.BRAND
      if (TextUtils.isEmpty(brand)) return "navigationbar_is_min"

      return if (brand.equals("HUAWEI", ignoreCase = true)) {
          "navigationbar_is_min"
      } else if (brand.equals("XIAOMI", ignoreCase = true)) {
          "force_fsg_nav_bar"
      } else if (brand.equals("VIVO", ignoreCase = true)) {
          "navigation_gesture_on"
      } else if (brand.equals("OPPO", ignoreCase = true)) {
          "navigation_gesture_on"
      } else {
          "navigationbar_is_min"
      }
    }

  /**
   * 全面屏（是否开启全面屏开关 0 关闭  1 开启）
   *
   * @param context
   * @return
   */
  protected fun navigationGestureEnabled(context: Context): Boolean {
    val `val`: Int = Settings.Global.getInt(context.getContentResolver(), deviceInfo, 0)
    return `val` != 0
  }

  protected fun hasPermanentMenuKey(): Boolean {
    val id: Int =
        mReactContext.getResources().getIdentifier("config_showNavigationBar", "bool", "android")
    return !(id > 0 && mReactContext.getResources().getBoolean(id))
  }

  // 获取魅族SmartBar高度
  protected fun getSmartBarHeight(metrics: DisplayMetrics): Float {
    val isMeiZu: Boolean = Build.MANUFACTURER.equals("Meizu")

    val autoHideSmartBar = Settings.System.getInt(
        mReactContext.getContentResolver(),
        "mz_smartbar_auto_hide", 0
    ) === 1

    if (!isMeiZu || autoHideSmartBar) {
        return 0f
    }
    try {
        val c: java.lang.Class<*> = java.lang.Class.forName("com.android.internal.R\$dimen")
        val obj: Any = c.newInstance()
        val field: java.lang.reflect.Field = c.getField("mz_action_button_min_height")
        val height: Int = field.get(obj).toString().toInt()
        return mReactContext.getResources().getDimensionPixelSize(height) / metrics.density
    } catch (e: Throwable) { // 不自动隐藏smartbar同时又没有smartbar高度字段供访问，取系统navigationbar的高度
        return getNormalNavigationBarHeight(mReactContext) / metrics.density
    }
      //return getNormalNavigationBarHeight(context) / metrics.density;
  }


  protected fun getSoftMenuBarHeight(metrics: DisplayMetrics): Float {
    if (hasPermanentMenuKey()) {
        return 0f
    }
    val heightResId: Int =
        mReactContext.getResources().getIdentifier("navigation_bar_height", "dimen", "android")
    return if (heightResId > 0
    ) mReactContext.getResources().getDimensionPixelSize(heightResId) / metrics.density
    else 0f
  }

  protected fun getRealHeight(metrics: DisplayMetrics): Float {
    return metrics.heightPixels / metrics.density
  }

  protected fun getRealWidth(metrics: DisplayMetrics): Float {
    return metrics.widthPixels / metrics.density
  }

  protected fun getNormalNavigationBarHeight(ctx: Context): Float {
    try {
      val res: Resources = ctx.getResources()
      val rid: Int = res.getIdentifier("config_showNavigationBar", "bool", "android")
      if (rid > 0) {
        val flag: Boolean = res.getBoolean(rid)
        if (flag) {
          val resourceId: Int =
            res.getIdentifier("navigation_bar_height", "dimen", "android")
          if (resourceId > 0) {
            return res.getDimensionPixelSize(resourceId).toFloat()
          }
        }
      }
    } catch (e: Throwable) {
      return 0f
    }
    return 0f
  }
}
