package action.DTO;

public class EdTO {
	//カテゴリーテーブル
	private int categoryId;
	private String categoryName;

	//商品テーブル
	private String itemName;
	private int price;
	private int stock;
	private String information;
	private int itemId;
	private int ifDelete;

	//レシートテーブル
	private int receiptId;
	private String buyTime;

	//購入履歴中間テーブル
	private int saleCount;
	
	//構成比
	private int ratio;

	public int getCategoryId() {
		return categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public String getItemName() {
		return itemName;
	}

	public int getPrice() {
		return price;
	}
	
	
	public int getStock() {
		return stock;
	}

	public String getInformation() {
		return information;
	}

	public int getItemId() {
		return itemId;
	}

	public int getIfDelete() {
		return ifDelete;
	}

	public int getReceiptId() {
		return receiptId;
	}

	public String getBuyTime() {
		return buyTime;
	}

	public int getSaleCount() {
		return saleCount;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	

	public void setPrice(int price) {
		this.price = price;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public void setIfDelete(int ifDelete) {
		this.ifDelete = ifDelete;
	}

	public void setReceiptId(int receiptId) {
		this.receiptId = receiptId;
	}

	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}

	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}

	public int getRatio() {
		return ratio;
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
	}

}

