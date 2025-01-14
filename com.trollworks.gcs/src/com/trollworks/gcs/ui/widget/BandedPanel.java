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

import com.trollworks.gcs.ui.Colors;
import com.trollworks.gcs.ui.GraphicsUtilities;
import com.trollworks.gcs.ui.UIUtilities;
import com.trollworks.gcs.ui.layout.ColumnLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

/** A simple panel that draws banded colors behind its contents. */
public class BandedPanel extends ActionPanel implements Scrollable {
    private String  mTitle;
    private boolean mForceTrackWidth;

    /**
     * Creates a new BandedPanel.
     *
     * @param title The title for this panel.
     */
    public BandedPanel(String title) {
        super(new ColumnLayout(1, 0, 0));
        mTitle = title;
    }

    /**
     * Creates a new BandedPanel.
     *
     * @param forceTrackWidth {@code true} if the width should always track the viewport.
     */
    public BandedPanel(boolean forceTrackWidth) {
        this("");
        mForceTrackWidth = forceTrackWidth;
    }

    @Override
    protected void paintComponent(Graphics gc) {
        super.paintComponent(GraphicsUtilities.prepare(gc));
        Rectangle bounds = getBounds();
        bounds.x = 0;
        bounds.y = 0;
        int step  = getStep();
        int count = getComponentCount();
        for (int i = 0; i < count; i += step) {
            Rectangle compBounds = getComponent(i).getBounds();
            for (int j = i + 1; j < i + step && j < count; j++) {
                compBounds = compBounds.union(getComponent(j).getBounds());
            }
            bounds.y = compBounds.y;
            bounds.height = compBounds.height;
            int logical = i / step;
            gc.setColor(getBandingColor(logical % 2 != 0));
            gc.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    protected Color getBandingColor(boolean odd) {
        return odd ? Colors.BANDING : Colors.CONTENT;
    }

    private int getStep() {
        return (getLayout() instanceof ColumnCounter cc) ? cc.getColumns() : 1;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return orientation == SwingConstants.VERTICAL ? visibleRect.height : visibleRect.width;
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        int height = 10;
        int count  = getComponentCount();
        if (count > 0) {
            count = Math.min(getStep(), count);
            for (int i = 0; i < count; i++) {
                int tmp = getComponent(i).getHeight();
                if (tmp > height) {
                    height = tmp;
                }
            }
        }
        return height;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return UIUtilities.shouldTrackViewportHeight(this);
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return mForceTrackWidth || UIUtilities.shouldTrackViewportWidth(this);
    }

    @Override
    public String toString() {
        return mTitle;
    }
}
