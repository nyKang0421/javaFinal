package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import dto.Item;
import dto.Member;
import util.Util;

public class ItemDAO {
	
	Util util = Util.getInterface();
	private ArrayList<Item> itemList;
	
	private String[] categories; // = {"고기","생선","음료","과자","아이스크림","과일","유제품","기타"};
	
	private ItemDAO() {
		itemList = new ArrayList<>();
		String cData = util.getCategories();
		if(cData == null) {
			cData = "고기\n생선\n음료\n과자\n아이스크림\n과일\n유제품\n야채\n기타";
			util.setCategories(cData);
		}
		String[] info = cData.split("\n");
		categories = new String[info.length];
		for(int i = 0; i<info.length;i++) {
			categories[i] = info[i];
		}
	}
	
	private static ItemDAO instance = new ItemDAO();
	public static ItemDAO getInstance() {
		return instance;
	}
	
	private String checkCategory(String string) {
		for(String s : categories) {
			if(string.equals(s)) {
				return s;
			}
		}
		return null;
	}
	
	public void insertItem() {
		System.out.println(Arrays.toString(categories));
		String category = util.getStrValue("카테고리");
		
		if(checkCategory(category) == null) {
			util.printErr("존재하지 않는 카테고리 입니다");
			return;
		}
		
		String itemName = util.getStrValue("제품명");
		
		if(checkCategory(itemName) != null) {
			util.printErr("카테고리 명은 사용할 수 없습니다");
			return;
		}
		
		if(checkItemNameDupl(itemName) != null) {
			util.printErr("이미 사용중인 제품명입니다");
			return;
		}
		
		int price = util.getIntValue("제품가격",100,1000000);
		if(price == -1) return;
		
		itemList.add(new Item(category,itemName,price));
		util.printMsg("아이탬 등록 완료");
		
	}

	private Object checkItemNameDupl(String itemName) {
		if(itemList.size() == 0) return null;
		for(Item i : itemList)
			if(i.getItemName().equalsIgnoreCase(itemName))
				return itemName;
		return null;
	}
	

	public int removeItem() {
		int delNum = util.getIntValue("삭제 아이템 번호", 1, itemList.get(itemList.size()-1).getItemNum());
		if(delNum == -1) return delNum;
		boolean isCheck = false;
		for(int i = 0; i<itemList.size();i++) {
			if(itemList.get(i).getItemNum() == delNum) {
				itemList.remove(i);
				isCheck = true;
			}
		}
		if(!isCheck) {
			util.printErr("없는 아이템번호입니다");
			return -1;
		}
		
		util.printMsg("아이템 정보 삭제 완료");
		return delNum;
	}

	public int getListSize() {
		return itemList.size();
	}

	public void printAllItem() {
		System.out.println("==== 카테고리별 아이템 목록 ================");
		if(itemList.size() == 0 ) {
			util.printMsg("no Item data");
			return;
		}
		ArrayList<Item> temp = (ArrayList<Item>) itemList.clone();
		Collections.sort(temp);
		for(Item i : temp) {
			i.printAItem();
			System.out.println();
		}
	}

	public ArrayList<int[]> getItemNumList() {
		ArrayList<int[]> info = new ArrayList();
		ArrayList<Item> temp = (ArrayList<Item>) itemList.clone();
		Collections.sort(temp);
		for(Item i : temp) {
			int[] numinfo = {i.getItemNum(),0};
			info.add(numinfo);
		}
		return info;
	}
	
	private int getListIdx(int itemNum) {
		int idx = -1;
		for(int i = 0; i<itemList.size();i++) {
			if(itemList.get(i).getItemNum() == itemNum)
				idx = i;
		}
		return idx;
	}

	public void printAllwithCnt(ArrayList<int[]> itemNumList) {
		if(itemNumList == null) {
			util.printMsg("no cart data");
			return;
		}
		ArrayList<Integer> cntList = makePrintList(itemNumList);
		ArrayList<Item> temp = (ArrayList<Item>) itemList.clone();
		Collections.sort(temp);
		
		for(Integer i : cntList)
			for(int k = 0; k<itemNumList.size();k++) {
				if(i == itemNumList.get(k)[1]) {
					int idx = getListIdx(itemNumList.get(k)[0]);
					if(idx == -1) {
						util.printErr("검색오류");
						continue;
					}
					if(itemNumList.get(k)[1]!=0) {
						itemList.get(idx).printAItem();
						System.out.printf(" %d개",itemNumList.get(k)[1]);
						System.out.println();
					}
				}
			}
	}

	private ArrayList<Integer> makePrintList(ArrayList<int[]> itemNumList) {
		int max = Integer.MAX_VALUE;
		ArrayList<Integer> cntList = new ArrayList();
		while(true) {
			int maxNext = Integer.MIN_VALUE;
			for(int i = 0; i<itemNumList.size();i++) {
				if(itemNumList.get(i)[1]<max&&itemNumList.get(i)[1]>maxNext) {
					maxNext = itemNumList.get(i)[1];
				}
			}
			cntList.add(maxNext);
			if(maxNext == 0) break;
			max = maxNext;
			
		}
			
		return cntList;
	}

	public String getUpdateData() {
		String s = "";
		if(itemList == null||itemList.size()==0) return s;
		for(Item i : itemList) {
			s += i;
		}
		return s;
	}

	public void upLoadData(String newData) {
		if(newData == null || newData.equals("")) {
			util.printErr("불러온 데이터가 없습니다");
			return;
		}
		String[] temp = newData.split("\n");
		itemList.clear();
		int lastNum = 0;
		for(String s : temp) {
			String[] info = s.split("/");
			itemList.add(new Item(Integer.parseInt(info[0]),info[1],info[2],Integer.parseInt(info[3])));
			lastNum = Integer.parseInt(info[0]);
		}
		Item.setNum(lastNum);
		util.printMsg("데이터 불러오기 완료");
		
	}

	public void printMyList(ArrayList<int[]> list) {
		int cnt = 0;
		int total = 0;
		for(int i = 0; i<list.size();i++) {
			for(Item item : itemList) {
				if(item.getItemNum() == list.get(i)[0]) {
					int sum = list.get(i)[1]*item.getPrice();
					System.out.printf("[%3s]%10s(%7s원)%5s개 총 %s 원\n",i+1,item.getItemName(),item.getPrice(),list.get(i)[1],sum);
					cnt += list.get(i)[1];
					total += sum;
				}
			}
		}
		
		System.out.println("=====================");
		System.out.printf("총 %s 개 ( %s 원 )\n",cnt,total);
		
	}

	public int[] getItemNum_Cnt() {
		if(itemList == null || itemList.size()==0) {
			util.printErr("no item data");
			return null;
		}
		int[] buy = new int[2];
		System.out.println("========== 쇼핑몰에 오신걸 환영합니다 ============");
		for(int i = 0; i<categories.length;i++) {
			System.out.printf("[%d] %s\n",i+1,categories[i]);
		}
		int selCate = util.getIntValue("카테고리", 1, categories.length);
		if(selCate == -1) return null;
		ArrayList<Item> tempList = new ArrayList();
		for(Item i : itemList) {
			if(i.getCategoryName().equals(categories[selCate-1])) {
				tempList.add(i);
			}
		}
		if(tempList.size() == 0) {
			util.printErr("해당 카테고리 등록 아이템이 없습니다");
			return null;
		}
		for(int i = 0; i<tempList.size();i++) {
			System.out.printf("[%d] %s ( %s원 )\n", i+1, tempList.get(i).getItemName(), tempList.get(i).getPrice());
		}
		String getItem = util.getStrValue("아이템");
		if(getItem == null) return null;
		for(Item i : tempList) {
			if(i.getItemName().equals(getItem)) {
				
				int cnt = util.getIntValue("구매수량", 1, 100);
				if(cnt == -1) return null;
				buy[0] = i.getItemNum();
				buy[1] = cnt;
				return buy;
			}
		}
		util.printErr("구매품 입력 오류");
		return null;
	}

	public int[] searchItemNum_Cnt() {
		String itemWord = util.getStrValue("아이템");
		if(itemWord == null) return null;
		ArrayList<Item> tempList = new ArrayList();
		for(Item i : itemList) {
			if(i.getItemName().contains(itemWord)) {
				tempList.add(i);
			}
		}
		if(tempList.size() == 0) {
			util.printErr("해당 카테고리 등록 아이템이 없습니다");
			return null;
		}
		System.out.println("[0] 뒤로가기");
		for(int i = 0; i<tempList.size();i++) {
			System.out.printf("[%d] %s ( %s원 )\n", i+1, tempList.get(i).getItemName(), tempList.get(i).getPrice());
		}
		int selNum = util.getIntValue("아이템 번호", 0, tempList.size());
		if(selNum == -1) return null;
		if(selNum == 0) {
			System.out.println("메뉴로 이동합니다");
			return null;
		}else {
			int[] buy = new int[2];
			int cnt = util.getIntValue("구매수량", 1, 100);
			if(cnt == -1) return null;
			buy[0] = tempList.get(selNum-1).getItemNum();
			buy[1] = cnt;
			return buy;
		}
		
		//return null;
	}

}
