/*
 * Copyright 2005 Dimitar Dimitrov (dimitar at schizogenesis dot com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Date: Jan 26, 2005
 * Time: 3:52:07 PM
 * $Id:;
 */
package com.schizogenesis.duralumin;

import com.incors.plaf.alloy.AlloyLookAndFeel;
import com.incors.plaf.alloy.AlloyTheme;
import com.incors.plaf.alloy.AlloyFontTheme;
import com.incors.plaf.alloy.themes.custom.CustomThemeFactory;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;
import com.sun.java.swing.plaf.windows.WindowsTreeUI;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;


/**
 * @author dimitar@schizogenesis.com
 * @since Jan 26, 2005  3:52:07 PM
 */
public class Duralumin implements ProjectComponent {
    private Project project;

    public Duralumin(Project project) { this.project = project; }

    public void initComponent() { }

    public void projectOpened() {
        LookAndFeel currentLookAndFeel = UIManager.getLookAndFeel();
        if (!(currentLookAndFeel instanceof AlloyLookAndFeel)) {
            return;
        }

        AlloyFontTheme fontTheme = createNativeFontTheme();
        AlloyTheme theme = createNativeTheme(fontTheme);

        try {
            UIManager.setLookAndFeel(new AlloyLookAndFeel(theme));
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException("Couldn't set native tame for Alloy Look and Feel.", e);
        }

        UIDefaults defaults = UIManager.getDefaults();
        defaults.put("Tree.collapsedIcon", WindowsTreeUI.CollapsedIcon.createCollapsedIcon());
        defaults.put("Tree.expandedIcon", WindowsTreeUI.ExpandedIcon.createExpandedIcon());

        /*
        // messes with the detachable tool windows
        JDialog.setDefaultLookAndFeelDecorated(true);
        JFrame.setDefaultLookAndFeelDecorated(true);
        */

        Window root = WindowManager.getInstance().suggestParentWindow(project);
        SwingUtilities.updateComponentTreeUI(root);
        tweakContainedComponents(root);
    }

    public void projectClosed() { }

    public void disposeComponent() { }

    public String getComponentName() { return "Duralumin"; }

    private static void tweakContainedComponents(Container container) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Component[] components = container.getComponents();
        for (int i = 0; i < components.length; i++) {
            Component component = components[i];
            if (component.getClass().getName().startsWith("com.intellij.openapi.wm.impl.")) {
                component.setBackground((Color) tk.getDesktopProperty("win.3d.backgroundColor"));
            }
            if (component instanceof Container) {
                tweakContainedComponents((Container) component);
            }
        }
    }

    private static AlloyTheme createNativeTheme(AlloyFontTheme ftheme) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Color contrastColor = (Color) tk.getDesktopProperty("win.3d.backgroundColor");
        Color standardColor = (Color) tk.getDesktopProperty("win.3d.backgroundColor");
        Color desktopColor = (Color) tk.getDesktopProperty("win.mdi.backgroundColor");
        Color selectionColor = (Color) tk.getDesktopProperty("win.item.highlightColor");
        Color rolloverColor =  (Color) tk.getDesktopProperty("win.item.highlightColor");
        Color highlightColor = (Color) tk.getDesktopProperty("win.item.highlightColor");
        return CustomThemeFactory.createTheme(
                contrastColor, standardColor, desktopColor,
                selectionColor, rolloverColor, highlightColor,
                ftheme
        );
    }

    private static AlloyFontTheme createNativeFontTheme() {
        Toolkit tk = Toolkit.getDefaultToolkit();

        final Font controlFont = (Font) tk.getDesktopProperty("win.defaultGUI.font");
        final Font systemFont = (Font) tk.getDesktopProperty("win.tooltip.font");
        final Font userFont = (Font) tk.getDesktopProperty("win.messagebox.font");
        final Font menuFont = (Font) tk.getDesktopProperty("win.menu.font");
        final Font windowFont = (Font) tk.getDesktopProperty("win.defaultGUI.font");
        final Font subFont = (Font) tk.getDesktopProperty("win.status.font");

        AlloyFontTheme ftheme = new AlloyFontTheme() {
            public FontUIResource getControlTextFont() { return new FontUIResource(controlFont); }
            public FontUIResource getSystemTextFont() { return new FontUIResource(systemFont); }
            public FontUIResource getUserTextFont() { return new FontUIResource(userFont); }
            public FontUIResource getMenuTextFont() { return new FontUIResource(menuFont); }
            public FontUIResource getWindowTitleFont() { return new FontUIResource(windowFont); }
            public FontUIResource getSubTextFont() { return new FontUIResource(subFont); }
        };
        return ftheme;
    }
}
