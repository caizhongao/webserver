package cza.webServer;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import cza.webServer.remotting.NettyServer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InterruptedException
    {
        /*File file=new File("D:\\JAVA\\testss\\build\\classes");
        URL url= file.toURI().toURL();
        ClassLoader classLoader=new URLClassLoader(new URL[]{url});
        Class t1=classLoader.loadClass("aa.Test1");
        Object ob=t1.newInstance();
        t1.getMethod("play").invoke(ob);*/
    	
    	NettyServer server=new NettyServer();
    	server.startServer();
    }
}
