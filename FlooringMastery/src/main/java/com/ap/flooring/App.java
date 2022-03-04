package com.ap.flooring;

import com.ap.flooringmastery.controller.FlooringMasteryController;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Andy Padilla
 */
public class App {
    public static void main(String[] args) throws FileNotFoundException, IOException  {
       /* UserIO myIo = new UserIOConsoleImpl();
        FlooringMasteryView myView = new FlooringMasteryView(myIo);
        FlooringMasteryDao myDao = new FlooringMasteryDaoFileImpl();
        FlooringMasteryServiceLayer myService = new FlooringMasteryServiceLayerFileImpl(myDao);
        FlooringMasteryController controller = new FlooringMasteryController(myService, myView);
        controller.run();*/
       ApplicationContext ctx = 
           new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringMasteryController controller = 
           ctx.getBean("controller", FlooringMasteryController.class);
        controller.run();
    }
}
