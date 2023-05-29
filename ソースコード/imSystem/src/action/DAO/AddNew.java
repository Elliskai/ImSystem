package action.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import action.util.DbUtil;


public class AddNew {
	private static final String TBL_NAME = "item";
	private static final String ITEM_ID = "item_id";
	private static final String ITEM_NAME = "item_name";
	private static final String PRICE = "price";
	private static final String STOCK = "stock";
	private static final String INFORMATION = "information";
	private static final String IF_DELETE = "if_delete";
	private static final String CATEGORY_ID = "category_id";
	private static final String CATEGORY_NAME = "category_name";
	

	public Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	public java.sql.ResultSetMetaData rsmd;

	public AddNew(Connection con) {
		this.con = con;
	}
	
	//商品情報登録機能メソッド
	public void addItem(String itemName, String categoryName, int price,int stock, String info)
			throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();
		
		//受け取ったカテゴリ名をIDに変換
		switch (categoryName) {
		case "食品":
			categoryName = "1";
			break;
		case "飲料":
			categoryName = "2";
			break;
		case "家電":
			categoryName = "3";
			break;
		case "ファッション":
			categoryName = "4";
			break;
		case "美容":
			categoryName = "5";
			break;
		case "書籍":
			categoryName = "6";
			break;
		case "ホーム":
			categoryName = "7";
			break;
		case "スポーツ":
			categoryName = "8";
			break;
		case "おもちゃ":
			categoryName = "9";
			break;
		case "その他":
			categoryName = "10";
			break;
		}

		//itemテーブルへINSERT
		sql.append(" INSERT ");
		sql.append("    " + " INTO ");
		sql.append(TBL_NAME + "(item_name, price, stock, information, category_id)");
		sql.append("    " + " VALUES ");
		sql.append("	" + "(?, ?, ?, ?, ?)");
		try {

			this.stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, itemName);
			stmt.setInt(2, price);
			stmt.setInt(3, stock);
			stmt.setString(4, info);
			stmt.setString(5, categoryName);
			stmt.executeUpdate();

		} finally {
			DbUtil.closeStatement(this.stmt);
		}
	}
}
