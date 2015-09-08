package utils;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/4/2015
 * File Name:       Browser
 * Project Name:    CD15 Compiler
 * Description:     Helper class to open a systems default browser
 */


public class Browser {

    // Browses to some specific file location
    public static void browseTo(String url) {

        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                URL url_test = new Browser().getClass().getResource("index.html");

                desktop.browse(new URI(url_test.toString()));
            } catch (IOException | URISyntaxException e) {
                // TODO Auto-generated catch block
                System.out.println("Could not browse to URL: " + url);
                e.printStackTrace();
            }
        }else{
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println("Could not browse to URL: " + url);
                e.printStackTrace();
            }
        }
    }

}
