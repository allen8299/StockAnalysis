import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Technique {
	/*
	 * getHigestShareAndPrice xらΘユ秖程基程穝蔼
	 * 
	 * @inputMap : Input Map
	 * 
	 * @continuousDay : 硈尿碭ユら(EX : 200)
	 */
	public Map<String, Map<Integer, Map<String, String>>> getHigestShareAndPrice(
			Map<String, Map<Integer, Map<String, String>>> inputMap, int continuousDay) throws IOException {
		Map<String, Map<Integer, Map<String, String>>> resultMap = new HashMap<String, Map<Integer, Map<String, String>>>();

		for (String stockid : inputMap.keySet()) {
			int length = inputMap.get(stockid).size();
			// Τ祇︽⊿, 戈计ぃday, 旧璓null
			if (length <= continuousDay) {
				continue;
			}
			// ヘ玡程Θユ秖
			int maxShare = Integer.parseInt(inputMap.get(stockid).get(length - continuousDay).get("Θユ计"));
			// ヘ玡程蔼基
			double maxPrice = Double.parseDouble(inputMap.get(stockid).get(length - continuousDay).get("程蔼基"));

			// セらΘユ秖
			int todayShare = Integer.parseInt(inputMap.get(stockid).get(length).get("Θユ计"));
			// セら程蔼基
			double todayPrice = Double.parseDouble(inputMap.get(stockid).get(length).get("程蔼基"));

			for (int i = (length - continuousDay); i <= length; i++) {
				int share = Integer.parseInt(inputMap.get(stockid).get(i).get("Θユ计"));
				double highestPrice = Double.parseDouble(inputMap.get(stockid).get(i).get("程蔼基"));
				// т程Θユ秖
				if (share > maxShare) {
					maxShare = share;
				}
				// т程蔼基
				if (highestPrice > maxPrice) {
					maxPrice = highestPrice;
				}
			}
			// 脄秖承蔼基
			if (todayShare >= maxShare && todayPrice >= maxPrice) {
				resultMap.put(stockid, inputMap.get(stockid));
			}
		}
		return resultMap;
	}

	public Map<String, Map<Integer, Map<String, String>>> getHigestShare(
			Map<String, Map<Integer, Map<String, String>>> inputMap, int continuousDay) throws IOException {
		Map<String, Map<Integer, Map<String, String>>> resultMap = new HashMap<String, Map<Integer, Map<String, String>>>();

		for (String stockid : inputMap.keySet()) {
			int length = inputMap.get(stockid).size();
			// Τ祇︽⊿, 戈计ぃday, 旧璓null
			if (length <= continuousDay) {
				continue;
			}
			// ヘ玡程Θユ秖
			int maxShare = Integer.parseInt(inputMap.get(stockid).get(length - continuousDay).get("Θユ计"));
			// ヘ玡程蔼基
			double maxPrice = Double.parseDouble(inputMap.get(stockid).get(length - continuousDay).get("程蔼基"));

			// セらΘユ秖
			int todayShare = Integer.parseInt(inputMap.get(stockid).get(length).get("Θユ计"));
			// セら程蔼基
			double todayPrice = Double.parseDouble(inputMap.get(stockid).get(length).get("程蔼基"));

			for (int i = (length - continuousDay); i <= length; i++) {
				int share = Integer.parseInt(inputMap.get(stockid).get(i).get("Θユ计"));
				double highestPrice = Double.parseDouble(inputMap.get(stockid).get(i).get("程蔼基"));
				// т程Θユ秖
				if (share > maxShare) {
					maxShare = share;
				}
				// т程蔼基
				if (highestPrice > maxPrice) {
					maxPrice = highestPrice;
				}
			}
			// 脄秖
			if (todayShare >= maxShare) {
				resultMap.put(stockid, inputMap.get(stockid));
			}
		}
		return resultMap;
	}

	/*
	 * getShareEnlargeOrShrink xらΘユ秖 /罽 y %
	 * 
	 * @inputMap : Input Map
	 * 
	 * @continuousDay : 硈尿碭ユら(EX : 200)
	 * 
	 * @sharePercent : /罽 y %
	 */
	public Map<String, Map<Integer, Map<String, String>>> getShareEnlargeOrShrink(
			Map<String, Map<Integer, Map<String, String>>> inputMap, int continuousDay, double sharePercent)
			throws IOException {
		Map<String, Map<Integer, Map<String, String>>> resultMap = new HashMap<String, Map<Integer, Map<String, String>>>();
		boolean result = true; // 琌Θユ秖 /罽 y %

		for (String stockid : inputMap.keySet()) {
			int length = inputMap.get(stockid).size();
			// Τ祇︽⊿, 戈计ぃday, 旧璓null
			if (length <= continuousDay + 1) {
				continue;
			}

			for (int i = (length - continuousDay); i < length; i++) {
				int share = Integer.parseInt(inputMap.get(stockid).get(i).get("Θユ计"));
				int nextShare = Integer.parseInt(inputMap.get(stockid).get(i + 1).get("Θユ计"));
				if ((share * (1 + sharePercent * 0.01)) > nextShare) {
					result = false;
					break;
				}
			}
			if (result) {
				resultMap.put(stockid, inputMap.get(stockid));
			}
		}

		return resultMap;
	}
}
