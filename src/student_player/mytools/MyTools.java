package student_player.mytools;

import java.util.ArrayList;

import hus.HusBoard;
import hus.HusBoardState;
import hus.HusMove;
import hus.HusPlayer;
import student_player.StudentPlayer;
public class MyTools {
    public static final int UTILITY_WIN = 3;
    public static final int UTILITY_DRAW = 1;
    public static final int UTILITY_LOSS = 0;

    public static int minimaxDecision(HusBoardState board_state, StudentPlayer myPlayer) {
        ArrayList<HusMove> moves = board_state.getLegalMoves();
        int[] values = new int[moves.size()];
        for (HusMove move : moves) {
            HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
            cloned_board_state.move(move);
            values[moves.indexOf(move)] = minimaxValue(5,cloned_board_state, myPlayer);
        }
        return findIndexOfMaxValue(values);
    }

    private static int findIndexOfMaxValue(int[] values) {
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
    private static int minimaxValue(int depth, HusBoardState board_state, StudentPlayer myPlayer) {
        if (board_state.gameOver()) {
            int winner = board_state.getWinner();
            if (winner == myPlayer.getPlayerID()) {
                return UTILITY_WIN;
            } else if (winner == myPlayer.getOpponentID()) {
                return UTILITY_LOSS;
            } else if (winner == HusBoardState.DRAW) {
                return UTILITY_DRAW;
            } else if (winner == HusBoardState.CANCELLED0) {
                if (myPlayer.getPlayerID() == 0) {
                    return UTILITY_LOSS;
                } else {
                    return UTILITY_WIN;
                }
            } else if (winner == HusBoardState.CANCELLED1) {
                if (myPlayer.getPlayerID() == 1) {
                    return UTILITY_LOSS;
                } else {
                    return UTILITY_WIN;
                }
            }
        }
        if (depth == 0) {
            return 2;
        }
        ArrayList<HusBoardState> successors = getSuccessors(board_state);
        if (board_state.getTurnPlayer() == myPlayer.getPlayerID()) {
            int backValue = -1;
            for( HusBoardState successor : successors) {
                int value = minimaxValue(depth-1,successor, myPlayer);
                backValue = Math.max(value, backValue);
            }
            return backValue;
        } else {
            int backValue = -1;
            for( HusBoardState successor : successors) {
                int value = minimaxValue(depth-1,successor, myPlayer);
                backValue = Math.min(value, backValue);
            }
            return backValue;
        }
    }

    private static ArrayList<HusBoardState> getSuccessors(HusBoardState board_state) {
        ArrayList<HusBoardState> successors = new ArrayList<HusBoardState>();
        ArrayList<HusMove> moves = board_state.getLegalMoves();
        for(HusMove move : moves) {
            HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
            cloned_board_state.move(move);
            successors.add(cloned_board_state);
        }
        return successors;
    }

    /**
     * My evaluation function based on
     * @param board_state
     * @param myPlayer
     * @return
     */
    private static int evaluateState(HusBoardState board_state, StudentPlayer myPlayer) {
        int[][] pits = board_state.getPits();
        int[] my_pits = pits[myPlayer.getPlayerID()];
        int[] op_pits = pits[myPlayer.getOpponentID()];
        if(board_state.getTurnPlayer() == myPlayer.getPlayerID()) {

        } else {

        }

        return 0;
    }
}
