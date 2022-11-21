package com.hostmm.csj.utility.features;

public class CommonMethod {
	/**
	 * @param rowCount
	 * @return generated userID
	 */
	public static String generateID(String type, int rowCount) {
		String id = "";
		rowCount += 1;
		if (rowCount < 10)
			id = type + "-000" + rowCount;
		else if (rowCount > 9 && rowCount < 100)
			id = type + "-00" + rowCount;
		else
			id = type + "-0" + rowCount;
		return id;
	}
}
