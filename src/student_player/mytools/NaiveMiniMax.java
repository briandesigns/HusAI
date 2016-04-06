package student_player.mytools;

import java.util.ArrayList;

import hus.HusBoardState;
import hus.HusMove;
import hus.MiniMaxPlayer;

public class NaiveMiniMax {
    public static int minimaxDecision(HusBoardState board_state, MiniMaxPlayer myPlayer) {
        ArrayList<HusMove> moves = board_state.getLegalMoves();
        int[] values = new int[moves.size()];
        for (HusMove move : moves) {
            HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
            cloned_board_state.move(move);
            values[moves.indexOf(move)] = minimaxValue(3, cloned_board_state, myPlayer);
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
    private static int minimaxValue(int depth, HusBoardState board_state, MiniMaxPlayer myPlayer) {
        if (board_state.gameOver() || depth == 0) {
            return evaluateState(board_state, myPlayer);
        }

        ArrayList<HusBoardState> successors = getSuccessors(board_state);
        if (board_state.getTurnPlayer() == myPlayer.getPlayerID()) {
            int backValue = Integer.MIN_VALUE;
            for (HusBoardState successor : successors) {
                int value = minimaxValue(depth - 1, successor, myPlayer);
                backValue = Math.max(value, backValue);
            }
            return backValue;
        } else {
            int backValue = Integer.MAX_VALUE;
            for (HusBoardState successor : successors) {
                int value = minimaxValue(depth - 1, successor, myPlayer);
                backValue = Math.min(value, backValue);
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

    /**
     * My evaluation function based on number of pits with seed count above 1
     * @param board_state
     * @param myPlayer
     * @return
     */
    public static int evaluateState(HusBoardState board_state, MiniMaxPlayer myPlayer) {
        int[][] pits = board_state.getPits();
        int[] my_pits = pits[myPlayer.getPlayerID()];
        int[] op_pits = pits[myPlayer.getOpponentID()];
        int value = 0;
        for (int i = 0; i < my_pits.length; i++) {
            if (my_pits[i] > 1) {
                value += my_pits[i] * 2;
            }
        }
        for (int i = 0; i < op_pits.length; i++) {
            if (op_pits[i] > 1) {
                value -= op_pits[i] * 2;
            }
        }
        return value;
    }
}
