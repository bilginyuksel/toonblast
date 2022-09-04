package toonblast;

import java.util.Map;

// TODO: toonblast game state should be constructed in ToonBlast class
// because every game will have a new state and moves or the elements
// could be initiated with some config
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
        if (!goals.containsKey(variantId)) {
            System.out.println("Blasted variant doesn't exist");
            return;
        }

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

    public int getRemainingMoveCount() {
        return remainingMoveCount;
    }

    public Map<Integer, Integer> getGoals() {
        return goals;
    }
}
