package com.rn.displaysize

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.Promise

abstract class DisplaySizeSpec internal constructor(context: ReactApplicationContext) :
  ReactContextBaseJavaModule(context) {

  abstract fun getDimensions(): Map<String, Any>

  abstract fun isStatusBarTranslucent(): Boolean
}
