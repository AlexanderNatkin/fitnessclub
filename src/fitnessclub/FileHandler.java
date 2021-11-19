package fitnessclub;

import java.io.*;
import java.util.LinkedList;

public class FileHandler {
    public LinkedList<Member> readFile(){
        LinkedList<Member> listMember = new LinkedList();
        String lineRead;
        String[] splitLine;
        Member member;

        try(BufferedReader reader = new BufferedReader(new FileReader("Members.csv"))) {
            lineRead = reader.readLine();
            while (lineRead != null) {
                splitLine = lineRead.split(", ");
                if (splitLine[0].equals("S")) {
                    member = new SingleClubMember('S', Integer.parseInt(splitLine[1]), splitLine[2],
                            Double.parseDouble(splitLine[3]), Integer.parseInt(splitLine[4]));
                } else {
                    member = new MultiClubMember('M', Integer.parseInt(splitLine[1]), splitLine[2],
                            Double.parseDouble(splitLine[3]), Integer.parseInt(splitLine[4]));
                }
                listMember.add(member);
                lineRead = reader.readLine();
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return listMember;
    }

    public void appendFile(String mem) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Members.csv", true))) {
            writer.write(mem + "\n");
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void overWriteFile(LinkedList<Member> m) {
        String s;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Members.temp", false))) {
            for (int i = 0; i < m.size(); i++) {
                s = m.get(i).toString();
                writer.write(m + "\n");
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        try {
            File oldVersion = new File("Members.csv");
            File newVersion = new File("Members.temp");
            oldVersion.delete();
            newVersion.renameTo(oldVersion);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
