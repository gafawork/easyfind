package gafawork.easyfind.util;

import org.apache.commons.cli.*;

import java.util.Arrays;

@SuppressWarnings("java:S6548")
public class Parameters {

    private static boolean debug = false;
    private static String projectName = null;
    private static String token = null;
    private static String parallel = null;
    private static String[] texts = null;
    private static String filter = null;
    private static String[] searchBranches = null;

    private static String hostUrl = null;
    private static Options options = new Options();

    private static Parameters instance;

    private static Object mutex = new Object();

    private Parameters() {

    }


    private static Parameters getInstance() {
        Parameters result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null)
                    instance = result = new Parameters();
            }
        }
        return result;
    }

    @SuppressWarnings("java:S1192")
    public static void validateParameters(String[] args) {

        getInstance();

        options.addOption(Option.builder("d")
                .longOpt("debug")
                .hasArg(false)
                .desc("debug")
                .required(false)
                .build());

        options.addOption(Option.builder("n")
                .longOpt("projectName")
                .hasArg(true)
                .desc("project name")
                .required(false)
                .build());

        options.addOption(Option.builder("t")
                .longOpt("token")
                .hasArg(true)
                .desc("token ([REQUIRED])")
                .required(true)
                .build());

        options.addOption(Option.builder("h")
                .longOpt("hostUrl")
                .hasArg(true)
                .desc("hostUrl ([REQUIRED])")
                .required(true)
                .build());

        options.addOption(Option.builder("p")
                .longOpt("gafawork/easyfind/parallel")
                .hasArg(true)
                .desc("parallel ([REQUIRED])")
                .required(true)
                .build());

        options.addOption(Option.builder("f")
                .longOpt("filter")
                .hasArg(true)
                .desc("filter")
                .build());

        options.addOption(Option.builder("s")
                .longOpt("search")
                .hasArg(true)
                .desc("debug")
                .required(false)
                .build());

        options.addOption(Option.builder("b")
                .longOpt("searchBranches")
                .hasArg(true)
                .desc("branches ")
                .numberOfArgs(Option.UNLIMITED_VALUES)
                .required(true)
                .build());

        parseParameter(options, args);
    }

    static void parseParameterDebug(CommandLine cmd) {
        if (cmd.hasOption("d")) {
            debug = true;
            System.out.println("-d option = true");
        }
    }

    private static void parseParameterNameProject(CommandLine cmd) {
        if (cmd.hasOption("n")) {
            projectName = cmd.getOptionValue("n");
            if (debug)
                System.out.println("-n option = " + projectName);

            if (!cmd.hasOption("n")) {
                System.out.println("-n project name is not defined");
            }
        }
    }

    private static void parseParameterparallel(CommandLine cmd) {
        if (cmd.hasOption("p")) {
            parallel = cmd.getOptionValue("p");
            if (debug)
                System.out.println("-p option = " + parallel);

            if (!cmd.hasOption("p")) {
                System.out.println("-n parallel option is not defined");
            }
        }
    }

    private static void parseParameterToken(CommandLine cmd) {
        if (cmd.hasOption("t")) {
            token = cmd.getOptionValue("t");
            if (debug)
                System.out.println("-t option = " + token);

            if (!cmd.hasOption("t")) {
                System.out.println("-t token option is not defined");
            }
        }
    }

    private static void parseParameterHostUrl(CommandLine cmd) {
        if (cmd.hasOption("h")) {
            hostUrl = cmd.getOptionValue("h");
            if (debug)
                System.out.println("-h option = " + hostUrl);

            if (!cmd.hasOption("h")) {
                System.out.println("-h hostUrl option is not defined");
            }
        }
    }

    private static void parseParameterFilter(CommandLine cmd) {
        if (cmd.hasOption("f")) {
            filter = cmd.getOptionValue("f");
            if (debug)
                System.out.println("-f option = " + filter);

            if (!cmd.hasOption("f")) {
                System.out.println("-t filter option is not defined");
            }
        }
    }

    private static void parseParameterBranch(CommandLine cmd) {
        if (cmd.hasOption("b")) {
            searchBranches = cmd.getOptionValues("b");
            if (debug) {
                System.out.println("Number of branche(s):" + (searchBranches.length));
                System.out.println("Search branche(s):" + String.join(",", Arrays.asList(searchBranches)));
            }
        }
    }

    private static void parseParameterSearch(CommandLine cmd) {
        if (cmd.hasOption("s")) {
            texts = cmd.getOptionValues("s");
            if (debug) {
                System.out.println("Number of search(s):" + (texts.length));
                System.out.println("text(s):" + String.join(",", Arrays.asList(texts)));
            }
        } else {

            String msg = """
                    please specify one of the command line options: 
                    -n <arg>
                    OR 
                    -t <arg>
                    OR 
                    -p <arg>
                    OR
                    -f <arg>
                    OR
                    -s <arg>""";

            System.out.println(msg);
        }
    }

    private static void parseParameter(Options options, String[] args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);

            parseParameterDebug(cmd);
            parseParameterNameProject(cmd);
            parseParameterparallel(cmd);
            parseParameterToken(cmd);
            parseParameterHostUrl(cmd);
            parseParameterFilter(cmd);
            parseParameterBranch(cmd);
            parseParameterSearch(cmd);


        } catch (ParseException pe) {
            System.out.println("Error parsing command-line arguments!");
            System.out.println("Please, follow the isntructions below");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Search gitlab", options);
            System.exit(1);
        }

    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        Parameters.debug = debug;
    }

    public static String getProjectName() {
        return projectName;
    }

    public static void setProjectName(String projectName) {
        Parameters.projectName = projectName;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Parameters.token = token;
    }

    public static String getParallel() {
        return parallel;
    }

    public static void setParallel(String parallel) {
        Parameters.parallel = parallel;
    }

    public static String[] getTexts() {
        return texts;
    }

    public static void setTexts(String[] texts) {
        Parameters.texts = texts;
    }

    public static String getFilter() {
        return filter;
    }

    public static void setFilter(String filter) {
        Parameters.filter = filter;
    }

    public static String[] getSearchBranches() {
        return searchBranches;
    }

    public static void setSearchBranches(String[] searchBranches) {
        Parameters.searchBranches = searchBranches;
    }

    public static Options getOptions() {
        return options;
    }

    public static void setOptions(Options options) {
        Parameters.options = options;
    }

    public static String getHostUrl() {
        return hostUrl;
    }

    public static void setHostUrl(String hostUrl) {
        Parameters.hostUrl = hostUrl;
    }


}