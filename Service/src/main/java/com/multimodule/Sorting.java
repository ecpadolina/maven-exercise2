package com.multimodule;

import java.util.Comparator;

public class Sorting{
  private Sorting(){}
  
  public static Comparator<Model> ascending = new Comparator<Model>(){
    @Override
    public int compare(Model m1, Model m2){
      String model1 = m1.getFirstVal() + m1.getSecondVal();
      String model2 = m2.getFirstVal() + m2.getSecondVal();
      return model1.compareTo(model2);
    }
  };
  public static Comparator<Model> descending = new Comparator<Model>(){
    @Override
    public int compare(Model m1, Model m2){
      String model1 = m1.getFirstVal() + m1.getSecondVal();
      String model2 = m2.getFirstVal() + m2.getSecondVal();
      return model2.compareTo(model1);
    }
  };
}
