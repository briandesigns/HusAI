package student_player.mytools;

import java.util.concurrent.Callable;

import hus.HusBoardState;
import student_player.StudentPlayer;
/**
 * Created by brian on 4/5/16.
 */
public class TimedTask {
    public  HusBoardState board_state;
    public  StudentPlayer myPlayer;
    public Callable<Object> alphaBetaCalc;

    public TimedTask(final HusBoardState board_state, final StudentPlayer myPlayer) {
        this.board_state = board_state;
        this.myPlayer = myPlayer;
        this.alphaBetaCalc = new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return AlphaBeta.alphabetaDecision(board_state,myPlayer);
            }

        };

    }


}
