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

package com.trollworks.gcs.ui;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

/**
 * Captures all mouse events in a {@link JRootPane} and redirects them to a particular component or
 * one of its children.
 */
public final class MouseCapture implements MouseListener, MouseMotionListener, HierarchyListener {
    private static final Map<Component, MouseCapture> MAP = new HashMap<>();
    private              Component                    mGlassPane;
    private              Component                    mCaptureComponent;

    /**
     * Starts redirecting all mouse events to the specified component or one of its children.
     *
     * @param target The target.
     * @param cursor The cursor to use while the mouse is captured.
     */
    public static void start(Component target, Cursor cursor) {
        start(target, target, cursor);
    }

    /**
     * Starts redirecting all mouse events to the specified component or one of its children.
     *
     * @param host   The host component, which determines the glass pane that will be used.
     * @param target The target.
     * @param cursor The cursor to use while the mouse is captured.
     */
    public static void start(Component host, Component target, Cursor cursor) {
        JRootPane rootPane = SwingUtilities.getRootPane(host);
        if (rootPane != null) {
            Component    glassPane = rootPane.getGlassPane();
            MouseCapture capture   = new MouseCapture(glassPane, target, cursor);
            MAP.put(target, capture);
            glassPane.setVisible(true);
        }
    }

    /**
     * Stops redirecting mouse events.
     *
     * @param target The target that was passed to one of the start calls.
     */
    public static void stop(Component target) {
        MouseCapture capture = MAP.remove(target);
        if (capture != null) {
            capture.finish();
        }
    }

    private MouseCapture(Component glassPane, Component capture, Cursor cursor) {
        mGlassPane = glassPane;
        mCaptureComponent = capture;
        mGlassPane.addMouseListener(this);
        mGlassPane.addMouseMotionListener(this);
        mGlassPane.addHierarchyListener(this);
        if (cursor != null) {
            mGlassPane.setCursor(cursor);
        }
    }

    private void finish() {
        mGlassPane.removeMouseListener(this);
        mGlassPane.removeMouseMotionListener(this);
        mGlassPane.removeHierarchyListener(this);
        mGlassPane.setCursor(null);
        mGlassPane.setVisible(false);
    }

    @Override
    public void hierarchyChanged(HierarchyEvent event) {
        if (!mCaptureComponent.isDisplayable()) {
            stop(mCaptureComponent);
        }
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        redispatchMouseEvent(event);
    }

    @Override
    public void mouseMoved(MouseEvent event) {
        redispatchMouseEvent(event);
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        redispatchMouseEvent(event);
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        redispatchMouseEvent(event);
    }

    @Override
    public void mouseExited(MouseEvent event) {
        redispatchMouseEvent(event);
    }

    @Override
    public void mousePressed(MouseEvent event) {
        redispatchMouseEvent(event);
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        redispatchMouseEvent(event);
    }

    private void redispatchMouseEvent(MouseEvent event) {
        Point     glassPanePoint = event.getPoint();
        Point     containerPoint = SwingUtilities.convertPoint(mGlassPane, glassPanePoint, mCaptureComponent);
        Component component      = SwingUtilities.getDeepestComponentAt(mCaptureComponent, containerPoint.x, containerPoint.y);
        if (component == null) {
            component = mCaptureComponent;
        }
        Point componentPoint = SwingUtilities.convertPoint(mGlassPane, glassPanePoint, component);
        component.dispatchEvent(new MouseEvent(component, event.getID(), event.getWhen(), event.getModifiersEx(), componentPoint.x, componentPoint.y, event.getClickCount(), event.isPopupTrigger()));
    }
}
