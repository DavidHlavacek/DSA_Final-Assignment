package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVLoader {
    private String filePath;
    private List<String[]> data;
    private String[] headers;
    
    public CSVLoader(String filePath) {
        this.filePath = filePath;
        this.data = new ArrayList<>();
    }
    
    public boolean loadCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            
            while ((line = reader.readLine()) != null) {
                String[] values = parseCSVLine(line);
                
                // parse headers
                if (firstLine) {
                    headers = values;
                    firstLine = false;
                } else {
                    data.add(values);
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error loading CSV: " + e.getMessage());
            return false;
        }
    }
    
    private String[] parseCSVLine(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder value = new StringBuilder();
        boolean inQuotes = false;
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            // handle quotes + commas
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                values.add(value.toString().trim());
                value = new StringBuilder();
            } else {
                value.append(c);
            }
        }
        
        values.add(value.toString().trim());
        return values.toArray(new String[0]);
    }
    
    public String[] getHeaders() {
        return headers;
    }
    
    public List<String[]> getData() {
        return data;
    }
    
    public <T extends Comparable<T>> List<T> getColumn(int index, Class<T> type) {
        List<T> column = new ArrayList<>();
        
        if (index < 0 || data.isEmpty() || index >= data.get(0).length) {
            return column;
        }
        
        for (String[] row : data) {
            if (index < row.length) {
                String value = row[index];
                try {
                    if (type == Double.class) {
                        column.add(type.cast(Double.valueOf(value)));
                    } else {
                        column.add(type.cast(value));
                    }
                } catch (Exception e) {
                
                }
            }
        }
        
        return column;
    }
    
    // check by majority
    public boolean isNumericColumn(int index) {
        if (index < 0 || data.isEmpty() || index >= data.get(0).length) {
            return false;
        }
        
        int numericCount = 0;
        int nonNumericCount = 0;
        int rowsToCheck = Math.min(5, data.size());
        
        for (int i = 0; i < rowsToCheck; i++) {
            try {
                Double.parseDouble(data.get(i)[index]);
                numericCount++;
            } catch (NumberFormatException e) {
                nonNumericCount++;
            }
        }
        
        return numericCount > nonNumericCount;
    }
} 
