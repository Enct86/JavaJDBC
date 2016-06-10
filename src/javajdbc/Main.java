/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javajdbc;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Max
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.lang.ClassNotFoundException
     */
    public static void main(String[] args) throws ClassNotFoundException {

        Class.forName(com.mysql.jdbc.Driver.class.getName());

        String hostname = "127.0.0.1";
        int hostport = 3306;
        String datebase = "test";
        String username = "root";
        String password = "Uu11!22!";

        Scanner sc1 = new Scanner(System.in);

        String connectionString = String.format("jdbc:mysql://%s:%d/%s?useSSL=true", hostname, hostport, datebase);
        while (true) {
            System.out.printf("1 - Show all contacts\n2 - Add contact\n3 - Edit contact\n4 - Find contact\n5 - Delete contact\n0 - Exit\n");
            String input1 = sc1.nextLine();
            int shose = Integer.parseInt(input1);

            switch (shose) {
                case 0:
                    System.exit(0);
                case 1:
                    try (Connection conn = DriverManager.getConnection(connectionString, username, password)) {
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM contacts");

                        System.out.println("+------------------------------------------------------------+");
                        System.out.println("|--------------------------CONTACTS--------------------------|");
                        System.out.println("|------------------------------------------------------------|");
                        System.out.println("+ID|Name----------------|Tel.Number-----|Skype---------------+");
                        while (rs.next()) {
                            int id = rs.getInt(1);
                            String name = rs.getString(2);
                            String number = rs.getString(3);
                            String skype = rs.getString(4);
                            System.out.printf("|%-2s|%-20s| %-13s |%-20s|\n", id, name, number, skype);
                        }
                        System.out.println("+------------------------------------------------------------+");
                    } catch (Exception e) {
                        System.out.println("No connection to db - Error" + e);
                    }
                    break;
                case 2:
                    try (Connection conn = DriverManager.getConnection(connectionString, username, password)) {
                        Statement stmt = conn.createStatement();
                        System.out.println("Enter name : ");
                        String inputname = sc1.nextLine();

                        System.out.println("Enter number (in format +XXXXXXXXXXXX : ");
                        String inputnumber = sc1.nextLine();

                        System.out.println("Enter skype : ");
                        String inputskype = sc1.nextLine();

                        String insert = "INSERT INTO test.contacts (name, number, skype)\n"
                                + "VALUES ('" + inputname + "' , '" + inputnumber + "', '" + inputskype + "')";
                        stmt.executeUpdate(insert);
                        System.out.println("Contact Add Finished!");

                    } catch (Exception e) {
                        System.out.println("No connection to db - Error - " + e);
                    }
                    break;
                case 3:
                    try (Connection conn = DriverManager.getConnection(connectionString, username, password)) {
                        Statement stmt = conn.createStatement();
                        System.out.println("What to find for edit: ");
                        String find = sc1.nextLine();
                        String findQuery = "SELECT * FROM contacts WHERE id = '" + find + "' or name = '" + find + "' or number = '" + find + "' or skype = '" + find + "'";
                        ResultSet rs = stmt.executeQuery(findQuery);
                        if (rs.next()) {

                            System.out.println("+------------------------------------------------------------+");
                            System.out.println("|--------------------------CONTACT---------------------------|");
                            System.out.println("|------------------------------------------------------------|");
                            System.out.println("+ID|Name----------------|Tel.Number-----|Skype---------------+");

                            int id = rs.getInt(1);
                            String name = rs.getString(2);
                            String number = rs.getString(3);
                            String skype = rs.getString(4);
                            System.out.printf("|%-2s|%-20s| %-13s |%-20s|\n", id, name, number, skype);

                            System.out.println("+------------------------------------------------------------+");
                            System.out.println("Edit contact? (type  - yes for confirm)");
                            String answeredit = sc1.nextLine();
                            if (answeredit.toLowerCase().equals("yes")) {
                                System.out.printf("Select what edit - 1 - Name, 2 - Number, 3 - Skype\n");
                                answeredit = sc1.nextLine();
                                int editCh = Integer.parseInt(answeredit);
                                switch (editCh) {
                                    default:
                                        System.out.println("Edit Canceled");
                                        break;
                                    case 1:
                                        System.out.println("Enter new name for id - " + id);
                                        String answerName = sc1.nextLine();
                                        stmt.executeUpdate("UPDATE contacts set name='" + answerName + "' WHERE id='" + id + "'");
                                        System.out.println("Contact id=" + id + " - updated");
                                        break;
                                    case 2:
                                        System.out.println("Enter new number for id - " + id);
                                        String answerNumber = sc1.nextLine();
                                        stmt.executeUpdate("UPDATE contacts set number='" + answerNumber + "' WHERE id='" + id + "'");
                                        System.out.println("Contact id=" + id + " - updated");
                                        break;
                                    case 3:
                                        System.out.println("Enter new Skype for id - " + id);
                                        String answerSkype = sc1.nextLine();
                                        stmt.executeUpdate("UPDATE contacts set skype='" + answerSkype + "' WHERE id='" + id + "'");
                                        System.out.println("Contact id=" + id + " - updated");
                                        break;
                                }
                            } else {
                                System.out.println("Edit Canceled");
                            }
                        } else {
                            System.out.println("Nothing found :.(");
                        }
                    } catch (Exception e) {
                        System.out.println("No connection to db - Error - " + e);
                    }
                    break;
                case 4:
                    try (Connection conn = DriverManager.getConnection(connectionString, username, password)) {
                        Statement stmt = conn.createStatement();
                        System.out.println("What to find : ");
                        String find = sc1.nextLine();
                        String findQuery = "SELECT * FROM contacts WHERE id = '" + find + "' or name = '" + find + "' or number = '" + find + "' or skype = '" + find + "'";
                        ResultSet rs = stmt.executeQuery(findQuery);
                        if (rs.next()) {
                            rs.previous();

                            System.out.println("+------------------------------------------------------------+");
                            System.out.println("|--------------------------CONTACTS--------------------------|");
                            System.out.println("|------------------------------------------------------------|");
                            System.out.println("+ID|Name----------------|Tel.Number-----|Skype---------------+");
                            while (rs.next()) {
                                int id = rs.getInt(1);
                                String name = rs.getString(2);
                                String number = rs.getString(3);
                                String skype = rs.getString(4);
                                System.out.printf("|%-2s|%-20s| %-13s |%-20s|\n", id, name, number, skype);
                            }
                            System.out.println("+------------------------------------------------------------+");
                        } else {
                            System.out.println("Nothing found :.(");
                        }
                    } catch (Exception e) {
                        System.out.println("No connection to db - Error - " + e);
                    }
                    break;
                case 5:
                    try (Connection conn = DriverManager.getConnection(connectionString, username, password)) {
                        Statement stmt = conn.createStatement();
                        System.out.println("What to find for delete : ");
                        String find = sc1.nextLine();
                        String findQuery = "SELECT * FROM contacts WHERE id = '" + find + "' or name = '" + find + "' or number = '" + find + "' or skype = '" + find + "'";
                        ResultSet rs = stmt.executeQuery(findQuery);
                        if (rs.next()) {
                            rs.previous();
                            ArrayList idfordel = new ArrayList();
                            System.out.println("+------------------------------------------------------------+");
                            System.out.println("|--------------------------CONTACTS--------------------------|");
                            System.out.println("|------------------------------------------------------------|");
                            System.out.println("+ID|Name----------------|Tel.Number-----|Skype---------------+");
                            while (rs.next()) {
                                int id = rs.getInt(1);
                                idfordel.add(id);
                                String name = rs.getString(2);
                                String number = rs.getString(3);
                                String skype = rs.getString(4);
                                System.out.printf("|%-2s|%-20s| %-13s |%-20s|\n", id, name, number, skype);
                            }
                            System.out.println("+------------------------------------------------------------+");
                            System.out.println("Delete contact(s)? (type  - yes for confirm)");
                            String answerdel = sc1.nextLine();
                            if (answerdel.toLowerCase().equals("yes")) {
                                for (Object id : idfordel) {
                                    String delQuery = "DELETE FROM contacts WHERE id='" + id + "'";
                                    stmt.executeUpdate(delQuery);
                                    System.out.println("id - " + id + " deleted");
                                }
                            } else {
                                System.out.println("Delete Canceled");
                            }

                        } else {
                            System.out.println("Nothing found :.(");
                        }
                    } catch (Exception e) {
                        System.out.println("No connection to db - Error - " + e);
                    }
                    break;
            }
        }
    }
}
