package org.example.model.entities;
import java.util.ArrayList;

import org.example.exceptions.BackpackWeightExceededException;
import org.example.exceptions.ItemNotFoundException;

public class Backpack {

        // Rappresento lo zaino come un arraylist di oggetti collezionabili
        // Lo zaino ha un peso dato dato dalla somma degli item

        private int maxWeight;
        private ArrayList<CollectableItem> items;

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


        public int getMaxWeight() {
                return maxWeight;
        }

        public int getTotalWeight() {
            //Gippi : return items.stream().mapToInt(CollectableItem::getWeight).sum();
            // Io :
            int totalWeight = 0;
            int i = 0;
            while (i < items.size()) {
                totalWeight += items.get(i).getWeight();
                i++;
            }
            return totalWeight;
        }


}
