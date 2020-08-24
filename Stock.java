import java.io.*;
import java.sql.*;
import java.util.*;

public class Stock {
	// 股票代號為第一層key, 往下為 日期 => 資訊 之 map
	protected static Map<String, Map<Integer, Map<String, String>>> stockArray = new HashMap<String, Map<Integer, Map<String, String>>>();
	private static Scanner scanner; // Input

	public static void main(String[] args) throws SQLException, IOException {
		scanner = new Scanner(System.in);

		Data data = new Data(); // 初始化資料

		Display display = new Display(); // 顯示

		Basic basic = new Basic(); // 基本功能

		Credit credit = new Credit(); // 信用交易

		Technique technique = new Technique(); // 技術性功能

		// 存放篩選後之股票資訊
		Map<String, Map<Integer, Map<String, String>>> selectedArray = new HashMap<String, Map<Integer, Map<String, String>>>();

		int typeBuyOrSell; // 買賣超

		int continuousDay; // 連續交易日數

		int typeLegal; // 法人類型

		double peLow, peHigh; // 最低本益比, 最高本益比

		double pbrLow, pbrHigh; // 最低股價淨值比, 最高股價淨值比

		int continuousYear; // 連續幾年殖利率
		double yieldRateLow, yieldRateHigh; // 最低殖利率, 最高殖利率

		int typeIncreaseOrDecrease; // 融資/券 增加 or 減少

		int minShare, maxShare; // 當日最低成交量, 當日最高成交量

		double sharePercent; // 放大/縮小 y %

		// 顯示功能
		display.functionDisplay();
		while (true) {
			int functionInput;

			System.out.print("請輸入:");
			functionInput = scanner.nextInt();
			if (functionInput == 0) {
				System.out.println("----查詢結果如下----");

				Map<String, Map<Integer, Map<String, String>>> sortedStockArray = new TreeMap<String, Map<Integer, Map<String, String>>>(
						selectedArray);

				// 找不到資料
				if (sortedStockArray.isEmpty()) {
					System.out.println("查無資料!");
				} else {
					int i = 0;
					for (String stockid : sortedStockArray.keySet()) {
						int length = sortedStockArray.get(stockid).size();
						String name = sortedStockArray.get(stockid).get(length).get("股票名稱");
						String todayClosingPrice = sortedStockArray.get(stockid).get(length).get("收盤價");
						System.out.println(stockid + "{" + name + ", (今日收盤價 : " + todayClosingPrice + ")" + "}");
						i++;
					}
					System.out.println("共 " + String.valueOf(i) + " 檔");
				}

				System.out.println("Finished!");
				System.exit(0);
			} else {
				// 取得未排列且未篩選之stockArray
				if (selectedArray.isEmpty()) {
					data.getStockArray(stockArray);
				}
				switch (functionInput) {
				// 三大法人買賣超
				case 1:
					System.out.print("請輸入法人類型 (1)外資 (2)投信 (3)自營商 : ");
					typeLegal = scanner.nextInt();
					System.out.print("請輸入 (1)買超 (2)賣超 : ");
					typeBuyOrSell = scanner.nextInt();
					System.out.print("請輸入連續幾個交易日 : ");
					continuousDay = scanner.nextInt();

					// 空的代表沒有撈過, 有撈過就接著上次的結果
					if (selectedArray.isEmpty()) {
						selectedArray = basic.getLegalBuyAndSell(stockArray, typeBuyOrSell, continuousDay, typeLegal);
					} else {
						selectedArray = basic.getLegalBuyAndSell(selectedArray, typeBuyOrSell, continuousDay,
								typeLegal);
					}
					break;
				// 本益比
				case 2:
					System.out.print("請輸入最低本益比 : ");
					peLow = scanner.nextDouble();
					System.out.print("請輸入最高本益比 : ");
					peHigh = scanner.nextDouble();

					// 空的代表沒有撈過, 有撈過就接著上次的結果
					if (selectedArray.isEmpty()) {
						selectedArray = basic.getPE(stockArray, peLow, peHigh);
					} else {
						selectedArray = basic.getPE(selectedArray, peLow, peHigh);
					}
					break;
				// 股價淨值比
				case 3:
					System.out.print("請輸入最低股價淨值比 : ");
					pbrLow = scanner.nextDouble();
					System.out.print("請輸入最高股價淨值比 : ");
					pbrHigh = scanner.nextDouble();

					// 空的代表沒有撈過, 有撈過就接著上次的結果
					if (selectedArray.isEmpty()) {
						selectedArray = basic.getPbr(stockArray, pbrLow, pbrHigh);
					} else {
						selectedArray = basic.getPbr(selectedArray, pbrLow, pbrHigh);
					}
					break;
				// 殖利率
				case 4:
					System.out.print("請連續幾年: ");
					continuousYear = scanner.nextInt();
					System.out.print("請輸入殖利率 : ");
					yieldRateLow = scanner.nextDouble();

					// 空的代表沒有撈過, 有撈過就接著上次的結果
					if (selectedArray.isEmpty()) {
						selectedArray = basic.getYieldRate(stockArray, continuousYear, yieldRateLow);
					} else {
						selectedArray = basic.getYieldRate(selectedArray, continuousYear, yieldRateLow);
					}
					break;
				// 融資
				case 5:
					System.out.print("請輸入融資類型 (1)增加 (2)減少: ");
					typeIncreaseOrDecrease = scanner.nextInt();
					System.out.print("請輸入連續幾個交易日 : ");
					continuousDay = scanner.nextInt();

					// 空的代表沒有撈過, 有撈過就接著上次的結果
					if (selectedArray.isEmpty()) {
						selectedArray = credit.getFinancing(stockArray, typeIncreaseOrDecrease, continuousDay);
					} else {
						selectedArray = credit.getFinancing(selectedArray, typeIncreaseOrDecrease, continuousDay);
					}
					break;
				// 融券
				case 6:
					System.out.print("請輸入融券類型 (1)增加 (2)減少: ");
					typeIncreaseOrDecrease = scanner.nextInt();
					System.out.print("請輸入連續幾個交易日 : ");
					continuousDay = scanner.nextInt();

					// 空的代表沒有撈過, 有撈過就接著上次的結果
					if (selectedArray.isEmpty()) {
						selectedArray = credit.getMargin(stockArray, typeIncreaseOrDecrease, continuousDay);
					} else {
						selectedArray = credit.getMargin(selectedArray, typeIncreaseOrDecrease, continuousDay);
					}
					break;
				// 持股比
				case 7:
					break;
				// 量放大/縮小股
				case 8:
					// 連續交易日數
					System.out.print("請輸入連續幾個交易日 : ");
					continuousDay = scanner.nextInt();
					// 放大/縮小 %
					System.out.print("請輸入放大/縮小  % : ");
					sharePercent = scanner.nextDouble();
					// 空的代表沒有撈過, 有撈過就接著上次的結果
					if (selectedArray.isEmpty()) {
						selectedArray = technique.getShareEnlargeOrShrink(stockArray, continuousDay, sharePercent);
					} else {
						selectedArray = technique.getShareEnlargeOrShrink(selectedArray, continuousDay, sharePercent);
					}
					break;
				// 股價漲跌
				case 9:
					break;
				// KD值
				case 10:
					break;
				// RSI值
				case 11:
					break;
				// 冠佑爆大量且創高價
				case 12:
					// 連續交易日數
					System.out.print("請輸入連續幾個交易日 : ");
					continuousDay = scanner.nextInt();
					// 空的代表沒有撈過, 有撈過就接著上次的結果
					if (selectedArray.isEmpty()) {
						selectedArray = technique.getHigestShareAndPrice(stockArray, continuousDay);
					} else {
						selectedArray = technique.getHigestShareAndPrice(selectedArray, continuousDay);
					}
					break;
				case 13:
					System.out.print("請輸入當日最低成交張數 : ");
					minShare = scanner.nextInt();
					System.out.print("請輸入當日最高成交張數 : ");
					maxShare = scanner.nextInt();

					if (selectedArray.isEmpty()) {
						selectedArray = basic.getShare(stockArray, minShare, maxShare);
					} else {
						selectedArray = basic.getShare(selectedArray, minShare, maxShare);
					}
					break;
				case 14:
					// 連續交易日數
					System.out.print("請輸入連續幾個交易日 : ");
					continuousDay = scanner.nextInt();
					// 空的代表沒有撈過, 有撈過就接著上次的結果
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
