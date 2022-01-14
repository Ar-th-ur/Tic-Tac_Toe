package com.company;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

	private static final byte GRID_SIZE = 3;
	private static final char[][] GRID = new char[GRID_SIZE][GRID_SIZE];
	private static final byte MIN_INDEX = 1;
	private static final byte MAX_INDEX = 3;
	private static final char X_SIGN = 'X';
	private static final char O_SIGN = 'O';
	private static final char EMPTY_SIGN = ' ';
	private static char currentMove;

	public static void main(String[] args) {
		fillGrid();
		printGrid();
		startGame();
	}

	static void startGame() {
		Scanner scanner = new Scanner(System.in).useDelimiter("\\s+");

		byte moveCount = 0;
		while (gameNotFinished(moveCount)) {
			System.out.println("Enter the coordinates: ");
			String value1 = scanner.next();
			String value2 = scanner.next();

			if (isNumber(value1) && isNumber(value2)) {
				byte x = Byte.parseByte(value1);
				byte y = Byte.parseByte(value2);
				if (isCoordinate(x) && isCoordinate(y)) {
					if (isEmpty(x, y)) {
						GRID[x - 1][y - 1] = nextMove();
						moveCount++;
						printGrid();
					} else {
						System.out.println(InputError.OCCUPIED_CELL);
					}
				} else {
					System.out.println(InputError.COORDINATES_OUT_OF_GRID);
				}
			} else {
				System.out.println(InputError.NOT_NUMBER);
			}
		}

		System.out.println(getResultOfGame());
		scanner.close();
	}

	static String getResultOfGame() {
		return xWins() ? GameResult.X_WINS : oWins() ? GameResult.O_WINS : GameResult.DRAW;
	}

	static boolean gameNotFinished(byte moveCount) {
		boolean nobodyWon = !(xWins() || oWins());
		boolean cellsAreFree = moveCount < 9;
		return nobodyWon && cellsAreFree;
	}

	static boolean xWins() {
		return isDiagonal(X_SIGN) || isColumn(X_SIGN) || isLine(X_SIGN);
	}

	static boolean oWins() {
		return isDiagonal(O_SIGN) || isColumn(O_SIGN) || isLine(O_SIGN);
	}

	static boolean isDiagonal(char sign) {
		return sign == GRID[1][1]
				&& (GRID[0][0] == sign && sign == GRID[2][2] || GRID[0][2] == sign && sign == GRID[2][0]);
	}

	static boolean isLine(char sign) {
		boolean result = false;
		for (char[] line : GRID) {
			result = result || sign == line[0] && sign == line[1] && sign == line[2];
		}
		return result;
	}

	static boolean isColumn(char sign) {
		boolean result = false;
		for (int x = 0; x < GRID_SIZE; x++) {
			result = result || sign == GRID[0][x] && sign == GRID[1][x] && sign == GRID[2][x];
		}
		return result;
	}

	static char nextMove() {
		currentMove = currentMove == X_SIGN ? O_SIGN : X_SIGN;
		return currentMove;
	}

	static boolean isNumber(String value) {
		return Character.isDigit(value.charAt(0));
	}

	static boolean isCoordinate(byte value) {
		return value <= MAX_INDEX && value >= MIN_INDEX;
	}

	static boolean isEmpty(byte x, byte y) {
		return GRID[x - 1][y - 1] == EMPTY_SIGN;
	}

	static void fillGrid() {
		for (char[] line : GRID) {
			Arrays.fill(line, EMPTY_SIGN);
		}
	}

	static void printGrid() {
		System.out.println("---------");
		for (char[] line : GRID) {
			System.out.print("|");
			for (int y = 0; y < GRID_SIZE; y++) {
				System.out.print(" " + line[y]);
			}
			System.out.println(" |");
		}
		System.out.println("---------");
	}
}