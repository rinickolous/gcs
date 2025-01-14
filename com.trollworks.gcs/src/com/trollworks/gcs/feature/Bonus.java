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

package com.trollworks.gcs.feature;

import com.trollworks.gcs.criteria.StringCriteria;
import com.trollworks.gcs.datafile.DataFile;
import com.trollworks.gcs.ui.widget.outline.ListRow;
import com.trollworks.gcs.utility.I18n;
import com.trollworks.gcs.utility.json.JsonMap;
import com.trollworks.gcs.utility.json.JsonWriter;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/** Describes a bonus. */
public abstract class Bonus extends Feature {
    private           LeveledAmount mAmount;
    // The "parent" item that is providing this particular bonus (for information only).
    private transient ListRow       mParent;

    /**
     * Creates a new bonus.
     *
     * @param amount The initial amount.
     */
    protected Bonus(double amount) {
        mAmount = new LeveledAmount(amount);
    }

    /**
     * Creates a new bonus.
     *
     * @param amount The initial amount.
     */
    protected Bonus(int amount) {
        mAmount = new LeveledAmount(amount);
    }

    /**
     * Creates a clone of the specified bonus.
     *
     * @param other The bonus to clone.
     */
    protected Bonus(Bonus other) {
        mAmount = new LeveledAmount(other.mAmount);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Bonus b) {
            return mAmount.equals(b.mAmount);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    protected void loadSelf(DataFile dataFile, JsonMap m) throws IOException {
        mAmount.load(m);
    }

    protected void saveSelf(JsonWriter w) throws IOException {
        mAmount.saveInline(w);
    }

    /** @return The leveled amount. */
    public LeveledAmount getAmount() {
        return mAmount;
    }

    @Override
    public void fillWithNameableKeys(Set<String> set) {
        // Nothing to do.
    }

    @Override
    public void applyNameableKeys(Map<String, String> map) {
        // Nothing to do.
    }

    public void setParent(ListRow parent) {
        mParent = parent;
    }

    public String getParentName() {
        return mParent == null ? I18n.text("Unknown") : mParent.toString();
    }

    public void addToToolTip(StringBuilder toolTip) {
        if (toolTip != null) {
            toolTip.append("\n").append(getParentName()).append(" [").append(getToolTipAmount()).append("]");
        }
    }

    public String getToolTipAmount() {
        return getAmount().getAmountAsString();
    }

    protected static boolean matchesCategories(StringCriteria criteria, Set<String> categories) {
        if (categories != null) {
            for (String category : categories) {
                if (criteria.matches(category)) {
                    return true;
                }
            }
        }
        return criteria.matches("");
    }
}
