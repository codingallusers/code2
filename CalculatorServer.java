import CalculatorApp.*; // Importing user-defined IDL-generated stubs for Calculator interface
import org.omg.CosNaming.*; // Provides naming service support to locate remote CORBA objects
import org.omg.CORBA.*; // Core CORBA classes: ORB, Object, Exception handling, etc.
import org.omg.PortableServer.*; // Portable Object Adapter (POA) used to register servant classes

class CalculatorImpl extends CalculatorPOA { // Implements server-side methods defined in IDL
    public double add(double x, double y) { return x + y; } // Addition
    public double subtract(double x, double y) { return x - y; } // Subtraction
    public double multiply(double x, double y) { return x * y; } // Multiplication
    public double divide(double x, double y) { // Division with basic validation
        if (y == 0) throw new org.omg.CORBA.BAD_PARAM("Cannot divide by zero"); // CORBA exception if divide by zero
        return x / y;
    }
}

public class CalculatorServer {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null); // Initialize the ORB (Object Request Broker), which enables CORBA communication
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA")); // Get reference to RootPOA (Portable Object Adapter)
            rootpoa.the_POAManager().activate(); // Activate the POA manager to start processing requests

            CalculatorImpl calcImpl = new CalculatorImpl(); // Create an instance of the servant (implementation class)
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(calcImpl); // Register servant with ORB and get CORBA object reference

            Calculator href = CalculatorHelper.narrow(ref); // Narrow the CORBA object reference to Calculator type
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService"); // Get reference to the Naming Service
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef); // Narrow the naming context reference for name-based operations

            NameComponent path[] = ncRef.to_name("calculator"); // Create a name path "calculator" for the remote object
            ncRef.rebind(path, href); // Bind the name to the object reference in the naming service

            System.out.println("CalculatorServer ready and waiting..."); // Server status output
            orb.run(); // Keep server running to handle client requests
        } catch (Exception e) {
            e.printStackTrace(); // Print any exception that occurs
        }
    }
}
