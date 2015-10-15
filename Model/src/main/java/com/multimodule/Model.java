package com.multimodule;

public class Model{
  String firstVal;
  String secondVal;
  
  public Model(String firstVal, String secondVal){
    this.firstVal = firstVal;
    this.secondVal = secondVal;
  }
  
  public void setFirstVal(String val){
    firstVal = val;
  }
  
  public void setSecondVal(String val){
    secondVal = val;
  }
  
  public String getFirstVal(){
    return firstVal;
  }
  
  public String getSecondVal(){
    return secondVal;
  }
  
}
