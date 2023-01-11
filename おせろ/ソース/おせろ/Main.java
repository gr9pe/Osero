package osero;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

	static String[][] field = new String[10][10];
	static String white = "○";
	static String black = "●";
	static String wall = "□";
	static String blank = "・";
	static String turn;

	public static void main(String[] args) {
		System.out.println("2桁の数値でマスを指定してください");
		Scanner sc = new Scanner(System.in);
		setfield();
		printField();
		turn = black;

		while(true) {
			if(turn.equals(black)) {
				System.out.println("黒のターン");
			}else {
				System.out.println("白のターン");
			}

			if(canPutPosList(turn).isEmpty()) {
				System.out.println("置ける場所がありません");
				changeTurn();
			}

			String input = sc.nextLine();

			if(checkInput(input)) {
				int inputRow = Character.getNumericValue(input.charAt(0));
				int inputCol = Character.getNumericValue(input.charAt(1));
				int[]inputPos = {inputRow,inputCol};

				boolean canPutFlag = false;
				for(int[]canPutPos : canPutPosList(turn)) {
					if(Arrays.equals(canPutPos,inputPos)) {
						canPutFlag = true;
					}
				}
				if(canPutFlag) {
					reverse(inputPos);
					printField();
				}else {
					System.out.println("その場所には置けません");
					continue;
				}
				if(!isFinish()) {
					changeTurn();
				}else {
					printResult();
					break;
				}
			}else {
				continue;
			}
		}
	}
	public static void printField() {
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				System.out.print(field[i][j]);
			}
			System.out.println();
		}
	}

	public static void setfield() {
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				if(i==0||i==9||j==0||j==9) {
					field[i][j] = wall;
				}else{
					field[i][j] = blank;
				}
			}
		}
		field[5][5] = black;
		field[4][4] = black;
		field[5][4] = white;
		field[4][5] = white;
	}

	//すべてのマスについて返せる石があるか8方向を捜索し、該当マスの位置を配列で返す。
	public static List<int[]> canPutPosList(String color) {

		int directions[][] = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
		String enemy = getEnemy(color);
		List<int[]>canPutList = new ArrayList<>();

		for(int row=1;row<9;row++) {
			for(int col=1;col<9;col++) {
				if(!field[row][col].equals(blank)) {
					continue;
				}

				for(int[]direction:directions) {
					boolean canPutFlag = false;
					int searchRow = row + direction[0];
					int searchCol = col + direction[1];

					if(!field[searchRow][searchCol].equals(enemy)) {
						continue;
					}

					while(true) {
						searchRow += direction[0];
						searchCol += direction[1];

						if(field[searchRow][searchCol] != enemy && field[searchRow][searchCol] != turn) {
							break;

						}else if(field[searchRow][searchCol].equals(enemy)) {
							continue;
						}else {
							int[]pos = {row,col};
							canPutList.add(pos);
							canPutFlag = true;
							break;
						}
					}
					if(canPutFlag) {
						break;
					}
				}
			}
		}
		return canPutList;
	}

	public static void reverse(int[]inputPos) {

		int directions[][] = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
		String enemy = getEnemy(turn);

		int putRow = inputPos[0];
		int putCol = inputPos[1];

		field[putRow][putCol] = turn;

			for(int[]direction:directions) {
				int row = putRow + direction[0];
				int col = putCol + direction[1];

				if(!field[row][col].equals(enemy)) {
					continue;
				}


				List<int[]>reversePosList = new ArrayList<>();
				int[] fReversePos = {row,col};
				reversePosList.add(fReversePos);

				boolean reverseFlag = false;
				while(true) {
					row += direction[0];
					col += direction[1];

					if(!field[row][col].equals(enemy)&&!field[row][col].equals(turn)) {
						break;
					}
					if(field[row][col].equals(enemy)) {
						int[] reversePos = {row,col};
						reversePosList.add(reversePos);
					}else {
						reverseFlag = true;
						break;
					}
				}
				if(reverseFlag) {
					for(int[]reversePos:reversePosList) {
						field[reversePos[0]][reversePos[1]] = turn;
					}
				}
			}
	}

	public static void changeTurn() {
		if(turn.equals(black)) {
			turn = white;
		}else {
			turn = black;
		}
	}

	public static String getEnemy(String color) {
		if(color.equals(black)) {
			return white;
		}
		return black;
	}

	public static boolean isFinish() {
		List<int[]>canPutWhiteList = canPutPosList(white);
		List<int[]>canPutBlackList = canPutPosList(black);
		if(canPutWhiteList.isEmpty()&&canPutBlackList.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean checkInput(String input) {
		if(input.length()==2&&input.matches("[+-]?\\d*(\\.\\d+)?")) {
			return true;
		}
		System.out.println("2桁の数値でマスを指定してください");
		System.out.println("入力例：53");
		return false;
	}

 	public static void printResult() {
		System.out.println("----結果----");
		int blackCount = 0;
		int whiteCount = 0;
		for(int row=1;row<9;row++) {
			for(int col=1;col<9;col++) {
				if(field[row][col].equals(black)) {
					blackCount++;
				}else {
					whiteCount++;
				}
			}
		}
		System.out.println(blackCount+"："+whiteCount);
		if(blackCount>whiteCount) {
			System.out.println("黒の勝ち");
		}else if(blackCount>whiteCount){
			System.out.println("白の勝ち");
		}else {
			System.out.println("引き分け");
		}
	}

}
