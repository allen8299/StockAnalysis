import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Basic {
	/*
	 * getLegalBuyAndSell 法人買賣超
	 * 
	 * @inputMap : Input Map
	 * 
	 * @typeBuyOrSell : 1 => 買超, 2 => 賣超
	 * 
	 * @continuousDay : 連續幾個交易日
	 * 
	 * @typeLegal : 1 => 外資, 2 => 投信, 3 => 自營商
	 */
	public Map<String, Map<Integer, Map<String, String>>> getLegalBuyAndSell(
			Map<String, Map<Integer, Map<String, String>>> inputMap, int typeBuyOrSell, int continuousDay,
			int typeLegal) throws IOException {
		Map<String, Map<Integer, Map<String, String>>> resultMap = new HashMap<String, Map<Integer, Map<String, String>>>();

		for (String stockid : inputMap.keySet()) {
			int length = inputMap.get(stockid).size();
			// 是否買賣超 day, 預設true
			boolean result = true;
			// 有可能剛發行沒多久, 資料數不多且小於day, 導致null
			if (length <= continuousDay) {
				continue;
			}

			for (int i = (length - continuousDay); i <= length; i++) {
				// 買賣超
				int Total = 0;
				// 法人類型
				switch (typeLegal) {
				case 1:
					Total = Integer.parseInt(inputMap.get(stockid).get(i).get("外資買賣超"));
					break;
				case 2:
					Total = Integer.parseInt(inputMap.get(stockid).get(i).get("投信買賣超"));
					break;
				case 3:
					Total = Integer.parseInt(inputMap.get(stockid).get(i).get("自營商買賣超"));
					break;
				default:
					System.out.println("請輸入 法人類型 (1)外資 (2)投信 (3)自營商");
					System.exit(0);
					break;
				}
				switch (typeBuyOrSell) {
				// 買超
				case 1:
					if (Total <= 0) {
						result = false;
						break;
					}
					break;
				// 賣超
				case 2:
					if (Total >= 0) {
						result = false;
						break;
					}
					break;
				default:
					System.out.println("請輸入 (1)買超 (2)賣超");
					System.exit(0);
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
	 * getPE 本益比
	 * 
	 * @inputMap : Input Map
	 * 
	 * @peLowValue : 1 for example
	 * 
	 * @peHighValue : 12 for example
	 */
	public Map<String, Map<Integer, Map<String, String>>> getPE(Map<String, Map<Integer, Map<String, String>>> inputMap,
			double peLowValue, double peHighValue) throws IOException {
		Map<String, Map<Integer, Map<String, String>>> resultMap = new HashMap<String, Map<Integer, Map<String, String>>>();

		for (String stockid : inputMap.keySet()) {
			int length = inputMap.get(stockid).size();
			double pe = Double.parseDouble(inputMap.get(stockid).get(length).get("本益比"));
			if (pe >= peLowValue && pe <= peHighValue) {
				resultMap.put(stockid, inputMap.get(stockid));
			}
		}
		return resultMap;
	}

	/*
	 * getPbr 股價淨值比
	 * 
	 * @inputMap : Input Map
	 * 
	 * @pbrLowValue : 1 for example
	 * 
	 * @pbrHighValue : 12 for example
	 */
	public Map<String, Map<Integer, Map<String, String>>> getPbr(
			Map<String, Map<Integer, Map<String, String>>> inputMap, double pbrLowValue, double pbrHighValue)
			throws IOException {
		Map<String, Map<Integer, Map<String, String>>> resultMap = new HashMap<String, Map<Integer, Map<String, String>>>();

		for (String stockid : inputMap.keySet()) {
			int length = inputMap.get(stockid).size();
			double pbr = Double.parseDouble(inputMap.get(stockid).get(length).get("股價淨值比"));
			if (pbr >= pbrLowValue && pbr <= pbrHighValue) {
				resultMap.put(stockid, inputMap.get(stockid));
			}
		}
		return resultMap;
	}

	/*
	 * getYieldRate 殖利率
	 * 
	 * @inputMap : Input Map
	 * 
	 * @continuousYear : 1 year for 250 data
	 * 
	 * @yieldRateLowValue : 12 for example
	 * 
	 * @yieldRateHighValue : 12 for example
	 * 
	 */
	public Map<String, Map<Integer, Map<String, String>>> getYieldRate(
			Map<String, Map<Integer, Map<String, String>>> inputMap, int continuousYear, double yieldRateLowValue) throws IOException {
		Map<String, Map<Integer, Map<String, String>>> resultMap = new HashMap<String, Map<Integer, Map<String, String>>>();
		for (String stockid : inputMap.keySet()) {
			int length = inputMap.get(stockid).size();

			if (length <= continuousYear * 250) {
				continue;
			}
			boolean result = true;
			for (int i = length; i >= (length - continuousYear * 250); i = i - continuousYear * 250) {
				double yieldRate = Double.parseDouble(inputMap.get(stockid).get(i).get("殖利率"));
				if (yieldRate < yieldRateLowValue) {
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

	/*
	 * getShare 成交量計算
	 * 
	 * @inputMap : Input Map
	 * 
	 * @minShare : 最低成交張數
	 * 
	 * @maxShare : 最低成交張數
	 * 
	 */
	public Map<String, Map<Integer, Map<String, String>>> getShare(
			Map<String, Map<Integer, Map<String, String>>> inputMap, int minShare, int maxShare) throws IOException {
		Map<String, Map<Integer, Map<String, String>>> resultMap = new HashMap<String, Map<Integer, Map<String, String>>>();

		for (String stockid : inputMap.keySet()) {
			int length = inputMap.get(stockid).size();
			double share = Integer.parseInt(inputMap.get(stockid).get(length).get("成交股數")) / 1000.0;
			if (share >= minShare && share <= maxShare) {
				resultMap.put(stockid, inputMap.get(stockid));
			}
		}
		return resultMap;
	}

}
