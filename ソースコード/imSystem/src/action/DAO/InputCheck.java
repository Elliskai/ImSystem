package action.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import action.DTO.DTO;
import action.util.DbUtil;

public class InputCheck {
	private static final String TBL_NAME = "item";

	/** DBコネクション */
	public Connection con;
	/** DBステートメント */
	PreparedStatement stmt;
	/** 検索結果 */
	ResultSet rs;
	//入力用

	//インスタンス生成時DBコネクション
	public InputCheck(Connection con) throws ClassNotFoundException, SQLException {
		this.con = con;
	}
	//商品名の存在チェック
		public String checkName(String name) throws SQLException, ClassNotFoundException {
			DTO dto = new DTO();
			String checkname = "noname";
			String sql = "SELECT item_name FROM item where item_name = ? AND if_delete = 0";

			//入力
			try {
				PreparedStatement stmt = con.prepareStatement(sql.toString());

				stmt.setString(1, name);
	

				this.stmt = con.prepareStatement(sql.toString());
				rs = stmt.executeQuery();
				while (rs.next()) {
				dto.setItemName(rs.getString("item_name"));
				}
				checkname = dto.getItemName();
			} finally {
				DbUtil.closeStatement(this.stmt);
			}
			return checkname;
		}
		//空欄トリミング
		public String cutBrank(String str) {
			String str2 = str.strip();
			return str2;
			
		}
		//商品名文字数チェック
		public boolean checkNameCount(String name) {
			String str = name;
			boolean flag = false;
			if(str.length() >= 51) {
				flag = true;
			}else {
				flag = false;
			}
			return flag;
			
		}
		
		//商品説明文字数チェック
				public boolean checkInfoCount(String info) {
					String str = info;
					boolean flag = false;
					if(str.length() >= 151) {
						flag = true;
					}else {
						flag = false;
					}
					return flag;
					
				}
		//商品ID取得
		public String getItemID(String name)throws SQLException, ClassNotFoundException {
			   String sql = "SELECT item_id FROM item where item_name = ? ";
			   int id = 0;
			   DTO dto = new DTO();
				//入力
				try {
					PreparedStatement stmt = con.prepareStatement(sql.toString());

					stmt.setString(1, name);
				

					this.stmt = con.prepareStatement(sql.toString());
					rs = stmt.executeQuery();
					while (rs.next()) {
					dto.setItemId(rs.getInt("item_id"));
					}
					id = dto.getItemId();
				
				} finally {
					DbUtil.closeStatement(this.stmt);
				}
				String str = ""+id;
				return str;
			   
		}
		//現在在庫取得
				public int nowStock(String name) throws SQLException, ClassNotFoundException {
					DTO dto = new DTO();
					int stock = 0;
					String sql = "SELECT stock FROM item where item_name = ?";

					//入力
					try {
						PreparedStatement stmt = con.prepareStatement(sql.toString());

						stmt.setString(1, name);
			

						this.stmt = con.prepareStatement(sql.toString());
						rs = stmt.executeQuery();
						while (rs.next()) {
						dto.setStock(rs.getInt("stock"));
						}
						stock = dto.getStock();
					} finally {
						DbUtil.closeStatement(this.stmt);
					}
					return stock;
				}
		
		//カテゴリのID変換
		public String changeCategoryInteger(String category) {
			String cate = category;
			String num = "0";
			if(cate.equals("食品")) {
				num = "1";
			}
			if(cate.equals("飲料")) {
				num = "2";
			}
			if(cate.equals("家電")) {
				num = "3";
			}
			if(cate.equals("ファッション")) {
				num = "4";
			}
			if(cate.equals("美容")) {
				num = "5";
			}
			if(cate.equals("書籍")) {
				num = "6";
			}
			if(cate.equals("ホーム")) {
				num = "7";
			}
			if(cate.equals("スポーツ")) {
				num = "8";
			}
			if(cate.equals("おもちゃ")) {
				num = "9";
			}
			if(cate.equals("その他")) {
				num = "10";
			}
			
			return num;
		}
		
}
