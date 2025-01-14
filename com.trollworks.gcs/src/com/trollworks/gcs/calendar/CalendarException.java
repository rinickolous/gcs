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

package com.trollworks.gcs.calendar;

public class CalendarException extends Exception {
    public CalendarException(String message) {
        super(message);
    }

    public CalendarException(Exception exception) {
        super(exception);
    }

    public CalendarException(String message, Exception exception) {
        super(message, exception);
    }
}
