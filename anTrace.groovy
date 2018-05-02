import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/////////////////////////

File traceFile = new File( "trace.txt" );

def lstA = [];

traceFile.eachLine { ln -> 
	def tkrA = ln.tokenize(' ');
	// println tkrA[2];
	def tkrB = tkrA[2].tokenize('"');
	lstA << tkrB[1];
}

lstA.sort();
lstA = lstA.unique();
// assert lstA.size() == lstA.unique().size() ;

def fs = lstA.collect { new File( it ) }

// String sToken1 = '/home/ltoenjes/exp/rnd/444/groovyAllExploded/';
String sToken1 = new File('.').canonicalFile.path + '/groovyAllExploded/' ;

File targetJarFile = new File( "gg.jar" );

FileOutputStream fos     = new FileOutputStream( targetJarFile );
BufferedOutputStream bos = new BufferedOutputStream(fos);
ZipOutputStream zos      = new ZipOutputStream(bos);

fs.each { f ->
	assert f.exists();
	assert f.isFile();
	String sf = f.toString();
	String sa = sf - sToken1;
	//println sa;
	String thePath = sa ;
	File   theFile = f  ;
	
	def ze = new ZipEntry( thePath);
	zos.putNextEntry( ze );
	def bs = theFile.bytes;
	int sz = bs.size();
	zos.write( bs, 0 , sz  )
	// zos.write( bs );
	zos.closeEntry();
	
}

zos.close();

//  jar ufe gg.jar test2

// jar ufe hello-world.jar groovy.ui.GroovyMain
// println( ['jar','ufe', targetJarFile.path, 'groovy.ui.GroovyMain' ].execute().text )

// ###
//println( ['jar','ufe', targetJarFile.path, 'test2' ].execute().text )

// println( ['jar','uvf', targetJarFile.path, 'x/classes/*' ].execute().text )
// ###

println "### jar setting Main-Class";

['jar','ufe', targetJarFile.path, 'loader' ].execute().waitFor();

println "### jar adding loader script classes";

['jar','uvf', targetJarFile.path, '-C', 'LoaderScript/classes', '.' ].execute().waitFor();

// jarShebang.txt

println "### jar creating executable gg from gg.jar";

['sh','-c', ''' 

cat jarShebang.txt >  gg
cat gg.jar         >> gg
chmod +x              gg


'''.trim() ].execute().waitFor();



println "### jar done";


// jar uvf gg.jar x/classes/*


//////////////////