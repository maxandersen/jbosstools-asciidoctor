package org.jboss.tools.asciidoctor.ui;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.jruby.Ruby;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.jboss.tools.asciidoctor.ui"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		setupRubyGems(context.getBundle());
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	private void setupRubyGems(Bundle b) {
        // First, RubyGems assumes gems are discoverable using file system
        // searching, which doesn't map well to OSGi containers.
        
        // This explodes the entries we know we want on the file system into
        // an OSGi managed path on the host operating system, which is returned
        // as path.
        String path = explodeEntries("gems",
                "/lib/asciidoctor-java-integration-0.1.2.jar!cache",
                "/lib/asciidoctor-java-integration-0.1.2.jar!gems",
                "/lib/asciidoctor-java-integration-0.1.2.jar!specifications",
                "/lib/asciidoctor-java-integration-0.1.2.jar!bin"
                );
        
        // Next problem is that we need to tell rubygems to search this path.
        // If I just grab a global Ruby runtime and set the environment via ENV,
        // that works for _that_ Ruby, but asciidoctor is creating its own, and
        // won't see this change.
        Ruby ruby = Ruby.getGlobalRuntime();
        ruby.evalScriptlet(String.format("ENV['GEM_PATH']=\"%s\"", path));
        ruby.evalScriptlet("puts require('asciidoctor')");

        // We don't have access to that Ruby runtime, so we'll have to do it via the JVM's
        // environment.
        System.out.println("Gems at: " + path);
        String gemPath = System.getenv().get("GEM_PATH");
        if (gemPath == null || gemPath.isEmpty()) {
            gemPath = path; 
        } else {
            gemPath += File.separator + path;
        }

        // Java doesn't want us to update System.getenv, so we have
        // to get tricky
        forceEnv("GEM_PATH", gemPath);
	}

    private static void forceEnv(String envVarName, String envVarValue) {
        Map<String, String> systemEnv = System.getenv();
        // We don't want the UnmodifiableMap, we want the underlying (modifiable) map
        Class<?> clazz = systemEnv.getClass();
        Field m;
        try {
            m = clazz.getDeclaredField("m");
            // You'll run into problems if you have a security manager in place.
            m.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<String, String> modifiableEnv = (Map<String, String>)m.get(systemEnv);
            modifiableEnv.put(envVarName, envVarValue);
        } catch (Exception e) {
            // Just do nothing if we failed
            e.printStackTrace();
        }
    }

    private static String explodeEntries(String home, String... paths) {
        Bundle b = FrameworkUtil.getBundle(AsciidoctorView.class);
        File homeDir = b.getDataFile(home);
        if (!homeDir.exists()) {
            homeDir.mkdir();
        }
        // For performance reasons, you'll probably want to store some sort of token in
        // the data dir to indicate that you've already extracted the files, and don't
        // need to read the jar again.  As written, we'll scan the asciidoctor jar every
        // time the bundle is started.
        for(String path : paths) {
            int delimiterIndex = path.indexOf('!');
            if(delimiterIndex >= 0) {
                String jarLocation = path.substring(0, delimiterIndex);
                String entryName = path.substring(delimiterIndex+1);
                System.out.println("Jar: " + jarLocation + "; Entry: " + entryName);
                URL url = b.getEntry(jarLocation);
                JarInputStream jar = null;
                try {
                    jar = new JarInputStream(url.openStream());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (jar != null) {
                    saveEntriesFromJar(jar, entryName, homeDir);
                }
            }
        }
        return homeDir.getAbsolutePath();
    }
    
    private static void saveEntriesFromJar(JarInputStream jar, String entryName, File destination) {
        JarEntry entry = null;
        try {
            while ((entry = jar.getNextJarEntry()) != null) {
                if (entry.getName().startsWith(entryName)) {
                    if (entry.isDirectory()) {
                        File dir  = new File(destination, entry.getName());
                        if (!dir.exists()) {
                            System.out.println("mkdir " + dir.getAbsolutePath());
                            dir.mkdir();
                        }
                    } else {
                        File file = new File(destination, entry.getName());
                        if (file.createNewFile()) {
                            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                            int bufferSize = 1024;
                            byte[] buffer = new byte[bufferSize];
                            long bytesRead = 0;
                            long bytesToRead = entry.getSize();
                            while(bytesRead < bytesToRead) {
                                long bytesLeft = bytesToRead - bytesRead;
                                int readSize = bufferSize;
                                if (bytesLeft < bufferSize) {
                                    readSize = (int) bytesLeft; // less than bufferSize, so must fit in an int
                                }
                                readSize = jar.read(buffer, 0, readSize);
                                bytesRead += readSize;
                                os.write(buffer, 0, readSize);
                            }
                            os.close();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
