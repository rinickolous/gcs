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
import com.trollworks.gcs.ui.UIUtilities;
import com.trollworks.gcs.ui.border.LineBorder;

import java.awt.Component;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/** The drop-down panel used by {@link Search}. */
class SearchDropDown extends ScrollPanel implements MouseListener {
    private JList<Object>            mList;
    private EditorField              mFilterField;
    private SearchTarget             mTarget;
    private DefaultListModel<Object> mModel;

    /**
     * Creates a new drop-down panel for use with {@link Search}.
     *
     * @param renderer    The item renderer to use in the list.
     * @param filterField The text field the drop-down will appear to be attached to.
     * @param target      The search target.
     */
    SearchDropDown(ListCellRenderer<Object> renderer, EditorField filterField, SearchTarget target) {
        super(null);
        mFilterField = filterField;
        mTarget = target;
        mModel = new DefaultListModel<>();
        mList = new JList<>(mModel);
        mList.setBackground(Colors.SEARCH_LIST);
        mList.setForeground(Colors.ON_SEARCH_LIST);
        mList.setFocusable(false);
        mList.addMouseListener(this);
        mList.setCellRenderer(renderer);
        setViewportView(mList);
        setBorder(new LineBorder(Colors.DIVIDER));
        setVisible(false);
    }

    /** @return The currently selected values. */
    public List<Object> getSelectedValues() {
        return mList.getSelectedValuesList();
    }

    /**
     * Adjust the list to the specified collection of hits.
     *
     * @param hits The current collection of hits.
     */
    void adjustToHits(List<Object> hits) {
        mModel.removeAllElements();
        for (Object one : hits) {
            mModel.addElement(one);
        }
        Point where  = new Point(0, mFilterField.getHeight());
        int   count  = mModel.getSize();
        int   height = 0;
        if (count > 0) {
            Component renderer = mList.getCellRenderer().getListCellRendererComponent(mList, mList.getModel().getElementAt(0), 0, true, true);
            renderer.invalidate();
            height = 5 * renderer.getPreferredSize().height;
            Component component = mList;
            while (component != this) {
                if (component instanceof JComponent comp) {
                    Insets insets = comp.getInsets();
                    height += insets.top + insets.bottom;
                }
                component = component.getParent();
            }
        }
        UIUtilities.convertPoint(where, mFilterField, getParent());
        UIUtilities.revalidateImmediately(mFilterField);
        Insets insets = getInsets();
        setBounds(where.x, where.y, mFilterField.getWidth(), insets.top + height + insets.bottom);
        setVisible(count > 0);
        revalidate();
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if (event.getClickCount() > 1) {
            mTarget.searchSelect(getSelectedValues());
        }
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        // Not used.
    }

    @Override
    public void mouseExited(MouseEvent event) {
        // Not used.
    }

    @Override
    public void mousePressed(MouseEvent event) {
        // Not used.
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        // Not used.
    }

    void handleKeyPressed(KeyEvent event) {
        mList.dispatchEvent(event);
    }
}
