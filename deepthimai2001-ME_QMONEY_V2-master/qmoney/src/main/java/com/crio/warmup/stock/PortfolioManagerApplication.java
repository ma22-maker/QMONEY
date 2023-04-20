
package com.crio.warmup.stock;


import com.crio.warmup.stock.dto.*;
import com.crio.warmup.stock.log.UncaughtExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.util.logging.Logger;
import com.crio.warmup.stock.portfolio.PortfolioManager;
import com.crio.warmup.stock.portfolio.PortfolioManagerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;
import java.util.UUID;
import java.util.TreeMap;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.logging.log4j.ThreadContext;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.client.RestTemplate;


public class PortfolioManagerApplication {

  //       - Read the json file provided in the argument[0], The file is available in the classpath.
  //       - Go through all of the trades in the given file,
  //       - Prepare the list of all symbols a portfolio has.
  //       - if "trades.json" has trades like
  //         [{ "symbol": "MSFT"}, { "symbol": "AAPL"}, { "symbol": "GOOGL"}]
  //     
      // Then you should return ["MSFT", "AAPL", "GOOGL"]
  //  Hints:
  //    1. Go through two functions provided - #resolveFileFromResources() and #getObjectMapper
  //       Check if they are of any help to you.
  //    2. Return the list of all symbols in the same order as provided in json.

  //  Note:
  //  1. There can be few unused imports, you will need to fix them to make the build pass.
  //  2. You can use "./gradlew build" to check if your code builds successfully.

  public static List<String> mainReadFile(String[] args) throws IOException, URISyntaxException {
     //readTradesFromJson(args[0]);
    
  
    ObjectMapper objectMapper = getObjectMapper();
    PortfolioTrade[] trades = objectMapper.readValue(resolveFileFromResources(args[0]), PortfolioTrade[].class);
   
    List<String> symbols = new ArrayList<String>();
    for (PortfolioTrade trade : trades) {
        //  trades.setSymbol(trade);
        symbols.add(trade.getSymbol());
    }
   // System.out.println(symbols);
    return symbols;
 
  //  return Collections.emptyList();


  }




  // TODO: CRIO_TASK_MODULE_CALCULATIONS
  //  Now that you have the list of PortfolioTrade and their data, calculate annualized returns
  //  for the stocks provided in the Json.
  //  Use the function you just wrote #calculateAnnualizedReturns.
  //  Return the list of AnnualizedReturns sorted by annualizedReturns in descending order.

  // Note:
  // 1. You may need to copy relevant code from #mainReadQuotes to parse the Json.
  // 2. Remember to get the latest quotes from Tiingo API.

  // TODO: CRIO_TASK_MODULE_REST_API
  //  Find out the closing price of each stock on the end_date and return the list
  //  of all symbols in ascending order by its close value on end date.

  // Note:
  // 1. You may have to register on Tiingo to get the api_token.
  // 2. Look at args parameter and the module instructions carefully.
  // 2. You can copy relevant code from #mainReadFile to parse the Json.
  // 3. Use RestTemplate#getForObject in order to call the API,
  //    and deserialize the results in List<Candle>



  private static void printJsonObject(Object object) throws IOException {
    Logger logger = Logger.getLogger(PortfolioManagerApplication.class.getCanonicalName());
    ObjectMapper mapper = new ObjectMapper();
    logger.info(mapper.writeValueAsString(object));
  }

  private static File resolveFileFromResources(String filename) throws URISyntaxException {
    return Paths.get(
        Thread.currentThread().getContextClassLoader().getResource(filename).toURI()).toFile();
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }


  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Follow the instructions provided in the task documentation and fill up the correct values for
  //  the variables provided. First value is provided for your reference.
  //  A. Put a breakpoint on the first line inside mainReadFile() which says
  //    return Collections.emptyList();
  //  B. Then Debug the test #mainReadFile provided in PortfoliomanagerApplicationTest.java
  //  following the instructions to run the test.
  //  Once you are able to run the test, perform following tasks and record the output as a
  //  String in the function below.
  //  Use this link to see how to evaluate expressions -
  //  https://code.visualstudio.com/docs/editor/debugging#_data-inspection
  //  1. evaluate the value of "args[0]" and set the value
  //     to the variable named valueOfArgument0 (This is implemented for your reference.)
  //  2. In the same window, evaluate the value of expression below and set it
  //  to resultOfResolveFilePathArgs0
  //     expression ==> resolveFileFromResources(args[0])
  //  3. In the same window, evaluate the value of expression below and set it
  //  to toStringOfObjectMapper.
  //  You might see some garbage numbers in the output. Dont worry, its expected.
  //    expression ==> getObjectMapper().toString()
  //  4. Now Go to the debug window and open stack trace. Put the name of the function you see at
  //  second place from top to variable functionNameFromTestFileInStackTrace
  //  5. In the same window, you will see the line number of the function in the stack trace window.
  //  assign the same to lineNumberFromTestFileInStackTrace
  //  Once you are done with above, just run the corresponding test and
  //  make sure its working as expected. use below command to do the same.
  //  ./gradlew test --tests PortfolioManagerApplicationTest.testDebugValues

  public static List<String> debugOutputs() {

     String valueOfArgument0 = "trades.json";
     String resultOfResolveFilePathArgs0 = "/home/crio-user/workspace/deepthimai2001-ME_QMONEY_V2/qmoney/bin/main/trades.json";
     String toStringOfObjectMapper = "com.fasterxml.jackson.databind.ObjectMapper@5542c4ed";
     String functionNameFromTestFileInStackTrace = ".mainReadFile(new String[]{filename});";
     String lineNumberFromTestFileInStackTrace = "29";


    return Arrays.asList(new String[]{valueOfArgument0, resultOfResolveFilePathArgs0,
        toStringOfObjectMapper, functionNameFromTestFileInStackTrace,
        lineNumberFromTestFileInStackTrace});
  }


  // Note:
  // Remember to confirm that you are getting same results for annualized returns as in Module 3.
  // public static List<String> mainReadQuotes(String[] args) throws IOException, URISyntaxException {
  //    List<PortfolioTrade> trade = readTradesFromJson(args[0]);
  //       if (trade.isEmpty()) {
  //     return Collections.emptyList();
  // }
     
  //   System.out.println(args[0]);
  //   System.out.println(trade.get(1).getSymbol());
  //   String token="d13004278a50fee3feb397d69468b21c2e642dd9";
  //   RestTemplate restTemplate = new RestTemplate();
  //   // List<String> responses =new ArrayList<>();
  //   TreeMap<Double,String> symbolClosingPrices = new TreeMap<>();
  //   LocalDate date = LocalDate.parse(args[1]);

  //   for (PortfolioTrade trades : trade) {

  //     String fooResourceUrl = prepareUrl( trades,date, token);
  //     TiingoCandle[] response = restTemplate.getForObject(fooResourceUrl ,TiingoCandle[].class);
  //     // symbolClosingPrices.put();
  //     // responses.add(response);
  //     System.out.println(response.length-1);
  //      symbolClosingPrices.put(response[response.length-1].getClose(),trades.getSymbol());

  //      /////sorting the array
    
  // }
  // ;
  // System.out.println(symbolClosingPrices);
  // List<String> list = new ArrayList<>(symbolClosingPrices.values());
  // // System.out.println(response);

  // return list;
  //   // return Collections.emptyList();
  // }

  public static List<String> mainReadQuotes(String[] args) throws IOException, URISyntaxException {
    List<PortfolioTrade> trades = readTradesFromJson(args[0]);
    String token = "d13004278a50fee3feb397d69468b21c2e642dd9";
    RestTemplate restTemplate = new RestTemplate();
    TreeMap<Double, String> symbolClosingPrices = new TreeMap<>();
    LocalDate date = LocalDate.parse(args[1]);

    for (PortfolioTrade trade : trades) {
        String url = prepareUrl(trade, date, token);
        TiingoCandle[] candles = restTemplate.getForObject(url, TiingoCandle[].class);
        double closePrice = candles[candles.length - 1].getClose();
        symbolClosingPrices.put(closePrice, trade.getSymbol());
    }

    List<String> sortedSymbols = new ArrayList<>(symbolClosingPrices.values());
    // System.out.println("Input trades: " + trades);
    // System.out.println("Closing prices: " + symbolClosingPrices);
    // System.out.println("Sorted symbols: " + sortedSymbols);

    return sortedSymbols;
}

  // TODO:
  //  After refactor, make sure that the tests pass by using these two commands
  //  ./gradlew test --tests PortfolioManagerApplicationTest.readTradesFromJson
  //  ./gradlew test --tests PortfolioManagerApplicationTest.mainReadFile
  public static List<PortfolioTrade> readTradesFromJson(String filename) throws IOException, URISyntaxException {
    // System.out.println(filename);
    ObjectMapper objectMapper = getObjectMapper();
    List<PortfolioTrade> trades = objectMapper.readValue(resolveFileFromResources(filename), new TypeReference<List<PortfolioTrade>>(){});
    //System.out.println(trades);
    return trades;
  }

   // PortfolioTrade[]!=List<PortfolioTrade>
    // List<PortfolioTrade> tradeList = Arrays.asList(trades);

    // System.out.println(trades.get(0).getSymbol());
    
 //   List<PortfolioTrade> trades = new ArrayList<>();
  //   for (PortfolioTrade trade : trades) {
  //       symbols.add(trade.getSymbol());
  //   }
  //  System.out.println(symbols);
  //   return symbols;

    // return Collections.emptyList();

  // TODO:
  //  Build the Url using given parameters and use this function in your code to cann the API.
  public static String prepareUrl(PortfolioTrade trade, LocalDate endDate, String token) {
   String baseUrl ="https://api.tiingo.com";
   String symbol = trade.getSymbol();

   String endpoint = String.format("/tiingo/daily/%s/prices", symbol);
   String tokenParam = "token=" + token;
   String startDateStr = trade.getPurchaseDate().toString();
   String endDateStr = endDate.toString();
   String url = String.format("%s%s?startDate=%s&endDate=%s&%s",
                               baseUrl, endpoint, startDateStr, endDateStr, tokenParam);

   System.out.println(url);

    return url;

    // return Collections.emptyList();
  }



  // TODO:
  //  Ensure all tests are passing using below command
  //  ./gradlew test --tests ModuleThreeRefactorTest
  static Double getOpeningPriceOnStartDate(List<Candle> candles) {

     return candles.get(0).getOpen();
  }


  public static Double getClosingPriceOnEndDate(List<Candle> candles) {
    double closingPrice = 0;
    for (int i = candles.size() - 1; i >= 0; i--) {
      if (candles.get(i).getClose() != null) {
        closingPrice = candles.get(i).getClose();
        break;
      }
    }
     return closingPrice;
  }

  public static String getToken() {

   String[] token =
    
    {"ba213dcac06ea722d054f3150e8988f3e398b015", "61ce1dff403ba4b2b40ba2db6d6e65cec1e6c187",
    
"b197a6dd821010c89ef8f1de157f9f785a1de2d7", "8f01a5a63850b10acdd7c2af98189529a3bcd575",
    
   "1cc10465be8655349441703e609b2e6b103b1a0a", "4cde2b644448ec2379b2147260d78783cabeda33",
    
   "953e5d1702c35f1aabd7a475fd8d256e2274d731", "dbafe4de20a19ef23df3deda52d513e373206cb0"};
    
  Random random = new Random();
    
     return token[random.nextInt(token.length)];
     }

  public static List<Candle> fetchCandles(PortfolioTrade trade, LocalDate endDate, String token) {
    RestTemplate restTemplate = new RestTemplate();
    List<Candle> allCandles = new ArrayList<>();

    
      String url = prepareUrl(trade ,endDate, token);
      TiingoCandle[] candles = restTemplate.getForObject(url, TiingoCandle[].class);
      //System.out.println(candles);
      allCandles.addAll(Arrays.asList(candles));
    return  allCandles;
     //return Collections.emptyList();
  }
 // System.out.println(allCandles);
    //System.out.println(allCandles.get(0).getOpen());
    //System.out.println(allCandles.get(allCandles.size()-1).getOpen());


  public static List<AnnualizedReturn> mainCalculateSingleReturn(String[] args) throws IOException, URISyntaxException {
   
    List<PortfolioTrade> trades = readTradesFromJson(args[0]);
    String token = getToken();
    LocalDate date = LocalDate.parse(args[1]);
    List<AnnualizedReturn> annualized_returns = new ArrayList<>();

    for (PortfolioTrade trade : trades) {

    List<Candle> candles = fetchCandles(trade,date,token);
     double initialPrice =getOpeningPriceOnStartDate( candles);
     double finalPrice =getClosingPriceOnEndDate(candles);
     AnnualizedReturn annualized_return = calculateAnnualizedReturns(date,  trade, initialPrice, finalPrice);
     annualized_returns.add(annualized_return);
     
    }
     Collections.sort(annualized_returns, (x, y) -> Double.compare(y.getAnnualizedReturn(), x.getAnnualizedReturn()));

    // return Collections.emptyList();
   System.out.println(annualized_returns);
  return annualized_returns;

  }

  // TODO: CRIO_TASK_MODULE_CALCULATIONS
  //  Return the populated list of AnnualizedReturn for all stocks.
  //  Annualized returns should be calculated in two steps:
  //   1. Calculate totalReturn = (sell_value - buy_value) / buy_value.
  //      1.1 Store the same as totalReturns
  //   2. Calculate extrapolated annualized returns by scaling the same in years span.
  //      The formula is:
  //      annualized_returns = (1 + total_returns) ^ (1 / total_num_years) - 1
  //      2.1 Store the same as annualized_returns
  //  Test the same using below specified command. The build should be successful.
  //     ./gradlew test --tests PortfolioManagerApplicationTest.testCalculateAnnualizedReturn

  public static AnnualizedReturn calculateAnnualizedReturns(LocalDate endDate, PortfolioTrade trade, Double buyPrice, Double sellPrice) {
    
    String symbol = trade.getSymbol();
    double returns = (sellPrice - buyPrice) / buyPrice;
    double totalNumYears = ChronoUnit.DAYS.between(trade.getPurchaseDate(),endDate) / 365.0;
    double annualizedReturns = Math.pow(1 + returns, 1 / totalNumYears) - 1;
    AnnualizedReturn annualizedReturn = new AnnualizedReturn(symbol, annualizedReturns, returns);
   // annualizedReturns.add(annualizedReturn);
    
    System.out.println(annualizedReturn);

    return annualizedReturn;
   // return new AnnualizedReturn("", 0.0, 0.0);
  }
























  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Once you are done with the implementation inside PortfolioManagerImpl and
  //  PortfolioManagerFactory, create PortfolioManager using PortfolioManagerFactory.
  //  Refer to the code from previous modules to get the List<PortfolioTrades> and endDate, and
  //  call the newly implemented method in PortfolioManager to calculate the annualized returns.

  // Note:
  // Remember to confirm that you are getting same results for annualized returns as in Module 3.


   private static String readFileAsString(String file) throws IOException {

    return new String(Files.readAllBytes(Paths.get(file))) ;
    }

  public static List<AnnualizedReturn> mainCalculateReturnsAfterRefactor(String[] args) throws Exception {
       String file = args[0];
       LocalDate endDate = LocalDate.parse(args[1]);
       String contents = readFileAsString(file);
       ObjectMapper objectMapper = getObjectMapper();
       List<PortfolioTrade> portfolioTrades = objectMapper.readValue(contents, new TypeReference<List<PortfolioTrade>>() {});
       PortfolioManager portfolioManager = PortfolioManagerFactory.getPortfolioManager(null);
       return portfolioManager.calculateAnnualizedReturn(portfolioTrades, endDate);
  }


  public static void main(String[] args) throws Exception {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
    ThreadContext.put("runId", UUID.randomUUID().toString());



    //printJsonObject(mainCalculateSingleReturn(args));


    printJsonObject(mainCalculateReturnsAfterRefactor(args));
  }
}

