package hus;

import java.util.ArrayList;

import student_player.mytools.AlphaBeta;

import hus.HusBoardState;
import hus.HusPlayer;
import hus.HusMove;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import student_player.mytools.AlphaBeta;
import student_player.mytools.NaiveMiniMax;
import student_player.mytools.TimedTask;

/**
 * A Hus player submitted by a student.
 */
public class MiniMaxPlayer extends HusPlayer {
    Random rand = new Random();


    /**
     * You must modify this constructor to return your student number.
     * This is important, because this is what the code that runs the
     * competition uses to associate you with your agent.
     * The constructor should do nothing else.
     */
    public MiniMaxPlayer() {
        super("minimax");
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
        return moves.get(NaiveMiniMax.minimaxDecision(board_state, this));


//         Pick a random move from the set of legal moves.
//        ArrayList<HusMove> moves = board_state.getLegalMoves();
//        HusMove move = moves.get(rand.nextInt(moves.size()));
//        return move;


//        ArrayList<HusMove> moves = board_state.getLegalMoves();
//        final ExecutorService service = Executors.newSingleThreadExecutor();
//        try {
//            TimedTask tt = new TimedTask(board_state, null, this);
//            final Future<Object> f = service.submit(tt.minimaxCalc);
//            return moves.get((Integer) f.get(1980, TimeUnit.MILLISECONDS));
//        } catch (final TimeoutException e) {
//            int[] values = new int[moves.size()];
//            for (HusMove move : moves) {
//                HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
//                cloned_board_state.move(move);
//                values[moves.indexOf(move)] = NaiveMiniMax.evaluateState(cloned_board_state, this);
//            }
//            System.out.println("backup move given");
//            return moves.get(NaiveMiniMax.findIndexOfMaxValue(values));
//        } catch (final Exception e) {
//            throw new RuntimeException(e);
//        } finally {
//            service.shutdown();
//        }
    }
}
