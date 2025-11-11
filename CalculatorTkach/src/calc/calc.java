package calc;
import java.util.Scanner;

public class calc {
    
    public static void main(String[] args) {
        System.out.println("Enter operation: ");
        System.out.println("1. Addition");
        System.out.println("2. Subtraction");
        System.out.println("3. Multiplication");
        System.out.println("4. Division");
        // Ткаченко Андрей Павлович
        
        Scanner scanner = new Scanner(System.in);
        int operation = scanner.nextInt();
        
        System.out.println("Enter first number: ");
        int x = scanner.nextInt();
        System.out.println("Enter second number: ");
        int y = scanner.nextInt();
        
        int result = 0;
        
        if (operation == 1)
            result = x + y;
        else if (operation == 2)
            result = x - y;
        else if (operation == 3)
            result = x * y;
        else if (operation == 4)
            result = x / y;
        
        System.out.println("Result = " + result);
        System.out.println("Tkach");
    }
}