package menu_admin;

import controller.MallController;
import mall.MenuCommand;
import util.Util;

public class _AdminMain implements MenuCommand {
	private MallController cont;
	private Util util;


	@Override
	public void init() {
		cont = MallController.getInstance();
		util = Util.getInterface();
		System.out.println("=====[ 관리자 ]=====");
		System.out.println("[1] 회원관리\n[2] 상품관리\n[3] 게시판관리\n[4] 카트관리\n[5] 파일관리\n[6] 로그아웃\n[0] 종료");
		System.out.println("=====================");
		
	}

	@Override
	public boolean update() {
		int sel = util.getIntValue("메뉴", 0, 6);
		if (sel == 0) {
			System.out.println("[ 프로그램 종료 ]");
			cont.setNext(null);
		}else if (sel == 1) {
			cont.setNext("AdminMember");

		}else if (sel == 2) {
			cont.setNext("AdminItem");
			
		}else if (sel == 3) {
			cont.setNext("AdminBoard");

		}else if (sel == 4) {
			cont.setNext("AdminCart");
			
		}else if (sel == 5) {
			cont.setNext("AdminFile");
			
		}else if (sel == 6) {
			cont.setNext("MallMain");
			cont.setLoginId(null);
			
		} 

		return false;
	}

}
