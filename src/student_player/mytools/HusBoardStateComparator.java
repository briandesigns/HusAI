package student_player.mytools;

import java.util.Comparator;

import hus.HusBoardState;
import student_player.StudentPlayer;
public class HusBoardStateComparator implements Comparator<HusBoardState> {

    private StudentPlayer myPlayer;
    private boolean isDesc;

    public HusBoardStateComparator(StudentPlayer myPlayer, boolean isDesc) {
        this.myPlayer = myPlayer;
        this.isDesc = isDesc;
    }

    @Override
    public int compare(HusBoardState o1, HusBoardState o2) {
        if (isDesc) {
            if (AlphaBeta.evaluateState(o1,myPlayer) < AlphaBeta.evaluateState(o2, myPlayer)) {
                return 1;
            } else if (AlphaBeta.evaluateState(o1,myPlayer) > AlphaBeta.evaluateState(o2,
                    myPlayer)) {
                return -1;
            } else {
                return 0;
            }
        } else {
            if (AlphaBeta.evaluateState(o1,myPlayer) > AlphaBeta.evaluateState(o2, myPlayer)) {
                return 1;
            } else if (AlphaBeta.evaluateState(o1,myPlayer) < AlphaBeta.evaluateState(o2, myPlayer)) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
