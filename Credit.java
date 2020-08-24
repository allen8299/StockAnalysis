import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Credit {
	/*
	 * getFinancing �ĸ�W��
	 * 
	 * @inputMap : Input Map
	 * 
	 * @typeIncreaseOrDecrease : 1 => Increase, 2 => Decrease
	 * 
	 * @continuousDay : �s��X�ӥ����
	 * 
	 */
	public Map<String, Map<Integer, Map<String, String>>> getFinancing(
			Map<String, Map<Integer, Map<String, String>>> inputMap, int typeIncreaseOrDecrease, int continuousDay)
			throws IOException {
		Map<String, Map<Integer, Map<String, String>>> resultMap = new HashMap<String, Map<Integer, Map<String, String>>>();

		for (String stockid : inputMap.keySet()) {
			int length = inputMap.get(stockid).size();
			// �O�_�s��W/�� day, �w�]true
			boolean result = true;
			// ���i���o��S�h�[, ��ƼƤ��h�B�p��day, �ɭPnull
			if (length <= continuousDay) {
				continue;
			}
			for (int i = (length - continuousDay); i <= length; i++) {
				int financingIn = Integer.parseInt(inputMap.get(stockid).get(i).get("�ĸ�R�i"));
				int financingOut = Integer.parseInt(inputMap.get(stockid).get(i).get("�ĸ��X"));
				int financingDiff = financingIn - financingOut;
				// ��� or ��W
				switch (typeIncreaseOrDecrease) {
				// ��W
				case 1:
					// �ҥH�u�n difference < 0 �N�O false
					if (financingDiff < 0) {
						result = false;
						break;
					}
					break;
				// ���
				case 2:
					// �ҥH�u�n difference > 0 �N�O false
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
	 * getMargin �Ĩ�W��
	 * 
	 * @inputMap : Input Map
	 * 
	 * @typeIncreaseOrDecrease : 1 => Increase, 2 => Decrease
	 * 
	 * @continuousDay : �s��X�ӥ����
	 * 
	 */
	public Map<String, Map<Integer, Map<String, String>>> getMargin(
			Map<String, Map<Integer, Map<String, String>>> inputMap, int typeIncreaseOrDecrease, int continuousDay)
			throws IOException {
		Map<String, Map<Integer, Map<String, String>>> resultMap = new HashMap<String, Map<Integer, Map<String, String>>>();

		for (String stockid : inputMap.keySet()) {
			int length = inputMap.get(stockid).size();
			// �O�_�s�� �W/�� day, �w�]true
			boolean result = true;
			// ���i���o��S�h�[, ��ƼƤ��h�B�p��day, �ɭPnull
			if (length <= continuousDay) {
				continue;
			}
			for (int i = (length - continuousDay); i <= length; i++) {
				int marginIn = Integer.parseInt(inputMap.get(stockid).get(i).get("�Ĩ�R�i"));
				int marginOut = Integer.parseInt(inputMap.get(stockid).get(i).get("�Ĩ��X"));
				int marginDiff = marginIn - marginOut;
				// ��� or ��W
				switch (typeIncreaseOrDecrease) {
				// ��W
				case 1:
					// �ҥH�u�n difference < 0 �N�O false
					if (marginDiff < 0) {
						result = false;
						break;
					}
					break;
				// ���
				case 2:
					// �ҥH�u�n difference > 0 �N�O false
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
