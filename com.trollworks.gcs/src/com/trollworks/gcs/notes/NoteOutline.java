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

package com.trollworks.gcs.notes;

import com.trollworks.gcs.character.CollectedModels;
import com.trollworks.gcs.datafile.DataFile;
import com.trollworks.gcs.ui.widget.outline.ListOutline;
import com.trollworks.gcs.ui.widget.outline.ListRow;
import com.trollworks.gcs.ui.widget.outline.OutlineModel;
import com.trollworks.gcs.ui.widget.outline.Row;
import com.trollworks.gcs.ui.widget.outline.RowPostProcessor;

import java.awt.EventQueue;
import java.awt.dnd.DropTargetDragEvent;
import java.util.ArrayList;
import java.util.List;

/** An outline specifically for notes. */
public class NoteOutline extends ListOutline {
    /**
     * Create a new notes outline.
     *
     * @param owner The owning data file.
     */
    public NoteOutline(CollectedModels owner) {
        this(owner, owner.getNotesModel());
    }

    /**
     * Create a new notes outline.
     *
     * @param dataFile The owning data file.
     * @param model    The {@link OutlineModel} to use.
     */
    public NoteOutline(DataFile dataFile, OutlineModel model) {
        super(dataFile, model);
        NoteColumn.addColumns(this, dataFile);
    }

    @Override
    protected boolean isRowDragAcceptable(DropTargetDragEvent dtde, Row[] rows) {
        return !getModel().isLocked() && rows.length > 0 && rows[0] instanceof Note;
    }

    @Override
    public void convertDragRowsToSelf(List<Row> list) {
        OutlineModel       model              = getModel();
        Row[]              rows               = model.getDragRows();
        boolean            forSheetOrTemplate = mDataFile instanceof CollectedModels;
        ArrayList<ListRow> process            = new ArrayList<>();
        for (Row element : rows) {
            Note note = new Note(mDataFile, (Note) element, true);
            model.collectRowsAndSetOwner(list, note, false);
            if (forSheetOrTemplate) {
                addRowsToBeProcessed(process, note);
            }
        }
        if (forSheetOrTemplate && !process.isEmpty()) {
            EventQueue.invokeLater(new RowPostProcessor(this, process));
        }
    }
}
