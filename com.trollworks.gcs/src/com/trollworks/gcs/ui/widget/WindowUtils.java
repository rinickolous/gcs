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

import com.trollworks.gcs.ui.UIUtilities;
import com.trollworks.gcs.ui.image.Images;
import com.trollworks.gcs.utility.Geometry;
import com.trollworks.gcs.utility.Log;
import com.trollworks.gcs.utility.Platform;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.InputEvent;
import javax.swing.JOptionPane;

/** Utilities for use with windows. */
public final class WindowUtils {
    private static Frame HIDDEN_FRAME;

    private WindowUtils() {
    }

    /**
     * @param comp The {@link Component} to use. May be {@code null}.
     * @return The most logical {@link Window} associated with the component.
     */
    public static Window getWindowForComponent(Component comp) {
        while (true) {
            if (comp == null) {
                return JOptionPane.getRootFrame();
            }
            if (comp instanceof Window w) {
                return w;
            }
            comp = comp.getParent();
        }
    }

    /**
     * Looks for the screen device that contains the largest part of the specified window.
     *
     * @param window The window to determine the preferred screen device for.
     * @return The preferred screen device.
     */
    public static GraphicsDevice getPreferredScreenDevice(Window window) {
        return getPreferredScreenDevice(window.getBounds());
    }

    /**
     * Looks for the screen device that contains the largest part of the specified global bounds.
     *
     * @param bounds The global bounds to determine the preferred screen device for.
     * @return The preferred screen device.
     */
    public static GraphicsDevice getPreferredScreenDevice(Rectangle bounds) {
        GraphicsEnvironment ge            = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice      best          = ge.getDefaultScreenDevice();
        Rectangle           overlapBounds = Geometry.intersection(bounds, best.getDefaultConfiguration().getBounds());
        int                 bestOverlap   = overlapBounds.width * overlapBounds.height;

        for (GraphicsDevice gd : ge.getScreenDevices()) {
            if (gd.getType() == GraphicsDevice.TYPE_RASTER_SCREEN) {
                overlapBounds = Geometry.intersection(bounds, gd.getDefaultConfiguration().getBounds());
                if (overlapBounds.width * overlapBounds.height > bestOverlap) {
                    best = gd;
                }
            }
        }
        return best;
    }

    /** @return The maximum bounds that fits on the main screen. */
    public static Rectangle getMaximumWindowBounds() {
        return getMaximumWindowBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds());
    }

    /**
     * Determines the screen that most contains the specified window and returns the maximum size
     * the window can be on that screen.
     *
     * @param window The window to determine a maximum bounds for.
     * @return The maximum bounds that fits on a screen.
     */
    public static Rectangle getMaximumWindowBounds(Window window) {
        return getMaximumWindowBounds(window.getBounds());
    }

    /**
     * Determines the screen that most contains the specified panel area and returns the maximum
     * size a window can be on that screen.
     *
     * @param panel The panel that contains the area.
     * @param area  The area within the panel to use when determining the maximum bounds for a
     *              window.
     * @return The maximum bounds that fits on a screen.
     */
    public static Rectangle getMaximumWindowBounds(Component panel, Rectangle area) {
        area = new Rectangle(area);
        UIUtilities.convertRectangleToScreen(area, panel);
        return getMaximumWindowBounds(area);
    }

    /**
     * Determines the screen that most contains the specified global bounds and returns the maximum
     * size a window can be on that screen.
     *
     * @param bounds The global bounds to use when determining the maximum bounds for a window.
     * @return The maximum bounds that fits on a screen.
     */
    public static Rectangle getMaximumWindowBounds(Rectangle bounds) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice      gd = getPreferredScreenDevice(bounds);

        if (gd == ge.getDefaultScreenDevice()) {
            bounds = ge.getMaximumWindowBounds();
            // The Mac (and now Windows as of Java 5) already return the correct
            // value... try to fix it up for the other platforms. This doesn't
            // currently work, either, since the other platforms seem to always
            // return empty insets.
            if (!Platform.isMacintosh() && !Platform.isWindows()) {
                Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gd.getDefaultConfiguration());

                // Since this is failing to do the right thing anyway, we're going
                // to try and come up with some reasonable limitations...
                if (insets.top == 0 && insets.bottom == 0) {
                    insets.bottom = 48;
                }

                bounds.x += insets.left;
                bounds.y += insets.top;
                bounds.width -= insets.left + insets.right;
                bounds.height -= insets.top + insets.bottom;
            }
            return bounds;
        }
        return gd.getDefaultConfiguration().getBounds();
    }

    public static void packAndCenterWindowOn(Window window, Component centeredOn) {
        window.pack();
        Dimension prefSize = window.getPreferredSize();
        Dimension minSize  = window.getMinimumSize();
        int       width    = Math.max(prefSize.width, minSize.width);
        int       height   = Math.max(prefSize.height, minSize.height);
        int       x;
        int       y;
        if (centeredOn != null && centeredOn.isShowing()) {
            Point     where = centeredOn.getLocationOnScreen();
            Dimension size  = centeredOn.getSize();
            x = where.x + (size.width - width) / 2;
            y = where.y + (size.height - height) / 2;
        } else {
            Rectangle bounds = getMaximumWindowBounds(window);
            x = bounds.x + (bounds.width - width) / 2;
            y = bounds.y + (bounds.height - height) / 2;
        }
        window.setLocation(x, y);
        forceOnScreen(window);
    }

    /**
     * Forces the specified window onscreen.
     *
     * @param window The window to force onscreen.
     */
    public static void forceOnScreen(Window window) {
        Rectangle maxBounds = getMaximumWindowBounds(window);
        Rectangle bounds    = window.getBounds();
        Point     location  = new Point(bounds.x, bounds.y);
        Dimension size      = window.getMinimumSize();

        if (bounds.width < size.width) {
            bounds.width = size.width;
        }
        if (bounds.height < size.height) {
            bounds.height = size.height;
        }

        if (bounds.x < maxBounds.x) {
            bounds.x = maxBounds.x;
        } else if (bounds.x >= maxBounds.x + maxBounds.width) {
            bounds.x = maxBounds.x + maxBounds.width - 1;
        }

        if (bounds.x + bounds.width >= maxBounds.x + maxBounds.width) {
            bounds.x = maxBounds.x + maxBounds.width - bounds.width;
            if (bounds.x < maxBounds.x) {
                bounds.x = maxBounds.x;
                bounds.width = maxBounds.width;
            }
        }

        if (bounds.y < maxBounds.y) {
            bounds.y = maxBounds.y;
        } else if (bounds.y >= maxBounds.y + maxBounds.height) {
            bounds.y = maxBounds.y + maxBounds.height - 1;
        }

        if (bounds.y + bounds.height >= maxBounds.y + maxBounds.height) {
            bounds.y = maxBounds.y + maxBounds.height - bounds.height;
            if (bounds.y < maxBounds.y) {
                bounds.y = maxBounds.y;
                bounds.height = maxBounds.height;
            }
        }

        if (location.x != bounds.x || location.y != bounds.y) {
            window.setBounds(bounds);
        } else {
            window.setSize(bounds.width, bounds.height);
        }
        window.validate();
    }

    /** @return A {@link Frame} to use when a valid frame of any sort is all that is needed. */
    public static Frame getAnyFrame() {
        Frame frame = BaseWindow.getTopWindow();
        if (frame == null) {
            for (Frame element : Frame.getFrames()) {
                if (element.isDisplayable()) {
                    return element;
                }
            }
            return getHiddenFrame(true);
        }
        return frame;
    }

    /** Attempts to force the app to the front. */
    public static void forceAppToFront() {
        // Calling Desktop.isDesktopSupported() generally doesn't have the desired effect on Windows
        boolean force = Platform.isWindows();
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().requestForeground(true);
            } catch (UnsupportedOperationException uoex) {
                force = true;
            }
        }
        if (force) {
            BaseWindow topWindow = BaseWindow.getTopWindow();
            if (topWindow != null) {
                if (!topWindow.isVisible()) {
                    topWindow.setVisible(true);
                }
                boolean alwaysOnTop = topWindow.isAlwaysOnTop();
                topWindow.setExtendedState(Frame.NORMAL);
                topWindow.toFront();
                topWindow.setAlwaysOnTop(true);
                try {
                    Point savedMouse = MouseInfo.getPointerInfo().getLocation();
                    Robot robot      = new Robot();
                    robot.mouseMove(topWindow.getX() + 100, topWindow.getY() + 10);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseMove(savedMouse.x, savedMouse.y);
                } catch (Exception ex) {
                    Log.warn(ex);
                } finally {
                    topWindow.setAlwaysOnTop(alwaysOnTop);
                }
            }
        }
    }

    /**
     * @param create Whether it should be created if it doesn't already exist.
     * @return The single instance of a special, hidden window that can be used for various
     *         operations that require a window before you actually have one available.
     */
    public static Frame getHiddenFrame(boolean create) {
        if (HIDDEN_FRAME == null && create) {
            HIDDEN_FRAME = new Frame();
            HIDDEN_FRAME.setUndecorated(true);
            HIDDEN_FRAME.setBounds(0, 0, 0, 0);
            HIDDEN_FRAME.setIconImages(Images.APP_ICON_LIST);
        }
        return HIDDEN_FRAME;
    }

    /** Repaints all visible windows. */
    public static void repaintAll() {
        for (Window window : Window.getWindows()) {
            if (window.isShowing()) {
                window.repaint();
            }
        }
    }
}
