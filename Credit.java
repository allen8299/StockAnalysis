import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Credit {
	/*
	 * getFinancing 融資增減
	 * 
	 * @inputMap : Input Map
	 * 
	 * @typeIncreaseOrDecrease : 1 => Increase, 2 => Decrease
	 * 
	 * @continuousDay : 連續幾個交易日
	 * 
	 */
	public Map<String, Map<Integer, Map<String, String>>> getFinancing(
			Map<String, Map<Integer, Map<String, String>>> inputMap, int typeIncreaseOrDecrease, int continuousDay)
			throws IOException {
		Map<String, Map<Integer, Map<String, String>>> resultMap = new HashMap<String, Map<Integer, Map<String, String>>>();

		for (String stockid : inputMap.keySet()) {
			int length = inputMap.get(stockid).size();
			// 是否連續增/減 day, 預設true
			boolean result = true;
			// 有可能剛發行沒多久, 資料數不多且小於day, 導致null
			if (length <= continuousDay) {
				continue;
			}
			for (int i = (length - continuousDay); i <= length; i++) {
				int financingIn = Integer.parseInt(inputMap.get(stockid).get(i).get("融資買進"));
				int financingOut = Integer.parseInt(inputMap.get(stockid).get(i).get("融資賣出"));
				int financingDiff = financingIn - financingOut;
				// 資減 or 資增
				switch (typeIncreaseOrDecrease) {
				// 資增
				case 1:
					// 所以只要 difference < 0 就是 false
					if (financingDiff < 0) {
						result = false;
						break;
					}
					break;
				// 資減
				case 2:
					// 所以只要 difference > 0 就是 false
					if (financingDiff > 0) {
						result = false;
						break;
					}
					break;
				}
			}
			if (result) {
				resultMap.put(stockid, inputMap.get(stockid));
			}
		}
		return resultMap;
	}

	/*
	 * getMargin 融券增減
	 * 
	 * @inputMap : Input Map
	 * 
	 * @typeIncreaseOrDecrease : 1 => Increase, 2 => Decrease
	 * 
	 * @continuousDay : 連續幾個交易日
	 * 
	 */
	public Map<String, Map<Integer, Map<String, String>>> getMargin(
			Map<String, Map<Integer, Map<String, String>>> inputMap, int typeIncreaseOrDecrease, int continuousDay)
			throws IOException {
		Map<String, Map<Integer, Map<String, String>>> resultMap = new HashMap<String, Map<Integer, Map<String, String>>>();

		for (String stockid : inputMap.keySet()) {
			int length = inputMap.get(stockid).size();
			// 是否連續 增/減 day, 預設true
			boolean result = true;
			// 有可能剛發行沒多久, 資料數不多且小於day, 導致null
			if (length <= continuousDay) {
				continue;
			}
			for (int i = (length - continuousDay); i <= length; i++) {
				int marginIn = Integer.parseInt(inputMap.get(stockid).get(i).get("融券買進"));
				int marginOut = Integer.parseInt(inputMap.get(stockid).get(i).get("融券賣出"));
				int marginDiff = marginIn - marginOut;
				// 券減 or 券增
				switch (typeIncreaseOrDecrease) {
				// 券增
				case 1:
					// 所以只要 difference < 0 就是 false
					if (marginDiff < 0) {
						result = false;
						break;
					}
					break;
				// 券減
				case 2:
					// 所以只要 difference > 0 就是 false
					if (marginDiff > 0) {
						result = false;
						break;
					}
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
