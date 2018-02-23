// Copyright 2000-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package org.jetbrains.plugins.groovy.console.actions;

import com.intellij.execution.console.ConsoleHistoryController;
import com.intellij.ide.scratch.ScratchFileService;
import com.intellij.internal.statistic.UsageTrigger;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.groovy.config.GroovyFacetUtil;
import org.jetbrains.plugins.groovy.console.GroovyConsole;
import org.jetbrains.plugins.groovy.console.GroovyConsoleRootType;
import org.jetbrains.plugins.groovy.statictics.GroovyStatisticsIds;

import static org.jetbrains.plugins.groovy.console.GroovyConsoleUtilKt.getAnyApplicableModule;

public class GrNewConsoleAction extends AnAction {
  @Override
  public void update(AnActionEvent e) {
    e.getPresentation().setEnabledAndVisible(getModule(e) != null);
  }

  @Override
  public void actionPerformed(AnActionEvent e) {
    final Project project = e.getProject();
    final Module module = getModule(e);
    if (project == null || module == null) return;

    UsageTrigger.trigger(GroovyStatisticsIds.GROOVY_NEW_CONSOLE);
    final VirtualFile contentFile = ConsoleHistoryController.getContentFile(
      GroovyConsoleRootType.getInstance(),
      GroovyConsoleRootType.CONTENT_ID,
      ScratchFileService.Option.create_new_always
    );
    assert contentFile != null;
    GroovyConsole.createConsole(project, contentFile, module);
    FileEditorManager.getInstance(project).openFile(contentFile, true);
  }

  @Nullable
  protected Module getModule(AnActionEvent e) {
    Project project = e.getProject();
    if (project == null) return null;

    VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
    if (file != null) {
      Module module = ModuleUtilCore.findModuleForFile(file, project);
      if (GroovyFacetUtil.isSuitableModule(module)) {
        return module;
      }
    }

    return getAnyApplicableModule(project);
  }
}