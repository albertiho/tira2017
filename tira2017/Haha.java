//package ht;

/* Tietorakenteet harjoitustyˆ 2017 
   Albert Iho
   
   hajautustaulun sek‰ joukko-opin operaatioita
   
   */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Integer;
import java.util.Scanner;

public class Haha {
   public void run() {
      System.out.println("Coursework handles numbers as Integers. The hashfunction used is (+1),");
      System.out.println("which means that overflow is possible by trying to enter");
      System.out.println("Integer.MAX_VALUE twice in a row or a group of values that would");
      System.out.println("end up exceeding Integer.MAX_VALUE.\n");
      
      HarjoitusTaulu htA = new HarjoitusTaulu();
      String[] setA = readInput("setA.txt");
      
      HarjoitusTaulu htB = new HarjoitusTaulu();
      String[] setB = readInput("setB.txt");
      
      
      boolean run = true;
      Scanner sc = new Scanner(System.in);
      while(run){
         
         System.out.println("\nUse commands \"or\", \"and\" and \"xor\" to perform actions.");
         System.out.println("You can exit the program by using \"exit\" -command.");
         String s = sc.nextLine();
         String command = s.substring(0, 1);
         
         if((s.charAt(0) == 'e') && (s.charAt(1) == 'x') && (s.charAt(2) == 'i') && 
                     (s.charAt(3) == 't') && (s.length() == 4)){
              run = false;
         } else {
            // import the data from txt-files incase user doesnt wish to exit.
            htA.addArray(setA, 1);
            htB.addArray(setB, 2);
            
            if((s.charAt(0) == 'o') && (s.charAt(1) == 'r') && (s.length() == 2)){
               union(htA, htB);
            } else if((s.charAt(0) == 'a') && (s.charAt(1) == 'n') && (s.charAt(2) == 'd') && (s.length() == 3)){
               intersection(htA, htB);
            } else if((s.charAt(0) == 'x') && (s.charAt(1) == 'o') && (s.charAt(2) == 'r') && (s.length() == 3)){
               exclude(htA, htB);
            } else {
               System.out.println("incorrect input");
            }
         }
      }
      System.out.println("\nCoursework closed.");
   }
   
   public void union(HarjoitusTaulu htA, HarjoitusTaulu htB){  // and-operation
      HarjoitusTaulu A = htA;
      HarjoitusTaulu B = htB; 
      HarjoitusTaulu C = new HarjoitusTaulu();
      
      while((A.size() > 0) && (B.size() > 0)){
         if(A.head().node().data() > B.head().node().data){
            C.insertItem(A.removeHead());
         } else {
            C.insertItem(B.removeHead());
         }
      }
      while(A.size() > 0){
         C.insertItem(A.removeHead());
      }
      while(B.size() > 0){
         C.insertItem(B.removeHead());
      }
      
      tulostaminen("or", C);
   }
   
   public void intersection(HarjoitusTaulu htA, HarjoitusTaulu htB){ // or-operation
      HarjoitusTaulu A = htA;
      HarjoitusTaulu B = htB;
      HarjoitusTaulu C = new HarjoitusTaulu();
      
      while((A.size() > 0) && (B.size() > 0)){
         int a = A.head().node().data();
         int b = B.head().node().data();
         
         if(a == b){
            C.insertItem(A.removeHead());
            B.removeHead();
         } else if (a < b){
            A.removeHead();
         } else if (b < a){
            B.removeHead();
         }
      }
      
      tulostaminen("and", C);
   }
   
   public void exclude(HarjoitusTaulu htA, HarjoitusTaulu htB){  // xor-operation
      HarjoitusTaulu A = htA;
      HarjoitusTaulu B = htB;
      HarjoitusTaulu C = new HarjoitusTaulu();
      
      while((A.size() > 0) && (B.size() > 0)){
         int a = A.head().node().data();
         int b = B.head().node().data();
         
         if(a == b){
            A.removeHead();
            B.removeHead();
         } else if (a < b){
            C.insertItem(A.removeHead());
         } else if (b < a){
            C.insertItem(B.removeHead());
         }
      }
      while(A.size() > 0){
         C.insertItem(A.removeHead());
      }
      while(B.size() > 0){
         C.insertItem(B.removeHead());
      }
      
      tulostaminen("xor", C);
   }

/* TrainingNode class to control information within one node */
public class HarjoitusNode {
   
   public HarjoitusNode(int d, int s, int r){
      data = d;
      source = s;
      rivinumero = r;
      amount = 1;
   }
   
   /* setters */
   public void data(int d){
      data = d;
   }
   
   public void amount(int a){
      amount = a;
   }
   
   /* getters */
   public int data(){
      return data;
   }
   
   public int source(){
      return source;
   }
   
   public int rivi(){
      return rivinumero;
   }
   
   public int amount(){
      return amount;
   }
   
   private int data;    // data within one node
   private int source; // helping variable for the xor-operation
   private int rivinumero; // "rownumber", helping variable for the and-operaation 
   private int amount;  // helping variable for the or-operation
}
  
  
/* Datatype that controls a single branch in the coursework */
public class HarjoitusLokero {
   
   public HarjoitusLokero(int i, HarjoitusNode n){
      index = i;
      node = n;
      next = prev = null;
   }
   
   /* setters */
   public void index(int i){
      index = i;
   }
   
   public void node(HarjoitusNode n){
      node = n;
   }
   
   public void next(HarjoitusLokero n){
      next = n;
   }
   
   public void prev(HarjoitusLokero p){
      prev = p;
   }

   /* getters */
   public int index(){
      return index;
   }
   
   public HarjoitusNode node(){
      return node;
   }
   
   public HarjoitusLokero next(){
      return next;
   }

   public HarjoitusLokero prev(){
      return prev;
   }
   
   private int index;
   private HarjoitusNode node;
   private HarjoitusLokero next;
   private HarjoitusLokero prev;
}


/* Datatype which is used in the hashmap */
public class HarjoitusTaulu {
   
   /* add the given file to the TrainingTable */
   public void addArray(String[] taulukko, int sourcefile){
      for(int i = 0; i < taulukko.length; i++){

         int apu = Integer.valueOf(taulukko[i]);
         HarjoitusLokero input = new HarjoitusLokero(apu, new HarjoitusNode(apu, sourcefile, i+1));
      
         if(size == 0){ // if the table is empty
            head = tail = input; 
         } else {
            for(HarjoitusLokero h = head; h != null; h = h.next()){
               if (h.index() == input.index()){
                  input.index(input.index() +1);  // grow the index number untill a free section is found
               }
            }
            placeItem(input);  // place the item
         }
         size++;  // grow the tables size
      }
   }
   
   /* add-operations item placing part */
   public void placeItem(HarjoitusLokero input){
      
      if(input.index() < head.index()){   // when the item is the smallest in the group
         HarjoitusLokero temp = head;
         head = input;
         input.next(temp);
         temp.prev(head);
         
      } else if (input.index() > tail.index()){ // when the item is the biggest in the group
         HarjoitusLokero temp = tail;
         tail = input;
         input.prev(temp);
         temp.next(tail);
         
      } else { // other cases
         HarjoitusLokero temp = head;
         HarjoitusLokero tempNext = head.next();
         for (int i = 0; i < size-1; i++){
            if (input.index() > temp.index() && input.index() <= tempNext.index()){
               temp.next(input);
               tempNext.prev(input);
               input.prev(temp);
               input.next(tempNext);
            } else {
               temp = temp.next();
               tempNext = tempNext.next();
            }
         }
      }
   }
   
   /* adding operation which is used by the group-operations */
   public void insertItem(HarjoitusLokero item){
      HarjoitusLokero temp = new HarjoitusLokero(item.node().data(), item.node());
      boolean sijoitettu = false;
      if (size == 0){
         head = tail = item;
         size++;
      } else {
         for(HarjoitusLokero h = head; h != null; h= h.next()){
            if(temp.node().data() == h.node().data()){
               int apu = h.node().amount();
               h.node().amount(apu + 1);
               sijoitettu = true;
               h = tail;
            }
         }
         if(!sijoitettu){
            placeItem(item);
            size++;
         }
      }
   }
   
   /* operation which deletes the smallest item on the list */
   public HarjoitusLokero removeHead(){
      if (size != 0) {
         HarjoitusLokero temp = new HarjoitusLokero(head.node().data(), head.node());
         
         if(size > 1) {
            head = head.next();
            head.prev(null);
         } else {
            head = tail = null;
         }
         size--;
         return temp;
      } else {
         return null;
      }
   }
   
   /* removing one item from the list */
   public boolean remove(int poistettava){
      boolean success = false;
      if (size != 0){
         // when the item is smallest
         if ((size == 1) || (head.node().data() == poistettava)){
            removeHead();
            success = true;
            
         // when the item is the largest
         } else if(tail.node().data() == poistettava) {
            tail = tail.prev();
            tail.next(null);
            size--;
            success = true;
            
         // other cases
         } else {
            for(HarjoitusLokero temp = head; temp != null; temp = temp.next()){
               if(temp.node().data() == poistettava){
                  temp.prev().next(temp.next());
                  temp.next().prev(temp.prev());
                  size--;
                  success = true;
                  temp = tail;
               }
            }
         }
      }
      return success;
   }
   
   public void print(){
      for(HarjoitusLokero temp = head; temp != null; temp = temp.next()){
         System.out.print("Index: " + temp.index() + ", data: " + temp.node().data() + ", ");
         System.out.println("rivi: " + temp.node().rivi() + ", m‰‰r‰: " + temp.node().amount());
      }
      System.out.println("list size: " + size);
   }

   /* constructors */
   public HarjoitusTaulu(){
      size = 0;
      head = tail = null;
   }

   /* setters */
   public void size(int s){
      size = s;
   }
   
   public void head(HarjoitusLokero h){
      head = h;
   }
   
   public void tail(HarjoitusLokero t){
      tail = t;
   }
   
   /* getters */
   public int size(){
      return size;
   }
   
   public HarjoitusLokero head(){
      return head;
   }
   
   public HarjoitusLokero tail(){
      return tail;
   }
   
   private int size;
   private HarjoitusLokero head;
   private HarjoitusLokero tail;
}

public static void main(String[] args){
   Haha ht = new Haha();
   ht.run();
   
}
   
   
   /* helping operations */


   /* reading the files */
   public String[] readInput(String luettava) {
      String line;
      String[] rvalues = new String[0];
      
      try {
         BufferedReader br = new BufferedReader( new FileReader(luettava));
         int koko = 0;  // grab the files size for later use
         while(br.readLine() != null){
            koko++;
         }
         br.close();
         
         String[] values = new String[koko];
         BufferedReader vv = new BufferedReader( new FileReader(luettava));
         
         for(int i=0; i < koko; i++){  // moving the file into values-table
            line=vv.readLine().trim();
            String[] temp = line.split("\n");
            values[i] = temp[0];
         }
         vv.close();
         rvalues = values;
	 
      } catch(IOException e){
         System.out.println("File not found.");;
      }
      System.out.println("File \"" + luettava + "\" read succesfully.");
      return rvalues;
   }
   
   
   /* printing the files in the preferred form */
   public void tulostaminen(String operation, HarjoitusTaulu C){
      boolean more = true;
      while(more){
         System.out.print("\nPress \"1\" if you wish to remove a number from the ");
         System.out.println(operation + "-hash table,");
         System.out.println("      \"0\" to continue without deleting");
         Scanner ab = new Scanner(System.in);
         int input = ab.nextInt();
         
         if(input == 1){
            delete(C);
         } else if(input == 0){
            more = false;
         } else {
            System.out.println("incorrect input");
         }
      }
      
      // forming the table which is printed
      String[] result = new String[C.size()];
      int secondaryValue = Integer.MAX_VALUE;
      HarjoitusLokero temp = C.head();
      
      for(int i = 0; i < result.length; i++){
         // filling the second column with preferred values
         if(operation == "or"){
            secondaryValue = temp.node().amount();
         } else if(operation == "and"){
            secondaryValue = temp.node().rivi();
         } else if(operation == "xor"){
            secondaryValue = temp.node().source();
         }
         
         result[i] = temp.node().data() + "   " + secondaryValue;
         temp = temp.next();
      }
      writeOutput(result, operation + ".txt");
      System.out.println("Operation \"" + operation + "\" succesful, result was a group which ");
      System.out.println("includes " + result.length + " items.");
   }

   
   /* deleting items */
   public void delete(HarjoitusTaulu table){
      if(table.size() > 0){
         Scanner sc = new Scanner(System.in);
         System.out.println("\nEnter the value you wish to remove: ");
         int input = sc.nextInt();
         
         if(table.remove(input)){
            System.out.println("Value deletion succesful.");
         } else {
            System.out.println("value not found.");
         }
      } else {
         System.out.println("Table empty, cannot delete.");
      }
   }
   
   
   /* printing files */
   public void writeOutput(String[] output, String operation){
      
      try {
         BufferedWriter bw = new BufferedWriter(new FileWriter(operation));	
         for(int i = 0; i < output.length; i++){
            bw.write(output[i]);
            bw.newLine();
         }
         bw.close();
      } catch (IOException e) {
         System.err.format("IOException: %s%n", e);
      }
      System.out.println("\nWriting file \"" + operation + "\".");
   }
   

}


