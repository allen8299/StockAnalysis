import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Data {
	/*
	 * DB 連線
	 */
	private Connection getConnection() throws SQLException {
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./stock2012-now.sqlite");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return c;
	}

	/*
	 * 空值處理 : 對 null, --, ---做處理
	 */
	private String checkNullOrSymbol(String inputString) throws IOException {
		// 對 null, --, ---做處理
		if (inputString == null || inputString.contains("--")) {
			inputString = "0";
		}
		return inputString;
	}

	// 建立stock array
	public Map<String, Map<Integer, Map<String, String>>>

			getStockArray(Map<String, Map<Integer, Map<String, String>>> inputMap) throws SQLException, IOException {
		Connection con = this.getConnection();// 建立連線

		Statement stmt = con.createStatement();// sql statement

		ResultSet rs = stmt.executeQuery("SELECT * FROM STOCK_BASIC ORDER BY stockid DESC");

		Map<String, String> tmpStockArray = new HashMap<String, String>();// 個股陣列

		while (rs.next()) {
			String id = rs.getString("stockid");
			String name = rs.getString("name");

			tmpStockArray.put(id, name);
		}
		// 已排列個股陣列
		Map<String, String> tmpSortedStockArray = new TreeMap<String, String>(tmpStockArray);

		for (Object key : tmpSortedStockArray.keySet()) {
			// 檢查該股信用交易table是否存在
			String checkCreditTable = "SELECT count(*) FROM sqlite_master WHERE type='table' AND name='"
					+ "STOCK_CREDIT_" + key + "'";
			ResultSet rs3 = stmt.executeQuery(checkCreditTable);

			int count = 0;
			if (rs3.next()) {
				// count = 1 表示存在
				count = rs3.getInt(1);
			}
			// 不存在就跳過此 stockid
			if (count == 0) {
				continue;
			}

			String sql = " SELECT B.date, B.name, B.share, ";
			sql += " B.openingPrice, B.closingPrice, B.highestPrice, B.lowestPrice, ";
			sql += " B.pe, B.yieldRate, B.pbr, ";
			sql += " B.foreignTotal, B.trustTotal, B.dealerTotal, ";
			sql += " C.financingIn, C.financingOut, C.marginIn, C.marginOut ";
			sql += " FROM STOCK_BASIC_" + key + " B ";
			sql += " LEFT JOIN STOCK_CREDIT_" + key + " C ON B.date = C.date ";
			sql += " ORDER BY B.date ASC";
			ResultSet rs2 = stmt.executeQuery(sql);

			// 暫存該股資訊, 日期 => 資訊
			Map<Integer, Map<String, String>> tmpStockArray2 = new HashMap<Integer, Map<String, String>>();
			int i = 1;
			while (rs2.next()) {
				// 暫存該股當天資訊
				Map<String, String> tmpDayArray = new HashMap<String, String>();

				// 成交日期
				String date = rs2.getString("date");
				tmpDayArray.put("日期", date);
				// 股票名稱
				String name = rs2.getString("name");
				tmpDayArray.put("股票名稱", name);

				// 成交股數
				String share = rs2.getString("share");
				tmpDayArray.put("成交股數", share);
				// 開盤價
				String openingPrice = this.checkNullOrSymbol(rs2.getString("openingPrice"));
				tmpDayArray.put("開盤價", openingPrice);
				// 收盤價
				String closingPrice = this.checkNullOrSymbol(rs2.getString("closingPrice"));
				tmpDayArray.put("收盤價", closingPrice);
				// 最高價
				String highestPrice = this.checkNullOrSymbol(rs2.getString("highestPrice"));
				tmpDayArray.put("最高價", highestPrice);
				// 最低價
				String lowestPrice = this.checkNullOrSymbol(rs2.getString("lowestPrice"));
				tmpDayArray.put("最低價", lowestPrice);
				// 本益比
				String pe = this.checkNullOrSymbol(rs2.getString("pe"));
				tmpDayArray.put("本益比", pe);
				// 殖利率
				String yieldRate = this.checkNullOrSymbol(rs2.getString("yieldRate"));
				tmpDayArray.put("殖利率", yieldRate);
				// 股價淨值比
				String pbr = this.checkNullOrSymbol(rs2.getString("pbr"));
				tmpDayArray.put("股價淨值比", pbr);
				// 外資買賣超
				String foreignTotal = this.checkNullOrSymbol(rs2.getString("foreignTotal"));
				tmpDayArray.put("外資買賣超", foreignTotal);
				// 投信買賣超
				String trustTotal = this.checkNullOrSymbol(rs2.getString("trustTotal"));
				tmpDayArray.put("投信買賣超", trustTotal);
				// 自營商買賣超
				String dealerTotal = this.checkNullOrSymbol(rs2.getString("dealerTotal"));
				tmpDayArray.put("自營商買賣超", dealerTotal);
				// 融資買進
				String financingIn = this.checkNullOrSymbol(rs2.getString("financingIn"));
				tmpDayArray.put("融資買進", financingIn);
				// 融資賣出
				String financingOut = this.checkNullOrSymbol(rs2.getString("financingOut"));
				tmpDayArray.put("融資賣出", financingOut);
				// 融券買進
				String marginIn = this.checkNullOrSymbol(rs2.getString("marginIn"));
				tmpDayArray.put("融券買進", marginIn);
				// 融券賣出
				String marginOut = this.checkNullOrSymbol(rs2.getString("marginOut"));
				tmpDayArray.put("融券賣出", marginOut);

				tmpStockArray2.put(i, tmpDayArray);
				i++;
			}
			// Sorted
			Map<Integer, Map<String, String>> sortedStockArray2 = new TreeMap<Integer, Map<String, String>>(
					tmpStockArray2);
			inputMap.put((String) key, sortedStockArray2);
		}
		return inputMap;
	}
}
