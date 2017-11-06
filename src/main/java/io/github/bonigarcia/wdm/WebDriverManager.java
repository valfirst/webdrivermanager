/*
 * (C) Copyright 2015 Boni Garcia (http://bonigarcia.github.io/)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 */
package io.github.bonigarcia.wdm;

import java.net.URL;
import java.util.List;

/**
 * Generic manager.
 *
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.3.1
 */
public class WebDriverManager extends BrowserManager {

    private static Class<? extends BrowserManager> browserManagerClass;

    public static synchronized BrowserManager getInstance(
            Class<?> webDriverClass) {

        switch (webDriverClass.getName()) {
        case "org.openqa.selenium.chrome.ChromeDriver":
            browserManagerClass = ChromeDriverManager.class;
            break;
        case "org.openqa.selenium.opera.OperaDriver":
            browserManagerClass = OperaDriverManager.class;
            break;
        case "org.openqa.selenium.ie.InternetExplorerDriver":
            browserManagerClass = InternetExplorerDriverManager.class;
            break;
        case "org.openqa.selenium.edge.EdgeDriver":
            browserManagerClass = EdgeDriverManager.class;
            break;
        case "org.openqa.selenium.phantomjs.PhantomJSDriver":
            browserManagerClass = PhantomJsDriverManager.class;
            break;
        case "org.openqa.selenium.firefox.MarionetteDriver":
        case "org.openqa.selenium.firefox.FirefoxDriver":
            browserManagerClass = FirefoxDriverManager.class;
            break;
        default:
            browserManagerClass = VoidDriverManager.class;
            break;
        }

        try {
            log.debug("Creating instance of {}", browserManagerClass);
            instance = browserManagerClass.newInstance();
        } catch (Exception e) {
            String errMessage = "Error creating WebDriverManager";
            log.error(errMessage, e);
            throw new WebDriverManagerException(errMessage, e);
        }
        return instance;
    }

    @Override
    protected List<URL> getDrivers() throws Exception {
        return instance.getDrivers();
    }

    @Override
    protected String getExportParameter() {
        return instance.getExportParameter();
    }

    @Override
    protected String getDriverVersionKey() {
        return instance.getDriverVersionKey();
    }

    @Override
    protected String getDriverUrlKey() {
        return instance.getDriverUrlKey();
    }

    @Override
    protected List<String> getDriverName() {
        return instance.getDriverName();
    }

}
