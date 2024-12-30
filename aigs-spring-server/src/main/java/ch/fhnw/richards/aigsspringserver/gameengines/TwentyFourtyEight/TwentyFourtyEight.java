package ch.fhnw.richards.aigsspringserver.gameengines.TwentyFourtyEight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import ch.fhnw.richards.aigsspringserver.game.Game;
import ch.fhnw.richards.aigsspringserver.gameengines.GameEngine;

// Main Game Logic of 2048
public class TwentyFourtyEight implements GameEngine {

    // Random for generating random elements in the game.
    Random random = new Random();
    long score = 0;
    private static final String KEY = "move";

    public Game newGame(Game game) {
        game.setBoard(initializeBoard());
        game.setResult(false);
        return game;
    }

    public Game move(Game game, HashMap<String, String> move) {

        String right = "right";
        String left = "left";
        String up = "up";
        String down = "down";

        try {
            this.score = game.getDifficulty();
        } catch (NullPointerException e) { // Initially, the score is null.
            this.score = 0;
        }

        long[][] oldBoard = game.getBoard();

        if (move.get(KEY).equals(right)) {
            game.setBoard(moveRight(oldBoard));
        } else if (move.get(KEY).equals(left)) {
            game.setBoard(moveLeft(oldBoard));
        } else if (move.get(KEY).equals(up)) {
            game.setBoard(moveUp(oldBoard));
        } else if (move.get(KEY).equals(down)) {
            game.setBoard(moveDown(oldBoard));
        }

        game.setDifficulty(this.score);
        
        if (checkGameOver(game.getBoard())) {
            game.setResult(true);

        } else {
            // Add a new tile after moving
            long[][] newBoard = addNewTile(game.getBoard());

            // Set the new board with added tile
            game.setBoard(newBoard);
        }
        
        return game;
    }

    // Method to initialize the board with a random tile of value 2
    long[][] initializeBoard() {
        long[][] board = new long[4][4];
        int startPosition = random.nextInt(16);
        
        int row = startPosition / 4;
        int col = startPosition % 4;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = 0;
            }
        }

        board[row][col] = 2;
        return board;
    }

    // Set a new random tile with value 2 to the board
    long[][] addNewTile(long[][] board) {
        // Find empty positions
        List<int[]> emptyPositions = new ArrayList<>();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] == 0) {
                    emptyPositions.add(new int[]{row, col});
                }
            }
        }
        
        // If no empty positions, return unchanged board
        if (emptyPositions.isEmpty()) {
            return board;
        }
        
        // Select random empty position
        int[] position = emptyPositions.get(random.nextInt(emptyPositions.size()));
        
        // Place new tile with value 2
        board[position[0]][position[1]] = 2;
        
        return board;
    }

    long[][] moveRight(long[][] board) {

        long [][] updatedBoard = new long[4][4];

        for (int i = 0; i < 4; i++) {
            MoveDTO moveDTO = consolidateToEnd(board[i]);
            updatedBoard[i] = moveDTO.getRow();
            score += moveDTO.getScore();
        }

        return updatedBoard;
    }


    long[][] moveLeft(long[][] board) {
        long [][] updatedBoard = new long[4][4];

        for (int row = 0; row < 4; row++) {
            MoveDTO moveDTO = consolidateToStart(board[row]);
            updatedBoard[row] = moveDTO.getRow();
            score += moveDTO.getScore();
        }

        return updatedBoard;
    }
    

    long[][] moveUp(long[][] board) {
        long [][] updatedBoard = new long[4][4];

        for (int col = 0; col < 4; col++) {
            long[] column = getColumn(board, col);
            MoveDTO moveDTO = consolidateToStart(column);
            setColumn(updatedBoard, col, moveDTO.getRow());
            score += moveDTO.getScore();
        }

        return updatedBoard;
    }


    long[][] moveDown(long[][] board) {
        long [][] updatedBoard = new long[4][4];

        for (int col = 0; col < 4; col++) {
            long[] column = getColumn(board, col);
            MoveDTO moveDTO = consolidateToEnd(column);
            setColumn(updatedBoard, col, moveDTO.getRow());
            score += moveDTO.getScore();
        }

        return updatedBoard;
    }

    MoveDTO consolidateToEnd(long[] row) {
        MoveDTO moveDTO = new MoveDTO();
        long[] temp = Arrays.copyOf(row, row.length);
        int localScore = 0;

        // Join tiles if they are equal and adjacent or separated by 0.
        for (int i = 3; i >= 1; i--) {
            for (int j = i - 1; j >= 0; j--) {
                if (row[i] == 0) {
                    break;
                } else if (row[i] == row[j]) {
                    temp[i] = row[i] * 2;
                    temp[j] = 0;
                    row[j] = 0;
                    localScore += temp[i];
                    break;
                } else if (row[j] != 0) {
                    break;
                }
            }
        }

        // Update the row with the new values and positions and reset the temp array.
        row = Arrays.copyOf(temp, temp.length);
        temp = new long[4];

        // Move the tiles to the end
        int count = 0;
        for (int i = 3; i >= 0; i--) {
            if (row[i] == 0) {
                count++;
            } else {
                temp[i + count] = row[i];
            }
        }

        moveDTO.setRow(temp);
        moveDTO.setScore(localScore);

        return moveDTO;
    }


    MoveDTO consolidateToStart(long[] row) {
        MoveDTO moveDTO = new MoveDTO();
        long[] temp = Arrays.copyOf(row, row.length);
        int localScore = 0;
    
        // Join tiles if they are equal and adjacent or separated by 0.
        int j;
        for (int i = 0; i <= 2; i++) {
            for (j = i + 1; j <= 3; j++) {
                if (row[i] == 0) {
                    break;
                } else if (row[i] == row[j]) {
                    temp[i] = row[i] * 2;
                    temp[j] = 0;
                    row[j] = 0;
                    localScore += temp[i];
                    break;
                } else if (row[j] != 0) {
                    break;
                }
            }
        }
    
        // Update the row with the new values and positions and reset the temp array.
        row = Arrays.copyOf(temp, temp.length);
        temp = new long[4];
    
        // Move the tiles to the start
        int count = 0;
        for (int i = 0; i <= 3; i++) {
            if (row[i] == 0) {
                count++;
            } else {
                temp[i - count] = row[i];
            }
        }
    
        moveDTO.setRow(temp);
        moveDTO.setScore(localScore);
    
        return moveDTO;
    }

    boolean checkGameOver(long[][] board) {
        boolean gameOver = true;

        // Check if any tile is zero:
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] == 0) {
                    gameOver = false;
                    break;
                }
            }
        }

        // Check if any tile can be combined horizontally:
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == board[row][col + 1]) { 
                    gameOver = false;
                    break;
                }
            }
        }

        // Check if any tile can be combined vertically:
        for (int col = 0; col < 4; col++) {
            long[] row = getColumn(board, col);
            for (int i = 0; i < 3; i++) {
                if (row[i] == row[i + 1]) {
                    gameOver = false;
                    break;
                }
            }
        }

        return gameOver;

    }

    long[] getColumn(long[][] board, int col) {
        long[] column = new long[4];
        for (int row = 0; row < 4; row++) {
            column[row] = board[row][col];
        }
        return column;
    }

    void setColumn(long[][] board, int colIndex, long[] column) {
        for (int row = 0; row < 4; row++) {
            board[row][colIndex] = column[row];
        }
    }

}
