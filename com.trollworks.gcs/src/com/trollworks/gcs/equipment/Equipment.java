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

package com.trollworks.gcs.equipment;

import com.trollworks.gcs.character.CollectedListRow;
import com.trollworks.gcs.character.CollectedOutlines;
import com.trollworks.gcs.character.GURPSCharacter;
import com.trollworks.gcs.datafile.DataFile;
import com.trollworks.gcs.datafile.LoadState;
import com.trollworks.gcs.feature.ContainedWeightReduction;
import com.trollworks.gcs.feature.Feature;
import com.trollworks.gcs.menu.item.HasSourceReference;
import com.trollworks.gcs.modifier.EquipmentModifier;
import com.trollworks.gcs.modifier.EquipmentModifierCostType;
import com.trollworks.gcs.modifier.EquipmentModifierWeightType;
import com.trollworks.gcs.modifier.Fraction;
import com.trollworks.gcs.modifier.Modifier;
import com.trollworks.gcs.modifier.ModifierCostValueType;
import com.trollworks.gcs.modifier.ModifierWeightValueType;
import com.trollworks.gcs.settings.SheetSettings;
import com.trollworks.gcs.skill.SkillDefault;
import com.trollworks.gcs.template.Template;
import com.trollworks.gcs.ui.widget.outline.Column;
import com.trollworks.gcs.ui.widget.outline.ListOutline;
import com.trollworks.gcs.ui.widget.outline.ListRow;
import com.trollworks.gcs.ui.widget.outline.RowEditor;
import com.trollworks.gcs.utility.FileType;
import com.trollworks.gcs.utility.Filtered;
import com.trollworks.gcs.utility.Fixed6;
import com.trollworks.gcs.utility.I18n;
import com.trollworks.gcs.utility.Log;
import com.trollworks.gcs.utility.SaveType;
import com.trollworks.gcs.utility.json.JsonArray;
import com.trollworks.gcs.utility.json.JsonMap;
import com.trollworks.gcs.utility.json.JsonWriter;
import com.trollworks.gcs.utility.units.WeightUnits;
import com.trollworks.gcs.utility.units.WeightValue;
import com.trollworks.gcs.weapon.WeaponStats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.Icon;

/** A piece of equipment. */
public class Equipment extends CollectedListRow implements HasSourceReference {
    public static final  String KEY_EQUIPMENT                = "equipment";
    public static final  String KEY_EQUIPMENT_CONTAINER      = "equipment_container";
    private static final String KEY_WEAPONS                  = "weapons";
    private static final String KEY_MODIFIERS                = "modifiers";
    private static final String KEY_IGNORE_WEIGHT_FOR_SKILLS = "ignore_weight_for_skills";
    private static final String KEY_EQUIPPED                 = "equipped";
    private static final String KEY_QUANTITY                 = "quantity";
    private static final String KEY_USES                     = "uses";
    private static final String KEY_MAX_USES                 = "max_uses";
    private static final String KEY_DESCRIPTION              = "description";
    private static final String KEY_TL                       = "tech_level";
    private static final String KEY_LC                       = "legality_class";
    private static final String KEY_VALUE                    = "value";
    private static final String KEY_WEIGHT                   = "weight";
    private static final String KEY_REFERENCE                = "reference";
    private static final String DEFAULT_LC                   = "4";
    private static final Fixed6 MIN_CF                       = new Fixed6("-0.8", Fixed6.ZERO, false);

    private boolean                 mEquipped;
    private int                     mQuantity;
    private int                     mUses;
    private int                     mMaxUses;
    private String                  mDescription;
    private String                  mTechLevel;
    private String                  mLegalityClass;
    private Fixed6                  mValue;
    private WeightValue             mWeight;
    private boolean                 mWeightIgnoredForSkills;
    private Fixed6                  mExtendedValue;
    private WeightValue             mExtendedWeight;
    private WeightValue             mExtendedWeightForSkills;
    private String                  mReference;
    private List<WeaponStats>       mWeapons;
    private List<EquipmentModifier> mModifiers;

    /**
     * Creates a new equipment.
     *
     * @param dataFile    The data file to associate it with.
     * @param isContainer Whether or not this row allows children.
     */
    public Equipment(DataFile dataFile, boolean isContainer) {
        super(dataFile, isContainer);
        mEquipped = true;
        mQuantity = 1;
        mDescription = I18n.text("Equipment");
        mTechLevel = "";
        mLegalityClass = DEFAULT_LC;
        mReference = "";
        mValue = Fixed6.ZERO;
        mExtendedValue = Fixed6.ZERO;
        mWeightIgnoredForSkills = false;
        mWeight = new WeightValue(Fixed6.ZERO, dataFile.getSheetSettings().defaultWeightUnits());
        mExtendedWeight = new WeightValue(mWeight);
        mExtendedWeightForSkills = new WeightValue(mWeight);
        mWeapons = new ArrayList<>();
        mModifiers = new ArrayList<>();
    }

    /**
     * Creates a clone of an existing equipment and associates it with the specified data file.
     *
     * @param dataFile  The data file to associate it with.
     * @param equipment The equipment to clone.
     * @param deep      Whether or not to clone the children, grandchildren, etc.
     */
    public Equipment(DataFile dataFile, Equipment equipment, boolean deep) {
        super(dataFile, equipment);
        boolean forTemplate = dataFile instanceof Template;
        boolean forSheet    = dataFile instanceof GURPSCharacter;
        mEquipped = !forSheet || equipment.mEquipped;
        mQuantity = (forSheet || forTemplate) ? equipment.mQuantity : 1;
        mUses = (forSheet || forTemplate) ? equipment.mUses : equipment.mMaxUses;
        mMaxUses = equipment.mMaxUses;
        mDescription = equipment.mDescription;
        mTechLevel = equipment.mTechLevel;
        mLegalityClass = equipment.mLegalityClass;
        mValue = equipment.mValue;
        mWeight = new WeightValue(equipment.mWeight);
        mWeightIgnoredForSkills = equipment.mWeightIgnoredForSkills;
        mReference = equipment.mReference;
        mWeapons = new ArrayList<>(equipment.mWeapons.size());
        for (WeaponStats weapon : equipment.mWeapons) {
            mWeapons.add(weapon.clone(this));
        }
        mModifiers = new ArrayList<>(equipment.mModifiers.size());
        for (EquipmentModifier modifier : equipment.mModifiers) {
            mModifiers.add(new EquipmentModifier(mDataFile, modifier, false));
        }
        mExtendedValue = new Fixed6(mQuantity).mul(getAdjustedValue());
        mExtendedWeight = new WeightValue(getAdjustedWeight(false));
        mExtendedWeight.setValue(mExtendedWeight.getValue().mul(new Fixed6(mQuantity)));
        mExtendedWeightForSkills = new WeightValue(getAdjustedWeight(true));
        mExtendedWeightForSkills.setValue(mExtendedWeightForSkills.getValue().mul(new Fixed6(mQuantity)));
        if (deep) {
            int count = equipment.getChildCount();
            for (int i = 0; i < count; i++) {
                addChild(new Equipment(dataFile, (Equipment) equipment.getChild(i), true));
            }
        }
    }

    /**
     * Loads an equipment and associates it with the specified data file.
     *
     * @param dataFile The data file to associate it with.
     * @param m        The {@link JsonMap} to load from.
     * @param state    The {@link LoadState} to use.
     */
    public Equipment(DataFile dataFile, JsonMap m, LoadState state) throws IOException {
        this(dataFile, m.getString(DataFile.TYPE).equals(KEY_EQUIPMENT_CONTAINER));
        load(dataFile, m, state);
    }

    @Override
    public Equipment cloneRow(DataFile newOwner, boolean deep, boolean forSheet) {
        return new Equipment(newOwner, this, deep);
    }

    @Override
    public ListOutline getOutlineFromCollectedOutlines(CollectedOutlines outlines) {
        return getOwner().getProperty(EquipmentList.KEY_OTHER_ROOT) != null ? outlines.getOtherEquipmentOutline() : outlines.getEquipmentOutline();
    }

    @Override
    public boolean isEquivalentTo(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Equipment row && super.isEquivalentTo(obj)) {
            if (mQuantity == row.mQuantity && mUses == row.mUses && mMaxUses == row.mMaxUses && mValue.equals(row.mValue) && mEquipped == row.mEquipped && mWeightIgnoredForSkills == row.mWeightIgnoredForSkills && mWeight.equals(row.mWeight) && mDescription.equals(row.mDescription) && mTechLevel.equals(row.mTechLevel) && mLegalityClass.equals(row.mLegalityClass) && mReference.equals(row.mReference)) {
                if (mWeapons.equals(row.mWeapons)) {
                    return mModifiers.equals(row.mModifiers);
                }
            }
        }
        return false;
    }

    @Override
    public String getLocalizedName() {
        return I18n.text("Equipment");
    }

    @Override
    public String getJSONTypeName() {
        return canHaveChildren() ? KEY_EQUIPMENT_CONTAINER : KEY_EQUIPMENT;
    }

    @Override
    public String getRowType() {
        return I18n.text("Equipment");
    }

    @Override
    protected void prepareForLoad(LoadState state) {
        super.prepareForLoad(state);
        mEquipped = true;
        mQuantity = 1;
        mUses = 0;
        mMaxUses = 0;
        mDescription = I18n.text("Equipment");
        mTechLevel = "";
        mLegalityClass = DEFAULT_LC;
        mReference = "";
        mValue = Fixed6.ZERO;
        mWeight.setValue(Fixed6.ZERO);
        mWeightIgnoredForSkills = false;
        mWeapons = new ArrayList<>();
        mModifiers = new ArrayList<>();
    }

    @Override
    protected void finishedLoading(LoadState state) {
        if (mMaxUses < 0) {
            mMaxUses = 0;
        }
        if (mUses > mMaxUses) {
            mUses = mMaxUses;
        } else if (mUses < 0) {
            mUses = 0;
        }
        update();
        super.finishedLoading(state);
    }

    @Override
    protected void loadSelf(JsonMap m, LoadState state) throws IOException {
        if (mDataFile instanceof GURPSCharacter) {
            mEquipped = m.getBoolean(KEY_EQUIPPED);
        }
        if (!canHaveChildren()) {
            mQuantity = m.getInt(KEY_QUANTITY);
        }
        mDescription = m.getString(KEY_DESCRIPTION);
        mTechLevel = m.getString(KEY_TL);
        mLegalityClass = m.getStringWithDefault(KEY_LC, DEFAULT_LC);
        mValue = new Fixed6(m.getString(KEY_VALUE), Fixed6.ZERO, false);
        mWeightIgnoredForSkills = m.getBoolean(KEY_IGNORE_WEIGHT_FOR_SKILLS);
        mWeight = WeightValue.extract(m.getString(KEY_WEIGHT), false);
        mReference = m.getString(KEY_REFERENCE);
        mUses = m.getInt(KEY_USES);
        mMaxUses = m.getInt(KEY_MAX_USES);
        if (m.has(KEY_WEAPONS)) {
            WeaponStats.loadFromJSONArray(this, m.getArray(KEY_WEAPONS), mWeapons);
        }
        if (m.has(KEY_MODIFIERS)) {
            JsonArray a     = m.getArray(KEY_MODIFIERS);
            int       count = a.size();
            for (int i = 0; i < count; i++) {
                mModifiers.add(new EquipmentModifier(getDataFile(), a.getMap(i), state));
            }
        }
    }

    @Override
    protected void loadChild(JsonMap m, LoadState state) throws IOException {
        if (!state.mForUndo) {
            String type = m.getString(DataFile.TYPE);
            if (KEY_EQUIPMENT.equals(type) || KEY_EQUIPMENT_CONTAINER.equals(type)) {
                addChild(new Equipment(mDataFile, m, state));
            } else {
                Log.warn("invalid child type: " + type);
            }
        }
    }

    @Override
    protected void saveSelf(JsonWriter w, SaveType saveType) throws IOException {
        if (mDataFile instanceof GURPSCharacter) {
            w.keyValue(KEY_EQUIPPED, mEquipped);
        }
        if (!canHaveChildren()) {
            w.keyValueNot(KEY_QUANTITY, mQuantity, 0);
        }
        w.keyValueNot(KEY_DESCRIPTION, mDescription, "");
        w.keyValueNot(KEY_TL, mTechLevel, "");
        w.keyValueNot(KEY_LC, mLegalityClass, DEFAULT_LC);
        w.keyValue(KEY_VALUE, mValue.toString());
        if (mWeightIgnoredForSkills) {
            w.keyValue(KEY_IGNORE_WEIGHT_FOR_SKILLS, true);
        }
        w.keyValue(KEY_WEIGHT, mWeight.toString(false));
        w.keyValueNot(KEY_REFERENCE, mReference, "");
        w.keyValueNot(KEY_USES, mUses, 0);
        w.keyValueNot(KEY_MAX_USES, mMaxUses, 0);
        WeaponStats.saveList(w, KEY_WEAPONS, mWeapons);
        saveList(w, KEY_MODIFIERS, mModifiers, saveType);

        // Emit the calculated values for third parties
        w.key("calc");
        w.startMap();
        w.keyValue("extended_value", mExtendedValue.toString());
        w.keyValue("extended_weight", mExtendedWeight.toString(false));
        if (mWeightIgnoredForSkills) {
            w.keyValue("extended_weight_for_skills", mExtendedWeightForSkills.toString(false));
        }
        w.endMap();
    }

    @Override
    public void update() {
        updateExtendedValue();
        updateExtendedWeight();
    }

    private void updateExtendedValue() {
        Fixed6 savedValue = mExtendedValue;
        int    count      = getChildCount();
        mExtendedValue = new Fixed6(mQuantity).mul(getAdjustedValue());
        for (int i = 0; i < count; i++) {
            Equipment child = (Equipment) getChild(i);
            child.updateExtendedValue();
            mExtendedValue = mExtendedValue.add(child.mExtendedValue);
        }
        if (!mExtendedValue.equals(savedValue)) {
            notifyOfChange();
        }
    }

    private void updateExtendedWeight() {
        WeightValue saved          = mExtendedWeight;
        WeightValue savedForSkills = mExtendedWeightForSkills;
        int         count          = getChildCount();
        WeightUnits units          = mWeight.getUnits();
        mExtendedWeight = new WeightValue(getAdjustedWeight(false).getValue().mul(new Fixed6(mQuantity)), units);
        mExtendedWeightForSkills = new WeightValue(getAdjustedWeight(true).getValue().mul(new Fixed6(mQuantity)), units);
        WeightValue   contained          = new WeightValue(Fixed6.ZERO, units);
        WeightValue   containedForSkills = new WeightValue(Fixed6.ZERO, units);
        SheetSettings sheetSettings      = mDataFile.getSheetSettings();
        boolean       useSimpleMetric    = sheetSettings.useSimpleMetricConversions();
        for (int i = 0; i < count; i++) {
            Equipment one = (Equipment) getChild(i);
            one.updateExtendedWeight();
            WeightValue weight = one.mExtendedWeight;
            if (useSimpleMetric) {
                weight = units.isMetric() ? GURPSCharacter.convertToGurpsMetric(weight) : GURPSCharacter.convertFromGurpsMetric(weight);
            }
            contained.add(weight);
            weight = one.mExtendedWeightForSkills;
            if (useSimpleMetric) {
                weight = units.isMetric() ? GURPSCharacter.convertToGurpsMetric(weight) : GURPSCharacter.convertFromGurpsMetric(weight);
            }
            containedForSkills.add(weight);
        }
        Fixed6      percentage         = Fixed6.ZERO;
        WeightValue reduction          = new WeightValue(Fixed6.ZERO, units);
        WeightUnits defaultWeightUnits = sheetSettings.defaultWeightUnits();
        for (Feature feature : getFeatures()) {
            if (feature instanceof ContainedWeightReduction cwr) {
                if (cwr.isPercentage()) {
                    percentage = percentage.add(new Fixed6(cwr.getPercentageReduction()));
                } else {
                    reduction.add(cwr.getAbsoluteReduction(defaultWeightUnits));
                }
            }
        }
        for (EquipmentModifier modifier : getModifiers()) {
            if (modifier.isEnabled()) {
                for (Feature feature : modifier.getFeatures()) {
                    if (feature instanceof ContainedWeightReduction cwr) {
                        if (cwr.isPercentage()) {
                            percentage = percentage.add(new Fixed6(cwr.getPercentageReduction()));
                        } else {
                            reduction.add(cwr.getAbsoluteReduction(defaultWeightUnits));
                        }
                    }
                }
            }
        }
        if (percentage.greaterThan(Fixed6.ZERO)) {
            Fixed6 oneHundred = new Fixed6(100);
            if (percentage.greaterThanOrEqual(oneHundred)) {
                contained = new WeightValue(Fixed6.ZERO, units);
                containedForSkills = new WeightValue(Fixed6.ZERO, units);
            } else {
                contained.subtract(new WeightValue(contained.getValue().mul(percentage).div(oneHundred), contained.getUnits()));
                containedForSkills.subtract(new WeightValue(containedForSkills.getValue().mul(percentage).div(oneHundred), containedForSkills.getUnits()));
            }
        }
        contained.subtract(reduction);
        containedForSkills.subtract(reduction);
        if (contained.getNormalizedValue().greaterThan(Fixed6.ZERO)) {
            mExtendedWeight.add(contained);
        }
        if (containedForSkills.getNormalizedValue().greaterThan(Fixed6.ZERO)) {
            mExtendedWeightForSkills.add(containedForSkills);
        }
        if (!saved.equals(mExtendedWeight) || !savedForSkills.equals(mExtendedWeightForSkills)) {
            notifyOfChange();
        }
    }

    /** @return The quantity. */
    public int getQuantity() {
        return mQuantity;
    }

    /**
     * @param quantity The quantity to set.
     * @return Whether it was modified.
     */
    public boolean setQuantity(int quantity) {
        if (quantity != mQuantity) {
            mQuantity = quantity;
            notifyOfChange();
            return true;
        }
        return false;
    }

    /** @return The number of times this item can be used. */
    public int getUses() {
        return mUses;
    }

    /** @param uses The number of times this item can be used. */
    public boolean setUses(int uses) {
        if (uses > mMaxUses) {
            uses = mMaxUses;
        } else if (uses < 0) {
            uses = 0;
        }
        if (uses != mUses) {
            mUses = uses;
            notifyOfChange();
            return true;
        }
        return false;
    }

    /** @return The maximum number of times this item can be used. */
    public int getMaxUses() {
        return mMaxUses;
    }

    /** @param maxUses The maximum number of times this item can be used. */
    public boolean setMaxUses(int maxUses) {
        if (maxUses < 0) {
            maxUses = 0;
        }
        if (maxUses != mMaxUses) {
            mMaxUses = maxUses;
            if (mMaxUses > mUses) {
                mUses = mMaxUses;
            }
            notifyOfChange();
            return true;
        }
        return false;
    }

    /** @return The description. */
    public String getDescription() {
        return mDescription;
    }

    /**
     * @param description The description to set.
     * @return Whether it was modified.
     */
    public boolean setDescription(String description) {
        if (!mDescription.equals(description)) {
            mDescription = description;
            notifyOfChange();
            return true;
        }
        return false;
    }

    /** @return The tech level. */
    public String getTechLevel() {
        return mTechLevel;
    }

    /**
     * @param techLevel The tech level to set.
     * @return Whether it was modified.
     */
    public boolean setTechLevel(String techLevel) {
        if (!mTechLevel.equals(techLevel)) {
            mTechLevel = techLevel;
            notifyOfChange();
            return true;
        }
        return false;
    }

    /** @return The legality class. */
    public String getLegalityClass() {
        return mLegalityClass;
    }

    public String getDisplayLegalityClass() {
        String lc = getLegalityClass().trim();
        return switch (lc) {
            case "0" -> I18n.text("LC0: Banned");
            case "1" -> I18n.text("LC1: Military");
            case "2" -> I18n.text("LC2: Restricted");
            case "3" -> I18n.text("LC3: Licensed");
            case "4" -> I18n.text("LC4: Open");
            default -> lc;
        };
    }

    /**
     * @param legalityClass The legality class to set.
     * @return Whether it was modified.
     */
    public boolean setLegalityClass(String legalityClass) {
        if (!mLegalityClass.equals(legalityClass)) {
            mLegalityClass = legalityClass;
            notifyOfChange();
            return true;
        }
        return false;
    }

    /** @return The value after any cost adjustments. */
    public Fixed6 getAdjustedValue() {
        return getValueAdjustedForModifiers(mValue, getModifiers());
    }

    /**
     * @param value     The base value to adjust.
     * @param modifiers The modifiers to apply.
     * @return The adjusted value.
     */
    public static Fixed6 getValueAdjustedForModifiers(Fixed6 value, List<EquipmentModifier> modifiers) {
        // Apply all EquipmentModifierCostType.TO_ORIGINAL_COST
        Fixed6 cost = processNonCFStep(EquipmentModifierCostType.TO_ORIGINAL_COST, value, modifiers);

        // Apply all EquipmentModifierCostType.TO_BASE_COST
        Fixed6 cf = Fixed6.ZERO;
        for (EquipmentModifier modifier : modifiers) {
            if (modifier.isEnabled() && modifier.getCostAdjType() == EquipmentModifierCostType.TO_BASE_COST) {
                String                adj = modifier.getCostAdjAmount();
                ModifierCostValueType mvt = EquipmentModifierCostType.TO_BASE_COST.determineType(adj);
                Fixed6                amt = mvt.extractValue(adj, false);
                if (mvt == ModifierCostValueType.MULTIPLIER) {
                    amt = amt.sub(Fixed6.ONE);
                }
                cf = cf.add(amt);
            }
        }
        if (!cf.equals(Fixed6.ZERO)) {
            if (cf.lessThan(MIN_CF)) {
                cf = MIN_CF;
            }
            cost = cost.mul(cf.add(Fixed6.ONE));
        }

        // Apply all EquipmentModifierCostType.TO_FINAL_BASE_COST
        cost = processNonCFStep(EquipmentModifierCostType.TO_FINAL_BASE_COST, cost, modifiers);

        // Apply all EquipmentModifierCostType.TO_FINAL_COST
        cost = processNonCFStep(EquipmentModifierCostType.TO_FINAL_COST, cost, modifiers);
        return cost.greaterThanOrEqual(Fixed6.ZERO) ? cost : Fixed6.ZERO;
    }

    private static Fixed6 processNonCFStep(EquipmentModifierCostType costType, Fixed6 value, List<EquipmentModifier> modifiers) {
        Fixed6 percentages = Fixed6.ZERO;
        Fixed6 additions   = Fixed6.ZERO;
        Fixed6 cost        = value;
        for (EquipmentModifier modifier : modifiers) {
            if (modifier.isEnabled() && modifier.getCostAdjType() == costType) {
                String                adj = modifier.getCostAdjAmount();
                ModifierCostValueType mvt = costType.determineType(adj);
                Fixed6                amt = mvt.extractValue(adj, false);
                switch (mvt) {
                    case ADDITION -> additions = additions.add(amt);
                    case PERCENTAGE -> percentages = percentages.add(amt);
                    case MULTIPLIER -> cost = cost.mul(amt);
                }
            }
        }
        cost = cost.add(additions);
        if (!percentages.equals(Fixed6.ZERO)) {
            cost = cost.add(value.mul(percentages.div(new Fixed6(100))));
        }
        return cost;
    }

    /** @return The value. */
    public Fixed6 getValue() {
        return mValue;
    }

    /**
     * @param value The value to set.
     * @return Whether it was modified.
     */
    public boolean setValue(Fixed6 value) {
        if (!mValue.equals(value)) {
            mValue = value;
            notifyOfChange();
            return true;
        }
        return false;
    }

    /** @return The extended value. */
    public Fixed6 getExtendedValue() {
        return mExtendedValue;
    }

    /** @return The weight after any adjustments. */
    public WeightValue getAdjustedWeight(boolean forSkills) {
        if (forSkills && mWeightIgnoredForSkills) {
            return new WeightValue(Fixed6.ZERO, mWeight.getUnits());
        }
        return getWeightAdjustedForModifiers(mWeight, getModifiers());
    }

    /**
     * @param weight    The base weight to adjust.
     * @param modifiers The modifiers to apply.
     * @return The adjusted value.
     */
    public WeightValue getWeightAdjustedForModifiers(WeightValue weight, List<EquipmentModifier> modifiers) {
        WeightUnits defUnits = getDataFile().getSheetSettings().defaultWeightUnits();
        weight = new WeightValue(weight);

        // Apply all EquipmentModifierWeightType.TO_ORIGINAL_COST
        Fixed6      percentages = Fixed6.ZERO;
        WeightValue original    = new WeightValue(weight);
        for (EquipmentModifier modifier : modifiers) {
            if (modifier.isEnabled() && modifier.getWeightAdjType() == EquipmentModifierWeightType.TO_ORIGINAL_WEIGHT) {
                String                  adj = modifier.getWeightAdjAmount();
                ModifierWeightValueType mvt = EquipmentModifierWeightType.TO_ORIGINAL_WEIGHT.determineType(adj);
                Fixed6                  amt = mvt.extractFraction(adj, false).value();
                if (mvt == ModifierWeightValueType.ADDITION) {
                    weight.add(new WeightValue(amt, ModifierWeightValueType.extractUnits(adj, defUnits)));
                } else {
                    percentages = percentages.add(amt);
                }
            }
        }
        if (!percentages.equals(Fixed6.ZERO)) {
            original.setValue(original.getValue().mul(percentages.div(new Fixed6(100))));
            weight.add(original);
        }

        // Apply all EquipmentModifierWeightType.TO_BASE_COST
        weight = processMultiplyAddWeightStep(EquipmentModifierWeightType.TO_BASE_WEIGHT, weight, defUnits, modifiers);

        // Apply all EquipmentModifierWeightType.TO_FINAL_BASE_COST
        weight = processMultiplyAddWeightStep(EquipmentModifierWeightType.TO_FINAL_BASE_WEIGHT, weight, defUnits, modifiers);

        // Apply all EquipmentModifierWeightType.TO_FINAL_COST
        weight = processMultiplyAddWeightStep(EquipmentModifierWeightType.TO_FINAL_WEIGHT, weight, defUnits, modifiers);
        if (weight.getValue().lessThan(Fixed6.ZERO)) {
            weight.setValue(Fixed6.ZERO);
        }
        return weight;
    }

    private static WeightValue processMultiplyAddWeightStep(EquipmentModifierWeightType weightType, WeightValue weight, WeightUnits defUnits, List<EquipmentModifier> modifiers) {
        weight = new WeightValue(weight);
        WeightValue sum = new WeightValue(Fixed6.ZERO, weight.getUnits());
        for (EquipmentModifier modifier : modifiers) {
            if (modifier.isEnabled() && modifier.getWeightAdjType() == weightType) {
                String                  adj      = modifier.getWeightAdjAmount();
                ModifierWeightValueType mvt      = weightType.determineType(adj);
                Fraction                fraction = mvt.extractFraction(adj, false);
                switch (mvt) {
                    case MULTIPLIER -> weight.setValue(weight.getValue().mul(fraction.mNumerator).div(fraction.mDenominator));
                    case PERCENTAGE_MULTIPLIER -> weight.setValue(weight.getValue().mul(fraction.mNumerator).div(fraction.mDenominator.mul(new Fixed6(100))));
                    case ADDITION -> sum.add(new WeightValue(fraction.value(), ModifierWeightValueType.extractUnits(adj, defUnits)));
                }
            }
        }
        weight.add(sum);
        return weight;
    }

    /** @return The weight. */
    public WeightValue getWeight() {
        return mWeight;
    }

    /**
     * @param weight The weight to set.
     * @return Whether it was modified.
     */
    public boolean setWeight(WeightValue weight) {
        if (!mWeight.equals(weight)) {
            mWeight = new WeightValue(weight);
            notifyOfChange();
            return true;
        }
        return false;
    }

    /** @return The extended weight. */
    public WeightValue getExtendedWeight(boolean forSkills) {
        return forSkills ? mExtendedWeightForSkills : mExtendedWeight;
    }

    /** @return Whether skills ignore the weight of this equipment for encumbrance calculations. */
    public boolean isWeightIgnoredForSkills() {
        return mWeightIgnoredForSkills;
    }

    /**
     * @param ignore Whether skills ignore the weight of this equipment for encumbrance
     *               calculations.
     * @return Whether it was changed.
     */
    public boolean setWeightIgnoredForSkills(boolean ignore) {
        if (mWeightIgnoredForSkills != ignore) {
            mWeightIgnoredForSkills = ignore;
            notifyOfChange();
            return true;
        }
        return false;
    }

    /** @return Whether this item is equipped. */
    public boolean isEquipped() {
        return mEquipped;
    }

    /**
     * @param equipped The new equipped state.
     * @return Whether it was changed.
     */
    public boolean setEquipped(boolean equipped) {
        if (mEquipped != equipped) {
            mEquipped = equipped;
            notifyOfChange();
            return true;
        }
        return false;
    }

    @Override
    public String getReference() {
        return mReference;
    }

    @Override
    public String getReferenceHighlight() {
        return getDescription();
    }

    @Override
    public boolean setReference(String reference) {
        if (!mReference.equals(reference)) {
            mReference = reference;
            notifyOfChange();
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(String text, boolean lowerCaseOnly) {
        if (getDescription().toLowerCase().contains(text)) {
            return true;
        }
        return super.contains(text, lowerCaseOnly);
    }

    @Override
    public Object getData(Column column) {
        return EquipmentColumn.values()[column.getID()].getData(this);
    }

    @Override
    public String getDataAsText(Column column) {
        return EquipmentColumn.values()[column.getID()].getDataAsText(this);
    }

    @Override
    public String toString() {
        return getDescription();
    }

    /** @return The weapon list. */
    public List<WeaponStats> getWeapons() {
        return Collections.unmodifiableList(mWeapons);
    }

    /**
     * @param weapons The weapons to set.
     * @return Whether it was modified.
     */
    public boolean setWeapons(List<WeaponStats> weapons) {
        if (!mWeapons.equals(weapons)) {
            mWeapons = new ArrayList<>(weapons);
            for (WeaponStats weapon : mWeapons) {
                weapon.setOwner(this);
            }
            notifyOfChange();
            return true;
        }
        return false;
    }

    @Override
    public Icon getIcon() {
        return FileType.EQUIPMENT.getIcon();
    }

    @Override
    public RowEditor<? extends ListRow> createEditor() {
        return new EquipmentEditor(this, getOwner().getProperty(EquipmentList.KEY_OTHER_ROOT) == null);
    }

    @Override
    public void fillWithNameableKeys(Set<String> set) {
        super.fillWithNameableKeys(set);
        extractNameables(set, mDescription);
        for (WeaponStats weapon : mWeapons) {
            for (SkillDefault one : weapon.getDefaults()) {
                one.fillWithNameableKeys(set);
            }
        }
        for (EquipmentModifier modifier : mModifiers) {
            modifier.fillWithNameableKeys(set);
        }
    }

    @Override
    public void applyNameableKeys(Map<String, String> map) {
        super.applyNameableKeys(map);
        mDescription = nameNameables(map, mDescription);
        for (WeaponStats weapon : mWeapons) {
            for (SkillDefault one : weapon.getDefaults()) {
                one.applyNameableKeys(map);
            }
        }
        for (EquipmentModifier modifier : mModifiers) {
            modifier.applyNameableKeys(map);
        }
    }

    /** @return The modifiers. */
    public List<EquipmentModifier> getModifiers() {
        return Collections.unmodifiableList(mModifiers);
    }

    /** @param modifiers The value to set for modifiers. */
    public void setModifiers(List<? extends Modifier> modifiers) {
        List<EquipmentModifier> in = Filtered.list(modifiers, EquipmentModifier.class);
        if (!mModifiers.equals(in)) {
            mModifiers = in;
            notifyOfChange();
            update();
        }
    }

    /**
     * @param name The name to match against. Case-insensitive.
     * @return The first modifier that matches the name.
     */
    public EquipmentModifier getActiveModifierFor(String name) {
        for (EquipmentModifier m : getModifiers()) {
            if (m.isEnabled() && m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    private static final String MODIFIER_SEPARATOR = "; ";

    @Override
    public String getModifierNotes() {
        StringBuilder builder = new StringBuilder();
        for (EquipmentModifier modifier : mModifiers) {
            if (modifier.isEnabled()) {
                builder.append(modifier.getFullDescription());
                builder.append(MODIFIER_SEPARATOR);
            }
        }
        if (!builder.isEmpty()) {
            // Remove the trailing MODIFIER_SEPARATOR
            builder.setLength(builder.length() - MODIFIER_SEPARATOR.length());
        }
        return builder.toString();
    }

    @Override
    public String getToolTip(Column column) {
        return EquipmentColumn.values()[column.getID()].getToolTip(this);
    }
}
