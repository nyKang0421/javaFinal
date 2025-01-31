package dto;

public class Item implements Comparable<Item> {
	private static int num;
	private int itemNum;
	private String categoryName;
	private String itemName;
	private int price;
	public Item(String category, String itemName, int price) {
		itemNum = ++num;
		categoryName = category;
		this.itemName = itemName;
		this.price = price;
		System.out.printf("itemNum : %s/categoryName : %s/itemName : %s/price : %s원/n",itemNum,categoryName,itemName,price);
	}
	public Item(int itemNum, String category, String itemName, int price) {
		this.itemNum = itemNum;
		categoryName = category;
		this.itemName = itemName;
		this.price = price;
		//System.out.printf("itemNum : %s/categoryName : %s/itemName : %s/price : %s원\n",itemNum,categoryName,itemName,price);
	}
	public static int getNum() {
		return num;
	}
	public static void setNum(int num) {
		Item.num = num;
	}
	public int getItemNum() {
		return itemNum;
	}
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return String.format("%s/%s/%s/%s\n", itemNum,categoryName,itemName,price);
	}
	@Override
	public int compareTo(Item o) {
		if(categoryName.compareTo(o.categoryName)==0) {
			return itemName.compareTo(o.itemName);
		}
		return categoryName.compareTo(o.categoryName);
	} 
	
	public void printAItem() {
		System.out.printf("[%4s][%15s][%10s][%10s원]",itemNum,categoryName,itemName,price);
		
	}
	

}
