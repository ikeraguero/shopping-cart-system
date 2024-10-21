import com.shoppingcart.product.Clothing;
import com.shoppingcart.product.Electronics;
import com.shoppingcart.product.Grocery;
import com.shoppingcart.product.Product;
import com.shoppingcart.stock.Stock;

import java.util.Scanner;

public class MenuOption {
    protected static int category = 0;
    protected static String productName = "";
    protected static double basePrice = 0.0;
    protected static String hasWarranty = "";
    protected static String isOnSale = "";
    protected static int discountPercentage = 0;
    protected static int inventory = 0;
    protected static final Scanner scanner = new Scanner(System.in);

    public MenuOption() {
    }

    public void printAddItem(){
        while(category==0 || category > 3) {
            System.out.print("\n[1] - Groceries\n");
            System.out.print("[2] - Electronics\n");
            System.out.print("[3] - Clothing\n");
            System.out.print("Enter your choice: ");
            category = Integer.parseInt(scanner.next());
            scanner.nextLine();
        }
        while(productName.isEmpty()) {
            System.out.print("Product name: ");
            productName = scanner.nextLine().toUpperCase();
        }
        if(!Stock.hasItem(productName)) {
            while (basePrice == 0.0 || basePrice < 0.0 ) {
                System.out.print("Base price: ");
                basePrice = Double.parseDouble(scanner.next());
                scanner.nextLine();
            }
            while(!isOnSale.equals("Y") && !isOnSale.equals("N")) {
                System.out.print("Is on sale? [Y/N]: ");
                isOnSale = scanner.next().toUpperCase();
            }
            if(isOnSale.equals("Y")) {
                System.out.print("Discount percentage (%): ");
                discountPercentage = Integer.parseInt(scanner.next());
                scanner.nextLine();
            }
            while(!hasWarranty.equals("Y") && !hasWarranty.equals("N") && category==2) {
                if(!Stock.hasItem(productName)) {
                    System.out.print("Has Warranty? [Y/N]: ");
                    hasWarranty = scanner.next().toUpperCase();
                }
            }
        } else {
            Product product = Stock.getFirstItem(productName);
            basePrice = product.getBasePrice();
            hasWarranty = (product instanceof Electronics && product.hasWarranty()) ? "Y" : "N";
            isOnSale = product.isOnSale() ? "Y" : "N";
            if (isOnSale.equals("Y")) {
                discountPercentage = product.getDiscountPercentage();
            }
        }
        System.out.print("Amount to add: ");
        inventory = Integer.parseInt(scanner.next());
        switch (category) {
            case 1:
                int count = 0;
                do {
                    Product grocery = new Grocery(productName, basePrice, (isOnSale.equals("Y")), discountPercentage, inventory);
                    Stock.addItemStock(grocery);
                    count++;
                } while (count < inventory);
                Stock.printStock(0);
                break;
            case 2:
                count = 0;
                do {
                    Product electronics = new Electronics(productName, basePrice, (isOnSale.equals("Y")), discountPercentage,(hasWarranty.equals("Y")), inventory);
                    Stock.addItemStock(electronics);
                    count++;
                } while (count < inventory);
                Stock.printStock(0);
                break;
            case 3:
                count = 0;
                do {
                    Product clothing = new Clothing(productName, basePrice, (isOnSale.equals("Y")), discountPercentage, inventory);
                    Stock.addItemStock(clothing);
                    count++;
                } while (count < inventory);
                Stock.printStock(0);
                break;
        }
        System.out.print("\n: ");
    }
    };
