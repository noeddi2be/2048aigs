package ch.fhnw.richards.aigsspringserver.gameengines.TwentyFourtyEight;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ch.fhnw.richards.aigsspringserver.game.Game;

@SpringBootTest
public class TwentyFourtyEightTest {

    @Test
    void testMove() {
        // Arrange
        TwentyFourtyEight test2048 = new TwentyFourtyEight();
        Game game = new Game();
        game = test2048.newGame(game);
        game.setBoard(new long[][] {
                { 2, 2, 2, 4 },
                { 2, 4, 8, 16 },
                { 2, 4, 8, 16 },
                { 2, 4, 8, 16 }
        });
        HashMap<String, String> move = new HashMap<>();

        // Test move right
        move.put("move", "right");
        game = test2048.move(game, move);
        long[][] expectedRight = {
                { 2, 2, 4, 4 },
                { 2, 4, 8, 16 },
                { 2, 4, 8, 16 },
                { 2, 4, 8, 16 }
        };
        for (int y = 0; y < 4; y++) {
            assertArrayEquals(expectedRight[y], game.getBoard()[y]);
        }

        // Test move left
        game.setBoard(new long[][] {
                { 2, 2, 2, 4 },
                { 2, 4, 8, 16 },
                { 2, 4, 8, 16 },
                { 2, 4, 8, 16 }
        });
        move.put("move", "left");
        game = test2048.move(game, move);
        long[][] expectedLeft = {
                { 4, 2, 4, 2 },
                { 2, 4, 8, 16 },
                { 2, 4, 8, 16 },
                { 2, 4, 8, 16 }
        };
        for (int y = 0; y < 4; y++) {
            assertArrayEquals(expectedLeft[y], game.getBoard()[y]);
        }

        // Test move up
        game.setBoard(new long[][] {
                { 4, 2, 2, 2 },
                { 2, 4, 4, 4 },
                { 2, 8, 8, 8 },
                { 4, 16, 16, 16 }
        });
        move.put("move", "up");
        game = test2048.move(game, move);
        long[][] expectedUp = {
                { 4, 2, 2, 2 },
                { 4, 4, 4, 4 },
                { 4, 8, 8, 8 },
                { 2, 16, 16, 16 }
        };
        for (int y = 0; y < 4; y++) {
            assertArrayEquals(expectedUp[y], game.getBoard()[y]);
        }

        // Test move down
        game.setBoard(new long[][] {
                { 4, 2, 2, 2 },
                { 2, 4, 4, 4 },
                { 2, 8, 8, 8 },
                { 4, 16, 16, 16 }
        });
        move.put("move", "down");
        game = test2048.move(game, move);
        long[][] expectedDown = {
                { 2, 2, 2, 2 },
                { 4, 4, 4, 4 },
                { 4, 8, 8, 8 },
                { 4, 16, 16, 16 }
        };
        for (int y = 0; y < 4; y++) {
            assertArrayEquals(expectedDown[y], game.getBoard()[y]);
        }

        // Test game over
        game.setBoard(new long[][] {
                { 2, 4, 2, 4 },
                { 4, 2, 4, 2 },
                { 2, 4, 2, 4 },
                { 4, 2, 4, 2 }
        });

        move.put("move", "up");
        game = test2048.move(game, move);

        long[][] expectedGameOver = {
                { 2, 4, 2, 4 },
                { 4, 2, 4, 2 },
                { 2, 4, 2, 4 },
                { 4, 2, 4, 2 }
        };
        for (int y = 0; y < 4; y++) {
            assertArrayEquals(expectedGameOver[y], game.getBoard()[y]);
        }
        assertTrue(game.getResult());
    }

    @Test
    void testNewGame() {
        // Arrange
        TwentyFourtyEight test2048 = new TwentyFourtyEight();
        Game game = new Game();

        // Act
        game = test2048.newGame(game);
        long[][] board = game.getBoard();

        // Assert
        // Check board dimensions
        assertEquals(4, board.length);
        assertEquals(4, board[0].length);

        // Check that exactly one tile has value 2
        int twoCount = 0;
        int zeroCount = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 2) {
                    twoCount++;
                } else if (board[i][j] == 0) {
                    zeroCount++;
                }
            }
        }

        assertEquals(1, twoCount, "Board should have exactly one tile with value 2");
        assertEquals(15, zeroCount, "Board should have 15 empty tiles");
        assertFalse(game.getResult(), "Game result should be false");
    }

    @Test
    void testAddNewTile() {
        // Arrange
        TwentyFourtyEight test2048 = new TwentyFourtyEight();

        long[][] board = {
                { 0, 2, 2, 2 },
                { 8, 8, 8, 8 },
                { 16, 16, 16, 16 },
                { 32, 32, 32, 32 }
        };

        // Act
        long[][] result = test2048.addNewTile(board);

        // Assert
        long[][] expect = {
                { 2, 2, 2, 2 },
                { 8, 8, 8, 8 },
                { 16, 16, 16, 16 },
                { 32, 32, 32, 32 }
        };
        for (int y = 0; y < 4; y++) {
            assertArrayEquals(expect[y], result[y]);
        }
    }

    @Test
    void testMoveRight() {
        // Arrange
        TwentyFourtyEight test2048 = new TwentyFourtyEight();

        long[][] board = {
                { 2, 2, 0, 0 },
                { 2, 0, 2, 0 },
                { 0, 16, 16, 0 },
                { 8, 8, 8, 8 }
        };

        long[][] expect = {
                { 0, 0, 0, 4 },
                { 0, 0, 0, 4 },
                { 0, 0, 0, 32 },
                { 0, 0, 16, 16 }
        };

        // Act
        long[][] result = test2048.moveRight(board);

        // Assert
        for (int y = 0; y < 4; y++) {
            assertArrayEquals(expect[y], result[y]);
        }
        assertEquals(72, test2048.score);
    }

    @Test
    void testMoveLeft() {
        // Arrange
        TwentyFourtyEight test2048 = new TwentyFourtyEight();

        long[][] board = {
                { 4, 2, 2, 4 },
                { 2, 0, 2, 0 },
                { 0, 16, 16, 0 },
                { 8, 8, 8, 8 }
        };

        long[][] expect = {
                { 4, 4, 4, 0 },
                { 4, 0, 0, 0 },
                { 32, 0, 0, 0 },
                { 16, 16, 0, 0 }
        };

        // Act
        long[][] result = test2048.moveLeft(board);

        // Assert
        for (int y = 0; y < 4; y++) {
            assertArrayEquals(expect[y], result[y]);
        }
        assertEquals(72, test2048.score);
    }

    @Test
    void testMoveUp() {
        // Arrange
        TwentyFourtyEight test2048 = new TwentyFourtyEight();

        long[][] board = {
                { 4, 2, 0, 0 },
                { 2, 0, 2, 0 },
                { 2, 16, 16, 0 },
                { 4, 8, 8, 8 }
        };

        long[][] expect = {
                { 4, 2, 2, 8 },
                { 4, 16, 16, 0 },
                { 4, 8, 8, 0 },
                { 0, 0, 0, 0 }
        };

        // Act
        long[][] result = test2048.moveUp(board);

        // Assert
        for (int y = 0; y < 4; y++) {
            assertArrayEquals(expect[y], result[y]);
        }
        assertEquals(4, test2048.score);
    }

    @Test
    void testMoveDown() {
        // Arrange
        TwentyFourtyEight test2048 = new TwentyFourtyEight();

        long[][] board = {
                { 2, 2, 0, 0 },
                { 2, 16, 2, 0 },
                { 0, 16, 16, 0 },
                { 8, 8, 8, 8 }
        };

        long[][] expect = {
                { 0, 0, 0, 0 },
                { 0, 2, 2, 0 },
                { 4, 32, 16, 0 },
                { 8, 8, 8, 8 }
        };

        // Act
        long[][] result = test2048.moveDown(board);

        // Assert
        for (int y = 0; y < 4; y++) {
            assertArrayEquals(expect[y], result[y]);
        }
        assertEquals(36, test2048.score);
    }

    @Test
    void testConsolidateToEnd() {
        // Arrange
        TwentyFourtyEight test2048 = new TwentyFourtyEight();
        long[] input = { 0, 8, 2, 2 };
        long[] expected = { 0, 0, 8, 4 };

        // Act
        MoveDTO testDTO = test2048.consolidateToEnd(input);

        // Assert
        assertArrayEquals(testDTO.getRow(), expected);
        assertEquals(4, testDTO.getScore());
    }

    @Test
    void testConsolidateToStart() {
        // Arrange
        TwentyFourtyEight test2048 = new TwentyFourtyEight();
        long[] input = { 0, 8, 16, 16 };
        long[] expected = { 8, 32, 0, 0 };

        // Act
        MoveDTO testDTO = test2048.consolidateToStart(input);

        // Assert
        assertArrayEquals(testDTO.getRow(), expected);
        assertEquals(32, testDTO.getScore());
    }

    @Test
    void testGetColumnShouldReturnCorrectColumn() {
        // Arrange
        TwentyFourtyEight test2048 = new TwentyFourtyEight();

        long[][] board = new long[4][4];
        int set = 0;

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                board[y][x] = set;
                set++;
            }
        }

        // Act
        long[] column = test2048.getColumn(board, 0);

        // Assert
        long[] expected = { 0, 4, 8, 12 };
        assertArrayEquals(expected, column);
    }

    @Test
    void testSetColumn() {
        // Arrange
        TwentyFourtyEight test2048 = new TwentyFourtyEight();
        long[][] board = new long[4][4];
        long[] newColumn = { 1, 1, 1, 1 };

        // Act
        int set = 0;

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                board[y][x] = set;
            }
        }

        test2048.setColumn(board, 3, newColumn);

        // Assert
        long[] expected = { 1, 1, 1, 1 };

        assertArrayEquals(expected, test2048.getColumn(board, 3));
    }

    @Test
    void testCheckGameOver() {
        // Arrange
        TwentyFourtyEight test2048 = new TwentyFourtyEight();
        HashMap<String, String> move = new HashMap<>();
        Game game = new Game();

        game = test2048.newGame(game);
        game.setBoard(new long[][] {
                { 2, 4, 2, 4 },
                { 4, 2, 4, 2 },
                { 2, 4, 2, 4 },
                { 64, 16, 8, 8 }
        });

        // Act
        move.put("move", "left");
        game = test2048.move(game, move);
        move.put("move", "left");
        game = test2048.move(game, move);
        move.put("move", "left");
        game = test2048.move(game, move);
        move.put("move", "left");
        game = test2048.move(game, move);

        long[][] expectedGameOver = {
                { 2, 4, 2, 4 },
                { 4, 2, 4, 2 },
                { 2, 4, 2, 4 },
                { 64, 32, 4, 2 }
        };

        for (int y = 0; y < 4; y++) {
            assertArrayEquals(expectedGameOver[y], game.getBoard()[y]);
        }
        assertTrue(game.getResult());
    }

}
