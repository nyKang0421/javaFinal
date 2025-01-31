package dto;

public class Cart {
	private static int num;
	private int cartNum;
	private String id;
	private int itemNum;
	private int itemCnt;
	public int getCartNum() {
		return cartNum;
	}
	public void setCartNum(int cartNum) {
		this.cartNum = cartNum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getItemNum() {
		return itemNum;
	}
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	public int getItemCnt() {
		return itemCnt;
	}
	public void setItemCnt(int itemCnt) {
		this.itemCnt = itemCnt;
	}
	public static int getNum() {
		return num;
	}
	public static void setNum(int num) {
		Cart.num = num;
	}
	@Override
	public String toString() {
		return String.format("%s/%s/%s/%s\n",cartNum,id,itemNum,itemCnt);
	}
	public Cart(int cartNum, String id, int itemNum, int itemCnt) {
		this.cartNum = cartNum;
		this.id = id;
		this.itemNum = itemNum;
		this.itemCnt = itemCnt;
	}
	public Cart(String id, int itemNum, int itemCnt) {
		cartNum = ++num;
		this.id = id;
		this.itemNum = itemNum;
		this.itemCnt = itemCnt;
	}
	public void printACart() {
		System.out.printf("[%3s][%6s][%3s][%3s개]\n",cartNum,id,itemNum,itemCnt);
	}
	
	
	
	

}
