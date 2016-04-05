package student_player;

import hus.HusBoardState;
import hus.HusPlayer;
import hus.HusMove;

import java.util.ArrayList;

import student_player.mytools.MyTools;

/**
 * A Hus player submitted by a student.
 */
public class StudentPlayer extends HusPlayer {

    /**
     * You must modify this constructor to return your student number.
     * This is important, because this is what the code that runs the
     * competition uses to associate you with your agent.
     * The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260381251");
    }

    public int getPlayerID() {
        return this.player_id;
    }

    public int getOpponentID() {
        return this.opponent_id;
    }

    /**
     * This is the primary method that you need to implement.
     * The ``board_state`` object contains the current state of the game,
     * which your agent can use to make decisions. See the class hus.RandomHusPlayer
     * for another example agent.
     */
    public HusMove chooseMove(HusBoardState board_state) {
        ArrayList<HusMove> moves = board_state.getLegalMoves();
        return moves.get(MyTools.minimaxDecision(board_state, this));
    }
}
