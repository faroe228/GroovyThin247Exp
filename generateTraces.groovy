
import static groovy.inspect.swingui.ObjectBrowser.inspect as gi;

import java.util.concurrent.TimeUnit;

def tracesDir = new File( "traces" );
tracesDir.deleteDir();
tracesDir.mkdirs();


cmdsList = [] ;

/*
cmdsList << [ 'strace', '-v', '-f', 'java', '-cp', 'groovyAllExploded', 'groovy.ui.GroovyMain'  ] ;
cmdsList << [ 'strace', '-v', '-f', 'java', '-cp', 'groovyAllExploded', 'groovy.ui.GroovyMain' , '-v' ] ;
cmdsList << [ 'strace', '-v', '-f', 'java', '-cp', 'groovyAllExploded', 'groovy.ui.GroovyMain', '-e', '10.times { println it }'  ] ;
cmdsList << [ 'strace', '-v', '-f', 'java', '-cp', 'groovyAllExploded', 'groovy.ui.GroovyMain', '-e', 'println 1'  ] ;
cmdsList << [ 'strace', '-v', '-f', 'java', '-cp', 'groovyAllExploded', 'groovy.ui.GroovyMain', 'test1.groovy'  ] ;
cmdsList << [ 'strace', '-v', '-f', 'java', '-cp', 'groovyAllExploded', 'groovy.ui.GroovyMain',  '-Dport=2222'  ,'ws.groovy'  ] ;
*/

// cmdsList << [ 'strace', '-v', '-f', 'java', '-cp', 'groovyAllExploded', 'groovy.ui.GroovyMain', 'test2.groovy'  ] ;

// cmdsList << [ 'strace', '-v', '-f', 'java', '-cp', 'groovyAllExploded', 'groovy.ui.GroovyMain', 'test1.groovy'  ] ;

cmdsList << [ 'strace', '-v', '-f', 'java', '-DbuildingRun=true'  , '-cp', 'groovyAllExploded:LoaderScript/classes', 'loader'  ] ;

//cmdsList << [ 'strace', '-v', '-f', 'java', '-cp', 'groovyAllExploded', 'test2'  ] ;

// cmdsList << [ 'strace', '-v', '-f', 'java', '-cp', 'groovyAllExploded', 'groovy.ui.GroovyMain', 'test2.groovy'  ] ;

// java -cp groovyAllExploded:x/classes test2

/*

redirectError	File	ProcessBuilder	JAVA	public	ProcessBuilder	
redirectOutput	File	ProcessBuilder	JAVA	public	ProcessBuilder	

*/

cmdsList.eachWithIndex { cmds, int idx -> 
	def pb = cmds as ProcessBuilder ;
	println pb;
	def outFile = new File( tracesDir, "${idx}.out" );
	def errFile = new File( tracesDir, "${idx}.err" );
	def inFile = new File( 'input.txt' );
	
	outFile.text = '';
	errFile.text = '';
	
	pb.redirectError( errFile );
	pb.redirectOutput( outFile );
	pb.redirectInput( inFile );
	
	def ps = pb.start();
	def psResult = ps.waitFor( 60000, TimeUnit.MILLISECONDS );
	println psResult;
	// gi pb;
}

// cat traces/*.err  |grep groovyAllExploded |grep open > trace.txt

def ps = ['bash', '-c', '''   

	cat traces/*.err  |grep groovyAllExploded |grep open > trace.txt
	
'''.trim()  ].execute();

println ps.waitFor();




/*
strace -v -f java \
  -cp groovyAllExploded groovy.ui.GroovyMain \
  -e "10.times {println it}" 2>&1    > trace.stdout.txt  \
  |grep groovyAllExploded |grep open > trace.txt
 */
