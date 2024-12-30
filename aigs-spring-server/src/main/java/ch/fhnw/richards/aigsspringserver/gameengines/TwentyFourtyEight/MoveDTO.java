package ch.fhnw.richards.aigsspringserver.gameengines.TwentyFourtyEight;


public class MoveDTO {
    private long[] row;
    private int score;

    public MoveDTO() {
        this.score = 0;
    }

    public long[] getRow() {
        return row;
    }

    public void setRow(long[] row) {
        this.row = row;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
