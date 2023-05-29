package action.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import action.DTO.DTO; // Import DTO instead of EdTO
import action.util.DbUtil;

public class Analyse {
	/** DBコネクション */
	public Connection con;
	
	/** DBステートメント */
	PreparedStatement stmt;
	
	/** 検索結果 */
	ResultSet rs;
	
	public Analyse(Connection con) {
		this.con = con;
	}

	public List<DTO> abcAnalyse() throws SQLException, ClassNotFoundException {
		List<DTO> rtnList = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
//		sql.append("SELECT ");
//		sql.append("item.item_id as '商品ID',item.item_name as '商品名',category.category_name as 'カテゴリ',item.price as '税抜き価格',item.stock as '在庫数',item.information as '商品説明',receipt_mg.sale_count as '購入数' ");
//	    sql.append(" FROM ");
//		sql.append(" item left JOIN receipt_mg ON item.item_id = receipt_mg.item_id");
//		sql.append(" INNER JOIN category ON item.category_id = category.category_id ");
		
		sql.append("SELECT \r\n"
				+ "item.item_id as '商品ID',item.item_name as '商品名',category.category_name as 'カテゴリ',item.price as '税抜き価格',item.stock as '在庫数',item.information as '商品説明',SUM(IFNULL(sale_count, 0)) as '購入数', SUM(item.price * receipt_mg.sale_count) / (SELECT\r\n"
				+ "                SUM(item.price * receipt_mg.sale_count)\r\n"
				+ "             FROM\r\n"
				+ "                 item left JOIN receipt_mg ON item.item_id = receipt_mg.item_id\r\n"
				+ " INNER JOIN category ON item.category_id = category.category_id) * 100 '構成比(%)'\r\n"
				+ " FROM\r\n"
				+ " item left JOIN receipt_mg ON item.item_id = receipt_mg.item_id\r\n"
				+ " INNER JOIN category ON item.category_id = category.category_id\r\n"
				+ " GROUP BY item.item_id\r\n"
				+ " ORDER BY  SUM(item.price * receipt_mg.sale_count) / (SELECT\r\n"
				+ "                SUM(item.price * receipt_mg.sale_count)\r\n"
				+ "             FROM\r\n"
				+ "                 item left JOIN receipt_mg ON item.item_id = receipt_mg.item_id\r\n"
				+ " INNER JOIN category ON item.category_id = category.category_id) * 100 DESC;");
		
		try {

			this.stmt = con.prepareStatement(sql.toString());
						
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				DTO dto = new DTO(); // Use DTO instead of EdTO
				dto.setItemId(rs.getInt("商品ID"));
			    dto.setItemName(rs.getString("商品名"));
			    dto.setCategoryName(rs.getString("カテゴリ"));
				dto.setPrice(rs.getInt("税抜き価格"));
				dto.setInformation(rs.getString("商品説明"));
				dto.setStock(rs.getInt("在庫数"));
				dto.setSaleCount(rs.getInt("購入数"));
				dto.setRatio(rs.getInt("税抜き価格") * rs.getInt("購入数"));
				
				rtnList.add(dto);
			}
		} finally {
			DbUtil.closeStatement(this.stmt);
		}
	 return rtnList;	
	}
	
}
