import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Technique {
	/*
	 * getHigestShareAndPrice ��x�馨��q�̤j�B�ѻ��̷s��
	 * 
	 * @inputMap : Input Map
	 * 
	 * @continuousDay : �s��X�ӥ����(EX : 200)
	 */
	public Map<String, Map<Integer, Map<String, String>>> getHigestShareAndPrice(
			Map<String, Map<Integer, Map<String, String>>> inputMap, int continuousDay) throws IOException {
		Map<String, Map<Integer, Map<String, String>>> resultMap = new HashMap<String, Map<Integer, Map<String, String>>>();

		for (String stockid : inputMap.keySet()) {
			int length = inputMap.get(stockid).size();
			// ���i���o��S�h�[, ��ƼƤ��h�B�p��day, �ɭPnull
			if (length <= continuousDay) {
				continue;
			}
			// �ثe�̤j����q
			int maxShare = Integer.parseInt(inputMap.get(stockid).get(length - continuousDay).get("����Ѽ�"));
			// �ثe�̰���
			double maxPrice = Double.parseDouble(inputMap.get(stockid).get(length - continuousDay).get("�̰���"));

			// ���馨��q
			int todayShare = Integer.parseInt(inputMap.get(stockid).get(length).get("����Ѽ�"));
			// ����̰���
			double todayPrice = Double.parseDouble(inputMap.get(stockid).get(length).get("�̰���"));

			for (int i = (length - continuousDay); i <= length; i++) {
				int share = Integer.parseInt(inputMap.get(stockid).get(i).get("����Ѽ�"));
				double highestPrice = Double.parseDouble(inputMap.get(stockid).get(i).get("�̰���"));
				// ��̤j����q
				if (share > maxShare) {
					maxShare = share;
				}
				// ��̰���
				if (highestPrice > maxPrice) {
					maxPrice = highestPrice;
				}
			}
			// �z�q�B�а���
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
			// ���i���o��S�h�[, ��ƼƤ��h�B�p��day, �ɭPnull
			if (length <= continuousDay) {
				continue;
			}
			// �ثe�̤j����q
			int maxShare = Integer.parseInt(inputMap.get(stockid).get(length - continuousDay).get("����Ѽ�"));
			// �ثe�̰���
			double maxPrice = Double.parseDouble(inputMap.get(stockid).get(length - continuousDay).get("�̰���"));

			// ���馨��q
			int todayShare = Integer.parseInt(inputMap.get(stockid).get(length).get("����Ѽ�"));
			// ����̰���
			double todayPrice = Double.parseDouble(inputMap.get(stockid).get(length).get("�̰���"));

			for (int i = (length - continuousDay); i <= length; i++) {
				int share = Integer.parseInt(inputMap.get(stockid).get(i).get("����Ѽ�"));
				double highestPrice = Double.parseDouble(inputMap.get(stockid).get(i).get("�̰���"));
				// ��̤j����q
				if (share > maxShare) {
					maxShare = share;
				}
				// ��̰���
				if (highestPrice > maxPrice) {
					maxPrice = highestPrice;
				}
			}
			// �z�q
			if (todayShare >= maxShare) {
				resultMap.put(stockid, inputMap.get(stockid));
			}
		}
		return resultMap;
	}

	/*
	 * getShareEnlargeOrShrink ��x�馨��q ��j/�Y�p y %
	 * 
	 * @inputMap : Input Map
	 * 
	 * @continuousDay : �s��X�ӥ����(EX : 200)
	 * 
	 * @sharePercent : ��j/�Y�p y %
	 */
	public Map<String, Map<Integer, Map<String, String>>> getShareEnlargeOrShrink(
			Map<String, Map<Integer, Map<String, String>>> inputMap, int continuousDay, double sharePercent)
			throws IOException {
		Map<String, Map<Integer, Map<String, String>>> resultMap = new HashMap<String, Map<Integer, Map<String, String>>>();
		boolean result = true; // �O�_����q ��j/�Y�p y %

		for (String stockid : inputMap.keySet()) {
			int length = inputMap.get(stockid).size();
			// ���i���o��S�h�[, ��ƼƤ��h�B�p��day, �ɭPnull
			if (length <= continuousDay + 1) {
				continue;
			}

			for (int i = (length - continuousDay); i < length; i++) {
				int share = Integer.parseInt(inputMap.get(stockid).get(i).get("����Ѽ�"));
				int nextShare = Integer.parseInt(inputMap.get(stockid).get(i + 1).get("����Ѽ�"));
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
