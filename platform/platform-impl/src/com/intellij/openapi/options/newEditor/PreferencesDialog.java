/*
 * Copyright 2000-2013 JetBrains s.r.o.
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
package com.intellij.openapi.options.newEditor;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.options.ConfigurableGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.ui.Gray;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author Konstantin Bulenkov
 */
public class PreferencesDialog extends DialogWrapper {
  private JPanel myRoot;
  private JPanel myTopPanel;
  private JPanel myCenterPanel;

  public PreferencesDialog(@Nullable Project project, ConfigurableGroup[] groups) {
    super(project);
    setSize(800, 600);
    init();
    ((JDialog)getPeer().getWindow()).setUndecorated(true);
    ((JComponent)((JDialog)getPeer().getWindow()).getContentPane()).setBorder(new LineBorder(Gray._140, 1));

    setTitle("Preferences");
  }

  @Nullable
  @Override
  protected JComponent createCenterPanel() {
    final JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(createEditorSettings());
    panel.add(createProjectSettings());
    panel.add(createApplicationSettings());
    panel.add(createOtherSettings());
    panel.setPreferredSize(new Dimension(800, 450));
    myCenterPanel.add(panel, BorderLayout.CENTER);
    return myRoot;
  }

  private static JComponent createEditorSettings() {
    final LabeledButtonsPanel panel = new LabeledButtonsPanel("Editor");
    panel.addButton(new PreferenceButton("Editor", AllIcons.Preferences.Editor));
    panel.addButton(new PreferenceButton("Code Style", AllIcons.Preferences.CodeStyle));
    return panel;
  }

  private static JComponent createProjectSettings() {
    final LabeledButtonsPanel panel = new LabeledButtonsPanel("Project");
    panel.addButton(new PreferenceButton("Compiler", AllIcons.Preferences.Compiler));
    panel.addButton(new PreferenceButton("Version Control", AllIcons.Preferences.VersionControl));
    panel.addButton(new PreferenceButton("File Colors", AllIcons.Preferences.FileColors));
    return panel;
  }

  private static JComponent createApplicationSettings() {
    final LabeledButtonsPanel panel = new LabeledButtonsPanel("IDE");
    panel.addButton(new PreferenceButton("Appearance", AllIcons.Preferences.Appearance));
    panel.addButton(new PreferenceButton("General", AllIcons.Preferences.General));
    panel.addButton(new PreferenceButton("Keymap", AllIcons.Preferences.Keymap));
    panel.addButton(new PreferenceButton("File Types", AllIcons.Preferences.FileTypes));
    return panel;
  }

  private static JComponent createOtherSettings() {
    final LabeledButtonsPanel panel = new LabeledButtonsPanel("Other");
    panel.addButton(new PreferenceButton("Plugins", AllIcons.Preferences.Plugins));
    panel.addButton(new PreferenceButton("Updates", AllIcons.Preferences.Updates));
    return panel;
  }




  @Nullable
  @Override
  protected JComponent createSouthPanel() {
    if (SystemInfo.isMac) {
      return null;
    }
    return super.createSouthPanel();
  }
}
