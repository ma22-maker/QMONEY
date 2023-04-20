
package com.crio.warmup.stock.portfolio;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.SECONDS;

import com.crio.warmup.stock.dto.AnnualizedReturn;
import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

public class PortfolioManagerImpl implements PortfolioManager {




  private RestTemplate restTemplate;

  // Caution: Do not delete or modify the constructor, or else your build will break!
  // This is absolutely necessary for backward compatibility
  protected PortfolioManagerImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }


  //TODO: CRIO_TASK_MODULE_REFACTOR
  // 1. Now we want to convert our code into a module, so we will not call it from main anymore.
  //    Copy your code from Module#3 PortfolioManagerApplication#calculateAnnualizedReturn
  //    into #calculateAnnualizedReturn function here and ensure it follows the method signature.
  // 2. Logic to read Json file and convert them into Objects will not be required further as our
  //    clients will take care of it, going forward.

  // Note:
  // Make sure to exercise the tests inside PortfolioManagerTest using command below:
  // ./gradlew test --tests PortfolioManagerTest

  //CHECKSTYLE:OFF

  static Double getOpeningPriceOnStartDate(List<Candle> candles) {

    return candles.get(0).getOpen();
 }


 public static Double getClosingPriceOnEndDate(List<Candle> candles) {
  //  double closingPrice = 0;
  //  for (int i = candles.size() - 1; i >= 0; i--) {
  //    if (candles.get(i).getClose() != null) {
  //      closingPrice = candles.get(i).getClose();
  //      break;
  //    }
  //  }
    return candles.get(candles.size()-1).getClose();
 }


  // public  List<AnnualizedReturn> calculateAnnualizedReturns(List<PortfolioTrade> portfolioTrades,LocalDate endDate) {
    
  //   List<AnnualizedReturn>annualized_Return=new ArrayList<>();

    
  //   try {
  //     for (PortfolioTrade trade : portfolioTrades) {
  //     String symbol = trade.getSymbol();
  //     List<Candle> candles;
  //     candles = getStockQuote(symbol,trade.getPurchaseDate(),endDate);
  //     double sellPrice =getOpeningPriceOnStartDate( candles);
  //     double buyPrice =getClosingPriceOnEndDate(candles);
  //     double returns = (sellPrice - buyPrice) / buyPrice;
  //     double totalNumYears = ChronoUnit.DAYS.between(trade.getPurchaseDate(),endDate) / 365.0;
  //     double annualizedReturns = Math.pow(1 + returns, 1 / totalNumYears) - 1;
  //     AnnualizedReturn annualizedReturn = new AnnualizedReturn(symbol, annualizedReturns, returns);
  //     annualized_Return.add(annualizedReturn);
      
  //    }
  //   }
  //    catch (JsonProcessingException e) {
  //     // TODO Auto-generated catch block
  //     e.printStackTrace();
  //   }

  //   System.out.println(annualized_Return);
  //   return annualized_Return;
  //  // return new AnnualizedReturn("", 0.0, 0.0);
  // }

  @Override
  public List<AnnualizedReturn> calculateAnnualizedReturn(List<PortfolioTrade> portfolioTrades,LocalDate endDate) {
    List<AnnualizedReturn>annualized_Return=new ArrayList<>();
    List<Candle> candles = new ArrayList<>();
    
    try {
      for (PortfolioTrade trade : portfolioTrades) {
      String symbol = trade.getSymbol();
      candles = getStockQuote(symbol,trade.getPurchaseDate(),endDate);
      double sellPrice =getOpeningPriceOnStartDate(candles);
      double buyPrice =getClosingPriceOnEndDate(candles);
      double returns = (sellPrice - buyPrice) / buyPrice;
      double totalNumYears = ChronoUnit.DAYS.between(endDate, trade.getPurchaseDate()) / 365.0;
      double annualizedReturns = Math.pow(1 + returns, 1 / totalNumYears) - 1;
      System.out.println(annualizedReturns);
      AnnualizedReturn annualizedReturn = new AnnualizedReturn(symbol, annualizedReturns, returns);
      annualized_Return.add(annualizedReturn);
      
     }
     annualized_Return.sort(getComparator());
    }
     catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    System.out.println(annualized_Return);
   // Collections.sort(annualized_Return, (x, y) -> Double.compare(y.getAnnualizedReturn(), x.getAnnualizedReturn()));
 
    return annualized_Return;



  }




  private Comparator<AnnualizedReturn> getComparator() {
    return Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed();
    
  }

  //CHECKSTYLE:OFF

  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Extract the logic to call Tiingo third-party APIs to a separate function.
  //  Remember to fill out the buildUri function and use that.


  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)throws JsonProcessingException {
    
    RestTemplate restTemplate = new RestTemplate();
    //List<Candle> allCandles = new ArrayList<>();

      String url = buildUri(symbol,from,to);
      TiingoCandle[] candles = restTemplate.getForObject(url, TiingoCandle[].class);
      //System.out.println(candles);
     
    return  Arrays.asList(candles);
   // return null
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
 
  protected String buildUri(String symbol, LocalDate startDate, LocalDate endDate) {
    String endDateStr = endDate.toString();
    String startDateStr = startDate.toString();
    String token = getToken();

       String uriTemplate = String.format("https:api.tiingo.com/tiingo/daily/%s/prices?"
            + "startDate=%s&endDate=%s&token=%s",symbol,startDateStr,endDateStr,token);
      
     return  uriTemplate;

  }


}
