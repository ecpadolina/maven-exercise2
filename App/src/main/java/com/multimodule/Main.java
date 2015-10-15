package com.multimodule;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;

public class Main{
  private static Service service;
  private static Scanner sc = new Scanner(System.in);
    
  public static void main(String[] args){
    Main main = new Main();
    service = new Service(args[0]);
    service.printGrid();
    main.showOptions();
  }
  
  private void showOptions(){
    Scanner sc = new Scanner(System.in);
    System.out.print("\nOPTIONS:\n" + 
                        "[1] Print\n" +
                        "[2] Edit \n" +
                        "[3] Search \n" + 
                        "[4] Add New Row\n" +
                        "[5] Sort Row\n" +
                        "[6] Exit\n" +
                        "Choose Your Option: ");
        String option = sc.next();      
        if(option.matches("[123456]")){
            if(option.equals("1")){
                service.printGrid();
                showOptions();
            }
            else if(option.equals("2")){
                inputEditData();
                showOptions();
            }
            else if(option.equals("3")){
                inputSearchData();
                showOptions();  
            }
            else if(option.equals("4")){
                inputAddRow();
                showOptions();
            }
            else if(option.equals("5")){
                inputSortRow();
                showOptions();
            }
            else if(option.equals("6")){
                return;
            }
        }
        else {
            System.out.println("Option not available");
            showOptions();
        }
  }
  
  private void inputEditData(){
    int rowToEdit = 0, colToEdit = 0;
    do{
      System.out.print("\nEnter row to edit: ");
      rowToEdit = checkInput();
      System.out.print("Enter column to edit: ");
      colToEdit = checkInput();
    }while(!(service.checkIfRowColExist(rowToEdit,colToEdit)));
    
    System.out.println("Choose what to edit:");
    System.out.println("[1] First Value");
    System.out.println("[2] Second Value");
    int option = 0;
    
    do{
      System.out.print("Option: ");
      option = checkInput();
      if(option <= 0 || option > 2)
        System.out.println("Input the right option!");
      }while(option <= 0 || option > 2);
    System.out.print("Enter new value: ");
    String newVal = sc.next();
    service.editGrid(rowToEdit,colToEdit,option,newVal);
  }
  
  private void inputSearchData(){
    System.out.print("\nInput search parameter: ");
    String searchParam = sc.next();
    System.out.print(service.searchString(searchParam));
  }
  
  private void inputSortRow(){
    System.out.print("\nEnter row to sort: ");
    String choice = "";
    int rowToSort = checkInput();
    if(rowToSort <= service.getMaxRows()){
      System.out.println("Sort by: ");
      System.out.println("[1] Ascending");
      System.out.println("[2] Descending");
      do{
        System.out.print("Choose your option: ");
        choice = sc.next();
      } while(!(choice.matches("[12]")));
      
        service.sortRow(rowToSort,choice);
        System.out.println("Row sorted!");
        
      } else {
        System.out.println("Row not found!");
        inputSortRow();
      }
  }
  
  private void inputAddRow(){
    ArrayList<Model> line = new ArrayList<Model>();
    int maxRows = service.getMaxRows();
    int maxCols = service.getMaxCols();
    Model model;
    System.out.println("Adding a new row[" + (maxRows + 1) + "]: ");
    
    for(int i = 0; i < maxCols; i++){
      System.out.print("Add First Value for cell ["+ (maxRows + 1) + "][" + (i + 1) + "]: ");
      String firstVal = sc.next();
      System.out.print("Add Second Value for cell ["+ (maxRows + 1) + "][" + (i + 1) + "]: ");
      String secondVal = sc.next();
      model = new Model(firstVal,secondVal);
      line.add(model);
    }
    service.addRow(line);
    System.out.println("Row added!");
  }
  
  private int checkInput(){
    int input = 0;
    boolean endLoop = false;
    while(!endLoop){
      try{
        input = sc.nextInt();
        endLoop = true;
      } catch(InputMismatchException e){
        System.out.println("Input is wrong!");
        sc.nextLine();
        System.out.print("Input again:");
      }
    }
        return input;
    }
}
