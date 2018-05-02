
// /home/ltoenjes/exp/rnd/444b/LoaderScript
// /home/ltoenjes/exp/rnd/444b/LoaderScript/loader.groovy

import static groovy.inspect.swingui.ObjectBrowser.inspect as gi;

boolean isBuildingRun = System.properties.'buildingRun' == 'true' ;

if ( isBuildingRun ) {
	// force NullObject class load
	println( new org.codehaus.groovy.runtime.NullObject() );
    assert System.properties.'dummy' == null;
}

File homeCacheDir = new File( System.properties.'user.home', 'cache' );
if ( !homeCacheDir.exists() ) {
	homeCacheDir.mkdirs();
}

String groovyVersion = '2.4.7';
String groovyJarName = "groovy-all-${groovyVersion}.jar";
String sUrl = "http://central.maven.org/maven2/org/codehaus/groovy/groovy-all/${groovyVersion}/groovy-all-${groovyVersion}.jar";

if ( isBuildingRun ) {
	sUrl = new File( groovyJarName ).canonicalFile.toURI().toURL().toString()
}

def url = sUrl.toURI().toURL();

// File myGroovyAllJarFile = new File( 'myGroovyAll.jar' );
File downloadedGroovyAllJarFile = new File( homeCacheDir, groovyJarName );

if ( isBuildingRun ) {
	downloadedGroovyAllJarFile.delete();
}

if ( !downloadedGroovyAllJarFile.exists() ) {
	
	downloadedGroovyAllJarFile.text = '';
	url.openStream().withStream { stream ->
		downloadedGroovyAllJarFile << stream ;
	};
	
}

// println args ;

ClassLoader.systemClassLoader.addURL( downloadedGroovyAllJarFile.toURI().toURL() );

this.getClass().classLoader.loadClass('groovy.ui.GroovyMain').main( args );
