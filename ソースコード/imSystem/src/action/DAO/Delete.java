package action.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import action.DTO.DTO;
import action.util.DbUtil;

public class Delete {

	private static final String TBL_NAME = "item";
	private static final String ITEM_ID = "item_id";
	private static final String ITEM_NAME = "item_name";
	private static final String PRICE = "price";
	private static final String STOCK = "stock";
	private static final String INFORMATION = "information";
	private static final String IF_DELETE = "if_delete";
	private static final String CATEGORY_ID = "category_id";

	public Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	public java.sql.ResultSetMetaData rsmd;

	public Delete(Connection con) {
		this.con = con;
	}

	//商品情報削除メソッド
	public void deleteItem(String deleteItem) throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ");
		sql.append("    " + TBL_NAME);
		sql.append(" SET ");
		
		//削除フラグを1に変更(1の場合は表示しない)
		sql.append("    " + IF_DELETE + "= 1");
		
		sql.append(" WHERE ");
		sql.append(ITEM_NAME + " = ? AND if_delete = 0");

		try {
			this.stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, deleteItem);
			stmt.executeUpdate();

		} finally {
			DbUtil.closeStatement(this.stmt);
		}

	}

	//削除した商品情報を戻すメソッド
	public void rollbackItem(String rollbackItem) throws SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ");
		sql.append("    " + TBL_NAME);
		sql.append(" SET ");
		
		//削除フラグを0に変更(0の場合は表示する)
		sql.append("    " + IF_DELETE + "= 0");
		sql.append(" WHERE ");
		sql.append(ITEM_NAME + " = ?");

		try {
			this.stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, rollbackItem);
			stmt.executeUpdate();

		} finally {
			DbUtil.closeStatement(this.stmt);
		}

	}
	public DTO selectbyName(String keyword) throws SQLException {
	    DTO item = null;
	    StringBuilder sql = new StringBuilder();
	    
	    sql.append(" SELECT * ");
	    sql.append(" FROM  " + TBL_NAME);
	    sql.append(" WHERE ");
	    sql.append(ITEM_NAME + " = ?");
	    sql.append(" AND ");
		sql.append(IF_DELETE + " = 0");
	    sql.append(" ORDER BY " + ITEM_ID);   // String wildcardKeyword = "%" + keyword + "%";
	    
	    try {
	        this.stmt = con.prepareStatement(sql.toString());
	        stmt.setString(1, keyword);
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            // create a new DTO object
	            item = new DTO();
	            item.setItemId(rs.getInt(ITEM_ID));
	            item.setItemName(rs.getString(ITEM_NAME));
	            item.setPrice(rs.getInt(PRICE));
	            item.setStock(rs.getInt(STOCK));
	            item.setInformation(rs.getString(INFORMATION));
	            item.setIfDelete(rs.getInt(IF_DELETE));
	            item.setCategoryId(rs.getInt(CATEGORY_ID));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DbUtil.closeStatement(this.stmt);
	    }
	    
	    return item;
	}

}
