//package com.uptech.accounted;
//
//import javafx.stage.Stage;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.context.ConfigurableApplicationContext;
//
//@SpringBootApplication
//public class Reset implements App {
//
//  protected ConfigurableApplicationContext springContext;
//
//  public static void main(final String[] args) {
//    Application.launch(args);
//  }
//
//  @Override
//  public void init() throws Exception {
//    springContext = springBootApplicationContext();
//  }
//
//  @Override
//  public void start(Stage stage) throws Exception {
//  }
//
//  @Override
//  public void stop() throws Exception {
//    springContext.close();
//  }
//
//  private ConfigurableApplicationContext springBootApplicationContext() {
//    SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
//    String[] args = getParameters().getRaw().stream().toArray(String[]::new);
//    return builder.run(args);
//  }
//}
