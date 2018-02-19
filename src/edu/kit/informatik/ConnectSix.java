package edu.kit.informatik;

import edu.kit.informatik.controller.Controller;

public class ConnectSix {
    public static void main(String[] args) {
        // args zu gameOptions parsen?
        // parser dem controler übergeben?
        Controller controller = new Controller(args);
        if (controller.isInitialised()) // Oder hier nen  controller ohne args instanziieren, der
            controller.run();
    }
}