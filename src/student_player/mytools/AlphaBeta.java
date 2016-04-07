package student_player.mytools;

import java.util.ArrayList;
import java.util.Collections;

import hus.HusBoardState;
import hus.HusMove;
import student_player.StudentPlayer;

/**
 * Logic for my alpha beta pruning algorithm
 */
public class AlphaBeta {

    public int DEPTH;
    private int result = -1;
    private boolean stop = false;

    public AlphaBeta(int depth) {
        this.DEPTH = depth;
    }

    public void stopSearch() {
        this.stop = true;
    }

    public int getResult() {
        return this.result;
    }


    /**
     * based on pseudocode in the class slides
     * Iterative deepening approach based on this wiki page
     * http://will.thimbleby.net/algorithms/doku.php?id=iterative_deepening_depth-first_search
     * @param board_state
     * @param myPlayer
     * @return index of the recommended move by the algorithm
     */
    public void alphabetaDecision(HusBoardState board_state, StudentPlayer myPlayer) {
        ArrayList<HusMove> moves = board_state.getLegalMoves();
        System.out.println(myPlayer.getName() +"-branching factor:" + moves.size());
        int[] values = new int[moves.size()];
        for (int maxDepth = 1; maxDepth < DEPTH; maxDepth++) {
            for (HusMove move : moves) {
                HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
                cloned_board_state.move(move);
                values[moves.indexOf(move)] = alphabetaValue(
                        Integer.MIN_VALUE,
                        Integer.MAX_VALUE,
                        maxDepth,
                        cloned_board_state,
                        myPlayer);
                if (stop) {
                    break;
                }
            }
            if (stop) {
                break;
            }
            this.result = findIndexOfMaxValue(values);
        }
    }

    /**
     * Linear search algorithm to find the move with the max value
     * based on pseudocode from comp202
     * @param values
     * @return index of the cell with the max value
     */
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
     * alpha beta pruning algorithm with move ordering
     * based on pseudocode from class slides and the following wikipedia pages
     * https://en.wikipedia.org/wiki/Minimax
     * https://en.wikipedia.org/wiki/Alpha-beta_pruning
     * move ordering based on answer 1 of the following stackoverflow question
     * http://stackoverflow.com/questions/9964496/alpha-beta-move-ordering
     * @param board_state
     * @param myPlayer
     * @return evaluation function value for a particular board_state
     */
    private Integer alphabetaValue(int a, int b, int depth, HusBoardState board_state,
                                      StudentPlayer myPlayer) {
        if (stop) {
            try {
                throw new Exception("");
            } catch (Exception e) {
                return null;
            }
        } else {
            if (board_state.gameOver() || depth == 0) {
                return evaluateState(board_state, myPlayer);
            }
            ArrayList<HusBoardState> successors = getSuccessors(board_state);
            if (board_state.getTurnPlayer() == myPlayer.getPlayerID()) {
                Collections.sort(successors, new HusBoardStateComparator(myPlayer, true));
                int backValue = Integer.MIN_VALUE;
                for (HusBoardState successor : successors) {
                    backValue = Math.max(backValue, alphabetaValue(a, b, depth - 1, successor, myPlayer));
                    a = Math.max(a, backValue);
                    if (a >= b) {
                        break;
                    }
                }
                return backValue;
            } else {
                Collections.sort(successors, new HusBoardStateComparator(myPlayer, false));
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
    }

    /**
     * @param board_state
     * @return return a list of successor states based on all the legal moves for a parent state
     */
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
     * Evaluation function based on the difference in seed count of pits more than 1 seed between
     * the 2 players
     * players
     * @param board_state
     * @param myPlayer
     * @return evaluation function value
     */
    public static int evaluateState(HusBoardState board_state, StudentPlayer myPlayer) {
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
