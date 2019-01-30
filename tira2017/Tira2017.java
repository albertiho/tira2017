import java.lang.Integer;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



public class Tira2017{
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
         if(A.head().node().data() > B.head().node().data()){
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
   
   public void tulostaminen(String operation, HarjoitusTaulu C){  // operation which handles print
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
      
      // forming the table which is printed to a preferred form
      String[] result = new String[C.size()];
      int secondaryValue = Integer.MAX_VALUE;
      HarjoitusLokero temp = C.head();
      
      for(int i = 0; i < result.length; i++){
         // filling the tables second column with preferred values
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

   /* operation which performs the deletion of values */
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
   
   /* reading the files */
   public String[] readInput(String luettava) {
      String line;
      String[] rvalues = new String[0];
      
      try {
         BufferedReader br = new BufferedReader( new FileReader(luettava));
         int koko = 0;  // grabbing the files size for later use
         while(br.readLine() != null){
            koko++;
         }
         br.close();
         
         String[] values = new String[koko];
         BufferedReader vv = new BufferedReader( new FileReader(luettava));
         
         for(int i=0; i < koko; i++){  // moving the data from the file to a table
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

   /* printing the files */
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


   public static void main(String[] args){
      Tira2017 ht = new Tira2017();
      ht.run();
      
   }


}
