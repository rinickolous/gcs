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

import com.trollworks.gcs.criteria.StringCompareType;
import com.trollworks.gcs.criteria.StringCriteria;
import com.trollworks.gcs.datafile.DataFile;
import com.trollworks.gcs.skill.Skill;
import com.trollworks.gcs.ui.widget.outline.ListRow;
import com.trollworks.gcs.utility.json.JsonMap;
import com.trollworks.gcs.utility.json.JsonWriter;
import com.trollworks.gcs.utility.text.Enums;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/** A skill bonus. */
public class SkillBonus extends Bonus {
    public static final  String KEY_ROOT           = "skill_bonus";
    private static final String KEY_SELECTION_TYPE = "selection_type";
    private static final String KEY_NAME           = "name";
    private static final String KEY_SPECIALIZATION = "specialization";
    private static final String KEY_CATEGORY       = "category";

    private SkillSelectionType mSkillSelectionType;
    private StringCriteria     mNameCriteria;
    private StringCriteria     mSpecializationCriteria;
    private StringCriteria     mCategoryCriteria;

    /** Creates a new skill bonus. */
    public SkillBonus() {
        super(1);
        mSkillSelectionType = SkillSelectionType.SKILLS_WITH_NAME;
        mNameCriteria = new StringCriteria(StringCompareType.IS, "");
        mSpecializationCriteria = new StringCriteria(StringCompareType.ANY, "");
        mCategoryCriteria = new StringCriteria(StringCompareType.ANY, "");
    }

    public SkillBonus(DataFile dataFile, JsonMap m) throws IOException {
        this();
        loadSelf(dataFile, m);
    }

    /**
     * Creates a clone of the specified bonus.
     *
     * @param other The bonus to clone.
     */
    public SkillBonus(SkillBonus other) {
        super(other);
        mSkillSelectionType = other.mSkillSelectionType;
        mNameCriteria = new StringCriteria(other.mNameCriteria);
        mSpecializationCriteria = new StringCriteria(other.mSpecializationCriteria);
        mCategoryCriteria = new StringCriteria(other.mCategoryCriteria);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof SkillBonus sb && super.equals(obj)) {
            if (mNameCriteria.equals(sb.mNameCriteria)) {
                return mSkillSelectionType == sb.mSkillSelectionType &&
                        mNameCriteria.equals(sb.mNameCriteria) &&
                        mSpecializationCriteria.equals(sb.mSpecializationCriteria) &&
                        mCategoryCriteria.equals(sb.mCategoryCriteria);
            }
        }
        return false;
    }

    @Override
    public Feature cloneFeature() {
        return new SkillBonus(this);
    }

    @Override
    public String getJSONTypeName() {
        return KEY_ROOT;
    }

    @Override
    public String getKey() {
        return switch (mSkillSelectionType) {
            case WEAPONS_WITH_NAME -> buildKey(WeaponDamageBonus.WEAPON_NAMED_ID_PREFIX);
            case SKILLS_WITH_NAME -> buildKey(Skill.ID_NAME);
            default -> WeaponDamageBonus.THIS_WEAPON_ID;
        };
    }

    private String buildKey(String prefix) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(prefix);
        if (mNameCriteria.isTypeIs() && mSpecializationCriteria.isTypeAnything() && mCategoryCriteria.isTypeAnything()) {
            buffer.append('/');
            buffer.append(mNameCriteria.getQualifier());
        } else {
            buffer.append("*");
        }
        return buffer.toString();
    }

    public boolean matchesCategories(Set<String> categories) {
        return matchesCategories(mCategoryCriteria, categories);
    }

    @Override
    protected void loadSelf(DataFile dataFile, JsonMap m) throws IOException {
        super.loadSelf(dataFile, m);
        mSkillSelectionType = Enums.extract(m.getString(KEY_SELECTION_TYPE), SkillSelectionType.values(), SkillSelectionType.SKILLS_WITH_NAME);
        mSpecializationCriteria.load(m.getMap(KEY_SPECIALIZATION));
        if (mSkillSelectionType != SkillSelectionType.THIS_WEAPON) {
            mNameCriteria.load(m.getMap(KEY_NAME));
            mCategoryCriteria.load(m.getMap(KEY_CATEGORY));
        }
    }

    @Override
    protected void saveSelf(JsonWriter w) throws IOException {
        super.saveSelf(w);
        w.keyValue(KEY_SELECTION_TYPE, Enums.toId(mSkillSelectionType));
        mSpecializationCriteria.save(w, KEY_SPECIALIZATION);
        if (mSkillSelectionType != SkillSelectionType.THIS_WEAPON) {
            mNameCriteria.save(w, KEY_NAME);
            mCategoryCriteria.save(w, KEY_CATEGORY);
        }
    }

    public SkillSelectionType getSkillSelectionType() {
        return mSkillSelectionType;
    }

    public boolean setSkillSelectionType(SkillSelectionType type) {
        if (mSkillSelectionType != type) {
            mSkillSelectionType = type;
            return true;
        }
        return false;
    }

    /** @return The name criteria. */
    public StringCriteria getNameCriteria() {
        return mNameCriteria;
    }

    /** @return The name criteria. */
    public StringCriteria getSpecializationCriteria() {
        return mSpecializationCriteria;
    }

    /** @return The category criteria. */
    public StringCriteria getCategoryCriteria() {
        return mCategoryCriteria;
    }

    @Override
    public void fillWithNameableKeys(Set<String> set) {
        if (mSkillSelectionType == SkillSelectionType.THIS_WEAPON) {
            ListRow.extractNameables(set, mSpecializationCriteria.getQualifier());
        } else {
            ListRow.extractNameables(set, mNameCriteria.getQualifier());
            ListRow.extractNameables(set, mSpecializationCriteria.getQualifier());
            ListRow.extractNameables(set, mCategoryCriteria.getQualifier());
        }
    }

    @Override
    public void applyNameableKeys(Map<String, String> map) {
        if (mSkillSelectionType == SkillSelectionType.THIS_WEAPON) {
            mSpecializationCriteria.setQualifier(ListRow.nameNameables(map, mSpecializationCriteria.getQualifier()));
        } else {
            mNameCriteria.setQualifier(ListRow.nameNameables(map, mNameCriteria.getQualifier()));
            mSpecializationCriteria.setQualifier(ListRow.nameNameables(map, mSpecializationCriteria.getQualifier()));
            mCategoryCriteria.setQualifier(ListRow.nameNameables(map, mCategoryCriteria.getQualifier()));
        }
    }
}
