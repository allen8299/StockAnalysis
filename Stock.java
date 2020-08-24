import java.io.*;
import java.sql.*;
import java.util.*;

public class Stock {
	// �Ѳ��N�����Ĥ@�hkey, ���U�� ��� => ��T �� map
	protected static Map<String, Map<Integer, Map<String, String>>> stockArray = new HashMap<String, Map<Integer, Map<String, String>>>();
	private static Scanner scanner; // Input

	public static void main(String[] args) throws SQLException, IOException {
		scanner = new Scanner(System.in);

		Data data = new Data(); // ��l�Ƹ��

		Display display = new Display(); // ���

		Basic basic = new Basic(); // �򥻥\��

		Credit credit = new Credit(); // �H�Υ��

		Technique technique = new Technique(); // �޳N�ʥ\��

		// �s��z��ᤧ�Ѳ���T
		Map<String, Map<Integer, Map<String, String>>> selectedArray = new HashMap<String, Map<Integer, Map<String, String>>>();

		int typeBuyOrSell; // �R��W

		int continuousDay; // �s�������

		int typeLegal; // �k�H����

		double peLow, peHigh; // �̧C���q��, �̰����q��

		double pbrLow, pbrHigh; // �̧C�ѻ��b�Ȥ�, �̰��ѻ��b�Ȥ�

		int continuousYear; // �s��X�~�ާQ�v
		double yieldRateLow, yieldRateHigh; // �̧C�ާQ�v, �̰��ާQ�v

		int typeIncreaseOrDecrease; // �ĸ�/�� �W�[ or ���

		int minShare, maxShare; // ���̧C����q, ���̰�����q

		double sharePercent; // ��j/�Y�p y %

		// ��ܥ\��
		display.functionDisplay();
		while (true) {
			int functionInput;

			System.out.print("�п�J:");
			functionInput = scanner.nextInt();
			if (functionInput == 0) {
				System.out.println("----�d�ߵ��G�p�U----");

				Map<String, Map<Integer, Map<String, String>>> sortedStockArray = new TreeMap<String, Map<Integer, Map<String, String>>>(
						selectedArray);

				// �䤣����
				if (sortedStockArray.isEmpty()) {
					System.out.println("�d�L���!");
				} else {
					int i = 0;
					for (String stockid : sortedStockArray.keySet()) {
						int length = sortedStockArray.get(stockid).size();
						String name = sortedStockArray.get(stockid).get(length).get("�Ѳ��W��");
						String todayClosingPrice = sortedStockArray.get(stockid).get(length).get("���L��");
						System.out.println(stockid + "{" + name + ", (���馬�L�� : " + todayClosingPrice + ")" + "}");
						i++;
					}
					System.out.println("�@ " + String.valueOf(i) + " ��");
				}

				System.out.println("Finished!");
				System.exit(0);
			} else {
				// ���o���ƦC�B���z�蠟stockArray
				if (selectedArray.isEmpty()) {
					data.getStockArray(stockArray);
				}
				switch (functionInput) {
				// �T�j�k�H�R��W
				case 1:
					System.out.print("�п�J�k�H���� (1)�~�� (2)��H (3)����� : ");
					typeLegal = scanner.nextInt();
					System.out.print("�п�J (1)�R�W (2)��W : ");
					typeBuyOrSell = scanner.nextInt();
					System.out.print("�п�J�s��X�ӥ���� : ");
					continuousDay = scanner.nextInt();

					// �Ū��N��S�����L, �����L�N���ۤW�������G
					if (selectedArray.isEmpty()) {
						selectedArray = basic.getLegalBuyAndSell(stockArray, typeBuyOrSell, continuousDay, typeLegal);
					} else {
						selectedArray = basic.getLegalBuyAndSell(selectedArray, typeBuyOrSell, continuousDay,
								typeLegal);
					}
					break;
				// ���q��
				case 2:
					System.out.print("�п�J�̧C���q�� : ");
					peLow = scanner.nextDouble();
					System.out.print("�п�J�̰����q�� : ");
					peHigh = scanner.nextDouble();

					// �Ū��N��S�����L, �����L�N���ۤW�������G
					if (selectedArray.isEmpty()) {
						selectedArray = basic.getPE(stockArray, peLow, peHigh);
					} else {
						selectedArray = basic.getPE(selectedArray, peLow, peHigh);
					}
					break;
				// �ѻ��b�Ȥ�
				case 3:
					System.out.print("�п�J�̧C�ѻ��b�Ȥ� : ");
					pbrLow = scanner.nextDouble();
					System.out.print("�п�J�̰��ѻ��b�Ȥ� : ");
					pbrHigh = scanner.nextDouble();

					// �Ū��N��S�����L, �����L�N���ۤW�������G
					if (selectedArray.isEmpty()) {
						selectedArray = basic.getPbr(stockArray, pbrLow, pbrHigh);
					} else {
						selectedArray = basic.getPbr(selectedArray, pbrLow, pbrHigh);
					}
					break;
				// �ާQ�v
				case 4:
					System.out.print("�гs��X�~: ");
					continuousYear = scanner.nextInt();
					System.out.print("�п�J�ާQ�v : ");
					yieldRateLow = scanner.nextDouble();

					// �Ū��N��S�����L, �����L�N���ۤW�������G
					if (selectedArray.isEmpty()) {
						selectedArray = basic.getYieldRate(stockArray, continuousYear, yieldRateLow);
					} else {
						selectedArray = basic.getYieldRate(selectedArray, continuousYear, yieldRateLow);
					}
					break;
				// �ĸ�
				case 5:
					System.out.print("�п�J�ĸ����� (1)�W�[ (2)���: ");
					typeIncreaseOrDecrease = scanner.nextInt();
					System.out.print("�п�J�s��X�ӥ���� : ");
					continuousDay = scanner.nextInt();

					// �Ū��N��S�����L, �����L�N���ۤW�������G
					if (selectedArray.isEmpty()) {
						selectedArray = credit.getFinancing(stockArray, typeIncreaseOrDecrease, continuousDay);
					} else {
						selectedArray = credit.getFinancing(selectedArray, typeIncreaseOrDecrease, continuousDay);
					}
					break;
				// �Ĩ�
				case 6:
					System.out.print("�п�J�Ĩ����� (1)�W�[ (2)���: ");
					typeIncreaseOrDecrease = scanner.nextInt();
					System.out.print("�п�J�s��X�ӥ���� : ");
					continuousDay = scanner.nextInt();

					// �Ū��N��S�����L, �����L�N���ۤW�������G
					if (selectedArray.isEmpty()) {
						selectedArray = credit.getMargin(stockArray, typeIncreaseOrDecrease, continuousDay);
					} else {
						selectedArray = credit.getMargin(selectedArray, typeIncreaseOrDecrease, continuousDay);
					}
					break;
				// ���Ѥ�
				case 7:
					break;
				// �q��j/�Y�p��
				case 8:
					// �s�������
					System.out.print("�п�J�s��X�ӥ���� : ");
					continuousDay = scanner.nextInt();
					// ��j/�Y�p %
					System.out.print("�п�J��j/�Y�p  % : ");
					sharePercent = scanner.nextDouble();
					// �Ū��N��S�����L, �����L�N���ۤW�������G
					if (selectedArray.isEmpty()) {
						selectedArray = technique.getShareEnlargeOrShrink(stockArray, continuousDay, sharePercent);
					} else {
						selectedArray = technique.getShareEnlargeOrShrink(selectedArray, continuousDay, sharePercent);
					}
					break;
				// �ѻ����^
				case 9:
					break;
				// KD��
				case 10:
					break;
				// RSI��
				case 11:
					break;
				// �a���z�j�q�B�а���
				case 12:
					// �s�������
					System.out.print("�п�J�s��X�ӥ���� : ");
					continuousDay = scanner.nextInt();
					// �Ū��N��S�����L, �����L�N���ۤW�������G
					if (selectedArray.isEmpty()) {
						selectedArray = technique.getHigestShareAndPrice(stockArray, continuousDay);
					} else {
						selectedArray = technique.getHigestShareAndPrice(selectedArray, continuousDay);
					}
					break;
				case 13:
					System.out.print("�п�J���̧C����i�� : ");
					minShare = scanner.nextInt();
					System.out.print("�п�J���̰�����i�� : ");
					maxShare = scanner.nextInt();

					if (selectedArray.isEmpty()) {
						selectedArray = basic.getShare(stockArray, minShare, maxShare);
					} else {
						selectedArray = basic.getShare(selectedArray, minShare, maxShare);
					}
					break;
				case 14:
					// �s�������
					System.out.print("�п�J�s��X�ӥ���� : ");
					continuousDay = scanner.nextInt();
					// �Ū��N��S�����L, �����L�N���ۤW�������G
					if (selectedArray.isEmpty()) {
						selectedArray = technique.getHigestShareAndPrice(stockArray, continuousDay);
					} else {
						selectedArray = technique.getHigestShareAndPrice(selectedArray, continuousDay);
					}
					break;
				}
			}
		}
	}
}
