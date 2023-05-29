package action.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import action.DAO.AddNew;
import action.DAO.Analyse;
import action.DAO.Delete;
import action.DAO.Search;
import action.DTO.DTO;
import action.util.DbUtil;

public class classManager {
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		System.out.println("システムを開始します");
		while (true) {
			try {
				new classManager().start();
			} catch (Exception e) {
				System.out.println("予期せぬエラーが発生しました");
				e.printStackTrace();
			}
		}
	}
	
	
	public void start() throws ClassNotFoundException, SQLException {
		System.out.println();
		System.out.println("1:検索 2:登録 3:更新 4:削除 0:終了");
		String ope = sc.nextLine();
		switch (ope) {
		case "1":
			dAnalyse();
			break;
		case "2":
			addItem();
			break;
		 case "4":  // let's assume 5 for searching an item
		        searchItem();
		        break;
		 case "5":
			    categorySearch();
			    break;
		 case "6":
			    searchByName();
			    break;
		case "0":
			System.out.println("システムを終了します");
			System.exit(0);
		default:
			System.out.println("指定の番号を入力してください");
			break;
		}
	}
//	public static List<DTO> dAnalyse1() throws ClassNotFoundException, SQLException {
//		List<DTO> List = new ArrayList<>();
//		Connection con = DbUtil.getConnection();
//		Analyse Dao = new Analyse(con);
//	
//		
//		int sum =0;
//		try {
////			
//			List = Dao.abcAnalyse();
//			if(!List.isEmpty()) {
//				System.out.println("hi");
//			}
//			
//			
//		} finally {
//			DbUtil.closeConnection(Dao.con);
//		}
//		return List;
//	}
	
	
	
	private void searchByName() throws ClassNotFoundException, SQLException {
		List<DTO> itemList = new ArrayList<>();
		Connection con = DbUtil.getConnection();
		Search searchDao = new Search(con);
		System.out.println("商品名を入力してください:");
		String itemName = sc.nextLine();
		try {
		itemList=searchDao.itemNameSearch(itemName);
		System.out.println(itemList.size());
		for (DTO itm : itemList) {
			System.out.println(itm.getItemName());
		}
		
		} finally {
			DbUtil.closeConnection(searchDao.con);
		}
	
		
		
	}




	/**
	 * 社員テーブルから情報を検索する
	 *
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void dAnalyse() throws ClassNotFoundException, SQLException {
		List <DTO> List = new ArrayList<>();
		Connection con = DbUtil.getConnection();
		Analyse Dao = new Analyse(con);
		System.out.println("1:a 2:b 3:c それ以外:戻る");
		String select = sc.nextLine();
		int sum =0;
		try {
			switch (select) {
			case "1":
				List = Dao.abcAnalyse();
				System.out.println("|商品ID|"+ "\t"+"|商品名|"+ "\t"+"|カテゴリ|"+ "\t"+"|税抜き価格|"+ "\t"+"|商品説明|"+ "\t"+"|在庫数|"+"\t"+"|構成比(％)|");
				
				for (DTO itm : List) {
					sum = sum + itm.getRatio();
				}
				
				for (DTO itm : List) {
					System.out.println(itm.getItemId()+"\t"+itm.getItemName()+"\t"+Math.floor((double)itm.getRatio()/sum * 100) + "%");
				}

				break;
			default:
				return;				
			}	
			List = Dao.abcAnalyse();
			
			
		} finally {
			DbUtil.closeConnection(Dao.con);
		}
	}
	
	public static List<DTO> dAnalyse1() throws ClassNotFoundException, SQLException {
		List<DTO> List = new ArrayList<>();
		Connection con = DbUtil.getConnection();
		Analyse Dao = new Analyse(con);
	
		
		int sum =0;
		try {
//			
			List = Dao.abcAnalyse();
			if(!List.isEmpty()) {
			}
			
			
		} finally {
			DbUtil.closeConnection(Dao.con);
		}
		return List;
	}
	
	public void addItem() throws ClassNotFoundException, SQLException {
		Connection con = DbUtil.getConnection();
		AddNew addNewDao = new AddNew(con);
		
		System.out.println("商品名を入力してください:");
		String itemName = sc.nextLine();
		System.out.println("カテゴリを入力してください:");
		String categoryName = sc.nextLine();
		System.out.println("価格を入力してください:");
		int price = sc.nextInt();
		System.out.println("在庫を入力してください:");
		int stock = sc.nextInt();
		System.out.println("情報を入力してください:");
		String info = sc.nextLine();
		String infoa = sc.nextLine();
		
		
		
		try {
			addNewDao.addItem(itemName, categoryName, price, stock, infoa);
			System.out.println("新しいアイテムが追加されました!");
		} catch(SQLException e) {
			System.out.println("アイテムの追加中にエラーが発生しました: " + e.getMessage());
		} finally {
			DbUtil.closeConnection(con);
		}
	}
	public void searchItem() throws ClassNotFoundException, SQLException {
	    Connection con = DbUtil.getConnection();
	    Delete deleteDao = new Delete(con);
		
	    System.out.println("商品名を入力してください:");
	    String itemName = sc.nextLine();
		
	    try {
	        DTO item = deleteDao.selectbyName(itemName);
	        if (item != null) {
	            System.out.println("アイテムが見つかりました!");
	            System.out.println("商品ID: " + item.getItemId());
	            System.out.println("商品名: " + item.getItemName());
	            // print other properties of the item...
	        } else {
	            System.out.println("指定したアイテムが見つかりませんでした。");
	        }
	    } catch(SQLException e) {
	        System.out.println("アイテムの検索中にエラーが発生しました: " + e.getMessage());
	    } finally {
	        DbUtil.closeConnection(con);
	    }
	}
	public void categorySearch() throws ClassNotFoundException, SQLException {
		 // Establish a connection to the database
        Connection con = DbUtil.getConnection();
        // Create a Search object
        Search searchDao = new Search(con);
        
        System.out.println("カテゴリ名を入力してください:");
        String categoryName = sc.nextLine();

        try {
            // Call the categorySearch method
            List<DTO> items = searchDao.categorySearch(categoryName);
            if (items.isEmpty()) {
                System.out.println("指定したカテゴリのアイテムが見つかりませんでした。");
            } else {
                for(DTO item : items) {
                    System.out.println("商品ID: " + item.getItemId());
                    System.out.println("商品名: " + item.getItemName());
                    // print other properties of the item...
                }
            }
        } catch(SQLException e) {
            System.out.println("アイテムの検索中にエラーが発生しました: " + e.getMessage());
        } finally {
            DbUtil.closeConnection(con);
        }
	}


	
}
