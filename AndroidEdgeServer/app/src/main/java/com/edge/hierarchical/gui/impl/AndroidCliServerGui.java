package com.edge.hierarchical.gui.impl;

import com.edge.http.configuration.ServerConfigFactory;
import com.edge.http.cli.DefaultCliServerGui;
import com.edge.hierarchical.resource.provider.impl.AndroidServerConfigFactory;

public class AndroidCliServerGui extends DefaultCliServerGui {

    public static void main(String[] args) {
        (new AndroidCliServerGui()).init();
    }

    @Override
    protected ServerConfigFactory getServerConfigFactory() {
        return new AndroidServerConfigFactory(null);
    }
}
