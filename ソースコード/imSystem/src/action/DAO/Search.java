package action.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import action.DTO.DTO;
import action.util.DbUtil;

public class Search {
	private static final String TBL_NAME = "item";
	private static final String ITEM_ID = "item_id";
	private static final String ITEM_NAME = "item_name";
	private static final String PRICE = "price";
	private static final String STOCK = "stock";
	private static final String INFORMATION = "information";
	private static final String IF_DELETE = "if_delete";
	private static final String CATEGORY_ID = "category_id";
	private static final String CATEGORY_NAME = "category_name";
	private static final String SALE_COUNT = "SUM(IFNULL(sale_count, 0))";

	public Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	public java.sql.ResultSetMetaData rsmd;

	public Search(Connection con) {
		this.con = con;
	}

	//売上数検索メソッド(「入力した数値」以上検索)
	public List<DTO> saleCountMoreSearch(int count) throws SQLException, ClassNotFoundException {
		List<DTO> rtnList = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append("    " + "item.item_id, item_name, category_name, price, stock, SUM(IFNULL(sale_count, 0)),information");
		sql.append(" FROM ");
		sql.append("    " + TBL_NAME);
		sql.append("    " + " INNER JOIN category \r\n"
				+ "        ON item.category_id = category.category_id \r\n"
				+ "    LEFT JOIN receipt_mg \r\n"
				+ "        ON item.item_id = receipt_mg.item_id ");
		sql.append(" WHERE ");
		sql.append(" IFNULL(sale_count, 0) >= ?");
		sql.append(" AND ");
		sql.append(IF_DELETE + " = 0");
		sql.append(" GROUP BY ");
		sql.append(ITEM_ID);
		sql.append(" ORDER BY ");
		sql.append(SALE_COUNT + " DESC");
		try {

			this.stmt = con.prepareStatement(sql.toString());
			stmt.setInt(1, count);
			rs = stmt.executeQuery();
			rsmd = rs.getMetaData();
			while (rs.next()) {
				DTO saleCount = new DTO();
				saleCount.setItemId(rs.getInt(ITEM_ID));
				saleCount.setItemName(rs.getString(ITEM_NAME));
				saleCount.setCategoryName(rs.getString(CATEGORY_NAME));
				saleCount.setPrice(rs.getInt(PRICE));
				saleCount.setStock(rs.getInt(STOCK));
				saleCount.setSaleCount(rs.getInt(SALE_COUNT));
				saleCount.setInformation(rs.getString(INFORMATION));

				rtnList.add(saleCount);
			}
		} finally {
			DbUtil.closeStatement(this.stmt);
		}
		return rtnList;
	}

	//売上数検索メソッド(「入力した数値」以下検索)
	public List<DTO> saleCountLessSearch(int count) throws SQLException, ClassNotFoundException {
		List<DTO> rtnList = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append("    " + "item.item_id, item_name, category_name, price, stock, SUM(IFNULL(sale_count, 0)),information");
		sql.append(" FROM ");
		sql.append("    " + TBL_NAME);
		sql.append("    " + " INNER JOIN category \r\n"
				+ "        ON item.category_id = category.category_id \r\n"
				+ "    LEFT JOIN receipt_mg \r\n"
				+ "        ON item.item_id = receipt_mg.item_id ");
		sql.append(" WHERE ");
		sql.append(" IFNULL(sale_count, 0) <= ?");
		sql.append(" AND ");
		sql.append(IF_DELETE + " = 0");
		sql.append(" GROUP BY ");
		sql.append(ITEM_ID);
		sql.append(" ORDER BY ");
		sql.append(SALE_COUNT + " DESC");
		try {

			this.stmt = con.prepareStatement(sql.toString());
			stmt.setInt(1, count);
			rs = stmt.executeQuery();
			rsmd = rs.getMetaData();
			while (rs.next()) {
				DTO saleCount = new DTO();
				saleCount.setItemId(rs.getInt(ITEM_ID));
				saleCount.setItemName(rs.getString(ITEM_NAME));
				saleCount.setCategoryName(rs.getString(CATEGORY_NAME));
				saleCount.setPrice(rs.getInt(PRICE));
				saleCount.setStock(rs.getInt(STOCK));
				saleCount.setSaleCount(rs.getInt(SALE_COUNT));
				saleCount.setInformation(rs.getString(INFORMATION));

				rtnList.add(saleCount);
			}
		} finally {
			DbUtil.closeStatement(this.stmt);
		}
		return rtnList;
	}

	//在庫切れ商品検索メソッド
	public List<DTO> stockSearch() throws SQLException, ClassNotFoundException {
		List<DTO> rtnList = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append("    " + "item.item_id, item_name, category_name, price, stock, SUM(IFNULL(sale_count, 0)),information");
		sql.append(" FROM ");
		sql.append("    " + TBL_NAME);
		sql.append("    " + " INNER JOIN category \r\n"
				+ "        ON item.category_id = category.category_id \r\n"
				+ "    LEFT JOIN receipt_mg \r\n"
				+ "        ON item.item_id = receipt_mg.item_id ");
		sql.append(" WHERE ");
		sql.append(STOCK + " = 0");
		sql.append(" AND ");
		sql.append(IF_DELETE + " = 0");
		sql.append(" GROUP BY ");
		sql.append(ITEM_ID);
		try {

			this.stmt = con.prepareStatement(sql.toString());
			rs = stmt.executeQuery();
			rsmd = rs.getMetaData();
			while (rs.next()) {
				DTO saleCount = new DTO();
				saleCount.setItemId(rs.getInt(ITEM_ID));
				saleCount.setItemName(rs.getString(ITEM_NAME));
				saleCount.setCategoryName(rs.getString(CATEGORY_NAME));
				saleCount.setPrice(rs.getInt(PRICE));
				saleCount.setStock(rs.getInt(STOCK));
				saleCount.setSaleCount(rs.getInt(SALE_COUNT));
				saleCount.setInformation(rs.getString(INFORMATION));

				rtnList.add(saleCount);
			}
		} finally {
			DbUtil.closeStatement(this.stmt);
		}
		return rtnList;
	}
	//在庫0から商品名検索
		public List<DTO> stockItemNameSearch(String inputName) throws SQLException, ClassNotFoundException {
			List<DTO> rtnList = new ArrayList<>();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT ");
			sql.append("    " + "item.item_id, item_name, category_name, price, stock, SUM(IFNULL(sale_count, 0)),information");
			sql.append(" FROM ");
			sql.append("    " + TBL_NAME);
			sql.append("    " + " INNER JOIN category \r\n"
					+ "        ON item.category_id = category.category_id \r\n"
					+ "    LEFT JOIN receipt_mg \r\n"
					+ "        ON item.item_id = receipt_mg.item_id ");
			sql.append(" WHERE ");
			sql.append(ITEM_NAME + " LIKE ?");
			sql.append(" AND ");
			sql.append(STOCK + " = 0");
			sql.append(" AND ");
			sql.append(IF_DELETE + " = 0");
			sql.append(" GROUP BY ");
			sql.append(ITEM_ID);
			try {

				this.stmt = con.prepareStatement(sql.toString());
				stmt.setString(1, "%" + inputName + "%");
				rs = stmt.executeQuery();
				rsmd = rs.getMetaData();
				while (rs.next()) {
					DTO saleCount = new DTO();
					saleCount.setItemId(rs.getInt(ITEM_ID));
					saleCount.setItemName(rs.getString(ITEM_NAME));
					saleCount.setCategoryName(rs.getString(CATEGORY_NAME));
					saleCount.setPrice(rs.getInt(PRICE));
					saleCount.setStock(rs.getInt(STOCK));
					saleCount.setSaleCount(rs.getInt(SALE_COUNT));
					saleCount.setInformation(rs.getString(INFORMATION));

					rtnList.add(saleCount);
				}
			} finally {
				DbUtil.closeStatement(this.stmt);
			}
			return rtnList;
		}

	//商品名検索メソッド
	public List<DTO> itemNameSearch(String itemName) throws SQLException, ClassNotFoundException {
	    List<DTO> rtnList = new ArrayList<>();
	    StringBuilder sql = new StringBuilder();
	    sql.append("SELECT ");
	    sql.append("    item.item_id, item_name, category_name, price, stock, SUM(IFNULL(sale_count, 0)), information");
	    sql.append(" FROM ");
	    sql.append("    " + TBL_NAME);
	    sql.append("    INNER JOIN category ON item.category_id = category.category_id");
	    sql.append("    LEFT JOIN receipt_mg ON item.item_id = receipt_mg.item_id");
	    sql.append(" WHERE ");
	    sql.append(ITEM_NAME + " LIKE ?"); // Modified line
	    sql.append(" AND ");
	    sql.append(IF_DELETE + " = 0");
	    sql.append(" GROUP BY ");
		sql.append(ITEM_ID);
	    
	    try {
	        this.stmt = con.prepareStatement(sql.toString());
	        stmt.setString(1, "%" + itemName + "%"); // Modified line
	        rs = stmt.executeQuery();
	        rsmd = rs.getMetaData();
	        
	        while (rs.next()) {
	            DTO saleCount = new DTO();
	            saleCount.setItemId(rs.getInt(ITEM_ID));
	            saleCount.setItemName(rs.getString(ITEM_NAME));
	            saleCount.setCategoryName(rs.getString(CATEGORY_NAME));
	            saleCount.setPrice(rs.getInt(PRICE));
	            saleCount.setStock(rs.getInt(STOCK));
	            saleCount.setSaleCount(rs.getInt(SALE_COUNT));
	            saleCount.setInformation(rs.getString(INFORMATION));
	            
	            rtnList.add(saleCount);
	        }
	    } finally {
	        DbUtil.closeStatement(this.stmt);
	    }
	    
	    return rtnList;
	}



	//カテゴリ検索メソッド
	public List<DTO> categorySearch(String categoryName) throws SQLException, ClassNotFoundException {
		List<DTO> rtnList = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append("    " + "item.item_id, item_name, category_name, price, stock, SUM(IFNULL(sale_count, 0)),information");
		sql.append(" FROM ");
		sql.append("    " + TBL_NAME);
		sql.append("    " + " INNER JOIN category \r\n"
				+ "        ON item.category_id = category.category_id \r\n"
				+ "    LEFT JOIN receipt_mg \r\n"
				+ "        ON item.item_id = receipt_mg.item_id ");
		sql.append(" WHERE ");
		sql.append(CATEGORY_NAME + " = ?");
		sql.append(" AND ");
		sql.append(IF_DELETE + " = 0");
		sql.append(" GROUP BY ");
		sql.append(ITEM_ID);
		try {

			this.stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, categoryName);
			rs = stmt.executeQuery();
			rsmd = rs.getMetaData();
			while (rs.next()) {
				DTO saleCount = new DTO();
				saleCount.setItemId(rs.getInt(ITEM_ID));
				saleCount.setItemName(rs.getString(ITEM_NAME));
				saleCount.setCategoryName(rs.getString(CATEGORY_NAME));
				saleCount.setPrice(rs.getInt(PRICE));
				saleCount.setStock(rs.getInt(STOCK));
				saleCount.setSaleCount(rs.getInt(SALE_COUNT));
				saleCount.setInformation(rs.getString(INFORMATION));

				rtnList.add(saleCount);
			}
		} finally {
			DbUtil.closeStatement(this.stmt);
		}
		return rtnList;
	}

} 