// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package git4idea.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.changes.ChangeListManager;
import com.intellij.openapi.vcs.changes.ui.ChangesViewContentManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.vcs.VcsShowToolWindowTabAction;
import git4idea.i18n.GitBundle;
import git4idea.stash.ui.GitStashContentProvider;
import git4idea.ui.GitUnstashDialog;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Git unstash action
 */
public class GitUnstash extends GitRepositoryAction {

  /**
   * {@inheritDoc}
   */
  @Override
  protected @NotNull String getActionName() {
    return GitBundle.message("unstash.action.name");
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    Project project = e.getProject();
    if (project != null && ChangesViewContentManager.getToolWindowFor(project, GitStashContentProvider.TAB_NAME) != null) {
      VcsShowToolWindowTabAction.activateVcsTab(project, GitStashContentProvider.TAB_NAME, false);
      return;
    }

    super.actionPerformed(e);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void perform(final @NotNull Project project,
                         final @NotNull List<VirtualFile> gitRoots,
                         final @NotNull VirtualFile defaultRoot) {
    final ChangeListManager changeListManager = ChangeListManager.getInstance(project);
    if (changeListManager.isFreezedWithNotification(GitBundle.message("unstash.error.can.not.unstash.changes.now"))) return;
    GitUnstashDialog.showUnstashDialog(project, gitRoots, defaultRoot);
  }
}
