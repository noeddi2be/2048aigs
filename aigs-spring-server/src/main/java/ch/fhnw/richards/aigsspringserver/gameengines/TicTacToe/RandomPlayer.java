package ch.fhnw.richards.aigsspringserver.gameengines.TicTacToe;

public class RandomPlayer implements tttAi {

	@Override
	public void makeMove(long[][] board) {
		boolean found = false;
		while (!found) {
			int row = (int) (Math.random() * 3);
			int col = (int) (Math.random() * 3);
			if (board[row][col] == 0) {
				board[row][col] = -1;
				found = true;
			}
		}
	}
	
}
