import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Basic {
	/*
	 * getLegalBuyAndSell �k�H�R��W
	 * 
	 * @inputMap : Input Map
	 * 
	 * @typeBuyOrSell : 1 => �R�W, 2 => ��W
	 * 
	 * @continuousDay : �s��X�ӥ����
	 * 
	 * @typeLegal : 1 => �~��, 2 => ��H, 3 => �����
	 */
	public Map<String, Map<Integer, Map<String, String>>> getLegalBuyAndSell(
			Map<String, Map<Integer, Map<String, String>>> inputMap, int typeBuyOrSell, int continuousDay,
			int typeLegal) throws IOException {
		Map<String, Map<Integer, Map<String, String>>> resultMap = new HashMap<String, Map<Integer, Map<String, String>>>();

		for (String stockid : inputMap.keySet()) {
			int length = inputMap.get(stockid).size();
			// �O�_�R��W day, �w�]true
			boolean result = true;
			// ���i���o��S�h�[, ��ƼƤ��h�B�p��day, �ɭPnull
			if (length <= continuousDay) {
				continue;
			}

			for (int i = (length - continuousDay); i <= length; i++) {
				// �R��W
				int Total = 0;
				// �k�H����
				switch (typeLegal) {
				case 1:
					Total = Integer.parseInt(inputMap.get(stockid).get(i).get("�~��R��W"));
					break;
				case 2:
					Total = Integer.parseInt(inputMap.get(stockid).get(i).get("��H�R��W"));
					break;
				case 3:
					Total = Integer.parseInt(inputMap.get(stockid).get(i).get("����ӶR��W"));
					break;
				default:
					System.out.println("�п�J �k�H���� (1)�~�� (2)��H (3)�����");
					System.exit(0);
					break;
				}
				switch (typeBuyOrSell) {
				// �R�W
				case 1:
					if (Total <= 0) {
						result = false;
						break;
					}
					break;
				// ��W
				case 2:
					if (Total >= 0) {
						result = false;
						break;
					}
					break;
				default:
					System.out.println("�п�J (1)�R�W (2)��W");
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
	 * getPE ���q��
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
			double pe = Double.parseDouble(inputMap.get(stockid).get(length).get("���q��"));
			if (pe >= peLowValue && pe <= peHighValue) {
				resultMap.put(stockid, inputMap.get(stockid));
			}
		}
		return resultMap;
	}

	/*
	 * getPbr �ѻ��b�Ȥ�
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
			double pbr = Double.parseDouble(inputMap.get(stockid).get(length).get("�ѻ��b�Ȥ�"));
			if (pbr >= pbrLowValue && pbr <= pbrHighValue) {
				resultMap.put(stockid, inputMap.get(stockid));
			}
		}
		return resultMap;
	}

	/*
	 * getYieldRate �ާQ�v
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
				double yieldRate = Double.parseDouble(inputMap.get(stockid).get(i).get("�ާQ�v"));
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
	 * getShare ����q�p��
	 * 
	 * @inputMap : Input Map
	 * 
	 * @minShare : �̧C����i��
	 * 
	 * @maxShare : �̧C����i��
	 * 
	 */
	public Map<String, Map<Integer, Map<String, String>>> getShare(
			Map<String, Map<Integer, Map<String, String>>> inputMap, int minShare, int maxShare) throws IOException {
		Map<String, Map<Integer, Map<String, String>>> resultMap = new HashMap<String, Map<Integer, Map<String, String>>>();

		for (String stockid : inputMap.keySet()) {
			int length = inputMap.get(stockid).size();
			double share = Integer.parseInt(inputMap.get(stockid).get(length).get("����Ѽ�")) / 1000.0;
			if (share >= minShare && share <= maxShare) {
				resultMap.put(stockid, inputMap.get(stockid));
			}
		}
		return resultMap;
	}

}
