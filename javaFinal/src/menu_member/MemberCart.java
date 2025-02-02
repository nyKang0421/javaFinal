package menu_member;

import java.util.ArrayList;

import controller.MallController;
import dao.CartDAO;
import dao.ItemDAO;
import mall.MenuCommand;
import util.Util;

public class MemberCart implements MenuCommand {
	private MallController cont;
	Util util;
	CartDAO cdao;
	ItemDAO idao;

	@Override
	public void init() {
		cont = MallController.getInstance();
		util = Util.getInterface();
		cdao = CartDAO.getInstance();
		idao = ItemDAO.getInstance();
		System.out.println("=====[ 내정보 ]=====");
		System.out.println("[1] 내쇼핑목록\n[2] 쇼핑목록수정\n[3] 뒤로가기\n[0] 종료");
		System.out.println("=====================");
		
	}

	@Override
	public boolean update() {
		ArrayList<int[]> myShoppingList = cdao.getMyShoppingList(cont.getLoginId());
		if(myShoppingList == null || myShoppingList.size() == 0) {
			util.printErr("no cart data");
			cont.setNext("MemberMain");
			return false;
		}
		int sel = util.getIntValue("메뉴", 0, 3);
		if (sel == 0) {
			System.out.println("[ 프로그램 종료 ]");
			cont.setNext(null);
		}else if (sel == 1) {
			idao.printMyList(myShoppingList);

		}else if (sel == 2) {
			cdao.modifyCart(cont.getLoginId());
			
		}else if (sel == 3) {
			util.printMsg("메뉴로 돌아갑니다");
			cont.setNext("MemberMain");
		}
		
		return false;
	}

}
