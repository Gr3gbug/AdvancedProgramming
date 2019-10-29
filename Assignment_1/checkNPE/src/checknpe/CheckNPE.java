package checknpe;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Scanner;

/**
 *
 * @author gregbug
 */
public class CheckNPE {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // create a scanner so that we can read the command-line input
        Scanner scanner = new Scanner(System.in);

        // Ask to user to insert a java class (i.e. java.lang.String) 
        System.out.print("Enter java class name: ");

        // Get their input as a String
        String s = scanner.next();

        // Retrieve the Class object associated with the inserted class
        Class passedClass = Class.forName(s);

        // I'm going to create an istance of that class, which will serve to invoke its method
        Object obj = passedClass.newInstance();

        /*
        Retrieve all methods information --> START <--
         */
        System.out.println("*** M E T H O D S ***");
        for (Method m : passedClass.getMethods()) {
            System.out.print("  --> " + m.getName() + "[");

            // trovo la lista dei costruttori per ogni metodo
            Type[] typeParameter = m.getParameterTypes();
            Object[] typeArgument = new Object[typeParameter.length];
            for (int i = 0; i < typeParameter.length; i++) {
                System.out.print(typeParameter[i].getTypeName() + (i != typeParameter.length - 1 ? ", " : ""));

                // creates the parameters array
                if (typeParameter[i].getTypeName().equals("int") || typeParameter[i].getTypeName().equals("float") || typeParameter[i].getTypeName().equals("double") || typeParameter[i].getTypeName().equals("long")) {
                    typeArgument[i] = 0;
                } else if (typeParameter[i].getTypeName().equals("boolean")) {
                    typeArgument[i] = false;
                } else if (typeParameter[i].getTypeName().equals("char")) {
                    typeArgument[i] = '\u0000';
                } else {
                    typeArgument[i] = null;
                }
            }

            System.out.print("]");

            if (typeParameter.length > 0) {
                try {
                    /*
                    Invokes the underlying method represented by this object, 
                    on the specified object with the specified parameters.
                     */
                    m.invoke(obj, typeArgument);
                } catch (InvocationTargetException npe) {
                    String ANSI_RED = "\u001B[31m";
                    String ANSI_RESET = "\u001B[0m";
                    /*
                     * Because the reflection layer wraps any expection in 'InvocationTargetException', 
                     * through the following 'if' structure i'm going to check:
                        - The cause of the exception trough getCause()
                        - The Class object that represents the runtime class of this object
                        - And if the returned class of the cause is the same of the NullPointerException, then i'll obtain an NPE
                     */
                    if (npe.getCause().getClass().equals(NullPointerException.class)) {
                        System.out.print(ANSI_RED + "  --> NPE Found! <--" + ANSI_RESET);
                    }
                }
            }

            System.out.println();
        }
        /*
        Retrieve all methods information --> END <--
         */

        /*
         Retrieve all constructor information --> START <--
         */
        System.out.println("*** C O N S T R U C T O R S ***");
        for (Constructor c : passedClass.getConstructors()) {
            System.out.print("  --> " + c.getName() + "[");

            // trovo la lista dei parametri di ogni costruttore
            Type[] typeParameter = c.getParameterTypes();
            Object[] typeArgument = new Object[typeParameter.length];
            for (int i = 0; i < typeParameter.length; i++) {
                System.out.print(typeParameter[i].getTypeName() + "");

                // creates the parameters array
                if (typeParameter[i].getTypeName().equals("int") || typeParameter[i].getTypeName().equals("float") || typeParameter[i].getTypeName().equals("double") || typeParameter[i].getTypeName().equals("long")) {
                    typeArgument[i] = 0;
                } else if (typeParameter[i].getTypeName().equals("boolean")) {
                    typeArgument[i] = false;
                } else if (typeParameter[i].getTypeName().equals("char")) {
                    typeArgument[i] = '\u0000';
                } else {
                    typeArgument[i] = null;
                }

            }
            System.out.print("]");

            if (typeParameter.length > 0) {
                /*
                    * Uses the constructor represented by this object to
                    * create and initialize a new instance of the constructor's
                    * declaring class, with the specified initialization parameters.
                    */
                try {
                    c.newInstance(typeArgument);
                } catch (InvocationTargetException npe) {
                    String ANSI_RED = "\u001B[31m";
                    String ANSI_RESET = "\u001B[0m";
                    /*
                     * Because the reflection layer wraps any expection in 'InvocationTargetException', 
                     * through the following 'if' structure i'm going to check:
                        - The cause of the exception trough getCause()
                        - The Class object that represents the runtime class of this object
                        - And if the returned class of the cause is the same of the NullPointerException, then i'll obtain an NPE
                     */
                    if (npe.getCause().getClass().equals(NullPointerException.class)) {
                        System.out.print(ANSI_RED + "--> NPE Found! <--" + ANSI_RESET);
                    }
                }
            }

            System.out.println();

        }
        /*
         Retrieve all constructor information --> START <--
         */
    }

}
