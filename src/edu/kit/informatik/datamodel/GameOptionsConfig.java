package edu.kit.informatik.datamodel;

public class GameOptionsConfig {
    private String[][] gameOptionsConfigArray = {
            {"standard",    "size:int", "playerCount:int"},
            {"torus",       "size:int", "playerCount:int"}
    };

    public boolean containsGameMode(String passedGameMode) {
        for (int i = 0; i < gameOptionsConfigArray.length; i++) {
            String validGameMode = getGameModeById(i);
            if (validGameMode.equals(passedGameMode)) {
                return true;
            }
        }

        return false;
    }

    private String getGameModeById(int id) {
        return gameOptionsConfigArray[id][0];
    }
}