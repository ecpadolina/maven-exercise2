package com.multimodule;

import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Service{
  private File file;
  private String filePath;
  private ArrayList<ArrayList<Model>> aList;
  
  public Service(String fileName){
    filePath = fileName;
    populateGrid();
  }

  public void printGrid(){
    System.out.println();
    for(ArrayList<Model> lines: aList){
      System.out.print("| ");
        for(Model mv : lines){
        String firstVal = mv.getFirstVal(); 
        String secondVal = mv.getSecondVal();
          System.out.print(firstVal + "," + secondVal);
          System.out.print(" | ");
        }
      System.out.println();
    }
  }
  
  public void editGrid(int rowToEdit, int colToEdit, int option, String newVal){
    ArrayList<Model> list = aList.get(rowToEdit-1);
    Model model = list.get(colToEdit-1);
    
    if(option == 1)
      model.setFirstVal(newVal);
    else
      model.setSecondVal(newVal);
      
    list.set(colToEdit-1, model);
    aList.set(rowToEdit-1, list);
    writeToFile();
    System.out.println("Grid successfully editted!");
  }
  
  public String searchString(String searchParam){
    String output = "";
    int totalOccurrences = 0;
    int colCounter = 1;
    
    for(ArrayList<Model> list : aList){
      for(Model model : list){
        String firstVal = model.getFirstVal();
        String secondVal = model.getSecondVal();
        int firstValOccurances = 0;
        int secondValOccurances = 0;
        
        if(firstVal.contains(searchParam) || secondVal.contains(searchParam)){
          if(firstVal.contains(searchParam))
            firstValOccurances = checkOccurrances(firstVal, searchParam);
          if(secondVal.contains(searchParam))
            secondValOccurances = checkOccurrances(secondVal, searchParam);
          output += "["+ (aList.indexOf(list) + 1) + "][" +colCounter + "] "
            + "with Key Occurances: " + firstValOccurances
            +  " Value Occurances: " + secondValOccurances + "\n";
        }
        totalOccurrences = totalOccurrences + (firstValOccurances + secondValOccurances);
        colCounter++;
      }
      colCounter = 1;
    }
    return "Search parameter \"" + searchParam + "\" occured (" + totalOccurrences + ") time(s) at \n" + output;
  }
  
  public void addRow(ArrayList<Model> model){
    aList.add(model);
    writeToFile();
  }
  
  public void sortRow(int rowToSort, String choice){
    ArrayList<Model> list = aList.get(rowToSort-1);
    if(choice.equals("1")){
      Collections.sort(list, Sorting.ascending);
    } else {
      Collections.sort(list, Sorting.descending);
    }
    aList.set(rowToSort-1,list);
    writeToFile();
  }
  
  private int checkOccurrances(String tempString, String stringToFind){
    int count = 0;
    if(stringToFind.length() == 1){
      count = tempString.length() - tempString.replaceAll(stringToFind, "").length();
    } else {
        int lastIndex = 0;
        while ((lastIndex = tempString.indexOf(stringToFind, lastIndex)) != -1) {
            count++;
            lastIndex += stringToFind.length() - 1;
        }
    }
      return count;
  }
  
  public int getMaxRows(){
    return aList.size();
  }
  
  public int getMaxCols(){
    return aList.get(getMaxRows() - 1).size();
  }
  
  public boolean checkIfRowColExist(int rowToEdit, int colToEdit){
    int maxRows = aList.size();
    int maxCols = aList.get(maxRows-1).size();
    
    if((rowToEdit <= maxRows && colToEdit <= maxCols) && (rowToEdit > 0 && colToEdit > 0))
      return true;
    else{
      System.out.println("Row/Col not found!");
      return false;
    }
  }
  
  public void populateGrid(){
    try{
      file = FileUtils.getFile(filePath);
      List<String> lines = FileUtils.readLines(file);
      aList = new ArrayList<ArrayList<Model>>();   
      for(String line : lines){
        ArrayList<Model> tmp = new ArrayList<Model>();
        for(String entry : line.split("\\s")){
          String data[] = entry.split("\\,");
          String firstVal = replaceSpecialChartoDelimiter(data[0]);
          String secondVal = replaceSpecialChartoDelimiter(data[1]);
          tmp.add(new Model(firstVal,secondVal));
        }
        aList.add(tmp);
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found!");
    } catch (IOException e) {
      System.out.println("Error in file.");
    }
  }
  
  public void writeToFile(){
    String content = "";
    int ctr = 1;
    try {
      for(ArrayList<Model> lines : aList){
        for(Model model : lines){
          String firstVal = replaceDelimeterInputToSpecialChar(model.getFirstVal());
          String secondVal = replaceDelimeterInputToSpecialChar(model.getSecondVal());
          content += firstVal + "," + secondVal;
          if(aList.size() > ctr){
            content += " ";
            ctr++;
          }
        }
        ctr = 1;
        content += "\n";
      } 
			if (!file.exists()) {
			  file.createNewFile();
			}
			FileUtils.writeStringToFile(file,content);
		} catch (IOException e) {
			System.out.println("Error in file.");
		}
  }
  
  public String replaceDelimeterInputToSpecialChar(String a){
    char replaceforComma = 128;
    char replaceforSpace = 129;
    char[] b = a.toCharArray();
    if(a.contains(",") || a.contains(" ")){
        for(int i = 0; i < b.length; i++){
          if(b[i] == ',')
            b[i] = replaceforComma;
          if(b[i] == ' ')
            b[i] = replaceforSpace;
          }
        }
      String newString = new String(b);
      return newString;
    }
    
    public String replaceSpecialChartoDelimiter(String a){
      char replaceforComma = 128;
      char replaceforSpace = 129;
      String specCharforComma = String.valueOf(replaceforComma);
      String specCharforSpace = String.valueOf(replaceforSpace);
      char[] b = a.toCharArray();
      if(a.contains(specCharforComma) || a.contains(specCharforSpace)){
        for(int i = 0; i < b.length; i++){
          if(b[i] == replaceforComma)
            b[i] = ',';
          if(b[i] == replaceforSpace)
            b[i] = ' ';
          }
        }
      String newString = new String(b);
      return newString;
    }
}
