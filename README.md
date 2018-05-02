# GroovyThin247Exp

```
### gg is a lighter executable groovy-all-2.4.7.jar

# gg is a lighter wrapper around groovy.ui.GroovyMain found in groovy-all-x.y.z.jar
# gg is basically a slimmed down groovy-all-x.y.z.jar with just enough files
# to run a loader class (groovy code compiled with groovyc).
# strace is used to identify which files are actually needed (java -verbose:class did not supply enough info)
# to run the the loader class (which cuts the jar bloat from about 7meg to 1meg).
# The loader class downloads a full groovy-all-x.y.z.jar one time and adds it
# to the system class loader.
# see file: loader.groovy
#   ...
#   ClassLoader.systemClassLoader.addURL( downloadedGroovyAllJarFile.toURI().toURL() );
#   this.getClass().classLoader.loadClass('groovy.ui.GroovyMain').main( args );

# e.g. ./gg -e "10.times { println it }"
# will print 10 lines of the sequence 0 to and including 9
# ./gg -h # to see help

# So far gg has worked on: 
#   OS X 10.11.6 with Java 8
#   OS X 10.7.5  with Java 7
#   Linux (various including docker containers) with Java 8

# A ready to use gg is at:
#                https://github.com/faroe228/GroovyThin247Exp/blob/master/bin/gg?raw=true
#   wget         https://github.com/faroe228/GroovyThin247Exp/blob/master/bin/gg?raw=true -O gg && chmod +x gg
#   curl -Lo gg "https://github.com/faroe228/GroovyThin247Exp/blob/master/bin/gg?raw=true"      && chmod +x gg

###
### commands to generate executable gg
###

./blast

./setup

bin/gg generateTraces.groovy

bin/gg anTrace.groovy

$ ls -gh |grep -E ' gg| groovy-all'
-rwxrwxr-x 1 ltoenjes 876K May  2 10:35 gg
-rw-rw-r-- 1 ltoenjes 876K May  2 10:35 gg.jar
-rw-rw-r-- 1 ltoenjes 6.7M May  2 10:33 groovy-all-2.4.7.jar

```
