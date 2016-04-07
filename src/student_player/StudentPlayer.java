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

    public static final int TIME_LIMIT = 1989;

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
     * keep computing optimal move using iterative deepening until time runs out
     */
    public HusMove chooseMove(HusBoardState board_state) {
        ArrayList<HusMove> moves = board_state.getLegalMoves();
        final ExecutorService service = Executors.newSingleThreadExecutor();
        AlphaBeta ab = new AlphaBeta(40);
        try {
            TimedTask tt = new TimedTask(board_state, this, ab);
            final Future<Object> f = service.submit(tt.alphaBetaCalc);
            f.get(TIME_LIMIT, TimeUnit.MILLISECONDS);
            return null;
        } catch (final TimeoutException e) {
            int res = ab.getResult();
            ab.stopSearch();
            return moves.get(res);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        } finally {
            service.shutdown();
        }
    }
}
