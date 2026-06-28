package net.abrikoos.blockout.server.goals;

import net.abrikoos.blockout.BlockoutLogger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


abstract public class ProgressBlockoutGoal extends BlockoutGoal{
    public ProgressBlockoutGoal(int id) {
        super(id);
    }
    protected Progress progress = new Progress();

    public class Progress{
        protected Map<String, List<String>> progressMap = new HashMap<>();

        public int getPlayersSize(String playerUUID) {
            return progressMap.getOrDefault(playerUUID, List.of()).size();
        }

        public void addEntry(String key, String value){
            List<String> current = progressMap.getOrDefault(key, List.of());
            if (!current.contains(value)){
                current.add(value);
                progressMap.put(key, current);
            }
            else {
                BlockoutLogger.log("Tried to add duplicate entry to progress key " + key + " with value " + value);
            }

        }

        public void removeEntry(String key, String value){

        }

        public void addCount(String key, int count){
            try {
                int current = Integer.parseInt(progressMap.getOrDefault(key, List.of("0")).getFirst());
                progressMap.put(key, List.of(String.valueOf(current + count)));
            }
            catch (Exception e){
                BlockoutLogger.log("Failed to add count to progress key " + key + " with count " + count);
            }

        }

    }

}
