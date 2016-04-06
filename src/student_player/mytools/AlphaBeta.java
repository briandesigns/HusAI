package student_player.mytools;

import java.util.ArrayList;
import java.util.Collections;
import hus.HusBoardState;
import hus.HusMove;
import student_player.StudentPlayer;

public class AlphaBeta {
    public static final int DEPTH = 6;
    public static int alphabetaDecision(HusBoardState board_state, StudentPlayer myPlayer) {

        ArrayList<HusMove> moves = board_state.getLegalMoves();
        int[] values = new int[moves.size()];
        for (HusMove move : moves) {
            HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
            cloned_board_state.move(move);
            values[moves.indexOf(move)] = alphabetaValue(
                    Integer.MIN_VALUE,
                    Integer.MAX_VALUE,
                    DEPTH,
                    cloned_board_state,
                    myPlayer);
        }
        return findIndexOfMaxValue(values);
    }

    public static int findIndexOfMaxValue(int[] values) {
        int maxIndex = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] > values[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    /**
     * MinimaxValue algorithm
     * using a combination of pseudocode in class slides and wikipedia page and my own tweaks
     * https://en.wikipedia.org/wiki/Minimax
     * @param board_state
     * @param myPlayer
     * @return
     */
    private static int alphabetaValue(int a, int b, int depth, HusBoardState board_state,
                                      StudentPlayer myPlayer) {
        if (board_state.gameOver() || depth == 0) {
            return evaluateState(board_state, myPlayer);
        }
        ArrayList<HusBoardState> successors = getSuccessors(board_state);
        if (board_state.getTurnPlayer() == myPlayer.getPlayerID()) {
//            ArrayList<HusBoardState> successors = (ArrayList<HusBoardState>)successorsList.clone();
            Collections.sort(successors,new HusBoardStateComparator(myPlayer,true));
            int backValue = Integer.MIN_VALUE;
            for (HusBoardState successor : successors) {
                backValue = Math.max(backValue,alphabetaValue(a,b,depth - 1, successor, myPlayer));
                a = Math.max(a, backValue);
                if (a >= b) {
                    break;
                }
            }
            return backValue;
        } else {
//            ArrayList<HusBoardState>  successors = (ArrayList<HusBoardState>)successorsList.clone();
            Collections.sort(successors,new HusBoardStateComparator(myPlayer,false));
            int backValue = Integer.MAX_VALUE;
            for (HusBoardState successor : successors) {
                backValue = Math.min(backValue, alphabetaValue(a, b, depth - 1, successor, myPlayer));
                b = Math.min(b, backValue);
                if (a >= b) {
                    break;
                }
            }
            return backValue;
        }
    }

    private static ArrayList<HusBoardState> getSuccessors(HusBoardState board_state) {
        ArrayList<HusBoardState> successors = new ArrayList<HusBoardState>();
        ArrayList<HusMove> moves = board_state.getLegalMoves();
        for (HusMove move : moves) {
            HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
            cloned_board_state.move(move);
            successors.add(cloned_board_state);
        }
        return successors;
    }

    public static int evaluateState(HusBoardState board_state, StudentPlayer myPlayer) {
        int[][] pits = board_state.getPits();
        int[] my_pits = pits[myPlayer.getPlayerID()];
        int[] op_pits = pits[myPlayer.getOpponentID()];
        int value = 0;
        for (int i = 0; i < my_pits.length; i++) {
            if (my_pits[i] > 1) {
                value += my_pits[i]*2;
            }
        }
        for (int i = 0; i < op_pits.length; i++) {
            if (op_pits[i] > 1) {
                value -= op_pits[i]*2;
            }
        }
        return value;
    }



}
