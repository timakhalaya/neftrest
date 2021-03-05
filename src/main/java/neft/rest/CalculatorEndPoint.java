package neft.rest;

import org.springframework.stereotype.Component;
import org.tempuri.CalculatorSoap;

@Component
public class CalculatorEndPoint implements CalculatorSoap {
  @Override
  public int subtract(int intA, int intB) {
    return intA - intB;
  }

  @Override
  public int divide(int intA, int intB) {
    return intA/intB;
  }

  /**
   * Adds two integers. This is a test WebService. ©DNE Online
   *
   * @param intA
   * @param intB
   */
  @Override
  public int add(int intA, int intB) {
    return intA + intB;
  }

  @Override
  public int multiply(int intA, int intB) {
    return intA * intB;
  }
}
