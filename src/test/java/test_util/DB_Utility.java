package test_util;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class DB_Utility {

    private static Connection con;
    private static Statement stm;
    private static ResultSet rs;
    private static ResultSetMetaData rsmd ;


    /**
     * a static method to create connection
     * with valid url and username password
     */

    public static void createConnection() {

        String url = ConfigurationReader.getProperty("hr.database.url");
        String username = ConfigurationReader.getProperty("hr.database.username");
        String password = ConfigurationReader.getProperty("hr.database.password");


//        try {
//
//            con = DriverManager.getConnection(url, username, password);
//            System.out.println("CONNECTION SUCCESSFUL");
//            con.close();
//
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            System.out.println("CONNECTION HAS FAILED " + e.getMessage());
//        }

        createConnection(url,username,password);
    }

    /**
     * a static method to create connection
     * with valid url and username password
     *
     * @param url
     * @param password
     * @param username
     */

    public static void createConnection(String url, String username, String password) {

//        url = ConfigurationReader.getProperty("hr.database.url");
//        username = ConfigurationReader.getProperty("hr.database.username");
//        password = ConfigurationReader.getProperty("hr.database.password");


        try {

            con = DriverManager.getConnection(url, username, password);
            System.out.println("CONNECTION SUCCESSFUL");

        } catch (SQLException e) {
            // e.printStackTrace();
            System.out.println("CONNECTION HAS FAILED " + e.getMessage());
        }
    }


    /**
     * a static method to get the ResultSet object
     * with valid connection nby executing query
     *
     * @param sql
     * @return ResultSet object that contain the result just in cases needed outside the class
     */

    public static ResultSet runQuery(String sql) {

        try {
            stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stm.executeQuery(sql); // setting the value of ResultSet object
            rsmd = rs.getMetaData() ;  // setting the value of ResultSetMetaData for reuse
        }catch(SQLException e){
            System.out.println("ERROR OCCURRED WHILE RUNNING QUERY "+ e.getMessage() );
        }

        return rs ;


}

    /**

     * This method will reset the cursor to before first location

     */



    /**
     * cleaning up the resources
     */

    public static void destroy() {

        try {
            if (rs != null) {
                rs.close();
            }
            if (con != null) {
                con.close();
            }
            if (stm != null) {
                stm.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    private static void resetCursor(){

        try {
            rs.beforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * get the row count of the Resultset
     *
     * @return the row number of the ResultSet given
     */

    public static int getRowCount() {

        int rowCount = 0;
        try {

            rs.last();
            rowCount = rs.getRow();


        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING ROW COUNT " + e.getMessage());

        }finally {
            resetCursor();
        }

        return rowCount;

    }

    /**
     * a method to get the column count of the current ResultSet
     */

    public static int getColumnCount() {

        int colCount = 0;

        try {

            ResultSetMetaData rsmd = rs.getMetaData();
            colCount = rsmd.getColumnCount();

        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING COLUMNS " + e.getMessage());
        }
        return colCount;
    }


    /**
     * a method that return all column names as list
     *
     * @param
     * @return List<String>
     */

    public static List<String> getAllColumnNamesAsList() {

        List<String> colNamesList = new ArrayList<>();

        try {

            ResultSetMetaData rsmd = rs.getMetaData();

            for (int colIndex = 1; colIndex <= rsmd.getColumnCount(); colIndex++) {

                String colName = rsmd.getColumnName(colIndex);
                colNamesList.add(colName);
            }

        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING COLUMN NAMES " + e.getMessage());
        }

        return colNamesList;

    }

    /**
     * getting the entire row name as List<String>
     *
     * @param rowNum the row number you want the list from
     * @return List of String that contains row data
     */

    public static List<String> getRowDataAsList(int rowNum) {

        List<String> rowDataList = new ArrayList<>();

        try {
            rs.absolute(rowNum);

            for (int colIndex = 1; colIndex <= getColumnCount(); colIndex++) {

                String cellValue = rs.getString(colIndex);
                rowDataList.add(cellValue);
            }

        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING ROW DATA AS LIST " + e.getMessage());
        }finally {
            resetCursor();
        }

        return rowDataList;
    }

    /**
     * getting single column cell value at certain row
     *
     * @param rowNum      the row number you want the list from
     * @param columnName column index number you want the list from
     * @return the data in String
     */

    public static String getCellValue(int rowNum ,String columnName){

        String cellValue = "" ;

        try {
            rs.absolute(rowNum) ;
            cellValue = rs.getString( columnName ) ;

        } catch (SQLException e) {
            System.out.println("ERROR OCCURRED WHILE getCellValue " + e.getMessage() );
        }finally {
            resetCursor();
        }
        return cellValue ;

    }


    /**
     * @param rowNum
     * @param columnName
     * @return the data at that row with that column name
     */

    public static String getColumnDataAtRow(int rowNum, String columnName) {

        String result = "";

        try {

            rs.absolute(rowNum);
            result = rs.getString(columnName);


        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING COLUMN DATA AT ROW " + e.getMessage());
        }finally {
            resetCursor();
        }

        return result;
    }

    /**
     * @param columnIndex the column you want to get a list out of
     * @return List of String that contains entire column data from 1st row to last row
     */

    public static String getCellValue(int rowNum , int columnIndex) {

        String cellValue = "" ;

        try {
            rs.absolute(rowNum) ;
            cellValue = rs.getString(columnIndex ) ;

        } catch (SQLException e) {
            System.out.println("ERROR OCCURRED WHILE getCellValue " + e.getMessage() );
        }finally {
            resetCursor();
        }
        return cellValue ;

    }


    /**
     * @param columnName the column you want to get a list out of
     * @return List of String that contains entire column data from 1st row to last row
     */

    public static List<String> getColumnDataAsList(String columnName) {

        List<String> columnDataList = new ArrayList<>();

        try {



            while (rs.next()) {
                String cellValue = rs.getString(columnName);
                columnDataList.add(cellValue);
            }



        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING COLUMN DATA AS LIST " + e.getMessage());

        }finally {
            resetCursor();
        }
        return columnDataList;

    }

    /**
     * getting entire column data as list according to column number
     * @param columnNum
     * @return List object that contains all rows of that column
     */
    public static List<String> getColumnDataAsList(int columnNum){

        List<String> columnDataLst = new ArrayList<>();

        try {
            rs.beforeFirst(); // make sure the cursor is at before first location
            while( rs.next() ){

                String cellValue = rs.getString(columnNum) ;
                columnDataLst.add(cellValue) ;
            }

        } catch (SQLException e) {
            System.out.println("ERROR OCCURRED WHILE getColumnDataAsList " + e.getMessage() );
        }finally {
            resetCursor();
        }


        return columnDataLst ;

    }

    /**
     * a method to display all the data in the result set
     */

    public static void displayAllData() {

        try {


            while (rs.next()) {

                for (int colIndex = 1; colIndex <= getColumnCount(); colIndex++) {

                   // System.out.print(rs.getString(colNum) + "\t");
                    System.out.printf("%-25s", rs.getString(colIndex));
                }

                System.out.println();
            }


        } catch (SQLException e) {
            System.out.println("ERROR WHILE DISPLAYING ALL DATA " + e.getMessage());

        }finally {
            resetCursor();
        }

    }

    /**
     * we want to store certain row data as a Map<String,String>
     * * For example :
     * give me number 3rd row -->> Map<String,String> {region_id : 3 , region_name : Asia}
     *
     * @param rowNum
     * @return Map object that contains column names as a key and cell as value
     */

    public static Map<String, String> getRowMap(int rowNum) {

        Map<String, String> rowMap = new LinkedHashMap<>();
        int columnCount = getColumnCount() ;

        try {
            rs.absolute(rowNum);


            for (int colIndex = 1; colIndex <= columnCount ; colIndex++) {
                String columnName = rsmd.getColumnName(colIndex) ;
                String cellValue  = rs.getString(colIndex) ;
                rowMap.put(columnName, cellValue) ;
            }


        } catch (SQLException e) {
            System.out.println("ERROR AT ROW MAP FUNCTION " + e.getMessage());
        }finally {
            resetCursor();
        }

        return rowMap;
    }

    /**
     * Getting entire ResultSet data as List of Map object
     *
     * @return List List<Map<String, String>> that represent all row data
     */

    public static List<Map<String, String>> getAllDataAsListOfMap() {

        List<Map<String, String>> rowMapList = new ArrayList<>();

        for (int rowNum = 1; rowNum <= getRowCount(); rowNum++) {

            rowMapList.add(getRowMap(rowNum));

        }
        return rowMapList;
    }

    /**
     * @return the first row first column name
     */

    public static String getFirstRowFirstColumn() {

        return getCellValue(1, 1);


    }



}
