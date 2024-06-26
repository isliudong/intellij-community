/*
 * Copyright 2000-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.openapi.vcs.ex

import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.ex.DocumentTracker.Block
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.concurrency.annotations.RequiresEdt

class SimpleLineStatusTracker(project: Project?, document: Document) : LineStatusTrackerBase<Range>(project, document) {

  constructor(project: Project?, document: Document, rendererBuilder: (SimpleLineStatusTracker) -> LineStatusMarkerRenderer)
    : this(project, document) {
    val renderer: LineStatusMarkerRenderer = rendererBuilder(this)
    listeners.addListener(object : LineStatusTrackerListener {
      override fun onRangesChanged() {
        renderer.scheduleUpdate()
      }
    })
  }

  override val virtualFile: VirtualFile? = FileDocumentManager.getInstance().getFile(document)
  override fun toRange(block: Block): Range = Range(block.start, block.end, block.vcsStart, block.vcsEnd, null)

  override val Block.ourData: DocumentTracker.BlockData
    get() = DocumentTracker.BlockData.Empty

  @RequiresEdt
  fun setBaseRevision(vcsContent: CharSequence) {
    setBaseRevisionContent(vcsContent, null)
  }
}