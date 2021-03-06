// Copyright 2000-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package org.jetbrains.plugins.github.pullrequest.ui

import com.intellij.openapi.Disposable
import com.intellij.util.EventDispatcher
import org.jetbrains.plugins.github.api.data.GithubSearchedIssue
import java.util.*
import kotlin.properties.Delegates

class GithubPullRequestsListSelectionModel {
  var current: GithubSearchedIssue? by Delegates.observable<GithubSearchedIssue?>(null) { _, _, _ ->
    changeEventDispatcher.multicaster.selectionChanged()
  }

  private val changeEventDispatcher = EventDispatcher.create(SelectionChangedListener::class.java)

  fun addChangesListener(listener: SelectionChangedListener, disposable: Disposable) =
    changeEventDispatcher.addListener(listener, disposable)

  interface SelectionChangedListener : EventListener {
    fun selectionChanged()
  }
}