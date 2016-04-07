package student_player.mytools;

import java.util.concurrent.Callable;

import hus.HusBoardState;
import hus.MiniMaxPlayer;
import student_player.StudentPlayer;

/**
 * A thread wrapper that wraps my alpha-beta search routine
 * this time limit solution is inspired by answer 2 of the following stackOverFlow question
 * http://stackoverflow.com/questions/4252187/how-to-stop-execution-after-a-certain-time-in-java
 */
public class TimedTask {

    public Callable<Object> alphaBetaCalc;
    public Callable<Object> minimaxCalc;

    /**
     * a callable that will run the alpha beta routine
     * @param board_state
     * @param myPlayer
     */
    public TimedTask(final HusBoardState board_state, final StudentPlayer myPlayer, final
                     MiniMaxPlayer miPlayer) {
        this.alphaBetaCalc = new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return AlphaBeta.alphabetaDecision(board_state, myPlayer);
            }

        };
        this.minimaxCalc = new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return NaiveMiniMax.minimaxDecision(board_state, miPlayer);
            }

        };
    }
}
