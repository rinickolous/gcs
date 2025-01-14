/*
 * Copyright ©1998-2021 by Richard A. Wilkes. All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, version 2.0. If a copy of the MPL was not distributed with
 * this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, version 2.0.
 */

package com.trollworks.gcs.ui.widget;

import com.trollworks.gcs.library.LibraryExplorerDockable;
import com.trollworks.gcs.menu.edit.JumpToSearchTarget;
import com.trollworks.gcs.menu.file.SignificantFrame;
import com.trollworks.gcs.settings.Settings;
import com.trollworks.gcs.ui.FontAdjustable;
import com.trollworks.gcs.ui.widget.dock.Dock;
import com.trollworks.gcs.ui.widget.dock.DockContainer;
import com.trollworks.gcs.ui.widget.dock.DockLocation;
import com.trollworks.gcs.ui.widget.dock.Dockable;
import com.trollworks.gcs.utility.Geometry;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.Window;
import java.util.List;

/** The workspace, where all files can be viewed and edited. */
public final class Workspace extends BaseWindow implements SignificantFrame, JumpToSearchTarget, FontAdjustable {
    private Dock mDock;

    /** @return The Workspace. */
    public static Workspace get() {
        Window window = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow();
        if (window instanceof Workspace w) {
            return w;
        }
        List<Workspace> windows = BaseWindow.getWindows(Workspace.class);
        if (!windows.isEmpty()) {
            return windows.get(0);
        }
        return new Workspace();
    }

    private Workspace() {
        super("GCS");
        Container content = getContentPane();
        Toolbar   toolbar = new Toolbar();
        content.add(toolbar, BorderLayout.NORTH);
        mDock = new Dock();
        content.add(mDock, BorderLayout.CENTER);
        LibraryExplorerDockable libraryExplorer = new LibraryExplorerDockable();
        mDock.dock(libraryExplorer, DockLocation.WEST);
        mDock.getLayout().findLayout(libraryExplorer.getDockContainer()).setDividerPosition(Settings.getInstance().getLibraryExplorerDividerPosition());
        restoreBounds();
        setVisible(true);
        WindowUtils.forceAppToFront();
    }

    @Override
    public void pack() {
        super.pack();
        setBounds(Geometry.inset(LayoutConstants.WINDOW_BORDER_INSET, new Rectangle(WindowUtils.getMaximumWindowBounds())));
    }

    @Override
    public String getWindowPrefsKey() {
        return "workspace";
    }

    /** @return The {@link Dock}. */
    public Dock getDock() {
        return mDock;
    }

    @Override
    public boolean isJumpToSearchAvailable() {
        DockContainer dc = mDock.getFocusedDockContainer();
        if (dc != null) {
            Dockable dockable = dc.getCurrentDockable();
            if (dockable instanceof JumpToSearchTarget target) {
                return target.isJumpToSearchAvailable();
            }
        }
        return false;
    }

    @Override
    public void jumpToSearchField() {
        DockContainer dc = mDock.getFocusedDockContainer();
        if (dc != null) {
            Dockable dockable = dc.getCurrentDockable();
            if (dockable instanceof JumpToSearchTarget target) {
                target.jumpToSearchField();
            }
        }
    }

    @Override
    public void saveBounds() {
        super.saveBounds();
        LibraryExplorerDockable lib = LibraryExplorerDockable.get();
        if (lib != null) {
            lib.savePreferences();
        }
    }

    @Override
    public void adjustToFontChanges() {
        for (Dockable dockable : mDock.getDockables()) {
            if (dockable instanceof FontAdjustable fa) {
                fa.adjustToFontChanges();
            }
        }
    }
}
