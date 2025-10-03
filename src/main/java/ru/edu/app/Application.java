package ru.edu.app;

public class Application {

    public void run() {
        new LogoPrinter().printLogo();
        AppContext appContext = new AppContext();
        appContext.process();
/*        UIInterface uiInterface = new UIInterface();
        uiInterface.printMainMenu();*/
    }

}
