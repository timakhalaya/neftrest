package utility;

import org.tempuri.Add;
import org.tempuri.Divide;
import org.tempuri.Multiply;
import org.tempuri.Subtract;

public class UtilityInstractions {

  /**
   * Specify type transformation for our class
   * @param classType
   * @return
   */
  public static Class<? extends Object> getCorrectClassOperation(Object classType){
    if(classType instanceof Divide){
      return Divide.class;
    }
    if(classType instanceof Subtract){
      return Subtract.class;
    }
    if(classType instanceof Multiply){
      return Multiply.class;
    }
    if(classType instanceof Add){
      return Add.class;
    }
    return Object.class;
  }

}
