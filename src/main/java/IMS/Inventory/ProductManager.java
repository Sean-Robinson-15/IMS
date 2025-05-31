package IMS.Inventory;
import IMS.Products.Product;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

public class ProductManager {
    private final Map<String, Product> inventory = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private final Map<String, Product> inTransit = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private final InputValidator validator = new InputValidator();

    public String addInventoryItem(String ID, String name, String quantityStr, String priceStr) {
        TreeMap<String, String> inputList = new TreeMap<>(Map.of("ID", ID, "name", name, "quantityStr", quantityStr, "priceStr", priceStr));
        String validationResult = validator.confirmInputs(inputList);
        if (!validationResult.isEmpty()) {
            return "Error: "+validationResult;
        }

        ArrayList<String> IDs = getAllIDs();

        int quantity = Integer.parseInt(quantityStr);
        double price = Double.parseDouble(priceStr);
        if (!IDs.contains(ID)) {
            inventory.put(ID, new Product(ID, name, quantity, price));
            return("Item added: " + ID + " " + name);
        }
        return "ID "+ID+" arleady exists";
    }

    public String updateItem(String ID, String name, String quantityStr, String priceStr) {
        Product product = inventory.get(ID);
        if (product != null) {

            if(name.isEmpty() && quantityStr.isEmpty() && priceStr.isEmpty()) {
                return ("All Fields empty, please enter at least one field to update.");
            }

            if(!quantityStr.isEmpty()) {
                String validationResult = validator.validateInt(quantityStr, "Quantity");
                if (!validationResult.isEmpty()) return validationResult;
                int quantity = Integer.parseInt(quantityStr);
                product.setQuantity(quantity);
            }

            if(!priceStr.isEmpty()) {
                String validationResult = validator.validateDouble(priceStr, "Price");
                if (!validationResult.isEmpty()) return validationResult;
                double price = Double.parseDouble(priceStr);
                product.setPrice(price);
            }

            if (!name.isEmpty()) {
                product.setName(name);
            }

            return ("Item Updated: " + ID + " " + name);
        }
        return ("Error: Product Code [" + ID +"] doesnt exist.");
    }

    public void updateItem(String ID, int quantity) {
        updateItem(ID, "", Integer.toString(quantity), "");
    }


    public String removeItem(String ID) {
        
        if (ID.isEmpty()) return ("Error: Product Code is empty");
        if (inventory.remove(ID) != null) {
            return ("Item Removed : " + ID);
        } else {
            return ("Error: Product Code [" + ID +"] doesnt exist.");
        }
    }

    public ArrayList<Product> getAllItems() {
        return new ArrayList<>(inventory.values());
    }
    public ArrayList<String> getAllIDs() {
        ArrayList<String> IDs = new ArrayList<>();
        for (Product p : inventory.values()) {
            IDs.add(p.getID());
        }
        return IDs;
    }

    public double getPrice(String ID){
        return inventory.get(ID).getPrice();
    }
    public String getName(String ID){
        return inventory.get(ID).getName();
    }
    public Integer getQuantity(String ID){
        try { return inventory.get(ID).getQuantity();} catch (NullPointerException e) {return 0;}
    }

    public ArrayList<Product> getInTransit() {
        return new ArrayList<>(inTransit.values());
    }

    public void addInTransitItem(Product product) {
        inTransit.put(product.getID(), product);
    }

    public void randomize(int num)  {
        String[] randomStr = {
                "Drill-X500",
                "ConveyorB72",
                "Press-6000",
                "Lathe-M140",
                "CNC-Mill303",
                "RobotArm47",
                "Extruder-T9",
                "Welder-RT55",
                "HydPress829",
                "Grinder-P45",
                "InjectMold88",
                "Compressor12",
                "TensileTest5",
                "Furnace-K700",
                "AssemblyBot3",
                "PalletWrap22",
                "StampingM67",
                "CutterV450",
                "Boiler-TX90",
                "Mixer-A230",
                "ForkliftAZ5",
                "CraneHoist8",
                "PumpSystem42",
                "MillingMC75",
                "PackagingT11"
        };
        for (int i = 0; i < num; i++) {
            String ID = String.format("BNU%03d", (int)(Math.random()*1000));
            String name = randomStr[(int)(Math.random()*randomStr.length)];
            int quantity = (int)(Math.random()*50) + 1;
            double price = (double) Math.round(Math.random()*1000)/100;
            inventory.put(ID, new Product(ID, name, quantity, price));
        }
    }

    public int getLowStockThreshold() {
        return InventoryManager.DEFAULT_LOW_STOCK_THRESHOLD;
    }


    public String receiveItem(String id, String quantityText) {
        if (id.isEmpty()) {
            return "Error: Product ID is empty";
        }

        Product inTransitProduct = inTransit.get(id);
        if (inTransitProduct == null) {
            return "Error: Product ID [" + id +"] doesnt exist in in transit.";
        }
        int inTransitQuantity = inTransitProduct.getQuantity();
        Product product = inventory.get(id);

        int receivedQuantity;


        if (quantityText.isEmpty()) {
             receivedQuantity = inTransitQuantity;
        } else {
            try {
                receivedQuantity = Integer.parseInt(quantityText);
            } catch (NumberFormatException e) {
                return "Error: Invalid quantity entered. Please try again.";
            }
        }

        if (receivedQuantity <= 0) {
            return "Error: Quantity must be greater than 0";
        }

        if (inTransitQuantity < receivedQuantity) {
            return "Error: received quantity is greater than in transit quantity. \n";
        }

        //Create Product with 0 quantity if product doesnt exist
        if (product == null) {
            addInventoryItem(id, inTransitProduct.getName(), "0",
                    Double.toString(inTransitProduct.getPrice()));
            product = inventory.get(id);
        }

        int currentQuantity = product.getQuantity();
        product.setQuantity(currentQuantity + receivedQuantity);

        if (inTransitQuantity == receivedQuantity) {
            inTransit.remove(id);
            return "Item received: " + id + " " + quantityText;
        }

        inTransitProduct.setQuantity(inTransitQuantity - receivedQuantity);
        return "Item received: " + id + " " + quantityText + " (Remaining: " + inTransitQuantity + ")";


    }
}
