// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.intellij.ide.warmup

import com.intellij.openapi.extensions.ExtensionPointName

/**
 * This class is used when warmup decides to relay important information to the user.
 */
interface WarmupLogger {

  /**
   * Allows reporting information messages.
   */
  fun logInfo(message: String)

  /**
   * Allows reporting error messages to the user.
   * Use this if there is an important error, which is still recoverable.
   */
  fun logError(message: String, throwable: Throwable?)

  /**
   * Allows aborting the process of warmup if something non-recoverable happens.
   */
  fun logFatalError(message: String, throwable: Throwable?)

  companion object {
    private val EP_NAME: ExtensionPointName<WarmupLogger> = ExtensionPointName("com.intellij.warmupLogger")

    fun message(message: String) {
      for (warmupLogger in EP_NAME.extensionList) {
        warmupLogger.logInfo(message)
      }
    }

    fun error(message: String, throwable: Throwable? = null) {
      for (warmupLogger in EP_NAME.extensionList) {
        warmupLogger.logError(message, throwable)
      }
    }

    fun fatalError(message: String, throwable: Throwable? = null) {
      for (warmupLogger in EP_NAME.extensionList) {
        warmupLogger.logFatalError(message, throwable)
      }
    }
  }
}