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

package com.trollworks.gcs.datafile;

import com.trollworks.gcs.ui.widget.outline.ListRow;
import com.trollworks.gcs.ui.widget.outline.OutlineModel;
import com.trollworks.gcs.ui.widget.outline.Row;
import com.trollworks.gcs.utility.SaveType;
import com.trollworks.gcs.utility.json.JsonArray;
import com.trollworks.gcs.utility.json.JsonMap;
import com.trollworks.gcs.utility.json.JsonWriter;
import com.trollworks.gcs.utility.text.NumericComparator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/** A list of rows. */
public abstract class ListFile extends DataFile {
    private static final String       KEY_ROWS = "rows";
    private              OutlineModel mModel   = new OutlineModel();

    protected ListFile() {
        setSortingMarksDirty(false);
    }

    @Override
    protected void loadSelf(JsonMap m, LoadState state) throws IOException {
        loadList(m.getArray(KEY_ROWS), state);
    }

    /**
     * Called to load the individual rows.
     *
     * @param a     The {@link JsonArray} to load data from.
     * @param state The {@link LoadState} to use.
     */
    protected abstract void loadList(JsonArray a, LoadState state) throws IOException;

    @Override
    protected final void saveSelf(JsonWriter w, SaveType saveType) throws IOException {
        List<Row> rows = getTopLevelRows();
        if (!rows.isEmpty()) {
            w.key(KEY_ROWS);
            w.startArray();
            for (Row one : rows) {
                ((ListRow) one).save(w, saveType);
            }
            w.endArray();
        }
    }

    /** @return The top-level rows in this list. */
    public List<Row> getTopLevelRows() {
        return mModel.getTopLevelRows();
    }

    /** @return The outline model. */
    public OutlineModel getModel() {
        return mModel;
    }

    @Override
    public boolean isEmpty() {
        return mModel.getRowCount() == 0;
    }

    /** @return The set of categories that exist in this ListFile. */
    public List<String> getCategories() {
        Set<String> set = new TreeSet<>();
        for (Row row : getTopLevelRows()) {
            processRowForCategories(row, set);
        }
        List<String> list = new ArrayList<>(set);
        list.sort(NumericComparator.CASELESS_COMPARATOR);
        return list;
    }

    private static void processRowForCategories(Row row, Set<String> set) {
        if (row instanceof ListRow lr) {
            set.addAll(lr.getCategories());
        }
        if (row.hasChildren()) {
            for (Row child : row.getChildren()) {
                processRowForCategories(child, set);
            }
        }
    }
}
