package dao;

import java.util.ArrayList;

import dto.Cart;
import dto.Item;
import dto.Member;
import util.Util;

public class CartDAO {
	Util util = Util.getInterface();
	
	private ArrayList<Cart> cartList;
	
	private CartDAO() {
		cartList = new ArrayList<>();
	}
	private static CartDAO instance = new CartDAO();
	public static CartDAO getInstance() {
		return instance;
	}
	public ArrayList<int[]> getItemCntList(ArrayList<int[]> itemNumList) {
		if(cartList.size() == 0) return null;
		for(Cart c : cartList) {
			for(int i = 0; i<itemNumList.size(); i++) {
				if(c.getItemNum() == itemNumList.get(i)[0]) {
					itemNumList.get(i)[1] += c.getItemCnt();
				}
			}
		}
		return itemNumList;
	}
	public void removeCart(int delNum) {
		if(cartList == null||cartList.size() == 0) {
			util.printMsg("no cart data");
			return;
		}
		for(int i = 0; i<cartList.size();i++) {
			if(cartList.get(i).getItemNum() == delNum) {
				cartList.remove(i);
			}
		}
		util.printMsg("카트리스트 내 아이템 정보 삭제 완료");
	}
	public void removeCart(String id) {
		if(cartList.size() == 0) {
			util.printMsg("no cart data");
			return;
		}
		for(int i = 0; i<cartList.size();i++) {
			if(cartList.get(i).getId().equals(id)) {
				cartList.remove(i);
				i--;
			}
		}
		util.printMsg("카트리스트 내 회원 정보 삭제 완료");
		
	}
	public String getUpdateData() {
		String s = "";
		if(cartList == null||cartList.size()==0) return s;
		for(Cart c : cartList) {
			s += c;
		}
		return s;
	}
	public void upLoadData(String newData) {
		if(newData == null || newData.equals("")) {
			util.printErr("불러온 데이터가 없습니다");
			return;
		}
		String[] temp = newData.split("\n");
		cartList.clear();
		int lastNum = 0;
		for(String s : temp) {
			String[] info = s.split("/");
			cartList.add(new Cart(Integer.parseInt(info[0]),info[1],Integer.parseInt(info[2]),Integer.parseInt(info[3])));
			lastNum = Integer.parseInt(info[0]);
		}
		Cart.setNum(lastNum);
		util.printMsg("데이터 불러오기 완료");
		
	}
	public void printAllCartList() {
		if(cartList == null || cartList.size() == 0) {
			util.printErr("no cart data");
			return;
		}
		for(Cart c : cartList) {
			c.printACart();
		}
	}
	public void removeMemberCart(String id) {
		if(cartList == null || cartList.size() == 0) {
			util.printErr("no cart data");
			return;
		}
		int cnt = printMemberCartList(id);
		if(cnt == 0) return;
		
		
		int cartNum = util.getIntValue("카트번호", cartList.get(0).getCartNum(),cartList.get(cartList.size()-1).getCartNum());
		if(cartNum == -1) return;
		removeACartList(id);
	}
	
	public int printMemberCartList(String id) {
		int cnt = 0;
		for(Cart c : cartList) {
			if(c.getId().equals(id)) {
				c.printACart();
				cnt++;
			}
		}
		if(cnt == 0) {
			util.printErr("no cart data");
		}
		return cnt;
	}
	
	public void removeACartList(String id) {
		int cartNum = util.getIntValue("카트번호", cartList.get(0).getCartNum(),cartList.get(cartList.size()-1).getCartNum());
		if(cartNum == -1) return;
		int idx = -1;
		for(int i = 0; i<cartList.size();i++) {
			if(cartList.get(i).getId().equals(id)&&cartList.get(i).getCartNum()==cartNum) {
				idx = i;
			}
		}
		if(idx == -1) {
			util.printErr("검색 회원의 카트가 아닙니다");
			return;
		}
		cartList.remove(idx);
		util.printMsg("카트 삭제 완료");
	}
	public void modifyCart(String loginId) {
		System.out.println("[1] 구매수량수정\n[2] 구매취소\n[3] 뒤로가기");
		int sel = util.getIntValue("메뉴", 0, 3);
		if (sel == 3) {
			util.printMsg("메뉴로 돌아갑니다");

		}else if (sel == 1) {
			printMemberCartList(loginId);
			modifyCartCnt(loginId);
			
		}
		else if (sel == 2) {
			removeACartList(loginId);
			
		}
	}
	private void modifyCartCnt(String loginId) {
		int cartNum = util.getIntValue("카트번호", cartList.get(0).getCartNum(),cartList.get(cartList.size()-1).getCartNum());
		if(cartNum == -1) return;
		int idx = -1;
		for(int i = 0; i<cartList.size();i++) {
			if(cartList.get(i).getId().equals(loginId)&&cartList.get(i).getCartNum()==cartNum) {
				idx = i;
			}
		}
		if(idx == -1) {
			util.printErr("회원님의 카트가 아닙니다");
			return;
		}
		int cartCnt = util.getIntValue("구매수량", 1,100);
		if(cartNum == -1) return;
		if(cartCnt == cartList.get(idx).getItemCnt()) {
			util.printErr("기존 수량과 동일합니다");
			return;
		}
		cartList.get(idx).setItemCnt(cartCnt);
		util.printMsg("구매 수량 수정 완료");
		
	}
	
	public ArrayList<int[]> getMyShoppingList(String loginId) {
		ArrayList<int[]> temp = new ArrayList();
		if(cartList == null || cartList.size()==0) return temp;
		for(Cart c : cartList) {
			if(c.getId().equals(loginId)) {
				boolean check = true;
				int[] info = {c.getItemNum(),c.getItemCnt()};
				for(int i = 0; i<temp.size();i++) {
					if(info[0] == temp.get(i)[0]) {
						temp.get(i)[1]+=info[1];
						check = false;
						break;
					}
				}
				if(check) {
					temp.add(info);
				}
			}
		}
		return temp;
	}
	public void insetCart(String loginId, int[] buy) {
		cartList.add(new Cart(loginId,buy[0],buy[1]));
		cartList.get(cartList.size()-1).printACart();
		util.printMsg("구매 완료");
	}
	


}
