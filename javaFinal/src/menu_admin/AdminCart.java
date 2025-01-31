package menu_admin;

import controller.MallController;
import dao.CartDAO;
import mall.MenuCommand;
import util.Util;

public class AdminCart implements MenuCommand {
	private MallController cont;
	private Util util;
	private CartDAO cdao;

	@Override
	public void init() {
		cont = MallController.getInstance();
		util = Util.getInterface();
		cdao = CartDAO.getInstance();
		System.out.println("=====[ 관리자 카트관리 ]=====");
		System.out.println("[1] 카트목록\n[2] 카트삭제\n[3] 뒤로가기\n[0] 종료");
		System.out.println("=====================");
		
	}

	@Override
	public boolean update() {
		int sel = util.getIntValue("메뉴", 0, 3);
		if (sel == 0) {
			System.out.println("[ 프로그램 종료 ]");
			cont.setNext(null);
		}else if (sel == 1) {
			cdao.printAllCartList();

		}else if (sel == 2) {
			String id = util.getStrValue("ID");
			if(id == null) return false;
			cdao.removeMemberCart(id);
			
		}else if (sel == 3) {
			util.printMsg("관리자 메뉴로 돌아갑니다");
			cont.setNext("AdminMain");

		}
		
		return false;
	}

}
