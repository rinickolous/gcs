/*
 * Copyright ©1998-2023 by Richard A. Wilkes. All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, version 2.0. If a copy of the MPL was not distributed with
 * this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, version 2.0.
 */

package gurps_test

import (
	"testing"

	"github.com/richardwilkes/gcs/v5/model/gurps"
	"github.com/richardwilkes/toolbox/check"
)

func TestGURPSLengthConversion(t *testing.T) {
	check.Equal(t, `1"`, gurps.FeetAndInches.Format(gurps.LengthFromInteger(1, gurps.Inch)))
	check.Equal(t, `1'3"`, gurps.FeetAndInches.Format(gurps.LengthFromInteger(15, gurps.Inch)))
	check.Equal(t, "2.5 cm", gurps.Centimeter.Format(gurps.LengthFromStringForced("2.5", gurps.Centimeter)))
	check.Equal(t, "37.5 cm", gurps.Centimeter.Format(gurps.LengthFromStringForced("37.5", gurps.Centimeter)))

	w, err := gurps.LengthFromString("1", gurps.Inch)
	check.NoError(t, err)
	check.Equal(t, `1"`, gurps.FeetAndInches.Format(w))
	w, err = gurps.LengthFromString(`6'         2"`, gurps.Inch)
	check.NoError(t, err)
	check.Equal(t, `6'2"`, gurps.FeetAndInches.Format(w))
	w, err = gurps.LengthFromString(" +32   yd  ", gurps.Inch)
	check.NoError(t, err)
	check.Equal(t, "96'", gurps.FeetAndInches.Format(w))
	w, err = gurps.LengthFromString("0.5m", gurps.Inch)
	check.NoError(t, err)
	check.Equal(t, "50 cm", gurps.Centimeter.Format(w))
	w, err = gurps.LengthFromString("1cm", gurps.Inch)
	check.NoError(t, err)
	check.Equal(t, "1 cm", gurps.Centimeter.Format(w))
}
