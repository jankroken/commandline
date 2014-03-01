Commandline: Users Guide
========================

What is CommandLine
-------------------

CommandLine is a Java library to parse command line arguments. Most argument parsing
libraries are modelled after the GNU getopt library dominant in the unix C environment
(Apache commons-cli is a great library based on this model). In contrast, this library
is based on a mapping from the command line arguments onto objects, by using annotations.

The reason I wrote this library is that I think using an argument to object mapping in
most cases is easier and more obvious than parsing arguments the getopt way. Also, by
using sub configurations, this approach scales a lot better. Note however, that this
approach does not work for all cases. Specifically, this library
(and object mapping of arguments in general) does not maintain order of arguments. One
example of that is the unix 'find' command, where the command line arguments makes out
a domain specific language. An example of a command is

    find . -name "*.txt" -o '(' -name "*.html" -a -amin 4 ')' -exec ls -l '{}' ';'

The functionality that is currently supported is:

* Options with none, one or multiple arguments
* Optional and mandatory options
* Multiple instances of options, without mixing the arguments specified for each occurance
* Option arguments with a delimiter (similar to the '-exec' option of 'find')
* Option groups (and multiple occurences of option groups)
* Support for arguments not connected to an option
* A short and long representation of each switch/option
* Unrecognized arguments will be caught and result in an exception
* Escaping the rest of the arguments with the GNU style "--" switch
* Single letter switches that can be concatenated in one switch/option (as in 'ls -lSr')

Functionality that is currently not supported is

* Generating a help text
* Built in validation, and automatic validation based on argument types of the annotated methods
* Argument separators other than whitespace (a.e. --color=true)
* A single argument not separated from the switch (like the GCC -O3)

Versioning and required Java version
------------------------------------

The version number of this module indicates backwards compatibility and required java version.
It is in the form major.minor.patch. The major version is increased when backwards compatibility
is broken. The minor number indicates the minimum required Java version, and the patch number
is increased when a new fully backwards compatible version is released.

At the time of writing, the current version of command line is 1.7.0, which indicates that
Java 7 is needed to use the library. When the library is rewritten for java 8, the version
will likely be 2.8.0, where 2 indicates that the interface to the library has been modified,
8 indicates that Java SE version 8 is required, and 0 indicates that it's the first 2.8 release.

Using the library
-----------------

To use the library, you must create at least one class with a no argument constructor. Arguments
are added by annotating setter methods. The following example shows a configuration class with
two arguments, '-file' (with one argument) and an optional '-debug'. These two arguments have the
short versions '-f' and '-d'.

There are two option styles available for the parser.

* OptionStyle.SIMPLE - All switches have to be standalone. All (both long and short) needs to be prepended with a
  single dash on the command line. Example: 'findalbum -v -title "Undeveloped" -artist "OhGr"'
* OptionStyle.LONG_OR_COMPACT - Long switches are prepended by two dashes. Short switches are prepended with a
  single dash, and may be concatenated into one switch. Example: 'ls -lSr --color true'

### A simple example ###

This example uses the SIMPLE OptionStyle
    
    public class Arguments () {
        private String filename;
        private boolean debug = false;
    
        @Option
        @LongSwitch("file")
        @ShortSwitch("f")
        @SingleArgument
        @Required
        public void setFilename(String filename) {
      	    this.filename = filename;
        }
      
        @Option
        @LongSwitch("debug")
        @ShortSwitch("d")
        @Toggle(true)
        public void setDebug(boolean debug) {
            this.debug = debug;
        }
      
        public String getFilename() {
            return filename;
        }
      
        public boolean getDebug() {
            return debug;
        }
    }

The following code actually scans the argument list, and creates an instance of the class
    
    public class Main {
        public final static final void main(String[] args) {
            try {
                Arguments arguments = CommandLineParser.parse(Arguments.class, args, OptionStyle.SIMPLE);
                    doSomething(arguments);
            } catch (InvalidCommandLineException clException) {
		        ...
            } catch (InvalidOptionConfigurationException configException) {
                ...
            } catch (UnrecognizedSwitchException unrecognizedSwitchException) {
                ...
            }
        }
    }

This will successfully parse the following command line
    
    java Main -file hello.txt -debug

### A more advanced example ###

Occationally, you might need to parse multiple occurences of a sequence of options.
The following example demonstrates both what the hell I'm talking about and how to
do exactly that:

    public class AlbumConfiguration {
		
        private String artist;
        private String name;
        private String year;
	
        @Option
        @LongSwitch("artist")
        @SingleArgument
        public void setArtist(String artist) {
            this.artist = artist;
        }

        @Option
        @LongSwitch("name")
        @SingleArgument
        public void setName(String name) {
            this.name = name;
        }

        @Option
        @LongSwitch("year")
        @SingleArgument
        public void setYear(String year) {
            this.year = year;
        }

        public String getArtist() {
            return artist;
        }

        public String getName() {
            return name;
        }
	
        public String getYear() {
            return year;
        }
    }


    public class MusicDatabaseConfiguration {
		
        private String database;
        private boolean verbose;
        private List<AlbumConfiguration> albums;


        @Option
        @LongSwitch("database")
        @ShortSwitch("d")
        @SingleArgument
        public void setFilename(String database) {
            this.database = database;
        }

        @Option
        @LongSwitch("verbose")
        @ShortSwitch("v")
        @Toggle(true)
        public void setVerbose(boolean verbose) {
            this.verbose = verbose;
        }

        @Option
        @LongSwitch("album")
        @ShortSwitch("a")
        @Multiple
        @SubConfiguration(AlbumConfiguration.class)
        public void setAlbums(List<AlbumConfiguration> albums) {
            this.albums = albums;
        }

        public boolean getVerbose() {
            return verbose;
        }

        public String getDatabase() {
            return database;
        }

        public List<AlbumConfiguration> getAlbums() {
            return albums;
        }
    }


    public class Main {
        public final static final void main(String[] args) {
            try {
                MusicDatabaseConfiguration dbConfig = CommandLineParser.parse(MusicDatabaseConfiguration.class, args,
                OptionStyle.LONG_OR_COMPACT);
                doSomething(arguments);
            } catch (InvalidCommandLineException clException) {
                 ...
            } catch (InvalidOptionConfigurationException configException) {
                 ...
            } catch (UnrecognizedSwitchException unrecognizedSwitchException) {
                 ...
            }
        }
    }

As you notice, this code got two option configurations. AlbumConfiguration represents an album,
while MusicDatabaseConfiguration represents information about a music database and a list of albums.
If you give the following command line:

    run.sh -database myalbums.db -verbose
    -album -artist "Access to Arasaka" -name "Void();" -year 2010
    -album -artist "Decree" -name "Fateless" -year 2011
    -album -artist "Karin Park" -name "Ashes to Gold" -year 2009
    -album -artist "The Kick" -name "Metal Heart" -year 2010

You would get an instance of MusicDatabaseConfiguration, containing the values database, verbose
and a list of AlbumConfiguration with 4 elements, each representing one of the 4 albums specified
on the command line.

### The LONG_OR_COMPACT OptionStyle ###

In the LONG_OR_COMPACT OptionStyle, arguments that are prepended with two dashes are considered regular switches,
and are interpreted as such. An argument prepended with only a single dash is parsed as a list of single letter
switches. Although there's separate annotations for short and long switches, they are treated completely alike.

    public class MyConfiguration {

        private boolean verbose;
        private boolean optimize;
        private boolean compress;
        private String name;

        @Option
        @LongSwitch("verbose")
        @ShortSwitch("v")
        @Toggle(true)
        public void setVerbose(boolean verbose) {
            this.verbose = verbose;
        }

        @Option
        @LongSwitch("optimize")
        @ShortSwitch("o")
        @Toggle(true)
        public void setOptimize(boolean optimize) {
            this.optimize = optimize;
        }

        @Option
        @LongSwitch("compress")
        @ShortSwitch("c")
        @Toggle(true)
        public void setCompress(boolean compress) {
            this.compress = compress;
        }

        @Option
        @LongSwitch("name")
        @ShortSwitch("n")
        @SingleArgument
        public void setName(String name) {
            this.name = name;
        }
    }
	
	
    public class Main {
        public final static final void main(String[] args) {
            try {
                MusicDatabaseConfiguration dbConfig = CommandLineParser.parse(MusicDatabaseConfiguration.class, args, OptionStyle.SIMPLE);
                doSomething(arguments);
            } catch (InvalidCommandLineException clException) {
		        ...
            } catch (InvalidOptionConfigurationException configException) {
                ...
            } catch (UnrecognizedSwitchException unrecognizedSwitchException) {
                ...
            }
        }
    }	

With OptionStyle.SIMPLE you run the application with arguments like

   	run.sh -name "Keyser Söze" -verbose -optimize -compress

or the shorter

   	run.sh -n "Keyser Söze" -v -o -c

with OptionStyle.LONG_OR_COMPACT, you can specify the options as
   	
    run.sh --name "Keyser Söze" --verbose --optimize --compress

or the shorter

    run.sh --n "Keyser Soze" --v --o --c

You can also use the even shorter

    run.sh -n "Keyser Söze" -v -o -c

or even

    run.sh -n "Keyser Söze" -voc

You can also use

    run.sh -vocn "Keyser Söze"

but this won't work

    run.sh -nvoc "Keyser Söze"

as the option n will be interpreted as not having any arguments, but be immediately followed by the option v

### Using argument delimiters ###

Sometimes, an option needs arguments that might be interpreted as arguments themself. The prime
example of this is the find -exec argument. To help in situations like that, it is possible to
specify that an option will consume arguments following it, until the delimiter is encountered.

    class ExecutorConfiguration {
        ...
        @Option
        @LongSwitch("verbose")
        @ShortSwitch("v")
        @Toggle(true)
        public void setVerbose(boolean verbose) {
            this.verbose = verbose;
        }

        @Option
        @LongSwitch("exec")
        @ShortSwitch("e")
        @UntilDelimiter(";")
        public void setVerbose(boolean verbose) {
            this.verbose = verbose;
        }
    }

with this specification, you can use a command line line

	run.sh -exec ls -lSr -- "*.txt" \; -verbose

and "ls","l","--","*.txt" and ";" are all considered arguments tto the -exec option,
while -verbose is interpreted as a second option. Note also that the use of the delimiter
overrides the meaning of -- (which is to prevent anything following it from being interpreted
as switches.

### Annotations ###

<TABLE>
    <TR>
        <TD>@Option</TD>
        <TD>Specifies that the annotated method represents an option</TD>
    </TR>
    <TR>
        <TD>@LongSwitch(switchname)</TD>
        <TD>Specifies the long switch used to give this option on the command line</TD>
    </TR>
    <TR>
        <TD>@ShortSwitch(switchname)</TD>
        <TD>Specifies the short switch used to give this option on the command line</TD>
    </TR>
    <TR>
        <TD>@LooseArguments</TD>
        <TD>Specifies that this setter handles arguments that are not connected to an option</TD>
    </TR>
    <TR>
        <TD>@Toggle(value)</TD>
        <TD>Specifies that this option is a toggle. The boolean argument is passed directly to the setter method</TD>
    </TR>
    <TR>
        <TD>@SingleArbgument</TD>
        <TD>Specifies that this option takes a single String argument</TD>
    </TR>
    <TR>
        <TD>@AllAvailableArguments</TD>
        <TD>Specifiews that this option takes all the argument available until the next switch or end of the argument
            list
        </TD>
    </TR>
    <TR>
        <TD>@ArgumentsUntilDelimiter(delimiter)</TD>
        <TD>Specifies that the option takes all arguments available until it encounters the delimiter or the end of the
            argument list. Any switches will be interpreted as regular options
        </TD>
    </TR>
    <TR>
        <TD>@SubConfiguration(class)</TD>
        <TD>Specifies that parsing should continue based on the specified class. When that parsing is finnished, parsing
            will resume for the main configuration
        </TD>
    </TR>
    <TR>
        <TD>@Multiple</TD>
        <TD>Specifies that the option migth occur multiple times
</TABLE>

#### Rules ####

* @Option must be specifies for all methods that should be processed
* For regular options, one of @LongSwitch and @ShortSwitch must be specified, but it's not required to specify
  both
* if @LooseArguments is specified, @ShortSwitch and @LongSwitch cannot be specified
* For switches, one of @Toggle, @SingleArgument, @AllAvailableArguments, @ArgumentsUntilDelimiter or
  @SubConfiguration must be specified
* @Multiple can be specified for all option types, including loose arguments
* @LongSwitch and @ShortSwitch must be given without leading dashes.

#### Types ####

* Toggles require the setter argument to be boolean
* Single arguments require the setter argument to be a String
* Multiple arguments require the setter argument to be a List<String>
* SubConfiguration(class) requires the setter argument to be an instance of the specified class
* LooseArguments requires the setter argument to be String
* When @Multiple is specified, the setter argument needs to be List&lt;T&gt;, where T is the type the
  setter would have had if @Multiple had not been specified

For further examples, please consult the unit tests
