package osero;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class game {
	
	static String[][] field = new String[10][10];
	static String white = "○";
	static String black = "●";
	static String wall = "□";
	static String blank = "・";
	static String turn;//trueで黒

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		gamestart();
		
		printField();
		
		turn = black;
		if(canPutPos().isEmpty()) {
			System.out.println("置ける場所がありません");
			changeTurn();
		}
		String input = sc.nextLine();
		if(input.length()!=2) {
			System.out.println("入力値が不正");
		}else {
			int x = Character.getNumericValue(input.charAt(0));
			int y = Character.getNumericValue(input.charAt(1));
			
			
			
			if(!isFinish()) {
				changeTurn();
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
	
	public static void gamestart() {
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
	//置ける場所の配列を返すように直します
	//盤面を総当りで、八方向について探索します
	public static List<int[]> canPutPos() {
		int directions[][] = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
		
		String enemy = getEnemy();
		List<int[]>canPutList = new ArrayList<>();
		
		for(int row=1;row<9;row++) {
			for(int col=1;col<9;col++) {
				for(int[]direction:directions) {
					
					int searchRow = x + direction[0];
					int searchCol = y + direction[1];
					 
					if(!field[searchRow][searchCol].equals(enemy)) {
						continue;
					}
					
					boolean canPutFlag = false;
					while(true) {
						searchRow += direction[0];
						searchCol += direction[1];
						
						if(field[searchRow][searchCol] != enemy && field[searchRow][searchCol] != turn) {
							break;
						}
						
						if(!field[searchRow][searchCol].equals(enemy)) {
							canPutFlag = true;
							break;
						}
					}
					if(canPutFlag) {
						int[] pos = {x + direction[0],y + direction[1]};
						canPutList.add(pos);
					}
				}
			}
		}
		return canPutList;
	}
	
	public static void reverse(int x,int y) {

		int directions[][] = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
		String enemy = getEnemy();
		
			for(int[]direction:directions) {
				int row = x + direction[0];
				int col = y + direction[1];
				 
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
					
					if(field[row][col].equals(wall)) {
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
			turn = "white";
		}else {
			turn = "black";
		}
	}
	public static String getEnemy() {
		if(turn.equals(black)) {
			return "white";
		}
		return "black";
	}
	
	public static boolean isFinish() {
		
	}
	

	public static void printResult(String[][] f) {
		
	}

}
