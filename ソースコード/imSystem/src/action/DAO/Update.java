package action.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import action.util.DbUtil;

public class Update {
	
	private static final String TBL_NAME = "item";

	/** DBコネクション */
	public Connection con;
	/** DBステートメント */
	PreparedStatement stmt;
	/** 検索結果 */
	ResultSet rs;
	//入力用

	//インスタンス生成時DBコネクション
	public Update(Connection con) throws ClassNotFoundException, SQLException {
		this.con = con;
	}

	//商品名変更
	public void updateItemName(String name, String item_name) throws SQLException, ClassNotFoundException {

		String sql = "update item set item_name = ? where item_name = ?";

		//入力
		try {
			PreparedStatement stmt = con.prepareStatement(sql.toString());

			stmt.setString(1, name);
			stmt.setString(2, item_name);

			this.stmt = con.prepareStatement(sql.toString());
			stmt.executeUpdate();
		} finally {
			DbUtil.closeStatement(this.stmt);
		}
	}

	//販売価格変更
	public void updateItemPrice(String price, String para) throws SQLException, ClassNotFoundException {

		String sql = "update item set price  = ?  where item_name = ?";

		//入力
		try {
			PreparedStatement stmt = con.prepareStatement(sql.toString());

			stmt.setString(1, price);
			stmt.setString(2, para);

			this.stmt = con.prepareStatement(sql.toString());
			stmt.executeUpdate();
		} finally {
			DbUtil.closeStatement(this.stmt);
		}
	}
	

	//入荷在庫追加
	public void updateItemStock(String stock, String para) throws SQLException, ClassNotFoundException {

		String sql = "update item set stock  = stock + ? where item_name = ?";

		//入力
		try {
			PreparedStatement stmt = con.prepareStatement(sql.toString());

			stmt.setString(1, stock);
			stmt.setString(2, para);

			this.stmt = con.prepareStatement(sql.toString());
			stmt.executeUpdate();
		} finally {
			DbUtil.closeStatement(this.stmt);
		}
	}

	//商品情報変更
	public void updateItemInformation(String info, String para) throws SQLException, ClassNotFoundException {

		String sql = "update item set information  = ? where item_name = ?";

		//入力
		try {
			PreparedStatement stmt = con.prepareStatement(sql.toString());

			stmt.setString(1, info);
			stmt.setString(2, para);

			this.stmt = con.prepareStatement(sql.toString());
			stmt.executeUpdate();
		} finally {
			DbUtil.closeStatement(this.stmt);
		}
	}

	//カテゴリー変更
	public void updateItemCategory(String category, String para) throws SQLException, ClassNotFoundException {

		String sql = "update item set category_id  = ? where item_name = ?";

		//入力
		try {
			PreparedStatement stmt = con.prepareStatement(sql.toString());

			stmt.setString(1, category);
			stmt.setString(2, para);

			this.stmt = con.prepareStatement(sql.toString());
			stmt.executeUpdate();
		} finally {
			DbUtil.closeStatement(this.stmt);
		}
	}
	
	//売上追加
		public void updateItemSell(String sell, String para) throws SQLException, ClassNotFoundException {

			String sql = "update item set stock  = stock - ? where item_name = ?";

			//入力
			try {
				PreparedStatement stmt = con.prepareStatement(sql.toString());

				stmt.setString(1, sell);
				stmt.setString(2, para);

				this.stmt = con.prepareStatement(sql.toString());
				stmt.executeUpdate();
			} finally {
				DbUtil.closeStatement(this.stmt);
			}
		}
		
		//登録時タイムスタンプ取得とレシート登録
		public String getTimestampSetReceipt()throws SQLException, ClassNotFoundException {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
			   String str = sdf.format(timestamp);
			   String sql = "INSERT INTO receipt (buy_time) VALUES ( ? );";

				//入力
				try {
					PreparedStatement stmt = con.prepareStatement(sql.toString());

					stmt.setString(1, str);
				

					this.stmt = con.prepareStatement(sql.toString());
					stmt.executeUpdate();
				} finally {
					DbUtil.closeStatement(this.stmt);
				}
				return str;
			   
		}
		
		
		
				
				
		//購入履歴登録
				public void setPurchaseHistory(String sale,String itemID,String dayTime)throws SQLException, ClassNotFoundException {
					   String sql = "INSERT INTO receipt_mg (item_id,receipt_id,sale_count) VALUES ( ?,(SELECT receipt_id FROM receipt WHERE buy_time = ?),?)";

						//入力
						try {
							PreparedStatement stmt = con.prepareStatement(sql.toString());

							stmt.setString(1, itemID);
							stmt.setString(2, dayTime);
							stmt.setString(3, sale);

							this.stmt = con.prepareStatement(sql.toString());
							stmt.executeUpdate();
						} finally {
							DbUtil.closeStatement(this.stmt);
						}
					   
				}
				
}
