package ui;

import datastructures.DataStructure;
import datastructures.MyArrayList;
import datastructures.LinkedList;
import datastructures.BinarySearchTree;
import algorithms.searching.SearchAlgorithm;
import algorithms.searching.LinearSearch;
import algorithms.searching.BinarySearch;
import algorithms.sorting.SortAlgorithm;
import algorithms.sorting.BubbleSort;
import algorithms.sorting.InsertionSort;
import utils.CSVLoader;
import utils.PerformanceTimer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

// no method chaining, trying different approach

// STRUCTURE:
/*
    ========= Dataset Panel ===============
                --- File Selection Panel
                --- Column Selection Panel
    ========= Options Panel ===============
                --- Data Structure Panel
                --- Algorithm Type Panel
                --- Algorithm Selection Panel
    ========= Search Panel =============== (conditional)
    ========= Subset Slider Panel =========
    ========= Run Button Panel ============
    ========= Results Panel ===============
*/
public class DSAView extends JFrame {
    // ========= Dataset Panel ===============
    private JComboBox<String> columnSelector;

    // ========= Options Panel ===============
    private JComboBox<String> dataStructureSelector;
    private JRadioButton sortRadio, searchRadio;
    private JComboBox<String> algorithmSelector;

    // ========= Search Panel ===============
    private JPanel searchPanel;
    private JTextField searchKeyField;

    // ========= Subset Slider Panel =========
    private JSlider subsetSlider;
    private JLabel subsetSizeLabel;

    // ========= Run Button Panel ============
    private JButton runButton;

    // ========= Results Panel ===============
    private JTextArea resultArea;
    private JLabel statusLabel;
    
    private CSVLoader csvLoader;
    private PerformanceTimer timer;
    private int selectedColumn = -1;
    private boolean isNumericColumn = false;
    
    public DSAView() {
        timer = new PerformanceTimer();

        setTitle("DSA Application");
        setSize(650, 900);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));
        
        initComponents();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        // main layout
        mainPanel.add(createDatasetPanel());
        mainPanel.add(createSeparator());
        mainPanel.add(createOptionsPanel());
        mainPanel.add(createSearchPanel());
        mainPanel.add(createSeparator());
        mainPanel.add(createSubsetSliderPanel());
        mainPanel.add(createSeparator());
        mainPanel.add(createRunButtonPanel());
        mainPanel.add(createSeparator());
        mainPanel.add(createResultsPanel());
        
        add(mainPanel, BorderLayout.CENTER);
        
        updateAlgorithmSelector();
    }

    // ---------------------------- DATASET PANEL ----------------------------

    private JPanel createDatasetPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        panel.add(createFileSelectionPanel());
        panel.add(createColumnSelectionPanel());
        
        return panel;
    }
    
    // file selection
    private JPanel createFileSelectionPanel() {
        JPanel panel = new JPanel();
        
        JButton browseButton = new JButton("Load CSV File");
        browseButton.addActionListener(e -> browseForCSV());
        
        panel.add(browseButton);
        
        return panel;
    }
    
    // column selection
    private JPanel createColumnSelectionPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        
        columnSelector = new JComboBox<>();
        columnSelector.setEnabled(false);
        columnSelector.addActionListener(e -> handleColumnSelection());
        
        panel.add(new JLabel("Column:"), BorderLayout.WEST);
        panel.add(columnSelector, BorderLayout.CENTER);
        
        return panel;
    }

    // ---------------------------- OPTIONS PANEL ----------------------------
    
    private JPanel createOptionsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        panel.add(createDataStructurePanel());
        panel.add(createAlgorithmTypePanel());
        panel.add(createAlgorithmSelectionPanel());
        
        return panel;
    }
    
    // DS selection
    private JPanel createDataStructurePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        dataStructureSelector = new JComboBox<>(new String[]{
            "Array", "Linked List", "Binary Search Tree"
        });
        
        panel.add(new JLabel("Data Structure:"));
        panel.add(dataStructureSelector);
        
        return panel;
    }

    // algorithm type selection
    private JPanel createAlgorithmTypePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        sortRadio = new JRadioButton("Sort", true);
        searchRadio = new JRadioButton("Search");
        
        sortRadio.addActionListener(e -> updateAlgorithmSelector());
        searchRadio.addActionListener(e -> updateAlgorithmSelector());

        // to setup mutual exclusivity
        ButtonGroup algorithmTypeGroup = new ButtonGroup();
        algorithmTypeGroup.add(sortRadio);
        algorithmTypeGroup.add(searchRadio);
        
        panel.add(new JLabel("Algorithm:"));
        panel.add(sortRadio);
        panel.add(searchRadio);
        
        return panel;
    }

    // algorithm selection
    private JPanel createAlgorithmSelectionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        algorithmSelector = new JComboBox<>();
        
        panel.add(new JLabel("Method:"));
        panel.add(algorithmSelector);
        
        return panel;
    }

    // ---------------------------- SEARCH PANEL ----------------------------
    
    private JPanel createSearchPanel() {
        searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        searchKeyField = new JTextField();
        
        searchPanel.add(new JLabel("Search Key:"), BorderLayout.WEST);
        searchPanel.add(searchKeyField, BorderLayout.CENTER);
        searchPanel.setVisible(false);
        
        return searchPanel;
    }

    // ---------------------------- SUBSET SLIDER PANEL ----------------------------
    
    private JPanel createSubsetSliderPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        subsetSizeLabel = new JLabel("Subset Size: 100%");
        subsetSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
        subsetSlider.setPaintTicks(true);
        subsetSlider.setPaintLabels(true);
        subsetSlider.setMajorTickSpacing(25);
        subsetSlider.addChangeListener(e -> updateSubsetLabel());
        
        panel.add(subsetSizeLabel, BorderLayout.NORTH);
        panel.add(subsetSlider, BorderLayout.CENTER);
        
        return panel;
    }

    // ---------------------------- RUN BUTTON PANEL ----------------------------
    
    private JPanel createRunButtonPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        runButton = new JButton("Run Algorithm");
        runButton.setEnabled(false);
        runButton.addActionListener(e -> runAlgorithm());
        
        panel.add(runButton);
        
        return panel;
    }

    // ---------------------------- RESULTS PANEL ----------------------------
    
    private JPanel createResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        
        statusLabel = new JLabel("Status: Ready");
        
        panel.add(new JLabel("Results:"), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(statusLabel, BorderLayout.SOUTH);
        
        return panel;
    }

    // ---------------------------- HELPER METHODS ----------------------------

    // simple horizontal separator
    private Component createSeparator() {
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return separator;
    }

    // update subset label
    private void updateSubsetLabel() {
        int percentage = subsetSlider.getValue();
        int dataSize = csvLoader != null && !csvLoader.getData().isEmpty() ? 
            csvLoader.getData().size() : 100;
        int actualSize = Math.max(0, dataSize * percentage / 100);
        subsetSizeLabel.setText("Subset Size: " + actualSize + " (" + percentage + "%)");
    }
    
    // update algorithm selector based on algorithm type change
    private void updateAlgorithmSelector() {
        algorithmSelector.removeAllItems();
        
        if (sortRadio.isSelected()) {
            algorithmSelector.addItem("Bubble Sort");
            algorithmSelector.addItem("Insertion Sort");
            searchPanel.setVisible(false);
        } else {
            algorithmSelector.addItem("Linear Search");
            algorithmSelector.addItem("Binary Search");
            searchPanel.setVisible(true);
        }
    }
    
    private void browseForCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select CSV File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV files (*.csv)", "csv"));
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            loadCSVFile(selectedFile.getAbsolutePath());
        }
    }
    
    private void loadCSVFile(String filePath) {
        csvLoader = new CSVLoader(filePath);
        if (csvLoader.loadCSV()) {
            
            // update column selector

            columnSelector.removeAllItems();
            String[] headers = csvLoader.getHeaders();
            
            if (headers != null) {
                for (String header : headers) {
                    columnSelector.addItem(header);
                }
            }
            
            columnSelector.setEnabled(true);
            statusLabel.setText("Status: CSV file loaded successfully (" + csvLoader.getData().size() + " rows)");
        } else {
            statusLabel.setText("Status: Failed to load CSV file");
        }
    }
    
    // whenever a column is selected, this executes
    private void handleColumnSelection() {
        if (columnSelector.getSelectedIndex() != -1) {
            selectedColumn = columnSelector.getSelectedIndex();
            isNumericColumn = csvLoader.isNumericColumn(selectedColumn);
            
            updateSubsetLabel();
            
            runButton.setEnabled(true);
            
            statusLabel.setText("Status: Selected column is " + (isNumericColumn ? "numeric" : "text"));
        }
    }
    
    // whenever the run button is clicked, this executes
    private void runAlgorithm() {
        resultArea.setText("");
        timer.reset();
        
        DataStructure dataStructure = createDataStructure();
        
        resultArea.append("Data Structure: " + dataStructureSelector.getSelectedItem());
        resultArea.append("\nAlgorithm: " + algorithmSelector.getSelectedItem());
        resultArea.append("\nData Size: " + dataStructure.size() + " elements\n\n");
        
        try {
            if (sortRadio.isSelected()) {
                runSortingAlgorithm(dataStructure);
            } else {
                runSearchAlgorithm(dataStructure);
            }
        } catch (Exception e) {
            timer.stop();
            resultArea.append("Error: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
        
        statusLabel.setText("Execution Time: " + timer.getElapsedTimeFormatted() + " (" + algorithmSelector.getSelectedItem() + ")");
    }

    private DataStructure createDataStructure() {
        String dsType = (String) dataStructureSelector.getSelectedItem();
        List<?> data;
        
        if (isNumericColumn) {
            data = csvLoader.getColumn(selectedColumn, Double.class);
        } else {
            data = csvLoader.getColumn(selectedColumn, String.class);
        }
        
        // apply subset
        int percentage = subsetSlider.getValue();
        int subsetSize = Math.max(1, data.size() * percentage / 100);
        if (subsetSize < data.size()) {
            data = data.subList(0, subsetSize);
        }
        
        // create data structure
        DataStructure dataStructure;
        
        if ("Array".equals(dsType)) {
            dataStructure = new MyArrayList<>();
        } else if ("Linked List".equals(dsType)) {
            dataStructure = new LinkedList<>();
        } else if ("Binary Search Tree".equals(dsType)) {
            dataStructure = new BinarySearchTree<>();
        } else {
            dataStructure = new MyArrayList<>(); // default
        }
        
        // add all data to the structure (through dedicated add method)
        for (Object value : data) {
            dataStructure.add(value);
        }
        
        return dataStructure;
    }
    
    private void runSortingAlgorithm(DataStructure dataStructure) {
        SortAlgorithm sorter = "Bubble Sort".equals(algorithmSelector.getSelectedItem()) 
            ? new BubbleSort() : new InsertionSort();
        
        timer.start();
        List<?> sortedList = sorter.sort(dataStructure);
        timer.stop();
        
        // display results
        resultArea.append("Sorted Results (first 20 elements): \n");
        int displayLimit = Math.min(sortedList.size(), 20);
        for (int i = 0; i < displayLimit; i++) {
            resultArea.append(sortedList.get(i) + "\n");
        }
        if (sortedList.size() > 20) {
            resultArea.append("... and " + (sortedList.size() - 20) + " more\n");
        }
    }
    
    private void runSearchAlgorithm(DataStructure dataStructure) {
        String searchKey = searchKeyField.getText().trim();
        if (searchKey.isEmpty()) {
            resultArea.setText("Please enter a search key");
            return;
        }
        
        SearchAlgorithm searcher = "Linear Search".equals(algorithmSelector.getSelectedItem())
            ? new LinearSearch() : new BinarySearch();
        
        List<Integer> indices;

        timer.start();

        if (isNumericColumn) {
            indices = searcher.search(dataStructure, Double.valueOf(searchKey));
        } else {
            indices = searcher.search(dataStructure, searchKey);
        }
        
        timer.stop();
        
        // display results
        resultArea.append("Search Results for '" + searchKey + "':\n");
        if (indices.isEmpty()) {
            resultArea.append("No matches found.\n");
        } else {
            resultArea.append("Found " + indices.size() + " matches at indices: ");
            for (int i = 0; i < indices.size(); i++) {
                resultArea.append(indices.get(i).toString());
                if (i < indices.size() - 1) resultArea.append(", ");
            }
            resultArea.append("\n");
        }
    }
} 