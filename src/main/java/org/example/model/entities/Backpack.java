package org.example.model.entities;
import java.util.ArrayList;

import org.example.exceptions.BackpackWeightExceededException;
import org.example.exceptions.ItemNotFoundException;

public class Backpack {

        // The Backpack class is represented as an ArrayList of CollectableItem objects.
        // The weight of the Backpack is determined by the sum of the weights of the items it contains.

        private final int maxWeight;
        private final ArrayList<CollectableItem> items;

        public Backpack(int maxWeight) {
            this.maxWeight = maxWeight;
            this.items = new ArrayList<>();
        }


        public void addItem(CollectableItem item) throws BackpackWeightExceededException {
            if (getTotalWeight() + item.getWeight() <= maxWeight) {
                items.add(item);
            } else {
                throw new BackpackWeightExceededException("Item too heavy to add to the backpack.");
            }
        }

        public void removeItem(int id) throws ItemNotFoundException {
            boolean itemRemoved = items.removeIf(item -> item.getID() == id);
            if (!itemRemoved) {
                throw new ItemNotFoundException("Item not found in the backpack.");
            }
        }


        // Getters

        public int getMaxWeight() {
                return maxWeight;
        }

        public int getTotalWeight() {

            int totalWeight = 0;
            int i = 0;
            while (i < items.size()) {
                totalWeight += items.get(i).getWeight();
                i++;
            }
            return totalWeight;

        }

        public boolean findItemById(int id) {
            for (CollectableItem item : items) {
                if (item.getID() == id) {
                    return true;
                }
            }
            return false;
        }

}
