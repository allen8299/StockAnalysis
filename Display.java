
public class Display {
	public void functionDisplay() {
		System.out.println("進入查詢功能:");
		System.out.println("1. 三大法人買賣超");
		System.out.println("  1-1. 法人類型 (1)外資 (2)投信 (3)自營商");
		System.out.println("  1-2. (1)買超 (2)賣超");
		System.out.println("  1-3. 連續 x 個交易日");
		System.out.println("2. 本益比");
		System.out.println("  2-1. y < pe < x");
		System.out.println("3. 股價淨值比");
		System.out.println("  2-1. y < pbr < x");
		System.out.println("4. 殖利率");
		System.out.println("  4-1. 連續 x 年殖利率 > y");
		System.out.println("5. 融資");
		System.out.println("  5-1. 融資連續 增/減 x 日");
		System.out.println("6. 融券");
		System.out.println("  6-1. 融券連續 增/減 x 日");
		System.out.println("7. 持股比(一周統計一次)(尚未開放)");
		System.out.println("  7-1. 一百張以下 上升/下降 x 統計日");
		System.out.println("  7-2. 四百張以下 上升/下降 x 統計日");
		System.out.println("  7-3. 八百張以下 上升/下降 x 統計日");
		System.out.println("  7-4. 一千張以下 上升/下降 x 統計日");
		System.out.println("8. 量放大/縮小股");
		System.out.println("  8-1. 連續 x 日成交量 放大/縮小 y %");
		System.out.println("9. 股價漲跌(尚未開放)");
		System.out.println("  9-1. 連續 上升/下降 x 日");
		// System.out.println("10. KD值(尚未開放)");
		// System.out.println(" 10-1. 黃金交叉");
		// System.out.println(" 10-1. 死亡交叉");
		// System.out.println("11. RSI值(尚未開放)");
		// System.out.println(" 11-1. 黃金交叉");
		// System.out.println(" 11-1. 死亡交叉");
		System.out.println("12. 冠佑爆大量版");
		System.out.println("  12-1. 今日成交量為近200日最大且今日股價創200日最新高");
		System.out.println("13. 成交量");
		System.out.println("  13-1. 今日成交量介於 x ~ y 張");
		System.out.println("14. Allen爆大量版");
		System.out.println("  14-1. 今日成交量為近200日最大");
		System.out.println("-----------------------------------------");
		System.out.println("輸入 0  結束查詢並印出結果");
	}
}
