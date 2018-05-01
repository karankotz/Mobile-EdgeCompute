package com.edge.http.cli;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ServerSocketFactory;

import com.edge.http.configuration.ServerConfigFactory;
import com.edge.http.controller.MainController;
import com.edge.http.gui.ServerGui;

public class DefaultCliServerGui implements ServerGui {
    private static final Logger LOGGER = Logger.getLogger(DefaultCliServerGui.class.getName());
    public static void main(String[] args) {
        (new DefaultCliServerGui()).init();
    }

    public void init() {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tF %1$tT  - %4$s  -  %2$s  -  %5$s%6$s%n");

        Logger rootLog = Logger.getLogger("");
        rootLog.setLevel(Level.FINE);
        rootLog.getHandlers()[0].setLevel(Level.FINE);

        ServerGui gui = new DefaultCliServerGui();
        System.out.println("");
        MainController mainController = new MainController(getServerConfigFactory(),
                ServerSocketFactory.getDefault(),
                gui);
        mainController.start();
    }

    protected ServerConfigFactory getServerConfigFactory() {
        return new DefaultServerConfigFactory();
    }

    @Override
    public void stop() {
        LOGGER.info("The server has stopped.");
    }

    @Override
    public void start() {
        LOGGER.info("The server has started.");
    }
}
