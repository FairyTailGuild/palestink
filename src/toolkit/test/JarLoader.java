package toolkit.test;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

public class JarLoader extends URLClassLoader {

        public JarLoader(URL[] urls) {
                super(urls);
        }

        public JarLoader(URL[] urls, ClassLoader parent) {
                super(urls, parent);
        }

        public JarLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
                super(urls, parent, factory);
        }

        public void addJar(String jarFilePath) throws Exception {
                URL url = new URL(jarFilePath);
                super.addURL(url);
        }
}
