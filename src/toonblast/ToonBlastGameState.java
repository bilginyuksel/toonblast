package toonblast;

import java.util.Map;

public class ToonBlastGameState {
    private final GameOver gameOver;
    private int remainingMoveCount;
    private final Map<Integer, Integer> goals;
    private int overallGoalsCount;

    public ToonBlastGameState(GameOver gameOver, int moveCount, Map<Integer, Integer> goals) {
        this.gameOver = gameOver;
        this.remainingMoveCount = moveCount;
        this.goals = goals;

        for (Map.Entry<Integer, Integer> entry : goals.entrySet()) {
            this.overallGoalsCount += entry.getValue();
        }
    }

    public void updateToonBlastState(int variantId, int blastedVariantCount) {
        var currentGoalCount = goals.get(variantId);
        goals.put(variantId, currentGoalCount - blastedVariantCount);
        overallGoalsCount -= currentGoalCount;

        if (overallGoalsCount <= 0) {
            gameOver.over(true);
        }

        if (--remainingMoveCount <= 0) {
            gameOver.over(false);
        }
    }
}
