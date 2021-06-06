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

package com.trollworks.gcs.ui.widget.tree;

/** A row that can be used with a {@link TreeRoot}. */
public class TreeRow implements Cloneable {
    /** The TreeRow's parent. */
    protected TreeContainerRow mParent;
    private   int              mIndex;

    @Override
    protected TreeRow clone() {
        try {
            TreeRow other = (TreeRow) super.clone();
            other.mParent = null;
            other.mIndex = 0;
            return other;
        } catch (CloneNotSupportedException exception) {
            return null; // Not possible
        }
    }

    /** @return The TreeRow's parent. */
    public TreeContainerRow getParent() {
        return mParent;
    }

    /** @return The index of this TreeRow within its parent, or -1 if it has no parent. */
    public int getIndex() {
        return mParent == null ? -1 : mIndex;
    }

    void setIndex(int index) {
        mIndex = index;
    }

    /**
     * @param row The {@link TreeContainerRow} to check for.
     * @return Whether this TreeRow is a descendant of the specified {@link TreeContainerRow}.
     */
    public boolean isDescendantOf(TreeContainerRow row) {
        TreeContainerRow parent = mParent;
        while (parent != null) {
            if (parent == row) {
                return true;
            }
            parent = parent.mParent;
        }
        return false;
    }

    /** @return The number of {@link TreeContainerRow}s above this TreeRow. */
    public int getDepth() {
        TreeContainerRow parent = mParent;
        int              depth  = 0;
        while (parent != null) {
            depth++;
            parent = parent.mParent;
        }
        return depth;
    }

    /** @return The {@link TreeRoot} the TreeRow is currently residing within, if any. */
    public TreeRoot getTreeRoot() {
        TreeRow row = this;
        while (row != null) {
            if (row instanceof TreeRoot) {
                return (TreeRoot) row;
            }
            row = row.mParent;
        }
        return null;
    }

    /** @return The owning {@link TreePanel}, if any. */
    public TreePanel getOwner() {
        TreeRoot root = getTreeRoot();
        return (root != null) ? root.getOwner() : null;
    }
}
