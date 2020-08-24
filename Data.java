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
	 * DB �s�u
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
	 * �ŭȳB�z : �� null, --, ---���B�z
	 */
	private String checkNullOrSymbol(String inputString) throws IOException {
		// �� null, --, ---���B�z
		if (inputString == null || inputString.contains("--")) {
			inputString = "0";
		}
		return inputString;
	}

	// �إ�stock array
	public Map<String, Map<Integer, Map<String, String>>>

			getStockArray(Map<String, Map<Integer, Map<String, String>>> inputMap) throws SQLException, IOException {
		Connection con = this.getConnection();// �إ߳s�u

		Statement stmt = con.createStatement();// sql statement

		ResultSet rs = stmt.executeQuery("SELECT * FROM STOCK_BASIC ORDER BY stockid DESC");

		Map<String, String> tmpStockArray = new HashMap<String, String>();// �ӪѰ}�C

		while (rs.next()) {
			String id = rs.getString("stockid");
			String name = rs.getString("name");

			tmpStockArray.put(id, name);
		}
		// �w�ƦC�ӪѰ}�C
		Map<String, String> tmpSortedStockArray = new TreeMap<String, String>(tmpStockArray);

		for (Object key : tmpSortedStockArray.keySet()) {
			// �ˬd�ӪѫH�Υ��table�O�_�s�b
			String checkCreditTable = "SELECT count(*) FROM sqlite_master WHERE type='table' AND name='"
					+ "STOCK_CREDIT_" + key + "'";
			ResultSet rs3 = stmt.executeQuery(checkCreditTable);

			int count = 0;
			if (rs3.next()) {
				// count = 1 ��ܦs�b
				count = rs3.getInt(1);
			}
			// ���s�b�N���L�� stockid
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

			// �Ȧs�ӪѸ�T, ��� => ��T
			Map<Integer, Map<String, String>> tmpStockArray2 = new HashMap<Integer, Map<String, String>>();
			int i = 1;
			while (rs2.next()) {
				// �Ȧs�Ӫѷ�Ѹ�T
				Map<String, String> tmpDayArray = new HashMap<String, String>();

				// ������
				String date = rs2.getString("date");
				tmpDayArray.put("���", date);
				// �Ѳ��W��
				String name = rs2.getString("name");
				tmpDayArray.put("�Ѳ��W��", name);

				// ����Ѽ�
				String share = rs2.getString("share");
				tmpDayArray.put("����Ѽ�", share);
				// �}�L��
				String openingPrice = this.checkNullOrSymbol(rs2.getString("openingPrice"));
				tmpDayArray.put("�}�L��", openingPrice);
				// ���L��
				String closingPrice = this.checkNullOrSymbol(rs2.getString("closingPrice"));
				tmpDayArray.put("���L��", closingPrice);
				// �̰���
				String highestPrice = this.checkNullOrSymbol(rs2.getString("highestPrice"));
				tmpDayArray.put("�̰���", highestPrice);
				// �̧C��
				String lowestPrice = this.checkNullOrSymbol(rs2.getString("lowestPrice"));
				tmpDayArray.put("�̧C��", lowestPrice);
				// ���q��
				String pe = this.checkNullOrSymbol(rs2.getString("pe"));
				tmpDayArray.put("���q��", pe);
				// �ާQ�v
				String yieldRate = this.checkNullOrSymbol(rs2.getString("yieldRate"));
				tmpDayArray.put("�ާQ�v", yieldRate);
				// �ѻ��b�Ȥ�
				String pbr = this.checkNullOrSymbol(rs2.getString("pbr"));
				tmpDayArray.put("�ѻ��b�Ȥ�", pbr);
				// �~��R��W
				String foreignTotal = this.checkNullOrSymbol(rs2.getString("foreignTotal"));
				tmpDayArray.put("�~��R��W", foreignTotal);
				// ��H�R��W
				String trustTotal = this.checkNullOrSymbol(rs2.getString("trustTotal"));
				tmpDayArray.put("��H�R��W", trustTotal);
				// ����ӶR��W
				String dealerTotal = this.checkNullOrSymbol(rs2.getString("dealerTotal"));
				tmpDayArray.put("����ӶR��W", dealerTotal);
				// �ĸ�R�i
				String financingIn = this.checkNullOrSymbol(rs2.getString("financingIn"));
				tmpDayArray.put("�ĸ�R�i", financingIn);
				// �ĸ��X
				String financingOut = this.checkNullOrSymbol(rs2.getString("financingOut"));
				tmpDayArray.put("�ĸ��X", financingOut);
				// �Ĩ�R�i
				String marginIn = this.checkNullOrSymbol(rs2.getString("marginIn"));
				tmpDayArray.put("�Ĩ�R�i", marginIn);
				// �Ĩ��X
				String marginOut = this.checkNullOrSymbol(rs2.getString("marginOut"));
				tmpDayArray.put("�Ĩ��X", marginOut);

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
