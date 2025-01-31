package menu_admin;

import java.util.ArrayList;

import controller.MallController;
import dao.CartDAO;
import dao.ItemDAO;
import mall.MenuCommand;
import util.Util;



public class AdminItem implements MenuCommand {
	private MallController cont;
	private Util util;
	private CartDAO cdao;
	private ItemDAO idao;
	/*
	private enum ItemCategoryName{
		MEAT("고기"),FISH("생선"),BEVERAGE("음료"),SNACK("과자"),ICECREAM("아이스크림"),
		FRUIT("과일"),OTHER("기타");
		
		private String category;

		ItemCategoryName(String category) {
			this.category = category;
		}
		
		public String getCategory(ItemCategoryName ic) {
			for(ItemCategoryName i : ItemCategoryName.values())
				if(ic == i)
					return i.category;
			return category;
		}
	}
	*/
	//private String[] categories = {"고기","생선","음료","과자","아이스크림","과일","기타"};

	@Override
	public void init() {
		cont = MallController.getInstance();
		util = Util.getInterface();
		cdao = CartDAO.getInstance();
		idao = ItemDAO.getInstance();
		System.out.println("=====[ 관리자 물품관리 ]=====");
		System.out.println("[1] 아이템추가\n[2] 아이템삭제\n[3] 총 아이템 갯수출력(판매량 높은 순으로)\n[4] 뒤로가기\n[0] 종료");
		System.out.println("=====================");
		
	}

	@Override
	public boolean update() {
		int sel = util.getIntValue("메뉴", 0, 4);
		if((sel == 2|| sel == 3) && idao.getListSize() == 0) {
			util.printErr("등록된 아이템이 없습니다");
			sel = -1;
		}
		
		if (sel == 0) {
			System.out.println("[ 프로그램 종료 ]");
			cont.setNext(null);
		}else if (sel == 1) {
			idao.printAllItem();
			idao.insertItem();

		}else if (sel == 2) {
			idao.printAllItem();
			int delNum = idao.removeItem();
			if(delNum != -1) {
				cdao.removeCart(delNum);
			}
		}else if (sel == 3) {
			ArrayList<int[]> itemNumList = idao.getItemNumList();
			itemNumList = cdao.getItemCntList(itemNumList);
			idao.printAllwithCnt(itemNumList);
			
		}else if (sel == 4) {
			util.printMsg("관리자 메뉴로 돌아갑니다");

		}
		cont.setNext("AdminMain");
		return false;
	}

}
