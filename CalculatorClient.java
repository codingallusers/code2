import CalculatorApp.*; // Imports the stubs and helper classes generated from the IDL for Calculator interface
import org.omg.CosNaming.*; // Provides access to CORBA Naming Service (used to locate remote objects)
import org.omg.CORBA.*; // Core CORBA classes for communication and object references
import org.omg.PortableServer.*; // Supports CORBA POA model (not directly used in client but can be required for stub resolution)

public class CalculatorClient {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null); // Initialize the ORB (Object Request Broker) for CORBA communication

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService"); // Get reference to CORBA naming service
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef); // Narrow the generic reference to NamingContextExt for easier operations

            Calculator calculator = CalculatorHelper.narrow(ncRef.resolve_str("calculator")); // Resolve the object named "calculator" and narrow it to Calculator type

            double x = 10.5; // Operand 1
            double y = 2.5;  // Operand 2

            // Remote method invocations on the CORBA object (executed on the server)
            System.out.println(x + " + " + y + " = " + calculator.add(x, y)); // Addition
            System.out.println(x + " - " + y + " = " + calculator.subtract(x, y)); // Subtraction
            System.out.println(x + " * " + y + " = " + calculator.multiply(x, y)); // Multiplication
            System.out.println(x + " / " + y + " = " + calculator.divide(x, y)); // Division
        } catch (Exception e) {
            e.printStackTrace(); // Handle and print any exceptions
        }
    }
}
