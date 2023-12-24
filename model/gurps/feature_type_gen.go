// Code generated from "enum.go.tmpl" - DO NOT EDIT.

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

package gurps

import (
	"strings"

	"github.com/richardwilkes/toolbox/i18n"
)

// Possible values.
const (
	AttributeBonusFeatureType FeatureType = iota
	ConditionalModifierFeatureType
	DRBonusFeatureType
	ReactionBonusFeatureType
	SkillBonusFeatureType
	SkillPointBonusFeatureType
	SpellBonusFeatureType
	SpellPointBonusFeatureType
	WeaponBonusFeatureType
	WeaponAccBonusFeatureType
	WeaponScopeAccBonusFeatureType
	WeaponDRDivisorBonusFeatureType
	WeaponMinSTBonusFeatureType
	WeaponMinReachBonusFeatureType
	WeaponMaxReachBonusFeatureType
	WeaponHalfDamageRangeBonusFeatureType
	WeaponMinRangeBonusFeatureType
	WeaponMaxRangeBonusFeatureType
	WeaponRecoilBonusFeatureType
	WeaponBulkBonusFeatureType
	WeaponParryBonusFeatureType
	WeaponBlockBonusFeatureType
	WeaponRofMode1ShotsBonusFeatureType
	WeaponRofMode1SecondaryBonusFeatureType
	WeaponRofMode2ShotsBonusFeatureType
	WeaponRofMode2SecondaryBonusFeatureType
	WeaponNonChamberShotsBonusFeatureType
	WeaponChamberShotsBonusFeatureType
	WeaponShotDurationBonusFeatureType
	WeaponReloadTimeBonusFeatureType
	WeaponSwitchFeatureType
	CostReductionFeatureType
	ContainedWeightReductionFeatureType
	LastFeatureType = ContainedWeightReductionFeatureType
)

// AllFeatureType holds all possible values.
var AllFeatureType = []FeatureType{
	AttributeBonusFeatureType,
	ConditionalModifierFeatureType,
	DRBonusFeatureType,
	ReactionBonusFeatureType,
	SkillBonusFeatureType,
	SkillPointBonusFeatureType,
	SpellBonusFeatureType,
	SpellPointBonusFeatureType,
	WeaponBonusFeatureType,
	WeaponAccBonusFeatureType,
	WeaponScopeAccBonusFeatureType,
	WeaponDRDivisorBonusFeatureType,
	WeaponMinSTBonusFeatureType,
	WeaponMinReachBonusFeatureType,
	WeaponMaxReachBonusFeatureType,
	WeaponHalfDamageRangeBonusFeatureType,
	WeaponMinRangeBonusFeatureType,
	WeaponMaxRangeBonusFeatureType,
	WeaponRecoilBonusFeatureType,
	WeaponBulkBonusFeatureType,
	WeaponParryBonusFeatureType,
	WeaponBlockBonusFeatureType,
	WeaponRofMode1ShotsBonusFeatureType,
	WeaponRofMode1SecondaryBonusFeatureType,
	WeaponRofMode2ShotsBonusFeatureType,
	WeaponRofMode2SecondaryBonusFeatureType,
	WeaponNonChamberShotsBonusFeatureType,
	WeaponChamberShotsBonusFeatureType,
	WeaponShotDurationBonusFeatureType,
	WeaponReloadTimeBonusFeatureType,
	WeaponSwitchFeatureType,
	CostReductionFeatureType,
	ContainedWeightReductionFeatureType,
}

// FeatureType holds the type of a Feature.
type FeatureType byte

// EnsureValid ensures this is of a known value.
func (enum FeatureType) EnsureValid() FeatureType {
	if enum <= LastFeatureType {
		return enum
	}
	return 0
}

// Key returns the key used in serialization.
func (enum FeatureType) Key() string {
	switch enum {
	case AttributeBonusFeatureType:
		return "attribute_bonus"
	case ConditionalModifierFeatureType:
		return "conditional_modifier"
	case DRBonusFeatureType:
		return "dr_bonus"
	case ReactionBonusFeatureType:
		return "reaction_bonus"
	case SkillBonusFeatureType:
		return "skill_bonus"
	case SkillPointBonusFeatureType:
		return "skill_point_bonus"
	case SpellBonusFeatureType:
		return "spell_bonus"
	case SpellPointBonusFeatureType:
		return "spell_point_bonus"
	case WeaponBonusFeatureType:
		return "weapon_bonus"
	case WeaponAccBonusFeatureType:
		return "weapon_acc_bonus"
	case WeaponScopeAccBonusFeatureType:
		return "weapon_scope_acc_bonus"
	case WeaponDRDivisorBonusFeatureType:
		return "weapon_dr_divisor_bonus"
	case WeaponMinSTBonusFeatureType:
		return "weapon_min_st_bonus"
	case WeaponMinReachBonusFeatureType:
		return "weapon_min_reach_bonus"
	case WeaponMaxReachBonusFeatureType:
		return "weapon_max_reach_bonus"
	case WeaponHalfDamageRangeBonusFeatureType:
		return "weapon_half_damage_range_bonus"
	case WeaponMinRangeBonusFeatureType:
		return "weapon_min_range_bonus"
	case WeaponMaxRangeBonusFeatureType:
		return "weapon_max_range_bonus"
	case WeaponRecoilBonusFeatureType:
		return "weapon_recoil_bonus"
	case WeaponBulkBonusFeatureType:
		return "weapon_bulk_bonus"
	case WeaponParryBonusFeatureType:
		return "weapon_parry_bonus"
	case WeaponBlockBonusFeatureType:
		return "weapon_block_bonus"
	case WeaponRofMode1ShotsBonusFeatureType:
		return "weapon_rof_mode_1_shots_bonus"
	case WeaponRofMode1SecondaryBonusFeatureType:
		return "weapon_rof_mode_1_secondary_bonus"
	case WeaponRofMode2ShotsBonusFeatureType:
		return "weapon_rof_mode_2_shots_bonus"
	case WeaponRofMode2SecondaryBonusFeatureType:
		return "weapon_rof_mode_2_secondary_bonus"
	case WeaponNonChamberShotsBonusFeatureType:
		return "weapon_non_chamber_shots_bonus"
	case WeaponChamberShotsBonusFeatureType:
		return "weapon_chamber_shots_bonus"
	case WeaponShotDurationBonusFeatureType:
		return "weapon_shot_duration_bonus"
	case WeaponReloadTimeBonusFeatureType:
		return "weapon_reload_time_bonus"
	case WeaponSwitchFeatureType:
		return "weapon_switch"
	case CostReductionFeatureType:
		return "cost_reduction"
	case ContainedWeightReductionFeatureType:
		return "contained_weight_reduction"
	default:
		return FeatureType(0).Key()
	}
}

// String implements fmt.Stringer.
func (enum FeatureType) String() string {
	switch enum {
	case AttributeBonusFeatureType:
		return i18n.Text("Gives an attribute modifier of")
	case ConditionalModifierFeatureType:
		return i18n.Text("Gives a conditional modifier of")
	case DRBonusFeatureType:
		return i18n.Text("Gives a DR bonus of")
	case ReactionBonusFeatureType:
		return i18n.Text("Gives a reaction modifier of")
	case SkillBonusFeatureType:
		return i18n.Text("Gives a skill level modifier of")
	case SkillPointBonusFeatureType:
		return i18n.Text("Gives a skill point modifier of")
	case SpellBonusFeatureType:
		return i18n.Text("Gives a spell level modifier of")
	case SpellPointBonusFeatureType:
		return i18n.Text("Gives a spell point modifier of")
	case WeaponBonusFeatureType:
		return i18n.Text("Gives a weapon damage modifier of")
	case WeaponAccBonusFeatureType:
		return i18n.Text("Gives a weapon accuracy modifier of")
	case WeaponScopeAccBonusFeatureType:
		return i18n.Text("Gives a weapon scope accuracy modifier of")
	case WeaponDRDivisorBonusFeatureType:
		return i18n.Text("Gives a weapon DR divisor modifier of")
	case WeaponMinSTBonusFeatureType:
		return i18n.Text("Gives a weapon minimum ST modifier of")
	case WeaponMinReachBonusFeatureType:
		return i18n.Text("Gives a weapon minimum reach modifier of")
	case WeaponMaxReachBonusFeatureType:
		return i18n.Text("Gives a weapon maximum reach modifier of")
	case WeaponHalfDamageRangeBonusFeatureType:
		return i18n.Text("Gives a weapon half-damage range modifier of")
	case WeaponMinRangeBonusFeatureType:
		return i18n.Text("Gives a weapon minimum range modifier of")
	case WeaponMaxRangeBonusFeatureType:
		return i18n.Text("Gives a weapon maximum range modifier of")
	case WeaponRecoilBonusFeatureType:
		return i18n.Text("Gives a weapon recoil modifier of")
	case WeaponBulkBonusFeatureType:
		return i18n.Text("Gives a weapon bulk modifier of")
	case WeaponParryBonusFeatureType:
		return i18n.Text("Gives a weapon parry modifier of")
	case WeaponBlockBonusFeatureType:
		return i18n.Text("Gives a weapon block modifier of")
	case WeaponRofMode1ShotsBonusFeatureType:
		return i18n.Text("Gives a weapon shots per attack (mode 1) modifier of")
	case WeaponRofMode1SecondaryBonusFeatureType:
		return i18n.Text("Gives a weapon secondary projectiles (mode 1) modifier of")
	case WeaponRofMode2ShotsBonusFeatureType:
		return i18n.Text("Gives a weapon shots per attack (mode 2) modifier of")
	case WeaponRofMode2SecondaryBonusFeatureType:
		return i18n.Text("Gives a weapon secondary projectiles (mode 2) modifier of")
	case WeaponNonChamberShotsBonusFeatureType:
		return i18n.Text("Gives a weapon non-chamber shots modifier of")
	case WeaponChamberShotsBonusFeatureType:
		return i18n.Text("Gives a weapon chamber shots modifier of")
	case WeaponShotDurationBonusFeatureType:
		return i18n.Text("Gives a weapon shot duration modifier of")
	case WeaponReloadTimeBonusFeatureType:
		return i18n.Text("Gives a weapon reload time modifier of")
	case WeaponSwitchFeatureType:
		return i18n.Text("Set the weapon flag")
	case CostReductionFeatureType:
		return i18n.Text("Reduces the attribute cost of")
	case ContainedWeightReductionFeatureType:
		return i18n.Text("Reduces the contained weight by")
	default:
		return FeatureType(0).String()
	}
}

// MarshalText implements the encoding.TextMarshaler interface.
func (enum FeatureType) MarshalText() (text []byte, err error) {
	return []byte(enum.Key()), nil
}

// UnmarshalText implements the encoding.TextUnmarshaler interface.
func (enum *FeatureType) UnmarshalText(text []byte) error {
	*enum = ExtractFeatureType(string(text))
	return nil
}

// ExtractFeatureType extracts the value from a string.
func ExtractFeatureType(str string) FeatureType {
	for _, enum := range AllFeatureType {
		if strings.EqualFold(enum.Key(), str) {
			return enum
		}
	}
	return 0
}
