package student_player;

import hus.HusBoardState;
import hus.HusPlayer;
import hus.HusMove;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import student_player.mytools.AlphaBeta;
import student_player.mytools.TimedTask;

/**
 * Hus AI Agent using minimax with alpha-beta pruning and move ordering
 * searches up to depth 5
 */
public class StudentPlayer extends HusPlayer {

    public static final int TIME_LIMIT = 1980;

    /**
     * You must modify this constructor to return your student number.
     * This is important, because this is what the code that runs the
     * competition uses to associate you with your agent.
     * The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260381251");
    }

    /**
     * used to find out whos turn it is
     * @return either 1 or 0 depending on the game
     */
    public int getPlayerID() {
        return this.player_id;
    }

    /**
     * used to find out whos turn it is
     * @return either 1 or 0 depending on the game
     */
    public int getOpponentID() {
        return this.opponent_id;
    }

    /**
     * fetches a backup Move using evaluation function on all legal moves if taking too long
     * else, return the move found by alpha beta search
     */
    public HusMove chooseMove(HusBoardState board_state) {
        ArrayList<HusMove> moves = board_state.getLegalMoves();
        final ExecutorService service = Executors.newSingleThreadExecutor();
        AlphaBeta ab = new AlphaBeta();
        try {
            TimedTask tt = new TimedTask(board_state, this, null, ab);
            final Future<Object> f = service.submit(tt.alphaBetaCalc);
//            return moves.get((Integer) f.get(TIME_LIMIT, TimeUnit.MILLISECONDS));
            f.get(TIME_LIMIT, TimeUnit.MILLISECONDS);
            return null;
        } catch (final TimeoutException e) {
//            int[] values = new int[moves.size()];
//            for (HusMove move : moves) {
//                HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
//                cloned_board_state.move(move);
//                values[moves.indexOf(move)] = AlphaBeta.evaluateState(cloned_board_state, this);
//            }
//            System.out.println("backup move given");
//            return moves.get(AlphaBeta.findIndexOfMaxValue(values));
            int res = ab.result;
            ab.stop = true;
            return moves.get(res);

        } catch (final Exception e) {
            throw new RuntimeException(e);
        } finally {
            service.shutdownNow();
        }



//        final HusBoardState clone_board_state = (HusBoardState) board_state.clone();
//        final StudentPlayer player = this;
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                AlphaBeta.alphabetaDecision((HusBoardState) clone_board_state.clone(), player);
//            }
//        });
//        t.start();


//        return moves.get(AlphaBeta.result);
    }
}
