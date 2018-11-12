package com.engine.model.beans.es;

import java.util.ArrayList;
import java.util.List;

public enum Nature {

	NEG_AND_POS(-3, "正负面"),

	NEGATIVE(-2, "负面"),

	NEGATIVE_1(-1, "负面一级（一般级）", "Buzz Level"),

	NEGATIVE_2(7, "负面二级（关注级）", "Monitor Level"),

	NEGATIVE_3(4, "负面三级（干预级）", "Action Level"),

	NEGATIVE_4(5, "负面四级（危机级）", "Crisis Level"),

	NEGATIVE_5(6, "负面五级（重大级）", "Significant Crisis Level"),

	POSITIVE(1, "正面"),

	HOT(2, "热点"),

	IRRELEVANT(3, "无关"),

	UNSIGNED(0, "未标注"),

	NEG_AND_POS_AND_HOT(-4, "正负热"),

	NORMAL(8, "中性");

	private int		id;
	private String	name;
	private String	englishName;

	private Nature(int id, String name) {
		this.id = id;
		this.name = name;
	}

	private Nature(int id, String name, String englishName) {
		this.id = id;
		this.name = name;
		this.englishName = englishName;
	}

	// 非正面
	public boolean isNotPos() {
		if (this.id == -2 || this.id == -1 || this.id == 7 || this.id == 4 || this.id == 5 || this.id == 6 || this.id == 2 || this.id == 0) {
			return true;
		} else {
			return false;
		}
	}

	// 负面
	public boolean isNeg() {
		if (this.id == -2 || this.id == -1 || this.id == 7 || this.id == 4 || this.id == 5 || this.id == 6) {
			return true;
		} else {
			return false;
		}
	}

	// 非无关
	public boolean isNotIrr() {
		if (this.id == 3) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isGroup() {
		if (this.id == -3 || this.id == -2 || this.id == -4) {
			return true;
		} else {
			return false;
		}
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEnglishName() {
		return englishName;
	}

	public int getNegaLevel() {
		if (isNegative()) {
			if (this == NEGATIVE_2) {
				return 2;
			} else if (this == NEGATIVE_3) {
				return 3;
			} else if (this == NEGATIVE_4) {
				return 4;
			} else if (this == NEGATIVE_5) {
				return 5;
			} else if (this == NEGATIVE_1) {
				return 1;
			}
		}
		return -1;
	}

	public String getNegaLevelName() {
		if (isNegative()) {
			if (this == NEGATIVE_2) {
				return "关注级";
			} else if (this == NEGATIVE_3) {
				return "干预级";
			} else if (this == NEGATIVE_4) {
				return "危机级";
			} else if (this == NEGATIVE_5) {
				return "重大危机级";
			} else if (this == NEGATIVE_1) {
				return "一般级";
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public static List<Nature> valuesNotGroup() {
		List<Nature> nl = new ArrayList<Nature>();
		for (Nature nature : values()) {
			if (!nature.isGroup()) {
				nl.add(nature);
			}
		}
		return nl;
	}

	public static List<Nature> valuesNeg() {
		List<Nature> list = new ArrayList<Nature>();
		for (Nature nature : values()) {
			if (nature.isNeg()) {
				list.add(nature);
			}
		}
		return list;
	}

	public static List<Nature> valuesNotPos() {
		List<Nature> list = new ArrayList<Nature>();
		for (Nature nature : values()) {
			if (nature.isNotPos()) {
				list.add(nature);
			}
		}
		return list;
	}

	public static List<Nature> valuesNotIrr() {
		List<Nature> list = new ArrayList<Nature>();
		for (Nature nature : values()) {
			if (nature.isNotIrr()) {
				list.add(nature);
			}
		}
		return list;
	}

	public static Nature valueOfId(int id) {
		for (Nature nt : Nature.values()) {
			if (nt.id == id) {
				return nt;
			}
		}
		return null;
	}

	public static Integer[] getNatureArray() {
		return new Integer[]{
			-1, 0, 1, 4, 5, 6, 7
		};
	}

	public static Integer[] getNegIdArray() {
		return new Integer[]{
			-1, 4, 5, 6, 7
		};
	}

	public static Integer[] getPosAndNegIdArray() {
		return new Integer[]{
			-1, 1, 4, 5, 6, 7
		};
	}

	public Integer[] getArray() {
		if (id == -3) {
			return new Integer[]{
				-1, 1, 4, 5, 6, 7
			};
		} else if (id == -2) {
			return new Integer[]{
				-1, 4, 5, 6, 7
			};
		} else if (id == -4) {
			return new Integer[]{
				-1, 1, 4, 5, 6, 7, 2
			};
		} else {
			return new Integer[]{
				this.id
			};
		}
	}

	public boolean isNegative() {
		return this == NEGATIVE_1 || this == NEGATIVE_2 || this == NEGATIVE_3 || this == NEGATIVE_4 || this == NEGATIVE_5;
	}

}
