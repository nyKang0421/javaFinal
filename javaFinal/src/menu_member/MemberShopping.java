package menu_member;

import java.util.ArrayList;

import controller.MallController;
import dao.CartDAO;
import dao.ItemDAO;
import mall.MenuCommand;
import util.Util;

public class MemberShopping implements MenuCommand {
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
		System.out.println("[1] 구매하기\n[2] 아이템검색\n[3] 뒤로가기\n[0] 종료");
		System.out.println("=====================");
		
	}

	@Override
	public boolean update() {
		int sel = util.getIntValue("메뉴", 0, 3);
		if (sel == 0) {
			System.out.println("[ 프로그램 종료 ]");
			cont.setNext(null);
		}else if (sel == 1) {
			int[] buy = idao.getItemNum_Cnt();
			if(buy != null) {
				cdao.insetCart(cont.getLoginId(),buy);
			}
			
		}else if (sel == 2) {
			int[] buy = idao.searchItemNum_Cnt();
			if(buy != null) {
				cdao.insetCart(cont.getLoginId(),buy);
			}
			
		}else if (sel == 3) {
			util.printMsg("메뉴로 돌아갑니다");
			cont.setNext("MemberMain");
		}
		
		return false;
	}

}
