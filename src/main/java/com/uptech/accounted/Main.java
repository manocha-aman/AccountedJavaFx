package com.uptech.accounted;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.HijrahChronology;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.uptech.accounted.config.StageManager;
import com.uptech.accounted.view.FxmlView;

import javafx.application.Application;
import javafx.stage.Stage;

@SpringBootApplication
public class Main extends Application {

  protected ConfigurableApplicationContext springContext;
  protected StageManager stageManager;

  public static void main(final String[] args)
      throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
//     injectCustomHijriVariant(getYearMonthsMap(), 0);
    Application.launch(args);
  }

  @Override
  public void init() throws Exception {
    springContext = springBootApplicationContext();
  }

  @Override
  public void start(Stage stage) throws Exception {
    stageManager = springContext.getBean(StageManager.class, stage);
    displayInitialScene();
  }

  @Override
  public void stop() throws Exception {
    springContext.close();
  }

  protected void displayInitialScene() {
    stageManager.switchScene(FxmlView.LOGIN);
  }

  private ConfigurableApplicationContext springBootApplicationContext()
      throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
    String[] args = getParameters().getRaw().stream().toArray(String[]::new);

    return builder.run(args);
  }

  private static Map<Integer, int[]> getYearMonthsMap() {
    Map<Integer, int[]> yearMonthsMap = new HashMap<Integer, int[]>();
    for (int i = 1; i < 2000; i++) {
      if (isIslamicLeapYear(i))
        yearMonthsMap.put(new Integer(i), new int[] { 30, 29, 30, 29, 30, 29, 30, 29, 30, 29, 30, 30 });
      else
        yearMonthsMap.put(new Integer(i), new int[] { 30, 29, 30, 29, 30, 29, 30, 29, 30, 29, 30, 29 });
    }
    return yearMonthsMap;
  }

  private static boolean isIslamicLeapYear(int i) {
    int[] remaindersOfLeapYear = { 2, 5, 8, 10, 13, 16, 19, 21, 24, 27, 29 };
    return Arrays.stream(remaindersOfLeapYear).anyMatch(num -> {
      return i % 30 == num;
    });
  }

  public static void injectCustomHijriVariant(Map<Integer, int[]> yearMonthsMap, long isoStartDate)
      throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    int minYear = Integer.MAX_VALUE;
    int maxYear = Integer.MIN_VALUE;

    for (int year : yearMonthsMap.keySet()) {
      maxYear = Math.max(maxYear, year);
      minYear = Math.min(minYear, year);
    }

    int isoStart = (int) LocalDateTime.ofInstant(Instant.ofEpochMilli(isoStartDate), ZoneId.systemDefault())
        .toLocalDate().toEpochDay();

    Field initCompleteField = HijrahChronology.class.getDeclaredField("initComplete");
    initCompleteField.setAccessible(true);
    initCompleteField.set(HijrahChronology.INSTANCE, true);

    Field hijrahStartEpochMonthField = HijrahChronology.class.getDeclaredField("hijrahStartEpochMonth");
    hijrahStartEpochMonthField.setAccessible(true);
    hijrahStartEpochMonthField.set(HijrahChronology.INSTANCE, minYear * 12);

    Field minEpochDayField = HijrahChronology.class.getDeclaredField("minEpochDay");
    minEpochDayField.setAccessible(true);
    minEpochDayField.set(HijrahChronology.INSTANCE, isoStart);

    Method createEpochMonthsMethod = HijrahChronology.class.getDeclaredMethod("createEpochMonths", int.class, int.class,
        int.class, Map.class);
    createEpochMonthsMethod.setAccessible(true);
    int[] hijrahEpochMonthStartDays = (int[]) createEpochMonthsMethod.invoke(HijrahChronology.INSTANCE, isoStart,
        minYear, maxYear, yearMonthsMap);

    Field hijrahEpochMonthStartDaysField = HijrahChronology.class.getDeclaredField("hijrahEpochMonthStartDays");
    hijrahEpochMonthStartDaysField.setAccessible(true);
    hijrahEpochMonthStartDaysField.set(HijrahChronology.INSTANCE, hijrahEpochMonthStartDays);

    Field maxEpochDayField = HijrahChronology.class.getDeclaredField("maxEpochDay");
    maxEpochDayField.setAccessible(true);
    maxEpochDayField.set(HijrahChronology.INSTANCE, hijrahEpochMonthStartDays[hijrahEpochMonthStartDays.length - 1]);

    Method getYearLengthMethod = HijrahChronology.class.getDeclaredMethod("getYearLength", int.class);
    getYearLengthMethod.setAccessible(true);

    Field minYearLengthField = HijrahChronology.class.getDeclaredField("minYearLength");
    minYearLengthField.setAccessible(true);

    Field maxYearLengthField = HijrahChronology.class.getDeclaredField("maxYearLength");
    maxYearLengthField.setAccessible(true);

    for (int year = minYear; year < maxYear; year++) {
      int length = (int) getYearLengthMethod.invoke(HijrahChronology.INSTANCE, year);
      int minYearLength = (int) minYearLengthField.get(HijrahChronology.INSTANCE);
      int maxYearLength = (int) maxYearLengthField.get(HijrahChronology.INSTANCE);
      minYearLengthField.set(HijrahChronology.INSTANCE, Math.min(minYearLength, length));
      maxYearLengthField.set(HijrahChronology.INSTANCE, Math.max(maxYearLength, length));
    }
  }
}
